package com.kait.longlongtran.storyvoz;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.kait.longlongtran.storyvoz.Adapter.StoryAdapter;
import com.kait.longlongtran.storyvoz.Database.DatabaseHelper;
import com.kait.longlongtran.storyvoz.Model.StoryModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class OffLineActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String TAG = OffLineActivity.class.getSimpleName();

    private ArrayList<StoryModel> mArrayList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private StoryAdapter mAdapter;
    DatabaseHelper db;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_offline);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mRecyclerView = findViewById(R.id.recyclerView);
        db = new DatabaseHelper(this);
        mAdapter = new StoryAdapter(mArrayList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(this, R.anim.item_layout_animation);
        mRecyclerView.setLayoutAnimation(controller);
        mRecyclerView.setAdapter(mAdapter);
        prePareData();
        mRecyclerView.scheduleLayoutAnimation();
    }


    /**
     * Request Database
     */

    private void prePareData() {
        db = new DatabaseHelper(this);
        try {

            db.createDataBase();
            db.openDataBase();

        } catch (Exception e) {
            e.printStackTrace();
        }
        SQLiteDatabase sd = db.getReadableDatabase();
        Cursor cursor = sd.query("truyenvoz", null, null, null, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    StoryModel model = new StoryModel();
                    model.setmTenTruyen(cursor.getString(cursor.getColumnIndex("tentruyen")));
                    model.setmTacGia(cursor.getString(cursor.getColumnIndex("tacgia")));
                    model.setmMoTa(cursor.getString(cursor.getColumnIndex("mota")));
                    model.setmTheLoai(cursor.getString(cursor.getColumnIndex("theloai")));
                    model.setmNgayViet(cursor.getString(cursor.getColumnIndex("ngayviet")));
                    model.setmNguon(cursor.getString(cursor.getColumnIndex("nguon")));
                    model.setmImage(cursor.getBlob(cursor.getColumnIndex("hinhanh")));
                    model.setmGhiChu(cursor.getString(cursor.getColumnIndex("ghichu")));
                    model.setmMoDau(cursor.getString(cursor.getColumnIndex("modau")));
                    model.setmTinhTrang(cursor.getString(cursor.getColumnIndex("tinhtrang")));
                    Collections.sort(mArrayList, new Comparator<StoryModel>() {
                        @Override
                        public int compare(StoryModel s1, StoryModel s2) {
                            return s1.getmTenTruyen().toLowerCase().compareTo(s2.getmTenTruyen().toLowerCase());
                        }
                    });
                    mArrayList.add(model);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_off, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final ArrayList<StoryModel> fillteredModeList = fileList(mArrayList, newText);
        if (fillteredModeList.size() > 0) {
            mAdapter.setFilter(fillteredModeList);
            return true;
        } else {
            Toast.makeText(OffLineActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    /**
     * query search recyclerview
     *
     * @param models
     * @param query
     * @return
     */
    private ArrayList<StoryModel> fileList(ArrayList<StoryModel> models, String query) {
        query = query.toLowerCase();
        final ArrayList<StoryModel> filteredModelist = new ArrayList<>();
        for (StoryModel storyModel : models) {
            final String text = storyModel.getmTenTruyen().toLowerCase();
            if (text.contains(query)) {
                filteredModelist.add(storyModel);
            }
        }
        mAdapter = new StoryAdapter(filteredModelist, OffLineActivity.this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(OffLineActivity.this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        return filteredModelist;

    }

}
