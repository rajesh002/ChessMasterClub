package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.TournamentDetails;
import services.TournamentDAOInterface;
import utility.ConnectionManager;

public class TournamentOperations implements TournamentDAOInterface {
	
	// Adding new Tournament
	public void addNewTournament(String tournamentName,LocalDate startDate,LocalDate endDate) throws Exception {
		TournamentDetails tournament=new TournamentDetails(tournamentName,startDate,endDate);
		Connection con=ConnectionManager.getConnection();
		PreparedStatement pstmt = con.prepareStatement("insert into tournament(name,start_date,end_date) values(?,?,?)");
		
		pstmt.setString(1, tournament.getName());
		pstmt.setDate(2,Date.valueOf(tournament.getStartDate()));
		pstmt.setDate(3,Date.valueOf(tournament.getEndDate()));
		pstmt.executeUpdate();
	}
	
	
	public boolean tournamentNotEnded(int id) throws Exception {
		LocalDate today=LocalDate.now();
		LocalDate endDate;
		Connection con=ConnectionManager.getConnection();
		PreparedStatement pstmt = con.prepareStatement("select end_date from tournament where id="+id);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		endDate=rs.getDate("end_date").toLocalDate();
		return (today.compareTo(endDate) <= 0); 
	}
	
	
	
	
	
	
	
	
	
	//Displaying Tournaments Data
	public void displayTournaments() throws Exception{
		Connection con=ConnectionManager.getConnection();
		PreparedStatement pstmt = con.prepareStatement("select * from tournament");
		ResultSet rs = pstmt.executeQuery();
		System.out.printf("TOURNAMENT_ID        NAME             STARTDATE        ENDDATE %n");
		while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            LocalDate startDate=rs.getDate("start_date").toLocalDate();
            LocalDate endDate=rs.getDate("end_date").toLocalDate();
            System.out.printf("%-15d %15s %15s %15s %n",id,name,startDate,endDate);
            
         }
	}
	
}
