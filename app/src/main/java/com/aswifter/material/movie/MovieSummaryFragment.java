package com.aswifter.material.movie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aswifter.material.R;

/**
 * Created by Administrator on 2015/11/12.
 */
public class MovieSummaryFragment  extends Fragment{

    private MovieInfo mMovieInfo;
    private ProgressBar mProgressBar;
    private TextView tvDirector,tvScreenWriter,tvActors,tvMovieType,tvMovieDuration;
    private TextView tvReleaseTime,tvMovieSummary;

    private IntentFilter mIntentFilter;
    private MovieInfoReceiver mMovieInfoReceiver;
    private String movieUrl;

    public static MovieSummaryFragment newInstance(String url)
    {
        Bundle bundle=new Bundle();
        bundle.putString("URL", url);
        MovieSummaryFragment movieSummaryFragment=new MovieSummaryFragment();
        movieSummaryFragment.setArguments(bundle);

        return movieSummaryFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIntentFilter=new IntentFilter(MovieApp.ACTION_LoadMovieInfoComplete);
        mMovieInfoReceiver=new MovieInfoReceiver();
        getActivity().registerReceiver(mMovieInfoReceiver,mIntentFilter);
        movieUrl=getArguments().getString("URL");
        Movie.getASpecieMoveInfo(movieUrl);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mMovieInfoReceiver);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_movie_summary,container,false);

        findViewByIdIn(view);
        return view;
    }

    public void findViewByIdIn(View view)
    {
        tvDirector= (TextView) view.findViewById(R.id.tvDirector);
        tvScreenWriter= (TextView) view.findViewById(R.id.tvScreenWriter);
        tvActors= (TextView) view.findViewById(R.id.tvMovieActors);
        tvMovieType= (TextView) view.findViewById(R.id.tvMovieType);
        tvReleaseTime= (TextView) view.findViewById(R.id.tvReleaseTime);
        tvMovieSummary= (TextView) view.findViewById(R.id.tvSummary);
        tvMovieDuration= (TextView) view.findViewById(R.id.tvMovieDuration);

        mProgressBar= (ProgressBar) view.findViewById(R.id.progressBar_Movie_Summary);


    }
    public void initFragment()
    {
        mProgressBar.setVisibility(View.GONE);
        tvDirector.setText(mMovieInfo.getDirector());
        tvScreenWriter.setText(mMovieInfo.getScreenWriter());
        tvActors.setText(mMovieInfo.getActors());
        tvMovieType.setText("类型:"+mMovieInfo.getMovieType());
        tvReleaseTime.setText("上映日期:"+mMovieInfo.getStartTime());
        tvMovieSummary.setText("简介:"+mMovieInfo.getSummary());
        tvMovieDuration.setText("片长:"+mMovieInfo.getMovieDuration());

    }

    class MovieInfoReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {
            mMovieInfo= (MovieInfo) intent.getSerializableExtra("movieInfo");
            initFragment();
        }
    }

}
