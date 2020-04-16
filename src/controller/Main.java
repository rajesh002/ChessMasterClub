package controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;

import businesslogic.RegisterValidation;
import dao.PlayerOperations;
import dao.TournamentOperations;
import dao.UserDAO;


public class Main {

	public static void main(String[] args) throws Exception {
		int role,adminChoice,playerAge,playerTournamentId,playerId,noOfWins,noOfLosses;
		String username,password,confirmPassword,adminDecision,tournamentName,playerName,playerEmail;
		LocalDate startDate,endDate;
		boolean detailsExist=false;
		//Creating objects
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		
		
		UserDAO userdao= new UserDAO();
		TournamentOperations tournamentOperations=new TournamentOperations();
		PlayerOperations playeroperations=new PlayerOperations();
		
		System.out.println("==========Chess Master Club========");
		System.out.println("		1.Admin");
		System.out.println("		2.Signup");
		System.out.println("		3.Login");
		role=Integer.parseInt(br.readLine());
		
		
		// Admin login
		if(role==1) {
			do {
				System.out.println("Enter Your Username: ");
				username = br.readLine();
				System.out.println("Enter Your PassWord: ");
				password=br.readLine();
				if(userdao.admin(username,password)) {    //Checking the entered user admin or not
					detailsExist=true;
					System.out.println("Welcome to Chessmaster club");
					System.out.println("Admin Operations");
				}
				else
					System.out.println("Invalid userdetails");
			}while(!detailsExist);	
			
			
			// Admin Operations
			
			System.out.println("	Admin Login Sucessfully		");
			do {
				System.out.println("	1.Add tournament 	      ");
				System.out.println("	2.Add player to tournament");
				System.out.println("	3.Record win to player    ");
				System.out.println("	4.Record loss to player   ");
				System.out.println("	5.View BestPlayer in tournament ");
			//	System.out.println("	6.Tournament ");
				adminChoice=Integer.parseInt(br.readLine());
				switch(adminChoice) {
							
					case 1:
						//Adding New Tournaments
						System.out.println("Enter the tournament name");
						tournamentName=br.readLine();
						System.out.println("Enter start date of tournament");
						startDate=LocalDate.parse(br.readLine());
						System.out.println("Enter end date of tournament");
						endDate=LocalDate.parse(br.readLine());
						tournamentOperations.addNewTournament(tournamentName,startDate,endDate);
						tournamentOperations.displayTournaments();
						break;
					case 2:
						System.out.println("Enter the player name");
						playerName=br.readLine();
						System.out.println("Enter the player age");
						playerAge=Integer.parseInt(br.readLine());
						System.out.println("Enter the email");
						playerEmail=br.readLine();
						System.out.println("Enter the player tournament id");
						playerTournamentId=Integer.parseInt(br.readLine());
						System.out.println(tournamentOperations.tournamentNotEnded(playerTournamentId));
						if(tournamentOperations.tournamentNotEnded(playerTournamentId)) {
							playeroperations.addNewPlayer(playerName, playerAge, playerEmail, playerTournamentId);
							playeroperations.displayPlayers();
						}
						else
							System.out.println("Sorry,the tournament was ended");
						break;
					case 3:
						tournamentOperations.displayTournaments();
						System.out.println("Select tournament which player you want to record wins");
						playerTournamentId=Integer.parseInt(br.readLine());
						playeroperations.displayPlayers(playerTournamentId);
						System.out.println("select player which you want to add wins");
						playerId=Integer.parseInt(br.readLine());
						System.out.println("How many wins you want to add ?");
						noOfWins=Integer.parseInt(br.readLine());
						playeroperations.addWins(playerId, noOfWins);
						playeroperations.displayPlayers(playerTournamentId);
						break;
					case 4:
						tournamentOperations.displayTournaments();
						System.out.println("Select tournament which player you want to record loss");
						playerTournamentId=Integer.parseInt(br.readLine());
						playeroperations.displayPlayers(playerTournamentId);
						System.out.println("select player which you want to add loss");
						playerId=Integer.parseInt(br.readLine());
						System.out.println("How many Losses you want to add ?");
						noOfLosses=Integer.parseInt(br.readLine());
						playeroperations.addLosses(playerId, noOfLosses);
						playeroperations.displayPlayers(playerTournamentId);
						break;
					case 5:
						tournamentOperations.displayTournaments();
						System.out.println("Enter the tournament id");
						playerTournamentId=Integer.parseInt(br.readLine());
						playeroperations.bestPlayer(playerTournamentId);
						break;
					case 6:
						break;
				}
				
				
				System.out.println("Are you want to continue : yes/no");
				adminDecision=br.readLine();		
			}while(adminDecision.equals("yes"));
			
			
			
			
			
			
			
		}
		
		
		

		// New user Registration
		else if(role==2) {
			do {
				System.out.println("Enter new username");
				username=br.readLine();
				System.out.println("Enter Password:");
				password=br.readLine();
				System.out.println("Enter Confirm Password");
				confirmPassword=br.readLine();
				RegisterValidation registerValidation=new RegisterValidation(username,password,confirmPassword);
				if(registerValidation.checkUserDetails()) {                  // validating the entered details
					detailsExist=true;
					userdao.signUp(username, password);
					System.out.println("your account was activated");
				}
			}while(!detailsExist);
		}
	    


		

		// Existing user
		if(role==2 || role==3) {
			do {
				System.out.println("Enter new username");
				username=br.readLine();
				System.out.println("Enter Password:");
				password=br.readLine();
				if(userdao.login(username,password)) {    //Checking the entered user details  or not
					detailsExist=true;
					System.out.println("Welcome to Chessmaster club");
					System.out.println("LeaderBoard");
				}
				else
					System.out.println("Invalid userdetails");
			}while(!detailsExist);	
		}

	}
}
