package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
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
		con.close();
	}
	
	// checking tournament ended or not
	public boolean tournamentNotEnded(int id) throws Exception {
		LocalDate today=LocalDate.now();
		LocalDate endDate;
		Connection con=ConnectionManager.getConnection();
		PreparedStatement pstmt = con.prepareStatement("select end_date from tournament where id="+id);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		endDate=rs.getDate("end_date").toLocalDate();
		con.close();
		return (today.compareTo(endDate) <= 0); 
		
	}
	
	
	
	//Displaying Tournaments Data
	public void displayTournaments() throws Exception{
		PreparedStatement pstmt1;
		ResultSet rs1 ;
		String status;
		Connection con=ConnectionManager.getConnection();
		PreparedStatement pstmt = con.prepareStatement("select * from tournament");
		ResultSet rs = pstmt.executeQuery();
		System.out.printf("TOURNAMENT_ID               NAME              STARTDATE       ENDDATE           STATUS%n");
		System.out.println("________________________________________________________________________________________________");
		while (rs.next()) {
			pstmt1 = con.prepareStatement("select * from players where tournament_id=? and final_result=?");
			pstmt1.setInt(1,rs.getInt("id"));
			pstmt1.setString(2, "Win");
			rs1 = pstmt1.executeQuery();
			if(rs1.next())
				status="Completed";
			else
				status="Running";
            int id = rs.getInt("id");
            String name = rs.getString("name");
            LocalDate startDate=rs.getDate("start_date").toLocalDate();
            LocalDate endDate=rs.getDate("end_date").toLocalDate();
            System.out.printf("%-15d %23s %15s %15s %15s %n",id,name,startDate,endDate,status);
            
         }
		con.close();
	}

	//Displaying players to the user based on date
	public boolean displayPlayersOfTournament(LocalDate date) throws Exception {
		Connection con=ConnectionManager.getConnection();
		
		PreparedStatement pstmt = con.prepareStatement("select tournament.name,tournament.start_date,tournament.end_date,players.name from players,tournament where ? BETWEEN tournament.start_date AND tournament.end_date and (players.final_result='Win' and players.tournament_id=tournament.id)");
		pstmt.setDate(1,Date.valueOf(date));
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()==false) {
			con.close();
			return false;
		}
		else {
			System.out.println("Tournament                StartDate        EndDate           WinnerName");
			while(rs.next()) {
				System.out.printf("%-20s %15s %15s %15s %n",rs.getString(1),rs.getDate(2).toLocalDate(),rs.getDate(3).toLocalDate(),rs.getString(4));
			}
			con.close();
			return true;
		}
	}
	
	
}
