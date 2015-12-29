package wei.jiang.datepicker;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import wei.jiang.R;
import wei.jiang.datepicker.view.MyDateTimePicker;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wei.jiang on 2015/12/28.
 */
public class TestActivity2 extends Activity implements View.OnClickListener {
    private PopupWindow popupWindow;
    private MyDateTimePicker dateTimePicker;
    private TextView txtDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtDateTime = (TextView) findViewById(R.id.txtDateTime);
    }

    public void alert(View v) {
        View view = this.getLayoutInflater().inflate(R.layout.dialog_datetimepicker, null);
        if (popupWindow == null) {
            popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT, true);
            popupWindow.showAtLocation(view, Gravity.CENTER
                    | Gravity.CENTER, 0, 0);
            popupWindow.setAnimationStyle(R.style.dialogAnimation);
            ColorDrawable dw = new ColorDrawable(0x000000);
            popupWindow.setBackgroundDrawable(dw);
            popupWindow.update();

            Button saveBtn = (Button) view.findViewById(R.id.saveBtn);
            saveBtn.setOnClickListener(this);
            dateTimePicker = (MyDateTimePicker) view.findViewById(R.id.dateTimePicker2);

        } else {
            if (!popupWindow.isShowing()) {
                popupWindow.showAtLocation(view, Gravity.RIGHT | Gravity.BOTTOM, 0, 0);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.saveBtn) {
            if (popupWindow != null) {
                String date = parseDate(dateTimePicker.getDate());
                txtDateTime.setText(date);
                popupWindow.dismiss();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    private String parseDate(Date date) {
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
}
