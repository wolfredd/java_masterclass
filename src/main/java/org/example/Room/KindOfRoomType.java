package org.example.Room;

public enum KindOfRoomType {
    Double(false, 2),
    Suite(true, 3),
    Twin(false, 2);

    private boolean breakfastWithRoom;
    private int maxCapacity;

    KindOfRoomType(boolean breakfastWithRoom, int maxCapacity) {
        this.breakfastWithRoom = breakfastWithRoom;
        this.maxCapacity = maxCapacity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

//    public void setMaxCapacity(int maxCapacity) {
//        this.maxCapacity = maxCapacity;
//    }
//
//    public boolean isBreakfastWithRoom() {
//        return breakfastWithRoom;
//    }
//
//    public void setBreakfastWithRoom(boolean breakfastWithRoom) {
//        this.breakfastWithRoom = breakfastWithRoom;
//    }
}
