package br.com.rformagio.person.exception;

import lombok.Getter;

public class PersonNotFoundException extends Exception{
    @Getter
    String id;

    public PersonNotFoundException(String id, String errorMessage) {
        super(errorMessage);
        this.id = id;
    }

}
