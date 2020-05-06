$(document).ready(function() 
{  
	if ($("#alertSuccess").text().trim() == "")  
	{   
		$("#alertSuccess").hide();  
	} 
	$("#alertError").hide(); 
}); 

//SAVE ============================================ 
$(document).on("click", "#btnSave", function(event) 
{  
	// Clear alerts---------------------  
	$("#alertSuccess").text("");  
	$("#alertSuccess").hide();  
	$("#alertError").text("");  
	$("#alertError").hide(); 

	// Form validation-------------------  
	var status = validateHospitalForm();  
	if (status != true)  
	{   
		$("#alertError").text(status);   
		$("#alertError").show();   
		return;  
	} 

	// If valid------------------------  
	var t = ($("#hidPatientIDSave").val() == "") ? "POST" : "PUT";
	
	$.ajax(
	{
		url : "PatientAPI",
		type : t,
		data : $("#formPatient").serialize(),
		dataType : "text",
		complete : function(response, status)
		{
			onHospitalSaveComplete(response.responseText, status);
		}
	});
}); 

function onHospitalSaveComplete(response, status){
	if(status == "success")
	{
		var resultSet = JSON.parse(response);
			
		if(resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully Saved.");
			$("#alertSuccess").show();
					
			$("#divPatientGrid").html(resultSet.data);
	
		}else if(resultSet.status.trim() == "error"){
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	}else if(status == "error"){
		$("#alertError").text("Error While Saving.");
		$("#slertError").show();
	}else{
		$("#alertError").text("Unknown Error while Saving.");
		$("#alertError").show();
	}
	$("#hidPatientIDSave").val("");
	$("#formPatient")[0].reset();
}

//UPDATE========================================== 
$(document).on("click", ".btnUpdate", function(event) 
		{     
			$("#hidPatientIDSave").val($(this).closest("tr").find('#hidPatientIDUpdate').val());     
			$("#fname").val($(this).closest("tr").find('td:eq(0)').text());     
			$("#lname").val($(this).closest("tr").find('td:eq(1)').text());     
			$("#age").val($(this).closest("tr").find('td:eq(2)').text());     
			$("#gender").val($(this).closest("tr").find('td:eq(3)').text());
			$("#email").val($(this).closest("tr").find('td:eq(4)').text());     
			$("#phone").val($(this).closest("tr").find('td:eq(5)').text());     
			

});


//Remove Operation
$(document).on("click", ".btnRemove", function(event){
	$.ajax(
	{
		url : "PatientAPI",
		type : "DELETE",
		data : "Pid=" + $(this).data("patientID"),
		dataType : "text",
		complete : function(response, status)
		{
			onHospitalDeletedComplete(response.responseText, status);
		}
	});
});

function onHospitalDeletedComplete(response, status)
{
	if(status == "success")
	{
		var resultSet = JSON.parse(response);
			
		if(resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully Deleted.");
			$("#alertSuccess").show();
					
			$("#divPatientGrid").html(resultSet.data);
	
		}else if(resultSet.status.trim() == "error"){
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	}else if(status == "error"){
		$("#alertError").text("Error While Deleting.");
		$("#alertError").show();
	}else{
		$("#alertError").text("Unknown Error While Deleting.");
		$("#alertError").show();
	}
}

//CLIENTMODEL
function validateHospitalForm() {  
	// First Name 
	if ($("#fname").val().trim() == "")  
	{   
		return "Insert First Name.";  
	} 

	// Last Name  
	if ($("#lname").val().trim() == "")  
	{  
		return "Insert Last Name.";  
	}
	
	// Age  
	if ($("#age").val().trim() == "")  
	{   
		return "Insert Age.";  
	} 

	// Gender  
	if ($("#gender").val().trim() == "")  
	{  
		return "Insert Gender.";  
	}
	
	// Email  
	if ($("#email").val().trim() == "")  
	{   
		return "Insert Email.";  
	} 
	
	
	// Phone
	if ($("#phone").val().trim() == "")  
	{  
		return "Insert Phone number .";
	}
	
	//is Numerical value
	var phoneNum = $("#phone").val().trim();  
	if (!$.isNumeric(phoneNum))  {   
		return "Insert valid phone number.";  
	} 
	
	
	
	return true;
}
