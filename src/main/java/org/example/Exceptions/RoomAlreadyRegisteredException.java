package org.example.Exceptions;

public class RoomAlreadyRegisteredException extends RuntimeException{
    public RoomAlreadyRegisteredException(String message) {
        super(message);
    }
}
