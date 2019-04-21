package es.anjon.dyl.storysharing.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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
import es.anjon.dyl.storysharing.adapter.GroupAdapter;
import es.anjon.dyl.storysharing.model.Group;

public class GroupsFragment extends Fragment {

    private static final String TAG = "GroupsFragment";

    public static Fragment newInstance() {
        Fragment frag = new GroupsFragment();
        frag.setRetainInstance(true);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_groups, container, false);

        final List<Group> groups = new ArrayList<>();
        final GroupAdapter adapter = new GroupAdapter(groups);
        final RecyclerView groupsView = view.findViewById(R.id.groups);
        LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext());
        groupsView.setLayoutManager(layoutManager);
        groupsView.setAdapter(adapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ListenerRegistration mGroupsListener = db.collection("groups").orderBy("createdAt", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "listen:error", e);
                    return;
                }

                for (DocumentChange dc : snapshots.getDocumentChanges()) {
                    Group group = dc.getDocument().toObject(Group.class);
                    group.setId(dc.getDocument().getId());
                    switch (dc.getType()) {
                        case ADDED:
                            Log.d(TAG, "New group: " + group);
                            groups.add(group);
                            break;
                        case MODIFIED:
                            Log.d(TAG, "Modified group: " + dc.getDocument().getData());
                            groups.set(groups.indexOf(group), group);
                            break;
                        case REMOVED:
                            Log.d(TAG, "Removed group: " + dc.getDocument().getData());
                            groups.remove(group);
                            break;
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
