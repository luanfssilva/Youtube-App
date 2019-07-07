package com.example.luan.youtubeapp.activitys;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.luan.youtubeapp.R;
import com.example.luan.youtubeapp.adapter.AdapterVideo;
import com.example.luan.youtubeapp.api.YoutubeService;
import com.example.luan.youtubeapp.helper.RetrofitConfig;
import com.example.luan.youtubeapp.helper.YoutubeConfig;
import com.example.luan.youtubeapp.model.Resultado;
import com.example.luan.youtubeapp.model.Video;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity{

    private RecyclerView recyclerVideos;
    private List<Video> videos = new ArrayList<>();
    private AdapterVideo adapterVideo;
    private MaterialSearchView searchView;

    private String chave_youtube_api;

    //Retrofit
    private Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Configura toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Youtube App");
        setSupportActionBar(toolbar);

        searchView = findViewById(R.id.searchView);

        chave_youtube_api = getString(R.string.google_youtube_key);
        retrofit = RetrofitConfig.getRetrofit();

        //Configura RecyclerView
        recyclerVideos = findViewById(R.id.recyclerVideos);
        //Log.i("Chave", "onCreate: " + CHAVE_YOUTUBE_API);
        recuperarVideos();
        adapterVideo = new AdapterVideo(videos,this);
        recyclerVideos.setHasFixedSize(true);
        recyclerVideos.setLayoutManager(new LinearLayoutManager(this));
        recyclerVideos.setAdapter(adapterVideo);

        //Configura métodos para SearchView
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

            }
        });


    }

    private void recuperarVideos(){

        YoutubeService youtubeService = retrofit.create( YoutubeService.class );
        youtubeService.recuperarVideos(
                "snippet","date","20", chave_youtube_api, YoutubeConfig.CANAL_ID
        ).enqueue(new Callback<Resultado>() {
            @Override
            public void onResponse(Call<Resultado> call, Response<Resultado> response) {
                Log.d("resultado","resultado: " + response.toString());
            }

            @Override
            public void onFailure(Call<Resultado> call, Throwable t) {

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.menu_search);
        searchView.setMenuItem( item );
        return true;
    }
}
