package com.biopark.cepex.handler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

//@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)  // Captura qualquer exceção
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  // Define o código de status HTTP 500
    public String handleAllExceptions(Exception ex, Model model) {

        System.err.println("Erro interno detectado:");
        ex.printStackTrace();  // Mostra a stack trace no console (sem precisar de logger)
        model.addAttribute("errorMessage", "Erro interno. Detalhes técnicos foram registrados no console.");

        return "error/500";  // Redireciona para a página de erro personalizada
    }

    // Tratar erro 404 (Página não encontrada)
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(NoHandlerFoundException ex, Model model) {
        model.addAttribute("errorMessage", "A página solicitada não foi encontrada.");
        return "error/404";  // Redireciona para a página 404
    }
}


