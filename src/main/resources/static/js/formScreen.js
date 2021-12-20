$(document).ready(function() {
   /* $('.js-example-basic-single').select2({
    	closeOnSelect : false,
		placeholder : "select option",
		allowHtml: true,
		allowClear: true,
    });*/
    frameformefields();
});


function frameformefields(){
	$.ajax(
			{
			 url: "/GT/getFormData", 
			 type : "POST",
			 data : {
				 
			 },
			 success: function(data)
			 {	
				 console.log(data)
				 
				 var html="";
				 
				 if(data.length>0){
					 
					 html+='<table><tbody>'
						 
			var icon=["fa fa-id-badge","","fa fa-user-circle","","fa fa-users","","fa fa-bookmark","","fa fa-microchip","","fa fa-sitemap",""]
					 
					 for(var i=0;i<data.length;i++){
						 html+='<tr>'
						// html+='<td class="userRow"><i class="'+icon[i]+'"></i></td>'
						 html+='<td class="userRow"><h4>'+data[i]+'</h4></td>'
						/* if(i==8){
			html+='<td class="userRow"><input type="text" class="formInpts formdisabled" value="'+data[i+1]+'" > <i class="fa fa-pencil-square-o" style="padding-left:10px;pointer:cursor;" onclick="getFormDrops(0,this)"></i></td>'	 
						 }
						 else if( i==10){
							 html+='<td class="userRow"><input type="text" class="formInpts formdisabled" value="'+data[i+1]+'" > <i class="fa fa-pencil-square-o" style="padding-left:10px;pointer:cursor;" onclick="getFormDrops(1,this)"></i></td>'
						 }
						 else{
						  html+='<td class="userRow"><input type="text" class="formInpts formdisabled" value="'+data[i+1]+'" ></td>'
						 }*/
						 if(data[i]=="Story"){
						var stry =data[i+1].split(",")
							 	  html+='<td class="userRow"><select id="storyDrpID" >'	
							 		  //onchange="getFormDrops(this.value)"
						 for(var s=0;s<stry.length;s++) {
							 html+='<option value='+stry[s]+'>'+stry[s]+'</option>'
						 }
							  html+='</select></td>'					 
						 }
						 else if(data[i]=="Technology"){
							 html+='<td class="userRow" style="display:flex;"><div id="0MultiDiv"></div><div onclick="showDropSelected(\'#0multiSel\',\'Technology\')" style="align-self:center;padding-left:5px;cursor:pointer;"> <i class="fa fa-eye" aria-hidden="true"></i></div></td>'
						 }
						 else if(data[i]=="Domain"){
							 html+='<td class="userRow" style="display:flex;"><div id="1MultiDiv"></div><div onclick="showDropSelected(\'#1multiSel\',\'Domain\')" style="align-self:center;padding-left:5px;cursor:pointer;"> <i class="fa fa-eye" aria-hidden="true"></i></div></td>'
						 }
						 else{
							  html+='<td class="userRow"><input type="text" class="formInpts formdisabled" value="'+data[i+1]+'" ></td>' 
							//  html+='<td class="userRow"><label style=""class="formInpts formdisabled" >'+data[i+1]+'</label></td>' 
						 }
						 html+='</tr>'
						 i++
					 }
					 
					 html+='</tbody></table>'
						 
						$(".formContainer").html(html) 
						$("#storyDrpID").hide()
						$("#storyDrpID").multiselect({
							/*  buttonWidth:'auto',
						    selectAllText:' Select all',
						    selectAllValue:'multiselect-all',*/
							 buttonWidth:200,
							onChange:function (option, checked) {
								//console.log(option[0].value)
								console.log($(option[0]).text())
								getFormDrops($(option[0]).text())
							}
							
						})
						getFormDrops($("#storyDrpID option")[0].value)
						
						/*$(".formContainer table tr").each(function(i,e){
							if(i<4){
								$(e).find('input').addClass('formdisabled')
							}
						});*/
						 
				 }
				 
			 },
			 error: function(err)
			 {
				 alert("sorry error occured");
			 }
			 })
}


function getFormDrops(val){
	
	$.ajax(
			{
				url: "/GT/getformdropdown", 
				type : "POST",
				data : {
				 story:val
				},
				success: function(data)
				{	
					console.log(data)
					var l=0;
					while(l<=1){
						var html='<select id="'+l+'multiSel" multiple style="">'
						for(var a=0;a<data[l].length;a++){
							html+='<option value="'+data[l][a][0]+'">'+data[l][a][0]+'</option>'
						}
						html+='</select>'
						/*$('#'+l+'MultiDiv').html(html)
						$('#'+l+'MultiDiv').multiselect({
								buttonWidth:'auto',
							    selectAllText:' Select all',
							    selectAllValue:'multiselect-all',
							    enableFiltering:true,
						});
						$('#'+l+'MultiDiv').hide()*/
					//$('#'+l+'MultiDiv').multiselect('refresh');
						
						if(l==0){
							$("#0MultiDiv").html(html)
							$("#0multiSel").hide()
							$("#0multiSel").multiselect({
								numberDisplayed: 1,
							    enableFiltering:true,
							    includeSelectAllOption:true,
							    maxHeight:350,
							    enableCaseInsensitiveFiltering:true,
							    buttonWidth:200,


							})
						}
						else if(l==1){
							$("#1MultiDiv").html(html)
							$('#1multiSel').hide()
							$('#1multiSel').multiselect({
							    enableFiltering:true,
							    includeSelectAllOption:true,
							    numberDisplayed: 1,
							    maxHeight:350,
							    enableCaseInsensitiveFiltering:true,
							    buttonWidth:200,


							})
							}
						l++
						
					}
					 var d =data[2]
					 console.log(d.length)
					 for(var f=0;f<d.length;f++){
						for(var h=0;h<d[f].length;h++){
							var v =d[f][h].split(",")
						v.forEach(function(m){
							 $("#"+h+"multiSel").multiselect('select',m)
						})
							 
						}
					 }
					 
					
					/*
						 $("#"+i+"multiSel").select2({
						    	closeOnSelect : false,
								placeholder : i==0 ? "Select the Technology" : "Select the Domain",
								allowHtml: true,
								allowClear: true,
								multiple:true,
								//dropdownAutoWidth: true,
								width: '100%',
								border: '1px solid #e4e5e7'
						 });*/
				},
				error: function(err)
				{
					alert("sorry error occured");
				}
			})
}


function saveDetails(){

	try{var tech =$("#0multiSel").val().join(',')}catch(e){alert("Select Technologies to Continue"); return }
	try{var dom =$("#1multiSel").val().join(',')}catch(e){alert("Select Domain to Continue"); return}
	
	if(tech ==null || tech.trim()==""){
		alert("Select Technologies to Continue")
		return
	}
	
	if( dom ==null || dom.trim()==""){
		alert("Select Domain to Continue")
		return
	}
	
	$.ajax(
			{
				url: "/GT/saveFormDetials", 
				type : "POST",
				data : {
					story:$("#storyDrpID").val(),
					tech:tech,
					domain:dom
				},
				success: function(data)
				{
				 if(data.toUpperCase()=="SUCCESS"){
					// alert("Successfully Saved")
					 
					 alert("CM Details has been successfully saved !\n\nTechnology : "+tech+"\nDomain : "+dom+"")
					 
					 frameformefields()
				 }
				 else{
					 alert("Error Occured while Saving")
				 }
				},
				error: function(err)
				{
					alert("sorry error occured");
				}
			})

}

function login(){
	
	$.ajax(
			{
				url: "/GT/login", 
				type : "POST",
				data : {
					loginId:"119060",
					loginPwd:"TATA@123"
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


function showDropSelected(id,t){
	console.log(id+" "+t)
	
	var val =$(id).val().join(" , ");
	if(val ==null || val.trim()==""){
		val = "No "+t+" has been selected.."
	}
	
	$("#dropSelAreaTitle").text("Selected "+t)
	$("#dropSelAreaBody").html('<p>'+val+'</p>')
	$("#dropSelArea").modal('show')
}

