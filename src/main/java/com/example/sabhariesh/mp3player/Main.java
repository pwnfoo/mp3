package com.example.sabhariesh.mp3player;

import android.app.ListActivity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Mp3Filter implements FilenameFilter {
    private File dir;
    private String filename;

    @Override
    public boolean accept(File dir, String filename) {
        this.dir = dir;
        this.filename = filename;
        return (filename.endsWith(".mp3"));
    }
}

public class Main extends ListActivity {

    private static final String SD_PATH = new String("/sdcard/");
    private List<String> songs = new ArrayList<String>();
    private MediaPlayer mp = new MediaPlayer();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updatePlaylist();


        Button stopPlay = (Button) findViewById(R.id.Stopbtn);
        stopPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
            }

        });
    }


    protected void OnListItemClick(ListView list, View view, int position, long id) {

        try {
            mp.reset();
            mp.setDataSource(SD_PATH + songs.get(position));
            mp.prepare();
            mp.start();
        } catch (IOException e) {
            Log.v(getString(R.string.app_name), e.getMessage());

        }
    }

    private void updatePlaylist() {
        File home = new File(SD_PATH);
        if (home.listFiles(new Mp3Filter()).length > 0) {
            for (File file : home.listFiles(new Mp3Filter())) {
                songs.add(file.getName());
            }
            ArrayAdapter<String> songList = new ArrayAdapter<String>(this, R.layout.song_item, songs);
            setListAdapter(songList);
        }

    }
}



