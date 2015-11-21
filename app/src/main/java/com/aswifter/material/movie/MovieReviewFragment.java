package com.aswifter.material.movie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.aswifter.material.R;
import com.bumptech.glide.Glide;

import java.util.LinkedList;

/**
 * Created by Administrator on 2015/11/14.
 */
public class MovieReviewFragment extends Fragment {


    private LinkedList<ReviewInfo> mReviewInfosList;

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    private IntentFilter intentFilter;
    private MovieReviewReceiver movieReviewReceiver;
    private static String url;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intentFilter=new IntentFilter(MovieApp.ACTION_LoadReviewInfoComplete);
        movieReviewReceiver=new MovieReviewReceiver();
        getActivity().registerReceiver(movieReviewReceiver, intentFilter);
        url=getArguments().getString("URL");
    }

    public static MovieReviewFragment newInstance(String url)
    {
        Bundle bundle=new Bundle();
        bundle.putString("URL", url);
        MovieReviewFragment movieReviewFragment=new MovieReviewFragment();
        movieReviewFragment.setArguments(bundle);
        return movieReviewFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //让MivieReviewFragment能在重新创建时发出网络请求，初始化界面。
        Object reviewObject=Movie.getStringFromCache(url+"reviews",MovieApp.Movie_Review);
        if(reviewObject!=null)
        {
            Review review= (Review) reviewObject;
            mReviewInfosList= review.getReviewInfosList();
            if(mReviewInfosList!=null)
            {
                initFragment();
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(movieReviewReceiver);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_movie_commment,container,false);

        setFindView(view);

        Object reviewObject=Movie.getStringFromCache(url+"reviews",MovieApp.Movie_Review);

        if(reviewObject!=null)
        {
            Review review= (Review) reviewObject;
            mReviewInfosList= review.getReviewInfosList();
            if(mReviewInfosList!=null)
            {
                initFragment();
            }
        }


        return view;
    }

    public void setFindView(View view)
    {
        mRecyclerView= (RecyclerView) view.findViewById(R.id.movie_recyclerview);
        mProgressBar= (ProgressBar) view.findViewById(R.id.comment_progressBar);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void initFragment()
    {
        MovieReviewAdapter movieReviewAdapter=new MovieReviewAdapter();
        mRecyclerView.setAdapter(movieReviewAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        mProgressBar.setVisibility(View.GONE);

    }

    public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.ReviewHolder>
    {

        @Override
        public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view=LayoutInflater.from(getActivity()).inflate(R.layout.movie_review_item,parent
            ,false);
            return new ReviewHolder(view);
        }

        @Override
        public void onBindViewHolder(ReviewHolder holder, int position) {
            ReviewInfo reviewInfo=mReviewInfosList.get(position);
            Glide.with(MovieReviewFragment.this).load(reviewInfo.getReviewSrc())
                    .fitCenter().into(holder.mivReviewImg);
            holder.mtvReviewTitle.setText(reviewInfo.getReviewTitle());
            holder.mRatingBar.setRating(reviewInfo.getReviewRating());
            holder.mtvReviewName.setText("----" + reviewInfo.getReviewName());
            holder.mtvReviewInfo.setText(reviewInfo.getReviewInfo());

        }

        @Override
        public int getItemCount() {
            return mReviewInfosList.size();
        }



        class ReviewHolder extends RecyclerView.ViewHolder
        {
            private TextView mtvReviewTitle,mtvReviewName,mtvReviewInfo;
            private RatingBar mRatingBar;
            private ImageView mivReviewImg;

            public ReviewHolder(View view)
            {
                super(view);
                mivReviewImg= (ImageView) view.findViewById(R.id.ivReviewImg);
                mtvReviewTitle= (TextView) view.findViewById(R.id.tvReviewTitle);
                mtvReviewName= (TextView) view.findViewById(R.id.tvReviewName);
                mtvReviewInfo= (TextView) view.findViewById(R.id.tvReviewInfo);
                mRatingBar= (RatingBar) view.findViewById(R.id.reviewRatingBar);
            }
        }
    }

    public class MovieReviewReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {
            Review review= (Review) intent.getSerializableExtra("Review");
            mReviewInfosList=review.getReviewInfosList();
            Log.w("review","reviewList(in OnReceive) is"+mReviewInfosList);
            initFragment();
        }
    }


}
