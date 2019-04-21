package es.anjon.dyl.storysharing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import es.anjon.dyl.storysharing.adapter.StoryAdapter;
import es.anjon.dyl.storysharing.model.Group;
import es.anjon.dyl.storysharing.model.Story;

public class GroupActivity extends FragmentActivity {

    public static final String TAG = "GroupActivity";
    public static final String GROUP_KEY = "group";

    private Group mGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        mGroup = (Group) getIntent().getSerializableExtra(GROUP_KEY);
        setContentView(R.layout.activity_group);
        TextView titleView = findViewById(R.id.title);
        titleView.setText(mGroup.getTitle());

        final List<Story> stories = new ArrayList<>();
        final StoryAdapter storyAdapter = new StoryAdapter(stories);
        final RecyclerView storiesView = findViewById(R.id.stories);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        storiesView.setLayoutManager(layoutManager);
        storiesView.setAdapter(storyAdapter);
        ListenerRegistration mStoriesListener = db.collection("stories").whereArrayContains("groups", mGroup.getId()).orderBy("createdAt", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "listen:error", e);
                    return;
                }

                for (DocumentChange dc : snapshots.getDocumentChanges()) {
                    Story story = dc.getDocument().toObject(Story.class);
                    story.setId(dc.getDocument().getId());
                    switch (dc.getType()) {
                        case ADDED:
                            Log.d(TAG, "New story: " + story);
                            stories.add(story);
                            break;
                        case MODIFIED:
                            Log.d(TAG, "Modified story: " + dc.getDocument().getData());
                            stories.set(stories.indexOf(story), story);
                            break;
                        case REMOVED:
                            Log.d(TAG, "Removed story: " + dc.getDocument().getData());
                            stories.remove(story);
                            break;
                    }
                }
                storyAdapter.notifyDataSetChanged();
            }
        });
    }

}
