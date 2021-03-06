package com.example.newslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {


    public NewsAdapter(@NonNull Context context, int resource, @NonNull List<News> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        News news = getItem(position);
        ViewHolder viewHolder;

        if(convertView==null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_display,parent,false);
            viewHolder= new ViewHolder();
            viewHolder.textViewTitle=(TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.textViewAuthor=(TextView) convertView.findViewById(R.id.tvAuthor);
            viewHolder.textViewDate=(TextView) convertView.findViewById(R.id.tvDate);
            viewHolder.ivNews=(ImageView) convertView.findViewById(R.id.ivNewsImage);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textViewTitle.setText(news.title);
        viewHolder.textViewAuthor.setText(news.author);
        viewHolder.textViewDate.setText(news.publishedAt);
        Picasso.with(getContext()).load(news.urlToImage).into(viewHolder.ivNews);
        return convertView;
    }
    private static class ViewHolder{
        TextView textViewTitle;
        TextView textViewAuthor;
        TextView textViewDate;
        TextView textViewUrl;
        ImageView ivNews;


    }
}


