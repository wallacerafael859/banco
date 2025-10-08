package com.example.banco.handler;

import com.example.banco.exception.ClienteNaoEncontradoException;
import com.example.banco.model.ErroDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClienteNaoEncontradoException.class)
    public ResponseEntity<ErroDTO> handlerClienteNaoEncontradoException(ClienteNaoEncontradoException exception) {
        System.err.println("Cliente não encontrado(404)" + exception.getMessage());

        ErroDTO erro = new ErroDTO();

        erro.setStatus(HttpStatus.NOT_FOUND.value());
        erro.setErro("Not Found");
        erro.setMensagem(exception.getMessage());

        return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);


    }

    public ResponseEntity<ErroDTO> handleValidationException(MethodArgumentNotValidException exception) {

        List<String> erros = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map( e -> e.getField() + ": " + e.getDefaultMessage())
                .toList();

        ErroDTO erro = new ErroDTO();
        erro.setStatus(HttpStatus.BAD_REQUEST.value());
        erro.setErro("Bad Request");
        erro.setMensagem("Falha na validação: " + String.join("; ") + erros);

        return new ResponseEntity<>(erro, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroDTO> handlerGenericErro(Exception exception) {

        exception.printStackTrace();

        ErroDTO erro = new ErroDTO();
        erro.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        erro.setErro("Internal Server Error");
        erro.setMensagem("Ocorreu um Erro Inesperado no Servidor.");

        return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);

    }


}