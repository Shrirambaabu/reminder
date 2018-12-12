package in.myreminder.srb.activity;

import android.app.DatePickerDialog;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.myreminder.srb.R;

import static in.myreminder.srb.utils.Utils.backButtonOnToolbar;

public class AddNotesActivity extends AppCompatActivity {

    private ArrayList<String> stringArrayList=new ArrayList<String>();

    @BindView(R.id.spinner)
    Spinner spinner;

    @BindView(R.id.textInputEditTextDate)
    TextInputEditText textInputEditTextDate;
    final Calendar myCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);
        ButterKnife.bind(this);
        backButtonOnToolbar(this);
        setupSpinner();

    }

    private void setupSpinner() {
        stringArrayList.add("Select Priority");
        stringArrayList.add("Normal");
        stringArrayList.add("Medium");
        stringArrayList.add("High");


        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,stringArrayList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);
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
    public void dateSelect(){
        new DatePickerDialog(AddNotesActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

}
