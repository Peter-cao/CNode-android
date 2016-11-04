package com.demo.cnode_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.menu.ExpandedMenuView;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ListView  topicsList;
    private TopicListAdapter topicsAdapter;
    private List<Topic>  dataList = new ArrayList<>();
    private int page = 1;
    private int pageSize = 20;
    private String tab = "";
    private SwipeRefreshLayout content_main;
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
        content_main.setProgressViewOffset(true,0,(int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        content_main.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getList(true);
            }
        });
        topicsList = (ListView) findViewById(R.id.topicsList);
        topicsList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
               if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    if(view.getLastVisiblePosition() == view.getCount()-1){
                        page++;
                        getList(false);
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
                String  topicId = dataList.get(position).getId();
                Intent intent = new Intent(MainActivity.this,TopicDetailActivity.class);
                intent.putExtra("topicId",topicId);
                startActivity(intent);
            }
        });
        topicsAdapter = new TopicListAdapter(this,dataList);
        topicsList.setAdapter(topicsAdapter);
        getList(true);
    }
    public void  getList(final boolean isRefresh){
        content_main.setRefreshing(true);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://cnodejs.org/api/v1/")
                .build();
        ApiService service = retrofit.create(ApiService.class);
        Call<TopicResponse> call =  service.getTopics(page,pageSize,tab);
        call.enqueue(new Callback<TopicResponse>() {
            @Override
            public void onResponse(Call<TopicResponse> call, Response<TopicResponse> response) {
                TopicResponse topicResponse = response.body();
                if(topicResponse.getSuccess()){
                    if(isRefresh){
                        dataList =topicResponse.getData();
                        topicsAdapter.setDataList(topicResponse.getData());
                    }else{
                        dataList.addAll(topicResponse.getData());
                        topicsAdapter.addDataList(topicResponse.getData());
                    }

                    topicsAdapter.notifyDataSetChanged();
                    content_main.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<TopicResponse> call, Throwable t) {
                Log.e("aaaa>>>>",t.getMessage());
            }
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.all) {
            page = 1;
            tab = "";
            getList(true);
        } else if (id == R.id.ask) {
            page = 1;
            tab = "ask";
            getList(true);
        } else if (id == R.id.share) {
            page = 1;
            tab = "share";
            getList(true);
        } else if (id == R.id.job) {
            page = 1;
            tab = "job";
            getList(true);
        } else if (id == R.id.good) {
            page = 1;
            tab = "good";
            getList(true);
        } else if (id == R.id.msg) {

        } else if (id == R.id.setting) {

        }else if(id == R.id.about){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
