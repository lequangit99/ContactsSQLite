package com.example.contactssqlite;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.contactssqlite.data.DBContext;
import com.example.contactssqlite.model.Contact;

public class AddContact extends AppCompatActivity {
    private EditText edtName, edtPhone;
    private Button btnSave;
    private DBContext dbContext;
    private TextView txtNgaySinh;
    private int mYear, mMonth, mDay, mHour, mMinute;

    private int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        initView();
        dbContext = new DBContext(this);
        final Intent intent = getIntent();
        code = intent.getIntExtra("code",0);

        if (code == 2){
            edtName.setText(intent.getStringExtra("name"));
            edtPhone.setText(intent.getStringExtra("phone"));
            txtNgaySinh.setText(intent.getStringExtra("ns"));
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (code == 1){
                    Contact contact = createContact();
                    if (contact != null) {
                        dbContext.addContact(contact);
                        Intent intent = new Intent(AddContact.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
                if (code == 2){
                    Contact contact = new Contact();
                    contact.setmId(intent.getIntExtra("id",0));
                    contact.setmName(edtName.getText().toString());
                    contact.setmPhoneNumber(edtPhone.getText().toString());
                    contact.setmBirthday(txtNgaySinh.getText().toString());
                    dbContext.updateContact(contact);
                    Intent intent = new Intent(AddContact.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        txtNgaySinh.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(AddContact.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                txtNgaySinh.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

    }

    private void initView(){
        edtName = (EditText) findViewById(R.id.edt_Name);
        edtPhone = (EditText) findViewById(R.id.edt_Phone);
        btnSave = (Button) findViewById(R.id.btn_Save);
        txtNgaySinh = (TextView) findViewById(R.id.txtHien_Birthday);
    }

    private Contact createContact() {
        String name = edtName.getText().toString();
        String phoneNumber = edtPhone.getText().toString();
        String Birthday = txtNgaySinh.getText().toString();

        Contact contact = new Contact(name,phoneNumber,Birthday);
        return contact;
    }
}