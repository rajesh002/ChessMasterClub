package dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import model.PlayerDetails;
import services.PlayerDAOInterface;
import utility.ConnectionManager;

public class PlayerOperations implements PlayerDAOInterface {
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
		System.out.printf("PLAYER_ID                  NAME             AGE        EMAIL             TOURNAMENT_ID %n");
		while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int age=rs.getInt("age");
            String email=rs.getString("email");
            int tournament_id=rs.getInt("tournament_id");
            System.out.printf("%-15d %15s %15d %15s %15d %n",id,name,age,email,tournament_id);
         }
	}
	
	public void displayPlayers(int tornamentId) throws Exception {
		Connection con=ConnectionManager.getConnection();
		PreparedStatement pstmt = con.prepareStatement("select players.id,players.name,ranking.win,ranking.loss from Players,ranking where players.id=ranking.id and players.tournament_id="+tornamentId);
		ResultSet rs = pstmt.executeQuery();
		System.out.printf("PLAYER_ID                  NAME             WIN             LOSS %n");
		while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int win=rs.getInt("win");
            int loss=rs.getInt("loss");
            System.out.printf("%-15d %15s %15d %15s %n",id,name,win,loss);
         }
	}
	
	public void addWins(int playerId,int wins) throws Exception {
		Connection con=ConnectionManager.getConnection();
		PreparedStatement pstmt = con.prepareStatement("update ranking set win= win + ? where id = ?");
		pstmt.setInt(1,wins);
		pstmt.setInt(2, playerId);
		pstmt.executeUpdate();
	}
	
	public void addLosses(int playerId,int losses) throws Exception {
		Connection con=ConnectionManager.getConnection();
		PreparedStatement pstmt = con.prepareStatement("update ranking set loss= loss + ? where id = ?");
		pstmt.setInt(1,losses);
		pstmt.setInt(2, playerId);
		pstmt.executeUpdate();
	}
	
	public void bestPlayer(int tid) throws Exception {
		Connection con=ConnectionManager.getConnection();
		PreparedStatement pstmt = con.prepareStatement(
				"select players.id,players.name,ranking.win from ranking,players where ranking.win=(select max(win) from players,ranking where players.id=ranking.id and players.tournament_id="+tid+" )  and players.id=ranking.id");
		ResultSet rs = pstmt.executeQuery();
		System.out.printf("PLAYER_ID                  NAME             WIN %n");
		while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int win=rs.getInt("win");
            System.out.printf("%-15d %15s %15d %n",id,name,win);
         }
	}
}
	
	
	
	
	
	
	
	
	

