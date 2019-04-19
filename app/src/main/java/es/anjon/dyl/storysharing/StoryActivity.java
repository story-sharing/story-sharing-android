package es.anjon.dyl.storysharing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import es.anjon.dyl.storysharing.model.Scene;
import es.anjon.dyl.storysharing.model.Story;
import es.anjon.dyl.storysharing.transformer.DepthPageTransformer;

public class StoryActivity extends FragmentActivity {

    public static final String TAG = "StoryActivity";
    public static final String STORY_KEY = "story";
    private Story mStory;
    private ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStory = (Story) getIntent().getSerializableExtra(STORY_KEY);
        setContentView(R.layout.activity_story);
        mPager = findViewById(R.id.pager);
        final PagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
        mPager.setPageTransformer(true, new DepthPageTransformer());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ListenerRegistration mScenesListener = db.collection("stories/" + mStory.getId() + "/scenes").orderBy("createdAt", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "listen:error", e);
                    return;
                }

                for (DocumentChange dc : snapshots.getDocumentChanges()) {
                    Scene scene = dc.getDocument().toObject(Scene.class);
                    scene.setId(dc.getDocument().getId());
                    switch (dc.getType()) {
                        case ADDED:
                            Log.d(TAG, "New scene: " + scene);
                            mStory.getScenes().add(scene);
                            break;
                        case MODIFIED:
                            Log.d(TAG, "Modified scene: " + dc.getDocument().getData());
                            mStory.getScenes().set(mStory.getScenes().indexOf(scene), scene);
                            break;
                        case REMOVED:
                            Log.d(TAG, "Removed scene: " + dc.getDocument().getData());
                            mStory.getScenes().remove(scene);
                            break;
                    }
                }
                pagerAdapter.notifyDataSetChanged();
            }
        });
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
