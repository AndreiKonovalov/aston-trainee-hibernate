package ru.konovalov.exception_handling;

public class NoContentException extends RuntimeException{

    public NoContentException(String message){
        super(message);
    }
}
