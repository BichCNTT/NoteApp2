package com.example.asus.noteapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddEditNoteActivity extends AppCompatActivity implements View.OnClickListener {
    Note note;
    private EditText edtTitle;
    private EditText edtContent;
    Button btSave;
    Button btCancel;
    int i = 2;
    int mode;
    private static final int MODE_CREATE = 1;
    private static final int MODE_EDIT = 2;

    private boolean needRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);

        edtTitle = (EditText) findViewById(R.id.edit_title);
        edtContent = (EditText) findViewById(R.id.edit_content);
        btSave = (Button) findViewById(R.id.button_save);
        btCancel = (Button) findViewById(R.id.button_cancel);

        //Lấy thư
        Intent intent = this.getIntent();
        //lấy node từ thư qua tên "note"
        this.note = (Note) intent.getSerializableExtra("note");
        //Nếu g.trị note=null -> không có gì thì mode create=1
        if (note == null) {
            this.mode = MODE_CREATE;
        } else {
            this.mode = MODE_EDIT;
            edtTitle.setText(note.getTitle());
            edtContent.setText(note.getContent());
        }
        btSave.setOnClickListener(this);
        btCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_save:
                MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(this);
                String title = this.edtTitle.getText().toString();
                String content = this.edtContent.getText().toString();
                if (title.equals("") || (content.equals(""))) {
                    Toast.makeText(getApplicationContext(), "Please enter content & title", Toast.LENGTH_LONG).show();
                    return;
                }
                if (mode == MODE_CREATE) {
                    this.note = new Note();
                    this.note.setId(i++);
                    this.note.setTitle(title);
                    this.note.setContent(content);
                    myDatabaseHelper.addNote(note);
                } else {
                    this.note.setTitle(title);
                    this.note.setContent(content);
                    myDatabaseHelper.updateNote(note);
                }
                this.needRefresh = true;
                this.onBackPressed();
                break;
            case R.id.button_cancel:
                this.onBackPressed();
                break;
            default:
                break;
        }
    }

    @Override
    public void finish() {
        Intent data = new Intent();
        data.putExtra("needRefresh", needRefresh);
        this.setResult(RESULT_OK, data);
        super.finish();
    }
}
