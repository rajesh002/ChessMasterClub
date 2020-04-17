package model;

public class PlayerDetails {
	String name;
	String place;
	int age;
	int tournamentId;
	public PlayerDetails(String name, String place, int age, int tournamentId) {
		super();
		this.name = name;
		this.place=place;
		this.age = age;
		this.tournamentId = tournamentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place=place;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getTournamentId() {
		return tournamentId;
	}
	public void setTournamentId(int tournamentId) {
		this.tournamentId = tournamentId;
	}
	
}
