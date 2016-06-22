package com.example.dailylist;

public class ListObject {
	  private long id;
	  private String object;

	  public long getId() {
	    return id;
	  }

	  public void setId(long id) {
	    this.id = id;
	  }

	  public String getComment() {
	    return object;
	  }

	  public void setComment(String comment) {
	    this.object = comment;
	  }

	  // Will be used by the ArrayAdapter in the ListView
	  @Override
	  public String toString() {
	    return object;
	  }
	} 
