package com.example.OptimalYou.service;

import com.example.OptimalYou.dao.TaskRepo;
import com.example.OptimalYou.model.Note;
import com.example.OptimalYou.model.Task;
import com.example.OptimalYou.model.User;
import com.example.OptimalYou.model.Wrappers.TaskWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskService {

    @Autowired
    private TaskRepo repo;

    private final Comparator<Task> orderOfPriority = new Comparator<Task>() {
        @Override
        public int compare(Task o1, Task o2) {
            return o2.getPriority() - o1.getPriority();
        }
    };

    public ResponseEntity<String> createTask(TaskWrapper task) {
        try{
            repo.save(wrapper(task));
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> editTask(TaskWrapper task) {
        try{
            Task original = repo.findById(task.getId()).orElse(null);
            if(original == null)
                return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
            task.setIssuedDate(original.getIssuedDate());
            repo.save(wrapper(task));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception e){
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
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<TaskWrapper>> getAllTasks(String username) {
        try{
            List<Task> tasks = repo.findByUser_Username(username);
            Collections.sort(tasks, orderOfPriority);
            List<TaskWrapper> wrappers = new ArrayList<>();
            for(Task t : tasks)
                wrappers.add(wrapper(t));
            return new ResponseEntity<>(wrappers,HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<TaskWrapper>> getAllTasksInProgress(String username) {
        try{
            List<Task> tasks = repo.findByUsernameAndInProgress(username, new Date());
            Collections.sort(tasks, orderOfPriority);
            List<TaskWrapper> wrappers = new ArrayList<>();
            for(Task t : tasks)
                wrappers.add(wrapper(t));
            return new ResponseEntity<>(wrappers,HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<TaskWrapper>> getAllTasksMissed(String username) {
        try{
            List<Task> tasks = repo.findByUsernameAndMissed(username, new Date());
            Collections.sort(tasks, orderOfPriority);
            List<TaskWrapper> wrappers = new ArrayList<>();
            for(Task t : tasks)
                wrappers.add(wrapper(t));
             return new ResponseEntity<>(wrappers,HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<TaskWrapper>> getAllTasksCompleted(String username) {
        try{
            List<Task> tasks = repo.findByUsernameAndCompleted(username);
            Collections.sort(tasks, orderOfPriority);
            List<TaskWrapper> wrappers = new ArrayList<>();
            for(Task t : tasks)
                wrappers.add(wrapper(t));
            return new ResponseEntity<>(wrappers,HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<TaskWrapper>> searchTasks(String username, String key) {
        try{
            List<Task> tasks = repo.searchByTitleOrDescription(key, username);
            Collections.sort(tasks, orderOfPriority);
            List<TaskWrapper> wrappers = new ArrayList<>();
            for(Task t : tasks)
                wrappers.add(wrapper(t));
            return new ResponseEntity<>(wrappers,HttpStatus.OK);
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

    public Task wrapper(TaskWrapper taskWrapper){
        Task task = new Task();
        task.setId(taskWrapper.getId());
        task.setTaskTitle(taskWrapper.getTaskTitle());
        task.setTaskDescription(taskWrapper.getTaskDescription());
        task.setPriority(taskWrapper.getPriority());
        task.setIssuedDate(taskWrapper.getIssuedDate());
        task.setDeadline(taskWrapper.getDeadline());
        task.setCompleted(taskWrapper.isCompleted());
        task.setUser(new User());
        task.getUser().setUsername(taskWrapper.getUsername());
        return task;
    }

    public TaskWrapper wrapper(Task task){
        TaskWrapper taskWrapper = new TaskWrapper();
        taskWrapper.setId(task.getId());
        taskWrapper.setTaskTitle(task.getTaskTitle());
        taskWrapper.setTaskDescription(task.getTaskDescription());
        taskWrapper.setPriority(task.getPriority());
        taskWrapper.setIssuedDate(task.getIssuedDate());
        taskWrapper.setDeadline(task.getDeadline());
        taskWrapper.setCompleted(task.isCompleted());
        taskWrapper.setUsername(task.getUser().getUsername());
        return taskWrapper;
    }
}
