<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Scout Automated Adviser</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css"
	href="resources/css/jquery.dataTables.css.css" />
<style type="text/css">
.buttonStyle {
	background-color: #ccc;
	-moz-border-radius: 5px;
	-webkit-border-radius: 5px;
	border-radius: 6px;
	color: #fff;
	font-family: 'Oswald';
	font-size: 20px;
	text-decoration: none;
	cursor: poiner;
	border: none;
}

.buttonStyle:hover {
	border: none;
	background: #A8DDE8;
	box-shadow: 0px 0px 1px #777;
}

</style>
</head>
<body>
	<br />
	<form id="form2" method="post" action="/spring-jpa/cont/upload"
		enctype="multipart/form-data" class="buttonStyle">
		<!-- File input -->
		<input class="buttonStyle" name="file2" id="file2" type="file" /><br />
	</form>
	<button value="Submit"  class="buttonStyle" onclick="uploadFile()">Upload</button>
	
	<table width="100%">
		<tr id="table">
		</tr>
	</table>
	<label>Headers are just fine</label>
	<input id="checkbox" type="checkbox" value="true" name="Headers are just fine.">
	<button value="Submit"  class="buttonStyle" onclick="importFile()">Import</button>

	<div id="demo"></div>
</body>
<script type="text/javascript" charset="utf8"
	src="resources/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" charset="utf8"
	src="resources/js/jquery.dataTables.1.10.0.js"></script>
<script type="text/javascript">
var headerLength = 0;
function importFile() {
	var header = new Array();
	if (headerLength > 0) {
		for (var i = 1; i <= headerLength; i++ ) {
			 $('#header'+i).change(function(){
			     var option = $(this).val();
			     $('option:selected', 'select').removeAttr('selected');
			     $('#header'+i+' option[value=' + option +']').attr('selected', 'selected');
			});
			header.push($('#header'+i+' option:selected').val());
		}
	}
	
	var json = JSON.stringify(header);
	var x=$("#checkbox").is(":checked");
	if (x) {
		header = new Array();
		json = JSON.stringify(header);
	}
	$.ajax({ 
	    url: "/spring-jpa/players/import", 
	    type: 'POST', 
	    dataType: 'json', 
	    data: json, 
	    contentType: 'application/json',
	    mimeType: 'application/json',
	    success: function(data) { 
	    	alert(data.imported);
	    },
	    error:function(data,status,er) { 
	        alert("error: "+data+" status: "+status+" er:"+er);
	    }
	});
	
};
function uploadFile() {
		//remove drop down list before uploading new csv file		
		var myNode = document.getElementById("table");
		while (myNode.firstChild) {
		    myNode.removeChild(myNode.firstChild);
		}
		  
		var oMyForm = new FormData();
		oMyForm.append("file", file2.files[0]);
		console.log("file = " + file2.files[0]);
		var aryColTableChecked = new Array();
		$.ajax({
			url : '/spring-jpa/players/upload',
			data : oMyForm,
			dataType : 'text',
			processData : false,
			contentType : false,
			type : 'POST',
			success : function(data) {
				//console.log("data = " + data);
				$('#demo').html('<table cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table>');
				var header = data.split("],[", 1);
				var h = header[0].split(",");
				for (var i = 0; i < h.length; i++) {
					if ( i == 0) {
						h[i] = h[i].slice(1);
					} 
					aryColTableChecked.push(h[i]);
				}
				var aryJSONColTable = [];
				for (var i = 0; i < aryColTableChecked.length; i++) {
					aryJSONColTable.push({"sTitle" : aryColTableChecked[i],"aTargets" : [ i ]});
				}
				var a = "["+data+"]";
				var ss = a.split("],[");
				var aaData = [];
				for ( var i=0 ; i<ss.length ; i++ ) {
					var n = ss[i].split(",");
					var f = eval(n);
					aaData.push(f);
				}
				aaData = aaData.slice(1);
				oTable = $('#example').dataTable( 
				{
					"aaData":aaData,
					"bDeferRender": true,
					"aoColumnDefs" : aryJSONColTable
				});
				
				//add drop down list after table is loaded - choose new headers
				var ni = document.getElementById('table');
				headerLength = h.length;
				for (var i = 1; i <= h.length; i++) {
					 var newdiv = document.createElement('td');
					 var divIdName = "header"+i;
					 newdiv.setAttribute('id',divIdName);
					 newdiv.innerHTML = '<select id="header'+i+'"><option value="None">-- Select --</option>'+
					 '<option value="name">name</option>'+
					 '<option value="stars">stars</option>'+
					 '<option value="pos">pos</option>'+
					 '<option value="location">location</option>'+
					 '<option value="height">height</option>'+
					 '<option value="weight">weight</option>'+
					 '<option value="fortyDash">fortyDash</option>'+
					 '<option value="rating">rating</option>'+
					 '<option value="gradYear">gradYear</option>'+
					 '<option value="state">state</option>'+
					 '<option value="schoolName">schoolName</option>'+
					 '<option value="schoolCity">schoolCity</option>'+
					 '<option value="maxprepsUrl">maxprepsUrl</option>'+
					 '<option value="GP">GP</option>'+
					 '<option value="Avg">Avg</option>'+
					 '<option value="OBP">OBP</option>'+
					 '<option value="H">H</option>'+
					 '<option value="RBI">RBI</option>'+
					 '<option value="R">R</option>'+
					 '<option value="SB">SB</option>'+
					 '<option value="AB">AB</option>'+
					 '<option value="SLG">SLG</option>'+
					 '<option value="PA">PA</option>'+
					 '<option value="FP">FP</option>'+
					 '<option value="K">K</option>'+
					 '<option value="IP">IP</option>'+
					 '</select>';
					 ni.appendChild(newdiv);
				}
			},
			error : function(data, status, er) {
				alert("error: " + data + " |status: "+ status + "| er:" + er);
			}
		});
	}
</script>
</html>