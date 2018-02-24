package com.kait.longlongtran.storyvoz.UI;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.kait.longlongtran.storyvoz.Network.NetworkUtil;
import com.kait.longlongtran.storyvoz.OffLineActivity;
import com.kait.longlongtran.storyvoz.R;

import java.lang.ref.WeakReference;

public class StartActivity extends AppCompatActivity {

    private final String TAG = StartActivity.class.getSimpleName();
    private ImageSwitcher imgSwitcher1, imgSwitcher2, imgSwitcher3, imgSwitcher4;
    private TextView mLableLoading;
    private Button mBtn_DocTruyenOffline, mBtn_DocTruyenOnline;
    NetworkUtil networkUtil;

    private static Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_start_activity);
        Log.d(TAG, "#onCreate");
        init();
        Animation rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_animation);
        Animation zoomOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomout);
        Animation blink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        Animation sequential = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sequential);
        imgSwitcher1.setInAnimation(rotate);
        imgSwitcher2.setInAnimation(zoomOut);
        imgSwitcher3.setInAnimation(blink);
        imgSwitcher4.setInAnimation(sequential);
        new HandlerStartImage(this).run();
        networkUtil = new NetworkUtil();
    }

    private void init() {
        imgSwitcher1 = findViewById(R.id.slide_transiton1);
        imgSwitcher2 = findViewById(R.id.slide_transiton2);
        imgSwitcher3 = findViewById(R.id.slide_transiton3);
        imgSwitcher4 = findViewById(R.id.slide_transiton4);
        mLableLoading = findViewById(R.id.tv_Loading);
        Typeface tf = Typeface.createFromAsset(getAssets(),
                "JLSSpaceGothicR_NC.otf");
        mLableLoading.setTypeface(tf);
        mBtn_DocTruyenOffline = findViewById(R.id.btn_DocTruyenOffline);
        mBtn_DocTruyenOnline = findViewById(R.id.btn_DocTruyenOnline);
        imgSwitcher1.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
                return imageView;
            }
        });
        imgSwitcher2.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
                return imageView;
            }
        });
        imgSwitcher3.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
                return imageView;
            }
        });
        imgSwitcher4.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
                return imageView;
            }
        });

        mBtn_DocTruyenOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, OffLineActivity.class);
                startActivity(intent);
            }
        });
        mBtn_DocTruyenOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (networkUtil.checkInternet(StartActivity.this)) {
                    Intent intent = new Intent(StartActivity.this, OnlineActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(StartActivity.this, "Bật Internet Để Có Thể Đọc Truyện...", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private static class HandlerStartImage implements Runnable {
        private final WeakReference<StartActivity> StartActivityWrf;
        private int mAnimationCount = 0;

        private HandlerStartImage(StartActivity startActivityWrf) {
            StartActivityWrf = new WeakReference<StartActivity>(startActivityWrf);
        }

        @Override
        public void run() {
            final StartActivity startActivity = StartActivityWrf.get();
            if (startActivity != null) {
                handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        switch (mAnimationCount++) {
                            case 0:
                                startActivity.imgSwitcher1.setImageResource(R.drawable.img_start1);
                                startActivity.imgSwitcher4.setImageResource(R.drawable.img_start_2_conver);
                                break;
                            case 1:
                                startActivity.imgSwitcher2.setImageResource(R.drawable.img_start_2);
                                startActivity.imgSwitcher3.setImageResource(R.drawable.img_start_3);
                                startActivity.imgSwitcher4.setImageResource(R.drawable.img_start3_convert);
                        }
                        mAnimationCount %= 2;
                        startActivity.imgSwitcher1.postDelayed(this, 5000);

                    }

                });


            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "#onStart");
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "#onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}