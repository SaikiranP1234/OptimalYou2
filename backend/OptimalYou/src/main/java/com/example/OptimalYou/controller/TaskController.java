package com.example.OptimalYou.controller;

import com.example.OptimalYou.model.Task;
import com.example.OptimalYou.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
public class TaskController {

    @Autowired
    TaskService service;

    @PostMapping("mundane/create")
    public ResponseEntity<String> create(@RequestBody Task task){
        return service.createTask(task);
    }

    //for this controller function, you must send the task id also.
    @PostMapping("mundane/edit")
    public ResponseEntity<String> edit(@RequestBody Task task){
        return service.createTask(task);
    }

    @PostMapping("mundane/completed/{id}")
    public ResponseEntity<String> completed(@PathVariable int id){
        return service.completedTask(id);
    }

    @GetMapping("mundane/all/{username}")
    public ResponseEntity<List<Task>> allTasks(@PathVariable String username){
        return service.getAllTasks(username);
    }

    @GetMapping("mundane/progressing/{username}")
    public ResponseEntity<List<Task>> progressingTasks(@PathVariable String username){
        return service.getAllTasksInProgress(username);
    }

    @GetMapping("mundane/missed/{username}")
    public ResponseEntity<List<Task>> missedTasks(@PathVariable String username){
        return service.getAllTasksMissed(username);
    }

    @GetMapping("mundane/completed/{username}")
    public ResponseEntity<List<Task>> completedTasks(@PathVariable String username){
        return service.getAllTasksCompleted(username);
    }

    @GetMapping("mundane/search/{username}/{key}")
    public ResponseEntity<List<Task>> searchTasks(@PathVariable String username, @PathVariable String key){
        return service.searchTasks(username, key);
    }

    @DeleteMapping("mundane/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id){
        return service.deleteTask(id);
    }
}
