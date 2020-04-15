package model;

import java.time.LocalDate;

public class TournamentDetails {
	String name;
	LocalDate startDate;
	LocalDate endDate;
	public TournamentDetails(String name, LocalDate startDate, LocalDate endDate) {
		super();
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
}
