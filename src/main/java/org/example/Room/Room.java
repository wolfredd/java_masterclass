package org.example.Room;

import java.util.Objects;

public abstract class Room {
    private int roomNumber;
    private int floorNumber;
    private int capacity;
    private double ratePerNight;
    private String kindOfRoom;
    private KindOfRoomType kindOfRoomType;


    public Room(int roomNumber, int floorNumber, String kindOfRoom, KindOfRoomType kindOfRoomType) {
        this.roomNumber = roomNumber;
        this.floorNumber = floorNumber;
        this.capacity = kindOfRoomType.getMaxCapacity();
        this.kindOfRoom = kindOfRoom;
        this.kindOfRoomType = kindOfRoomType;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomNumber == room.roomNumber && floorNumber == room.floorNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, floorNumber);
    }

//
//    public int getRoomNumber() {
//        return roomNumber;
//    }
//
//    public void setRoomNumber(int roomNumber) {
//        this.roomNumber = roomNumber;
//    }
//
//    public int getFloorNumber() {
//        return floorNumber;
//    }
//
//    public void setFloorNumber(int floorNumber) {
//        this.floorNumber = floorNumber;
//    }
//
//
//
//    public void setCapacity(int capacity) {
//        this.capacity = capacity;
//    }
//
//    public double getRatePerNight() {
//        return ratePerNight;
//    }
//
//    public void setRatePerNight(double ratePerNight) {
//        this.ratePerNight = ratePerNight;
//    }
//
//    public String getKindOfRoom() {
//        return kindOfRoom;
//    }
//
//    public void setKindOfRoom(String kindOfRoom) {
//        this.kindOfRoom = kindOfRoom;
//    }
//
//
//    public void setKindOfRoomType(KindOfRoomType kindOfRoomType) {
//        this.kindOfRoomType = kindOfRoomType;
//    }
//    public KindOfRoomType getKindOfRoomType() {
//        return kindOfRoomType;
//    }

}
