package com.demo.cnode_android;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import static com.demo.cnode_android.R.id.imageView;

/**
 * Created by ck on 2016/10/21.
 */

public class TopicListAdapter extends BaseAdapter {
    private Context context;
    private List<Topic> dataList;
    private LruCache<String, Bitmap> mMemoryCaches;


    public void addDataList(List<Topic> dataList) {
        this.dataList.addAll(dataList) ;
    }


    public void setDataList(List<Topic> dataList) {
        this.dataList = dataList;
    }

    private LayoutInflater mInflater;

    static class ViewHolder {
        ImageView author_image;
        TextView author_name;
        LinearLayout top;
        LinearLayout good;
        TextView last_reply;
        TextView topicLitle;
        TextView create_at;
        TextView visit_count;
        TextView reply_count;
    }

    public TopicListAdapter(Context context, List<Topic> list) {
        this.context = context;
        this.dataList = list;
        mInflater = LayoutInflater.from(context);

        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSizes = maxMemory / 5;
        mMemoryCaches = new LruCache<String,Bitmap>(cacheSizes){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount();
            }
        };
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    ;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.topic_item, null);
            viewHolder.author_image = (ImageView) convertView.findViewById(R.id.author_image);
            viewHolder.author_name = (TextView) convertView.findViewById(R.id.author_name);
            viewHolder.top = (LinearLayout) convertView.findViewById(R.id.top);
            viewHolder.good = (LinearLayout) convertView.findViewById(R.id.good);
            viewHolder.last_reply = (TextView) convertView.findViewById(R.id.last_reply);
            viewHolder.topicLitle = (TextView) convertView.findViewById(R.id.topicLitle);
            viewHolder.create_at = (TextView) convertView.findViewById(R.id.create_at);
            viewHolder.visit_count = (TextView) convertView.findViewById(R.id.visit_count);
            viewHolder.reply_count = (TextView) convertView.findViewById(R.id.reply_count);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Topic topic = dataList.get(position);
        viewHolder.author_name.setText(topic.getAuthor().getLoginname());
        viewHolder.top.setVisibility(topic.isTop() ? View.VISIBLE : View.GONE);
        viewHolder.good.setVisibility(topic.isGood() ? View.VISIBLE : View.GONE);
        viewHolder.last_reply.setText(Utils.getDateDiff(topic.getLast_reply_at()));
        viewHolder.topicLitle.setText(topic.getTitle());
        viewHolder.create_at.setText(Utils.formatDate(topic.getCreate_at()));
        viewHolder.visit_count.setText(topic.getVisit_count() + "");
        viewHolder.reply_count.setText(topic.getReply_count() + "");

        viewHolder.author_image.setImageResource(R.mipmap.ic_launcher);

        String authorImageUrl = topic.getAuthor().getAvatar_url().contains("http") ? topic.getAuthor().getAvatar_url() : "https:" + topic.getAuthor().getAvatar_url();
        viewHolder.author_image.setTag(authorImageUrl);
        //showImageByAsyncTask(authorImageUrl, viewHolder.author_image);
        Picasso.with(context).load(authorImageUrl).into(viewHolder.author_image);
        return convertView;
    }

   /* public void showImageByAsyncTask(String url, ImageView imageView) {
        Bitmap bitmap = mMemoryCaches.get(url);
        if(bitmap == null){
            new AuthorImageLoader(url, imageView).execute(url);
        }else{
            imageView.setImageBitmap(bitmap);
        }

    }*/
}
