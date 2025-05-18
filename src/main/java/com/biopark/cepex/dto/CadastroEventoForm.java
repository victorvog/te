package com.biopark.cepex.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter @Setter
public class CadastroEventoForm {
    private String titulo;
    private String palavras;
    private String proponente;
    private String departamento;
    private String classificacao;
    private String local;
    private String descricao;
    private LocalDate inicio;
    private LocalDate fim;

    // Campos específicos para 'evento'
    private String tipoEvento;
    private String publicoAlvo;
    private Integer numParticipantes;
    private String organizadores;
    private String palestrantes;
    private String materialDivulgacao;
    private String taxaInscricao;
    private Integer vagasDisponiveis;
    private String credenciamento;
    private String certificados;
    private String projetoVinculado;
    private String valorEstimado;
    private String detalhesOrcamento;
    private String infoAdicionalOrcamento;

    // arquivos
    private MultipartFile[] orcamentosAnexados;
}
