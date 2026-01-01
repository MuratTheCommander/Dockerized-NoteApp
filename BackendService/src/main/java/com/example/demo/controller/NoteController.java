package com.example.demo.controller;

import com.example.demo.dto.RequestNoteDto;
import com.example.demo.dto.ResponseNoteDto;
import com.example.demo.entity.Note;
import com.example.demo.repository.DataRepository;
import jakarta.persistence.EntityNotFoundException;
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
    @GetMapping("/")
    public ResponseEntity<List<ResponseNoteDto>> getNotes(){
        List<Note> noteList;
        noteList = dataRepository.findAll();
            System.out.println("There are some notes");
           List<ResponseNoteDto> returnList = noteList.stream().map(note -> new ResponseNoteDto(note.getNoteId(),note.getUserId(),note.getTextContent(),
                    note.isPinned(),note.isArchived())).toList();
           return ResponseEntity.status(200).body(returnList);
    }

    //get a note - GET
    @GetMapping("/{noteId}")
    public ResponseEntity<ResponseNoteDto> getNote(@PathVariable long noteId){
        if(dataRepository.existsById(noteId)){
            Note note = dataRepository.findById(noteId).orElse(null);
            return ResponseEntity.status(200).body(new ResponseNoteDto(note.getNoteId(),note.getUserId(),note.getTextContent(),
                    note.isPinned(),note.isArchived()));
        }
        return ResponseEntity.status(404).build();
    }

    //add a note - POST
    @PostMapping("/")
    public ResponseEntity<ResponseNoteDto> addNote(@RequestBody RequestNoteDto requestNoteDto){
        try{
            Note resultNote = dataRepository.save(new Note(requestNoteDto.getUserId(),requestNoteDto.getTextContent(),requestNoteDto.isPinned(),requestNoteDto.isArchived()));
            ResponseNoteDto responseNoteDto = new ResponseNoteDto(resultNote.getNoteId(),resultNote.getUserId(),
                    resultNote.getTextContent(),resultNote.isPinned(),resultNote.isArchived());
            return ResponseEntity.status(201).body(responseNoteDto);
        }catch (Exception e){
            System.out.println(e);
            return ResponseEntity.status(500).build();
        }
    }

    //update a note - PUT
    @PutMapping("/{noteId}")
    public ResponseEntity<ResponseNoteDto> updateNote(@PathVariable long noteId,@RequestBody RequestNoteDto requestNoteDto){
        if(dataRepository.existsById(noteId)){
            Note note = dataRepository.findById(noteId).orElseThrow(() ->new EntityNotFoundException("Note not found"));
            note.setNoteId(noteId);
            note.setUserId(requestNoteDto.getUserId());
            note.setTextContent(requestNoteDto.getTextContent());
            note.setPinned(requestNoteDto.isPinned());
            note.setArchived(requestNoteDto.isArchived());
            dataRepository.save(note);
            return ResponseEntity.status(200).body(
              new ResponseNoteDto(note.getNoteId(),note.getUserId(),note.getTextContent(),note.isPinned(),note.isArchived())
            );
        }else{
            return ResponseEntity.status(404).build();
        }
    }

    //delete a note - DELETE
    @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable long noteId){

        if(dataRepository.existsById(noteId)) {
            dataRepository.deleteById(noteId);
            return ResponseEntity.noContent().build();
        }else{
            System.out.println("Requested note is not found no deletion made");
            return ResponseEntity.notFound().build();
        }

    }
}
