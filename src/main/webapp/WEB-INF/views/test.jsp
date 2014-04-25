<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/css/jquery.dataTables.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Spring MVC - Upload File</title>
</head>
<body>
	<!--  Form 2 -->
	<i>Uploading File With Ajax</i>
	<br />
	<form id="form2" method="post" action="/spring-jpa/cont/upload"
		enctype="multipart/form-data">
		<!-- File input -->
		<input name="file2" id="file2" type="file" /><br />
	</form>

	<button value="Submit" onclick="uploadFormData()">Upload</button>
	<i>Using FormData Object</i>

	<div id="demo"></div>
</body>
<script type="text/javascript" charset="utf8"
	src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-2.1.0.min.js"></script>
<script type="text/javascript" charset="utf8"
	src="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/jquery.dataTables.js"></script>
<script type="text/javascript">
	function uploadFormData() {
		var oMyForm = new FormData();
		oMyForm.append("file", file2.files[0]);
		var aryColTableChecked = new Array();
		$.ajax({
			url : '/spring-jpa/players/upload',
			data : oMyForm,
			dataType : 'text',
			processData : false,
			contentType : false,
			type : 'POST',
			success : function(data) {
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
			},
			error : function(data, status, er) {
				alert("error: " + data + " status: "+ status + " er:" + er);
			}
		});
	}
</script>
</html>