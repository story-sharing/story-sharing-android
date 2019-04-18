package es.anjon.dyl.storysharing.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import es.anjon.dyl.storysharing.R;
import es.anjon.dyl.storysharing.model.Story;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryCardViewHolder> {

    private List<Story> mStories;

    public static class StoryCardViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public TextView titleView;
        public StoryCardViewHolder(CardView v) {
            super(v);
            cardView = v;
            titleView = v.findViewById(R.id.title);
        }
    }

    public StoryAdapter(List<Story> stories) {
        mStories = stories;
    }

    @Override
    public StoryCardViewHolder onCreateViewHolder(ViewGroup parent,
                                                  int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.story_card, parent, false);
        StoryCardViewHolder vh = new StoryCardViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(StoryCardViewHolder holder, int position) {
        holder.titleView.setText(mStories.get(position).toString());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent(v.getContext(), SecondPage.class);
                //v.getContext().startActivity(intent);
                Toast.makeText(v.getContext().getApplicationContext(), "Hi", Toast.LENGTH_SHORT);
                Log.d("yo", "I clicked one of the card views please");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStories.size();
    }

}
