package com.example.OptimalYou.controller;

import com.example.OptimalYou.dao.NoteBucketRepo;
import com.example.OptimalYou.model.Wrappers.NoteBucketResponse;
import com.example.OptimalYou.model.Wrappers.NoteBucketWrapper;
import com.example.OptimalYou.model.Wrappers.NoteWrapper;
import com.example.OptimalYou.service.NoteBucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("notebucket")
@CrossOrigin
public class NoteBucketController {

    @Autowired
    private NoteBucketService service;

    //returns HttpStatus.CREATED
    @PostMapping("create")
    public ResponseEntity<String> create(@RequestBody NoteBucketWrapper bucket){
        return service.create(bucket);
    }

    @PostMapping("add/{noteId}/{bucketId}")
    public ResponseEntity<String> add(@PathVariable int noteId, @PathVariable int bucketId){
        return service.add(noteId, bucketId);
    }

    @DeleteMapping("remove/{noteId}/{bucketId}")
    public ResponseEntity<String> remove(@PathVariable int noteId, @PathVariable int bucketId){
        return service.remove(noteId, bucketId);
    }

    @PostMapping("edit/{bucketId}/{title}")
    public ResponseEntity<String> edit(@PathVariable int bucketId, @PathVariable String title){
        return service.edit(bucketId, title);
    }

    @GetMapping("get/{username}")
    public ResponseEntity<List<NoteBucketResponse>> get(@PathVariable String username){
        return service.get(username);
    }

    @GetMapping("not-in-bucket/{bucketId}")
    public ResponseEntity<List<NoteWrapper>> getNot(@PathVariable int bucketId){
        return service.getNot(bucketId);
    }

    @GetMapping("/in-bucket/{bucketId}")
    public ResponseEntity<List<NoteWrapper>> getIn(@PathVariable int bucketId){
        return service.getIn(bucketId);
    }

    @GetMapping("search/{username}/{key}")
    public ResponseEntity<List<NoteBucketResponse>> search(@PathVariable String username, @PathVariable String key){
        return service.search(username, key);
    }

    //all the notes inside the bucket are also deleted
    @DeleteMapping("delete/{username}/{bucketId}")
    public ResponseEntity<String> delete(@PathVariable int bucketId, @PathVariable String username){
        return service.delete(bucketId, username);
    }
}
