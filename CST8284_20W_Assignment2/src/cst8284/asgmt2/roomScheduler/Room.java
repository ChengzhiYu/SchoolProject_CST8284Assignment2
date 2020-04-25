/*	Course Name: CST8284
 	Student Name: Chengzhi Yu
 	Class Name: Room.java
 	Date: March 1st, 2020
 */
package cst8284.asgmt2.roomScheduler;

import java.io.Serializable;

public abstract class Room implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static String DEFAULT_ROOM_NUMBER = "unknown room number";
	private String roomNumber;
	
	protected Room() {
		this(DEFAULT_ROOM_NUMBER);
		}
	protected Room(String roomNum) { 
		setRoomNumber(roomNum); 
		}
	
	public void setRoomNumber(String roomNum) {
		roomNumber = roomNum;
		}
	public String getRoomNumber() {
		return roomNumber;
		}
	
    protected abstract String getRoomType() ;
	protected abstract int getSeats();
	protected abstract String getDetails();
	
	public String toString( ) { return getRoomNumber() + " is a " +
		getRoomType() + " with " + getSeats() + " seats; " + getDetails() +"\n" ;}
}
