package com.ws28.ws28.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import org.bson.Document;
import org.bson.types.ObjectId;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

public class Game {
    private Integer gid;
    private String name;
    private Integer year;
    private Integer ranking;
    private Integer users_rated;
    private String url;
    private String image;
    private String timestamp;
    private String[] reviews;

    public static Game create(Document d) {
        Game g = new Game();
        List reviewsIdArr = (ArrayList) d.get("reviews");
        List newReviewsId = new LinkedList<>();
        for (Object a : reviewsIdArr) {
            ObjectId oa = (ObjectId) a;
            newReviewsId.add("/review/" + oa.toString());
        }

        g.setGid(d.getInteger("gid"));
        g.setName(d.getString("name"));
        g.setYear(d.getInteger("year"));
        g.setRanking(d.getInteger("ranking"));
        g.setUsers_rated(d.getInteger("users_rated"));
        g.setUrl(d.getString("url"));
        g.setImage(d.getString("image"));
        g.setTimestamp(d.getDate("timestamp").toString());
        g.setReviews((String[]) newReviewsId.toArray(new String[newReviewsId.size()]));
        return g;
    }

    public JsonObject toJSON() {
        JsonArray reviewsJ = null;
        JsonArrayBuilder bld = Json.createArrayBuilder();
        for (String x : getReviews())
            bld.add(x);
        reviewsJ = bld.build();
        return Json.createObjectBuilder()
                .add("gid", getGid())
                .add("name", getName())
                .add("year", getYear())
                .add("ranking", getRanking())
                .add("users_rated", getUsers_rated() != null ? getUsers_rated() : 0)
                .add("url", getUrl())
                .add("image", getImage())
                .add("timestamp", getTimestamp())
                .add("reviews", reviewsJ.toString())
                .build();
    }

    public Integer getGid() {
        return gid;
    }
    public void setGid(Integer gid) {
        this.gid = gid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getYear() {
        return year;
    }
    public void setYear(Integer year) {
        this.year = year;
    }
    public Integer getRanking() {
        return ranking;
    }
    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }
    public Integer getUsers_rated() {
        return users_rated;
    }
    public void setUsers_rated(Integer users_rated) {
        this.users_rated = users_rated;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public String[] getReviews() {
        return reviews;
    }
    public void setReviews(String[] reviews) {
        this.reviews = reviews;
    }
    @Override
    public String toString() {
        return "Game [gid=" + gid + ", name=" + name + ", year=" + year + ", ranking=" + ranking + ", users_rated="
                + users_rated + ", url=" + url + ", image=" + image + ", timestamp=" + timestamp + ", reviews="
                + Arrays.toString(reviews) + "]";
    }

    

    
}
