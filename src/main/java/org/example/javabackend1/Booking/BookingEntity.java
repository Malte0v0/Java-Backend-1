package org.example.javabackend1.Booking;

import jakarta.persistence.*;

import org.example.javabackend1.Room.RoomEntity;

import java.time.LocalDate;


@Entity
@Table (name = "booking")
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomEntity room;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int numberOfGuests;

    public BookingEntity() {
    }

    public BookingEntity(Long id, Long customerId, LocalDate checkInDate, RoomEntity room, LocalDate checkOutDate, int numberOfGuests) {
        this.id = id;
        this.customerId = customerId;
        this.checkInDate = checkInDate;
        this.room = room;
        this.checkOutDate = checkOutDate;
        this.numberOfGuests = numberOfGuests;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public RoomEntity getRoom() {
        return room;
    }

    public void setRoom(RoomEntity room) {
        this.room = room;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }
}
