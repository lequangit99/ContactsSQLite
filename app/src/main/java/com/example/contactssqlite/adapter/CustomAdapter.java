package com.example.contactssqlite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.contactssqlite.R;
import com.example.contactssqlite.model.Contact;

import java.util.List;

public class CustomAdapter extends ArrayAdapter {
    private Context context;
    private int resoure;
    private List<Contact> contactList;

    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<Contact> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resoure = resource;
        this.contactList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_contact,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tvId = (TextView)convertView.findViewById(R.id.tv_id);
            viewHolder.tvName = (TextView)convertView.findViewById(R.id.tv_name);
            viewHolder.tvPhone = (TextView)convertView.findViewById(R.id.tv_phone);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Contact contact = contactList.get(position);
        viewHolder.tvId.setText(String.valueOf(contact.getmId()));
        viewHolder.tvName.setText(contact.getmName());
        viewHolder.tvPhone.setText(contact.getmPhoneNumber());

        return convertView;
    }

    public class ViewHolder{

        private TextView tvId;
        private TextView tvName;
        private TextView tvPhone;

    }
}
