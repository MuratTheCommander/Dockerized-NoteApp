package com.example.demo.controller;

import com.example.demo.dto.RequestNoteDto;
import com.example.demo.dto.ResponseNoteDto;
import com.example.demo.entity.Note;
import com.example.demo.repository.DataRepository;
import com.example.demo.security.SecurityService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NoteController {

    @Autowired
    DataRepository dataRepository;

    @Autowired
    SecurityService securityService;

    private static final String USER_PATH = "/user/{userId}";
    private static final String USER_NOTE_PATH = "/user/{userId}/note/{noteId}";

    //hi test
    @GetMapping("/hi")
    public String hiTest(){
        return "hi from note backend service";
    }

    //get all notes - GET
    // @GetMapping("/user/{userId}")
    @GetMapping(USER_PATH)
    public ResponseEntity<List<ResponseNoteDto>> getNotes(@PathVariable long userId){
        List<ResponseNoteDto> returnList = dataRepository.findByUserId(userId)
                .stream()
                .map(ResponseNoteDto::from)
                .toList();
            return ResponseEntity.ok(returnList);
    }

    //get a note - GET
//    @GetMapping("/user/{userId}/note/{noteId}")
    @GetMapping(USER_NOTE_PATH)
    public ResponseEntity<ResponseNoteDto> getNote(@PathVariable long userId,@PathVariable long noteId){

        return dataRepository.findByNoteIdAndUserId(noteId, userId)
                .map(note -> ResponseEntity.ok(
                        ResponseNoteDto.from(note)
                ))
                .orElse(ResponseEntity.notFound().build());
    }

    //add a note - POST
//    @PostMapping("/user/{userId}")
    @PostMapping(USER_PATH)
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
//    @PutMapping("/user/{userId}/note/{noteId}")
    @PutMapping(USER_NOTE_PATH)
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
//    @DeleteMapping("/user/{userId}/note/{noteId}")
    @DeleteMapping(USER_NOTE_PATH)
    public ResponseEntity<Void> deleteNote(
            @PathVariable long userId,
            @PathVariable long noteId) {

        int deleted = dataRepository.deleteByNoteIdAndUserId(noteId, userId);

        return deleted > 0
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/isJwtValid")
    public ResponseEntity<Integer> isJwtValid(@RequestBody String clientJwt){
        if(securityService.validateJwt(clientJwt)){
            return ResponseEntity.ok(200);
        }return ResponseEntity.ok(401);
    }


}
