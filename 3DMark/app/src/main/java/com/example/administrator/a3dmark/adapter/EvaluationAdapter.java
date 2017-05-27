package com.example.administrator.a3dmark.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.bean.Evaluation_Bean;
import com.loopj.android.image.SmartImageView;

import java.util.List;

/**
 * Created by Administrator on 2017/3/28.
 */
public class EvaluationAdapter extends BaseAdapter {
    List<Evaluation_Bean> list;
    Context context;

    public EvaluationAdapter(List<Evaluation_Bean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Evaluation_Holder evaluation_holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_boutique_evaluation, null);
            evaluation_holder = new Evaluation_Holder();
            evaluation_holder.image_boutuque_evaluation_1 = (ImageView) convertView.findViewById(R.id.image_boutuque_evaluation_1);
            evaluation_holder.image_boutuque_evaluation_2 = (ImageView) convertView.findViewById(R.id.image_boutuque_evaluation_2);
            evaluation_holder.image_boutuque_evaluation_3 = (ImageView) convertView.findViewById(R.id.image_boutuque_evaluation_3);
                 evaluation_holder.ll_evaluation_images = (LinearLayout) convertView.findViewById(R.id.ll_evaluation_images);
            evaluation_holder.tv_boutuque_evaluation_color = (TextView) convertView.findViewById(R.id.tv_boutuque_evaluation_color);
            evaluation_holder.tv_boutuque_evaluation_head = (SmartImageView) convertView.findViewById(R.id.tv_boutuque_evaluation_head);
            evaluation_holder.tv_boutuque_evaluation_main = (TextView) convertView.findViewById(R.id.tv_boutuque_evaluation_main);
            evaluation_holder.tv_boutuque_evaluation_name = (TextView) convertView.findViewById(R.id.tv_boutuque_evaluation_name);
            evaluation_holder.tv_boutuque_evaluation_size = (TextView) convertView.findViewById(R.id.tv_boutuque_evaluation_size);
            evaluation_holder.tv_boutuque_evaluation_time = (TextView) convertView.findViewById(R.id.tv_boutuque_evaluation_time);
            //追加评价
            evaluation_holder.tv_evaluation_add = (TextView) convertView.findViewById(R.id.tv_evaluation_add);
            evaluation_holder.tv_boutuque_evaluation_add_main = (TextView) convertView.findViewById(R.id.tv_boutuque_evaluation_add_main);
            evaluation_holder.ll_evaluation_images_add = (LinearLayout) convertView.findViewById(R.id.ll_evaluation_images_add);
            evaluation_holder.image_boutuque_evaluation_add_1 = (ImageView) convertView.findViewById(R.id.image_boutuque_evaluation_add_1);
            evaluation_holder.image_boutuque_evaluation_add_2 = (ImageView) convertView.findViewById(R.id.image_boutuque_evaluation_add_2);
            evaluation_holder.image_boutuque_evaluation_add_3 = (ImageView) convertView.findViewById(R.id.image_boutuque_evaluation_add_3);
            evaluation_holder.ll_evaluation_add = (LinearLayout) convertView.findViewById(R.id.ll_evaluation_add);
            //商家回复
            evaluation_holder.ll_evaluation_reply = (LinearLayout) convertView.findViewById(R.id.ll_evaluation_reply);
            evaluation_holder.tv_evaluation_reply = (TextView) convertView.findViewById(R.id.tv_evaluation_reply);
            evaluation_holder.tv_boutuque_evaluation_reply_main = (TextView) convertView.findViewById(R.id.tv_boutuque_evaluation_reply_main);
            evaluation_holder.tv_reply_time = (TextView) convertView.findViewById(R.id.tv_reply_time);
            evaluation_holder.tv_add_time = (TextView) convertView.findViewById(R.id.tv_add_time);
            convertView.setTag(evaluation_holder);
        } else if (convertView != null) {
            evaluation_holder = (Evaluation_Holder) convertView.getTag();
        }

        Log.d("size==========", list.size() + "");
        Glide.with(context).load(list.get(position).getHeadimg()).centerCrop().placeholder(R.mipmap.touxiang_default).into(evaluation_holder.tv_boutuque_evaluation_head);
        evaluation_holder.tv_boutuque_evaluation_time.setText(list.get(position).getTime_evaluation());
        evaluation_holder.tv_boutuque_evaluation_size.setText("尺寸：" + list.get(position).getSize_evaluation());
        evaluation_holder.tv_boutuque_evaluation_color.setText("颜色：" + list.get(position).getColor_evaluation());
        evaluation_holder.tv_boutuque_evaluation_main.setText(list.get(position).getContent_evaluation());
        evaluation_holder.tv_boutuque_evaluation_name.setText(list.get(position).getEvaluation_name());
        //展示图片
        if (list.get(position).getImage_evaluation_3() != null) {
            Glide.with(context).load(list.get(position).getImage_evaluation_1()).centerCrop()
                    .placeholder(R.mipmap.default_image_goods).into(evaluation_holder.image_boutuque_evaluation_1);
            Glide.with(context).load(list.get(position).getImage_evaluation_2()).centerCrop()
                    .placeholder(R.mipmap.default_image_goods).into(evaluation_holder.image_boutuque_evaluation_2);
            Glide.with(context).load(list.get(position).getImage_evaluation_3()).centerCrop()
                    .placeholder(R.mipmap.default_image_goods).into(evaluation_holder.image_boutuque_evaluation_3);
        } else if (list.get(position).getImage_evaluation_2() != null) {
            Glide.with(context).load(list.get(position).getImage_evaluation_1()).centerCrop()
                    .placeholder(R.mipmap.default_image_goods).into(evaluation_holder.image_boutuque_evaluation_1);
            Glide.with(context).load(list.get(position).getImage_evaluation_2()).centerCrop()
                    .placeholder(R.mipmap.default_image_goods).into(evaluation_holder.image_boutuque_evaluation_2);
            evaluation_holder.image_boutuque_evaluation_3.setVisibility(View.INVISIBLE);

        } else if (list.get(position).getImage_evaluation_1() != null) {
            Glide.with(context).load(list.get(position).getImage_evaluation_1()).centerCrop()
                    .placeholder(R.mipmap.default_image_goods).into(evaluation_holder.image_boutuque_evaluation_1);
            evaluation_holder.image_boutuque_evaluation_3.setVisibility(View.INVISIBLE);
            evaluation_holder.image_boutuque_evaluation_2.setVisibility(View.INVISIBLE);
        } else if (list.get(position).getImage_evaluation_1() == null) {
            evaluation_holder.image_boutuque_evaluation_3.setVisibility(View.GONE);
            evaluation_holder.image_boutuque_evaluation_2.setVisibility(View.GONE);
            evaluation_holder.image_boutuque_evaluation_1.setVisibility(View.GONE);
            evaluation_holder.ll_evaluation_images.setVisibility(View.GONE);
        }
              /*追加评价*/
        if (list.get(position).getContent_evaluation_add() == null) {
            evaluation_holder.ll_evaluation_add.setVisibility(View.GONE);
        }
        evaluation_holder.tv_evaluation_add.setText("追加评价");
        evaluation_holder.tv_add_time.setText(list.get(position).getTime_add());
        evaluation_holder.tv_boutuque_evaluation_add_main.setText(list.get(position).getContent_evaluation_add());
        if (list.get(position).getImage_evaluation_add_3() != null) {
            Glide.with(context).load(list.get(position).getImage_evaluation_add_1()).centerCrop()
                    .placeholder(R.mipmap.default_image_goods).into(evaluation_holder.image_boutuque_evaluation_add_1);
            Glide.with(context).load(list.get(position).getImage_evaluation_add_2()).centerCrop()
                    .placeholder(R.mipmap.default_image_goods).into(evaluation_holder.image_boutuque_evaluation_add_2);
            Glide.with(context).load(list.get(position).getImage_evaluation_add_3()).centerCrop()
                    .placeholder(R.mipmap.default_image_goods).into(evaluation_holder.image_boutuque_evaluation_add_3);
        } else if (list.get(position).getImage_evaluation_add_2() != null) {
            Glide.with(context).load(list.get(position).getImage_evaluation_add_1()).centerCrop()
                    .placeholder(R.mipmap.default_image_goods).into(evaluation_holder.image_boutuque_evaluation_add_1);
            Glide.with(context).load(list.get(position).getImage_evaluation_add_2()).centerCrop()
                    .placeholder(R.mipmap.default_image_goods).into(evaluation_holder.image_boutuque_evaluation_add_2);
            evaluation_holder.image_boutuque_evaluation_3.setVisibility(View.INVISIBLE);

        } else if (list.get(position).getImage_evaluation_add_1() != null) {
            Glide.with(context).load(list.get(position).getImage_evaluation_add_1()).centerCrop()
                    .placeholder(R.mipmap.default_image_goods).into(evaluation_holder.image_boutuque_evaluation_add_1);
            evaluation_holder.image_boutuque_evaluation_3.setVisibility(View.INVISIBLE);
            evaluation_holder.image_boutuque_evaluation_2.setVisibility(View.INVISIBLE);
        } else if (list.get(position).getImage_evaluation_add_1() == null) {
            evaluation_holder.image_boutuque_evaluation_add_3.setVisibility(View.GONE);
            evaluation_holder.image_boutuque_evaluation_add_2.setVisibility(View.GONE);
            evaluation_holder.image_boutuque_evaluation_add_1.setVisibility(View.GONE);
            evaluation_holder.ll_evaluation_images_add.setVisibility(View.GONE);
        }
        //商家回复
//        evaluation_holder.ll_evaluation_reply
        if (list.get(position).content_evaluation_reply == null) {
            evaluation_holder.ll_evaluation_reply.setVisibility(View.GONE);
        }
        evaluation_holder.tv_evaluation_reply.setText("商家回复");
        evaluation_holder.tv_boutuque_evaluation_reply_main.setText(list.get(position).getContent_evaluation_reply());
        evaluation_holder.tv_reply_time.setText(list.get(position).getTime_reply());
        return convertView;
    }

    class Evaluation_Holder {
        SmartImageView tv_boutuque_evaluation_head;
        TextView tv_boutuque_evaluation_name;
        TextView tv_boutuque_evaluation_time;
        TextView tv_boutuque_evaluation_color;
        TextView tv_boutuque_evaluation_size;
        TextView tv_boutuque_evaluation_main;
        LinearLayout ll_evaluation_images;
        ImageView image_boutuque_evaluation_1;
        ImageView image_boutuque_evaluation_2;
        ImageView image_boutuque_evaluation_3;
        //追加评论
        TextView tv_evaluation_add;
        TextView tv_boutuque_evaluation_add_main;
        LinearLayout ll_evaluation_images_add;
        ImageView image_boutuque_evaluation_add_1;
        ImageView image_boutuque_evaluation_add_2;
        ImageView image_boutuque_evaluation_add_3;
        LinearLayout ll_evaluation_add;
        //商家回复
        LinearLayout ll_evaluation_reply;
        TextView tv_evaluation_reply;
        TextView tv_boutuque_evaluation_reply_main;
        TextView tv_reply_time;
        TextView tv_add_time;
    }
}









