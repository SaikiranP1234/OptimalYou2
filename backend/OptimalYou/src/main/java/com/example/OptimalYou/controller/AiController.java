package com.example.OptimalYou.controller;

import com.example.OptimalYou.model.ai.UserDietPreferences;
import com.example.OptimalYou.model.ai.UserWorkoutPreferences;
import com.example.OptimalYou.service.AiService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("ai")
@CrossOrigin
public class AiController {

    private final ChatClient chatClient;

    public AiController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @Autowired
    private AiService service;

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
