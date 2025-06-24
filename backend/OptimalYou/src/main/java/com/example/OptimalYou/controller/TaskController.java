package com.example.OptimalYou.controller;

import com.example.OptimalYou.model.Task;
import com.example.OptimalYou.model.Wrappers.TaskWrapper;
import com.example.OptimalYou.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("mundane")
@CrossOrigin
public class TaskController {

    @Autowired
    private TaskService service;

    @PostMapping("create")
    public ResponseEntity<String> create(@RequestBody TaskWrapper task){
        return service.createTask(task);
    }

    //for this controller function, you must send the task id also.
    //Prahith, pls review the logic behind this.
    @PostMapping("edit")
    public ResponseEntity<String> edit(@RequestBody TaskWrapper task){
        return service.editTask(task);
    }

    @PostMapping("completed/{id}")
    public ResponseEntity<String> completed(@PathVariable int id){
        return service.completedTask(id);
    }

    @GetMapping("all/{username}")
    public ResponseEntity<List<TaskWrapper>> allTasks(@PathVariable String username){
        return service.getAllTasks(username);
    }

    @GetMapping("progressing/{username}")
    public ResponseEntity<List<TaskWrapper>> progressingTasks(@PathVariable String username){
        return service.getAllTasksInProgress(username);
    }

    @GetMapping("missed/{username}")
    public ResponseEntity<List<TaskWrapper>> missedTasks(@PathVariable String username){
        return service.getAllTasksMissed(username);
    }

    @GetMapping("completed/{username}")
    public ResponseEntity<List<TaskWrapper>> completedTasks(@PathVariable String username){
        return service.getAllTasksCompleted(username);
    }

    @GetMapping("search/{username}/{key}")
    public ResponseEntity<List<TaskWrapper>> searchTasks(@PathVariable String username, @PathVariable String key){
        return service.searchTasks(username, key);
    }

    //there is a way for someone to exploit this endpoint, Prahith if you are reading this comment, pls look into it
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id){
        return service.deleteTask(id);
    }
}
