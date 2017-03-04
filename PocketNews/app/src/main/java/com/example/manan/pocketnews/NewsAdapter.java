package com.example.manan.pocketnews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.R.attr.author;

/**
 * Created by Manan on 02-03-2017.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, ArrayList<News> news) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.layout_view, parent, false);
        }

        News currentNews = getItem(position);

        TextView headline = (TextView) convertView.findViewById(R.id.headline);
        headline.setText(currentNews.getHeadline().trim());

        Log.i("headline in adapter", currentNews.getHeadline());
        TextView content = (TextView) convertView.findViewById(R.id.content);
        content.setText((Html.fromHtml(currentNews.getContent()).toString().trim()));


        TextView author = (TextView) convertView.findViewById(R.id.author);
        author.setText("by - " + currentNews.getAuthor());

        ImageView thumbnail = (ImageView) convertView.findViewById(R.id.news_image);

        Picasso.with(getContext()).load(currentNews.getImageUrl()).into(thumbnail);


        return convertView;
    }
}
