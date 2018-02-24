package com.kait.longlongtran.storyvoz.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kait.longlongtran.storyvoz.Model.StoryModelOnline;
import com.kait.longlongtran.storyvoz.R;
import com.kait.longlongtran.storyvoz.Singlaton.SingletonOnline;
import com.kait.longlongtran.storyvoz.Singlaton.SingletonVolley;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;

import dmax.dialog.SpotsDialog;

public class DetailActivityOnline extends AppCompatActivity {

    private static final String TAG = DetailActivityOnline.class.getSimpleName();
    TextView tv_tacGiaDetail_Online, tv_theLoaiDetail_Online, tv_tinhTrangdetail_Online, tv_ghiChuDetail_Online, tv_moDau_Online, tv_nguon_Online;
    Button btn_docTruyen_Online;
    String TenTruyen, HinhAnh;
    ImageView mHinhAnh_Online;
    int sync;
    StoryModelOnline bigData;
    private AlertDialog mProgressDialog;
    private Boolean mIsResult = false;
    String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_online);
        init();
        sync = getIntent().getIntExtra("synconline", -1);
        bigData = SingletonOnline.get().getLargeData(sync);
        setTitleAppBar();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_Online);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_online);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        if (bigData.getmTenTruyen() != null) {
            TenTruyen = bigData.getmTenTruyen();
        }
        if (bigData.getmTacGia() != null) {
            tv_tacGiaDetail_Online.setText(bigData.getmTacGia());
        }
        if (bigData.getmMoDau() != null) {
            tv_moDau_Online.setText(bigData.getmMoDau());
        }
        if (bigData.getmNguon() != null) {
            tv_nguon_Online.setText(bigData.getmNguon());
        }
        if (bigData.getmTinhTrang() != null) {
            tv_tinhTrangdetail_Online.setText(bigData.getmTinhTrang());
        }
        if (bigData.getmTheLoai() != null) {
            tv_theLoaiDetail_Online.setText(bigData.getmTheLoai());
        }
        if (bigData.getmGhiChu() != null) {
            tv_ghiChuDetail_Online.setText(bigData.getmGhiChu());
        }
        if (bigData.getmHinhAnh() != null) {
            Picasso.with(DetailActivityOnline.this).load(bigData.getmHinhAnh()).placeholder(R.drawable.loading).into(mHinhAnh_Online);
        }
        if (bigData.getmMoTa() != null) {
            // new GetDataUrl(this).execute(bigData.getmMoTa());
            getDataVolley(bigData.getmMoTa());
        }
    }

    /**
     * set title Appbar when scroll view
     */
    private void setTitleAppBar() {
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout_online);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_online);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("Truyện Online");
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

    private void getDataVolley(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    byte[] u = response.toString().getBytes("ISO-8859-1");
                    result = new String(u, "UTF-8");
                    bigData.setmReadMoTa(result);
                    mIsResult = true;
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        SingletonVolley.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    /**
     * check Progress Dialog showing
     */

    private void checkDialogShowing() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivityOnline.this);
            builder.setTitle("Load Data");
            builder.setMessage("Đang Load Dữ Liệu..");
            builder.setCancelable(true);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (mProgressDialog != null) {
                        mProgressDialog.dismiss();
                    }
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        }
    }


    /**
     * referent to view
     */
    public void init() {
        tv_ghiChuDetail_Online = findViewById(R.id.tv_GhiChuDetail_Online);
        tv_tacGiaDetail_Online = findViewById(R.id.tv_TacGiaDetail_Online);
        tv_theLoaiDetail_Online = findViewById(R.id.tv_TheLoaiDetail_Online);
        tv_tinhTrangdetail_Online = findViewById(R.id.tv_TinhTrangDetail_Online);
        btn_docTruyen_Online = findViewById(R.id.btn_DocTruyen_Online);
        tv_nguon_Online = findViewById(R.id.tv_NguonDetail_Online);
        tv_moDau_Online = findViewById(R.id.tv_MoDau_Online);
        mHinhAnh_Online = findViewById(R.id.img_HinhAnh_Online);
        mProgressDialog = new SpotsDialog(DetailActivityOnline.this, R.style.Custom);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        btn_docTruyen_Online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivityOnline.this, ReadStoryActivityOnline.class);
                intent.putExtra("sync2", sync);
                if (mIsResult == true) {
//                    mProgressDialog = new SpotsDialog(DetailActivityOnline.this, R.style.Custom);
//                    mProgressDialog.setCanceledOnTouchOutside(false);
//                    mProgressDialog.show();
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * change file text to string
     */
    private static class GetDataUrl extends AsyncTask<String, Void, String> {
        private WeakReference<DetailActivityOnline> onlineActivityWef;

        private GetDataUrl(DetailActivityOnline detailActivityOnline) {
            onlineActivityWef = new WeakReference<DetailActivityOnline>(detailActivityOnline);
        }

        DetailActivityOnline startActivity = null;

        @Override
        protected void onPreExecute() {
            startActivity = onlineActivityWef.get();
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {
            startActivity.mIsResult = false;
            String result = "";
            try {
                URL url = new URL(strings[0]);
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String line = null;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
            } catch (MalformedURLException e) {
                //startActivity.tv_Show.setText("Error + " + e.toString());
                e.printStackTrace();
            } catch (IOException e) {
                //startActivity.tv_Show.setText("Error + " + e.toString());
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            startActivity.bigData.setmReadMoTa(s);
            startActivity.mIsResult = true;
            if (startActivity.mProgressDialog != null && startActivity.mProgressDialog.isShowing()) {
                startActivity.mProgressDialog.dismiss();
                Intent intent = new Intent(startActivity, ReadStoryActivityOnline.class);
                intent.putExtra("sync2", startActivity.sync);
                startActivity.startActivity(intent);
            }
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}
