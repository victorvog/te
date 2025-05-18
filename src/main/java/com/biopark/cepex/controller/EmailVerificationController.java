package com.biopark.cepex.controller;

import com.biopark.cepex.repository.UsuarioRepository;  // Importando o repositório
import org.springframework.beans.factory.annotation.Autowired;  // Para injetar o repositório
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.HashMap;

@RestController
public class EmailVerificationController {

    @Autowired
    private UsuarioRepository usuarioRepository;  // Injeção de dependência do repositório

    @GetMapping("/verificar-email")
    public ResponseEntity<Map<String, Boolean>> verificarEmail(@RequestParam String email) {
        // Lógica de verificação se o e-mail já existe
        boolean exists = usuarioRepository.existsByEmail(email);  // Usando a instância do repositório

        // Criando o mapa para resposta
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);

        // Retornando a resposta
        return ResponseEntity.ok(response);
    }
}
