package com.example.practice_10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class NewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        EditText nameInput = findViewById(R.id.name_input);
        EditText albumNameInput = findViewById(R.id.album_input);
        EditText authorNameInput = findViewById(R.id.author_input);
        Button saveButton = findViewById(R.id.button_db_save);
        Button deleteButton = findViewById(R.id.button_db_delete);
        Button findButton = findViewById(R.id.button_db_find);
        RecyclerView tracksList = findViewById(R.id.tracks_list);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<Track> tracks = dbHelper.getAllContacts();
        TrackAdapter adapter = new TrackAdapter(tracks);
        tracksList.setLayoutManager(new LinearLayoutManager(this));
        tracksList.setAdapter(adapter);


        saveButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString();
            String album_name = albumNameInput.getText().toString();
            String author_name = authorNameInput.getText().toString();
            if (dbHelper.addTrack(new Track(name, album_name, author_name)))
            {
                tracks.add(new Track(name, album_name, author_name));
                adapter.notifyItemInserted(tracks.size() - 1);
                Toast.makeText(this, "Track saved successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to save tracks", Toast.LENGTH_SHORT).show();
            }
        });



        deleteButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString();
            if (dbHelper.deleteTrack(name)) {
                int position = -1;
                for (int i = 0; i < tracks.size(); i++) {
                    if (tracks.get(i).getName().equals(name))
                    {
                        position = i;
                        tracks.remove(i);
                        break;
                    }
                }
                if (position != -1) {
                    adapter.notifyItemRemoved(position);
                    Toast.makeText(this, "Track deleted successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Track not found",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Failed to delete track",
                        Toast.LENGTH_SHORT).show();
            }
        });

        findButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString();
            Track foundContact = dbHelper.findTrack(name);
            if (foundContact != null) {
                nameInput.setText(foundContact.getName());
                albumNameInput.setText(foundContact.getAlbum_name());
                authorNameInput.setText(foundContact.getAuthor_name());
                Toast.makeText(this, "Track found: " + foundContact.getName(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Track not found", Toast.LENGTH_SHORT).show();
            }
        });

        Button updateButton = findViewById(R.id.button_db_update);
        updateButton.setOnClickListener(v -> {
            String oldAlbumName = albumNameInput.getText().toString();
            String newName = nameInput.getText().toString();
            String newAlbumName = albumNameInput.getText().toString();
            String newAuthorName = authorNameInput.getText().toString();
            if (dbHelper.updateTrack(oldAlbumName, newName, newAlbumName, newAuthorName)) {
                Toast.makeText(this, "Track updated successfully!", Toast.LENGTH_SHORT).show();
                refreshContactsList(dbHelper, tracks, adapter, tracksList);
            } else {
                Toast.makeText(this, "Failed to update track", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void refreshContactsList(DatabaseHelper dbHelper, List<Track> tracks, TrackAdapter adapter, RecyclerView tracksList) {
        tracks = dbHelper.getAllContacts();
        adapter = new TrackAdapter(tracks);
        tracksList.setAdapter(adapter);
    }
}