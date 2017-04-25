package com.labkod.karikatup;

/**
 * Created by eray on 4/23/17.
 */

public class Comic {

    public String username;
    public String date;
    public String image;
    public Integer likeCount;
    public Integer commentCount;
    public String author;

    public Comic(String username, String image, String date, String author, Integer likeCount, Integer commentCount) {
        this.username = username;
        this.image = image;
        this.date = date;
        this.author = author;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }

}
