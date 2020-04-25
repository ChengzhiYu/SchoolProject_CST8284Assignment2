/*	Course Name: CST8284
 	Student Name: Chengzhi Yu
 	Class Name: Boardroom.java
 	Date: March 1st, 2020
 */

package cst8284.asgmt2.roomScheduler;

public final class Boardroom extends Room {
	private int seats = 16;
	
	public Boardroom() {
		super("B321");
	}
	@Override
	protected String getRoomType() {
		
		return "board room";
	}

	@Override
	protected int getSeats() {
		
		return seats;
	}

	@Override
	protected String getDetails() {
		
		return "conferebce call enabled";
	}

}
