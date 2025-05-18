package com.biopark.cepex.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "arquivo_orcamento")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ArquivoOrcamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    private String nomeArquivo;
    private String caminhoArquivo;
    private String tipoConteudo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cadastro_evento_id")
    @ToString.Exclude
    private CadastroEvento cadastroEvento;
}
