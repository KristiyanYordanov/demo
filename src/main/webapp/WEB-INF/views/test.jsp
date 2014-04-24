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
	
	<div id="table">
		<table cellpadding="0" cellspacing="0" border="0" class="display"
			id="example" width="100%" height="70%">
			<thead>
			</thead>
			<tbody>
				<tr>
				</tr>
			</tbody>
			<tfoot>
			</tfoot>
		</table>
	</div>
</body>
<script type="text/javascript" charset="utf8"
	src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-2.1.0.min.js"></script>
<script type="text/javascript" charset="utf8"
	src="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/jquery.dataTables.js"></script>
<script type="text/javascript">
	//using FormData() object
	function uploadFormData() {
		$('#result').html('');

		var oMyForm = new FormData();
		oMyForm.append("file", file2.files[0]);

		$.ajax({
			url : 'http://localhost:8080/spring-jpa/cont/upload',
			data : oMyForm,
			dataType : 'text',
			processData : false,
			contentType : false,
			type : 'POST',
			success : function(data) {
				//create JSON array for aoColumnDefs
				var aryColTableChecked = [ "engine", "browser" ];
				var aryJSONColTable = [];
				for (var i = 0; i < aryColTableChecked.length; i++) {
					aryJSONColTable.push({
						"sTitle" : aryColTableChecked[i],
						"aTargets" : [ i ]
					});
				}

				oTable = $('#example').dataTable({
					"aoColumnDefs" : aryJSONColTable,
					"paging" : true,
					"bProcessing" : true,
					"bServerSide" : true,
					"bLengthChange" : true,
					"bFilter" : true,
					"aaSorting" : [ [ 1, "desc" ] ],
					"sScrollX" : "100%",
					"bScrollCollapse" : true,
					"bJQueryUI" : true,
					"sAjaxSource" : "/spring-jpa/resources/deep.txt"
				});
			}
		});
	}
</script>
</html>