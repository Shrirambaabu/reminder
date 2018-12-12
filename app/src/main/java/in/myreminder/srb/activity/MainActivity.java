package in.myreminder.srb.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.myreminder.srb.R;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @OnClick(R.id.add_notes)
    public void addNotes() {
        Intent intent = new Intent(getApplicationContext(), AddNotesActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }
}
