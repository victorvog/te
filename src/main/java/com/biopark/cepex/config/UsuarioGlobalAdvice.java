package com.biopark.cepex.config;

import com.biopark.cepex.model.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class UsuarioGlobalAdvice {

    @ModelAttribute
    public void adicionarUsuarioLogadoNoModel(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario != null) {
            model.addAttribute("usuarioLogado", usuario);
        }
    }
}