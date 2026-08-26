package org.example.javabackend1.Booking;

import jakarta.validation.constraints.NotNull;

public class BookingCreateDTO {
    @NotNull
    private Long customerId;

    @NotNull
    private Long roomId;

    @NotNull
    private String checkInDate;

    @NotNull
    private String checkOutDate;

    @NotNull
    private int numberOfGuests;

    public BookingCreateDTO(Long customerId, Long roomId, String checkInDate, String checkOutDate, int numberOfGuests) {
        this.customerId = customerId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfGuests = numberOfGuests;
    }

    public BookingCreateDTO() {
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }
}
