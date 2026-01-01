package com.example.demo.controller;

import com.example.demo.dto.RequestNoteDto;
import com.example.demo.dto.ResponseNoteDto;
import com.example.demo.entity.Note;
import com.example.demo.repository.DataRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/note")
public class NoteController {

    @Autowired
    DataRepository dataRepository;

    //hi test
    @GetMapping("hi")
    public String hiTest(){
        return "hi from note backend service";
    }
    
    //get all notes - GET
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ResponseNoteDto>> getNotes(@PathVariable long userId){
        List<ResponseNoteDto> returnList = dataRepository.findByUserId(userId)
                .stream()
                .map(ResponseNoteDto::from)
                .toList();
            return ResponseEntity.status(200).body(returnList);
    }

    //get a note - GET
    @GetMapping("/user/{userId}/note/{noteId}")
    public ResponseEntity<ResponseNoteDto> getNote(@PathVariable long userId,@PathVariable long noteId){

        return dataRepository.findByNoteIdAndUserId(noteId, userId)
                .map(note -> ResponseEntity.ok(
                        ResponseNoteDto.from(note)
                ))
                .orElse(ResponseEntity.notFound().build());
    }

    //add a note - POST
    @PostMapping("/user/{userId}")
    public ResponseEntity<ResponseNoteDto> addNote(@PathVariable long userId,@RequestBody RequestNoteDto requestNoteDto){
        try{
            Note resultNote = dataRepository.save(new Note(userId,requestNoteDto.getTextContent(),requestNoteDto.isPinned(),requestNoteDto.isArchived()));
            ResponseNoteDto responseNoteDto = ResponseNoteDto.from(resultNote);
            return ResponseEntity.status(201).body(responseNoteDto);
        }catch (Exception e){
            System.out.println(e);
            return ResponseEntity.status(500).build();
        }
    }

    //update a note - PUT
    @PutMapping("/user/{userId}/note/{noteId}")
    public ResponseEntity<ResponseNoteDto> updateNote(@PathVariable long userId,@PathVariable long noteId,@RequestBody RequestNoteDto requestNoteDto){
        return dataRepository.findByNoteIdAndUserId(noteId, userId).map(note -> {
            note.setTextContent(requestNoteDto.getTextContent());
            note.setPinned(requestNoteDto.isPinned());
            note.setArchived(requestNoteDto.isArchived());

            Note updatedNote = dataRepository.save(note);
                    return ResponseEntity.ok(ResponseNoteDto.from(updatedNote));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    //delete a note - DELETE
    @Transactional
    @DeleteMapping("/user/{userId}/note/{noteId}")
    public ResponseEntity<Void> deleteNote(
            @PathVariable Long userId,
            @PathVariable Long noteId) {

        int deleted = dataRepository.deleteByNoteIdAndUserId(noteId, userId);

        return deleted > 0
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }




}
