/*	Course Name: CST8284
 	Student Name: Chengzhi Yu
 	Class Name: ContactInfo.java
 	Date: March 1st, 2020
 */

/*  Course Name: CST8284
    Author: Prof. Dave Houtman
    Class name: ContactInfo.java
    Date: February 11, 2020
*/ 

package cst8284.asgmt2.roomScheduler;

import java.io.Serializable;

public class ContactInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String firstName, lastName, phoneNumber, organization;
	
	public ContactInfo(String firstName, String lastName, String phoneNumber) {this(firstName, lastName, phoneNumber, "Algonquin College");}
	
	public ContactInfo(String firstName, String lastName, String phoneNumber, String organization) {
		setFirstName(firstName); setLastName(lastName); 
		setPhoneNumber(phoneNumber); setOrganization(organization);
	}	

	public void setFirstName(String firstName) {this.firstName = firstName;}
	public String getFirstName() {return firstName;}

	public void setLastName(String lastName) {this.lastName = lastName;}
	public String getLastName() {return lastName;}

	public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}
	public String getPhoneNumber() {return phoneNumber;}

	public void setOrganization(String organization) {this.organization = organization;}
	public String getOrganization() {return organization;}
	
	@Override
	public String toString() {
		return "Contact Information: " +
			((getFirstName()!="")?(getFirstName() + " " + getLastName()):"") + "\n" +
			"Phone: " + getPhoneNumber() +  
			((getOrganization().equals(""))?"":("\n" +getOrganization() + "\n"));
	}
}
