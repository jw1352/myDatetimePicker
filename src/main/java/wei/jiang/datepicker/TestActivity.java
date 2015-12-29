package wei.jiang.datepicker;

import android.app.Activity;
import android.os.Bundle;

import wei.jiang.R;
import wei.jiang.datepicker.adapter.WheelViewAdapter;
import wei.jiang.datepicker.view.WheelView;

/**
 * Created by wei.jiang on 2015/12/28.
 */
public class TestActivity extends Activity {
    private WheelView wvYear;
    private WheelView wvMonth;
    private WheelView wvDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_datepicker_layout);

        wvYear = (WheelView) findViewById(R.id.wvYear);
        WheelViewAdapter adapterYear = new WheelViewAdapter(1990, 2020, this);
        wvYear.setAdapter(adapterYear);

        wvMonth = (WheelView) findViewById(R.id.wvMonth);
        WheelViewAdapter adapterMonth = new WheelViewAdapter(1, 12, this);
        wvMonth.setAdapter(adapterMonth);

        wvDay = (WheelView) findViewById(R.id.wvDay);
        WheelViewAdapter adapterDay = new WheelViewAdapter(1, 31, this);
        wvDay.setAdapter(adapterDay);
    }
}
