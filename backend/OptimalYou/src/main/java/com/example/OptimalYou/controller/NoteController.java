package com.example.OptimalYou.controller;

import com.example.OptimalYou.model.Note;
import com.example.OptimalYou.model.Wrappers.NoteWrapper;
import com.example.OptimalYou.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("notes")
@CrossOrigin
public class NoteController {

    @Autowired
    private NoteService service;

    //returns HttpStatus.CREATED
    @PostMapping("create")
    public ResponseEntity<String> create(@RequestBody NoteWrapper note){
        return service.create(note);
    }

    //for this function, the id should also be sent
    @PostMapping("edit")
    public ResponseEntity<String> edit(@RequestBody NoteWrapper note){
        return service.edit(note);
    }

    @GetMapping("get/{username}")
    public ResponseEntity<List<NoteWrapper>> get(@PathVariable String username){
        return service.getAllNotes(username);
    }

    @GetMapping("get/{username}/{id}")
    public ResponseEntity<NoteWrapper> get(@PathVariable String username, @PathVariable int id){
        return service.getOneNote(username, id);
    }

    @GetMapping("search/{username}/{key}")
    public ResponseEntity<List<NoteWrapper>> search(@PathVariable String username, @PathVariable String key){
        return service.searchNotes(username, key);
    }


    @DeleteMapping("{username}/{id}")
    public ResponseEntity<String> delete(@PathVariable String username, @PathVariable int id){
        return service.deleteNote(username, id);
    }
}
