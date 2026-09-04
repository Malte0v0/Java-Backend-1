package org.example.javabackend1.Exceptions;

public class RoomDatesInvalid extends RuntimeException {
    public RoomDatesInvalid(String message) {
        super(message);
    }
}
