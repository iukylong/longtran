package com.kait.longlongtran.storyvoz.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kait.longlongtran.storyvoz.Model.StoryModel;
import com.kait.longlongtran.storyvoz.R;
import com.kait.longlongtran.storyvoz.Singlaton.ResultIPC;

import java.io.ByteArrayInputStream;

public class DetailActivityOffline extends AppCompatActivity {

    TextView tv_tacGiaDetail, tv_theLoaiDetail, tv_tinhTrangdetail, tv_ghiChuDetail, tv_moDau, tv_nguon;
    Button btn_docTruyen;
    ImageView img_hinhAnh;
    String TenTruyen;
    int sync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_detail_offline);
        sync = getIntent().getIntExtra("sync", -1);
        final StoryModel bigData = ResultIPC.get().getLargeData(sync);
        setTitleAppBar();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        init();
        if (bigData.getmImage() != null) {
            byte[] b = bigData.getmImage();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(b);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            img_hinhAnh.setImageBitmap(bitmap);
        }
        if (bigData.getmTenTruyen() != null) {
            TenTruyen = bigData.getmTenTruyen();
        }
        if (bigData.getmTacGia() != null) {
            tv_tacGiaDetail.setText(bigData.getmTacGia());
        }
        if (bigData.getmMoDau() != null) {
            tv_moDau.setText(bigData.getmMoDau());
        }
        if (bigData.getmNguon() != null) {
            tv_nguon.setText(bigData.getmNguon());
        }
        if (bigData.getmTinhTrang() != null) {
            tv_tinhTrangdetail.setText(bigData.getmTinhTrang());
        }
        if (bigData.getmTheLoai() != null) {
            tv_theLoaiDetail.setText(bigData.getmTheLoai());
        }
        if (bigData.getmGhiChu() != null) {
            tv_ghiChuDetail.setText(bigData.getmGhiChu());
        }

    }

    /**
     * Show title when scrolling toolbar
     */

    private void setTitleAppBar() {
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("Truyện Voz");
                    isShow = true;
                } else if (isShow) {
                    if (TenTruyen != null) {
                        collapsingToolbarLayout.setTitle(TenTruyen);//carefull there should a space between double quote otherwise it wont work
                        isShow = false;
                    }
                }
            }
        });

    }


    public void init() {
        tv_ghiChuDetail = findViewById(R.id.tv_GhiChuDetail);
        tv_tacGiaDetail = findViewById(R.id.tv_TacGiaDetail);
        tv_theLoaiDetail = findViewById(R.id.tv_TheLoaiDetail);
        tv_tinhTrangdetail = findViewById(R.id.tv_TinhTrangDetail);
        btn_docTruyen = findViewById(R.id.btn_DocTruyen);
        tv_nguon = findViewById(R.id.tv_NguonDetail);
        tv_moDau = findViewById(R.id.tv_MoDau);
        img_hinhAnh = findViewById(R.id.img_HinhAnh);
        btn_docTruyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivityOffline.this, ReadStoryActivityOffline.class);
                intent.putExtra("sync2", sync);
                startActivity(intent);
            }
        });
    }
}
