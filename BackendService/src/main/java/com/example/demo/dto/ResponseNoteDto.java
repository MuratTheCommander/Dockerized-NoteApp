package com.example.demo.dto;

import java.time.OffsetDateTime;

public class ResponseNoteDto {

    // TODO: Revisit for improvements

    private long noteId;

    private long userId;

    private String textContent;

    private OffsetDateTime createDate;

    private OffsetDateTime updateDate;

    private boolean isPinned;

    private boolean isArchived;

    public ResponseNoteDto(){

    }

    public ResponseNoteDto(long noteId, long userId, String textContent, boolean isPinned, boolean isArchived) {
        this.noteId = noteId;
        this.userId = userId;
        this.textContent = textContent;
        this.isPinned = isPinned;
        this.isArchived = isArchived;
    }

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public OffsetDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(OffsetDateTime createDate) {
        this.createDate = createDate;
    }

    public OffsetDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(OffsetDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public void setPinned(boolean pinned) {
        isPinned = pinned;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

}
