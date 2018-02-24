package com.kait.longlongtran.storyvoz.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kait.longlongtran.storyvoz.Adapter.StoryAdapterOnline;
import com.kait.longlongtran.storyvoz.Model.StoryModelOnline;
import com.kait.longlongtran.storyvoz.OffLineActivity;
import com.kait.longlongtran.storyvoz.R;
import com.kait.longlongtran.storyvoz.Singlaton.SingletonOnline;

import java.util.ArrayList;

public class OnlineActivity extends AppCompatActivity implements StoryAdapterOnline.OnItemClickListener, SearchView.OnQueryTextListener, NavigationView.OnNavigationItemSelectedListener, LinearLayout.OnClickListener {
    private static final String TAG = OnlineActivity.class.getSimpleName();

    private DatabaseReference mDatabase;
    private RecyclerView mRecyclerView;
    private ArrayList<StoryModelOnline> mArrayList = new ArrayList<>();
    private StoryAdapterOnline mAdapter;
    private LinearLayout clk1, clk2, clk3, clk4;
    TextView mTextMatter;
    ImageView mTrangChu;
    private int mChooseQuery;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_online);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
        showDialog();
        mRecyclerView = findViewById(R.id.recyclerViewOnline);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(OnlineActivity.this));
        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(this, R.anim.item_layout_animation);
        mRecyclerView.setLayoutAnimation(controller);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Example");
        addDataFireBase();
        mRecyclerView.scheduleLayoutAnimation();
        mChooseQuery = 1;
        mTextMatter = (TextView) findViewById(R.id.textMatter);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    /**
     * referen to view
     */
    private void init() {
        mTrangChu = findViewById(R.id.img_TrangChu);
        clk1 = (LinearLayout) findViewById(R.id.click1);
        clk2 = (LinearLayout) findViewById(R.id.click2);
        clk3 = (LinearLayout) findViewById(R.id.click3);
        clk4 = (LinearLayout) findViewById(R.id.click4);
        mTrangChu.setOnClickListener(this);
        clk1.setOnClickListener(this);
        clk2.setOnClickListener(this);
        clk3.setOnClickListener(this);
        clk4.setOnClickListener(this);
        mProgressBar = findViewById(R.id.ProgressBar_Online);
    }

    /**
     * add data from firebase
     */
    private void addDataFireBase() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    StoryModelOnline data = snapshot.getValue(StoryModelOnline.class);
                    mArrayList.add(data);
                }
                mAdapter = new StoryAdapterOnline(mArrayList, OnlineActivity.this);
                hideDialog();
                mRecyclerView.setAdapter(mAdapter);
                animationRecyclerView();
                mAdapter.setOnItemClickListener(OnlineActivity.this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Animation RecyclerView
     */
    private void animationRecyclerView() {
        mRecyclerView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mRecyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                for (int i = 0; i < mRecyclerView.getChildCount(); i++) {
                    View view = mRecyclerView.getChildAt(i);
                    view.setAlpha(0.0f);
                    view.animate().alpha(1.0f).setDuration(300).setStartDelay(i * 50).start();
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_online, menu);
        MenuItem item = menu.findItem(R.id.action_search_online);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            Animation animation = AnimationUtils.loadAnimation(OnlineActivity.this, R.anim.slidedown);
            drawer.setAnimation(animation);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, DetailActivityOnline.class);
        StoryModelOnline clickedItem = mArrayList.get(position);
        int sync = SingletonOnline.get().setLargeData(clickedItem);
        intent.putExtra("synconline", sync);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        ArrayList<StoryModelOnline> fillterModeList = null;
        if (mChooseQuery == 1) {
            fillterModeList = fileList(mArrayList, newText);
        } else if (mChooseQuery == 2) {
            fillterModeList = fileListNew(mArrayList, newText);
        } else if (mChooseQuery == 3) {
            fillterModeList = fileListStatus(mArrayList, newText);
        } else if (mChooseQuery == 4) {
            fillterModeList = fileListHotTrend(mArrayList, newText);
        }
        if (fillterModeList.size() > 0) {
            mAdapter.setFilter(fillterModeList);
            mAdapter.setOnItemClickListener(OnlineActivity.this);
            return true;
        } else {
            Toast.makeText(OnlineActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * query model ten truyen
     *
     * @param models
     * @param query
     * @return
     */
    private ArrayList<StoryModelOnline> fileList(ArrayList<StoryModelOnline> models, String query) {
        query = query.toLowerCase();
        final ArrayList<StoryModelOnline> filteredModelist = new ArrayList<>();
        for (StoryModelOnline storyModel : models) {
            if (storyModel.getmTenTruyen() != null) {
                final String text = storyModel.getmTenTruyen().toLowerCase();
                if (text.contains(query)) {
                    filteredModelist.add(storyModel);
                }
            }

        }
        mAdapter = new StoryAdapterOnline(filteredModelist, OnlineActivity.this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(OnlineActivity.this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        return filteredModelist;
    }

    /**
     * query model new on database
     *
     * @param models
     * @param query
     * @return
     */
    private ArrayList<StoryModelOnline> fileListNew(ArrayList<StoryModelOnline> models, String query) {
        query = query.toLowerCase();
        final ArrayList<StoryModelOnline> filteredModelist = new ArrayList<>();
        for (StoryModelOnline storyModel : models) {
            if (storyModel.getmNew() != null) {
                final String text = storyModel.getmNew().toLowerCase();
                if (text.contains(query)) {
                    filteredModelist.add(storyModel);
                }
            }

        }
        mAdapter = new StoryAdapterOnline(filteredModelist, OnlineActivity.this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(OnlineActivity.this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        return filteredModelist;
    }

    /**
     * query status of Story
     *
     * @param models
     * @param query
     * @return
     */
    private ArrayList<StoryModelOnline> fileListStatus(ArrayList<StoryModelOnline> models, String query) {
        query = query.toLowerCase();
        final ArrayList<StoryModelOnline> filteredModelist = new ArrayList<>();
        for (StoryModelOnline storyModel : models) {
            if (storyModel.getmTinhTrang() != null) {
                final String text = storyModel.getmTinhTrang().toLowerCase();
                if (text.contains(query)) {
                    filteredModelist.add(storyModel);
                }
            }

        }
        mAdapter = new StoryAdapterOnline(filteredModelist, OnlineActivity.this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(OnlineActivity.this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        return filteredModelist;
    }

    /**
     * query story hot trend
     *
     * @param models
     * @param query
     * @return
     */
    private ArrayList<StoryModelOnline> fileListHotTrend(ArrayList<StoryModelOnline> models, String query) {
        query = query.toLowerCase();
        final ArrayList<StoryModelOnline> filteredModelist = new ArrayList<>();
        for (StoryModelOnline storyModel : models) {
            if (storyModel.getmTheLoai() != null) {

                final String text = storyModel.getmTheLoai().toLowerCase();
                if (text.contains(query)) {
                    filteredModelist.add(storyModel);
                }
            }
        }
        mAdapter = new StoryAdapterOnline(filteredModelist, OnlineActivity.this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(OnlineActivity.this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        return filteredModelist;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * show dialog progress
     */
    private void showDialog() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * check dialog and hide dialog
     */
    private void hideDialog() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    /**
     * set background linearlayout item
     *
     * @param chooseQuery
     */
    private void setBackGround(int chooseQuery) {
        if (chooseQuery == 1) {
            mTrangChu.setBackgroundColor(getResources().getColor(R.color.tvtacgia));
            clk1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryText));
            clk2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryText));
            clk3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryText));
            clk4.setBackgroundColor(getResources().getColor(R.color.colorPrimaryText));
        } else if (chooseQuery == 2) {
            mTrangChu.setBackgroundColor(getResources().getColor(R.color.colorPrimaryText));
            clk1.setBackgroundColor(getResources().getColor(R.color.tvtacgia));
            clk2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryText));
            clk3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryText));
            clk4.setBackgroundColor(getResources().getColor(R.color.colorPrimaryText));
        } else if (chooseQuery == 3) {
            mTrangChu.setBackgroundColor(getResources().getColor(R.color.colorPrimaryText));
            clk1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryText));
            clk2.setBackgroundColor(getResources().getColor(R.color.tvtacgia));
            clk3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryText));
            clk4.setBackgroundColor(getResources().getColor(R.color.colorPrimaryText));
        } else if (chooseQuery == 4) {
            mTrangChu.setBackgroundColor(getResources().getColor(R.color.colorPrimaryText));
            clk1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryText));
            clk2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryText));
            clk3.setBackgroundColor(getResources().getColor(R.color.tvtacgia));
            clk4.setBackgroundColor(getResources().getColor(R.color.colorPrimaryText));
        } else if (chooseQuery == 5) {
            mTrangChu.setBackgroundColor(getResources().getColor(R.color.colorPrimaryText));
            clk1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryText));
            clk2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryText));
            clk3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryText));
            clk4.setBackgroundColor(getResources().getColor(R.color.tvtacgia));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_TrangChu:
                mChooseQuery = 1;
                onQueryTextChange("");
                onBackPressed();
                setBackGround(mChooseQuery);
                break;
            case R.id.click1:
                mChooseQuery = 2;
                onQueryTextChange("new");
                onBackPressed();
                setBackGround(mChooseQuery);
                break;
            case R.id.click2:
                mChooseQuery = 3;
                onQueryTextChange("Hoàn Thành");
                onBackPressed();
                setBackGround(mChooseQuery);
                break;
            case R.id.click3:
                mChooseQuery = 4;
                onQueryTextChange("Huyền Thoại");
                onBackPressed();
                setBackGround(mChooseQuery);
                break;
            case R.id.click4:
                mChooseQuery = 5;
                onBackPressed();
                setBackGround(mChooseQuery);
                Intent intent = new Intent(this, OffLineActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
        }
    }


}
