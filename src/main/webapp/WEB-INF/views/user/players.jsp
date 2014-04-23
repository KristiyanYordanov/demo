<html>
<head>
<link rel="stylesheet" type="text/css"
	href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/css/jquery.dataTables.css">
</head>
<style type="text/css">
.dataTables_info {
	background:linear-gradient(to bottom, #A8DDE8 0%, #96CBD7 100%);
	border: thin;
	height: 25px;
	padding-left: 10px;
	padding-right: 10px;
	-webkit-border-top-left-radius: 5px;
-webkit-border-bottom-left-radius: 5px;
-moz-border-radius-topleft: 5px;
-moz-border-radius-bottomleft: 5px;
border-top-left-radius: 5px;
border-bottom-left-radius: 5px;
}

.dataTables_length {
	background:linear-gradient(to bottom, #A8DDE8 0%, #96CBD7 100%);
	border: thin;
	height: 25px;
	padding-left: 10px;
	padding-right: 10px;
-webkit-border-top-right-radius: 5px;
-webkit-border-bottom-right-radius: 5px;
-moz-border-radius-topright: 5px;
-moz-border-radius-bottomright: 5px;
border-top-right-radius: 5px;
border-bottom-right-radius: 5px;
}

.DTTT_button DTTT_button_copy {
	background-color: yellow;
	border: solid;
}
</style>
<body>
	<h3>Username : ${username}</h3>
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
			"bProcessing" : true,
			"bServerSide" : true,
			"sAjaxSource" : "/spring-jpa/players/playersjson",
			"aaSorting" : [ [ 0, "asc" ] ],
			"sDom" : 'r<"top"ilpfT>t<"bottom"ilpT>r<"clear">',
			 "sPaginationType": "full_numbers",
			"oTableTools" : {
				"sSwfPath" : "media/swf/copy_csv_xls_pdf.swf"
			},
			"oTableTools" : {
				"aButtons" : [ "copy", "print", {
					"sExtends" : "collection",
					"sButtonText" : "Save",
					"aButtons" : [ "csv", "xls", "pdf" ]
				} ]
			},
			//"sDom": '<"top"iflp<"clear">>rt<"bottom"iflp<"clear">>', //paging in header and footer
			"aLengthMenu" : [ [ 10, 25, 50, -1 ], [ 10, 25, 50, "All" ] ], //row number drop down
		//"bStateSave": true, // save table state
		//"sDom": 'Rlfrtip', //reorder cols
		//"sScrollY" : "300px",
		//"sScrollX" : "150%",
		//"sScrollXInner" : "150%",
		//	"bScrollCollapse" : true,
		});
		//show hide colums by column number
		function fnShowHide(iCol) {
			iCol = 0;
			/* Get the DataTables object again - this is not a recreation, just a get of the object */
			var oTable = $('#example').dataTable();
			var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
			oTable.fnSetColumnVis(iCol, bVis ? false : true);
		}
	</script>
</body>
</html>