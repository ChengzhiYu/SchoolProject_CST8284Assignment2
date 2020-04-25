/*	Course Name: CST8284
 	Student Name: Chengzhi Yu
 	Class Name: RoomScheduler.java
 	Date: March 1st, 2020
 */

/*  Course Name: CST8284
    Author: Prof. Dave Houtman
    Class name: RoomScheduler.java
    Date: February 11, 2020
*/ 

package cst8284.asgmt2.roomScheduler;

import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class RoomScheduler {
	
	private static Scanner scan = new Scanner(System.in);
	private ArrayList<RoomBooking> roomBookings = new ArrayList<RoomBooking>();
	private Room room;
	private static final int DISPLAY_ROOM_INFOMATION = 1, ENTER_ROOM_BOOKING = 2, DELETE_BOOKING =3,
							CHENGE_BOOKING =4, DISPLAY_BOOKING = 5, DISPLAY_DAY_BOOKINGS = 6, 
							SAVE_BOOKING_TO_FILE = 7, LOAD_BOOKING_FROM_FILE = 8, EXIT = 0;
	
	public RoomScheduler(Room room) {
		setRoom(room);	
	}
	
	public void launch() {
		int choice = 0;
		
		do {
		   choice = displayMenu();
		   executeMenuItem(choice);
		} while (choice != EXIT);		
	}
	
	private int displayMenu() {
		System.out.println("Enter a selection from the following menu:");
		System.out.println(
			DISPLAY_ROOM_INFOMATION + ". Display room infomation\n" +
			ENTER_ROOM_BOOKING + ". Enter a room booking\n" +
			DELETE_BOOKING + ". Remove a room booking\n" +
			CHENGE_BOOKING + ". Change a room booking\n" +
			DISPLAY_BOOKING  + ". Display a booking\n" +
			DISPLAY_DAY_BOOKINGS + ". Display room bookings for the whole day\n" +
			SAVE_BOOKING_TO_FILE + ". Backup current booking to file\n" +
			LOAD_BOOKING_FROM_FILE + ". Load current booking from file\n" +
			EXIT + ". Exit program");
		int ch = scan.nextInt();
		scan.nextLine();  // 'eat' the next line in the buffer
		System.out.println(); // add a space before next menu output
		return ch;
	}
	
	private void executeMenuItem(int choice) {
		switch (choice) {
			case DISPLAY_ROOM_INFOMATION:
				displayRoomInfo();
				break;
			case ENTER_ROOM_BOOKING: 
				saveRoomBooking(makeBookingFromUserInput()); 
				break;
			case DELETE_BOOKING:
				deleteBooking(null);
				break;
			case CHENGE_BOOKING:
				changeBooking(null);
				break;
			case DISPLAY_BOOKING: 
				displayBooking(makeCalendarFromUserInput(null, true));
				break;
			case DISPLAY_DAY_BOOKINGS: 
				displayDayBookings(makeCalendarFromUserInput(null, false)); 
				break;
			case SAVE_BOOKING_TO_FILE:
				saveBookingToFile();
				break;
			case LOAD_BOOKING_FROM_FILE:
				loadBookingFromFile();
				break;
			case EXIT: 
				
				saveBookingToFile();
				System.out.println("Exiting Room Booking Application\n\n"); 
				break;
			default: System.out.println("Invalid choice: try again. (Select " + EXIT + " to exit.)\n");
		}
		System.out.println();  // add blank line after each output
	}
	
    private static String getResponseTo(String s) {
    	System.out.print(s);
		return(scan.nextLine());
    }
	
    private static RoomBooking makeBookingFromUserInput() {
    	String[] fullName = getResponseTo("Enter Client Name (as FirstName LastName): ").split(" ");
 		String phoneNumber = getResponseTo("Phone Number (e.g. 613-555-1212): ");
		String organization = getResponseTo("Organization (optional): ");
		String category = getResponseTo("Enter event category: ");
		String description = getResponseTo("Enter detailed description of event: ");
		Calendar startCal = makeCalendarFromUserInput(null, true);
		Calendar endCal = makeCalendarFromUserInput(startCal, true);
		
		ContactInfo contactInfo = new ContactInfo(fullName[0], fullName[1], phoneNumber, organization);
		Activity activity = new Activity(category, description);
		TimeBlock timeBlock = new TimeBlock(startCal, endCal);
		return (new RoomBooking(contactInfo, activity, timeBlock));
    }
    
    private static Calendar makeCalendarFromUserInput(Calendar initCal, boolean requestHour) {
    	Calendar cal = Calendar.getInstance(); cal.clear();
    	String date = "";
    	int hour = 0;	
    	boolean needCal = (initCal==null);
    	
   		if (needCal) date = getResponseTo("Event Date (entered as DDMMYYYY): ");
   		int day = needCal ? Integer.parseInt(date.substring(0,2)) : initCal.get(Calendar.DAY_OF_MONTH);
   		int month = needCal ? Integer.parseInt(date.substring(2,4))-1 : initCal.get(Calendar.MONTH);
   		int year = needCal ? Integer.parseInt(date.substring(4,8)) : initCal.get(Calendar.YEAR);
     		
		if (requestHour) {				
		   String time = getResponseTo((needCal?"Start":"End") +" Time: ");
		   hour = processTimeString(time);
		}

		cal.set(year, month, day, hour, 0);
		return (cal);
    }
    
	private static int processTimeString(String t) {
		int hour = 0;
		t = t.trim();
		if (t.contains ("pm") || (t.contains("p.m."))) hour = Integer.parseInt(t.split(" ")[0]) + 12;
		if (t.contains("am") || t.contains("a.m.")) hour = Integer.parseInt(t.split(" ")[0]);
		if (t.contains(":")) hour = Integer.parseInt(t.split(":")[0]);
		return hour;
	}
	
    private RoomBooking findBooking(Calendar cal) {
    	Calendar oneHourLater = Calendar.getInstance();
    	oneHourLater.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY)+1);
    	TimeBlock findTB = new TimeBlock(cal, oneHourLater);
    	/*for (int idx = 0; idx < getRoomBookings().size(); idx++) 
    		if (getRoomBookings().get(idx).getTimeBlock().overlaps(findTB)) 
    			return getRoomBookings().get(idx);*/
    	for(RoomBooking rb: getRoomBookings())
    	{
    		if(rb.getTimeBlock().overlaps(findTB))
    		{
    			return rb;
    		}
    	}
    	
    	return null;
    }
    	
	private boolean saveRoomBooking(RoomBooking booking) {	
		TimeBlock tb = booking.getTimeBlock();  // Check this TimeBlock to see if already booked
		Calendar cal = (Calendar)tb.getStartTime().clone(); // use its Calendar
		int hour = cal.get(Calendar.HOUR_OF_DAY);//Get first hour of block
		for (; hour < tb.getEndTime().get(Calendar.HOUR_OF_DAY); hour++){ //Loop through each hour in TimeBlock
			cal.set(Calendar.HOUR_OF_DAY, hour); // set next hour
		    if (findBooking(cal)!=null) {  // TimeBlock already booked at that hour, can't add appointment
		    	System.out.println("Cannot save booking; that time is already booked");
				return false;
		    }	
		}  // else time slot still available; continue loop to next hour
		getRoomBookings().add(booking);  
		System.out.println("Booking time and date saved." );  
		return true;
	}
	
	private RoomBooking displayBooking(Calendar cal) {  
		RoomBooking booking = findBooking(cal);
		int hr = cal.get(Calendar.HOUR_OF_DAY);
		System.out.print((booking!=null) ?
		   "---------------\n"+ booking.toString()+"---------------\n": 
  	       "No booking scheduled between "+ hr + ":00 and " + (hr + 1) + ":00\n");
		return booking;
	}
	
	private void displayDayBookings(Calendar cal) {
		for (int hrCtr = 8; hrCtr < 24; hrCtr++) {
			cal.set(Calendar.HOUR_OF_DAY, hrCtr);
			RoomBooking rb = displayBooking(cal);	
			if (rb !=null) hrCtr = rb.getTimeBlock().getEndTime().get(Calendar.HOUR_OF_DAY) - 1;
		}
	}
	
	private ArrayList<RoomBooking> getRoomBookings() {
		return roomBookings;
	}
	
	private void displayRoomInfo(){
		System.out.println((getRoom().toString()));

	}
	
	private boolean deleteBooking(Calendar cal) {
		
		System.out.println("Enter booking to delete");
		cal = makeCalendarFromUserInput(null, true);
		displayBooking(cal);
		System.out.println ("Press 'Y' to confirm deletion, any other key to abort: ");
		if (scan.next().charAt(0)=='Y'){
			if (findBooking(cal)!= null){
				roomBookings.remove(findBooking(cal));  
				System.out.println("Booking time and date deleted.");
				return true;
			}
		}	
	      return false;	
	}

	private boolean changeBooking(Calendar cal) {
		
		String startTime = "";
		String endTime ="";
		int startHour = 0;
		int endHour = 0;
		Calendar startCal;
		Calendar endCal = Calendar.getInstance();
		
		System.out.println("Enter booking to change");
		
		cal = makeCalendarFromUserInput(null, true);
		displayBooking(cal);
		
		System.out.print("Enter New Start Time: ");
		startTime = scan.nextLine();
		startHour = processTimeString(startTime);
		
		System.out.print("Enter New End Time: ");
		endTime = scan.nextLine();
		endHour = processTimeString(endTime);
		
	    RoomBooking newRb = findBooking(cal);
	     
	    	startCal = cal;
	     	endCal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
 
	    if (newRb!= null) {
	    	for(RoomBooking rb: roomBookings) {
	    		if (rb.equals(newRb)) {
			       startCal.set(Calendar.HOUR_OF_DAY, startHour);
			       endCal.set(Calendar.HOUR_OF_DAY, endHour);
	    			
			      TimeBlock timeBlock = new TimeBlock(startCal, endCal);
	    			rb.setTimeBlock(timeBlock);
	    			System.out.println("Booking has been changed to:");
	    			displayBooking(startCal);

	    			return true;
				}
	    	}
	    }
		return false;	 
	}
	
	private boolean saveBookingToFile() {
		try {
            FileOutputStream fos = new FileOutputStream("CurrentRoomBookings.book");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(roomBookings);
            System.out.println("Current room bookings backed up to file");
            oos.close();
            fos.close();
            return true;
            
        } 
        catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
		
	}
	ArrayList<RoomBooking> loadBookingFromFile(){
		
		ArrayList<RoomBooking> bookings = new ArrayList<RoomBooking> ();
		       
            try{
            	FileInputStream fis = new FileInputStream("CurrentRoomBookings.book");
                ObjectInputStream ois = new ObjectInputStream(fis);
     
                bookings = (ArrayList<RoomBooking>) ois.readObject();
                System.out.println("Current room bookings loaded from file");
                ois.close();
                fis.close();
            } 
            catch (IOException ioe) {
                ioe.printStackTrace();
                return null;
            } 
            catch (ClassNotFoundException c) {
                c.printStackTrace();
                return  null;
            }
            
            roomBookings = bookings;
       
		return bookings;
	}
	private void setRoom(Room room) {
		this.room = room;
	}
	private Room getRoom() {
		return room;
		}
   
}
