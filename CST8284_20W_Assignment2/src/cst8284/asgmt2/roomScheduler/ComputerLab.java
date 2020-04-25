/*	Course Name: CST8284
 	Student Name: Chengzhi Yu
 	Class Name: ComputerLab.java
 	Date: March 1st, 2020
 */
package cst8284.asgmt2.roomScheduler;

public final class ComputerLab extends Room {
	private int seats = -1;
	private static final int DEFAULT_SEATES = 30;
	public ComputerLab() {
		super("B119");
	}
	@Override
	protected String getRoomType() {
		return "computer lab";
	}

	@Override
	protected int getSeats() {
		
		return DEFAULT_SEATES;
	}

	@Override
	protected String getDetails() {
		return "contains outlets for 30 laptops";
	}

}
