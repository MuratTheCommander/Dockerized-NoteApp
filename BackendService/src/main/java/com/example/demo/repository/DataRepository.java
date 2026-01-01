package com.example.demo.repository;

import com.example.demo.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DataRepository extends JpaRepository<Note, Long> {

    List<Note> findByUserId(long userId);

    Optional<Note> findByNoteIdAndUserId(long userId, long noteId);

    int deleteByNoteIdAndUserId(long userId, long noteId);

}
