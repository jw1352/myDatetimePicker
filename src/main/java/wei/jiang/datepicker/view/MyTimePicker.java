package wei.jiang.datepicker.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import wei.jiang.R;
import wei.jiang.datepicker.adapter.WheelViewAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by wei.jiang on 2015/12/28.
 */
public class MyTimePicker extends FrameLayout {

    private WheelView wvHour;
    private WheelView wvMinute;
    private TextView txtTime;
    private Calendar mCalendar;

    private OnTimeSetListener onTimeSetListener;
    private static final String TIME_FOMRAT = "HH:mm";

    public void setOnTimeSetListener(OnTimeSetListener onTimeSetListener) {
        this.onTimeSetListener = onTimeSetListener;
    }

    public MyTimePicker(Context context) {
        super(context);
        initView(context);
    }

    public MyTimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(final Context context) {
        LayoutInflater.from(context).inflate(R.layout.my_timepicker_layout, this,
                true);

        mCalendar = Calendar.getInstance();
        int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = mCalendar.get(Calendar.MINUTE);

        txtTime = (TextView) findViewById(R.id.txtTime);
        txtTime.setText(formatDate(hour, minute));

        wvHour = (WheelView) findViewById(R.id.wvHour);
        WheelViewAdapter adapterHour = new WheelViewAdapter(0, 23, context);
        wvHour.setAdapter(adapterHour);
        Log.d("dd", ""+ hour);
        wvHour.setCurrentItem(hour + 24 * 5); //设置到中间位置实现循环滑动

        wvMinute = (WheelView) findViewById(R.id.wvMinute);
        WheelViewAdapter adapterMinute = new WheelViewAdapter(0, 60, context);
        wvMinute.setAdapter(adapterMinute);
        wvMinute.setCurrentItem(minute + 12 * 5);  //设置到中间位置实现循环滑动



        wvHour.setOnItemChangedListener(new WheelView.OnItemChangedListener() {
            @Override
            public void onItemSelected(int value) {
                //月份是从0开始， 格式化时要减一
                txtTime.setText(formatDate(value, wvMinute.getCurrentValue()));
                if (onTimeSetListener != null) {
                    onTimeSetListener.onTimeSet(value, wvMinute.getCurrentValue());
                }
            }
        });

        wvMinute.setOnItemChangedListener(new WheelView.OnItemChangedListener() {
            @Override
            public void onItemSelected(int value) {

                txtTime.setText(formatDate(wvHour.getCurrentValue(), value));
                if (onTimeSetListener != null) {
                    onTimeSetListener.onTimeSet(wvHour.getCurrentValue(), value);
                }
            }
        });

    }


    private interface  OnTimeSetListener {
        void onTimeSet(int hour, int minute);
    }

    private String formatDate(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FOMRAT);
        return sdf.format(calendar.getTime());
    }
}
