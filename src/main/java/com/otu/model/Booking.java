package com.otu.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
public class Booking {
	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
	@JoinTable(name = "customer_bookings", joinColumns = { @JoinColumn(name = "booking_id") }, inverseJoinColumns = {
            @JoinColumn(name = "customer_id") })
	private Customer customer;
	private long customerId;
	@ManyToOne
	@JoinTable(name = "room_bookings", joinColumns = { @JoinColumn(name = "booking_id") }, inverseJoinColumns = {
            @JoinColumn(name = "room_id") })
	private Room room;
	private long roomId;
	private LocalDate startDate;
	private LocalDate endDate;

	@OneToMany(mappedBy = "booking")
	private List<ProvidedService> services;
	
	
	public Booking() {
		super();
	}

	public Booking(long id, Customer customer, Room room, LocalDate startDate, LocalDate endDate) {
		super();
		this.id = id;
		this.customer = customer;
		this.customerId = customer.getId();
		this.room = room;
		this.roomId = room.getId();
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public long getCustomerId() {
		return this.customerId;
	}
	
	public long getRoomId() {
		return this.roomId;
	}
	
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
		this.customerId = customer.getId();
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
		this.roomId = room.getId();
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}
	
	public String isRoomSelected(long targetId) {
		if(this.roomId == targetId) {
			return "selected";
		}
		return "";
	}
	
	public String isCustomerSelected(long targetId) {
		if(this.customerId == targetId) {
			return "selected";
		}
		return "";
	}
	
	public String getStartDateAsString() {
		if(this.startDate == null) {
			return "";
		}
//		return "2000-01-01";
		return this.startDate.toString();
	}
	
	public String getEndDateAsString() {
		if(this.endDate == null) {
			return "";
		}
//		return "2000-01-01";
		return this.endDate.toString();
//		return this.endDate.format(DateTimeFormatter.ofPattern("uuuu-mm-dd"));
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	public List<ProvidedService> getServices(){
		return this.services;
	}
	
	public void setServices(List<ProvidedService> services) {
		this.services = services;
	}
	
	public boolean dateRangeIsValid() {
		if(this.endDate == null) {
			return false;
		}
		if(this.endDate == null) {
			return false;
		}
		if(this.endDate.isBefore(this.startDate)) {
			return false;
		}
		return true;
	}
	
	
	// This is used when we have a list of bookings for a room, and we want to see what days are available
	public boolean getDatesOverlap(LocalDate queryStartDate, LocalDate queryEndDate) {
		if(this.startDate.isAfter(queryEndDate)) {
			return false; // the whole query is before this's start date
		}
		if(this.endDate.isBefore(queryStartDate)) {
			return false; // the whole query is after this's end date
		}
		return true; // there is some other overlap going on
	}
	
	public boolean getDatesOverlap(Booking queryBooking) {
		return this.getDatesOverlap(queryBooking.getStartDate(), queryBooking.getEndDate());
	}

	@Override
	public String toString() {
		return "Booking [id=" + id + ", customerId=" + customer.getId() + ", roomId=" + room.getId() + ", startDate=" + startDate
				+ ", endDate=" + endDate + "]";
	}
	
	public String toDropDown() {
		return "Room " + Integer.toString(room.getRoomNumber()) + " - " + startDate; 
	}
	
}
