package org.example.Hotel;

import org.example.BookingService.RoomPricingService;
import org.example.Exceptions.RoomAlreadyRegisteredException;
import org.example.Room.*;
import org.example.Room.Double;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ENSHotelImplementationTest {

    ENSHotelImplementation ensHotelImplementation;
    RoomPricingService roomPricingService;
    Room room1;
    Room room2;
    Room room3;
    Room room4;
    Room room5;

    @BeforeEach
    public void setUp(){
        roomPricingService = mock(RoomPricingService.class);
        ensHotelImplementation = new ENSHotelImplementation(roomPricingService);
        room1 = new Double(1, 2, "Double", KindOfRoomType.Double);
        room2 = new Suite(2, 2, "Suite", KindOfRoomType.Suite);
        room3 = new Twin(3, 2, "Twin", KindOfRoomType.Twin);
        room4 = new Suite(2, 2, "Suite", KindOfRoomType.Suite);
        room5 = new Suite(5, 2, "Suite", KindOfRoomType.Suite);
    }

    @Test
    public void roomsAddedSuccessfully(){

        ensHotelImplementation.addRoom(room1);
        ensHotelImplementation.addRoom(room2);
        ensHotelImplementation.addRoom(room3);

        assertEquals(3, ensHotelImplementation.numberOfRoomsAddedToListOfRooms());

        //All three rooms were added to the list of hotel rooms
    }

    @Test
    public void whenRoomFailedToAddToListBecauseRoomIsAlreadyInList(){
        ensHotelImplementation.addRoom(room1);
        ensHotelImplementation.addRoom(room2);

        assertThrows(RoomAlreadyRegisteredException.class, () ->ensHotelImplementation.addRoom(room2));

        //room2 was not added to the list of hotel rooms because it is already in the list

    }

    @Test
    public void whenRoomFailedToAddToListBecauseDuplicateCombinationOfRoomNumberAndFloorNumberFound(){
        ensHotelImplementation.addRoom(room1);
        ensHotelImplementation.addRoom(room2);

        assertThrows(RoomAlreadyRegisteredException.class, () ->ensHotelImplementation.addRoom(room4));

        //room4 was not added to the list of hotel rooms because it has the same room number and floor number as room2, so they are the same

    }

    @Test
    public void whenBookingRoomSucceeds(){
        ensHotelImplementation.addRoom(room1);
        ensHotelImplementation.addRoom(room2);
        ensHotelImplementation.addRoom(room3);

        ensHotelImplementation.bookRoom(room1, 2, true);
        ensHotelImplementation.bookRoom(room2, 2, false);
        ensHotelImplementation.bookRoom(room3, 2, true);

        assertEquals(3, ensHotelImplementation.numberOfRoomsBooked());

        //All three rooms in the list of hotel rooms were booked successfully

    }

    @Test
    public void whenRoomAlreadyBookedAndAreReplacingOldBooking(){
        ensHotelImplementation.addRoom(room1);
        ensHotelImplementation.addRoom(room2);
        ensHotelImplementation.addRoom(room3);

        ensHotelImplementation.bookRoom(room1, 2, true);
        ensHotelImplementation.bookRoom(room1, 1, true);

        ensHotelImplementation.bookRoom(room2, 2, true);
        ensHotelImplementation.bookRoom(room2, 1, true);

        ensHotelImplementation.bookRoom(room3, 2, true);
        ensHotelImplementation.bookRoom(room3, 1, true);

        assertEquals(3, ensHotelImplementation.numberOfRoomsBooked());

        //ensHotelImplementation.bookRoom was called six times yet only three rooms were booked because 3 room bookings were replaced with new ones

    }

    @Test
    public void whenBookingRoomFailsBecauseRoomIsNotAvailable(){

        ensHotelImplementation.addRoom(room1);
        ensHotelImplementation.addRoom(room2);
        ensHotelImplementation.addRoom(room3);

        ensHotelImplementation.bookRoom(room1, 2, true);
        ensHotelImplementation.bookRoom(room2, 2, true);
        ensHotelImplementation.bookRoom(room3, 2, true);
        ensHotelImplementation.bookRoom(room5, 2, true);

        assertNotEquals(4, ensHotelImplementation.numberOfRoomsBooked());
        assertEquals(3, ensHotelImplementation.numberOfRoomsBooked());

        //room5 was not booked because it is not in the list of hotel rooms so there are only 3 rooms in the list
    }

    @Test
    public void whenBookingRoomFailsBecauseNumberOfGuestsIsGreaterThanRoomCapacity(){
        ensHotelImplementation.addRoom(room1);
        ensHotelImplementation.addRoom(room2);
        ensHotelImplementation.addRoom(room3);
        ensHotelImplementation.addRoom(room5);

        ensHotelImplementation.bookRoom(room1, 2, true);
        ensHotelImplementation.bookRoom(room2, 2, false);
        ensHotelImplementation.bookRoom(room3, 1, true);
        ensHotelImplementation.bookRoom(room5, 5, false);

        assertNotEquals(4, ensHotelImplementation.numberOfRoomsBooked());
        assertEquals(3, ensHotelImplementation.numberOfRoomsBooked());

        //room5 was not booked because the number of guests set for room5 is greater than its capacity

    }

    @Test
    public void gettingTotalGuestForTodayWhenAllRoomsInListAreBooked(){
        ensHotelImplementation.addRoom(room1);
        ensHotelImplementation.addRoom(room2);
        ensHotelImplementation.addRoom(room3);

        ensHotelImplementation.bookRoom(room1, 2, true);
        ensHotelImplementation.bookRoom(room2, 1, true);
        ensHotelImplementation.bookRoom(room3, 1, false);

        assertEquals(4, ensHotelImplementation.totalGuestsForToday());

        //The number of guests in the hotel was returned accurately
    }

    @Test
    public void gettingTotalGuestForTodayWhenSomeRoomsFailedToBook(){
        ensHotelImplementation.addRoom(room1);
        ensHotelImplementation.addRoom(room2);
        ensHotelImplementation.addRoom(room3);

        ensHotelImplementation.bookRoom(room1, 2, true);
        ensHotelImplementation.bookRoom(room2, 1, false);
        ensHotelImplementation.bookRoom(room3, 5, true);

        assertEquals(3, ensHotelImplementation.totalGuestsForToday());
        assertNotEquals(8, ensHotelImplementation.totalGuestsForToday());

        //The number of guests for room3 was not counted because in room3's booking, number of guests exceeds its capacity so room3 was not booked
    }

    @Test
    public void gettingTotalValueOfBookingsForToday(){
        //roomPricingService = mock(RoomPricingService.class);
        when(roomPricingService.price(anyInt(), anyBoolean())).thenReturn(5.0);

        ensHotelImplementation.addRoom(room1);
        ensHotelImplementation.addRoom(room2);
        ensHotelImplementation.addRoom(room3);

        ensHotelImplementation.bookRoom(room1, 2, false);
        ensHotelImplementation.bookRoom(room2, 1, false);
        ensHotelImplementation.bookRoom(room3, 1, false);

        Mockito.verify(roomPricingService, times(3)).price(anyInt(), anyBoolean());

        assertEquals(15.0, ensHotelImplementation.totalValueOfBookingsForToday());

        //The price for each room was mocked to 5.0 so the three rooms booked resulted in a value of 15
        //roomPricingService.price() was called 3 times because three rooms were booked
    }

    @Test
    public void gettingTotalValueOfBookingsForTodayWhenSomeRoomsFailedToBook(){
        //roomPricingService = mock(RoomPricingService.class);
        when(roomPricingService.price(anyInt(), anyBoolean())).thenReturn(5.0);

        ensHotelImplementation.addRoom(room1);
        ensHotelImplementation.addRoom(room2);
        ensHotelImplementation.addRoom(room3);

        ensHotelImplementation.bookRoom(room1, 2, true);
        ensHotelImplementation.bookRoom(room2, 1, false);
        ensHotelImplementation.bookRoom(room3, 5, true);

        Mockito.verify(roomPricingService, times(2)).price(anyInt(), anyBoolean());

        assertEquals(10.0, ensHotelImplementation.totalValueOfBookingsForToday());

        //Only two rooms were booked so the total value of the day was 10.0
        //roomPricingService.price() was called 2 times because two rooms were booked
    }
}

