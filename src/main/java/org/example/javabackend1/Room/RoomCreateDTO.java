package org.example.javabackend1.Room;

import jakarta.validation.constraints.NotNull;

public class RoomCreateDTO {
    @NotNull
    private Long roomId;

    @NotNull
    private RoomType roomType;

    @NotNull
    private int extraBeds;
}
