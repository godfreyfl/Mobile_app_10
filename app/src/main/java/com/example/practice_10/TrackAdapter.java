package com.example.practice_10;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.ViewHolder>{
    private final List<Track> tracks;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final TextView albumNameTextView;
        private final TextView authorNameTextView;
        public ViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.name_text);
            albumNameTextView = view.findViewById(R.id.album_name_text);
            authorNameTextView = view.findViewById(R.id.author_name_text);
        }
        public void bind(Track track) {
            nameTextView.setText(track.getName());
            albumNameTextView.setText(track.getAlbum_name());
            authorNameTextView.setText(track.getAuthor_name());
        }
    }
    public TrackAdapter(List<Track> tracks) {
        this.tracks = tracks;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_track, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.bind(tracks.get(position));
    }
    @Override
    public int getItemCount() {
        return tracks.size();
    }
}

