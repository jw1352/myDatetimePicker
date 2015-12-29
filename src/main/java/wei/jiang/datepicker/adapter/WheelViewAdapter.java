package wei.jiang.datepicker.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import wei.jiang.R;

/**
 * Created by wei.jiang on 2015/12/28.
 */
public class WheelViewAdapter extends BaseAdapter {
    private int minValue;
    private int maxValue;
    private int count;

    private Context context;

    public WheelViewAdapter(int minValue, int maxValue, Context context) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.context = context;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public int getCount() {
        count = maxValue - minValue + 1;
        return count * 10;
    }

    @Override
    public Object getItem(int position) {
        count = maxValue - minValue + 1;
        return position % count + minValue;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        count = maxValue - minValue + 1;
       // Log.d("xx", count + "postion" + position );
        position %= count; //实现循环滑动

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.wheelview_item, null);
        }
        ((TextView) convertView).setText(String.format( "%02d", getItem(position)));
        return convertView;
    }
}
