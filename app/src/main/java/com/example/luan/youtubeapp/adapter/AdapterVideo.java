package com.example.luan.youtubeapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luan.youtubeapp.R;
import com.example.luan.youtubeapp.model.Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by @luanfssilva on 06/07/2019.
 */


public class AdapterVideo extends RecyclerView.Adapter<AdapterVideo.MyViewHolder> {

    private List<Item> items = new ArrayList<>();
    private Context context;

    public AdapterVideo(List<Item> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_video,viewGroup, false);
        return new AdapterVideo.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        Item item = items.get(i);
        myViewHolder.titulo.setText(item.snippet.title);

        String url = item.snippet.thumbnails.high.url;
        Picasso.get().load(url).into(myViewHolder.capa);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titulo;
        ImageView capa;
        //TextView descricao;
        //TextView data;

        public MyViewHolder(View itemView){
            super(itemView);

            titulo = itemView.findViewById(R.id.textTitulo);
            capa = itemView.findViewById(R.id.imageCapa);
        }


    }


}
