package com.example.OptimalYou.dao;

import com.example.OptimalYou.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoteRepo extends JpaRepository<Note, Integer> {

    List<Note> findByUser_Username(String username);

    @Query("FROM Note n WHERE n.user.username = :username AND (n.title LIKE %:searchTerm% OR n.note LIKE %:searchTerm%)")
    List<Note> searchNoteByTitleAndNote(String username, String searchTerm);
}
