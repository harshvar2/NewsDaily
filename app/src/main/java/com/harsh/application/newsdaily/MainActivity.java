package com.harsh.application.newsdaily;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsData>>{

    /** Adapter for the list of earthquakes */
    private NewsAdapter mAdapter;

    private static final String REQUEST_URL = "https://newsapi.org/v2/top-headlines?country=in&apiKey=0a85e88440d543b6ab2adb907c7613dd";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ListView newsView=(ListView)findViewById(R.id.list_view);
        mAdapter=new NewsAdapter(this, new ArrayList<NewsData>());
        newsView.setAdapter(mAdapter);


       LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(0, null, this);




    }
    
    
    @Override
    public Loader<List<NewsData>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        Uri base = Uri.parse(REQUEST_URL);

        Uri.Builder uriBuilder = base.buildUpon();

        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("api-key", "test");

        return new NewsLoader(this, uriBuilder.toString());
    }



    @Override
    public void onLoadFinished(Loader<List<NewsData>> loader, List<NewsData> earthquakes) {
        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (earthquakes != null && !earthquakes.isEmpty()) {
            mAdapter.addAll(earthquakes);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsData>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

}