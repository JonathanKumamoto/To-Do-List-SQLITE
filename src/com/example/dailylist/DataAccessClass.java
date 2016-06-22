package com.example.dailylist;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DataAccessClass {

  // Database fields
  private SQLiteDatabase database;
  private JonathanSQLiteHelper dbHelper;
  private String[] allColumns = { JonathanSQLiteHelper.COLUMN_ID,
      JonathanSQLiteHelper.COLUMN_COMMENT };

  public DataAccessClass(Context context) {
    dbHelper = new JonathanSQLiteHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  public ListObject createComment(String comment) {
    ContentValues values = new ContentValues();
    values.put(JonathanSQLiteHelper.COLUMN_COMMENT, comment);
    long insertId = database.insert(JonathanSQLiteHelper.TABLE_COMMENTS, null,
        values);
    Cursor cursor = database.query(JonathanSQLiteHelper.TABLE_COMMENTS,
        allColumns, JonathanSQLiteHelper.COLUMN_ID + " = " + insertId, null,
        null, null, null);
    cursor.moveToFirst();
    ListObject newComment = cursorToComment(cursor);
    cursor.close();
    return newComment;
  }

  public void deleteComment(ListObject comment) {
    long id = ((ListObject) comment).getId();
    System.out.println("Comment deleted with id: " + id);
    database.delete(JonathanSQLiteHelper.TABLE_COMMENTS, JonathanSQLiteHelper.COLUMN_ID
        + " = " + id, null);
  }

  public List<ListObject> getAllComments() {
    List<ListObject> comments = new ArrayList<ListObject>();

    Cursor cursor = database.query(JonathanSQLiteHelper.TABLE_COMMENTS,
        allColumns, null, null, null, null, null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      ListObject comment = cursorToComment(cursor);
      comments.add(comment);
      cursor.moveToNext();
    }
    // make sure to close the cursor
    cursor.close();
    return comments;
  }

  private ListObject cursorToComment(Cursor cursor) {
	ListObject comment = new ListObject();
    comment.setId(cursor.getLong(0));
    comment.setComment(cursor.getString(1));
    return comment;
  }
} 

