package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.PlayerDetails;
import services.PlayerDAOInterface;
import utility.ConnectionManager;

public class PlayerOperations implements PlayerDAOInterface {
	
	//Adding new player to the tournament
	
	public boolean addNewPlayer(String name,int age,String place,int tournament_id) throws Exception {
		PlayerDetails playerdetails=new PlayerDetails(name,place,age,tournament_id);
		Connection con=ConnectionManager.getConnection();
	    boolean status=false;
		PreparedStatement pst = con.prepareStatement("select * from players where final_result='Win' and tournament_id="+tournament_id);
		
		 ResultSet rs1 = pst.executeQuery();
		 status = rs1.next();
		 
		 if(status) {
			 return false;
		 }
		else {

			PreparedStatement pstmt = con.prepareStatement("insert into Players(name,age,place,tournament_id,final_result) values(?,?,?,?,?)");
		
			pstmt.setString(1, playerdetails.getName());
			pstmt.setInt(2,playerdetails.getAge());
			pstmt.setString(3,playerdetails.getPlace());
			pstmt.setInt(4,playerdetails.getTournamentId());
			pstmt.setString(5, null);
			pstmt.executeUpdate();
		
			PreparedStatement pstmt1 = con.prepareStatement("select id from players where name=? and age=? and place=? and tournament_id=?");
			pstmt1.setString(1, name);
			pstmt1.setInt(2, age);
			pstmt1.setString(3, place);
			pstmt1.setInt(4, tournament_id);
			ResultSet rs=pstmt1.executeQuery();
			rs.next();
		
			PreparedStatement pstmt2 = con.prepareStatement("insert into ranking(id,win,loss) values(?,?,?)");
			pstmt2.setInt(1, rs.getInt("id"));
			pstmt2.setInt(2, 0);
			pstmt2.setInt(3, 0);
			pstmt2.executeUpdate();
			con.close();
			return true;
	}
}		 
		
	//Displaying players based on tournament id
	
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
		con.close();
	}
	
	//Adding wins to the players
	
	public void addWins(int playerId,int wins) throws Exception {
		Connection con=ConnectionManager.getConnection();
		PreparedStatement pstmt = con.prepareStatement("update ranking set win= win + ? where id = ?");
		pstmt.setInt(1,wins);
		pstmt.setInt(2, playerId);
		pstmt.executeUpdate();
		con.close();
	}
	
	//Adding loses to the players
	
	public void addLosses(int playerId,int losses) throws Exception {
		Connection con=ConnectionManager.getConnection();
		PreparedStatement pstmt = con.prepareStatement("update ranking set loss= loss + ? where id = ?");
		pstmt.setInt(1,losses);
		pstmt.setInt(2, playerId);
		pstmt.executeUpdate();
		con.close();
	}
	
	
//	
//	public void bestPlayer(int tid) throws Exception {
//		Connection con=ConnectionManager.getConnection();
//		PreparedStatement pstmt = con.prepareStatement(
//				"select players.id,players.name,ranking.win from ranking,players where ranking.win=(select max(win) from players,ranking where players.id=ranking.id and players.tournament_id="+tid+" )  and players.id=ranking.id and players.tournament_id="+tid);
//		ResultSet rs = pstmt.executeQuery();
//		System.out.printf("PLAYER_ID                  NAME             WIN %n");
//		while (rs.next()) {
//            int id = rs.getInt("id");
//            String name = rs.getString("name");
//            int win=rs.getInt("win");
//            System.out.printf("%-15d %15s %15d %n",id,name,win);
//         }
//		con.close();
//	}

	
	// Marking player as winner
	
	public boolean markAsWinner(int playerTournamentId) throws Exception {
		 boolean status = false;
		 Connection con = ConnectionManager.getConnection();
		 PreparedStatement pst = con.prepareStatement("select * from players where final_result='Win' and tournament_id="+playerTournamentId);
			
		 ResultSet rs1 = pst.executeQuery();
		 status = rs1.next();
		 
		 if(status) { 
			 con.close();
			 return false;
		 }
		else {
			PreparedStatement pstmt1 = con.prepareStatement("select ranking.id from ranking where id=( select ranking.id from ranking,players where win=(select max(win) from players,ranking where players.id=ranking.id and players.tournament_id="+playerTournamentId+")and players.id=ranking.id and players.tournament_id="+playerTournamentId+")");
			ResultSet rs=pstmt1.executeQuery();
			rs.next();
		
			PreparedStatement pstmt2 = con.prepareStatement("update players set final_result='Win' where id=?");
			pstmt2.setInt(1, rs.getInt("id"));
			pstmt2.executeUpdate();
		
			PreparedStatement pstmt3 = con.prepareStatement("update players set final_result='Lose' where id!=? and tournament_id="+playerTournamentId);
			pstmt3.setInt(1, rs.getInt("id"));
			pstmt3.executeUpdate();
			con.close();
			return true;
	}
}
	
	
	
	// Records player details for request to the tournament
	
	public void requestToParticipate(String playerName, int playerAge, String playerCity,String username,String password) throws Exception {
		Connection con=ConnectionManager.getConnection();
		PreparedStatement pstmt2 = con.prepareStatement("select id from logindata where username=? and password=?");
		pstmt2.setString(1, username);//rs.getInt("id"));
		pstmt2.setString(2, password);
		ResultSet rs=pstmt2.executeQuery();
		rs.next();
		
		PreparedStatement pstmt = con.prepareStatement("insert into requests(id,name,age,place,status) values(?,?,?,?,?)");
		pstmt.setInt(1, rs.getInt("id"));
		pstmt.setString(2, playerName);
		pstmt.setInt(3, playerAge);
		pstmt.setString(4, playerCity);
		pstmt.setString(5, "Pending");
		pstmt.executeUpdate();
		con.close();
	}
	
	
	
	
	
	//Displaying all pending requests
	
	public void displayPendingRequests() throws Exception {
		Connection con=ConnectionManager.getConnection();
		PreparedStatement pstmt = con.prepareStatement("select * from requests where status=?");
		pstmt.setString(1, "Pending");
		ResultSet rs = pstmt.executeQuery();
		System.out.printf("PLAYER_ID                  NAME             AGE             PLACE %n");
		while (rs.next()) {
	        int id = rs.getInt("id");
	        String name = rs.getString("name");
	        int age=rs.getInt("age");
	        String place=rs.getString("place");
	        System.out.printf("%-15d %15s %15d %15s %n",id,name,age,place);
	     }
		con.close();
}
	
	
	
	// Accepting request from the user
	
	public void acceptRequestAddPlayer(int requestId, int playerTournamentId) throws Exception {
		Connection con=ConnectionManager.getConnection();
		PreparedStatement pstmt = con.prepareStatement("select * from requests where id="+requestId);
		ResultSet rs= pstmt.executeQuery();
		rs.next();
		
		
		if(addNewPlayer(rs.getString(2), rs.getInt(3), rs.getString(4), playerTournamentId)==true) {
			displayPlayers(playerTournamentId);
			PreparedStatement pstmt1 = con.prepareStatement("update requests set status=? where id="+requestId);
			pstmt1.setString(1, "Completed");
			pstmt1.executeUpdate();
		}
		else
			System.out.println("Sorry! Result announced ,No players can be added");
		con.close();
	 }

	
	
	// All existing requests status 
	
	public boolean requestExists(String username, String password) throws Exception {
		Connection con=ConnectionManager.getConnection();
		PreparedStatement pstmt2 = con.prepareStatement("select id from logindata where username=? and password=?");
		pstmt2.setString(1, username);//rs.getInt("id"));
		pstmt2.setString(2, password);
		ResultSet rs=pstmt2.executeQuery();
		rs.next();
		
		PreparedStatement pstmt = con.prepareStatement("select * from requests where requests.id="+rs.getInt("id"));
		ResultSet rs1= pstmt.executeQuery();
		
		boolean status = rs1.next();
		
		 if(status==false) {
			 con.close();
			 return false;
		 }
		 else {
				PreparedStatement pstmt1 = con.prepareStatement("select status from requests where id="+rs.getInt("id"));
				ResultSet rs2= pstmt1.executeQuery();
				rs2.next();
				if(rs2.getString("status").equals("Pending")) {
					System.out.println("Your Request is in Pending");
					return true;
				}
				else {
					System.out.println("Your Request has accepted");
					con.close();
					return true;
				}
		 }
	}
  }
	
	
	
	
	
	

