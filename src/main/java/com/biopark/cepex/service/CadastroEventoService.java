package com.biopark.cepex.service;

import com.biopark.cepex.dto.CadastroEventoForm;
import com.biopark.cepex.model.ArquivoOrcamento;
import com.biopark.cepex.model.CadastroEvento;
import com.biopark.cepex.repository.CadastroEventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Service
@Transactional
@RequiredArgsConstructor
public class CadastroEventoService {

    private final CadastroEventoRepository eventoRepo;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public CadastroEvento salvar(CadastroEventoForm form) {
        // 1) Mapeia DTO → Entidade
        CadastroEvento ev = new CadastroEvento();
        ev.setTitulo(form.getTitulo());
        ev.setPalavras(form.getPalavras());
        ev.setProponente(form.getProponente());
        ev.setDepartamento(form.getDepartamento());
        ev.setClassificacao(form.getClassificacao());
        ev.setLocal(form.getLocal());
        ev.setDescricao(form.getDescricao());
        ev.setInicio(form.getInicio());
        ev.setFim(form.getFim());
        ev.setTipoEvento(form.getTipoEvento());
        ev.setPublicoAlvo(form.getPublicoAlvo());
        ev.setNumParticipantes(form.getNumParticipantes());
        ev.setOrganizadores(form.getOrganizadores());
        ev.setPalestrantes(form.getPalestrantes());
        ev.setMaterialDivulgacao(form.getMaterialDivulgacao());
        ev.setTaxaInscricao(form.getTaxaInscricao());
        ev.setVagasDisponiveis(form.getVagasDisponiveis());
        ev.setCredenciamento(form.getCredenciamento());
        ev.setCertificados(form.getCertificados());
        ev.setProjetoVinculado(form.getProjetoVinculado());
        ev.setValorEstimado(form.getValorEstimado());
        ev.setDetalhesOrcamento(form.getDetalhesOrcamento());
        ev.setInfoAdicionalOrcamento(form.getInfoAdicionalOrcamento());

        // 2) Persiste primeiro para obter ID
        ev = eventoRepo.save(ev);

        // 3) Processa upload de arquivos
        MultipartFile[] arquivos = form.getOrcamentosAnexados();
        if (arquivos != null) {
            for (MultipartFile f : arquivos) {
                if (f.isEmpty()) {
                    continue;
                }
                try {
                    // Cria pasta evento-{id} se não existir
                    Path pasta = Paths.get(uploadDir, "evento-" + ev.getId());
                    Files.createDirectories(pasta);

                    // Salva o arquivo no disco
                    Path destino = pasta.resolve(f.getOriginalFilename());
                    f.transferTo(destino.toFile());

                    // Cria e associa o ArquivoOrcamento
                    ArquivoOrcamento ao = new ArquivoOrcamento();
                    ao.setNomeArquivo(f.getOriginalFilename());
                    ao.setCaminhoArquivo(destino.toString());
                    ao.setTipoConteudo(f.getContentType());
                    ao.setCadastroEvento(ev);
                    ev.getOrcamentosAnexados().add(ao);

                } catch (IOException e) {
                    throw new RuntimeException(
                            "Erro ao salvar arquivo " + f.getOriginalFilename(), e);
                }
            }
            // 4) Persiste novamente para gravar os ArquivoOrcamento no banco
            ev = eventoRepo.save(ev);
        }

        // 5) Retorna a entidade completa
        return ev;
    }
}