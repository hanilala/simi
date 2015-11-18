package com.aswifter.material.movie;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by Administrator on 2015/11/14.
 */
public class Review implements Serializable {
    int num;
    LinkedList<ReviewInfo> reviewInfosList=new LinkedList<>();

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public LinkedList<ReviewInfo> getReviewInfosList() {
        return reviewInfosList;
    }

    public void setReviewInfosList(LinkedList<ReviewInfo> reviewInfosList) {
        this.reviewInfosList = reviewInfosList;
    }
}
