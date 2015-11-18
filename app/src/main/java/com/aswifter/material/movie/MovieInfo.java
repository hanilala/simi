package com.aswifter.material.movie;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/11/12.
 */
public class MovieInfo implements Serializable {

    String director;
    String screenWriter;
    String topStars;
    String movieType="";
    String language;
    String startTime="";
    String movieDuration;

    String actors;
    String summary;
    String CountryMaking;

    public String getCountryMaking() {
        return CountryMaking;
    }

    public void setCountryMaking(String countryMaking) {
        CountryMaking = countryMaking;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getScreenWriter() {
        return screenWriter;
    }

    public void setScreenWriter(String screenWriter) {
        this.screenWriter = screenWriter;
    }

    public String getMovieType() {
        return movieType;
    }

    public void setMovieType(String movieType) {
        this.movieType = movieType;
    }

    public String getTopStars() {
        return topStars;
    }

    public void setTopStars(String topStars) {
        this.topStars = topStars;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getMovieDuration() {
        return movieDuration;
    }

    public void setMovieDuration(String movieDuration) {
        this.movieDuration = movieDuration;
    }
}
