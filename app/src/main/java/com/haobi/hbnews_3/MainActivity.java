package com.haobi.hbnews_3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.haobi.hbnews_3.gson.Data;
import com.haobi.hbnews_3.gson.News;
import com.haobi.hbnews_3.gson.Result;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.R.id.list;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendRequestWithOKHttp();
    }

    private void sendRequestWithOKHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://v.juhe.cn/toutiao/index?type=top&key=468a538795ca302846f994e7559df8a7")
                            .build();
                    Response response = null;
                    response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d("测试：", responseData);
                    parseJsonWithGson(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJsonWithGson(String jsonData){
        Gson gson = new Gson();
        News news = gson.fromJson(jsonData, News.class);
        List<Data> list = news.getResult().getData();
        for (int i=0; i<list.size(); i++){
            String title = list.get(i).getTitle();
            String date = list.get(i).getDate();
            String author_name = list.get(i).getAuthorName();
            String content_url = list.get(i).getUrl();
            String pic_url = list.get(i).getThumbnail_pic_s();
            System.out.println("标题："+title);
            System.out.println("日期："+date);
            System.out.println("作者："+author_name);
            System.out.println("网址："+content_url);
            System.out.println("图片："+pic_url);
        }
    }
}
