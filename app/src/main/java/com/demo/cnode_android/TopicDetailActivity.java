package com.demo.cnode_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TopicDetailActivity extends AppCompatActivity {
    WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detail);
        mWebView = (WebView)findViewById(R.id.detail_webview);
        mWebView.getSettings().setSupportZoom(false);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        //mWebView.loadUrl("https://cnodejs.org/topic/"+getIntent().getStringExtra("topicId"));
        getDetail(getIntent().getStringExtra("topicId"));
    }
    public void getDetail(String topicId){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://cnodejs.org/api/v1/")
                .build();
        ApiService service = retrofit.create(ApiService.class);
        Call<ApiService.TopicDetailResponse> call =  service.getTopic(topicId);
        call.enqueue(new Callback<ApiService.TopicDetailResponse>() {
            @Override
            public void onResponse(Call<ApiService.TopicDetailResponse> call, Response<ApiService.TopicDetailResponse> response) {
                ApiService.TopicDetailResponse topicDetailResponse = response.body();
                if(topicDetailResponse.getSuccess()){
                    String detail = topicDetailResponse.getData().getContent();
                    String temp =   "<!DOCTYPE html>\n" +
                                    "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                                    "<head>\n" +
                                    "  <meta charset=\"utf-8\"/>\n" +
                                    "  <meta name='description' content='CNode：Node.js专业中文社区'>\n" +
                                    "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"/>\n" +
                                    "  <meta name=\"referrer\" content=\"always\">\n" +
                                    "  <meta name=\"author\" content=\"EDP@TaoBao\" />\n" +
                                    "  <meta property=\"wb:webmaster\" content=\"617be6bd946c6b96\" />\n" +
                                    "  <link title=\"RSS\" type=\"application/rss+xml\" rel=\"alternate\" href=\"/rss\"/>\n" +
                                    "  <link rel=\"icon\" href=\"//o4j806krb.qnssl.com/public/images/cnode_icon_32.png\" type=\"image/x-icon\"/>\n" +
                                    "  <link rel=\"stylesheet\" href=\"//o4j806krb.qnssl.com/public/stylesheets/index.min.3c50e680.min.css\" media=\"all\" />\n" +
                                    "  <script src=\"//o4j806krb.qnssl.com/public/index.min.aeb155e1.min.js\"></script>\n" +
                                    "  <title>分享我用cnode社区api做微信小应用的入门过程 - CNode技术社区</title>"+
                                    "  <meta content=\"_csrf\" name=\"csrf-param\">\n" +
                                    "</head>\n" +
                                    "<body>"+detail+
                                    "</body>\n" +
                                    "</html>";

                    mWebView.loadDataWithBaseURL("",temp,"text/html","utf-8",null);
                }else{

                }
            }

            @Override
            public void onFailure(Call<ApiService.TopicDetailResponse> call, Throwable t) {

            }
        });
    }
}
