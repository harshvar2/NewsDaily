package com.harsh.application.newsdaily;

import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsData>>{

    /** Adapter for the list of earthquakes */
    private NewsAdapter mAdapter;
    private ListView newsView;
    private TextView eTextView;

    private static final String REQUEST_URL = "https://content.guardianapis.com/search";
    
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        eTextView = findViewById(R.id.empty);

        ListView newsView= findViewById(R.id.list_view);
        mAdapter=new NewsAdapter(this, new ArrayList<NewsData>());
        newsView.setAdapter(mAdapter);
        newsView.setEmptyView(eTextView);
          ConnectivityManager CM = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        assert CM != null;
        NetworkInfo NI=CM.getActiveNetworkInfo();
        if (NI != null && NI.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(0, null, this);

        } else {

            eTextView.setText("No Internet Connection");
        }

        newsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                NewsData current = mAdapter.getItem(position);

                assert current != null;
                Uri news_Uri = Uri.parse(current.getWebURL());

                Intent website = new Intent(Intent.ACTION_VIEW, news_Uri);
                startActivity(website);
            }
        });


    }
    
    
    @Override
    public Loader<List<NewsData>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        Uri base = Uri.parse(REQUEST_URL);

        Uri.Builder uriBuilder = base.buildUpon();
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("api-key", "7f37f7be-1583-4af0-bfae-59e10d9dbab8");

        return new NewsLoader(this, uriBuilder.toString());
    }



    @Override
    public void onLoadFinished(Loader<List<NewsData>> loader, List<NewsData> newsData) {
        // Clear the adapter of previous earthquake data
        mAdapter.clear();
        eTextView.setText("No News found");
        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (newsData != null && !newsData.isEmpty()) {
            mAdapter.addAll(newsData);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsData>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

}