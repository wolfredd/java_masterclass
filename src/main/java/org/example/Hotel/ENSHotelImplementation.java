package org.example.Hotel;

import org.example.BookingService.RoomPricingService;
import org.example.Exceptions.RoomAlreadyRegisteredException;
import org.example.Room.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ENSHotelImplementation implements ENSHotel{
    private final RoomPricingService roomPricingService;
    private final List<Room> listOfRooms = new ArrayList<>();
    private final Map<Room, Boolean> bookedRooms = new HashMap<>();
    private final Map<Room, Double> priceOfRooms = new HashMap<>();
    private final Map<Room, Integer> numberOfGuestsInRoom = new HashMap<>();
    private double totalValueOfBookingsForToday;
    private int totalNumberOfGuestForToday;

    public ENSHotelImplementation(RoomPricingService roomPricingService) {
        this.roomPricingService = roomPricingService;
    }

    @Override
    public void addRoom(Room room) throws RoomAlreadyRegisteredException {
        if(!listOfRooms.contains(room)){
            listOfRooms.add(room);
        }
        else {
            throw new RoomAlreadyRegisteredException("This room has already been registered");
        }
    }

    @Override
    public void bookRoom(Room room, int numOfGuests, boolean includeBreakfast) {
        if(listOfRooms.contains(room) && numOfGuests <= room.getCapacity()){
            bookedRooms.put(room, true);
            double priceOfRoom = roomPricingService.price(numOfGuests, includeBreakfast);
            priceOfRooms.put(room, priceOfRoom);
            numberOfGuestsInRoom.put(room, numOfGuests);
        }
    }

    @Override
    public int totalGuestsForToday() {
        for (int value : numberOfGuestsInRoom.values()) {
            totalNumberOfGuestForToday = totalNumberOfGuestForToday + value;
        }
        return totalNumberOfGuestForToday;
    }

    @Override
    public double totalValueOfBookingsForToday() {
        for (double value : priceOfRooms.values()) {
            totalValueOfBookingsForToday = totalValueOfBookingsForToday + value;
        }
        return totalValueOfBookingsForToday;
    }

    public int numberOfRoomsAddedToListOfRooms(){
        int i = 0;
        for (Room room: listOfRooms){
            i++;
        }
        return i;
    }

    public int numberOfRoomsBooked(){
        return bookedRooms.size();
    }
}
