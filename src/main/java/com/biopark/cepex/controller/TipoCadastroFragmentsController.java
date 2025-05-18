package com.biopark.cepex.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fragments")
public class TipoCadastroFragmentsController {

    @GetMapping("/evento")
    public String eventoFragment() {
        // retorna apenas o fragmento "specificFields" do template evento.html
        return "fragments/evento :: specificFields";
    }

    @GetMapping("/monitoria")
    public String monitoriaFragment() {
        return "fragments/monitoria :: specificFields";
    }

    @GetMapping("/ppe")
    public String ppeFragment() {
        return "fragments/ppe :: specificFields";
    }
}