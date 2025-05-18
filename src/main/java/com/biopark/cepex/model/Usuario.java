package com.biopark.cepex.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório.")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ]+\\s+[A-Za-zÀ-ÿ]+.*$", message = "Digite seu nome completo (nome e sobrenome).")
    @Column(nullable = false)
    private String nome;

    @Pattern(regexp = "\\d{8}", message = "Digite um RA válido.")
    @Column(nullable = false)
    private String ra;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Pattern(
            regexp = "^[a-zA-Z]+\\.[a-zA-Z]+@(alunos|professor|coordenador)\\.bpkedu\\.com\\.br$",
            message = "Utilize um e-mail institucional válido"
    )
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario", nullable = false)
    private TipoUsuario tipoUsuario;

}
