package com.demo.cnode_android.contract;

import com.demo.cnode_android.model.entity.Topic;

import java.util.List;


public interface MainContract {

    interface View  {
        void showLoading();
        void hideLoading();
        void loadMoreList(List<Topic> list);
        void refreshMoreList(List<Topic> list);
        void showError(String msg);

    }
    interface Presenter  {
        void loadMoreList();
        void refreshMoreList();
        void changeTab(String tab);
    }
}
