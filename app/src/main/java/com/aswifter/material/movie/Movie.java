package com.aswifter.material.movie;

import android.content.Intent;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aswifter.material.MyUtils.MyApplication;
import com.google.gson.Gson;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;

/**
 * Created by Administrator on 2015/11/08.
 */
public class Movie {

    private static String Url = "http://api.douban.com/v2/movie/us_box";

    private static MovieInfo movieInfo=null;

    private static LinkedList<CommentInfo> commentInfoList=new LinkedList<>();

    private static LinkedList<ReviewInfo> reviewInfosList=new LinkedList<>();

    private static RequestQueue mRequestQueue=Volley.newRequestQueue(MyApplication.getContext());


    private static Us_Box allData;


    public static void InitUs_Box() {




        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Url
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.w("Movie", "jsonResponse(in Movie) is " + response);

                Gson gson = new Gson();


                allData = gson.fromJson(response.toString(), Us_Box.class);

                Intent intent = new Intent(MovieApp.ACTION_LoadComplete);
                intent.putExtra("allData", allData);

                MyApplication.getContext().sendBroadcast(intent);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w("Movie", " Error(in Movie) is " + error.getMessage());

            }
        });

        mRequestQueue.add(jsonObjectRequest);


    }

    public static void initCommentInfoList(String url)
    {
        Log.w("movie","url is: "+url);

        StringRequest stringRequest=new StringRequest(url + "comments", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                transformCommentList(response);

                if(!commentInfoList.isEmpty())
                {
                    Intent i=new Intent(MovieApp.ACTION_LoadCommentInfoComplete);
                    Comment comment=new Comment();
                    comment.setNum(commentInfoList.size());
                    comment.setCommentInfoList(commentInfoList);


                    i.putExtra("Comment", comment);
                    Log.w("movie", "commentList is" + commentInfoList);
                    MyApplication.getContext().sendBroadcast(i);

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mRequestQueue.add(stringRequest);
    }

    public static void initReviewInfoList(String url)
    {
        Log.w("review","url is:"+url);
        StringRequest stringRequest=new StringRequest(url + "reviews", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                transformReviewList(response);
                if(!reviewInfosList.isEmpty())
                {
                    Review review=new Review();
                    review.setNum(reviewInfosList.size());
                    review.setReviewInfosList(reviewInfosList);
                    Intent i=new Intent(MovieApp.ACTION_LoadReviewInfoComplete);
                    i.putExtra("Review",review);

                    Log.w("movie","reviewList is:"+reviewInfosList);
                    MyApplication.getContext().sendBroadcast(i);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mRequestQueue.add(stringRequest);
    }


    public static void transformReviewList(String response)
    {

        if(!reviewInfosList.isEmpty())
        {
            reviewInfosList.clear();
        }

        Document doc=Jsoup.parse(response);
        Elements reviewList=doc.getElementsByClass("review");

        for(Element review:reviewList)
        {
            ReviewInfo mReviewInfo=new ReviewInfo();

            Element review_hd=review.getElementsByClass("review-hd").get(0);

            Element revie_bd=review.getElementsByClass("review-bd").get(0);

            Element h3=review_hd.select("h3").get(0);

            Element revie_hd_info=review_hd.getElementsByClass("review-hd-info").get(0);

            String  reviewRating=revie_hd_info.select("span").get(0).attr("title");

            if(reviewRating.equals("很差"))
            {
                mReviewInfo.setReviewRating(1f);
            }
            else if(reviewRating.equals("较差"))
            {
                mReviewInfo.setReviewRating(2f);

            }
            else if(reviewRating.equals("还行"))
            {
                mReviewInfo.setReviewRating(3f);


            }else if(reviewRating.equals("推荐"))
            {
                mReviewInfo.setReviewRating(4f);

            }else mReviewInfo.setReviewRating(5f);


            String reviewName=h3.getAllElements().select("a").first().attr("title");

            String reviewSrc =h3.getAllElements().first().select("img").get(0).attr("src");

            String reviewTitle=h3.getAllElements().last().attr("title");

            String reviewInfo=revie_bd.select(".review-short").select("span").get(0).text();//压缩

            mReviewInfo.setReviewName(reviewName);
            mReviewInfo.setReviewSrc(reviewSrc);
            mReviewInfo.setReviewTitle(reviewTitle);
            mReviewInfo.setReviewInfo(reviewInfo);

            reviewInfosList.add(mReviewInfo);

        }










    }

    public static void transformCommentList(String response)
    {
        if(!commentInfoList.isEmpty())
        {
            commentInfoList.clear();
        }

        Document doc=Jsoup.parse(response);
        Elements commentList=doc.getElementsByClass("comment-item");

        for(Element comment:commentList)
        {
            CommentInfo commentInfo=new CommentInfo();
            String commentPerson=comment.getElementsByClass("avatar").get(0).select("a").attr("title");
            String picSrc=comment.select("img").attr("src");

            commentInfo.setCommentName(commentPerson);
            commentInfo.setPicSrc(picSrc);

            Element comment_info=comment.getElementsByClass("comment-info").get(0);

            String rating = comment_info.select("span.rating").attr("title");

            String commentDate = comment_info.getAllElements().last().text();

            String commentContent=comment.getElementsByTag("p").get(0).text();


            if(rating.endsWith("很差"))
            {
                commentInfo.setRating(1f);
            }else if(rating.endsWith("较差"))
            {
                commentInfo.setRating(2f);
            }else if(rating.endsWith("还行"))
            {
                commentInfo.setRating(3f);
            }else if(rating.endsWith("推荐"))
            {
                commentInfo.setRating(4f);
            }else commentInfo.setRating(5f);

            commentInfo.setCommentDate(commentDate);
            commentInfo.setCommentInfo(commentContent);

            commentInfoList.add(commentInfo);

        }
    }

    public static void getASpecieMoveInfo(String url)
    {
        StringRequest stringRequest=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                setASpecieMoveInfo(response);

                if(movieInfo!=null)
                {
                    Intent i=new Intent(MovieApp.ACTION_LoadMovieInfoComplete);

                    i.putExtra("movieInfo",movieInfo);
                    MyApplication.getContext().sendBroadcast(i);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mRequestQueue.add(stringRequest);
    }


    public static void setASpecieMoveInfo(String response)
    {
        Document doc = Jsoup.parse(response);
        Element indent = doc.getElementById("link-report");


        Element summary = indent.select("span").get(0);


        String text = summary.text();

        movieInfo = new MovieInfo();

        movieInfo.setSummary(text);

        Element nodeInfo = doc.getElementById("info");

        Elements Spans = nodeInfo.getAllElements();
        String str;

        for (Element node : Spans) {
            if (node.attr("property").endsWith("v:genre")) {
                str = movieInfo.getMovieType() + " ";
                movieInfo.setMovieType(str + node.text());
            } else if (node.attr("property").endsWith("v:initialReleaseDate")) {
                str = movieInfo.getStartTime() + "  ";
                movieInfo.setStartTime(node.text() + str);
            } else if (node.attr("property").endsWith("v:runtime")) {
                movieInfo.setMovieDuration("片长:" + node.text());
            }
        }

        Element director, actors, screenWriters;

        director = nodeInfo.getElementsByClass("attrs").get(0);
        actors = nodeInfo.getElementsByClass("attrs").get(2);
        screenWriters = nodeInfo.getElementsByClass("attrs").get(1);

        movieInfo.setDirector("导演:" + director.text());
        movieInfo.setScreenWriter("编剧:" + screenWriters.text());
        movieInfo.setActors("主演:" + actors.text());

    }


}
