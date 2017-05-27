package com.example.administrator.a3dmark.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.bean.Goods_Seckill;
import com.example.administrator.a3dmark.util.MyProgress;
import com.loopj.android.image.SmartImageView;

import java.util.List;


/**
 * Created by Stability.Yang on 2017/3/12.
 */

public class SeckillAdapter extends BaseAdapter {

    private List<Goods_Seckill> mainlist;
    private Context mContext;
    private BtnClick btnClick;

    public SeckillAdapter(List<Goods_Seckill> mainlist, Context mContext) {
        this.mainlist = mainlist;
        this.mContext = mContext;
    }


    public void setBtnClick(BtnClick btnClick){
        this.btnClick = btnClick;
    }


    @Override
    public int getCount() {
        return mainlist.size();
    }

    @Override
    public Object getItem(int position) {
        Goods_Seckill info = null;
        info = mainlist.get(position);
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_seckill, parent, false);
            viewHolder.img = (SmartImageView) convertView.findViewById(R.id.img);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.price_now = (TextView) convertView.findViewById(R.id.price_now);
            viewHolder.price_old = (TextView) convertView.findViewById(R.id.price_old);
            viewHolder.buynum = (TextView) convertView.findViewById(R.id.buynum);
            viewHolder.progressBar = (MyProgress) convertView.findViewById(R.id.progressBar);
            viewHolder.snap = (Button) convertView.findViewById(R.id.snap);
            viewHolder.price_old.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            convertView.setTag(viewHolder);
        } else if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        viewHolder.goods_image.setImageUrl(mainlist.get(position).getImg());
        Glide.with(mContext).load(mainlist.get(position).getImg()).centerCrop().placeholder(R.drawable.icon_empty).into(viewHolder.img);
        viewHolder.name.setText(mainlist.get(position).getName());
        viewHolder.price_now.setText(mainlist.get(position).getPrice_now());
        viewHolder.price_old.setText(mainlist.get(position).getPrice_old());
        viewHolder.buynum.setText(mainlist.get(position).getBuynum()+"人已购买");
        viewHolder.progressBar.setText(mainlist.get(position).getProportion());
        viewHolder.snap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnClick.click(position);
            }
        });

        return convertView;
    }


    /**
     * 抢购接口
     */
    public interface BtnClick {
        void click(int position);
    }




    class ViewHolder {

        SmartImageView img;//商品图片
        TextView name;//描述
        TextView price_now;//抢购价
        TextView price_old;//原价
        TextView buynum;//已购人数
        TextView id;//ID
        MyProgress progressBar;//已购数与库存比值
        Button snap;//抢购
    }

}
