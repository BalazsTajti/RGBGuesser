package com.tajtesz.rgbguesser;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 2017.11.11..
 */

public class HighScoreAdapter extends RecyclerView.Adapter<HighScoreAdapter.HighScoreViewHolder> {
    private final List<HighScore> items;

    public HighScoreAdapter() {
        items = new ArrayList<>();
    }

    @Override
    public HighScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_highscore_list, parent, false);
        HighScoreViewHolder viewHolder = new HighScoreViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HighScoreViewHolder holder, int position) {
        HighScore item = items.get(position);
        holder.placeTextView.setText(Integer.toString(position+1) + ".");
        holder.userNameTextView.setText(item.getName());
        holder.highScoreTextView.setText(Integer.toString(item.getScore()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class HighScoreViewHolder extends RecyclerView.ViewHolder {

        TextView placeTextView;
        TextView userNameTextView;
        TextView highScoreTextView;

        public HighScoreViewHolder(View itemView) {
            super(itemView);
            placeTextView = (TextView) itemView.findViewById(R.id.HighScorePlace);
            userNameTextView = (TextView) itemView.findViewById(R.id.HighScoreUser);
            highScoreTextView = (TextView) itemView.findViewById(R.id.HighScoreScore);
        }
    }

    public void addItem(HighScore item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void update(List<HighScore> highScoreItems) {
        items.clear();
        items.addAll(highScoreItems);
        notifyDataSetChanged();
    }

}
