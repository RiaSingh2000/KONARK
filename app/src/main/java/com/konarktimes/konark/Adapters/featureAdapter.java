package com.konarktimes.konark.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.konarktimes.konark.DetailsActivity;
import com.konarktimes.konark.Model.Posts;
import com.konarktimes.konark.R;

import java.util.List;

public class featureAdapter extends RecyclerView.Adapter<featureAdapter.featureAdapterViewHolder> {
    Context context;
    List<Posts> posts;

    public featureAdapter(Context context, List<Posts> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public featureAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View v=inflater.inflate(R.layout.feature_layout,viewGroup,false);
        return new featureAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull featureAdapterViewHolder holder, int i){
        final Posts obj=posts.get(i);
        holder.title.setText(obj.getTitle());
        Glide.with(context).load(obj.getImage_url()).into(holder.img);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        Intent intent=new Intent(context, DetailsActivity.class);
        intent.putExtra("title",obj.getTitle());
        intent.putExtra("content",obj.getContent());
        intent.putExtra("image_url",obj.getImage_url());
        intent.putExtra("created_at",obj.getCreated_at());
        intent.putExtra("name",obj.getName());//Category
        intent.putExtra("summary",obj.getSummary());
        context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        if(posts.size()<=5)
        return posts.size();
        else
            return 5;
    }

    public class featureAdapterViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView title;
        RelativeLayout relativeLayout;

        public featureAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img);
            title=itemView.findViewById(R.id.title);
           relativeLayout=itemView.findViewById(R.id.relLayout);
        }
    }
}
