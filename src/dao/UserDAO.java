package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Login;
import services.UserDAOInterface;
import utility.ConnectionManager;

public class UserDAO implements UserDAOInterface {
	
	
	@Override
	public boolean admin(String username, String password) throws Exception{
		Login login=new Login(username,password);
		Connection con=ConnectionManager.getConnection();
		boolean status = false;
		try {
			PreparedStatement pstmt = con.prepareStatement("select * from CHESSADMIN where username = ? and password = ? ");
			
			pstmt.setString(1, login.getUsername());
			pstmt.setString(2, login.getPassword());

			ResultSet rs = pstmt.executeQuery();
			status = rs.next();	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		con.close();
		return status;
	}

	@Override
	public void signUp(String username, String password) throws Exception {
		Login login=new Login(username,password);
		Connection con=ConnectionManager.getConnection();
		PreparedStatement pstmt = con.prepareStatement("insert into logindata(username,password) values(?,?)");
		
		pstmt.setString(1, login.getUsername());
		pstmt.setString(2, login.getPassword());
		pstmt.executeUpdate();
		con.close();
	}

	@Override
	public boolean login(String username, String password) throws Exception {
		Login login=new Login(username,password);
		Connection con=ConnectionManager.getConnection();
		boolean status = false;
		try {
			PreparedStatement pstmt = con.prepareStatement("select * from logindata where username = ? and password = ? ");
			
			pstmt.setString(1, login.getUsername());
			pstmt.setString(2, login.getPassword());

			ResultSet rs = pstmt.executeQuery();
			status = rs.next();	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		con.close();
		return status;
	}

}

