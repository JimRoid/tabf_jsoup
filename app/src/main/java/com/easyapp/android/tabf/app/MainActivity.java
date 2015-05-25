package com.easyapp.android.tabf.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.orhanobut.logger.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    private ListView lv;
    private String url = "http://service.tabf.org.tw/Exam/NewsList.aspx";

    private String title;
    private Elements date;
    private Elements words;

    private Adapter_News adapter_news;
    private ArrayList<News> data = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.lv);
        adapter_news = new Adapter_News(this);
        adapter_news.setData(data);
        lv.setAdapter(adapter_news);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startUri(data.get(position).href);
            }
        });

        new Thread(runnable).start();
    }

    private void startUri(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                Document doc = Jsoup.connect(url).get();
                title = doc.title();
                date = doc.getElementsByClass("NewsDate");
                words = doc.getElementsByClass("NewsText");

                Logger.d(words.size() + "");
                Logger.d(words.get(0).toString() + "");
                Logger.d(words.get(0).select("a[href]").get(0).absUrl("href") + "");
                Logger.d(words.get(0).text() + "");
                Logger.d(words.get(0).absUrl("[href]") + "");
                Logger.d(words.get(0).attr("[abs:href]") + "");
                Logger.d(words.get(0).attr("[abs:href]") + "");
                for (int i = 0; i < words.size(); i++) {
                    data.add(new News(date.get(i).text(), words.get(i).text(), words.get(0).select("a[href]").get(0).absUrl("href")));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(0);
        }
    };

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            getSupportActionBar().setTitle(title);
            adapter_news.notifyDataSetChanged();
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
