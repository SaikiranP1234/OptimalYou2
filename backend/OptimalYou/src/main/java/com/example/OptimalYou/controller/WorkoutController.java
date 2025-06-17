package com.example.OptimalYou.controller;

import com.example.OptimalYou.model.ai.UserDietPreferences;
import com.example.OptimalYou.model.ai.UserWorkoutPreferences;
import com.example.OptimalYou.service.WorkoutService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("workout")
public class WorkoutController {

    private final ChatClient chatClient;

    public WorkoutController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @Autowired
    private WorkoutService service;

    @PostMapping("generateWorkout")
    public Flux<String> generateWorkout(@RequestBody UserWorkoutPreferences uwp){
        String prompt = service.generateWorkout(uwp);
        return chatClient.prompt()
                .user(prompt)
                .stream()
                .content();
    }

    @PostMapping("generateDiet")
    public Flux<String> generateDiet(@RequestBody UserDietPreferences udp){
        String prompt = service.generateDiet(udp);
        return chatClient.prompt()
                .user(prompt)
                .stream()
                .content();
    }
}
