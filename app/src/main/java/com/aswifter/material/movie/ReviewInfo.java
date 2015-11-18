package com.aswifter.material.movie;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/11/14.
 */
public class ReviewInfo implements Serializable {

    String reviewSrc;
    String reviewName;
    String reviewTitle;
    Float reviewRating;
    String reviewInfo;


    public String getReviewSrc() {
        return reviewSrc;
    }

    public void setReviewSrc(String reviewSrc) {
        this.reviewSrc = reviewSrc;
    }

    public String getReviewName() {
        return reviewName;
    }

    public void setReviewName(String reviewName) {
        this.reviewName = reviewName;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public String getReviewInfo() {
        return reviewInfo;
    }

    public void setReviewInfo(String reviewInfo) {
        this.reviewInfo = reviewInfo;
    }

    public Float getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(Float reviewRating) {
        this.reviewRating = reviewRating;
    }
}
