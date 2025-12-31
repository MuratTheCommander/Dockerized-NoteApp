package com.example.demo.controller;

import com.example.demo.dto.RequestNoteDto;
import com.example.demo.dto.ResponseNoteDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/note")
public class NoteController {

    //hi test
    @GetMapping("hi")
    public String hiTest(){
        return "hi from note backend service";
    }

    //get all notes - GET
    @GetMapping("/")
    public ResponseEntity<List<ResponseNoteDto>> getNotes(){
        return null;
    }

    //get a note - GET
    @GetMapping("/{noteId}")
    public ResponseEntity<ResponseNoteDto> getNote(@PathVariable long noteId){
        return null;
    }

    //add a note - POST
    @PostMapping("/")
    public ResponseEntity<ResponseNoteDto> addNote(@RequestBody RequestNoteDto requestNoteDto){
        return null;
    }

    //update a note - PUT
    @PutMapping("/{noteId}")
    public ResponseEntity<ResponseNoteDto> updateNote(@PathVariable long noteId,@RequestBody RequestNoteDto requestNoteDto){
        return null;
    }

    //delete a note - DELETE
    @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable long noteId){
        return null;
    }
}
