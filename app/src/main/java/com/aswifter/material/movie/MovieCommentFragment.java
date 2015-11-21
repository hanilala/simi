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
 * Created by Administrator on 2015/11/13.
 */
public class MovieCommentFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private LinkedList<CommentInfo> mCommentInfoList;
    private MovieCommentReceiver mMovieCommentReceiver;
    private IntentFilter mIntentFilter;
    private ProgressBar mProgressBar;
    private static String url;

    public static MovieCommentFragment newInstance(String movieAlt)
    {
        Bundle bundle=new Bundle();
        bundle.putString("URL", movieAlt);
        MovieCommentFragment movieCommentFragment=new MovieCommentFragment();
        movieCommentFragment.setArguments(bundle);
        return movieCommentFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMovieCommentReceiver=new MovieCommentReceiver();
        mIntentFilter=new IntentFilter(MovieApp.ACTION_LoadCommentInfoComplete);
        getActivity().registerReceiver(mMovieCommentReceiver,mIntentFilter);

        url=getArguments().getString("URL");


        Movie.initCommentInfoList(url);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mMovieCommentReceiver);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //让Fragment能在重新创建时发出网络请求，初始化界面。

        Object CommentList=Movie.getStringFromCache(url,MovieApp.Movie_Comment);
        mCommentInfoList= (LinkedList<CommentInfo>) CommentList;
        if(mCommentInfoList!=null)
        {
            initFragment();
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie_commment, container, false);

        setFindView(view);

        Object CommentList=Movie.getStringFromCache(url,MovieApp.Movie_Comment);
        mCommentInfoList= (LinkedList<CommentInfo>) CommentList;
        if(mCommentInfoList!=null)
        {
            initFragment();
        }
        return view;
    }

    public void setFindView(View view)
    {

        mProgressBar= (ProgressBar) view.findViewById(R.id.comment_progressBar);
        mRecyclerView= (RecyclerView) view.findViewById(R.id.movie_recyclerview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    public void initFragment()
    {
        MovieCommentAdapter mMovieCommentAdapter=new MovieCommentAdapter();
        mRecyclerView.setAdapter(mMovieCommentAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()
                ,DividerItemDecoration.VERTICAL_LIST));
        mProgressBar.setVisibility(View.GONE);
    }


    public class MovieCommentReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {

            Comment comment= (Comment) intent.getSerializableExtra("Comment");
            mCommentInfoList=comment.getCommentInfoList();
            initFragment();

        }
    }



    public class MovieCommentAdapter extends RecyclerView.Adapter<MovieCommentAdapter.CommentViewHolder> {


        @Override
        public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view=LayoutInflater.from(getContext()).
                    inflate(R.layout.movie_comment_item, parent, false);
            return new CommentViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CommentViewHolder holder, int position) {

            CommentInfo commentInfo=mCommentInfoList.get(position);

            Glide.with(MovieCommentFragment.this).load(commentInfo.getPicSrc())
                    .fitCenter().into(holder.ivCommentPic);

            holder.tvCommentName.setText(commentInfo.getCommentName());
            holder.tvCommentDate.setText(commentInfo.getCommentDate());
            holder.ratingBar.setRating(commentInfo.getRating());
            holder.tvCommentInfo.setText(commentInfo.getCommentInfo());
        }

        @Override
        public int getItemCount() {
            return mCommentInfoList.size();
        }

        public class CommentViewHolder extends RecyclerView.ViewHolder {

            private ImageView ivCommentPic;
            private TextView  tvCommentName;
            private TextView tvCommentDate;
            private RatingBar ratingBar;
            private TextView tvCommentInfo;

            public CommentViewHolder(View view) {
                super(view);

                ivCommentPic= (ImageView) view.findViewById(R.id.ivCommentPic);
                tvCommentName= (TextView) view.findViewById(R.id.tvCommentName);
                tvCommentDate= (TextView) view.findViewById(R.id.tvCommentDate);
                ratingBar= (RatingBar) view.findViewById(R.id.RatingMovie);
                tvCommentInfo= (TextView) view.findViewById(R.id.tvCommentInfo);
            }
        }
    }

}
