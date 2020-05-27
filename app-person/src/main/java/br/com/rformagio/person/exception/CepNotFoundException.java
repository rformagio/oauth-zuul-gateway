package br.com.rformagio.person.exception;

import lombok.Getter;

public class CepNotFoundException extends Exception {

    @Getter
    String cep;

    public CepNotFoundException(String cep, String errorMessage){
        super(errorMessage);
        this.cep = cep;
    }
}
