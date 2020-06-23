package com.example.contactssqlite;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.contactssqlite.adapter.CustomAdapter;
import com.example.contactssqlite.data.DBContext;
import com.example.contactssqlite.model.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listViewContacts;
    private FloatingActionButton floatingActionButton;
    private CustomAdapter customAdapter;
    private DBContext dbContext;
    private List<Contact> contactList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkAndRequestPermissions();
        dbContext = new DBContext(this);
        initView();
        contactList = dbContext.getAllContact();
        setAdapter();
        registerForContextMenu(listViewContacts);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddContact.class);
                intent.putExtra("code",1);
                startActivity(intent);
            }
        });

        listViewContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contact contact = contactList.get(i);
                Intent intent = new Intent(MainActivity.this, AddContact.class);
                intent.putExtra("code", 2);
                intent.putExtra("id", contact.getmId());
                intent.putExtra("name",contact.getmName());
                intent.putExtra("phone",contact.getmPhoneNumber());
                intent.putExtra("ns",contact.getmBirthday());
                intent.putExtra("email", contact.getmEmail());
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.options_menu, menu);

        return true;

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.action_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.main_sort_id_option) {
            Collections.sort(contactList, new Comparator<Contact>() {
                @Override
                public int compare(Contact contact, Contact t1) {
                    return String.valueOf(contact.getmId()).compareTo(String.valueOf(t1.getmId()));
                }
            });
            customAdapter.notifyDataSetChanged();
        }
        if (item.getItemId() == R.id.main_sort_name_option){
            Collections.sort(contactList, new Comparator<Contact>() {
                @Override
                public int compare(Contact contact, Contact t1) {
                    return contact.getmName().compareTo(t1.getmName());
                }
            });
            customAdapter.notifyDataSetChanged();
        }

        if (item.getItemId() == R.id.main_sort_phone_option){
            Collections.sort(contactList, new Comparator<Contact>() {
                @Override
                public int compare(Contact contact, Contact t1) {
                    return contact.getmPhoneNumber().compareTo(t1.getmPhoneNumber());
                }
            });
            customAdapter.notifyDataSetChanged();
        }

        return true;
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){
            case R.id.detele_contacts:
                Contact contact = contactList.get(menuInfo.position);
                dbContext.deleteContact(contact.getmId());
                updateListStudent();
                return true;
            case R.id.call_contacts:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + contactList.get(menuInfo.position).getmPhoneNumber()));
                startActivity(intent);
                return true;
            case R.id.sms_contacts:
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + contactList.get(menuInfo.position).getmPhoneNumber()));
                startActivity(intent1);
                return true;
            case R.id.email_contacts:
                Intent intent2 = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + contactList.get(menuInfo.position).getmEmail()));
                startActivity(intent2);
                return true;
        }

        return super.onContextItemSelected(item);
    }

    public void initView(){
        listViewContacts = (ListView) findViewById(R.id.lst_contact);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.btnF_Add);
    }

    private void setAdapter() {
        if (customAdapter == null) {
            customAdapter = new CustomAdapter(this, R.layout.item_contact, contactList);
            listViewContacts.setAdapter(customAdapter);
        }else{
            customAdapter.notifyDataSetChanged();
            listViewContacts.setSelection(customAdapter.getCount()-1);
        }
    }

    public void updateListStudent(){
        contactList.clear();
        contactList.addAll(dbContext.getAllContact());
        if(customAdapter!= null){
            customAdapter.notifyDataSetChanged();
        }
    }

    private void checkAndRequestPermissions() {
        String[] permissions = new String[]{
                Manifest.permission.CALL_PHONE,
                Manifest.permission.SEND_SMS
        };
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
        }
    }
}