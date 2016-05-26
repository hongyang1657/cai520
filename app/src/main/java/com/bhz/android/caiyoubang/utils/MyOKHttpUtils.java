package com.bhz.android.caiyoubang.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
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
    int id;
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

    public void doSearch(String url,String titleurl){
        Request request=new Request.Builder().url(url+"?menu="+titleurl+"&dtype=&pn=&rn=&albums=&key=2410b10e88e355c49c614d422dd19956").build();
        this.request=request;
    }

    public void dogetID(String url, int id) {
        this.id=id;
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

    public void getDrawable(String url,final OKHttpHelper helper){
        Request request=new Request.Builder().url(url).build();
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
                InputStream pic_in=response.body().byteStream();
                Bitmap bm= BitmapFactory.decodeStream(pic_in);
                final BitmapDrawable drawable=new BitmapDrawable(bm);
                if(helper!=null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            helper.getDrawable(drawable);
                        }
                    });
                }
            }
        });
    }

    public void search(final OKHttpHelper helper){
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
                String main=response.body().string();
                try {
                    JSONObject object=new JSONObject(main);
                    JSONObject result=object.getJSONObject("result");
                    JSONArray data=result.getJSONArray("data");
                    JSONObject content=data.getJSONObject(0);
                    final String menuname=content.getString("title");
                    final String menutips=content.getString("tags");
                    final String menuabstract=content.getString("imtro");
                    String show1=content.getString("ingredients");
                    String show2=content.getString("burden");
                    final String menustuff=show1+","+show2;
                    JSONArray albums=content.getJSONArray("albums");
                    final String menuimageurl=albums.getString(0);
                    JSONArray steps=content.getJSONArray("steps");
                    final String[] imalist=new String[steps.length()];
                    final String[] detaillist=new String[steps.length()];
                    for(int i=0;i<steps.length();i++){
                        JSONObject details=steps.getJSONObject(i);
                        imalist[i]=details.getString("img");
                        detaillist[i]=details.getString("step");
                    }
                    if(helper!=null){
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                helper.getdetail(menuname,menuabstract,menustuff,menutips,menuimageurl,
                                        imalist,detaillist);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }



    private String appendContent(int id) {
        String sid = "?id=" + id + "&dtype=&key=2410b10e88e355c49c614d422dd19956";
        return sid;
    }

    public interface OKHttpHelper{
        void OnFailure(String s);
        void OnResponse(String message);
        void getDrawable(Drawable drawable);
        void getdetail(String menuname,String menuabstract,
                       String menustuff,String menutips,String menuimagehead,
                       String[] imalist,String[] detailist);
    }
}
