package com.kait.longlongtran.storyvoz.UI;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kait.longlongtran.storyvoz.Model.StoryModelOnline;
import com.kait.longlongtran.storyvoz.R;
import com.kait.longlongtran.storyvoz.Singlaton.SingletonOnline;

public class ReadStoryActivityOnline extends AppCompatActivity {

    private static final String TAG = ReadStoryActivityOnline.class.getSimpleName();
    private LinearLayout mLinearScroll;
    String dulieu = "";
    long kytu = 21000;
    TextView tv_mota;
    ScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_read_story_online);
        tv_mota = findViewById(R.id.tv_MoTa_Online);
        mScrollView = findViewById(R.id.scrollView_Online);
        mLinearScroll = findViewById(R.id.linear_scroll_Online);
        int sync = getIntent().getIntExtra("sync2", -1);
        final StoryModelOnline bigData = SingletonOnline.get().getLargeData(sync);
        if (bigData.getmReadMoTa() != null) {
            dulieu = bigData.getmReadMoTa();
        }
        tv_mota.setText(dulieu.substring(0, (int) kytu));
        long size = dulieu.length() / kytu + 1;

        for (int j = 1; j <= size; j++) {
            final int k;
            k = j;

            setPage(k, j);
            mScrollView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            break;
                        default:
                            return ReadStoryActivityOnline.super.onTouchEvent(motionEvent);
                    }
                    return false;
                }
            });

        }

    }

    private void setPage(final int m, int j) {
        final TextView tv_Page = new TextView(ReadStoryActivityOnline.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(120, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(5, 2, 2, 2);
        tv_Page.setTextColor(Color.parseColor("#5E35B1"));
        tv_Page.setTextSize(26.0f);
        tv_Page.setId(j);
        tv_Page.setText(String.valueOf(j));
        mLinearScroll.addView(tv_Page, layoutParams);
        tv_Page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addText(m);
                mScrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }

    /**
     * add text from scrolling
     *
     * @param count
     */
    public void addText(int count) {
        tv_mota.clearComposingText();
        long v2 = count * kytu;
        if (v2 > dulieu.length()) {
            v2 = dulieu.length();
        }
        long v1 = ((count - 1) * kytu) + 1;
        if (count == 1 || count == 0) {
            String txt1 = dulieu.substring(0, (int) v2);
            tv_mota.setText(txt1.toString());
        } else {
            String txt2 = dulieu.substring((int) v1, (int) v2);
            tv_mota.setText(txt2.toString());
        }
    }
}
