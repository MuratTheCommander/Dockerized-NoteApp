package com.example.demo.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(name = "notes")
public class Note {

    // TODO: Revisit for improvements


    protected Note(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,name = "note_id")
    private long noteId;

    @Column(nullable = false,name = "userid")
    private long userId;

    @Lob
    @Column(nullable = false,name = "note_content")
    private String textContent;

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

    @Column(nullable = false,name = "create_date")
    @CreationTimestamp
    private OffsetDateTime createDate;

    @UpdateTimestamp
    @Column(nullable = false,name = "update_date")
    private OffsetDateTime updateDate;

    @Column(nullable = false,name = "ispinned")
    private boolean isPinned;

    @Column(nullable = false,name = "isarchived")
    private boolean isArchived;
}
