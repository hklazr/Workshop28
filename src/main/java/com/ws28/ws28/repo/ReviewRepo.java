package com.ws28.ws28.repo;

import java.time.LocalDateTime;
import java.util.Optional;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AddFieldsOperation;
import org.springframework.data.mongodb.core.aggregation.AddFieldsOperation.AddFieldsOperationBuilder;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.ws28.ws28.model.Game;
import com.ws28.ws28.model.Review;

@Repository
public class ReviewRepo {

    @Autowired
    MongoTemplate mongoTemplate;
    
    public Optional<Game> aggregrateGameReviews(String gameId) {
        MatchOperation matchGameId = Aggregation.match(
                Criteria.where("gid").is(Integer.parseInt(gameId)));

        LookupOperation linkReviewsGame = Aggregation.lookup("reviews",
                "gid", "gameId", "reviewsDocs");

        ProjectionOperation projection = Aggregation
                .project("_id", "gid", "name", "year", "ranking",
                        "users_rated", "url", "image")
                .and("reviewsDocs._id").as("reviews");

        AddFieldsOperationBuilder adFieldOpsBld = Aggregation.addFields();
        adFieldOpsBld.addFieldWithValue("timestamp", LocalDateTime.now());
        AddFieldsOperation newFieldOps = adFieldOpsBld.build();

        Aggregation pipeline = Aggregation
                .newAggregation(matchGameId, linkReviewsGame, projection, newFieldOps);
        AggregationResults<Document> results = mongoTemplate
                .aggregate(pipeline, "game", Document.class);
        if (!results.iterator().hasNext())
            return Optional.empty();

        Document doc = results.iterator().next();
        Game g = Game.create(doc);
        return Optional.of(g);
    }

    public Review getReview(String _id) {
        ObjectId docId = new ObjectId(_id);
        return mongoTemplate.findById(docId, Review.class, "reviews");
    }

}
