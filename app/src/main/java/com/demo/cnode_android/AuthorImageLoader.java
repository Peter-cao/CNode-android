package com.demo.cnode_android;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ck on 2016/10/22.
 */

public class AuthorImageLoader extends AsyncTask <String,Void,Bitmap>{
    private ImageView mImageView;
    private String mUrl;

    public AuthorImageLoader( String mUrl,ImageView mImageView) {
        this.mImageView = mImageView;
        this.mUrl = mUrl;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String url = params[0];
        Bitmap bitmap = getBitmapFromUrl(params[0]);
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if(mImageView.getTag().equals(mUrl)){
            mImageView.setImageBitmap(bitmap);
        }
    }
    public Bitmap getBitmapFromUrl(String url){
        Bitmap bitmap;
        InputStream is = null;
        try{
            URL mUrl = new URL(url);
            HttpURLConnection mHttpURLConnection = (HttpURLConnection) mUrl.openConnection();
            is = new BufferedInputStream(mHttpURLConnection.getInputStream());
            bitmap = BitmapFactory.decodeStream(is);
            mHttpURLConnection.disconnect();
            return  bitmap;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
