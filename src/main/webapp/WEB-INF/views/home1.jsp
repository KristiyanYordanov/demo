<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/css/jquery.dataTables.css">
</head>
<style type="text/css">
#container {
	padding-top: 60px !important;
	width: 960px !important;
}

#dt_example .big {
	font-size: 1.3em;
	line-height: 1.45em;
	color: #111;
	margin-left: -10px;
	margin-right: -10px;
	font-weight: normal;
}

#dt_example {
	font: 95%/1.45em "Lucida Grande", Verdana, Arial, Helvetica, sans-serif;
	color: #111;
}

div.dataTables_wrapper,table {
	font: 13px/1.45em "Lucida Grande", Verdana, Arial, Helvetica, sans-serif;
}

#dt_example h1 {
	font-size: 16px !important;
	color: #111;
}

#footer {
	line-height: 1.45em;
}

div.examples {
	padding-top: 1em !important;
}

div.examples ul {
	padding-top: 1em !important;
	padding-left: 1em !important;
	color: #111;
}
</style>
<body>
	<h3>Message : ${message}</h3>
	<h3>Username : ${username}</h3>
	<br> ${player}
	<br>
	<!-- <br> ${players} -->

	<table cellpadding="0" cellspacing="0" border="0" class="display"
		id="example" width="100%">
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

	<script type="text/javascript" charset="utf8"
		src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-2.1.0.min.js"></script>
	<script type="text/javascript" charset="utf8"
		src="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/jquery.dataTables.js"></script>


	<script type="text/javascript" charset="utf8"
		src="https://editor.datatables.net/release/DataTables/extras/TableTools/media/js/TableTools.js"></script>
	<script>
		$(document).ready(function() {
			var editor; // use a global for the submit and return data rendering in the examples
			$('#example').dataTable({
				"bProcessing" : true,
				"bServerSide" : true,
				"sAjaxSource" : "/spring-jpa/getjsonexample",
				"sDom": '<"top"iflp<"clear">>rt<"bottom"iflp<"clear">>',
				 "aLengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
				"aoColumnDefs" : [ {
					// `data` refers to the data for the cell (defined by `mData`, which
					// defaults to the column being worked with, in this case is the first
					// Using `row[0]` is equivalent.
					"mRender" : function(data, type, row) {
						return data + ' ' + row[3];},
						"aTargets" : [ 0 ] }, 
						{	"bVisible" : false,
							"aTargets" : [ 3 ] }, 
						{
						"sClass" : "center",
						"aTargets" : [ 4 ]
				} ]
			});
		});
	</script>
</body>
</html>