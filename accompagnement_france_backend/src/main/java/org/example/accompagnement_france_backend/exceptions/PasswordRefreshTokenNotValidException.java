package org.example.accompagnement_france_backend.exceptions;

public class PasswordRefreshTokenNotValidException extends RuntimeException{
    public PasswordRefreshTokenNotValidException(String message){
        super(message);
    }
}
