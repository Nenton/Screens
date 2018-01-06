package com.nenton.schools.data.storage.dto;

/**
 * Created by serge on 06.01.2018.
 */

public class EntityHistoryPass {
    private String schoolId;
    private String room;
    private String teacher;
    private long start;
    private long end;

    public EntityHistoryPass() {
    }

    public EntityHistoryPass(String schoolId, String room, String teacher, long start, long end) {
        this.schoolId = schoolId;
        this.room = room;
        this.teacher = teacher;
        this.start = start;
        this.end = end;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }
}
