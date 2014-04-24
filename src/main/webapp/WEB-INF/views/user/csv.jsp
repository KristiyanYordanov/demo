<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/css/jquery.dataTables.css">
<title>Upload File Request Page</title>
</head>
<body>

	<form method="POST" action="players/import"
		enctype="multipart/form-data">
		File to upload: <input type="file" name="file"><br /> Name: <input
			type="text" name="name"><br /> <br /> <input type="submit"
			value="Upload"> Press here to upload the file! <input
			type="hidden" name="load" />
	</form>

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
<script>
	$(document).ready(function() {
		var message = '<c:out value="${nom}" />';
		console.log(message);
		console.log(message === 'load');
		if (message === 'load') {

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
</script>
</html>