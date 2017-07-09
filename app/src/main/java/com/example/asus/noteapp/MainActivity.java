package com.example.asus.noteapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Khai báo 1 đối tượng kiểu listview
    ListView listView;

    private static final int MENU_ITEM_VIEW = 111;
    private static final int MENU_ITEM_EDIT = 222;
    private static final int MENU_ITEM_CREATE = 333;
    private static final int MENU_ITEM_DELETE = 444;
    private static final int MY_REQUEST_CODE = 1000;

    //khai báo 1 mảng đối tượng
    private final List<Note> noteList = new ArrayList<Note>();
    private ArrayAdapter<Note> adapter =null;
//    ListNoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(this);
        myDatabaseHelper.createDefaultNotesIfNeed();

        List<Note> list = myDatabaseHelper.getAllNote();
        noteList.addAll(list);

        adapter = new ArrayAdapter<Note>(this,android.R.layout.simple_list_item_1 ,android.R.id.text1, noteList);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select the action");

        menu.add(0, MENU_ITEM_CREATE, 0, "Create note");
        menu.add(0, MENU_ITEM_DELETE, 1, "Delete note");
        menu.add(0, MENU_ITEM_EDIT, 2, "Edit note");
        menu.add(0, MENU_ITEM_VIEW, 3, "View note");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Note selectNote = (Note) this.listView.getItemAtPosition(info.position);
        if (item.getItemId() == MENU_ITEM_VIEW) {
            Toast.makeText(getApplicationContext(), selectNote.getContent(), Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == MENU_ITEM_CREATE) {
            Intent intent = new Intent(this, AddEditNoteActivity.class);
            this.startActivityForResult(intent, MY_REQUEST_CODE);
        } else if (item.getItemId() == MENU_ITEM_EDIT) {
            Intent intent = new Intent(this, AddEditNoteActivity.class);
            intent.putExtra("note", selectNote);
            this.startActivityForResult(intent, MY_REQUEST_CODE);
        } else if (item.getItemId() == MENU_ITEM_DELETE) {
            new AlertDialog.Builder(this)
                    .setMessage(selectNote.getTitle() + ".Are you sure want to delete?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            deleteNote(selectNote);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        } else {
            return false;
        }
        return true;
    }

    private void deleteNote(Note note) {
        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(this);
        myDatabaseHelper.deleteNote(note);
        this.noteList.remove(note);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == MY_REQUEST_CODE) {
            boolean needRefresh = data.getBooleanExtra("needRefresh", true);
            if (needRefresh) {
                this.noteList.clear();
                MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(this);
                List<Note> list = myDatabaseHelper.getAllNote();
                this.noteList.addAll(list);
                this.adapter.notifyDataSetChanged();
            }
        }
    }
}
