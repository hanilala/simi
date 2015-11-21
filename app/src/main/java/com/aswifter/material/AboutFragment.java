package com.aswifter.material;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aswifter.material.movie.Movie;
import com.aswifter.material.movie.MovieApp;
import com.aswifter.material.movie.SimpleSubjectPro;
import com.aswifter.material.movie.Us_Box;

import java.util.List;

/**
 * Created by Chenyc on 15/6/27.
 */
public class AboutFragment extends Fragment {


    private List<Us_Box.Subjects> allSubjects = null;

    private SimpleSubjectPro subject;

    private TextView tvData;
    private ProgressBar mProgressBar;

    private IntentFilter intentFilter;
    private MovieReceiverInAbout movieReceiver;






    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, null);

        tvData = (TextView) view.findViewById(R.id.appData);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        intentFilter=new IntentFilter();

        intentFilter.addAction(MovieApp.ACTION_LoadComplete);

        movieReceiver=new MovieReceiverInAbout();
        getActivity().registerReceiver(movieReceiver,intentFilter);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
/*
        if (allSubjects != null) {
            mProgressBar.setVisibility(View.GONE);
            subject = allSubjects.get(0).getSubject();
            tvData.setText("影片 :" + subject.getTitle());
        }*/


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getUsData();


    }

    public void getUsData() {


        Movie.InitUs_Box(MovieApp.Url_AllData);
        Log.w("Movie", "allSubjects(in AboutFrag) is :" + allSubjects);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(movieReceiver);
    }

    class MovieReceiverInAbout extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {

            Us_Box allData= (Us_Box) intent.getSerializableExtra("allData");

            allSubjects=allData.getSubjects();

            mProgressBar.setVisibility(View.GONE);
            subject = allSubjects.get(0).getSubject();
            tvData.setText("影片 :" + subject.getTitle());



        }
    }


}
