package com.example.manan.pocketnews;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.button;
import static android.support.design.widget.BaseTransientBottomBar.LENGTH_INDEFINITE;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    static int show_hide_container_flag = 0;
    private String News_Request_Url = "https://content.guardianapis.com/search?section=world&page-size=20&show-fields=headline,byline,shortUrl,publication,standfirst,thumbnail&order-by=newest&api-key=06f23686-8cfc-4620-a506-e6cc5ba78ebf";
    private CoordinatorLayout coordinatorLayout;
    private NewsAdapter mNewsAdapter;
    private LinearLayout buttonContainer;
    private TextView noInternet;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.snackbar);

        buttonContainer = (LinearLayout) findViewById(R.id.button_container);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);

        noInternet = (TextView) findViewById(R.id.no_internet);
        noInternet.setText("No Internet Connection :(");

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            Snackbar.make(coordinatorLayout, "Internet Connected", Snackbar.LENGTH_LONG).show();
            Log.i("connection", "connection created");
            performSearch();
        } else {
            noInternet.setVisibility(View.VISIBLE);
            Snackbar.make(coordinatorLayout, "Intenet Connection Lost", LENGTH_INDEFINITE).setAction("SETTINGS", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Settings.ACTION_SETTINGS);
                    startActivity(intent);
                }
            }).setActionTextColor(ContextCompat.getColor(this, R.color.white)).show();
        }

        final Button categories = (Button) findViewById(R.id.categories);
        categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (show_hide_container_flag == 0) {
                    buttonContainer.setVisibility(View.VISIBLE);
                    show_hide_container_flag = 1;
                } else {
                    buttonContainer.setVisibility(View.GONE);
                    show_hide_container_flag = 0;
                }
            }
        });
        final Button button1 = (Button) findViewById(R.id.buttonPanel);
        final Button button2 = (Button) findViewById(R.id.buttonPane2);
        final Button button3 = (Button) findViewById(R.id.buttonPane3);
        final Button button4 = (Button) findViewById(R.id.buttonPane4);
        final Button button5 = (Button) findViewById(R.id.buttonPane5);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = button1.getText().toString();
                categories.setText(text);
                buttonContainer.setVisibility(View.GONE);
                show_hide_container_flag = 0;
                News_Request_Url = "https://content.guardianapis.com/search?section=football&page-size=20&show-fields=headline,byline,shortUrl,publication,standfirst,thumbnail&order-by=newest&api-key=06f23686-8cfc-4620-a506-e6cc5ba78ebf";
                performSearch();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = button2.getText().toString();
                categories.setText(text);
                buttonContainer.setVisibility(View.GONE);
                show_hide_container_flag = 0;
                News_Request_Url = "https://content.guardianapis.com/search?section=culture&page-size=20&show-fields=headline,byline,shortUrl,publication,standfirst,thumbnail&order-by=newest&api-key=06f23686-8cfc-4620-a506-e6cc5ba78ebf";
                performSearch();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = button3.getText().toString();
                categories.setText(text);
                buttonContainer.setVisibility(View.GONE);
                show_hide_container_flag = 0;
                News_Request_Url = "https://content.guardianapis.com/search?section=fashion&page-size=20&show-fields=headline,byline,shortUrl,publication,standfirst,thumbnail&order-by=newest&api-key=06f23686-8cfc-4620-a506-e6cc5ba78ebf";
                performSearch();
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = button4.getText().toString();
                categories.setText(text);
                buttonContainer.setVisibility(View.GONE);
                show_hide_container_flag = 0;
                News_Request_Url = "https://content.guardianapis.com/search?section=technology&page-size=20&show-fields=headline,byline,shortUrl,publication,standfirst,thumbnail&order-by=newest&api-key=06f23686-8cfc-4620-a506-e6cc5ba78ebf";
                performSearch();
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = button5.getText().toString();
                categories.setText(text);
                buttonContainer.setVisibility(View.GONE);
                show_hide_container_flag = 0;
                News_Request_Url = "https://content.guardianapis.com/search?section=business&page-size=20&show-fields=headline,byline,shortUrl,publication,standfirst,thumbnail&order-by=newest&api-key=06f23686-8cfc-4620-a506-e6cc5ba78ebf";
                performSearch();
            }
        });


        ListView newsList = (ListView) findViewById(R.id.list_item);
        mNewsAdapter = new NewsAdapter(this, new ArrayList<News>());
        newsList.setAdapter(mNewsAdapter);

        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                News currnetNews = mNewsAdapter.getItem(position);

                Uri NewsUri = Uri.parse(currnetNews.getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, NewsUri);
                startActivity(websiteIntent);
            }
        });
    }

    public void performSearch() {
        progressBar.setVisibility(View.VISIBLE);
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(0, null, NewsActivity.this);
        loaderManager.restartLoader(0, null, NewsActivity.this);
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this, News_Request_Url);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        mNewsAdapter.clear();

        if (news != null && !news.isEmpty()) {
            mNewsAdapter.addAll(news);
        }
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mNewsAdapter.clear();

    }
}
