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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by wei.jiang on 2015/12/28.
 */
public class MyDateTimePicker extends FrameLayout {

    private WheelView wvYear;
    private WheelView wvMonth;
    private WheelView wvDay;
    private WheelView wvHour;
    private WheelView wvMinute;
    private TextView txtDate;
    private Calendar mCalendar;

    private int startYear = 1990;
    private int endYear = 2020;
    private String DATE_FORMAT = "yyyy-MM-dd HH:mm";

    // 添加大小月月份并将其转换为list,方便之后的判断
    String[] months_big = {"1", "3", "5", "7", "8", "10", "12"};
    String[] months_little = {"4", "6", "9", "11"};
    private List<String> list_big;
    private List<String> list_little;
    private OnTimeSetListener onTimeSetListener;

    private Date crruentDate;

    public Date getDate() {
        return crruentDate;
    }

    public void setCrruentDate(Date crruentDate) {
        this.crruentDate = crruentDate;
    }

    public void setOnTimeSetListener(OnTimeSetListener onTimeSetListener) {
        this.onTimeSetListener = onTimeSetListener;
    }

    public MyDateTimePicker(Context context) {
        super(context);
        initView(context);
    }

    public MyDateTimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(final Context context) {
        LayoutInflater.from(context).inflate(R.layout.my_datetimepicker_layout, this,
                true);

        mCalendar = Calendar.getInstance();
        final int year = mCalendar.get(Calendar.YEAR);
        final int month = mCalendar.get(Calendar.MONTH);
        final int day = mCalendar.get(Calendar.DATE);
        final int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        final int minute = mCalendar.get(Calendar.MINUTE);
        crruentDate = mCalendar.getTime();

        list_big = Arrays.asList(months_big);
        list_little = Arrays.asList(months_little);

        txtDate = (TextView) findViewById(R.id.txtDate);
        txtDate.setText(formatDate(year,month, day, hour, minute));

        wvYear = (WheelView) findViewById(R.id.wvYear);
        WheelViewAdapter adapterYear = new WheelViewAdapter(startYear, endYear, context);
        wvYear.setAdapter(adapterYear);
        wvYear.post(new Runnable() {
            @Override
            public void run() {
                wvYear.setCurrentItem(year - startYear, year); //设置到中间位置实现循环滑动
            }
        });

        wvMonth = (WheelView) findViewById(R.id.wvMonth);
        WheelViewAdapter adapterMonth = new WheelViewAdapter(1, 12, context);
        wvMonth.setAdapter(adapterMonth);
        wvMonth.post(new Runnable() {
            @Override
            public void run() {
                wvMonth.setCurrentItem(month, month + 1);
            }
        });

        wvDay = (WheelView) findViewById(R.id.wvDay);
        final WheelViewAdapter adapterDay = new WheelViewAdapter(1, 31, context);
        wvDay.setAdapter(adapterDay);
        changeDayItem(year, month + 1, day, context, adapterDay);

        wvHour = (WheelView) findViewById(R.id.wvHour);
        WheelViewAdapter adapterHour = new WheelViewAdapter(0, 23, context);
        wvHour.setAdapter(adapterHour);
        wvHour.post(new Runnable() {
            @Override
            public void run() {
                wvHour.setCurrentItem(hour, hour);
            }
        });

        wvMinute = (WheelView) findViewById(R.id.wvMinute);
        WheelViewAdapter adapterMinute = new WheelViewAdapter(1, 60, context);
        wvMinute.setAdapter(adapterMinute);
        wvMinute.post(new Runnable() {
            @Override
            public void run() {
                wvMinute.setCurrentItem(minute - 1, minute);
            }
        });


        wvYear.setOnItemChangedListener(new WheelView.OnItemChangedListener() {
            @Override
            public void onItemSelected(int value) {
                int day = changeDayItem(value, wvMonth.getCurrentValue(), wvDay.getCurrentValue(), context, adapterDay);
                //月份是从0开始， 格式化时要减一
                txtDate.setText(formatDate(value, wvMonth.getCurrentValue() - 1, day, wvHour.getCurrentValue(), wvMinute.getCurrentValue()));
                if (onTimeSetListener != null) {
                    onTimeSetListener.onTimeSet(value, wvMonth.getCurrentValue() - 1, day, wvHour.getCurrentValue(), wvMinute.getCurrentValue());
                }
            }
        });

        wvMonth.setOnItemChangedListener(new WheelView.OnItemChangedListener() {
            @Override
            public void onItemSelected(int value) {
                int day = changeDayItem(wvYear.getCurrentValue(), value, wvDay.getCurrentValue(), context, adapterDay);

                txtDate.setText(formatDate(wvYear.getCurrentValue(), value - 1, day, wvHour.getCurrentValue(), wvMinute.getCurrentValue()));
                if (onTimeSetListener != null) {
                    onTimeSetListener.onTimeSet(wvYear.getCurrentValue() , value - 1, day, wvHour.getCurrentValue(), wvMinute.getCurrentValue());
                }
            }
        });

        wvDay.setOnItemChangedListener(new WheelView.OnItemChangedListener() {
            @Override
            public void onItemSelected(int value) {
                txtDate.setText(formatDate(wvYear.getCurrentValue(), wvMonth.getCurrentValue() - 1, value, wvHour.getCurrentValue(), wvMinute.getCurrentValue()));
                if (onTimeSetListener != null) {
                    onTimeSetListener.onTimeSet(wvYear.getCurrentValue(), wvMonth.getCurrentValue() - 1, value, wvHour.getCurrentValue(), wvMinute.getCurrentValue());
                }
            }
        });

        wvHour.setOnItemChangedListener(new WheelView.OnItemChangedListener() {
            @Override
            public void onItemSelected(int value) {
                //月份是从0开始， 格式化时要减一
                txtDate.setText(formatDate(wvYear.getCurrentValue(), wvMonth.getCurrentValue() - 1, wvDay.getCurrentValue(), value, wvMinute.getCurrentValue()));
                if (onTimeSetListener != null) {
                    onTimeSetListener.onTimeSet(wvYear.getCurrentValue(), wvMonth.getCurrentValue() - 1, wvDay.getCurrentValue(), value, wvMinute.getCurrentValue());
                }
            }
        });

        wvMinute.setOnItemChangedListener(new WheelView.OnItemChangedListener() {
            @Override
            public void onItemSelected(int value) {

                txtDate.setText(formatDate(wvYear.getCurrentValue(), wvMonth.getCurrentValue() - 1, wvDay.getCurrentValue(), wvHour.getCurrentValue(), value));
                if (onTimeSetListener != null) {
                    onTimeSetListener.onTimeSet(wvYear.getCurrentValue(), wvMonth.getCurrentValue() - 1, wvDay.getCurrentValue(), wvHour.getCurrentValue(), value);
                }
            }
        });


    }


    /**
     * // 判断大小月及是否闰年,用来确定"日"的数据
     *
     * @param year       年
     * @param month      月
     * @param day
     * @param context
     * @param adapterDay
     */
    private int changeDayItem(int year, int month, int day, Context context, WheelViewAdapter adapterDay) {
        int maxDay;
        if (list_big
                .contains(String.valueOf(month))) {
            maxDay = 31;
        } else if (list_little.contains(String.valueOf(month))) {
            maxDay = 30;
        } else {
            if ((year % 4 == 0 && year % 100 != 0)
                    || year % 400 == 0) {
                maxDay = 29;
            } else {
                maxDay = 28;

            }
        }
        adapterDay.setMaxValue(maxDay);
        adapterDay.notifyDataSetChanged();

        final int temp;
        if (day > maxDay) {
            temp = maxDay;
        } else {
            temp = day;
        }
        wvDay.post(new Runnable() {
            @Override
            public void run() {
                wvDay.setCurrentItem(temp - 1, temp);
            }
        });
        return temp;
    }

    private interface OnTimeSetListener {
        void onTimeSet(int year, int month, int day, int hour, int minute);
    }

    private String formatDate(int year, int month, int day, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        crruentDate = calendar.getTime();
        return sdf.format(calendar.getTime());
    }

}
