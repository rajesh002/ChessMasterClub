package dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import model.PlayerDetails;
import utility.ConnectionManager;

public class PlayerOperations {
	public void addNewPlayer(String name,int age,String email,int tournament_id) throws Exception {
		PlayerDetails playerdetails=new PlayerDetails(name,email,age,tournament_id);
		Connection con=ConnectionManager.getConnection();
		PreparedStatement pstmt = con.prepareStatement("insert into Players(name,age,email,tournament_id) values(?,?,?,?)");
		
		pstmt.setString(1, playerdetails.getName());
		pstmt.setInt(2,playerdetails.getAge());
		pstmt.setString(3,playerdetails.getEmail());
		pstmt.setInt(4,playerdetails.getTournamentId());
		pstmt.executeUpdate();
	}
	
	
	public void displayPlayers() throws Exception {
		Connection con=ConnectionManager.getConnection();
		PreparedStatement pstmt = con.prepareStatement("select * from Players");
		ResultSet rs = pstmt.executeQuery();
		System.out.printf("ID                         NAME             AGE        EMAIL             TOURNAMENT_ID %n");
		while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int age=rs.getInt("age");
            String email=rs.getString("email");
            int tournament_id=rs.getInt("tournament_id");
            System.out.printf("%-15d %15s %15d %15s %15d %n",id,name,age,email,tournament_id);
         }
	}	
}
