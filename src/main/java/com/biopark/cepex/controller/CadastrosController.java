package com.biopark.cepex.controller;

import com.biopark.cepex.dto.CadastroEventoForm;
import com.biopark.cepex.service.CadastroEventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/cadastros")
@RequiredArgsConstructor
public class CadastrosController {

    private final CadastroEventoService eventoService;
    // injete outros services aqui, se precisar

    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("page", "cadastros");
        model.addAttribute("form", new CadastroEventoForm());
        // model.addAttribute("departamentos", departamentoService.findAll());
        return "cadastros";
    }

    @PostMapping
    @Transactional
    public String handleSubmit(
            @ModelAttribute("form") CadastroEventoForm form,
            @RequestParam(required = false) MultipartFile[] orcamentosAnexados
    ) {
        form.setOrcamentosAnexados(orcamentosAnexados);

        switch (form.getClassificacao()) {
            case "evento":
                eventoService.salvar(form);
                break;
            // case "monitoria": …
            // case "ppe": …
            default:
                throw new IllegalArgumentException("Classificação não suportada");
        }

        return "redirect:/cadastros?sucesso";
    }

    // ← NOVO: rota que devolve apenas o HTML do fragmento
    @GetMapping(
            value = "/fragment/{tipo}",
            produces = MediaType.TEXT_HTML_VALUE
    )
    @ResponseBody
    public String getFragment(
            @PathVariable String tipo,
            Model model
    ) {
        // Se seu fragmento precisar de dados (ex.: departamentos), adicione aqui:
        // model.addAttribute("departamentos", departamentoService.findAll());

        // Retorna só o pedaço de HTML do fragmento
        return "fragments/" + tipo + " :: specificFields";
    }
}
