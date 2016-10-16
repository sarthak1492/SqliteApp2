package com.example.sarthaksharma.sqliteapp;

import android.database.Cursor;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDB;
    private static Button addData;
    private static Button viewAllBtn, updateData;
    private static Button deletebtn;
    EditText editName, editSurname, editMarks, editId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DatabaseHelper(this);

        addData = (Button)findViewById(R.id.button);
        editId = (EditText)findViewById(R.id.editText_id);
        editName = (EditText)findViewById(R.id.editText_name);
        editSurname = (EditText)findViewById(R.id.editText_surname);
        editMarks = (EditText)findViewById(R.id.editText_marks);
        viewAllBtn = (Button)findViewById(R.id.button_viewAll);
        updateData = (Button)findViewById(R.id.button_update);
        deletebtn = (Button)findViewById(R.id.button_delete);
        viewAll();
        addData();
        updateData();
        DeleteData();
    }

    public void viewAll(){
        viewAllBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDB.getALLDATA();
                        if (res.getCount() == 0) {
                            //show message
                            showMessage("Error", "No Data found!!");
                            return;
                        }


                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()){
                            buffer.append("Id: "+res.getString(0)+"\n");
                            buffer.append("Name: "+res.getString(1)+"\n");
                            buffer.append("Surname: "+res.getString(2)+"\n");
                            buffer.append("Marks: "+res.getString(3)+"\n\n");
                        }

                        showMessage("Data", buffer.toString());
                        //show all data
                    }

                }

        );
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void updateData(){
        updateData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDB.updateData(editId.getText().toString(), editName.getText().toString(), editSurname.getText().toString()
                        , editMarks.getText().toString());

                        if (isUpdate == true)
                            Toast.makeText(MainActivity.this, "Data Updates", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_SHORT).show();


                    }
                }
        );
    }

    public void DeleteData(){
        deletebtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deleteRows = myDB.deleteData(editId.getText().toString());
                        if (deleteRows > 0)
                            Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this, "Data not Deleted", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public void addData(){
        addData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      boolean isInserted = myDB.insertData(editName.getText().toString(), editSurname.getText().toString(), editMarks.getText().toString());
                        if (isInserted == true)
                            Toast.makeText(MainActivity.this, "Data is Inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data is not Inserted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
}
