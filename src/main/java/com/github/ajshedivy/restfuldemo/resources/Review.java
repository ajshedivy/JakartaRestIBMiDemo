package com.github.ajshedivy.restfuldemo.resources;

public class Review {
    private String reviewText;
    private String bookId;
    private int rating; // 1-10

    // Constructors, getters, and setters
    public Review(String reviewText, String bookId, int rating) {
        this.reviewText = reviewText;
        this.bookId = bookId;
        this.rating = rating;
    }

    public Review() {}

    public int getRating() {
        return this.rating;
    }

    public String getReview() {
        return this.reviewText;
    }

    public String getBookID() {
        return this.bookId;
    }


    public void setReview(String review) {
        this.reviewText = review;
    }

    public void setBookID(String id) {
        this.bookId = id;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
