package com.ws28.ws28.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.ResourceTransformerSupport;

import com.ws28.ws28.model.EditedComment;
import com.ws28.ws28.model.Game;
import com.ws28.ws28.model.Review;
import com.ws28.ws28.repo.ReviewRepo;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepo reviewRepo;
    
    public Review getReview(String _id) {
        Review r = reviewRepo.getReview(_id);
        if (r.getEdited() != null) {
            List<EditedComment> ll = (List<EditedComment>) r.getEdited();
            System.out.println(ll.size());
            if (ll.size() > 0)
                r.setIsEdited(Boolean.valueOf(true));
            else
                r.setIsEdited(Boolean.valueOf(false));
        }

        r.setTimestamp(LocalDateTime.now());
        return r;
    }

    public Optional<Game> aggregateGame(String gid) {
        return reviewRepo.aggregrateGameReviews(gid);
    }
}
