/*	Course Name: CST8284
 	Student Name: Chengzhi Yu
 	Class Name: RoomSchedulerLauncher.java
 	Date: March 1st, 2020
 */


/*  Course Name: CST8284
    Author: Prof. Dave Houtman
    Class name: RoomSchedulerLauncher.java
    Date: February 11, 2020
*/ 
package cst8284.asgmt2.roomScheduler;


public class RoomSchedulerLauncher {

	public static void main(String[] args) {
		
		ComputerLab lab = new ComputerLab();
		//Classroom cr = new Classroom();
		 new RoomScheduler(lab).launch();
		 	}
}