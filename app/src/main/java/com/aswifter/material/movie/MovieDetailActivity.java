package com.aswifter.material.movie;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.aswifter.material.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/08.
 */
public class MovieDetailActivity extends AppCompatActivity {


    private SimpleSubjectPro mSubject;

    private ViewPager mViewPager;
    private CollapsingToolbarLayout collapsingToolbar;
    private ImageView mImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appbar_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        collapsingToolbar= (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mSubject= (SimpleSubjectPro) getIntent().getSerializableExtra("movie");
        collapsingToolbar.setTitle(mSubject.getTitle());

        mImageView= (ImageView) findViewById(R.id.ivImage);

        Glide.with(this).load(mSubject.getImages().getLarge())
                .fitCenter().into(mImageView);

        mViewPager= (ViewPager) findViewById(R.id.viewpager);

        AddFragmentToViewPager();


        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.addTab(tabLayout.newTab().setText("剧情简介"));
        tabLayout.addTab(tabLayout.newTab().setText("影片短评"));
        tabLayout.addTab(tabLayout.newTab().setText("影评"));
        tabLayout.setupWithViewPager(mViewPager);

        Log.w("movie","I am in MovieDetailActivity");

    }


    public void AddFragmentToViewPager()
    {
        MoviePagerAdapter mMoviePagerAdapter=new MoviePagerAdapter(getSupportFragmentManager());
        mMoviePagerAdapter.addFragment("剧情简介",
                MovieSummaryFragment.newInstance(mSubject.getAlt()));

        mMoviePagerAdapter.addFragment("影片短评",
                MovieCommentFragment.newInstance(mSubject.getAlt()));
        mMoviePagerAdapter.addFragment("影评",MovieReviewFragment.newInstance(mSubject.getAlt()));

        mViewPager.setAdapter(mMoviePagerAdapter);
    }


    class MoviePagerAdapter extends FragmentPagerAdapter
    {
        private List<Fragment> mFragmentList=new ArrayList<>();
        private List<String> mFragmentTitleList=new ArrayList<>();
        public MoviePagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        public void addFragment(String title,Fragment fragment)
        {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }


        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
