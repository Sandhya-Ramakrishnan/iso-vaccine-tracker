var reldrop=null;
var vacTyDrop=null;
var semp="";
var trendcity="";
var accessArr =[]
    $(document).ready(function() {
    	
    	
    	/*if (typeof(Storage) !== "undefined") {
					    		if(localStorage.getItem("emp")!==null){
					    			getvaccinationLocation(localStorage.getItem("emp"))
					    		}
    	}*/
    	
    	
    	$("#date").html(new Date().toDateString())
    	//getvaccinationLocation()
    						$("#country").prop('disabled', true);
							$("#state").prop('disabled', true);
							$("#location").prop('disabled', true);
							$("#hospital").prop('disabled', true);
    	
    	$(".header").hide()
    	$(".content").hide()
    	//snackBar("welcome im new")
    	
    	$("#getVendorScrID").hide()
    	
    	 $('#empid, #location, #state, #country',"#hospital").keypress(function(e) {
 		    var key = e.which;
 		    if (key == 13) // the enter key code
 		    {
 		    	 $('#checkEmp').click()
 		      return false;
 		    }
 		  });
    	
    	
    	$('#empIdInput').keypress(function(e) {
 		    var key = e.which;
 		    if (key == 13) // the enter key code
 		    {
 		   	if(accessArr[0].includes("T")){
				$('#geratetknSearchID').click()
			}
			else if(accessArr[0].includes("ALL")){
				$('#geratetknSearchID').click()
				//$("#submissionSearchID").show()
			}
			else {
				$("#submissionSearchID").click()
			}
 		      return false;
 		    }
 		  });
    	
    	
    	/*$('#sIcon').keypress(function(e) {
 		    var key = e.which;
 		    if (key == 13) // the enter key code
 		    {
 		    	 $('#searchFlagId').click()
 		      return false;
 		    }
 		  });*/
    	
    	
    		$(".searchFlagHandler li").click(function(e){
    			//console.log(e)
    			//console.log($(e))
    			//console.log(document.getElementById("searchFlagLabel").innerText =e.target.innerText)
    			$("#srchFlag").val(e.target.id)
    			
    			if(accessArr[0].includes("T")){
    				$('#geratetknSearchID').click()
    			}
    			else if(accessArr[0].includes("ALL")){
    				$('#geratetknSearchID').click()
    				//$("#submissionSearchID").show()
    			}
    			else {
    				$("#submissionSearchID").click()
    			}
    		 return false
    		});
    	
    	
    	
    	$("#generateToken").hide()
    	$("#addDepentID").hide()
    	
    	$("#empIdClear").click(function(){
    	  $("#empIdInput").val("")
    		});
    	
    	
    	//for modal
    	$('.openmodale').click(function (e) {
            e.preventDefault();
            $('.modale').addClass('opened');
    	});
    	$('.closemodale').click(function (e) {
            e.preventDefault();
            $('.modale').removeClass('opened');
       });
    	
    	
    	$("#empid").focusout(function(){
    		 getvaccinationLocation($("#empid").val())
    		});
    	
    	
    	
    	getFormFields();

//search in grid #dashboardgrid

$("#searchinput").on("keyup", function() {
    var value = $(this).val().toLowerCase();
    $("#tablebody tr").filter(function() {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });

    	
    	});
  
function onchangeSearch(){
	
	$("#searchinput").on("keyup", function() {
    var value = $(this).val().toLowerCase();

    $("#tablebody tr").filter(function() {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
}    

    
function checkEmp(e){
	
var emp = $("#empid").val() !=null ? $("#empid").val().trim() : ""
   var cntry = $("#country").val() !=null ? $("#country").val().trim() : ""
   var state = $("#state").val() !=null ? $("#state").val().trim() : ""
   var loc = $("#location").val() !=null ? $("#location").val().trim() : ""
	   var hos = $("#hospital").val() !=null ? $("#hospital").val().trim() : ""

	if(emp=="") {snackBar("Employee Id cannot be empty");return}
	if(cntry=="") {snackBar("Country cannot be empty");return}
	if(state=="") {snackBar("State cannot be empty");return}
	if(loc=="") {snackBar("Location cannot be empty");return}
	if(hos=="") {snackBar("Hospital cannot be empty");return}
	
	 $.ajax(
				{
					url: "/VaccinationTracker/checkEmp", 
					type : "POST",
					data : {
					 empid:emp,
					 flag:"",
					},
					success: function(data)
					{
						console.log(data)
						 accessArr =[]
						if(data.trim()!=""){
							if(data.includes("#") && data.includes("^")){
								accessArr.push(data.split("#")[0])
								accessArr.push("^,"+(data.split("^")[1]).split(",").toString())
							}
							else if(data.includes("^")){
								accessArr.push("^,"+data.split("^")[1].toString())
							}
							else {
								if(data.trim()=="T")accessArr.push("#"+data)
								else accessArr.push(data)
							}
							
							if(accessArr !== undefined && accessArr.length > 0){
								
								
							/*	if (typeof(Storage) !== "undefined") {
									if(localStorage.getItem("log")==null){
										
										 localStorage.setItem("emp", emp);
							    		  localStorage.setItem("country", cntry);
							    		  localStorage.setItem("state", state);
							    		  localStorage.setItem("location", loc);
							    		  localStorage.setItem("hosiptal", hos);
							    		  localStorage.setItem("log", "1");
									}
						    		} else {
						    		 console.warn("Sorry, your browser does not support Web Storage...")
						    		}*/
						    	
								
								
								$(".header").show()
						    	$(".content").show()
						    	$(".modalSec").hide()
						    	if(accessArr.length == 2){
						    		if(accessArr[0]=="T"){
						    			$('#geratetknSearchID').show()
						    		}
						    		
						    		var acs =accessArr[1]
					    			if(acs.includes('C')){
					    				console.log("cowin")
					    			}
					    			if(acs.includes('V')){
					    				console.log("vaccine")
					    			}
					    			if(acs.includes('P')){
					    				console.log("post")
					    			}
						    	}
						    	else{
						    		if(accessArr[0].includes('^')){
						    			$("#geratetknSearchID").hide()
						    			$(".userCardContainer").hide()
						    			var acs =accessArr[0].split("^")[1]
						    			if(acs.includes('C')){
						    				console.log("cowin")
						    			}
						    			if(acs.includes('V')){
						    				console.log("vaccine")
						    			}
						    			if(acs.includes('P')){
						    				console.log("post")
						    			}
						    		}
						    		else if(accessArr[0].includes('#')){
					    				console.log("token")
					    				$('#geratetknSearchID').show()
					    				$("#submissionSearchID").hide()
					    				$(".userdetailsBox").hide()
					    			}
						    		else {
						    			console.log("else")
						    			console.log(accessArr[0])
						    			if(accessArr[0].trim()=="ALL") $('#geratetknSearchID').show()
						    		}
						    	}
							}
						}
						
						/*if(data.trim()=="N")snackBar("Your Employee id is not registered as volunteer")
						else if(data.trim()=="Y"){
							$(".header").show()
					    	$(".content").show()
					    	$(".modalSec").hide()
						}*/
					},
					error: function(err)
					{
						snackBar("sorry error occured");
					}
				})
	
}


function getvaccinationLocation(e){
	 getPageLoad()
	
		$.ajax(
				{
					url: "/VaccinationTracker/getvaccinationLocation", 
					type : "POST",
					data : {
					 empid:e,
					},
					success: function(data)
					{
						console.log(data)
						
						if(data!=""){
							
							var ids=["country","state","location","hospital"]				
							for(var i=0;i<data.length;i++){
								var html='<option value data-isdefault="true">--Select--</option>'
								for(var j=0;j<data[i].length;j++){
									html+='<option value="'+data[i][j][0]+'">'+data[i][j][0]+'</option>'
								}
								//html+='</select>'
							   $(`#${ids[i]}`).html(html)
							}
							reldrop=data[3]
							vacTyDrop=data[4]
							
							if(e.trim()!=""){
								$("#country").prop('disabled', false);
								$("#state").prop('disabled', false);
								$("#location").prop('disabled', false);
								$("#hospital").prop('disabled', false);
								$("#country").focus()
							}
							
							/*if (typeof(Storage) !== "undefined") {
					    		if(localStorage.getItem("log")!==null){
					    			
					    			$("#empid").val(localStorage.getItem("emp"))
					    			$("#country").val(localStorage.getItem("country"))
					    			$("#state").val(localStorage.getItem("state"))
					    			$("#location").val(localStorage.getItem("location"))
					    			$("#hospital").val(localStorage.getItem("hosiptal"))
					    			
					    			
					    			$("#checkEmp").click()
					    		}
					    		} else {
					    		   console.warn("Sorry, your browser does not support Web Storage...");
					    		}*/
						}
						else {
							$("#country").prop('disabled', true);
							$("#state").prop('disabled', true);
							$("#location").prop('disabled', true);
							$("#hospital").prop('disabled', true);
							snackBar("Exception occurred,failed to load dropdowns")
						}
						
						 removePageLoad()
					},
					error: function(err)
					{
						snackBar("sorry error occured");
						removePageLoad
					}
				})
	
}

function getTokenScrn(f){
	
		
		
	
		$('#geratetknSearchID').addClass('generateTokenSearchSelected')
		$('#submissionSearchID').removeClass('generateTokenSearchSelected')
		$('#getVendorScrID').removeClass('generateTokenSearchSelected')
		$('#getReportScrID').removeClass('generateTokenSearchSelected')
		$('#getDashboardScrID').removeClass('generateTokenSearchSelected')
	   var emp = $("#empIdInput").val().trim()
	   
	   
	  /* if(!f){
	    var search = /[a-z]/i.test(emp)
	    if(search){
	    	getSubmissionScr()
	    	return;
	    }
	   }*/
	   
	   if(emp=="") {
		   snackBar("Search after entering a valid Employee ID "); 
		   $('#geratetknSearchID').removeClass('generateTokenSearchSelected')
		   $("#geratetknSearchID").css('background','white')
		   $("#geratetknSearchID").css('color','#17A2B8')
		   return
		   }
	   
	   var srcFlag =$("#srchFlag").val()
	   if(srcFlag.trim()==""){
		   snackBar("Please select company name to proceed"); 
		   $('#geratetknSearchID').removeClass('generateTokenSearchSelected')
		   $("#geratetknSearchID").css('background','white')
		   $("#geratetknSearchID").css('color','#17A2B8')
		   return
	   }
	   
	   $("#geratetknSearchID").css('background','#17A2B8')
	   $("#geratetknSearchID").css('color','snow')
	   $("#submissionSearchID").css('background','white')
	   $("#submissionSearchID").css('color','#17A2B8')
	   $("#getVendorScrID").css('background','white')
	   $("#getVendorScrID").css('color','#17A2B8')
	   $("#getReportScrID").css('background','white')
	   $("#getReportScrID").css('color','#17A2B8')
	   $("#getDashboardScrID").css('background','white')
	   $("#getDashboardScrID").css('color','#17A2B8')
	   //$("#submissionSearchID").css('color','snow')
	   
	   
	   	var spoc =$("#empid").val()+","+$("#country").val()+","+$("#state").val()+","+$("#location").val()+","+$("#hospital").val()
	   
	$.ajax(
			{
				url: "/VaccinationTracker/getDetails", 
				type : "POST",
				async:true,
				data : {
				 empid:emp,
				 flag:srcFlag,
				 spocID:spoc,
				},
				beforeSend: function() {
					 getPageLoad()
			    },
				success: function(data)
				{
					console.log(data)
					
					semp=emp;
					
					if(data!=""){
						var html=''
							
							for(var i=0;i<data[3].length;i++){
								
								if(data[3][i][11].trim()==""){
									html+='<div class="userCard">'
								}
								else{
									html+='<div class="userCard usrDisabled">'
								}
							
								if(data[3][i][14].trim()!="" && data[3][i][14].indexOf("-")==-1){
								  html+='<div class="dueCard"> Dose2 due by  '+data[3][i][14]+' days </div>'	
								
								}
								if(data[3][i][15].trim()!="" ){
									if(data[3][i][15].trim()=="F"){
								  		html+='<div class="statusCard">Fully Vaccinated</div>'	
									}else if(data[3][i][15].trim()=="R"){
								  		html+='<div class="statusCard">Registered</div>'	
									}else if(data[3][i][15].trim()=="U"){
								  		html+='<div class="statusCard">Not Registered</div>'	
									}
								
								}
								html+='<div class="userCardTop">'
								html+='<div><div class="circle circle-color1"><p class="circleText">'+getInitial(data[3][i][0])+'</p></div><h3 class="cardName" id="name">'+data[3][i][0]+'</h3>'
								
								if(data[3][i][11].trim()==""){
									html+='</div>'
										
									html+='<div class="chkbxDiv"><p><input type="checkbox" name="checkUser" id="cb'+i+'" value="'+i+'" ><label for="cb'+i+'"></label></p></div></div>'
								}
								else{
									html+='</div>'
									
									html+='<div class="" >'
									html+='<p style=" align-self: center; color: #07d20d; font-weight: bold; width: 100%; ">'+data[3][i][11].toUpperCase()+'</p></div></div>'	
								}
								
								html+='<div class="cardRow2">'
								html+='<div class="cardRow2Item">'
								html+='<p class="cardLabel">'+data[0][1][0]+'</p>'
								html+='<p class="cardText labelRed" id="cowin" contenteditable style="border: 1px solid #ece7e7;">'+data[3][i][1]+'</p></div>'
								
								html+='<div class="cardRow2Item">'
								html+='<p class="cardLabel">'+data[0][2][0]+'</p>'
								html+='<p class="cardText" id="relid">'+data[3][i][2]+'</p></div>'
								
								html+='<div class="cardRow2Item">'
								html+='<p class="cardLabel">'+data[0][7][0]+'</p>'
								html+='<p class="cardText" id="gessID">'+data[3][i][12]+'</p></div></div>'
								
								
								  
								html+='<div class="cardDoseDiv"><p class="dummyDose" style="padding-left:14px">Dose 0</p>'
								html+='<div class="tableHeading">'
								html+='  <p>'+data[0][3][0]+'</p> <p>'+data[0][4][0]+'r</p><p>'+data[0][5][0]+'</p></div></div>'
								
								html+='<div class="cardDoseDiv">'+
								'<p class="doseLabel">'
								//Edited for adding radio button for dose 998643
								try{
								if(data[3][i][13]=="Radio 1"){
									html+='<input type="radio" id="doseRadio'+i+'" name="doseRadio'+i+'" value="dose1" checked style="cursor:pointer">'
									+'<label id="doselabel" class="doseLabel" style="padding-left:0.5rem;cursor:pointer" for="doseRadio'+i+'">'
								+data[3][i][3]+'</label></p>'
								}
								
								else if(data[3][i][13]=="Radio 2"){
									html+='<input type="radio" id="doseRadio'+i+'" name="doseRadio'+i+'" value="dose1"  style="cursor:pointer">'
									+'<label id="doselabel" class="doseLabel" style="padding-left:0.5rem;cursor:pointer" for="doseRadio'+i+'">'
								+data[3][i][3]+'</label></p>'
								}
								else{
									html+='<label id="doselabel" class="doseLabel" style="padding-left:0.5rem;cursor:pointer" for="doseRadio'+i+'">'+data[3][i][3]+'</label></p>'
								}
								}
								catch(e){
									/*html+='<input type="radio" id="doseRadio'+i+'" name="doseRadio'+i+'" value="dose1" style="cursor:pointer">'
									+'<label class="doseLabel" style="padding-left:0.5rem;cursor:pointer" for="doseRadio'+i+'">'
								+data[3][i][3]+'</label></p>'*/
								}
		                        html+='<div class="doseTable doseItemGreen"><div class="doseItem dosedate">'
		                        if(data[3][i][4].trim()==""){
		                        	html+='<input type="date" class="editOn" id="date" style="width: 100%;height: 30px;font-weight:bold;text-align: center;"></div>'
		                        }
		                      //  else html+='<p class="cardText" id="date" >'+data[3][i][4]+'</p></div>'
								  else html+='<input type="date" class="editOn" id="date" value="'+formatDateY(data[3][i][4])+'" style="width: 100%;height: 30px;font-weight:bold;text-align: center;"></div>'
		                        
		                        html+='<div class="doseItem">'
		                        
		                        if(data[3][i][5].trim()=="" ){
		                        	html+='<select id="center" class="editOn" ><option value data-isdefault="true">--Select--</option>'
		                        	
		                        		for(var vc=0;vc<data[4].length;vc++){
		                        			html+='<option value'+data[4][vc][0]+'>'+data[4][vc][0]+'</option>'
		                        		}
		                        		
		                        	html+='</select></div>'
		                        }
		                     //   else html+='<p class="cardText" id="center">'+data[3][i][5]+'</p></div>'
		                       else {
		                    	   
		                    	   html+='<select id="center" class="editOn" >'
			                        	
		                        		for(var vc=0;vc<data[4].length;vc++){
		                        			if(data[3][i][5].trim()==data[4][vc][0].trim()){
		                        				html+='<option value'+data[4][vc][0]+' selected="true" >'+data[4][vc][0]+'</option>'
		                        			}
		                        			else html+='<option value'+data[4][vc][0]+'>'+data[4][vc][0]+'</option>'
		                        		}
		                        		
		                        	html+='</select></div>'
		                       }
		                        
		                        
		                        if(data[3][i][6].trim()==""){
		                        	 html+='<div class="doseItem">'
		                        		 
		                              		html+='<select id="type" class="editOn" ><option value data-isdefault="true">--Select--</option>'
		                                     	
		                                 		for(var vc=0;vc<data[2].length;vc++){
		                                 			html+='<option value'+data[2][vc][0]+'>'+data[2][vc][0]+'</option>'
		                                 		}
		                                 		
		                                 	html+='</select>'
		                                 		
		                        		html+= '</div></div></div>'
		                        }
		                        //else html+='<div class="doseItem"><p class="cardText" id="type">'+data[3][i][6]+'</p></div></div></div>'
		                        else {
		                        	
		                        	html+='<div class="doseItem">'
		                        		 
	                              		html+='<select id="type" class="editOn" >'
	                                     	
	                                 		for(var vc=0;vc<data[2].length;vc++){
	                                 			if(data[3][i][6].trim()==data[2][vc][0].trim()){
	                                 				html+='<option value'+data[2][vc][0]+' selected="true">'+data[2][vc][0]+'</option>'
	                                 			}
	                                 			else html+='<option value'+data[2][vc][0]+'>'+data[2][vc][0]+'</option>'
	                                 		}
	                                 		
	                                 	html+='</select>'
	                                 		
	                        		html+= '</div></div></div>'
		                        }
		                        
		                        	
		                     	html+='<div class="cardDoseDiv"><p class="doseLabel">'
                   
								//Edited for adding radio button for dose 998643
								try{
									//Edited for adding radio button for dose 998643
								if(data[3][i][13]=="Radio 1"){
									html+='<input type="radio" id="doseRadio'+i+'" name="doseRadio'+i+'" value="dose2" style="cursor:pointer">'
									+'<label id="doselabel" class="doseLabel" style="padding-left:0.5rem;cursor:pointer" for="doseRadio'+i+'">'
								+data[3][i][7]+'</label></p>'
								}
								else if(data[3][i][13]=="Radio 2"){
									html+='<input type="radio" id="doseRadio'+i+'" name="doseRadio'+i+'" value="dose2" checked style="cursor:pointer">'
									+'<label id="doselabel" class="doseLabel" style="padding-left:0.5rem;cursor:pointer" for="doseRadio'+i+'">'
								+data[3][i][7]+'</label></p>'
								}
								else{
									html+='<label id="doselabel" class="doseLabel" style="padding-left:0.5rem;cursor:pointer" for="doseRadio'+i+'">'+data[3][i][7]+'</label></p>'
								}
								
								}
								catch(e){
									/*html+='<input type="radio" id="doseRadio2'+i+'" name="doseRadio'+i+'" value="dose2" style="cursor:pointer">'
									+'<label class="doseLabel"  style="padding-left:0.5rem;cursor:pointer" for="doseRadio'+i+'">'
								+data[3][i][7]+'</label></p>'*/
								}
								
		                        html+='<div class="doseTable doseItemGreen"><div class="doseItem dosedate">'
		                        
		                        	 if(data[3][i][8].trim()=="" ){
		                             	html+='<input type="date" class="editOn"  id="date1"  style="width: 100%;height: 30px;text-align: center;"></div>'
		                             }
		                        	 //else html+='<p class="cardText" id="date1" > '+data[3][i][8]+'</p></div>'
		                             else  html+='<input type="date" class="editOn"  value="'+formatDateY(data[3][i][8])+'" id="date1"  style="width: 100%;height: 30px;text-align: center;"></div>'
		                             
		                             html+='<div class="doseItem">'
		                             
		                             if(data[3][i][9].trim()==""){
		                            	 html+='<select id="center1" class="editOn" ><option value data-isdefault="true">--Select--</option>'
		                                 	
		                             		for(var vc=0;vc<data[4].length;vc++){
		                             			html+='<option value'+data[4][vc][0]+'>'+data[4][vc][0]+'</option>'
		                             		}
		                             		
		                             	html+='</select></div>'
		                             }
		                            // else html+='<p class="cardText" id="center1">'+data[3][i][9]+'</p></div>'
		                             else {
		                            	 html+='<select id="center1" class="editOn" >'
			                                 	
			                             		for(var vc=0;vc<data[4].length;vc++){
			                             			if(data[3][i][9].trim()==data[4][vc][0].trim()){
			                             				html+='<option value'+data[4][vc][0]+' selected="true">'+data[4][vc][0]+'</option>'
			                             			}
			                             			else html+='<option value'+data[4][vc][0]+'>'+data[4][vc][0]+'</option>'
			                             		}
			                             		
			                             	html+='</select></div>'
		                             }
		                             
		                             
		                             if(data[3][i][10].trim()==""){
		                             	 html+='<div class="doseItem">'
		                             		 
		                             		html+='<select id="type1" class="editOn" ><option value data-isdefault="true">--Select--</option>'
		                                    	
		                                		for(var vc=0;vc<data[2].length;vc++){
		                                			html+='<option value'+data[2][vc][0]+'>'+data[2][vc][0]+'</option>'
		                                		}
		                                		
		                                	html+='</select>'
		                             		 
		                             		 html+='</div></div></div></div>'
		                             }
		                          //   else html+='<div class="doseItem"><p class="cardText" id="type1">'+data[3][i][10]+'</p></div></div></div></div>'
		                             else {
		                            	 html+='<div class="doseItem">'
		                             		 
			                             		html+='<select id="type1" class="editOn" >'
			                                    	
			                                		for(var vc=0;vc<data[2].length;vc++){
			                                			if(data[3][i][10].trim()==data[2][vc][0].trim()){
			                                				html+='<option value'+data[2][vc][0]+' selected="true">'+data[2][vc][0]+'</option>'
			                                			}
			                                			else html+='<option value'+data[2][vc][0]+'>'+data[2][vc][0]+'</option>'
			                                		}
			                                		
			                                	html+='</select>'
			                             		 
			                             		 html+='</div></div></div></div>'
			                             			 
			                             			 
		                            	 
		                             }
		                        html+='</div>'
		                    
							}
						$(".userCardContainer").show()
						$(".userdetailsBox").hide()
						$(".addVendorDIv").hide()
							$(".userCardContainer").html(html)
							$(".reportsDIv").hide()
							$(".turnoutDashboardDiv").hide()
								$("#generateToken").show()
								$("#addDepentID").show()
								
								$('html, body').animate({
						              scrollTop: $(".userCardContainer").offset().top
						        }, 500); 
						
						//console.log(new Date($.now()))
					}
					else{
			//			$(".userCardContainer").html("<div style='display:flex;'><h4 style=';margin:80px;'>Employee Not Found, to add an Employee or a Dependent   <a onclick='showAddUser()' style='color:#316cd0;text-decoration: underline;text-underline-position: from-font;'> Add Member</a></h4></div>")
						$(".userCardContainer").html("<h2 style='text-align:center;margin:80px;'>Employee or the Token Not Found</h2>")
							$("#generateToken").hide()
							$(".userCardContainer").show()
							$(".userdetailsBox").hide()
							$(".addVendorDIv").hide()
							$("#addDepentID").hide()
								$(".reportsDIv").hide()
								$(".turnoutDashboardDiv").hide()
							
					}
					
					
					removePageLoad();
				},
				error: function(err)
				{
					snackBar("sorry error occured");
					removePageLoad();
				},
			})
			
			//removePageLoad();
}


function getInitial(d){
	var name =d.trim().split(" ")
	if(name.length>1) return name[0].charAt(0)+name[1].charAt(0)
	else return name[0].charAt(0)
}
    // ["#empIdInput","#name","#cowin","#relid","Dose 1","#date","#center","#type"]


function generateToken(){
	 getPageLoad()
	   var emp = $("#empIdInput").val().trim()
	   if(emp=="") {snackBar("Search after entering a valid Employee ID "); removePageLoad();return}
	 
	 var src = $("#srchFlag").val().trim()
	 if(src=="") {snackBar("Please select company name to proceed"); removePageLoad();return}
	//var ids =["#empIdInput","#name","#cowin","#relid","Dose 1","#date","#center","#type","Dose 2","#date1","#center1","#type1"]
	
	var lp=$(".userCardContainer").children().length
	if(lp==$(".usrDisabled").length){snackBar("Token has already been generated for the individual and the dependent");removePageLoad(); return}
	
	
	var chk =$("input[name=checkUser]:checked").map(function () {
	    return this.value;
	  }).get().join(",")
	  if(chk!=""){
		  var chkArr =chk.split(",")
		  
		  var ins=[]
			var arr=""
			for(var i=0;i<chkArr.length;i++){
				//For  radio button alert 998643///////////
				var radiono=Number(chkArr[i]);
var radiobtnname="doseRadio"+radiono;
console.log(radiobtnname);
var x="";
try{
 x=$("input[name="+radiobtnname+"]:checked").val();
if( x==""){
	snackBar("Please select either dose1 or dose2 for the selected individual"); removePageLoad();return;
}
}
catch(e){
	
}
//For  radio button alert 998643/////////
				ins=[]
				ins.push($("#empIdInput").val())
				ins.push($(".userCardContainer").children().eq(chkArr[i]).find("#name").text().trim())
				ins.push($(".userCardContainer").children().eq(chkArr[i]).find("#cowin").text().trim())
				ins.push($(".userCardContainer").children().eq(chkArr[i]).find("#relid").text().trim())
				ins.push("Dose 1")
				$(".userCardContainer").children().eq(chkArr[i]).find('#date').hasClass("editOn") ? 	ins.push(formatDate($(".userCardContainer").children().eq(chkArr[i]).find('#date').val())) : ins.push($(".userCardContainer").children().eq(chkArr[i]).find('#date').text())
				$(".userCardContainer").children().eq(chkArr[i]).find('#center').hasClass("editOn") ? 	ins.push($(".userCardContainer").children().eq(chkArr[i]).find('#center').val()) : ins.push($(".userCardContainer").children().eq(chkArr[i]).find('#center').text())
				$(".userCardContainer").children().eq(chkArr[i]).find('#type').hasClass("editOn") ? 	ins.push($(".userCardContainer").children().eq(chkArr[i]).find('#type').val()) : ins.push($(".userCardContainer").children().eq(chkArr[i]).find('#type').text())
				ins.push("Dose 2")
				$(".userCardContainer").children().eq(chkArr[i]).find('#date1').hasClass("editOn") ? 	ins.push(formatDate($(".userCardContainer").children().eq(chkArr[i]).find('#date1').val())) : ins.push($(".userCardContainer").children().eq(chkArr[i]).find('#date1').text())
				$(".userCardContainer").children().eq(chkArr[i]).find('#center1').hasClass("editOn") ? 	ins.push($(".userCardContainer").children().eq(chkArr[i]).find('#center1').val()) : ins.push($(".userCardContainer").children().eq(chkArr[i]).find('#center1').text())
				$(".userCardContainer").children().eq(chkArr[i]).find('#type1').hasClass("editOn") ? 	ins.push($(".userCardContainer").children().eq(chkArr[i]).find('#type1').val()) : ins.push($(".userCardContainer").children().eq(chkArr[i]).find('#type1').text())
			
			//For adding dose1 or dose2 998643
			ins.push(x);
			
			//ins.push($("#gessID").text().trim())
			ins.push($(".userCardContainer").children().eq(chkArr[i]).find("#gessID").text().trim())
			arr +=ins.join("^")+"!"	
			}
		//	console.log(ins)
			console.log(arr.slice(0,-1))
			
			var spoc =$("#empid").val()+","+$("#country").val()+","+$("#state").val()+","+$("#location").val()+","+$("#hospital").val()
			
		//	console.log(spoc)
			
			
			$.ajax(
						{
							url: "/VaccinationTracker/generateToken", 
							type : "POST",
							async:true,
							data : {
							 empid:$("#empIdInput").val(),
							 flag:spoc,
							 input:arr.slice(0,-1),
							 SpocID:src
							},
							success: function(data)
							{
								console.log(data)
								if(data!=""){
									getTokenScrn()
									snackBar("Token Successfully Generated ")
								}
								else{
									snackBar("error occured while generating Token")
								}
								removePageLoad();
							},
							error: function(err)
							{
								snackBar("sorry error occured");
								removePageLoad();
							}
						})
		  
		 
	  }
	  else{
		  snackBar("Select an Individual to Generate Token")
		  removePageLoad();
	  }
	  
	  
	
	
	
	
}






function formatDate(e){
	if(e.includes("-")){
		var e =e.split("-")
		if(e.length<=1) return ""
		else return e[2]+"/"+e[1]+"/"+e[0]	
	}
	else return ""
	
}

function formatDateY(e){
	if(e.includes("/")){
		var e =e.split("/")
		if(e.length<=1) return ""
		else return e[2]+"-"+e[1]+"-"+e[0]	
	}
	else return ""
	
}



function getSubmissionScr(){
		
	$('#geratetknSearchID').removeClass('generateTokenSearchSelected')
	$('#submissionSearchID').addClass('generateTokenSearchSelected')
	$('#getVendorScrID').removeClass('generateTokenSearchSelected')
	$('#getReportScrID').removeClass('generateTokenSearchSelected')
	$('#getDashboardScrID').removeClass('generateTokenSearchSelected')
	
	   var emp = $("#empIdInput").val().trim()
	   if(emp=="") {
		   snackBar("Search after entering a valid Employee ID ");
			$('#submissionSearchID').removeClass('generateTokenSearchSelected')
		   $("#submissionSearchID").css('background','white')
		   $("#submissionSearchID").css('color','#17A2B8')
		   return
		   }
	var srch = $("#srchFlag").val().trim()
	if(srch=="") {
		snackBar("Please select company name to proceed")
		$('#submissionSearchID').removeClass('generateTokenSearchSelected')
		$("#submissionSearchID").css('background','white')
		$("#submissionSearchID").css('color','#17A2B8')
		return
	}
	
	  $("#submissionSearchID").css('background','#17A2B8') 
	  $("#submissionSearchID").css('color','snow') 
	   $("#geratetknSearchID").css('background','white')
	   $("#geratetknSearchID").css('color','#17A2B8')
	    $("#getVendorScrID").css('background','white')
	   $("#getVendorScrID").css('color','#17A2B8')
	   $("#getReportScrID").css('background','white')
	   $("#getReportScrID").css('color','#17A2B8')
	     $("#getDashboardScrID").css('background','white')
	   $("getDashboardScrID").css('color','#17A2B8')
	   
	    $(".stage2Div").hide()
	$(".stage3Div").hide()
	$(".stage4Div").hide()
	$(".stage2Div").removeClass('disableResponse')
	$(".stage3Div").removeClass('disableResponse')
	$(".stage4Div").removeClass('disableResponse')
	$('input[name="stage2r"]').prop('checked',false)
	$('input[name="stage3r"]').prop('checked',false)
	$('input[name="stage4r"]').prop('checked',false)
	   var spoc =$("#empid").val()+","+$("#country").val()+","+$("#state").val()+","+$("#location").val()+","+$("#hospital").val()
	   
	   getPageLoad()
		$.ajax(
				{
					url: "/VaccinationTracker/getSubmissionData", 
					type : "POST",
					async:true,
					data : {
					 empid:emp,
					/* flag:srch,*/
					flag:null,
					 spocID:spoc,
					},
					success: function(data)
					{
						console.log(data)
						
						if(data!="" && data[0].length>=1){
							
							var html =''
								var html1 =''
									
									console.log(data[0].length)
									var vacPop=[];
								var cowPop=[];
									var  frStages="";
							for(var i=0;i<data[0].length;i++){
								
					html+='<div id="userdetailLeft'+i+'" onclick="handleBoxUserChange(\''+i+'\')" class="userdetailLeftItem">'	
					html+='<div class="circle circle-color1"><p class="circleText">'+getInitial(data[0][i][1])+'</p></div>'	
					html+='<h3 class="cardName" id="SubnameID'+i+'">  '+data[0][i][1]+'</h3>' 
					if(data[0][i][0].trim()!=""){
						html+='<h4 class="cardTokenText'+i+'" style="background:#17a917;color: white;padding:6px;border-radius:8px;">'+data[0][i][0].toUpperCase()+'</h4>'
					}
					html+='<i class="fas fa-chevron-right"  id="userLeftArrow'+i+'"style=""></i></div>'
						
						
						html1+='<div class="userEntryHide" id="userdetailRight'+i+'">'
						
						
						html1+='<div class="EntryInputItem" id="cowinStageID'+i+'" style=""><div class="stage2Div" style=""> <label style="align-self: center;">Cowin Beneficiary ID :</label> <div style="display: flex;"> <input type="radio" style="width: 40px;height: 22px;align-self: center;" name="stage2r'+i+'" value="Y" onchange="cowinStat(this,\''+i+'\')"> <label style="align-self: center;">Verified</label> </div> <div style="display: flex;padding: 8px 0px;"> <input type="radio" style="width: 40px;height: 22px;align-self: center;" name="stage2r'+i+'" value="N" onchange="cowinStat(this,\''+i+'\')"> <label style="align-self: center;white-space: nowrap;">Non Verified</label> </div> </div><p>'+data[3][1][0]+'<span style="color: red;"> *</span></p>'
						//var pat ="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\\..*?)\\..*/g, '$1');"
		            	if(data[0][i][2].trim()==""){
		            		 html1+='<input type="text" id="SubcovinID'+i+'" >'
		            	}
		            	else{
		            		
		            		 html1+='<input type="text" id="SubcovinID'+i+'" value="'+data[0][i][2]+'" >'
		            	}
						
					html1+='<div class="" id="cowinStageN'+i+'" style="display:none;border: none;"><p style="color: #26335E;font-size: 0.8rem;font-weight: 500;margin-bottom: 0.3rem !important;">Reason<span style="color: red;">*</span></p>'
					
			           
					//html1+='<input type="text" value="" id="SubreasonID'+i+'">'
					html1+='<select id="coReason'+i+'">'
					html1+='<option value data-isdefault="true">--Select--</option>'
					html1+='<option value="Mandatory 84 days not completed after Dose-1">Mandatory 84 days not completed after Dose-1</option>'
					html1+='<option value="Another Vaccine taken during Dose-1">Another Vaccine taken during Dose-1</option>'
					html1+='<option value="Dose 1 not updated in Cowin Portal">Dose 1 not updated in Cowin Portal</option>'
					html1+='<option value="Others">Others</option></select>'
						
					html1+='</div></div>'	
						
							html1+='<div class="EntryInputItem" id="vaccineStage'+i+'" >'
								
							html1+='<div class="stage3Div" style="justify-content: space-evenly;"> <label style="align-self: center;">Vaccinated :</label> <div style="display: flex;"> <input type="radio" style="width: 40px; height: 22px;" name="stage3r'+i+'" value="Y" onchange="vaccineStat(this,\''+i+'\')"> <label style="align-self: center;">Yes</label> </div> <div style="display: flex;"> <input type="radio" onchange="vaccineStat(this,\''+i+'\')" style="width: 40px; height: 22px;" name="stage3r'+i+'" value="N"> <label style="align-self: center;">No </label> </div> </div>'	
								
							
							html1 += '<div class="EntryInputItem" id="vaccineName'+i+'" style="display:none;border: none;"><p>'+ data[3][2][0] + '<span>*</span></p>'

							html1 += '<select id="SubVnameID'+i+'"><option value data-isdefault="true">--Select--</option>'
							for (var vc = 0; vc < data[1].length; vc++) {
								html1 += '<option value="' + data[1][vc][0] + '">' + data[1][vc][0] + '</option>'
							}
							html1 += '</select></div>'
							
							
								html1+='<div class="EntryInputItem" id="reason'+i+'" style="display:none;border: none;"><p>'+data[3][3][0]+'<span>*</span></p>'
						           
								//html1+='<input type="text" value="" id="SubreasonID'+i+'">'
								html1+='<select id="SubreasonID'+i+'">'
								html1+='<option value data-isdefault="true">--Select--</option>'
								html1+='<option value="Mandatory 90 days after covid not completed">Mandatory 90 days after covid not completed</option>'
							html1+='<option value="High BP identified before Vaccination">High BP identified before Vaccination</option>'
					html1+='<option value="Pre - Medical conditions">Pre - Medical conditions</option>'
					html1+='<option value="Others">Others</option></select>'	
								html1+='</div>'	
								
								
				            html1+='</div>'
						

								
								//
									/*html1+='<div class="EntryInputItem" id="exitStage'+i+'" style="">'
							           
									html1+='<div class="stage4Div" style="justify-content: space-evenly;"> <label style="align-self: center;">Post Vaccination Exit :</label> <div style="display: flex;"> <input type="radio" style="width: 40px; height: 22px;" name="stage4r'+i+'" value="Y"> <label style="align-self: center;">Yes</label> </div> <div style="display: flex;"> <input type="radio" style="width: 40px; height: 22px;" name="stage4r'+i+'" value="N"> <label style="align-self: center;">No </label> </div> </div>'
										
									html1+='</div>'*/
									
								//	
								
									
								html1+='<br></br> <div class="btnDiv" id="submissionCtrlID" style="text-align:center;">'
								//html1+='</div><h2>fdasf</h2>'
								
								html1+='<button class="button-primary" id="submitDetails'+i+'"  style="display:none;"onclick="finalSubmission(\''+i+'\')">Submit</button></div></div>'
								//html1+='</div>'
								
								
								frStages+=data[0][i][3].trim()+"#";
								vacPop.push(data[0][i][4].trim()+"#"+data[0][i][5].trim())
								cowPop.push(data[0][i][6].trim()+"#"+data[0][i][7].trim())
							}		
							$(".userdetailsBoxLeft").html(html)		
							$(".userdetailsBoxRight").html(html1)		
									
							$("#userdetailRight0").removeClass('userEntryHide').addClass('userEntryShow')
							$("#userdetailLeft0").addClass('userdetailLeftItemSelected')
							
							$(".userCardContainer").hide()
							$(".addVendorDIv").hide()
							$("#generateToken").hide()
							$("#addDepentID").hide()
							$(".reportsDIv").hide()
							$(".turnoutDashboardDiv").hide()
							
							$(".userdetailsBox").show()
						
							
							$('html, body').animate({
					              scrollTop: $(".userdetailsBox").offset().top
					        }, 500); 
							
						}
						else{
							$(".userCardContainer").html("<h2 style='text-align:center;margin:80px;'>Employee or the Token Not Found</h2>")
							$("#generateToken").hide()
							$(".userCardContainer").show()
							$(".userdetailsBox").hide()
							$(".addVendorDIv").hide()
							$("#addDepentID").hide()
							$(".reportsDIv").hide()
							$(".turnoutDashboardDiv").hide()
						}
						
						//getProcces();
						removePageLoad()
						
						toggleProcesses(frStages,vacPop,cowPop);
						
						
					},
					error: function(err)
					{
						snackBar("sorry error occured");
						removePageLoad()
					}
				})
	
				

}

function handleBoxUserChange(index) {
    for(var i = 0; i< $('.userdetailsBoxLeft').children().length; i++) {
        $(`#userdetailRight${i}`).hide();
        $(`#userdetailLeft${i}`).removeClass("userdetailLeftItemSelected");
        $(`#userLeftArrow${i}`).hide();
    }
    $(`#userdetailRight${index}`).show();
    $(`#userdetailLeft${index}`).addClass("userdetailLeftItemSelected");
    $(`#userLeftArrow${index}`).show();

}

function vaccineStat(e,i){
	console.log($(e).val())
	
	var r=$(e).val().trim()
	if(r=="Y"){
			
			$(`#scowID${i}`).show()
			$(`#vaccineName${i}`).show()
			$(`#reason${i}`).hide()
	}
	else if(r=="N"){
		$(`#scowID${i}`).show()
		$(`#vaccineName${i}`).hide()
		$(`#reason${i}`).show()
	}
	
}
function cowinStat(e,i){
	console.log($(e).val())
	
	var r=$(e).val().trim()
	if(r=="Y"){
		
		//$(`#scowID${i}`).show()
		$(`#SubcovinID${i}`).show()
		$(`#SubcovinID${i}`).prev().show()
		$(`#cowinStageN${i}`).hide()
	}
	else if(r=="N"){
		//$(`#scowID${i}`).show()
		$(`#cowinStageN${i}`).show()
		$(`#SubcovinID${i}`).hide()
		$(`#SubcovinID${i}`).prev().hide()
	}
	
}


function finalSubmission(i){
	
	
	   var emp = $("#empIdInput").val().trim()
	   if(emp=="") {snackBar("Search after entering a valid Employee ID "); return}
	   
	   var srcFlag =$("#srchFlag").val()
	   if(srcFlag.trim()==""){
		   snackBar("Please select company name to proceed"); 
		   return
	   }
	
	if(i==""){snackBar("error");return}
	
	
	if($(`.cardTokenText${i}`).text().trim()==""){
		snackBar("Token number is mandatory to continue..")
		return;
	}
	
	//var ids= ["#SubnameID","#SubvaccineStaID","#SubcovinID","#SubVnameID","#SubreasonID"]
	var arr="";
	var ins =[]
	
		// ins.push($("#empIdInput").val().trim())
		ins.push($(`#SubnameID${i}`).text().trim())
		
		if($(`#cowinStageID${i}`).hasClass('enableReponse')){
			
			var coRdo = $(`input[name="stage2r${i}"]:checked`).val()
			/*if(coRdo!=undefined){
				ins.push(coRdo)
				ins.push($(`#SubcovinID${i}`).val())
			}
			else {
				$(`#cowinStageID${i}`).removeClass("disableResponse").addClass("enableReponse");
				$(`#vaccineStage${i}`).removeClass("disableResponse").addClass("enableReponse");
				$(`#exitStage${i}`)
					snackBar("Select the status of Cowin Beneficiary ID to continue");
					return
				}*/
			if(coRdo!=undefined){
				ins.push(coRdo)
				if(coRdo=="Y"){
						if($(`#SubcovinID${i}`).val().trim()==""){snackBar("Enter the Cowin Beneficiary ID to continue"); return}
						else ins.push($(`#SubcovinID${i}`).val())
				}
				else if(coRdo=="N"){
					if($(`#coReason${i}`).val().trim()==""){snackBar("Enter the reason to continue"); return}
					else ins.push($(`#coReason${i}`).val())
				}
				else {
					snackBar("error");
					return;
				}
			}
			else {
					snackBar("Select the status of Vaccination to continue");
					return
					}
		}
		else{
			ins.push("")
			ins.push("")
		}
		
		
	if($(`#vaccineStage${i}`).hasClass('enableReponse')){
		var vacRdo = $(`input[name="stage3r${i}"]:checked`).val()
		if(vacRdo!=undefined){
			ins.push(vacRdo)
			if(vacRdo=="Y"){
					if($(`#SubVnameID${i}`).val().trim()==""){snackBar("Select the Vaccine type to continue"); return}
					else ins.push($(`#SubVnameID${i}`).val())
			}
			else if(vacRdo=="N"){
				if($(`#SubreasonID${i}`).val().trim()==""){snackBar("Enter the reason for not vaccinating"); return}
				else ins.push($(`#SubreasonID${i}`).val())
			}
			else {
				snackBar("error");
				return;
			}
		}
		else {
				snackBar("Select the status of Vaccination to continue");
				return
				}
	}
	else {
		ins.push("")
		ins.push("")
	}
	
		
	/*if($(`#exitStage${i}`).hasClass('enableReponse')){
		var posRdo = $(`input[name="stage4r${i}"]:checked`).val()
		if(posRdo!=undefined){
			ins.push(posRdo)
		}
		else {
				snackBar("Select the status of Post Vaccination Exit to continue");
				return
			}
	}
	else {
		ins.push("")
	}*/
		
	var aa = /[a-z]/i.test($(`.cardTokenText${i}`).text().trim())
    if(aa){
    	ins.push($(`.cardTokenText${i}`).text().trim())
    }
    else{
    	
    	ins.push(emp)
    }
		//ins.push($(`.cardTokenText${i}`).text().trim());

	
		/*if($(`#SubvaccineStaID${i}`).val()=="Vaccinated"){
			
			
			if($(`#SubcovinID${i}`).val()==""){snackBar("Continue entering valid Cowin ID"); return}
			else ins.push($(`#SubcovinID${i}`).val())
			if($(`#SubVnameID${i}`).val()==""){snackBar("Vaccination Type Cannot be empty"); return}
			else ins.push($(`#SubVnameID${i}`).val())
			
		}
		else if($(`#SubvaccineStaID${i}`).val()=="Not Vaccinated"){
			
			
			if($(`#SubcovinID${i}`).val()==""){snackBar("Continue entering valid Cowin ID"); return}
			else ins.push($(`#SubcovinID${i}`).val())
			if($(`#SubreasonID${i}`).val()==""){snackBar("Continue entering the reason for not vaccinating"); return}
			else ins.push($(`#SubreasonID${i}`).val())
		}
		else {
			snackBar("Select Vaccination Status to Continue")
			return
		}*/
		arr=ins.join("^")
		//console.log(ins)
		console.log(arr)
	
	
	 $.ajax(
				{
					url: "/VaccinationTracker/sumbitStatus", 
					type : "POST",
					data : {
					flag:srcFlag,
					 empid:emp,
					 input:arr
					},
					success: function(data)
					{
						console.log(data)
						if(data.toUpperCase()=="SUCCESS") {
							getSubmissionScr()
							snackBar("Successfully Submitted")
						}
						else{
							snackBar("Exception Occured while Submitting the response")
						}
					},
					error: function(err)
					{
						snackBar("sorry error occured");
					}
				})
	
}

function snackBar(d) {
    // Get the snackbar DIV
    var x = document.getElementById("snackbar")
    // add content
     x.innerHTML =d
    // Add the "show" class to DIV
    x.className = "show";
   

    // After 3 seconds, remove the show class from DIV
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
}

function getPageLoad(){
	//console.log("pageon")
	//console.log(new Date($.now()))
	
	 $(".loading-screen").show()
	// $("*").find('div').not(".loading-screen , .loading").css('opacity', '0.5');
	 $("*").find('div').not(".loading-screen , .loading").css({
		    'opacity': '0.5', 
		    'pointer-events': 'none'
		});
	
}
function removePageLoad(){
	//console.log("pageoff")
	//	console.log(new Date($.now()))
	
	//$("*").find('div').not(".loading-screen",".loading","#snackbar").css('opacity', '');
	$("*").find('div').not(".loading-screen",".loading","#snackbar").css({
	    'opacity': '', 
	    'pointer-events': ''
	});
	$(".loading-screen").hide()
}


function showAddUser(e){
	
	
	 
	var ele ='<div style="padding: 10px;background: linear-gradient(rgb(255, 255, 255) 0%, rgb(255, 255, 255) 95%, rgba(155, 155, 155, 0.22) 100%) 0% 0% transparent;border: 1px solid #E8E8E8;"> <div style="display: flex; padding: 10px;"> <label class="labelQ" style="align-self: center;">Employee ID :</label> <div style="padding-left: 10px;"> <input type="text" disabled value="'+semp+'"id="empA"  style="width: 200px; height: 28px; text-align: center; font-size: 16px;"> </div>   </div> <div style="display: flex; padding: 10px;"> <label class="labelQ" style="align-self: center;">Name of the Dependent or Self :</label> <div style="padding-left: 20px;"> <input type="text" id="nameA" style=" text-align: center; font-size: 16px;padding:2px;width:20em;">   </div> </div>   <div style="display: flex; padding: 10px;"> <label class="labelQ" style="align-self: center;">Relationship :</label> <div style="padding-left: 20px;"> <select id="relA"></select>   </div> </div> <div style="display: flex; padding: 10px;"> <label class="" style="align-self: center;">Cowin Beneficiary ID :</label> <div style="padding-left: 20px;"> <input type="text" id="cowinA" style=" text-align: center; font-size: 16px;padding:2px;width:20em;">   </div> </div>   </div>'
		
		ele+='<div class="btnDiv" style=""><button class="button-primary" id="saveDetailsID" onclick="addNewUser()" style="">Save Details</button></div>'
			
	   
	 $(".modal-body").html(ele); 
	$(".modal_header").text("Add Member"); 
	var html='<option value data-isdefault="true">--Select--</option>'
		for(var j=0;j<reldrop.length;j++){
			html+='<option value="'+reldrop[j][0]+'">'+reldrop[j][0]+'</option>'
		}
		
	$("#relA").html(html)
	

	$(".openmodale").click()
	$(".modale").show()
	
	
	 $('#empA, #nameA, #relA, #cowinA').keypress(function(e) {
		    var key = e.which;
		    if (key == 13) // the enter key code
		    {
		    	 $('#saveDetailsID').click()
		      return false;
		    }
		  });
}

function addNewUser(){
	
	var emp= $("#empA").val().trim()
	var name= $("#nameA").val().trim()
	var rel= $("#relA").val().trim()
	var cow= $("#cowinA").val().trim()
	
	if(emp=="") {snackBar("Employee Id cannot be empty");return}
	if(name=="") {snackBar("Name cannot be empty");return}
	if(rel=="") {snackBar("Select Relationship to continue");return}
	//if(cow=="") {snackBar("Cowin Benificiary ID cannot be empty");return}
	
	
	$.ajax(
				{
					url: "/VaccinationTracker/addMember", 
					type : "POST",
					data : {
					 empid:emp,
					 input:name,
					 spocID:rel,
					 flag:cow
					},
					success: function(data)
					{
						console.log(data)
						if(data.toUpperCase()=="SUCCESS"){
							snackBar("Member Successfully Added");
							$(".openmodale").click()
							$(".modale").hide()
							getTokenScrn();
							}
						else if(data.toUpperCase()=="Y"){
							snackBar("Member Already Exists in the Records")
						}
						else{
							snackBar("Exception Occured while saving")
						}
					},
					error: function(err)
					{
						snackBar("sorry error occured");
					}
				})
}




/*function getTokenScrn(){
	
	$(".stage2Div").hide()
	$(".stage3Div").hide()
	$(".stage4Div").hide()
	$(".stage2Div").removeClass('disableResponse')
	$(".stage3Div").removeClass('disableResponse')
	$(".stage4Div").removeClass('disableResponse')
	$('input[name="stage2r"]').prop('checked',false)
	$('input[name="stage3r"]').prop('checked',false)
	$('input[name="stage4r"]').prop('checked',false)
	
	
	$('#geratetknSearchID').addClass('generateTokenSearchSelected')
	$('#submissionSearchID').removeClass('generateTokenSearchSelected')
	$('#getVendorScrID').removeClass('generateTokenSearchSelected')
   var emp = $("#empIdInput").val().trim()
   if(emp=="") {
	   snackBar("Search after entering a valid Employee ID "); 
	   $('#geratetknSearchID').removeClass('generateTokenSearchSelected')
	   $("#geratetknSearchID").css('background','white')
	   $("#geratetknSearchID").css('color','#17A2B8')
	   return
	   }
   
   $("#geratetknSearchID").css('background','#17A2B8')
   $("#geratetknSearchID").css('color','snow')
   $("#submissionSearchID").css('background','white')
   $("#submissionSearchID").css('color','#17A2B8')
   $("#getVendorScrID").css('background','white')
   $("#getVendorScrID").css('color','#17A2B8')
   //$("#submissionSearchID").css('color','snow')
   
   
   $.ajax(
		{
			url: "/VaccinationTracker/getDetails", 
			type : "POST",
			async:true,
			data : {
			 empid:emp
			},
			beforeSend: function() {
				 getPageLoad()
		    },
			success: function(data)
			{
				console.log(data)
				
				semp=emp;
				
				if(data!=""){
					var html=''
						
						for(var i=0;i<data[3].length;i++){
							
							if(data[3][i][11].trim()==""){
								html+='<div class="userCard">'
							}
							else{
								html+='<div class="userCard usrDisabled">'
							}
							
							html+='<div class="userCardTop">'
							html+='<div><div class="circle circle-color1"><p class="circleText">'+getInitial(data[3][i][0])+'</p></div><h3 class="cardName" id="name">'+data[3][i][0]+'</h3>'
							
							if(data[3][i][11].trim()==""){
								html+='</div>'
									
								html+='<div class="chkbxDiv"><p><input type="checkbox" name="checkUser" id="cb'+i+'" value="'+i+'" ><label for="cb'+i+'"></label></p></div></div>'
							}
							else{
								html+='</div>'
								
								html+='<div class="" style="background: #1da53c;width: 60px;height: 60px;border-radius: 50%;display: flex;text-align: center;">'
								html+='<p style=" align-self: center; color: snow; font-weight: bold; width: 100%; ">'+data[3][i][11].toUpperCase()+'</p></div></div>'	
							}
							
							html+='<div class="cardRow2">'
							html+='<div class="cardRow2Item">'
							html+='<p class="cardLabel">'+data[0][1][0]+'</p>'
							html+='<p class="cardText labelRed" id="cowin" style="padding-left: 12px;">'+data[3][i][1]+'</p></div>'
							
							html+='<div class="cardRow2Item">'
							html+='<p class="cardLabel">'+data[0][2][0]+'</p>'
							html+='<p class="cardText" id="relid">'+data[3][i][2]+'</p></div>'
							
							html+='<div class="cardRow2Item">'
							html+='<p class="cardLabel">'+data[0][7][0]+'</p>'
							html+='<p class="cardText" id="relid">'+data[3][i][12]+'</p></div></div>'
							
	                        html+='</div>'
	                    
						}
					$(".userCardContainer").show()
					$(".userdetailsBox").hide()
					$(".addVendorDIv").hide()
					$("#processSubmitID").hide()
						$(".userCardContainer").html(html)
							$("#generateToken").show()
							$("#addDepentID").show()
							
							$('html, body').animate({
					              scrollTop: $(".userCardContainer").offset().top
					        }, 500); 
					
					//console.log(new Date($.now()))
					getProcces();
				}
				else{
					$(".userCardContainer").html("<div style='display:flex;'><h4 style=';margin:80px;'>Employee Not Found, to add an Employee or a Dependent   <a onclick='showAddUser()' style='color:#316cd0;text-decoration: underline;text-underline-position: from-font;'> Add Member</a></h4></div>")
						$("#generateToken").hide()
						$(".userCardContainer").show()
						$(".userdetailsBox").hide()
						$(".addVendorDIv").hide()
						$("#addDepentID").hide()
						
				}
				
				
				removePageLoad();
			},
			error: function(err)
			{
				snackBar("sorry error occured");
				removePageLoad();
			},
		})
		
		
		
		//removePageLoad();
}*/


function getProcces(){
	var emp = $("#empIdInput").val().trim()
	   if(emp=="") {
		   snackBar("Search after entering a valid Employee ID or Token ID"); 
		return   
	   }
	 var search = /[a-z]/i.test(emp)
	  if(search){
		  $.ajax(
					{
						url: "/VaccinationTracker/processSubmit", 
						type : "POST",
						async:true,
						data : {
						 token:emp,
						 cowin:"",
						 vaccine:"",
						 exit:"",
						},
						success: function(data)
						{
							console.log(data)
							
							if(data!=""){
								
								if(data=="2"){
									$(".stage2Div").show()
									html =' <button class="button-primary" id="processSubmitID" onclick="processSubmit(\'2\')">SUBMIT</button>'
										$(".processBtnDIv").html(html)	
								
								$("#generateToken").hide()
						    	$("#addDepentID").hide()
								}
								else if(data=="3"){
									//$('input[name="stage3r"]:checked').val()
									$(".stage2Div").show()
									$(".stage2Div").addClass('disableResponse')
									$('input[name="stage2r"][value="Y"]').prop('checked',"checked")
								//	$(".stage3Div").show()
								html =' <button class="button-primary" id="processSubmitID" onclick="processSubmit(\'3\')">SUBMIT</button>'
								$(".processBtnDIv").html(html)	
								
								$("#generateToken").hide()
						    	$("#addDepentID").hide()
								}
								else if(data=="4"){
									//$('input[name="stage3r"]:checked').val()
									$(".stage2Div").show()
									$('input[name="stage2r"][value="Y"]').prop('checked',"checked")
							//		$(".stage3Div").show()
									$(".stage2Div").addClass('disableResponse')
									$(".stage3Div").addClass('disableResponse')
									$('input[name="stage3r"][value="Y"]').prop('checked',"checked")
									$(".stage4Div").show()
									html =' <button class="button-primary" id="processSubmitID" onclick="processSubmit(\'4\')">SUBMIT</button>'
										$(".processBtnDIv").html(html)	
										
										$("#generateToken").hide()
										$("#addDepentID").hide()
										
								}
								else if(data=="5"){
									//$('input[name="stage3r"]:checked').val()
									$(".stage2Div").show()
									$('input[name="stage2r"][value="Y"]').prop('checked',"checked")
									//$(".stage3Div").show()
									$('input[name="stage3r"][value="Y"]').prop('checked',"checked")
									$(".stage4Div").show()
									$('input[name="stage4r"][value="Y"]').prop('checked',"checked")
									$(".stage2Div").addClass('disableResponse')
									$(".stage3Div").addClass('disableResponse')
									$(".stage4Div").addClass('disableResponse')
									
									$("#generateToken").hide()
									$("#addDepentID").hide()
									$("#processSubmitID").hide()
								}
								
							}
							else{
								snackBar("Exception Occurred While searching with Token");
							}
						},
						error: function(err)
						{
							snackBar("sorry error occured");
						}
					})
	  }
}

function processSubmit(e){
	console.log(e)
	$("#generateToken").hide()
	$("#addDepentID").hide()
	var co=""
	var va=""
	var ex=""
		var emp = $("#empIdInput").val().trim()
		   if(emp=="") {
			   snackBar("Search after entering a valid Employee ID or Token ID"); 
			return   
		   }
	if(e=="2"){
		co =$('input[name="stage2r"]:checked').val()
		if(co==undefined || co==""){
			snackBar("Select Your Reponse to Continue");
			return
		}
	}
	/*else if(e=="3"){
		//co =$('input[name="stage2r"]:checked').val()
		va =$('input[name="stage3r"]:checked').val()
		if(va==undefined || va==""){
			snackBar("Select Your Reponse to Continue");
			return
		}
	}*/
	else if(e=="4"){
		
		//co =$('input[name="stage2r"]:checked').val()
		//va =$('input[name="stage3r"]:checked').val()
		ex =$('input[name="stage4r"]:checked').val()
		if(ex==undefined || ex==""){
			snackBar("Select Your Reponse to Continue");
			return
		}
	}
	
	$.ajax(
			{
				url: "/VaccinationTracker/processSubmit", 
				type : "POST",
				data : {
				 token:emp,
				 cowin:co,
				 vaccine:va,
				 exit:ex,
				},
				success: function(data)
				{

					console.log(data)
					
					if(data!=""){
						snackBar("Response Successfully Submitted");
						getSubmissionScr();
						if(data=="2"){
							$(".stage2Div").show()
							html =' <button class="button-primary" id="processSubmitID" onclick="processSubmit(\'2\')">SUBMIT</button>'
								$(".processBtnDIv").html(html)	
						
						$("#generateToken").hide()
				    	$("#addDepentID").hide()
						}
						else if(data=="3"){
							//$('input[name="stage3r"]:checked').val()
							$(".stage2Div").show()
							$(".stage2Div").addClass('disableResponse')
							$('input[name="stage2r"][value="Y"]').prop('checked',"checked")
						//	$(".stage3Div").show()
						html =' <button class="button-primary" id="processSubmitID" onclick="processSubmit(\'3\')">SUBMIT</button>'
						$(".processBtnDIv").html(html)	
						
						$("#generateToken").hide()
				    	$("#addDepentID").hide()
						}
						else if(data=="4"){
							//$('input[name="stage3r"]:checked').val()
							$(".stage2Div").show()
							$('input[name="stage2r"][value="Y"]').prop('checked',"checked")
						//	$(".stage3Div").show()
							$(".stage2Div").addClass('disableResponse')
							$(".stage3Div").addClass('disableResponse')
							$('input[name="stage3r"][value="Y"]').prop('checked',"checked")
							$(".stage4Div").show()
							html =' <button class="button-primary" id="processSubmitID" onclick="processSubmit(\'4\')">SUBMIT</button>'
								$(".processBtnDIv").html(html)	
								
								$("#generateToken").hide()
								$("#addDepentID").hide()
								
						}
						else if(data=="5"){
							//$('input[name="stage3r"]:checked').val()
							$(".stage2Div").show()
							$('input[name="stage2r"][value="Y"]').prop('checked',"checked")
							//$(".stage3Div").show()
							$('input[name="stage3r"][value="Y"]').prop('checked',"checked")
							$(".stage4Div").show()
							$('input[name="stage4r"][value="Y"]').prop('checked',"checked")
							$(".stage2Div").addClass('disableResponse')
							$(".stage3Div").addClass('disableResponse')
							$(".stage4Div").addClass('disableResponse')
							
							$("#generateToken").hide()
							$("#addDepentID").hide()
							
							$("#processSubmitID").hide()
						}
						
					}
					else{
						snackBar("Exception Occurred While searching with Token");
					}
				
				},
				error: function(err)
				{
					snackBar("sorry error occured");
				}
			})
	
}


function getVendorForm(){
	
	$('#getVendorScrID').addClass('generateTokenSearchSelected')
	$('#submissionSearchID').removeClass('generateTokenSearchSelected')
	$('#geratetknSearchID').removeClass('generateTokenSearchSelected')
	$('#getDashboardScrID').removeClass('generateTokenSearchSelected')
	  
	   $("#getVendorScrID").css('background','#17A2B8')
	   $("#getVendorScrID").css('color','snow')
	   $("#geratetknSearchID").css('background','white')
	   $("#geratetknSearchID").css('color','#17A2B8')
	   $("#submissionSearchID").css('background','white')
	   $("#submissionSearchID").css('color','#17A2B8')
 $("#getDashboardScrID").css('background','white')
	   $("getDashboardScrID").css('color','#17A2B8')
	
	   //vaccinTypeVdr //relVdr
	
	html ='<div style="padding: 10px; background:linear-gradient(rgb(255, 255, 255) 0%, rgb(255, 255, 255) 95%, rgba(155, 155, 155, 0.22) 100%) 0% 0% transparent;border: 1px solid #E8E8E8;">   <div style="display: flex; padding: 10px;"> <label class="labelQ" style="align-self: center;width: 180px;">Name :</label> <div style="padding-left: 20px;"> <input type="text" id="nameVdr" style=" text-align: center; font-size: 16px;padding:2px;width:20em;">   </div> </div>   <div style="display: flex; padding: 10px;"> <label class="labelQ" style="align-self: center;width: 180px;">Vendor Type :</label> <div style="padding-left: 20px;"> <input type="text" id="vendorTypeVdr" style=" text-align: center; font-size: 16px;padding:2px;width:20em;">   </div> </div>   <div style="display: flex; padding: 10px;"> <label class="labelQ" style="align-self: center;width: 180px;">Relationship :</label> <div style="padding-left: 20px;"> <input type="text" id="relVdr" style=" text-align: center; font-size: 16px;padding:2px;width:20em;">   </div> </div>     <div style="display: flex; padding: 10px;"> <label class="labelQ" style="align-self: center;width: 180px;">Phone Number :</label> <div style="padding-left: 20px;"> <input type="number" id="phoneVdr" min="1" style="width: 200px; height: 28px; text-align: center; font-size: 16px;"> </div>   </div>   <div style="display: flex; padding: 10px;"> <label class="labelQ" style="align-self: center;width: 180px;">Vaccine Type :</label> <div style="padding-left: 20px;">  <input type="text" id="vaccinTypeVdr" style=" text-align: center; font-size: 16px;padding:2px;width:20em;">  </div>   </div> </div>'
	html+='<div class="btnDiv"> <button class="button-primary" id="addVendrBtnID" onclick="saveVendor()">Add Vendor</button></div>'
		
	$(".addVendorDIv").html(html)
	$(".addVendorDIv").show()
	$(".userCardContainer").hide()
	$("#generateToken").hide()
	$("#processSubmitID").hide()
	$(".userdetailsBox").hide()	
	$("#addDepentID").hide()
	$(".stage2Div").hide()
	$(".stage3Div").hide()
	$(".stage4Div").hide()
	
	var html='<option value data-isdefault="true">--Select--</option>'
		for(var j=0;j<vacTyDrop.length;j++){
			html+='<option value="'+vacTyDrop[j][0]+'">'+vacTyDrop[j][0]+'</option>'
		}
	$("#vaccinTypeVdr").html(html)
	
	
	var html='<option value data-isdefault="true">--Select--</option>'
		for(var j=0;j<reldrop.length;j++){
			html+='<option value="'+reldrop[j][0]+'">'+reldrop[j][0]+'</option>'
		}
		
	$("#relVdr").html(html)
	
}

function saveVendor(){
	
	var name= $("#nameVdr").val().trim()
	var vnTy= $("#vendorTypeVdr").val().trim()
	var rel= $("#relVdr").val().trim()
	var phn= $("#phoneVdr").val().trim()
	var vcTy= $("#vaccinTypeVdr").val().trim()
	
	if(name=="") {snackBar("Name cannot be empty");return}
	if(vnTy=="") {snackBar("Vendor Type cannot be empty");return}
	if(rel=="") {snackBar("Relationship cannot be empty");return}
	if(phn=="") {snackBar("Phone Number cannot be empty");return}
	if(vcTy=="") {snackBar("Vaccine Type cannot be empty");return}
	
	
	$.ajax(
			{
				url: "/VaccinationTracker/saveVendor", 
				type : "POST",
				data : {
				 empid:name,
				 input:vnTy,
				 spocID:rel,
				 flag:phn,
				 vaccine:vcTy,
				},
				success: function(data)
				{
					console.log(data)
					if(data.toUpperCase()=="SUCCESS"){
						snackBar("Vendor Successfully Added");
						$("#empIdInput").val(name)
						getTokenScrn(true);
						}
					else if(data.toUpperCase()=="Y"){
						snackBar("Vendor Already Exists in the Records")
					}
					else{
						snackBar("Exception Occured while saving")
					}
				},
				error: function(err)
				{
					snackBar("sorry error occured");
				}
			})
}

function toggleProcesses(d,pop,copop){
	
	
	var lp =$(".userdetailsBoxLeft").children().length
	var e= d.split("#");
	
	var acss=""
	if(accessArr == undefined && accessArr.length == 0){
		return
	}
	else if(accessArr.length == 2){
		acss=accessArr[1].split("^")[1].toString()
	}
	else{
		if(accessArr[0].trim()=="ALL"){
			acss="C,V,P"
		}
		else{
			acss=accessArr[0];
		}
	}
	
	for(var i=0;i<lp;i++){
		
		if(e[i].split("^")[0]=="1"){
			if(e[i].split("^")[1]=="N"){
				$(`input[name="stage2r${i}"][value="N"]`).prop('checked',"checked")
				$(`#cowinStageID${i}`).removeClass("enableReponse").addClass("disableResponse");
				$(`#vaccineStage${i}`).addClass("disableResponse");
				$(`#exitStage${i}`).addClass("disableResponse");
				$(`#submitDetails${i}`).hide()
			}
			else{
				$(`input[name="stage2r${i}"][value="Y"]`).prop('checked',"checked")
				$(`#cowinStageID${i}`).removeClass("enableReponse").addClass("disableResponse");
				if(acss.includes('V')){$(`#vaccineStage${i}`).removeClass("disableResponse").addClass("enableReponse");$(`#submitDetails${i}`).show()}
				else {
					$(`#vaccineStage${i}`).removeClass("enableReponse").addClass("disableResponse")
				$(`#submitDetails${i}`).hide()
				}
				$(`#exitStage${i}`).removeClass("enableReponse").addClass("disableResponse");
			}
			
		}
		else if(e[i].split("^")[0]=="2" ){
			
			if(e[i].split("^")[1]=="N"){
				$(`input[name="stage2r${i}"][value="Y"]`).prop('checked',"checked")
				$(`input[name="stage3r${i}"][value="N"]`).prop('checked',"checked")
				$(`#cowinStageID${i}`).removeClass("enableReponse").addClass("disableResponse");
				$(`#vaccineStage${i}`).removeClass("enableReponse").addClass("disableResponse");
				$(`#exitStage${i}`).addClass("disableResponse");
			//	$(`#vaccineName${i}`).show()
				$(`#reason${i}`).show()
				$(`#submitDetails${i}`).hide()
			}
			else{
				$(`input[name="stage2r${i}"][value="Y"]`).prop('checked',"checked")
				$(`input[name="stage3r${i}"][value="Y"]`).prop('checked',"checked")
				$(`#cowinStageID${i}`).removeClass("enableReponse").addClass("disableResponse");
				$(`#vaccineStage${i}`).removeClass("enableReponse").addClass("disableResponse");
			/*	if(acss.includes('P')) {
					$(`#exitStage${i}`).removeClass("disableResponse").addClass("enableReponse")
					$("#submitDetails").show()
				}
				else {
					$(`#exitStage${i}`).removeClass("enableReponse").addClass("disableResponse")
					$("#submitDetails").hide()}*/
				$(`#vaccineName${i}`).show()
				$(`#reason${i}`).hide()
					$(`#submitDetails${i}`).hide()
			}
			
		}
		/*else if(e[i].split("^")[0]=="3"){
			
			
			if(e[i].split("^")[1]=="N"){
				$(`input[name="stage2r${i}"][value="Y"]`).prop('checked',"checked")
				$(`input[name="stage3r${i}"][value="Y"]`).prop('checked',"checked")
				$(`input[name="stage4r${i}"][value="N"]`).prop('checked',"checked")
				$(`#cowinStageID${i}`).removeClass("enableReponse").addClass("disableResponse");
				$(`#vaccineStage${i}`).removeClass("enableReponse").addClass("disableResponse");
				$(`#exitStage${i}`).removeClass("enableReponse").addClass("disableResponse");
				$("#submitDetails").hide()
			}
			else{
				$(`input[name="stage2r${i}"][value="Y"]`).prop('checked',"checked")
				$(`input[name="stage3r${i}"][value="Y"]`).prop('checked',"checked")
				$(`input[name="stage4r${i}"][value="Y"]`).prop('checked',"checked")
				$(`#cowinStageID${i}`).removeClass("enableReponse").addClass("disableResponse");
				$(`#vaccineStage${i}`).removeClass("enableReponse").addClass("disableResponse");
				$(`#exitStage${i}`).removeClass("enableReponse").addClass("disableResponse");
				$("#submitDetails").hide()
			}
		
		}*/
		else if(e[i].split("^")[0]==""){
			if(acss.includes('C')) {
				$(`#cowinStageID${i}`).removeClass("disableResponse").addClass("enableReponse");
				$(`#submitDetails${i}`).show()
			}
			else  {
				$(`#cowinStageID${i}`).removeClass("enableReponse").addClass("disableResponse");
				$(`#submitDetails${i}`).hide()
			}
			 $(`#vaccineStage${i}`).removeClass("enableReponse").addClass("disableResponse");
			$(`#exitStage${i}`).removeClass("enableReponse").addClass("disableResponse");
			
		}
		
		if(pop[i].split("#")[0]=="N"){
			$(`#SubreasonID${i}`).val(pop[i].split("#")[1])
			$(`#vaccineName${i}`).hide()
			$(`#reason${i}`).show()
			
		}
		else //if(pop[i].split("#")[0]=="N")
			{
			$(`#SubVnameID${i}`).val(pop[i].split("#")[1])
			$(`#vaccineName${i}`).show()
			$(`#reason${i}`).hide()
		}
		
		if(copop[i].split("#")[0]=="N"){
			$(`#coReason${i}`).val(copop[i].split("#")[1])
			$(`#SubcovinID${i}`).hide()
			$(`#SubcovinID${i}`).prev().hide()
			$(`#cowinStageN${i}`).show()
			
		}
		else //if(pop[i].split("#")[0]=="N")
		{
			$(`#SubcovinID${i}`).val(copop[i].split("#")[1])
			$(`#cowinStageN${i}`).hide()
			$(`#SubcovinID${i}`).show()
			$(`#SubcovinID${i}`).prev().show()
		}
	}
	
	
}


function downloadReport(){
	var spoc =$("#empid").val()+","+$("#country").val()+","+$("#state").val()+","+$("#location").val()+","+$("#hospital").val()
	$("#formInp").val(spoc)
	$("#downloadreport").submit()
}



function getFormFields(){
	$.ajax(
			{
				url: "/VaccinationTracker/getCategoryDrop", 
				type : "POST",
				data : {
				 empid:"",
				},
				success: function(data)
				{
					console.log(data)
					
					if(data!=""){
						var html='';
						for(var i=0;i<data[0].length;i++){
							html+='<option value="'+data[0][i][1]+'">'+data[0][i][0]+'</option>'
						}
						$("#srchFlag").html(html)
						create_custom_dropdowns();
					}
				},
				error: function(err)
				{
					snackBar("sorry error occured");
				}
			})	
}


function create_custom_dropdowns() {
    $('#srchFlag').each(function (i, select) {
        if (!$(this).next().hasClass('dropdown-select')) {
            $(this).after('<div class="dropdown-select wide ' + ($(this).attr('class') || '') + '" tabindex="0"><span class="current"></span><div class="list"><ul></ul></div></div>');
            var dropdown = $(this).next();
            var options = $(select).find('option');
            var selected = $(this).find('option:selected');
            dropdown.find('.current').html(selected.data('display-text') || selected.text());
            options.each(function (j, o) {
                var display = $(o).data('display-text') || '';
                dropdown.find('ul').append('<li class="option ' + ($(o).is(':selected') ? 'selected' : '') + '" data-value="' + $(o).val() + '" data-display-text="' + display + '">' + $(o).text() + '</li>');
            });
        }
    });

    $('.dropdown-select ul').before('<div class="dd-search"><input id="txtSearchValue" autocomplete="off" onkeyup="filter()" class="dd-searchbox" type="text"></div>');
}

// Event listeners

// Open/close
$(document).on('click', '.dropdown-select', function (event) {
    if($(event.target).hasClass('dd-searchbox')){
        return;
    }
    $('.dropdown-select').not($(this)).removeClass('open');
    $(this).toggleClass('open');
    if ($(this).hasClass('open')) {
        $(this).find('.option').attr('tabindex', 0);
        $(this).find('.selected').focus();
    } else {
        $(this).find('.option').removeAttr('tabindex');
        $(this).focus();
    }
});

// Close when clicking outside
$(document).on('click', function (event) {
    if ($(event.target).closest('.dropdown-select').length === 0) {
        $('.dropdown-select').removeClass('open');
        $('.dropdown-select .option').removeAttr('tabindex');
    }
    event.stopPropagation();
});

function filter(){
    var valThis = $('#txtSearchValue').val();
    $('.dropdown-select ul > li').each(function(){
     var text = $(this).text();
        (text.toLowerCase().indexOf(valThis.toLowerCase()) > -1) ? $(this).show() : $(this).hide();         
   });
};
// Search

// Option click
$(document).on('click', '.dropdown-select .option', function (event) {
    $(this).closest('.list').find('.selected').removeClass('selected');
    $(this).addClass('selected');
    var text = $(this).data('display-text') || $(this).text();
    $(this).closest('.dropdown-select').find('.current').text(text);
    $(this).closest('.dropdown-select').prev('select').val($(this).data('value')).trigger('change');
});

// Keyboard events
$(document).on('keydown', '.dropdown-select', function (event) {
    var focused_option = $($(this).find('.list .option:focus')[0] || $(this).find('.list .option.selected')[0]);
    // Space or Enter
    //if (event.keyCode == 32 || event.keyCode == 13) {
    if (event.keyCode == 13) {
        if ($(this).hasClass('open')) {
            focused_option.trigger('click');
        } else {
            $(this).trigger('click');
        }
        return false;
        // Down
    } else if (event.keyCode == 40) {
        if (!$(this).hasClass('open')) {
            $(this).trigger('click');
        } else {
            focused_option.next().focus();
        }
        return false;
        // Up
    } else if (event.keyCode == 38) {
        if (!$(this).hasClass('open')) {
            $(this).trigger('click');
        } else {
            var focused_option = $($(this).find('.list .option:focus')[0] || $(this).find('.list .option.selected')[0]);
            focused_option.prev().focus();
        }
        return false;
        // Esc
    } else if (event.keyCode == 27) {
        if ($(this).hasClass('open')) {
            $(this).trigger('click');
        }
        return false;
    }
});

function getReportsTab(){

	/*trendDashboard()*/
	$('#getReportScrID').addClass('generateTokenSearchSelected')
	$('#submissionSearchID').removeClass('generateTokenSearchSelected')
	$('#geratetknSearchID').removeClass('generateTokenSearchSelected')
	$('#getVendorScrID').removeClass('generateTokenSearchSelected')
	$('#getDashboardScrID').removeClass('generateTokenSearchSelected')
	
	$("#getDashboardScrID").css('background','white')
	   $("#getDashboardScrID").css('color','#17A2B8')
		$("#getReportScrID").css('background','#17A2B8')
	   $("#getReportScrID").css('color','snow')
	   $("#geratetknSearchID").css('background','white')
	   $("#geratetknSearchID").css('color','#17A2B8')
	   $("#submissionSearchID").css('background','white')
	   $("#submissionSearchID").css('color','#17A2B8')
	   $("#getVendorScrID").css('background','#white')
	   $("#getVendorScrID").css('color','17A2B8')
	
	   
		   
	   
	$(".reportsDIv").html($(".reportsDIv").html())
	$(".reportsDIv").show()
	$(".userCardContainer").hide()
	$("#generateToken").hide()
	$("#processSubmitID").hide()
	$(".userdetailsBox").hide()	
	$("#addDepentID").hide()
	$(".turnoutDashboardDiv").hide()
	//trendDashboard()
	
}

function getDashboardTab(){

	$('#getDashboardScrID').addClass('generateTokenSearchSelected')
	$('#getReportScrID').removeClass('generateTokenSearchSelected')
	$('#submissionSearchID').removeClass('generateTokenSearchSelected')
	$('#geratetknSearchID').removeClass('generateTokenSearchSelected')
	$('#getVendorScrID').removeClass('generateTokenSearchSelected')
	
		$("#getDashboardScrID").css('background','#17A2B8')
	   $("#getDashboardScrID").css('color','snow')
		$("#getReportScrID").css('background','white')
	   $("#getReportScrID").css('color','#17A2B8')
	   $("#geratetknSearchID").css('background','white')
	   $("#geratetknSearchID").css('color','#17A2B8')
	   $("#submissionSearchID").css('background','white')
	   $("#submissionSearchID").css('color','#17A2B8')
	   $("#getVendorScrID").css('background','#white')
	   $("#getVendorScrID").css('color','17A2B8')
	
	   
		   
	$(".turnoutDashboardDiv").html($(".turnoutDashboardDiv").html())
	$(".reportsDIv").hide()   
	$(".turnoutDashboardDiv").show()
	$(".userCardContainer").hide()
	$("#generateToken").hide()
	$("#processSubmitID").hide()
	$(".userdetailsBox").hide()	
	$("#addDepentID").hide()
	
	$(".turnout").show();
var now = new Date();

var day = ("0" + now.getDate()).slice(-2);
var month = ("0" + (now.getMonth() + 1)).slice(-2);

var today = now.getFullYear()+"-"+(month)+"-"+(day) ;

	
	$('#turnoutDate').val(today);
$("#turnoutDate1").val(today);
$('#trendDate').val(today);
$("#trendDate1").val(today);

getgridData();
//showlocationtrend('Chennai');	
	//trendchart('Chennai');
}



//1817628
function CVCDownload()
{
	if($("#trackerDate").val() == "")
	{
		snackBar("Please select the fromdate before you download CVC_Report");
	return;
	}

if($("#trackerDate1").val() == "")
{
	snackBar("Please select the todate before you download CVC_Report");
return;
}
	var fileName="CVC_Report";
	var formData = "";
	
	formData += "<input type='hidden' name='datedata' value='"+$("#trackerDate").val()+"'/>";
	formData += "<input type='hidden' name='todate' value='"+$("#trackerDate1").val()+"'/>";
	formData += "<input type='hidden' name='fileID' value='"+fileName+"'/>";
	$("#CvcReport").html(formData);
	$("#CvcReport").submit();
	
}
function reconDownload(){
	if($("#trackerDate").val() == "")
	{
		snackBar("Please select the fromdate before you download Recon_Report");
	return;
	}

if($("#trackerDate1").val() == "")
{
	snackBar("Please select the todate before you download Recon_Report");
return;
}
	var fileName="Recon_Report";
	var formData = "";
	var spoc =$("#empid").val()+","+$("#country").val()+","+$("#state").val()+","+$("#location").val()+","+$("#hospital").val()
	formData += "<input type='hidden' name='datedata' value='"+$("#trackerDate").val()+"'/>";
	formData += "<input type='hidden' name='todate' value='"+$("#trackerDate1").val()+"'/>";
	formData += "<input type='hidden' name='fileID' value='"+fileName+"'/>";
	formData += "<input type='hidden' name='spocID' value='"+spoc+"'/>";
	$("#reconReport").html(formData);
	$("#reconReport").submit();
}

function downloadturnout(){
	if($("#turnoutDate").val() == "")
	{
		snackBar("Please select the fromdate before you download Turnout Report");
	return;
	}

if($("#turnoutDate1").val() == "")
{
	snackBar("Please select the todate before you download Turnout Report");
return;
}
	var fileName="Turnout_Report";
	var formData = "";
	var spoc =$("#empid").val()+","+$("#country").val()+","+$("#state").val()+","+$("#location").val()+","+$("#hospital").val()
	formData += "<input type='hidden' name='datedata' value='"+$("#turnoutDate").val()+"'/>";
	formData += "<input type='hidden' name='todate' value='"+$("#turnoutDate1").val()+"'/>";
	formData += "<input type='hidden' name='fileID' value='"+fileName+"'/>";
	formData += "<input type='hidden' name='spocID' value='"+spoc+"'/>";
	$("#turnoutReport").html(formData);
	$("#turnoutReport").submit();

}
function downloadtrend(){
	if($("#trendDate").val() == "")
	{
		snackBar("Please select the fromdate before you download");
	return;
	}

if($("#trendDate1").val() == "")
{
	snackBar("Please select the todate before you download");
return;
}
	var fileName="Trend_Report";
	var formData = "";
	var spoc =$("#empid").val()+","+$("#country").val()+","+$("#state").val()+","+$("#location").val()+","+$("#hospital").val()
	formData += "<input type='hidden' name='datedata' value='"+$("#trendDate").val()+"'/>";
	formData += "<input type='hidden' name='todate' value='"+$("#trendDate1").val()+"'/>";
	formData += "<input type='hidden' name='fileID' value='"+fileName+"'/>";
	formData += "<input type='hidden' name='spocID' value='"+spoc+"'/>";
	formData += "<input type='hidden' name='tcity' value='"+trendcity+"'/>";
	$("#trendReport").html(formData);
	$("#trendReport").submit();
}
function getgridData(){

	if($("#turnoutDate").val() == "")
	{
		snackBar("Please select the fromdate before you download Turnout Report");
			
	return;
	}else{
		var fromdate = $("#turnoutDate").val();
	}

if($("#turnoutDate1").val() == "")
{
	snackBar("Please select the todate before you download Turnout Report");
	
return;
}else{
		var todate = $("#turnoutDate1").val();
	}
$.ajax({
		url: "/VaccinationTracker/getGridData", 
		type : "POST",
		async : false,
		data : {
			empId:"",
			fromDate:fromdate,
			toDate:todate
		},
		beforeSend: function() {
					 getPageLoad()
		},
		success: function(data)
		{
			
			console.log("Grid data : ", data)
			var header=data[0];
			var header1=data[1];
			var  tabledata=data[2];
			var html=` <tr><th rowspan="2">${header[0][0]}</th><th colspan="3">${header[1][0]}</th><th rowspan="2">${header[4][0]}</th><th rowspan="2">${header[5][0]}</th><th rowspan="2">${header[6][0]}</th></tr><tr><th>${header1[1][0]}</th><th>${header1[2][0]}</th><th>${header1[3][0]}</th> </tr>`
			$('#tablehead').html(html)
			//$("#turnouttable").append(html)
			$("#tablebody").html("");
			var html1="";
			tabledata.map((item,x)=>{
				 html1=`<tr id=${x} >`
				
				item.map((values,i)=>{
					
					html1+=`<td id=${i} >${values?values:"-"}</td>`
					
				})
			
				//$("#turnouttable").append(html1)
				$("#tablebody").append(html1)
			})
			//$(".tablebody").append(html1)
			removePageLoad();
			},
			error: function(err){
				snackBar("sorry error occured");	
				removePageLoad();	
			}
			})
}

function ScheduleUpload(){
		getPageLoad();
	$("#errorbutton").hide();
	 var input = document.getElementById('filedata');
	if(!input.files[0])
	{
		alert("Please choose file before upload")
		removePageLoad();	
		return;
	}else{
	var spoc =$("#empid").val();
	
	var formData = new FormData();
		formData.append('filedata', input.files[0]);
		//formData.append('fileType', fileExtType);
		formData.append('spocID',spoc);
		//$('#schedulefileupload').submit();	
	}	
	//$('#schedulefileupload').ajaxForm(
		$.ajax(	{
				url: "/VaccinationTracker/uploadschedule", 
				type : "POST",
				data : formData,
			    processData: false,  // tell jQuery not to process the data
		        contentType: false,  // tell jQuery not to set contentType*/
				success: function(data)
				{
					$("#filedata").val("")
					console.log(data)
					if(data=="SUCCESS"){
						alert("Upload Successfull")
						closeuploadmodal()
					}
					else if(data=="H"){
						alert("Header Mismatch in Uploaded File. Please Upload correct file")					
						closeuploadmodal()
					}else if(data.flag1=="BE"){						
						alert("Blank Sheet Uploded!Please Upload the file with data.");
						closeuploadmodal()
					}
					
					else if(data=="N"){
						$("#errorbutton").show();
						alert("Error occured! Please Download Error Report")
					}
					removePageLoad();	
				},
				
				error: function(err)
				{
					snackBar("sorry error occured");
					$("#filedata").val("")
					removePageLoad();	
				}
			})	
	
}
function downloadErrorreport(){
	var spoc =$("#empid").val();
	var formData = "";
	formData += "<input type='hidden' name='spocID' value='"+spoc+"'/>";
	$("#downloadErrorReport").html(formData);
	$("#downloadErrorReport").submit();
}
function openuploadmodal(){
	
	console.log("open")
	$("#uploadmodal").css("display","block");
	$("#errorbutton").hide();
}
function closeuploadmodal(){
	console.log("closed")
	$("#uploadmodal").css("display","none");
}

function showGridTrend(t){
	console.log(t);
	if(t=="grid"){
		$(".turnout").show();
		$(".trend").hide();
		$("#grid").addClass("btn-select");
		$("#grid").removeClass("btn-remove");
		$("#trend").removeClass("btn-select");
		$("#trend").addClass("btn-remove");
		
	}else{
	$(".turnout").hide();
		$(".trend").show();	
		$("#trend").addClass("btn-select");
		$("#trend").removeClass("btn-remove");
		$("#grid").removeClass("btn-select");
		$("#grid").addClass("btn-remove");
		showlocationtrend('Chennai');
	}
}
function showlocationtrend(t){
	console.log(t);
	if(t=="Chennai"){
		//$(".chennai").show();
		//$(".non").hide();
		$("#chennai").addClass("btn-select1");
		$("#chennai").removeClass("btn-remove1");
		$("#non").removeClass("btn-select1");
		$("#non").addClass("btn-remove1");
		getTrendData("Chennai");
	}else{
	//$(".").hide();
		//$(".trend").show();	
		$("#non").addClass("btn-select1");
		$("#non").removeClass("btn-remove1");
		$("#chennai").removeClass("btn-select1");
		$("#chennai").addClass("btn-remove1");
		getTrendData("Other_location");
	}
}
//trend dashboard
function getTrendData(t){
	trendcity=t;
	console.log(t);
	//getPageLoad();
	if($("#trendDate").val() == "")
	{
		snackBar("Please select the from date");
			
	return;
	}else{
		var fromdate = $("#trendDate").val();
	}

if($("#trendDate1").val() == "")
{
	snackBar("Please select the to date");
	
return;
}else{
		var todate = $("#trendDate1").val();
	}
	//getPageLoad();
$.ajax({
	
		url: "/VaccinationTracker/getTrendData", 
		type : "POST",
		data : {
			empId:"",
			fromDate:fromdate,
			toDate:todate,
			city:t,
		},
		beforeSend: function() {
					 getPageLoad()
			    },
		success: function(data)
		{
			
			console.log("Trend data : ", data)
			var value=data[0];
			console.log(value);
			var a=[];
			value.map(x=>{
				
				a.push({
					"date": x[0],
  "planned": x[1],
  "actuals": x[2],
  "turnout": x[3]
			
				})
			})
			console.log("aaa",a);
			/*for(var i=0;i<data[3].length;i++){
				a.push({"date": "2013-01-16",
  "planned": data[3][i][0],
  "actuals": data[3][i][0],
  "turnout": data[3][i][0]})

			}*/
			 trendchart(a,t);
			removePageLoad();
			},
			error: function(err){
				snackBar("sorry error occured");	
				removePageLoad();	
			}
			})

}
function trendchart(a,t){
if(a.length==0){
	snackBar("No Data Available")
}
else{
	am4core.ready(function() {

// Themes begin
am4core.useTheme(am4themes_animated);
// Themes end

// Create chart instance
var chart = am4core.create("chartdiv", am4charts.XYChart);

// Add data
chart.data=a
/*chart.data = [{
  "date": "2013-07-16",
  "planned": 600,
  "actuals": 155,
  "turnout": 26,
  "city":"Chennai"
 
}, {
  "date": "2013-07-17",
  "planned": 500,
  "actuals": 177,
  "turnout": 35,
  "city":"Others"
  
}, {
  "date": "2013-07-18",
  "planned": 322,
  "actuals": 51,
  "turnout": 16
}, {
  "date": "2013-07-19",
  "planned": 306,
  "actuals": 114,
  "turnout": 37,
  "city":"Chennai"
 
}, {
  "date": "2013-07-20",
  "planned": 1184,
  "actuals": 218,
  "turnout": 18,
  "city":"Others"
 
}, {
  "date": "2013-07-21",
  "planned": 227,
  "actuals": 158,
  "turnout": 70,
  "city":"Chennai"
}, {
  "date": "2013-07-22",
  "planned": 173,
  "actuals": 25,
  "turnout": 14,
  "city":"Others"
}];*/

// Create axes
var dateAxis = chart.xAxes.push(new am4charts.DateAxis());
dateAxis.renderer.grid.template.location = 0;
dateAxis.renderer.minGridDistance = 30;
dateAxis.skipEmptyPeriods=true;

var valueAxis1 = chart.yAxes.push(new am4charts.ValueAxis());
valueAxis1.title.text = "Turnout Ratio";

var valueAxis2 = chart.yAxes.push(new am4charts.ValueAxis());
valueAxis2.title.text = "Planned & Actuals";
valueAxis2.renderer.opposite = true;
valueAxis2.renderer.grid.template.disabled = true;

/*chart.colors.list = [
  am4core.color("red"),
  am4core.color("blue"),
  
];*/
// Create series
var series1 = chart.series.push(new am4charts.ColumnSeries());
series1.dataFields.valueY = "turnout";
series1.dataFields.dateX = "date";
series1.yAxis = valueAxis1;
series1.name = "Turnout Ratio";
series1.tooltipText = "{name}\n[bold font-size: 20]{valueY}%[/]";
//series1.fill = chart.colors.getIndex(0);
series1.fill = am4core.color("#ffb84d");
series1.strokeWidth = 0;
//series1.clustered = true;
series1.columns.template.width = am4core.percent(40);


/*var series2 = chart.series.push(new am4charts.ColumnSeries());
series2.dataFields.valueY = "sales2";
series2.dataFields.dateX = "date";
series2.yAxis = valueAxis1;
series2.name = "Actual Sales";
series2.tooltipText = "{name}\n[bold font-size: 20]${valueY}M[/]";
series2.fill = chart.colors.getIndex(0).lighten(0.5);
series2.strokeWidth = 0;
series2.clustered = false;
series2.toBack();*/

var series3 = chart.series.push(new am4charts.LineSeries());
series3.dataFields.valueY = "planned";
series3.dataFields.dateX = "date";
series3.name = "Planned";
series3.strokeWidth = 2;
series3.tensionX = 0.7;
series3.yAxis = valueAxis2;
series3.tooltipText = "{name}\n[bold font-size: 20]{valueY}[/]";

var bullet3 = series3.bullets.push(new am4charts.CircleBullet());
bullet3.circle.radius = 3;
bullet3.circle.strokeWidth = 2;
bullet3.circle.fill = am4core.color("#fff");

var series4 = chart.series.push(new am4charts.LineSeries());
series4.dataFields.valueY = "actuals";
series4.dataFields.dateX = "date";
series4.name = "Actuals";
series4.strokeWidth = 2;
series4.tensionX = 0.7;
series4.yAxis = valueAxis2;
series4.tooltipText = "{name}\n[bold font-size: 20]{valueY}[/]";
//series4.stroke = chart.colors.getIndex(0).lighten(0.5);
series4.stroke = am4core.color("red").lighten(0.3);
series4.strokeDasharray = "3,3";


var bullet4 = series4.bullets.push(new am4charts.CircleBullet());
bullet4.circle.radius = 3;
bullet4.circle.strokeWidth = 2;
bullet4.circle.fill = am4core.color("#fff");

// Add chart title
var title = chart.titles.create();
title.text = t;
title.fontSize = 18;
title.marginBottom = 20;

// Add cursor
chart.cursor = new am4charts.XYCursor();

// Add legend
chart.legend = new am4charts.Legend();
//chart.legend.position = "top";

// Add scrollbar
/*chart.scrollbarX = new am4charts.XYChartScrollbar();
chart.scrollbarX.series.push(series1);
chart.scrollbarX.series.push(series3);
chart.scrollbarX.parent = chart.bottomAxesContainer;*/


// Enable export
chart.exporting.menu = new am4core.ExportMenu();
chart.exporting.menu.items = [
  {
    "label": "...",
    "menu": [
      { "type": "png", "label": "PNG" },
      { "type": "jpg", "label": "JPG" }
      
    ]
  }
];
chart.exporting.filePrefix = "Trend_Report";
//chart.exporting.getFormatOptions("xslx").pivot = true;
/*chart.exporting.filePrefix = "Trend_Report";
chart.exporting.formatOptions.getKey("pdf").disabled = true;
chart.exporting.formatOptions.getKey("svg").disabled = true;
chart.exporting.formatOptions.getKey("csv").disabled = true;
chart.exporting.formatOptions.getKey("pdfdata").disabled = true;
chart.exporting.formatOptions.getKey("html").disabled = true;
chart.exporting.formatOptions.getKey("json").disabled = true;
chart.exporting.formatOptions.getKey("print").disabled = true;*/
}); // end am4core.ready()
}
}