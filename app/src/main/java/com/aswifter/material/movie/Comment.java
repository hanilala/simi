package com.aswifter.material.movie;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by Administrator on 2015/11/13.
 */
public class Comment implements Serializable{


    //此类用于
    //再次添加
    private int num;
    private LinkedList<CommentInfo> commentInfoList;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public LinkedList<CommentInfo> getCommentInfoList() {
        return commentInfoList;
    }

    public void setCommentInfoList(LinkedList<CommentInfo> commentInfoList) {
        this.commentInfoList = commentInfoList;
    }
}
