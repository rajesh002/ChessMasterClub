package model;

public class PlayerDetails {
	String name;
	String email;
	int age;
	int tournamentId;
	public PlayerDetails(String name, String email, int age, int tournamentId) {
		super();
		this.name = name;
		this.email = email;
		this.age = age;
		this.tournamentId = tournamentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
