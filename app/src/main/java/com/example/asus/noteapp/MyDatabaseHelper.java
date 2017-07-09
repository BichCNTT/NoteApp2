package com.example.asus.noteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Asus on 7/7/2017.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Note.db";
    private static final String TABLE_NAME = "NoteDetail";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_CONTENT = "content";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "MyDatabaseHelper.onCreate...");
        String sQLite = "create table if not exists " + TABLE_NAME + "(" + COLUMN_ID + " integer, "
                + COLUMN_TITLE + " nvarchar, " + COLUMN_CONTENT + " text)";
        db.execSQL(sQLite);
    }

    public void createDefaultNotesIfNeed() {
        int count = this.countNote();
        if (count == 0) {
            Note note1 = new Note(0, "Firstly see Android ListView",
                    "See Android ListView Example in o7planning.org");
            Note note2 = new Note(1, "Learning Android SQLite",
                    "See Android SQLite Example in o7planning.org");
            this.addNote(note1);
            this.addNote(note2);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "MyDatabaseHelper.onUpgrade...");
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }

    public int countNote() {
        Log.i(TAG, "MyDatabaseHelper.countNote...");
        int count = 0;
        String sQLite = "select * from " + TABLE_NAME;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(sQLite, null);
        count = cursor.getCount();
        cursor.close();
        return count;
    }

    public void addNote(Note note) {
        Log.i(TAG, "MyDatabaseHelper.addNote " + note.getTitle());
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, note.getId());
        contentValues.put(COLUMN_TITLE, note.getTitle());
        contentValues.put(COLUMN_CONTENT, note.getContent());
        database.insert(TABLE_NAME, null, contentValues);
        database.close();
    }

    public int updateNote(Note note) {
        Log.i(TAG, "MyDatabaseHelper.updateNote..." + note.getId());
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, note.getTitle());
        contentValues.put(COLUMN_CONTENT, note.getContent());
        return database.update(TABLE_NAME, contentValues, COLUMN_ID + " = ?", new String[]{String.valueOf(note.getId())});
    }

    public void deleteNote(Note note) {
        Log.i(TAG, "MyDatabaseHelper.deleteNote " + note.getId());
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(note.getId())});
        database.close();
    }

    public List<Note> getAllNote() {
        Log.i(TAG, "MyDatabaseHelper.getAllNote");

        List<Note> noteList = new ArrayList<Note>();

        String sQLite = "Select * from " + TABLE_NAME;

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(sQLite, null);

        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(Integer.parseInt(cursor.getString(0)));
                note.setTitle(String.valueOf(cursor.getString(1)));
                note.setContent(String.valueOf(cursor.getString(2)));
                noteList.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return noteList;
    }

    public Note getANote(int id) {
        Log.i(TAG, "MyDatabaseHelper.getANote " + id);
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME,
                new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_CONTENT}, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Note note = new Note(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
        return note;
    }

}
