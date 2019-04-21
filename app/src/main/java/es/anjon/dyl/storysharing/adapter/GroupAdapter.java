package es.anjon.dyl.storysharing.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import es.anjon.dyl.storysharing.R;
import es.anjon.dyl.storysharing.model.Group;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {

    private List<Group> mGroups;
    private final GroupAdapter.OnItemClickListener mListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.title);
        }

        public void bind(final Group group, final OnItemClickListener listener) {
            mTextView.setText(group.getTitle());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(group);
                }
            });
        }
    }

    public GroupAdapter(List<Group> groups, OnItemClickListener listener) {
        this.mGroups = groups;
        this.mListener = listener;
    }

    @Override
    public GroupAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Group group = mGroups.get(position);
        holder.bind(group, mListener);
    }

    @Override
    public int getItemCount() {
        return mGroups.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Group group);
    }

}
