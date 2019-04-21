package es.anjon.dyl.storysharing.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import es.anjon.dyl.storysharing.R;
import es.anjon.dyl.storysharing.adapter.StoryAdapter;
import es.anjon.dyl.storysharing.model.Story;

public class StoriesFragment extends Fragment {

    private static final String TAG = "StoriesFragment";

    private ListenerRegistration mStoriesListener;

    public static Fragment newInstance() {
        Fragment frag = new StoriesFragment();
        frag.setRetainInstance(true);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stories, container, false);

        final List<Story> stories = new ArrayList<>();
        final RecyclerView recyclerView = view.findViewById(R.id.stories);
        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        final StoryAdapter adapter = new StoryAdapter(stories);
        recyclerView.setAdapter(adapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query query = db.collection("stories").orderBy("createdAt", Query.Direction.DESCENDING);
        mStoriesListener = query.addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                adapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mStoriesListener.remove();
    }

}
