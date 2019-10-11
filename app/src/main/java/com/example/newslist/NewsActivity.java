package com.example.newslist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {
    ListView newsDisplay;
    ArrayList<News> newsList =null;
    News news;
    Source source;
    String SourceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        newsDisplay = findViewById(R.id.lw_newsDiplay);

        //final Bundle fromMain =getIntent().getExtras();

        //String SourceId = fromMain.getString("ID");
        Intent i = getIntent();
        SourceId=i.getExtras().getString("ids");


        if(SourceId!=null)
        {
            if(isConnected())
            {
                Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
                Log.d("DEMO", SourceId);
                new GetNewsData().execute("https://newsapi.org/v2/top-headlines?sources="+SourceId+"&apiKey=f820982a73524d00bf2c0870568c7706");
            }
            else
            {
                Toast.makeText(this, "Not Connected", Toast.LENGTH_SHORT).show();
            }

        }

    }
    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }

    private class GetNewsData extends AsyncTask<String, Void, ArrayList<News>>
    {

        @Override
        protected ArrayList<News> doInBackground(String... strings) {
            HttpURLConnection connection = null;
            ArrayList<News> result = new ArrayList<>();
            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String json = IOUtils.toString(connection.getInputStream(), "UTF8");

                    JSONObject root = new JSONObject(json);
                    JSONArray sources = root.getJSONArray("articles");

                    for (int i=0;i<sources.length();i++) {

                        JSONObject newsJson = sources.getJSONObject(i);

                        news = new News();
                        source=new Source();
                        news.title=newsJson.getString("title");
                        news.author=newsJson.getString("author");
                        news.publishedAt=newsJson.getString("publishedAt");
                        news.url=newsJson.getString("url");
                        news.urlToImage=newsJson.getString("urlToImage");
                        //news.source=source;
                        result.add(news);

                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(final ArrayList<News> result) {

            if(result.size()>0)
            {
                newsList= result;
                Log.d("News", newsList.toString());
                NewsAdapter adapter = new NewsAdapter(NewsActivity.this, R.layout.news_display, newsList);
                newsDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        News n = result.get(position);

                        Intent i = new Intent(NewsActivity.this, WebViewURL.class);

                        i.putExtra("URL", n.url);
                        startActivity(i);
                    }
                });

                newsDisplay.setAdapter(adapter);
            }
            else
            {
                Log.d("DEMO", "No data");
            }


        }
    }
}
