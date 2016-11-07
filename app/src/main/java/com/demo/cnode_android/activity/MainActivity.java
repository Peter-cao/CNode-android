package com.demo.cnode_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.demo.cnode_android.contract.MainContract;
import com.demo.cnode_android.R;
import com.demo.cnode_android.model.entity.Topic;
import com.demo.cnode_android.adapter.TopicListAdapter;
import com.demo.cnode_android.presenter.MainPresenter;

import java.util.List;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainContract.View {
    private ListView  topicsList;
    private TopicListAdapter topicsAdapter;
    private SwipeRefreshLayout content_main;
    private MainPresenter mMainPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        content_main = (SwipeRefreshLayout)findViewById(R.id.content_main);
        topicsList = (ListView) findViewById(R.id.topicsList);

        content_main.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMainPresenter.refreshMoreList();
            }
        });

        topicsList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
               if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    if(view.getLastVisiblePosition() == view.getCount()-1){
                        mMainPresenter.loadMoreList();
                    }
               }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        topicsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String  topicId = topicsAdapter.getItem(position).getId();
                Intent intent = new Intent(MainActivity.this,TopicDetailActivity.class);
                intent.putExtra("topicId",topicId);
                startActivity(intent);
            }
        });
        topicsAdapter = new TopicListAdapter(this);
        topicsList.setAdapter(topicsAdapter);
        mMainPresenter = new MainPresenter(this);

        showLoading();
        mMainPresenter.refreshMoreList();
    }

    @Override
    public void showLoading() {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                content_main.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideLoading() {
        content_main.setRefreshing(false);
    }

    @Override
    public void loadMoreList(List<Topic> list) {
        topicsAdapter.addDataList(list);
        topicsAdapter.notifyDataSetChanged();
        hideLoading();
    }

    @Override
    public void refreshMoreList(List<Topic> list) {
        topicsAdapter.setDataList(list);
        topicsAdapter.notifyDataSetChanged();
        hideLoading();
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.all) {
            mMainPresenter.changeTab("");
        } else if (id == R.id.ask) {
            mMainPresenter.changeTab("ask");
        } else if (id == R.id.share) {
            mMainPresenter.changeTab("share");
        } else if (id == R.id.job) {
            mMainPresenter.changeTab("job");
        } else if (id == R.id.good) {
            mMainPresenter.changeTab("good");
        } else if (id == R.id.msg) {

        } else if (id == R.id.setting) {

        }else if(id == R.id.about){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
