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
import android.widget.TextView;

import com.aswifter.material.R;
import com.aswifter.material.widget.RecyclerItemClickListener;
import com.bumptech.glide.Glide;

/**
 * Created by Administrator on 2015/11/08.
 */
public class MovieFragment extends Fragment {

    private Us_Box allData;

    private SimpleSubjectPro simpleSubjectPro;
    private IntentFilter mIntentFilter;
    private MovieReceiver mMovieReceiver;

    private RecyclerView mRecyclerView;
    private MovieAdapter mAdapter;
    private ProgressBar mProgressBar;

    private int MovieNum;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container,
                false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), listener));
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);



        InitUs_box();
        return view;
    }

    public void Init_Adapter()
    {
        mAdapter = new MovieAdapter();
        mRecyclerView.setAdapter(mAdapter);


        mProgressBar.setVisibility(View.GONE);

    }


    private RecyclerItemClickListener.OnItemClickListener listener = new
            RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    simpleSubjectPro=allData.getSubjects().get(position).getSubject();

                    Intent  intent=new Intent(getActivity(),MovieDetailActivity.class);
                    intent.putExtra("movie", simpleSubjectPro);

/*
                    ActivityOptionsCompat options =
                            ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                                    view.findViewById(R.id.ivBook), getString(R.string.transition_book_img));

                    ActivityCompat.startActivity(getActivity(), intent, options.toBundle());*/
                    Log.w("movie", "click in OnItemClickListener ");

                    getActivity().startActivity(intent);

                }
            };


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void InitUs_box() {

        mIntentFilter=new IntentFilter(MovieApp.ACTION_LoadComplete);
        mMovieReceiver=new MovieReceiver();
        getActivity().registerReceiver(mMovieReceiver, mIntentFilter);

        Object AllFrmCache=Movie.getStringFromCache(MovieApp.Url_AllData,
                MovieApp.Movie_AllData);

        if(AllFrmCache!=null)
        {
            allData=(Us_Box)AllFrmCache;

            MovieNum=allData.getSubjects().size();
            Init_Adapter();
            Log.w("haha", "getCache is Ok");

        }
        //如果AllFrmCach为null,Movie会发起网络请求，并将
        //结果广播回来.


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mMovieReceiver);
    }

    public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
        private SimpleSubjectPro subject;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View vH = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item
                    , parent, false);

            ViewHolder viewHolder = new ViewHolder(vH);

            return viewHolder;


        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            subject = allData.getSubjects().get(position).getSubject();
            holder.tvMovieTitle.setText(subject.getTitle());

            holder.tvMainActors.setText(subject.getMainActorsName());
            holder.tvRating.setText("排名："+allData.getSubjects().get(position).getRank());
            holder.tvBox.setText("票房："+allData.getSubjects().get(position).getBox());

            Glide.with(MovieFragment.this)
                    .load(subject.getImages().getLarge())
                    .centerCrop()
                    .placeholder(R.drawable.book2)
                    .crossFade()
                    .into(holder.ivMovie);

        }

        @Override
        public int getItemCount() {
            Log.w("Movie","allData(in MovieFrag) is"+allData);
            return MovieNum;
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView ivMovie;
            private TextView tvMovieTitle;
            private TextView tvMainActors;

            private TextView tvRating;
            private TextView tvBox;

            public ViewHolder(View v) {
                super(v);

                ivMovie = (ImageView) v.findViewById(R.id.ivMovie);
                tvMovieTitle = (TextView) v.findViewById(R.id.tvMovieTitle);
                tvMainActors = (TextView) v.findViewById(R.id.tvMainActors);
                tvRating = (TextView) v.findViewById(R.id.tvRating);
                tvBox = (TextView) v.findViewById(R.id.tvBox);

            }

        }
    }

    class MovieReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {

            allData= (Us_Box) intent.getSerializableExtra("allData");
            MovieNum=allData.getSubjects().size();
            Init_Adapter();



        }
    }


}
