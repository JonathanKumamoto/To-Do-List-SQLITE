package com.example.dailylist;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.List;
import java.util.Random;

import android.app.ListActivity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

public class MainActivity extends ListActivity {
  private DataAccessClass datasource;
  //
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    datasource = new DataAccessClass(this);
    datasource.open();
    List<ListObject> values = datasource.getAllComments();

    // use the SimpleCursorAdapter to show the
    // elements in a ListView
    ArrayAdapter<ListObject> adapter = new ArrayAdapter<ListObject>(this,
        android.R.layout.simple_list_item_1, values);
    setListAdapter(adapter);

  }

  // Will be called via the onClick attribute
  // of the buttons in main.xml
  public void onClick(View view) {
	  
	  //Grab the string from the input object
	 EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
	 String itemText = etNewItem.getText().toString();
    
    @SuppressWarnings("unchecked")
    ArrayAdapter<ListObject> adapter = (ArrayAdapter<ListObject>) getListAdapter();
    ListObject comment = null;
    switch (view.getId()) {
    case R.id.add:
      // save the new comment to the database
    
      //---[addText]--- The purpose of this is to reset the input and if there is no input add a random string from a set
      if(itemText.length() > 0){
    	  comment = datasource.createComment(itemText);
    	  etNewItem.setText("");
      }else{
    	  String[] comments = new String[] { "Jonathan Kumamoto", "Welcome", "Store SQLite" };
          int nextInt = new Random().nextInt(3);
          comment = datasource.createComment(comments[nextInt]);
      }
      //---END of [addText]---
      adapter.add(comment);
      break;
    case R.id.delete:
      if (getListAdapter().getCount() > 0) {
        comment = (ListObject) getListAdapter().getItem(0);
        datasource.deleteComment(comment);
        adapter.remove(comment);
      }
      break;
    }
    
    //---[closeView]---The purpose of this is check if the keyboard is open, and if it is, automatically close every possible keyboard
    View view2 = this.getCurrentFocus();
    if (view2 != null) {  
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    //--END of [closeView]---
    
    adapter.notifyDataSetChanged();
  }

  @Override
  protected void onResume() {
    datasource.open();
    super.onResume();
  }

  @Override
  protected void onPause() {
    datasource.close();
    super.onPause();
  }

} 

