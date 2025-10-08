package com.example.banco.exception;

public class ClienteNaoEncontradoException extends RuntimeException {
    public ClienteNaoEncontradoException (String message){
        super(message);
    }
}
