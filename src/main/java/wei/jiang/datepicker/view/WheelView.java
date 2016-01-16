package wei.jiang.datepicker.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by wei.jiang on 2015/12/28.
 * 滚动的条目
 */
public class WheelView extends ListView implements AbsListView.OnScrollListener {
    public final static int MAX_ITEM_COUNT = 5;
    private int itemHeight; //每个条目的高度
    private boolean isFirst = true;

    private int currentValue;
    private int firstPostion;

    private OnItemChangedListener onItemChangedListener;
    private boolean isIdle;

    public void setOnItemChangedListener(WheelView.OnItemChangedListener onItemChangedListener) {
        this.onItemChangedListener = onItemChangedListener;
    }

    public WheelView(Context context) {
        super(context);
        setOnScrollListener(this);
        setVerticalScrollBarEnabled(false);
    }

    public WheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnScrollListener(this);
        setVerticalScrollBarEnabled(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (getChildCount() > 0) { //设置listview高度为五个item
            itemHeight = getChildAt(0).getMeasuredHeight();
            setMeasuredDimension(widthMeasureSpec, (itemHeight + getDividerHeight()) * MAX_ITEM_COUNT);
            setDividerHeight(0);
        }
    }

    public void doScroll() {
        final int offsetY = getChildAt(0).getBottom();
        final int distance;
        if (offsetY < itemHeight / 2) { //向上
            distance = offsetY;
            currentValue = Integer.parseInt(((TextView) getChildAt(3)).getText().toString()); //上滑时取下边一个
        } else {
            distance = offsetY - itemHeight;
            currentValue = Integer.parseInt(((TextView) getChildAt(2)).getText().toString());
        }

        postDelayed(new Runnable() { //这里要post到主线程度才会有平滑的效果, delay 100ms解决连续滚动时的阻塞
            @Override
            public void run() {
                if (isIdle && distance != 0) {
                    smoothScrollBy(distance, 1000 * Math.abs(distance) / itemHeight); //下滑时取下边一个，可能是滑动方法是异步的
                }
            }
        }, 100);
        if (onItemChangedListener != null) {
            onItemChangedListener.onItemSelected(currentValue);
        }
    }



    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
            isIdle = true;
            doScroll();
        } else {
            isIdle = false;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (visibleItemCount > 0) {
            initColor();
            int offsetY = getChildAt(0).getBottom() + getDividerHeight();
            if (offsetY > itemHeight / 2) {// 控制滑过一半改变颜色
                ((TextView) getChildAt(2)).setTextColor(0xffE75A3E);

                ((TextView) getChildAt(1)).setTextColor(0xff989898);
                ((TextView) getChildAt(3)).setTextColor(0xff989898);

                ((TextView) getChildAt(0)).setTextColor(0xffCBCBCB);
                ((TextView) getChildAt(4)).setTextColor(0xffCBCBCB);
                if (getChildCount() > 5)
                    ((TextView) getChildAt(5)).setTextColor(0xffCBCBCB);
            } else {
                ((TextView) getChildAt(3)).setTextColor(0xffE75A3E);

                ((TextView) getChildAt(2)).setTextColor(0xff989898);
                ((TextView) getChildAt(4)).setTextColor(0xff989898);

                ((TextView) getChildAt(1)).setTextColor(0xffCBCBCB);
                ((TextView) getChildAt(0)).setTextColor(0xffCBCBCB);
                if (getChildCount() > 5)
                    ((TextView) getChildAt(5)).setTextColor(0xffCBCBCB);
            }
        }

    }


    private void initColor() {
        for (int i = 0; i < getChildCount(); i++) {
            ((TextView) getChildAt(i)).setTextColor(Color.BLACK);
        }
    }

    /**
     * 设置初始的位置，放在OnSizeChanged里面执行，不然有问题
     * @param position 位置
     * @param value 值
     */
    public void setCurrentItem(final int position, int value) {
        firstPostion = position;
        currentValue = value;
        setSelection(firstPostion);
    }


    public int getCurrentValue() {
        return currentValue;
    }

    /**
     * 设置初始位置
     */
    private void performFirstSelectedPosition() {
        if (firstPostion != 0) { //只执行一次
            this.setSelection(firstPostion);
            firstPostion = 0;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        performFirstSelectedPosition();
    }

    public interface OnItemChangedListener {
        void onItemSelected(int value);
    }
}
