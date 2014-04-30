<html>
<head>
<title>Scout Automated Adviser</title>
<link rel="stylesheet" type="text/css"
	href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/css/jquery.dataTables.css">
</head>
<style type="text/css">
</style>
<body>
	<div id="demo" style="height: 70%;">
		<table cellpadding="0" cellspacing="0" border="0" class="display"
			id="example" width="100%" height="70%">
			<thead>
				<tr>
					<th>name</th>
					<th>location</th>
					<th>pos(s)</th>
					<th>height</th>
					<th>fortyDash</th>
					<th>weight</th>
					<th>stars</th>
					<th>rating</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="5" class="dataTables_empty">Loading data from
						server</td>
				</tr>
			</tbody>
			<tfoot>
				<tr>
					<th>name</th>
					<th>location</th>
					<th>pos(s)</th>
					<th>height</th>
					<th>fortyDash</th>
					<th>weight</th>
					<th>stars</th>
					<th>rating</th>
				</tr>
			</tfoot>
		</table>
	</div>

	<script type="text/javascript" charset="utf8"
		src="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/jquery.dataTables.js"></script>

	<script type="text/javascript" charset="utf8"
		src="https://datatables.net/release-datatables/extras/ColReorder/media/js/ColReorder.js"></script>

	<script type="text/javascript" charset="utf8"
		src="https://editor.datatables.net/release/DataTables/extras/TableTools/media/js/TableTools.js"></script>

	<script type="text/javascript" charset="utf8"
		src="https://datatables.net/release-datatables/extras/TableTools/media/js/ZeroClipboard.js"></script>
	<script>
		var table = $('#example').dataTable({	
			"bDeferRender": true,
			"bProcessing" : true,
			"bServerSide" : true,
			"sAjaxSource" : "/spring-jpa/players/playersjson"
		});
	</script>
</body>
</html>