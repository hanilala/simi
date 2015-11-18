package com.aswifter.material.movie;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/11/08.
 */
public class Us_Box  implements Serializable{

    String date;
    String title;

    List<Subjects> subjects;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Subjects> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subjects> subject) {
        this.subjects = subject;
    }



    public class Subjects implements Serializable
    {
        int rank;
        int box;
        SimpleSubjectPro subject;



        public int getBox() {
            return box;
        }

        public void setBox(int box) {
            this.box = box;
        }

        public SimpleSubjectPro getSubject() {
            return subject;
        }

        public void setSubject(SimpleSubjectPro subject) {
            this.subject = subject;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }
    }
}
