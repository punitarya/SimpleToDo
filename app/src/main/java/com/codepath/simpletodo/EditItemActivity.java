package com.codepath.simpletodo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Activity for adding or editing an item
 */
public class EditItemActivity extends AppCompatActivity {
    ActionType actionType;
    EditText etName;
    TextView txtDate;
    EditText etNotes;

    Calendar calendar;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        etName = (EditText) findViewById(R.id.etTaskName);
        txtDate = (TextView) findViewById(R.id.txtDate);
        etNotes = (EditText) findViewById(R.id.etTaskNotes);
        actionType =ActionType.valueOf(getIntent().getStringExtra("actionType"));

        // read the intent
        String item = getIntent().getStringExtra("itemName");
        String date = getIntent().getStringExtra("itemDate");
        String notes = getIntent().getStringExtra("itemNotes");

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        if (actionType == ActionType.ADD) {
            String title = getString(R.string.add_item);
            this.setTitle(title);
            showDate(year, month+1, day);
        } else {
            etName.setText(item);
            etName.setSelection(item.length());
            etNotes.setText(notes);
            txtDate.setText(date);
        }

    }

    public void onSave(View v) {
        // Prepare data intent
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("itemName", etName.getText().toString());
        data.putExtra("itemDate", txtDate.getText().toString());
        data.putExtra("itemNotes", etNotes.getText().toString());
        data.putExtra("actionType", actionType.toString());

        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }

    public  void onSetDate(View v){
        showDialog(999);
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        txtDate.setText(new StringBuilder().append(month).append("/")
                .append(day).append("/").append(year));
    }
}
