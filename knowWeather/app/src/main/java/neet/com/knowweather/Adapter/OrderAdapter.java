package neet.com.knowweather.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import neet.com.knowweather.R;
import neet.com.knowweather.bean.OrderRecord;

public class OrderAdapter extends BaseAdapter {
    private Context context;
    private int itemLayout;
    private List<OrderRecord> list =new ArrayList<>();

    public OrderAdapter(Context context, int itemLayout, List<OrderRecord> list) {
        this.context = context;
        this.itemLayout = itemLayout;
        this.list = list;
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
        if (null == convertView) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(itemLayout, null);
        }
        TextView tvLong;
        TextView tvTime;
        TextView monery;
        tvLong = (TextView) convertView.findViewById(R.id.tv_long);
        tvTime = (TextView) convertView.findViewById(R.id.tv_time);
        monery=convertView.findViewById(R.id.monery);
        if (list.get(position).getMid()==1){
            tvLong.setText("一个月");
            monery.setText("¥5");
        }else if(list.get(position).getMid()==2){
            tvLong.setText("三个月");
            monery.setText("¥12");
        }else {
            tvLong.setText("一年");
            monery.setText("¥36");
        }
        tvTime.setText(list.get(position).getCreateTime());
        return convertView;
    }
}
