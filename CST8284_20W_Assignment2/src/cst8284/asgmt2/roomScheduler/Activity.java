/*	Course Name: CST8284
 	Student Name: Chengzhi Yu
 	Class Name: Activity.java
 	Date: March 1st, 2020
 */

/*  Course Name: CST8284
    Author: Prof. Dave Houtman
    Class name: Activity.java
    Date: February 11, 2020
*/ 
package cst8284.asgmt2.roomScheduler;

import java.io.Serializable;


public class Activity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String category, description;
	
	public Activity(String category, String description) {
		setCategory(category); setDescription(description);	
	}
	
	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}
	
	public String getCategory() {return category;}
	public void setCategory(String category) {this.category = category;}
	
	@Override
	public String toString() {
		return  "Event: " + getCategory() + "\n" + 
			((getDescription()!="")?"Description: " + getDescription():"") + "\n";
	}
}

