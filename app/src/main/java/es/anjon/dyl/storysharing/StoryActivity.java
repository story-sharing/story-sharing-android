package es.anjon.dyl.storysharing;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import es.anjon.dyl.storysharing.model.Scene;
import es.anjon.dyl.storysharing.model.Story;

public class StoryActivity extends FragmentActivity {

    public static final String STORY_KEY = "story";
    private Story mStory;
    private ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStory = (Story) getIntent().getSerializableExtra(STORY_KEY);
        setContentView(R.layout.activity_story);
        mPager = findViewById(R.id.pager);
        PagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Scene scene = mStory.getScenes().get(position);
            return SceneFragment.newInstance(scene);
        }

        @Override
        public int getCount() {
            return mStory.getScenes().size();
        }
    }

}