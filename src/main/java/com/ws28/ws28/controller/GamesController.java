package com.ws28.ws28.controller;
    
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ws28.ws28.model.Game;
import com.ws28.ws28.service.ReviewService;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;


@RestController
@RequestMapping(path = "/game")
public class GamesController {
    @Autowired
    private ReviewService reviewSvc;

    @GetMapping("{gameId}/reviews")
    public ResponseEntity<String> getReviewHistory(@PathVariable String gameId) {
        JsonObject result = null;
        Optional<Game> r = reviewSvc.aggregateGame(gameId);
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("review", r.get().toJSON());
        result = builder.build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }
}
