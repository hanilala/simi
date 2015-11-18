package com.aswifter.material.book;


import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aswifter.material.MyUtils.MyApplication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Chenyc on 15/6/30.
 * app:border_color="@color/primary_light"
 *
 *
 *
 * <android.support.design.widget.FloatingActionButton
 android:id="@+id/fab_normal"
 android:layout_width="wrap_content"
 android:layout_height="wrap_content"
 android:layout_gravity="bottom|right"
 android:layout_marginBottom="@dimen/btn_fab_margins"
 android:layout_marginRight="@dimen/btn_fab_margins"
 android:src="@drawable/ic_find_in_page_white"
 app:borderWidth="0dp"
 app:elevation="6dp"
 app:pressedTranslationZ="12dp" />
 *
 *
 *
 *
 *
 app:border_width="2dp"
 */
public class Book implements Serializable {

    private String subtitle;
    private String[] author;
    private String pubdate;
    private String origin_title;
    private String image;
    private String catalog;
    private String alt;
    private String id;
    private String publisher;
    private String title;
    private String url;
    private String author_intro;
    private String summary;
    private String price;
    private String pages;
    private Images images;


    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String[] getAuthor() {
        return author;
    }

    public void setAuthor(String[] author) {
        this.author = author;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getOrigin_title() {
        return origin_title;
    }

    public void setOrigin_title(String origin_title) {
        this.origin_title = origin_title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor_intro() {
        return author_intro;
    }

    public void setAuthor_intro(String author_intro) {
        this.author_intro = author_intro;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public class Images implements Serializable {
        private String small;
        private String large;
        private String medium;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }
    }

    @Override
    public String toString() {
        return "Book{" +
                "subtitle='" + subtitle + '\'' +
                ", author=" + Arrays.toString(author) +
                ", pubdate='" + pubdate + '\'' +
                ", origin_title='" + origin_title + '\'' +
                ", image='" + image + '\'' +
                ", catalog='" + catalog + '\'' +
                ", alt='" + alt + '\'' +
                ", id='" + id + '\'' +
                ", publisher='" + publisher + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", author_intro='" + author_intro + '\'' +
                ", summary='" + summary + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    private static AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);

    private static RequestQueue mRequestQueue= Volley.newRequestQueue(MyApplication.getContext());


//    private StringRequest mStringRequest









    private static final String BASE_URL = "http://api.douban.com/v2/";

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }


    public interface IBookResponse<T> {
        void onData(T data);
    }




    public static void searchBooks(final String name, final IBookResponse<List<Book>> responseBook) {
       /* RequestParams params = new RequestParams();
        params.put("q", name);
        params.put("start", 0);
        params.put("end", 50);*/



         Response.Listener listener=new Response.Listener() {
            @Override
            public void onResponse(Object response) {

                Gson gson=new Gson();

                try {
                    JSONObject jsonObject=new JSONObject((String)response);

                    JSONArray jsonArray=jsonObject.getJSONArray("books");

                    List<Book> books = gson.fromJson(jsonArray.toString(), new TypeToken<List<Book>>() {
                    }.getType());

                    responseBook.onData(books);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener=new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.w("MyTip","error is:"+error);
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.POST, getAbsoluteUrl("book/search"),
                listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("q", name);
                map.put("start", Integer.toString(0));
                map.put("end",Integer.toString(50));
                return map;
            }
        };

        mRequestQueue.add(stringRequest);

       /* client.get(getAbsoluteUrl("book/search"), params, new AsyncHttpResponseHandler() {



            public void onSuccess(int statusCode, PreferenceActivity.Header[] headers, byte[] responseBody) {
                try {
                    Gson gson = new Gson();
                    JSONObject json = new JSONObject(new String(responseBody));


                    JSONArray jaBooks = json.optJSONArray("books");
                    List<Book> books = gson.fromJson(jaBooks.toString(), new TypeToken<List<Book>>() {
                    }.getType());
                    response.onData(books);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


            public void onFailure(int statusCode, PreferenceActivity.Header[] headers, byte[] responseBody, Throwable error) {

            }
        });*/
    }


}
