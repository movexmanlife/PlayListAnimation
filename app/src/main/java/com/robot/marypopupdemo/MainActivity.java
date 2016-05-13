package com.robot.marypopupdemo;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listview;
    List<User> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (ListView) findViewById(R.id.listview);

        datas.add(new User(121, "jack"));
        datas.add(new User(123, "rose"));
        datas.add(new User(124, "ann"));
        datas.add(new User(125, "now"));
        datas.add(new User(126, "jacc"));
        datas.add(new User(127, "mann"));
        datas.add(new User(128, "jack1"));
        datas.add(new User(129, "rose1"));
        datas.add(new User(130, "ann1"));
        datas.add(new User(131, "now1"));
        datas.add(new User(132, "jacc1"));
        datas.add(new User(122, "mann1"));
        final MyAdapter myAdapter = new MyAdapter(this, datas);
        listview.setAdapter(myAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = datas.get(position);
                myAdapter.setCurrentId(user.userId);
                myAdapter.notifyDataSetChanged();
            }
        });
    }

    public class MyAdapter extends BaseAdapter {

        private List<User> datas;
        private Context context;
        private int currentId;
        private AnimationDrawable mPeakOneAnimation;

        public MyAdapter(Context context, List<User> datas) {
            this.context = context;
            this.datas = datas;
        }

        public void setCurrentId(int currentId) {
            this.currentId = currentId;
        }

        public int getCurrentId() {
            return this.currentId;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }


        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView  = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.image = (ImageView)convertView.findViewById(R.id.play_img);
                viewHolder.name = (TextView)convertView.findViewById(R.id.name_tv);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            User user = datas.get(position);
            viewHolder.name.setText(user.name);

            if (user.userId == currentId) {
                if (!user.isPlaying) {
                    viewHolder.image.setImageResource(R.drawable.peak_meter_1);
                    mPeakOneAnimation = (AnimationDrawable) viewHolder.image.getDrawable();
                    mPeakOneAnimation.start();
                }
                user.isPlaying = true;
            } else {
                if (user.isPlaying) {
                    if (mPeakOneAnimation != null && mPeakOneAnimation.isRunning()) {
                        mPeakOneAnimation.stop();
                    }
                }
                user.isPlaying = false;
                viewHolder.image.setImageResource(0);
            }
            return convertView;
        }

        class ViewHolder {
            ImageView image;
            TextView name;
        }
    }
}

/**
 * 注意：
 * （1）使用ImageView.setImageResource(0)来清除背景，如果使用setBackgroudDrawable(null)，不能达到目的；
 * （2）特别注意一点就是这里的，View复用问题，简单的处理了。如果隐藏0这个item的动画，重新拉出来，则这个动画是重新开始的动画，并非原先的动画。
 *
 * 上面基本的效果不错了，但是有点就是点击过快的时候，有时候AnimationDrawable只显示第一帧动画效果。
 */
