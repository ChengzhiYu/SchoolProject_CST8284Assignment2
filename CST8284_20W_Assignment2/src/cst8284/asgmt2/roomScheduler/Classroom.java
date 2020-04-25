/*	Course Name: CST8284
 	Student Name: Chengzhi Yu
 	Class Name: Classroom.java
 	Date: March 1st, 2020
 */

package cst8284.asgmt2.roomScheduler;

public final class Classroom extends Room {
	private int seats = -1;
	private static final int DEFAULT_SEATES = 120;
	public Classroom() {
		super("C442");
	}
	@Override
	protected String getRoomType() {
		return "class room";
	}

	@Override
	protected int getSeats() {
		return DEFAULT_SEATES;
	}

	@Override
	protected String getDetails() {
		return "contains overhead projects";
	}

}
