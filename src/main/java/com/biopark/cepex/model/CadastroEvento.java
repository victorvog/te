package com.biopark.cepex.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cadastro_evento")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CadastroEvento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    private String titulo;
    private String palavras;
    private String proponente;
    private String departamento;
    private String classificacao;
    private String local;
    @Column(columnDefinition = "TEXT")
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
    @Column(columnDefinition = "TEXT")
    private String detalhesOrcamento;
    private String infoAdicionalOrcamento;

    @OneToMany(mappedBy = "cadastroEvento", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<ArquivoOrcamento> orcamentosAnexados = new ArrayList<>();
}
