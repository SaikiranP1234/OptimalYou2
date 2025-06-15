package com.example.OptimalYou.dao;

import com.example.OptimalYou.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Integer> {
    List<Task> findByUsername(String username);

    @Query("FROM Task t WHERE t.username = :username AND t.deadline >= :date AND t.completed = false")
    List<Task> findByUsernameAndInProgress(String username, Date date);

    @Query("FROM Task t WHERE t.username = :username AND t.deadline < :date AND t.completed = false")
    List<Task> findByUsernameAndMissed(String username, Date date);

    @Query("FROM Task t WHERE t.username = :username AND t.completed = true")
    List<Task> findByUsernameAndCompleted(String username);

    @Query("FROM Task t WHERE t.username = :username AND (t.taskTitle LIKE %:searchTerm% OR t.taskDescription LIKE %:searchTerm%)")
    List<Task> searchByTitleOrDescription(String searchTerm, String username);
}
