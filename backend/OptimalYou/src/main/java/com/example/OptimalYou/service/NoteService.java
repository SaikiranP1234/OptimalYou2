package com.example.OptimalYou.service;

import com.example.OptimalYou.dao.NoteBucketRepo;
import com.example.OptimalYou.dao.NoteRepo;
import com.example.OptimalYou.model.Note;
import com.example.OptimalYou.model.NoteBucket;
import com.example.OptimalYou.model.User;
import com.example.OptimalYou.model.Wrappers.NoteWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepo repo;

    @Autowired
    private NoteBucketRepo bucketRepo;

    public ResponseEntity<String> create(NoteWrapper notewrapper) {
        try {
            Note note = wrapper(notewrapper);
            repo.save(note);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> edit(NoteWrapper note) {
        try {
            Note original = repo.findById(note.getId()).orElse(null);
            if(original == null)
                return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
            note.setIssuedDate(original.getIssuedDate());
            note.setId(original.getId());
            repo.save(wrapper(note));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<NoteWrapper>> getAllNotes(String username) {
        try {
            List<Note> notes = repo.findByUser_Username(username);
            List<NoteWrapper> wrappers = new ArrayList<>();
            for(Note n : notes)
                wrappers.add(wrapper(n));
            return new ResponseEntity<>(wrappers,HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<NoteWrapper>> searchNotes(String username, String key) {
        try {
            List<Note> notes = repo.searchNoteByTitleAndNote(username, key);
            List<NoteWrapper> wrappers = new ArrayList<>();
            for(Note n : notes)
                wrappers.add(wrapper(n));
            return new ResponseEntity<>(wrappers,HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteNote(String username, int id) {
        try {
            Note note = repo.findById(id).orElse(null);
            if(note == null)
                return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
            else if(!note.getUser().getUsername().equals(username))
                return new ResponseEntity<>("not authorized", HttpStatus.UNAUTHORIZED);
            List<NoteBucket> buckets = bucketRepo.findByUser_Username(username);
            for(NoteBucket bucket : buckets){
                boolean changed = false;
                while (bucket.getNotes().contains(note)) {
                    bucket.getNotes().remove(note);
                    changed = true;
                }
                if(changed)
                    bucketRepo.save(bucket);
            }
            repo.deleteById(id);
            return new ResponseEntity<>("deleted",HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Note wrapper(NoteWrapper notewrapper){
        Note note = new Note();
        note.setId(notewrapper.getId());
        note.setTitle(notewrapper.getTitle());
        note.setNote(notewrapper.getNote());
        note.setIssuedDate(notewrapper.getIssuedDate());
        note.setUser(new User());
        note.getUser().setUsername(notewrapper.getUsername());
        return note;
    }

    public NoteWrapper wrapper(Note note){
        NoteWrapper noteWrapper = new NoteWrapper();
        noteWrapper.setId(note.getId());
        noteWrapper.setTitle(note.getTitle());
        noteWrapper.setNote(note.getNote());
        noteWrapper.setIssuedDate(note.getIssuedDate());
        noteWrapper.setUsername(note.getUser().getUsername());
        return noteWrapper;
    }
}
