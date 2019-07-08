package com.example.luan.youtubeapp.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.example.luan.youtubeapp.R;
import com.example.luan.youtubeapp.adapter.AdapterVideo;
import com.example.luan.youtubeapp.api.YoutubeService;
import com.example.luan.youtubeapp.helper.RetrofitConfig;
import com.example.luan.youtubeapp.helper.YoutubeConfig;
import com.example.luan.youtubeapp.listener.RecyclerItemClickListener;
import com.example.luan.youtubeapp.model.Resultado;
import com.example.luan.youtubeapp.model.Item;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity{

    private RecyclerView recyclerVideos;
    private List<Item> items = new ArrayList<>();
    private Resultado resultado;
    private AdapterVideo adapterVideo;
    private MaterialSearchView searchView;

    private static String CHAVE_YOUTUBE_API;

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

        recyclerVideos = findViewById(R.id.recyclerVideos);
        searchView = findViewById(R.id.searchView);

        CHAVE_YOUTUBE_API = getString(R.string.google_youtube_key);
        retrofit = RetrofitConfig.getRetrofit();

        recuperarVideos("");

        //Configura métodos para SearchView
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recuperarVideos( query );
                return true;
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
                recuperarVideos("");
            }
        });

    }

    private void recuperarVideos(String pesquisa){

        String q = pesquisa.replaceAll(" ","+");

        YoutubeService youtubeService = retrofit.create( YoutubeService.class );
        youtubeService.recuperarVideos(
                "snippet","date","20", CHAVE_YOUTUBE_API, YoutubeConfig.CANAL_ID, q
        ).enqueue(new Callback<Resultado>() {
            @Override
            public void onResponse(Call<Resultado> call, Response<Resultado> response) {
                Log.d("resultado","resultado: " + response.toString());

                if(response.isSuccessful()){
                    resultado = response.body();
                    items = resultado.items;
                    configuraRecyclerView();
                }

            }

            @Override
            public void onFailure(Call<Resultado> call, Throwable t) {

            }
        });

    }

    public void configuraRecyclerView(){

        adapterVideo = new AdapterVideo(items,this);
        recyclerVideos.setHasFixedSize(true);
        recyclerVideos.setLayoutManager(new LinearLayoutManager(this));
        recyclerVideos.setAdapter(adapterVideo);

        //Configura evento de clique
        recyclerVideos.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerVideos, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Item item = items.get(position);
                        String idVideo = item.id.videoId;

                        Intent i = new Intent(MainActivity.this, PlayerActivity.class);
                        i.putExtra("idVideo", idVideo);
                        startActivity(i);


                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                })
        );
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
