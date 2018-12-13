package in.myreminder.srb.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.myreminder.srb.R;
import in.myreminder.srb.adapter.AlertAdapter;
import in.myreminder.srb.model.MyAlert;
import in.myreminder.srb.utils.EmptyRecyclerView;

import static in.myreminder.srb.utils.Utils.backButtonOnToolbar;

public class AddReminderActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view_empty)
    EmptyRecyclerView emptyRecyclerView;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;

    private ArrayList<MyAlert> myAlertArrayList = new ArrayList<MyAlert>();
    private AlertAdapter alertAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        ButterKnife.bind(this);
        backButtonOnToolbar(this);
        setupRecyclerView();
        swipeRefreshLayout.setOnRefreshListener(this);
        getAlertList();
    }

    private void getAlertList() {

        MyAlert myAlertModel = new MyAlert();

        myAlertArrayList.add(myAlertModel);
        myAlertArrayList.add(myAlertModel);
        myAlertArrayList.add(myAlertModel);
        myAlertArrayList.add(myAlertModel);
        myAlertArrayList.add(myAlertModel);
        myAlertArrayList.add(myAlertModel);
        alertAdapter.notifyDataSetChanged();
    }

    private void setupRecyclerView() {
        alertAdapter = new AlertAdapter(myAlertArrayList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        emptyRecyclerView.setLayoutManager(mLayoutManager);
        emptyRecyclerView.setItemAnimator(new DefaultItemAnimator());
        emptyRecyclerView.setHasFixedSize(true);
        emptyRecyclerView.setEmptyView(emptyView);
        emptyRecyclerView.setAdapter(alertAdapter);
    }


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        return true;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }
}
