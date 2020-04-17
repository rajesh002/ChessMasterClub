package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import model.Login;
import services.UserDAOInterface;
import utility.ConnectionManager;

public class UserDAO implements UserDAOInterface {
	
	
	@Override
	public boolean admin(String username, String password) throws Exception{
		Login login=new Login(username,password);
		boolean status = false;
		try {
			Connection con=ConnectionManager.getConnection();
			PreparedStatement pstmt = con.prepareStatement("select * from CHESSADMIN where username = ? and password = ? ");
			
			pstmt.setString(1, login.getUsername());
			pstmt.setString(2, login.getPassword());

			ResultSet rs = pstmt.executeQuery();
			status = rs.next();	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public void signUp(String username, String password) throws Exception {
		Login login=new Login(username,password);
		Connection con=ConnectionManager.getConnection();
		PreparedStatement pstmt = con.prepareStatement("insert into logindata values(?,?)");
		
		pstmt.setString(1, login.getUsername());
		pstmt.setString(2, login.getPassword());
		pstmt.executeUpdate();
	}

	@Override
	public boolean login(String username, String password) {
		Login login=new Login(username,password);
		boolean status = false;
		try {
			Connection con=ConnectionManager.getConnection();
			PreparedStatement pstmt = con.prepareStatement("select * from logindata where username = ? and password = ? ");
			
			pstmt.setString(1, login.getUsername());
			pstmt.setString(2, login.getPassword());

			ResultSet rs = pstmt.executeQuery();
			status = rs.next();	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	public void displayPlayersOfTournament(LocalDate date) throws Exception {
		Connection con=ConnectionManager.getConnection();
		
		PreparedStatement pstmt = con.prepareStatement("select tournament.name,tournament.start_date,tournament.end_date,players.name from players,tournament where ? BETWEEN tournament.start_date AND tournament.end_date and (players.final_result='Win' and players.tournament_id=tournament.id)");
		pstmt.setDate(1,Date.valueOf(date));
		ResultSet rs=pstmt.executeQuery();
		if(rs ==null)
			System.out.println("No tournament occured");
		else {
			System.out.println("Tournament                StartDate        EndDate           WinnerName");
			while(rs.next()) {
				System.out.printf("%-20s %15s %15s %15s %n",rs.getString(1),rs.getDate(2).toLocalDate(),rs.getDate(3).toLocalDate(),rs.getString(4));
			}
		}
	}
}

