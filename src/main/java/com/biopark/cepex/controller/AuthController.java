package com.biopark.cepex.controller;

import com.biopark.cepex.model.PasswordResetToken;
import com.biopark.cepex.model.TipoUsuario;
import com.biopark.cepex.model.Usuario;
import com.biopark.cepex.repository.PasswordResetTokenRepository;
import com.biopark.cepex.repository.UsuarioRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import javax.naming.Binding;
import java.time.LocalDateTime;
import java.util.UUID;

@Controller
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @GetMapping("/login")
    public String exibirLogin() {
        return "login"; //
    }

    @GetMapping("/cadastro")
    public String exibirCadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastro"; //
    }

    @PostMapping("/cadastro")
    public String processarCadastro(@Valid @ModelAttribute Usuario usuario, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "cadastro";
        }

        if (usuarioRepository.findByEmail(usuario.getEmail()) != null) {
            model.addAttribute("erroEmailExistente", "Já existe uma conta cadastrada com este e-mail. Tente fazer login.");
            return "cadastro";
        }

        if (usuarioRepository.findByRa(usuario.getRa()) != null) {
            model.addAttribute("erroRaExistente", "Já existe uma conta cadastrada com este RA. Tente fazer login.");
            return "cadastro";
        }

        String email = usuario.getEmail().toLowerCase();

        if(email.endsWith("@alunos.bpkedu.com.br")){
            usuario.setTipoUsuario(TipoUsuario.ALUNO);
        } else if (email.endsWith("@professor.bpkedu.com.br")) {
            usuario.setTipoUsuario(TipoUsuario.PROFESSOR);
        } else if (email.endsWith("@coordenador.bpkedu.com.br")) {
            usuario.setTipoUsuario(TipoUsuario.COORDENADOR);
        }else {
            model.addAttribute("erro", "Domínio de e-mail inválido.");
            return "cadastro";
        }

        // Validar senha
        String senha = usuario.getSenha();
        String regexSenha = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$";
        if (!senha.matches(regexSenha)) {
            model.addAttribute("erro", "A senha deve conter de 8 a 16 caracteres, com pelo menos uma letra maiúscula, um número e um caractere especial.");
            model.addAttribute("usuario", usuario);
            return "cadastro";
        }

        // Criptografando a senha
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada); // Salvando a senha criptografada

        usuarioRepository.save(usuario);
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String processarLogin(@RequestParam String email,
                                 @RequestParam String senha,
                                 Model model,
                                 HttpSession session) {
        Usuario usuario = usuarioRepository.findByEmail(email);

        if (usuario == null || !passwordEncoder.matches(senha, usuario.getSenha())) {
            model.addAttribute("erro", "Credenciais inválidas. Verifique seu E-mail e senha.");
            return "login";
        }

        session.setAttribute("usuarioLogado", usuario); // login salvo na sessão

        return "redirect:/home";
    }

    @PostMapping("/recuperar-senha")
    public String gerarTokenRecuperacao(@RequestParam String email, Model model) {
        Usuario usuario = usuarioRepository.findByEmail(email);

        if (usuario == null) {
            model.addAttribute("mensagemRecuperacao", "E-mail não encontrado.");
            return "login";
        }

        PasswordResetToken tokenAntigo = tokenRepository.findByUsuario(usuario);
        if (tokenAntigo != null) {
            tokenRepository.delete(tokenAntigo);
        }

        String token = UUID.randomUUID().toString();
        PasswordResetToken prt = new PasswordResetToken();
        prt.setToken(token);
        prt.setUsuario(usuario);
        prt.setValidade(LocalDateTime.now().plusMinutes(15));
        tokenRepository.save(prt);

        model.addAttribute("mensagemRecuperacao", "Use este link para redefinir sua senha: /redefinir?token=" + token);
        return "login";
    }

    @GetMapping("/redefinir")
    public String mostrarFormularioRedefinicao(@RequestParam String token, Model model) {
        PasswordResetToken prt = tokenRepository.findByToken(token);
        if (prt == null || prt.getValidade().isBefore(LocalDateTime.now())) {
            model.addAttribute("erro", "Token inválido ou expirado.");
            return "login";
        }

        model.addAttribute("token", token);
        return "redefinir";
    }

    @PostMapping("/redefinir")
    public String redefinirSenha(@RequestParam String token,
                                 @RequestParam String novaSenha,
                                 Model model) {
        PasswordResetToken prt = tokenRepository.findByToken(token);
        if (prt == null || prt.getValidade().isBefore(LocalDateTime.now())) {
            model.addAttribute("erro", "Token inválido ou expirado.");
            return "login";
        }

        Usuario usuario = prt.getUsuario();
        usuario.setSenha(passwordEncoder.encode(novaSenha)); // atualiza a senha criptografada em memória

        usuarioRepository.save(usuario); // salva a alteração no banco

        tokenRepository.delete(prt); // apaga o token

        model.addAttribute("mensagemRecuperacao", "Senha redefinida com sucesso.");
        return "login";
    }
}
