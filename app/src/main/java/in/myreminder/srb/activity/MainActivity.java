package in.myreminder.srb.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.myreminder.srb.R;
import in.myreminder.srb.adapter.MyReminderAdapter;
import in.myreminder.srb.database.DatabaseHelper;
import in.myreminder.srb.model.MyNotes;
import in.myreminder.srb.utils.EmptyRecyclerView;

import static in.myreminder.srb.utils.Utils.showFancyDate;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view_empty)
    EmptyRecyclerView emptyRecyclerView;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;
    @BindView(R.id.date_value)
    TextView dateTextView;
    DatabaseHelper db;
    private ArrayList<MyNotes> myNotesArrayList = new ArrayList<MyNotes>();
    private MyReminderAdapter myReminderAdapter;
    final Calendar myCalendar = Calendar.getInstance();
    private String selectedDate = "";

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
        dateTextView.setText("All Notes");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.calendar:
                new DatePickerDialog(MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                return true;
            case R.id.alert:
                Intent intent = new Intent(getApplicationContext(), AddReminderActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {
        String myFormat = "dd-MM-yyyy"; //In which y
        // ou need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        selectedDate = sdf.format(myCalendar.getTime());

        dateTextView.setText(showFancyDate(selectedDate));
        myNotesArrayList.clear();
        myNotesArrayList.addAll(db.getParticularNote(selectedDate));
        myReminderAdapter.notifyDataSetChanged();
    }
}
