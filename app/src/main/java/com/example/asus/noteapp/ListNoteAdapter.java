package com.example.asus.noteapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Asus on 7/8/2017.
 */

public class ListNoteAdapter extends BaseAdapter {

    Context context;
    List<Note> noteList;
    LayoutInflater inflater;

    MyViewHolder myViewHolder;

    public ListNoteAdapter(Context context, List<Note> noteList) {
        this.context = context;
        this.noteList = noteList;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return noteList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row, null);
            myViewHolder = new MyViewHolder();
            convertView.setTag(myViewHolder);
        } else {
            //Luu lai du lieu khi keo len xuong
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        myViewHolder.textTitle = (TextView) convertView.findViewById(R.id.text_title);
//        myViewHolder.textContent.setText(noteList.get(position).getContent());
        myViewHolder.textTitle.setText(noteList.get(position).getTitle());
        return convertView;
    }

    public static class MyViewHolder {
        TextView textTitle;
        TextView textContent;
        TextView textId;
    }
}
