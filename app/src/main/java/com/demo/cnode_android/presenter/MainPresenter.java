package com.demo.cnode_android.presenter;

import android.content.Context;

import com.demo.cnode_android.contract.MainContract;
import com.demo.cnode_android.model.api.ApiClient;
import com.demo.cnode_android.model.entity.TopicResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainPresenter implements MainContract.Presenter {
    private MainContract.View mMainView;
    private int page = 1;
    private int pageSize = 20;
    private String tab = "";

    public MainPresenter(MainContract.View context) {
        mMainView = context;
    }

    @Override
    public void loadMoreList() {
        page ++;
        Call<TopicResponse> call = ApiClient.service.getTopics(page, pageSize, tab);
        call.enqueue(new Callback<TopicResponse>() {
            @Override
            public void onResponse(Call<TopicResponse> call, Response<TopicResponse> response) {
                TopicResponse topicResponse = response.body();
                if (topicResponse.getSuccess()) {
                    mMainView.loadMoreList(topicResponse.getData());
                } else {
                    mMainView.showError("接口异常");
                }
            }

            @Override
            public void onFailure(Call<TopicResponse> call, Throwable t) {
                mMainView.showError("网络异常");
            }
        });
    }

    @Override
    public void refreshMoreList() {
        page = 1;
        Call<TopicResponse> call = ApiClient.service.getTopics(page, pageSize, tab);
        call.enqueue(new Callback<TopicResponse>() {
            @Override
            public void onResponse(Call<TopicResponse> call, Response<TopicResponse> response) {
                TopicResponse topicResponse = response.body();
                if (topicResponse.getSuccess()) {
                    mMainView.refreshMoreList(topicResponse.getData());
                } else {
                    mMainView.showError("接口异常");
                }
            }

            @Override
            public void onFailure(Call<TopicResponse> call, Throwable t) {
                mMainView.showError("网络异常");
            }
        });
    }

    @Override
    public void changeTab(String tab) {
        this.tab = tab;
        mMainView.showLoading();
        refreshMoreList();
    }
}
