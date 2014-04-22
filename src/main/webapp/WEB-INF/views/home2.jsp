<html>
<head>
<link rel="stylesheet" type="text/css"
	href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/css/jquery.dataTables.css">
</head>
<style type="text/css">
</style>
<body>
	<h3>Username : ${username}</h3>
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
		src="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/jquery.dataTables.js"></script>
		
	<script type="text/javascript" charset="utf8"
		src="https://datatables.net/release-datatables/extras/ColReorder/media/js/ColReorder.js"></script>

	<script type="text/javascript" charset="utf8"
		src="https://editor.datatables.net/release/DataTables/extras/TableTools/media/js/TableTools.js"></script>

	<script type="text/javascript" charset="utf8"
		src="https://datatables.net/release-datatables/extras/TableTools/media/js/ZeroClipboard.js"></script>
	<script>
	
			$('#example').dataTable({
				"bProcessing" : true,
				"bServerSide" : true,
				"sAjaxSource" : "/spring-jpa/getjsonexample", 
				"sDom": 'T<"clear">lfrtip',
				"oTableTools": {
					"sSwfPath": "media/swf/copy_csv_xls_pdf.swf"
				},
				 "oTableTools": {
			            "aButtons": [
			                "copy",
			                "print",
			                {
			                    "sExtends":    "collection",
			                    "sButtonText": "Save",
			                    "aButtons":    [ "csv", "xls", "pdf" ]
			                }
			            ]
			        },
				//"sDom": '<"top"iflp<"clear">>rt<"bottom"iflp<"clear">>', //paging in header and footer
				 "aLengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]], //row number drop down
				 //"bStateSave": true, // save table state
				 //"sDom": 'Rlfrtip', //reorder cols
			});
			
		function fnShowHide( iCol )
		{
			iCol = 0;
		    /* Get the DataTables object again - this is not a recreation, just a get of the object */
		    var oTable = $('#example').dataTable();
		    var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
		    oTable.fnSetColumnVis( iCol, bVis ? false : true );
		}
			
	</script>
</body>
</html>