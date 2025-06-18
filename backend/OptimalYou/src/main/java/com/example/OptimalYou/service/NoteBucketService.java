package com.example.OptimalYou.service;

import com.example.OptimalYou.dao.NoteBucketRepo;
import com.example.OptimalYou.dao.NoteRepo;
import com.example.OptimalYou.model.Note;
import com.example.OptimalYou.model.NoteBucket;
import com.example.OptimalYou.model.User;
import com.example.OptimalYou.model.Wrappers.NoteBucketResponse;
import com.example.OptimalYou.model.Wrappers.NoteBucketWrapper;
import com.example.OptimalYou.model.Wrappers.NoteWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteBucketService {

    @Autowired
    private NoteBucketRepo bucketRepo;

    @Autowired
    private NoteRepo noteRepo;

    @Autowired
    private NoteService service;

    public ResponseEntity<String> create(NoteBucketWrapper tempoBucket) {
        try {
            bucketRepo.save(wrapper(tempoBucket));
            return new ResponseEntity<>("created",HttpStatus.CREATED);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> add(int noteId, int bucketId) {
        try {
            Note note = noteRepo.findById(noteId).orElse(null);
            NoteBucket bucket = bucketRepo.findById(bucketId).orElse(null);

            if(note == null || bucket == null || bucket.getNotes().contains(note))
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            bucket.getNotes().add(note);
            bucketRepo.save(bucket);
            return new ResponseEntity<>("added", HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> remove(int noteId, int bucketId) {
        try {
            Note note = noteRepo.findById(noteId).orElse(null);
            NoteBucket bucket = bucketRepo.findById(bucketId).orElse(null);
            if(note == null || bucket == null || !bucket.getNotes().contains(note))
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            bucket.getNotes().remove(note);
            bucketRepo.save(bucket);
            return new ResponseEntity<>("added", HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> edit(int bucketId, String title) {
        try {
            NoteBucket bucket = bucketRepo.findById(bucketId).orElse(null);
            if(bucket == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            bucket.setTitle(title);
            bucketRepo.save(bucket);
            return new ResponseEntity<>("changed", HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<NoteBucketResponse>> get(String username) {
        try {
            List<NoteBucket> buckets = bucketRepo.findByUser_Username(username);
            List<NoteBucketResponse> responses = new ArrayList<>();
            for(NoteBucket b : buckets)
                responses.add(wrapper(b));
            return new ResponseEntity<>(responses,HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<NoteWrapper>> getNot(int bucketId) {
        try {
            NoteBucket bucket = bucketRepo.findById(bucketId).orElse(null);
            if(bucket == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            String user = bucket.getUser().getUsername();
            ResponseEntity<List<NoteWrapper>> tempNotes = service.getAllNotes(user);
            List<Note> bucketNotes = bucket.getNotes();
            List<NoteWrapper> responses = new ArrayList<>();
            if(!tempNotes.getStatusCode().is2xxSuccessful())
                return new ResponseEntity<>(tempNotes.getStatusCode());
            List<NoteWrapper> notes = tempNotes.getBody();
            for(int i = 0; i < notes.size(); i++){
                boolean present = false;
                for(int j = 0; j < bucketNotes.size(); j++){
                    if(notes.get(i).getId() == bucketNotes.get(j).getId()){
                        present = true;
                        break;
                    }
                }
                if(!present)
                    responses.add(notes.get(i));
//                if(j >= bucketNotes.size() || notes.get(i).getId() != bucketNotes.get(j).getId())
//                    responses.add(notes.get(i));
//                else
//                    j++;
            }
            return new ResponseEntity<>(responses, HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<NoteWrapper>> getIn(int bucketId) {
        try {
            NoteBucket bucket = bucketRepo.findById(bucketId).orElse(null);
            if(bucket == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            List<NoteWrapper> wrappers = new ArrayList<>();
            for(Note n : bucket.getNotes())
                wrappers.add(service.wrapper(n));
            return new ResponseEntity<>(wrappers, HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<NoteBucketResponse>> search(String username, String key) {
        try {
            List<NoteBucket> buckets = bucketRepo.searchNoteBucketByTitle(username, key);
            List<NoteBucketResponse> responses = new ArrayList<>();
            for(NoteBucket b : buckets)
                responses.add(wrapper(b));
            return new ResponseEntity<>(responses, HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> delete(int bucketId, String username) {
        try {
            NoteBucket bucket = bucketRepo.findById(bucketId).orElse(null);
            if(bucket == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            if(!bucket.getUser().getUsername().equals(username))
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            bucket.setNotes(null);
            bucketRepo.save(bucket);
            bucketRepo.delete(bucket);
            return new ResponseEntity<>("deleted", HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public NoteBucket wrapper(NoteBucketWrapper wrapper){
        NoteBucket bucket = new NoteBucket();
        bucket.setId(wrapper.getId());
        bucket.setUser(new User());
        bucket.setNotes(new ArrayList<Note>());
        bucket.setTitle(wrapper.getTitle());
        bucket.getUser().setUsername(wrapper.getUsername());
        if( wrapper.getNote_ids() != null)
            for(int id : wrapper.getNote_ids()){
                Note note = noteRepo.findById(id).orElse(null);
                if(note == null)
                    continue;
                bucket.getNotes().add(note);
            }
        return bucket;
    }

    public NoteBucketResponse wrapper(NoteBucket wrapper) {
        NoteBucketResponse bucket = new NoteBucketResponse();
        bucket.setId(wrapper.getId());
        bucket.setNotes(new ArrayList<>());
        for(Note n : wrapper.getNotes())
            bucket.getNotes().add(service.wrapper(n));
        bucket.setTitle(wrapper.getTitle());
        bucket.setUsername(wrapper.getUser().getUsername());
        return bucket;
    }

}
