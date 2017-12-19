package com.nenton.schools.data.storage.dto;

import java.util.List;

/**
 * Created by serge on 19.12.2017.
 */

public class RoomsOfSchool {

    private List<Room> rooms;

    public List<Room> getRooms() {
        return rooms;
    }

    public class Room {

        private String name;
        private String teacher;

        public Room(String name, String teacher) {
            this.name = name;
            this.teacher = teacher;
        }

        public String getName() {
            return name;
        }

        public String getTeacher() {
            return teacher;
        }
    }
}
