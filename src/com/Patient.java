package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class Patient {

	//A common method to connect to the DB 
		private Connection connect() {
			Connection con = null;
			
			try {
				 Class.forName("com.mysql.jdbc.Driver");
				 //Provide the correct details: DBServer/DBName, username, password 
				 con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hosptal?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");

				//For testing          
				 System.out.print("Successfully connected");
				 
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			return con; 
		}
		
		public String readPatient() {  
			String output = "";  
			
			try {  
				Connection con = connect();  
				if (con == null)  {   
					return "Error while connecting to the database for reading.";  
				} 

				// Prepare the html table to be displayed   
				output = "<table border='1'><tr><th>First Name</th>"
						+ "<th>Last Name</th><th>Age</th>"
						+ "<th>Gender</th><th>Email</th>"
						+ "<th>Phone</th>"
						+ "<th>Update</th><th>Remove</th></tr>";


				  String query = "select * from user";   
				  Statement stmt = con.createStatement();   
				  ResultSet rs = stmt.executeQuery(query); 

				  // iterate through the rows in the result set   
				  while (rs.next())   {  

					  String Pid = Integer.toString(rs.getInt("Pid"));
					  String Fname = rs.getString("Fname");
					  String Lname = rs.getString("Lname");
					  String Age = Integer.toString(rs.getInt("Age"));
					  String Gender = rs.getString("Gender");
					  String email = rs.getString("email");
					  String Phone = Integer.toString(rs.getInt("Phone"));
					 
					  // Add into the html table    

					  output += "<tr><td><input id='hidPatientIDUpdate' name='hidPatientIDUpdate' type='hidden' value='" + Pid + "'>" + Fname + "</td>"; 

					  output += "<td>" + Lname + "</td>";
					  output += "<td>" + Age + "</td>";    
					  output += "<td>" + Gender + "</td>"; 
					  output += "<td>" + email + "</td>";    
					  output += "<td>" + Phone + "</td>";
					  
					  
					// buttons     
					  output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
					  		+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-patientID='"+ Pid +"'>"+"</td></tr>";

					} 
				  
				  con.close(); 

				  // Complete the html table   
				  output += "</table>"; 
				}
				catch (Exception e) {  
					output = "Error while reading the patient.";  
					System.err.println(e.getMessage()); 
				}

				return output;
			}
		
		//Insert Patient
		public String insertPatient(String fname, String lname, String age, String gender, String email, String phone ) {
			String output = "";

			try {
				Connection con = connect();  

				if (con == null) {
					return "Error while connecting to the database";
				}

				// create a prepared statement   
				String query = " insert into user (`Pid`,`Fname`,`Lname`,`Age`,`Gender`, `email`,`Phone`)"+" values (?, ?, ?, ?, ?, ?, ?)";

				PreparedStatement preparedStmt = con.prepareStatement(query);

				// binding values 
				preparedStmt.setInt(1, 0);
				preparedStmt.setString(2, fname);
				preparedStmt.setString(3, lname);
				preparedStmt.setString(4, age);
				preparedStmt.setString(5, gender);
				preparedStmt.setString(6, email);
				preparedStmt.setString(7, phone);
				
				
				//execute the statement   
				preparedStmt.execute();   
				con.close(); 

				//Create JSON Object to show successful msg.
				String newPatient = readPatient();
				output = "{\"status\":\"success\", \"data\": \"" + newPatient + "\"}";
			}
			catch (Exception e) {  
				//Create JSON Object to show Error msg.
				output = "{\"status\":\"error\", \"data\": \"Error while Inserting patient.\"}";   
				System.err.println(e.getMessage());  
			} 

			 return output; 
		}
		
		//Update patient
		public String updatePatient(String ID, String fname, String lname, String age, String gender, String email, String phone)  {   
			String output = ""; 
		 
		  try   {   
			  Connection con = connect();
		 
			  if (con == null)    {
				  return "Error while connecting to the database for updating."; 
			  } 
		 
		   // create a prepared statement    
			   String query = "UPDATE user SET Fname=?,Lname=?,Age=?,Gender=?,email=?,Phone=? WHERE Pid=?";
				 
		   PreparedStatement preparedStmt = con.prepareStatement(query); 
		 
		   // binding values    
		   preparedStmt.setString(1, fname);    
		   preparedStmt.setString(2, lname);    
		   preparedStmt.setString(3, age);
		   preparedStmt.setString(4, gender);
		   preparedStmt.setString(5, email);
		   preparedStmt.setInt(6, Integer.parseInt(phone));
		   preparedStmt.setInt(7, Integer.parseInt(ID));
		   
		 
		   // execute the statement    
		   preparedStmt.execute();    
		   con.close(); 
		 
		   //create JSON object to show successful msg
		   String newPatient = readPatient();
		   output = "{\"status\":\"success\", \"data\": \"" + newPatient + "\"}";
		   }   catch (Exception e)   {    
			   output = "{\"status\":\"error\", \"data\": \"Error while Updating patient Details.\"}";      
			   System.err.println(e.getMessage());   
		   } 
		 
		  return output;  
		  }
		
		public String deletePatient(String Pid) {  
			String output = ""; 
		 
		 try  {   
			 Connection con = connect();
		 
		  if (con == null)   {    
			  return "Error while connecting to the database for deleting.";   
		  } 
		 
		  // create a prepared statement   
		  String query = "DELETE FROM user WHERE Pid=?"; 
		
		 PreparedStatement preparedStmt  = con.prepareStatement(query) ;
		 
		  // binding values   
		  preparedStmt.setInt(1, Integer.parseInt(Pid));       
		  // execute the statement
		  preparedStmt.execute(); 
		  con.close(); 
		 
		  //create JSON Object
		  String newPatient = readPatient();
		  output = "{\"status\":\"success\", \"data\": \"" + newPatient + "\"}";
		  }  catch (Exception e)  {  
			  //Create JSON object 
			  output = "{\"status\":\"error\", \"data\": \"Error while Deleting patient.\"}";
			  System.err.println(e.getMessage());  
			  
		 } 
		 
		 return output; 
		 }
}
