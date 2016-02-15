package com.giyer7.dropanote.beans;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by giyer7 on 2/14/16.
 */
public class Note extends RealmObject {
    private String note;
    @PrimaryKey
    private long addedTime;
    private long dueTime;
    private boolean isComplete;

    public Note() {
    }

    public Note(String note, long addedTime, long dueTime, boolean isComplete) {
        this.note = note;
        this.addedTime = addedTime;
        this.dueTime = dueTime;
        this.isComplete = isComplete;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(long addedTime) {
        this.addedTime = addedTime;
    }

    public long getDueTime() {
        return dueTime;
    }

    public void setDueTime(long dueTime) {
        this.dueTime = dueTime;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }
}
