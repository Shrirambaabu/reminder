package in.myreminder.srb.activity;

import android.content.Intent;
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
import butterknife.OnClick;
import in.myreminder.srb.R;
import in.myreminder.srb.adapter.MyReminderAdapter;
import in.myreminder.srb.database.DatabaseHelper;
import in.myreminder.srb.model.MyNotes;
import in.myreminder.srb.utils.EmptyRecyclerView;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view_empty)
    EmptyRecyclerView emptyRecyclerView;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;
    DatabaseHelper db;
    private ArrayList<MyNotes> myNotesArrayList = new ArrayList<MyNotes>();
    private MyReminderAdapter myReminderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this);
        ButterKnife.bind(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        setupRecyclerView();
        getNotesList();
    }

    private void getNotesList() {
        myNotesArrayList.clear();
        myNotesArrayList.addAll(db.getAllNotes());
        myReminderAdapter.notifyDataSetChanged();
    }

    private void setupRecyclerView() {
        myReminderAdapter = new MyReminderAdapter(myNotesArrayList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        emptyRecyclerView.setLayoutManager(mLayoutManager);
        emptyRecyclerView.setItemAnimator(new DefaultItemAnimator());
        emptyRecyclerView.setHasFixedSize(true);
        emptyRecyclerView.setEmptyView(emptyView);
        emptyRecyclerView.setAdapter(myReminderAdapter);
    }


    @OnClick(R.id.add_notes)
    public void addNotes() {
        Intent intent = new Intent(getApplicationContext(), AddNotesActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
        getNotesList();
    }
}
