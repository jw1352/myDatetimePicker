package wei.jiang.datepicker.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import wei.jiang.R;
import wei.jiang.datepicker.view.WheelView;

/**
 * Created by wei.jiang on 2015/12/28.
 */
public class WheelViewAdapter extends BaseAdapter {
    private int minValue;
    private int maxValue;
    private int count;
    private WheelView wheelView;

    private Context context;

    public WheelViewAdapter(int minValue, int maxValue, Context context, WheelView wheelView) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.context = context;
        this.wheelView = wheelView;
    }


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
        count = maxValue - minValue + 5;
        return count;
    }

    @Override
    public Object getItem(int position) {
        return position + minValue - 2;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.wheelview_item, null);
        }
        if (position == 0 || position == 1 || position == getCount() - 1 || position == getCount() - 2) {
            ((TextView) convertView).setText("");
        } else {
            ((TextView) convertView).setText(String.format("%02d", getItem(position)));
        }
        return convertView;
    }


    public void setCurrentItem(int position) {
        wheelView.setSelection(position - 2);
        notifyDataSetChanged();
    }
}
