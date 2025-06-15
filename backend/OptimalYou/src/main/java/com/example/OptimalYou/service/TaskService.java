package com.example.OptimalYou.service;

import com.example.OptimalYou.dao.TaskRepo;
import com.example.OptimalYou.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    TaskRepo repo;

    private final Comparator<Task> orderOfPriority = new Comparator<Task>() {
        @Override
        public int compare(Task o1, Task o2) {
            return o2.getPriority() - o1.getPriority();
        }
    };

    public ResponseEntity<String> createTask(Task task) {
        try{
            repo.save(task);
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> completedTask(int id) {
        try{
            Task t = repo.findById(id).orElse(null);
            if(t == null)
                return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
            t.setCompleted(true);
            repo.save(t);
            return new ResponseEntity<>("success", HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Task>> getAllTasks(String username) {
        try{
            List<Task> tasks = repo.findByUsername(username);
            Collections.sort(tasks, orderOfPriority);
            return new ResponseEntity<>(tasks,HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Task>> getAllTasksInProgress(String username) {
        try{
            List<Task> tasks = repo.findByUsernameAndInProgress(username, new Date());
            Collections.sort(tasks, orderOfPriority);
            return new ResponseEntity<>(tasks,HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Task>> getAllTasksMissed(String username) {
        try{
            List<Task> tasks = repo.findByUsernameAndMissed(username, new Date());
            Collections.sort(tasks, orderOfPriority);
            return new ResponseEntity<>(tasks,HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Task>> getAllTasksCompleted(String username) {
        try{
            List<Task> tasks = repo.findByUsernameAndCompleted(username);
            Collections.sort(tasks, orderOfPriority);
            return new ResponseEntity<>(tasks,HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Task>> searchTasks(String username, String key) {
        try{
            List<Task> tasks = repo.searchByTitleOrDescription(key, username);
            Collections.sort(tasks, orderOfPriority);
            return new ResponseEntity<>(tasks,HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteTask(int id) {
        try{
            repo.deleteById(id);
            return new ResponseEntity<>("deleted",HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
