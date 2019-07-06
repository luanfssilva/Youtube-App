package com.example.luan.youtubeapp.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.luan.youtubeapp.R;
import com.example.luan.youtubeapp.adapter.AdapterVideo;
import com.example.luan.youtubeapp.model.Video;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private RecyclerView recyclerVideos;
    private List<Video> videos = new ArrayList<>();
    private AdapterVideo adapterVideo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Configura toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Youtube App");

        //Configura RecyclerView
        recyclerVideos = findViewById(R.id.recyclerVideos);
        recuperarVideos();
        adapterVideo = new AdapterVideo(videos,this);
        recyclerVideos.setHasFixedSize(true);
        recyclerVideos.setLayoutManager(new LinearLayoutManager(this));
        recyclerVideos.setAdapter(adapterVideo);

    }

    private void recuperarVideos(){

        Video video1 = new Video();
        video1.setTitulo("Video1 legal");
        videos.add( video1 );

        Video video2 = new Video();
        video2.setTitulo("Video2 legal");
        videos.add( video2 );

    }

}
