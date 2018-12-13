package in.myreminder.srb.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
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

import static in.myreminder.srb.utils.Utils.backButtonOnToolbar;

public class AddNotesActivity extends AppCompatActivity {

    private ArrayList<String> stringArrayList = new ArrayList<String>();

    @BindView(R.id.textInputLayoutSpinner)
    Spinner spinner;

    @BindView(R.id.textInputEditTextDate)
    TextInputEditText textInputEditTextDate;
    @BindView(R.id.textInputEditTextName)
    TextInputEditText textInputEditTextName;
    final Calendar myCalendar = Calendar.getInstance();
    int position = -1;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);
        db = new DatabaseHelper(this);
        ButterKnife.bind(this);
        backButtonOnToolbar(this);
        setupSpinner();


    }


    private void setupSpinner() {
        stringArrayList.add("Select Priority");
        stringArrayList.add("Normal");
        stringArrayList.add("Medium");
        stringArrayList.add("High");


        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stringArrayList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                position = pos;
                Log.e("Pos", "" + pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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

        textInputEditTextDate.setText(sdf.format(myCalendar.getTime()));
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

    @OnClick(R.id.textInputEditTextDate)
    public void dateSelect() {
        new DatePickerDialog(AddNotesActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }


    @OnClick(R.id.save_notes)
    public void saveNotes() {
        if (textInputEditTextName.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Enter Full Name", Toast.LENGTH_LONG).show();
            return;
        }
        if (position == 0) {
            Toast.makeText(getApplicationContext(), "Select Priority", Toast.LENGTH_LONG).show();
            return;
        }
        if (textInputEditTextDate.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Select Date", Toast.LENGTH_LONG).show();
            return;
        }

        saveValuestoDB(textInputEditTextName.getText().toString(),spinner.getSelectedItem().toString(),textInputEditTextDate.getText().toString());
    }

    private void saveValuestoDB(String name, String priority, String date) {

        Log.e("Values",""+name);
        Log.e("Values",""+priority);
        Log.e("Values",""+date);
        MyNotes myNotes=new MyNotes();

        myNotes.setNotesName(name);
        myNotes.setNotesDate(date);
        myNotes.setNotesPriority(priority);
        myNotes.setNotesRead("0");

        long rowInserted = db.addNotes(myNotes);

        if (rowInserted != -1) {
            Toast.makeText(getApplicationContext(), "Note Added", Toast.LENGTH_LONG).show();
            Intent intent=new Intent(AddNotesActivity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

}
