package com.example.databasesql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnAdd, btnRead, btnClear;
    EditText etUsername, etPassword;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);

        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);

        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View v) {

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();


        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        switch (v.getId()) {


            case R.id.btnAdd:

                contentValues.put(DBHelper.KEY_USERNAME, username);
                contentValues.put(DBHelper.KEY_PASSWORD, password);

                database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);

                break;

            case R.id.btnRead:
                Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null, null,
                        null, null, null, null);

                if (cursor.moveToFirst()) {
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int nameIndex = cursor.getColumnIndex(DBHelper.KEY_USERNAME);
                    int emailIndex = cursor.getColumnIndex(DBHelper.KEY_PASSWORD);
                    do {
                        Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                                ", username = " + cursor.getString(nameIndex) +
                                ", password = " + cursor.getString(emailIndex));
                    } while (cursor.moveToNext());
                } else
                    Log.d("mLog","0 rows");

                cursor.close();

                break;

            case R.id.btnClear:
                database.delete(DBHelper.TABLE_CONTACTS, null, null);

                break;

        }
        dbHelper.close();
    }
}
