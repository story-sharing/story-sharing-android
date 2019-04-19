package es.anjon.dyl.storysharing;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import es.anjon.dyl.storysharing.adapter.CommentAdapter;
import es.anjon.dyl.storysharing.model.Comment;
import es.anjon.dyl.storysharing.model.Scene;
import es.anjon.dyl.storysharing.model.Story;

public class SceneFragment extends Fragment {

    private static final String TAG = "SceneFragment";
    private Scene mScene;
    private Story mStory;

    public static SceneFragment newInstance(Scene scene, Story story) {
        SceneFragment fragment = new SceneFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Scene.TAG, scene);
        bundle.putSerializable(Story.TAG, story);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mScene = (Scene) getArguments().getSerializable(Scene.TAG);
        mStory = (Story) getArguments().getSerializable(Story.TAG);
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_scene, container, false);

        RelativeLayout layout = rootView.findViewById(R.id.content);
        layout.setBackgroundColor(Color.parseColor(mScene.getBackgroundColour()));

        TextView titleView = layout.findViewById(R.id.title);
        titleView.setText(mScene.getTitle());
        titleView.setTextColor(Color.parseColor(mScene.getTextColour()));

        final TextView commentCountView = layout.findViewById(R.id.comment_count);
        final CommentAdapter commentAdapter = new CommentAdapter(mScene.getComments());
        final RecyclerView commentsView = layout.findViewById(R.id.comments);
        LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext());
        commentsView.setLayoutManager(layoutManager);
        commentsView.setAdapter(commentAdapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ListenerRegistration mCommentsListener = db.collection("stories/" + mStory.getId() + "/scenes/" + mScene.getId() + "/comments").orderBy("createdAt", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "listen:error", e);
                    return;
                }

                for (DocumentChange dc : snapshots.getDocumentChanges()) {
                    Comment comment = dc.getDocument().toObject(Comment.class);
                    comment.setId(dc.getDocument().getId());
                    switch (dc.getType()) {
                        case ADDED:
                            Log.d(TAG, "New comment: " + comment);
                            mScene.getComments().add(comment);
                            break;
                        case MODIFIED:
                            Log.d(TAG, "Modified comment: " + dc.getDocument().getData());
                            mScene.getComments().set(mScene.getComments().indexOf(comment), comment);
                            break;
                        case REMOVED:
                            Log.d(TAG, "Removed comment: " + dc.getDocument().getData());
                            mScene.getComments().remove(comment);
                            break;
                    }
                }
                commentAdapter.notifyDataSetChanged();
                commentCountView.setText(String.valueOf(mScene.getComments().size()));
            }
        });

        commentCountView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commentsView.getVisibility() == View.GONE) {
                    commentsView.setVisibility(View.VISIBLE);
                } else {
                    commentsView.setVisibility(View.GONE);
                }
            }
        });

        return rootView;
    }

}
