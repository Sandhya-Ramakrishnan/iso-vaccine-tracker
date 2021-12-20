$(document).ready(function()
{
	console.log("onload")
	
	//$("#removeDocxID").hide();

	getFileNames();
	getCardsData();
	
	$("#uploadGTXlID").hide()
	$("#uploadGTPathID").hide()
	
});




function getFileNames(){
	const actualBtn=document.getElementById('gtxlUploadPathID');
	const fileChosen=document.getElementById('file-chosen');
	actualBtn.addEventListener('change',function(){
		
		 if($("#gtxlUploadPathID").val()==""){
			 fileChosen.textContent= "No File Chosen.."
				 $("#uploadGTPathID").hide()
		 }
		 else{
			 var a= this.files[0].name;
			 if( a.substring(a.length-4).trim()==".zip" ){
				 fileChosen.textContent=a 
				 $("#uploadGTPathID").show()
			 }
			 else{
				 alert("Upload the appropriate file")
				 $("#gtxlUploadPathID").val("")
				 fileChosen.textContent= "No File Chosen.."
				 $("#uploadGTPathID").hide()
			 }
			
			
		 }
		 
	})
	 
	const actualBtn1=document.getElementById('gtxlUploadID');
	const fileChosen1=document.getElementById('file-chosen1');
	actualBtn1.addEventListener('change',function(){

		
		 if($("#gtxlUploadID").val()==""){
			 fileChosen1.textContent= "No File Chosen.."
				 $("#uploadGTXlID").hide()
		 }
		 else{
			 var a= this.files[0].name;
			 if(a.substring(a.length-5).trim()==".xlsx"  || a.substring(a.length-4).trim()==".xls" ){
				 fileChosen1.textContent=a 
				 $("#uploadGTXlID").show()
			 }
			 else{
				 alert("Upload the appropriate file")
				 $("#gtxlUploadID").val("")
				 fileChosen1.textContent= "No File Chosen.."
				 $("#uploadGTXlID").hide()
			 }
			
			
		 }
		 
	
	   
	})
}

function checkConn(){
	$.ajax(
			{
			 url: "/GT/checkConn", 
			 type : "POST",
			 data : {
				 
			 },
			 success: function(data)
			 {	
				 console.log(data)
			 },
			 error: function(err)
			 {
				 alert("sorry error occured");
			 }
			 })
}



function gtxlUpload(){
	var formData = new FormData();
	formData.append('url', "/GT/gtxlUpload");
	formData.append('filedata', $("#gtxlUploadID")[0].files[0]);
	formData.append('flag', "");
//	formData.append('fileName', $("#getFileID").val());
	
	var requestObject = $.ajax({
		type : "POST",
	//	cache : false,
	    processData: false,  
	    contentType: false, 
	//	async : true,
		url : "/GT/gtxlUpload",
		data : formData
		//dataType : "json"
	});
	
	requestObject.done(function(data) {
		console.log(data);
		if(data.toUpperCase()=="SUCCESS"){
			alert("Successfull Uploaded")
			$("#gtxlUploadID").val();
			$("#file-chosen1").text("No file chosen..")
			$("#uploadGTXlID").hide()
		}
		else if(data=="header mismatch"){
			alert("Headers mismatch, Upload the correct file")
		}
		else if(data=="empty"){
			alert("Sheet is empty")
		}
		else if(data=="errorcheck"){
			alert("Uploaded excel has errors check out the error report downloaded below")
			$("#errReportID").click();
		}
		else if(data=="problem in error check"){
			alert("problem in error check")
		}
		else if(data=="Error Check Flag Null"){
			alert("Error Check Flag Null")
		}
		else{
			alert("Error Occurred")
		}
	});
	requestObject.fail(function(jxhr, status) {
		alert("unable to establish the connection to the server.");
		
	}); 
	
}

function gtxlUploadPath(){
	var formData = new FormData();
	formData.append('url', "/GT/gtxlUploadPath");
	formData.append('filedata', $("#gtxlUploadPathID")[0].files[0]);
//	formData.append('fileName', $("#getFileID").val());
	
	var requestObject = $.ajax({
		type : "POST",
		//	cache : false,
		processData: false,  
		contentType: false, 
		//	async : true,
		url : "/GT/gtxlUploadPath",
		data : formData
		//dataType : "json"
	});
	
	requestObject.done(function(data) {
		console.log(data);
		if(data=="success") {
			alert("Document successfully uploaded")
			$("#gtxlUploadPathID").val();
			$("#file-chosen").text("No file chosen..")
				$("#uploadGTPathID").hide()
		}
		
		else alert("error occured while saving the document")
	});
	requestObject.fail(function(jxhr, status) {
		alert("unable to establish the connection to the server.");
	}); 
	
}


function getCardsData(){
	$.ajax(
			{
			 url: "/GT/getGridData", 
			 type : "POST",
			 data : {
				 
			 },
			 success: function(data)
			 {	
				 console.log(data)
				 var ids = ["#crmIDheaderID", "#crmIDCountID", "#crmIDDateID","#mappedCIDheaderID","#MappedCIDCountID","#MappedCIDDateID","#cmfilesIDheaderID","","","#cmfilesIDCountID","#cmfilesIDDateID"];
				 for(var i=0;i<data.length;i++){
					 if(data[i]!=null){
						 $(ids[i]).text(data[i])
					 }
				 }
			 },
			 error: function(err)
			 {
				 alert("sorry error occured");
			 }
			 })
}


function weeklyMail(){
	$.ajax(
			{
				url: "/GT/weeklyMail", 
				type : "POST",
				data : {
					alertid:"589"
				},
				success: function(data)
				{
					console.log(data)
					alert(data)
				},
				error: function(err)
				{
					alert("sorry error occured");
				}
			})
}

function DailyMail(){
	$.ajax(
			{
				url: "/GT/DailyMail", 
				type : "POST",
				data : {
					alertid:"590"
				},
				success: function(data)
				{
					console.log(data)
					alert(data)
				},
				error: function(err)
				{
					alert("sorry error occured");
				}
			})
}
