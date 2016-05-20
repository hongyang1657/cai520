package com.bhz.android.caiyoubang.utils;

import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/5/17.
 */
public class MyOKHttpUtils {
    OkHttpClient client = new OkHttpClient();
    Request request;
    private static MyOKHttpUtils utils;
    android.os.Handler handler = new android.os.Handler();

    private MyOKHttpUtils() {

    }

    public static MyOKHttpUtils getinit() {
        if (utils == null) {
            utils = new MyOKHttpUtils();
            return utils;
        }
        return utils;
    }

    public void dogetID(String url, int id) {
        Request.Builder builder = new Request.Builder();
        Request request=builder.url(url + appendContent(id)).build();
        this.request=request;
    }

    public void excute(final OKHttpHelper helper){

        Call call=client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(helper!=null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            helper.OnFailure("获取网络数据失败");
                        }
                    });
                }

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
              if(helper!=null){
                  final String message=response.body().string();
                  handler.post(new Runnable() {
                      @Override
                      public void run() {
                           helper.OnResponse(message);
                      }
                  });
              }

            }
        });
    }

    private String appendContent(int id) {
        String sid = "?id=" + id + "&dtype=&key=07af1522a76db61e30a46ef9b1d7ef50";
        return sid;
    }

    public interface OKHttpHelper{
        void OnFailure(String s);
        void OnResponse(String message);
        void changeDrawable(String url);
    }
}
