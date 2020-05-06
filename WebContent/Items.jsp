<%@page import="com.Patient"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Patient Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css"> 
<script src="Components/jquery-3.2.1.min.js"></script> 
<script src="Components/Patient.js"></script> 
</head> 
<body> 
<div class="container"> 
<div class="row"> 
<div class="col-6">  
	<h3 align="center">Patient Management</h3>
 
 	<form id="formPatient" name="formPatient" action="Hospital.jsp">   
 		First Name:   
 		<input id="fname" name="fname" type="text" class="form-control form-control-sm"> 
 
  		<br> Last Name:   
  		<input id="lname" name="lname" type="text" class="form-control form-control-sm"> 
 
 		<br> Age:   
 		<input id="age" name="age" type="text" class="form-control form-control-sm"> 
 
  		<br> Gender:   
  		<input id="gender" name="gender" type="text" class="form-control form-control-sm"> 
 
  		<br> Email:   
  		<input id="email" name="email" type="text" class="form-control form-control-sm"> 
 
  		<br> Phone:   
  		<input id="phone" name="phone" type="text" class="form-control form-control-sm"> 
 
  		<br>   
  		<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
  		<input type="hidden" id="hidPatientIDSave" name="hidPatientIDSave" value="">  
  	</form> 
 
 	<div id="alertSuccess" class="alert alert-success"></div>  
 	<div id="alertError" class="alert alert-danger"></div> 

 	<br> 
 	<div id="divPatientGrid">
 			<%    
					Patient patientObj = new Patient();    
 				    out.print(patientObj.readPatient());   
 				    
 				    
 			%> 
 	</div>
 </div>   
 </div>  
 </div>   
 </body> 
 </html> 