<html>
<head>
<title>Scout Automated Adviser</title>
<link rel="stylesheet" type="text/css"
	href="resources/css/jquery.dataTables.css.css" />

<link rel="stylesheet" type="text/css"
	href="resources/css/dataTables.editor.min.css" />

<link rel="stylesheet" type="text/css"
	href="resources/css/font-awesome.css" />

<style type="text/css">
.round-border {
	border: 1px solid #eee;
	border: 1px solid rgba(0, 0, 0, 0.05);
	-webkit-border-radius: 4px;
	-moz-border-radius: 4px;
	border-radius: 4px;
	padding: 10px;
	margin-bottom: 5px;
}

#example {
	min-width: 1000px;
	max-width: 1200px;
}

.dataTables_info {
	background: linear-gradient(to bottom, #A8DDE8 0%, #96CBD7 100%);
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
	background: linear-gradient(to bottom, #A8DDE8 0%, #96CBD7 100%);
	border: thin;
	height: 37px;
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

.DTE_Body {
	max-height: 350px;
}

.DTE_Body_Content {
	max-height: 325px;
}

div.container {
	width: 80%;
}

//
editor
table.dataTable tr td:first-child {
	text-align: center;
}

table.dataTable tr td:first-child:before {
	content: "\f096"; /* fa-square-o */
	font-family: FontAwesome;
}

table.dataTable tr.selected td:first-child:before {
	content: "\f046"; /* fa-check-square-o */
}
</style>
<script type="text/javascript" charset="utf8"
	src="resources/js/jquery-1.10.2.min.js"></script>

<script type="text/javascript" charset="utf8"
	src="resources/js/jquery.dataTables.1.10.0.js"></script>
<script type="text/javascript" charset="utf8"
	src="resources/js/dataTables.editor.js"></script>

<script type="text/javascript" class="init">
	var editor; // use a global for the submit and return data rendering in the examples
	$(document).ready(function() {
		editor = new $.fn.dataTable.Editor({
			ajax : {
				create : {
					type : 'POST',
					dataType : 'json',
					contentType : 'application/json',
					url : 'players/create'
				},
				edit : {
					type : 'PUT',
					dataType : 'json',
					contentType : 'application/json',
					url : 'players/edit'
				},
				remove : {
					type : 'DELETE',
					dataType : 'json',
					contentType : 'application/json',
					url : 'players/delete'
				}
			},
			table : "#example",
			//label are for create method
			fields : [ {
				label : "ets:",
				name : "ets:"
			}, {
				label : "schoolName:",
				name : "schoolName:"
			}, {
				label : "schoolAddress:",
				name : "nschoolAddressame:"
			}, {
				label : "schoolCity:",
				name : "schoolCity:"
			}, {
				label : "schoolState:",
				name : "schoolState:"
			}, {
				label : "schoolZip:",
				name : "schoolZip:"
			}, {
				label : "schoolAreaCode:",
				name : "schoolAreaCode:"
			}, {
				label : "schoolPhone:",
				name : "schoolPhone:"
			}, {
				label : "schoolCoachOffice:",
				name : "schoolCoachOffice:"
			}, {
				label : "schoolFax:",
				name : "schoolFax:"
			} ]
		});
		$('#example').DataTable({
			dom : '<"top"ilpT<"clear">>rt<"bottom"ilp<"clear">>',
			"aLengthMenu" : [ [ 10, 25, 50, -1 ], [ 10, 25, 50, "All" ] ],
			"scrollY" : 400,
			"scrollX" : true,
			ajax : {
				url : "schools/getschools",
				type : "GET"
			},
			serverSide : true,
			columns : [ {
				data : null,
				render : function(data, type, row) {
					//id data.DT_RowId
					// Combine the first and last names into a single table field
					return '';
				}
			}, {
				data : "ets"
			}, {
				data : "schoolName"
			}, {
				data : "schoolAddress"
			}, {
				data : "schoolCity"
			}, {
				data : "schoolState"
			}, {
				data : "schoolZip"
			}, {
				data : "schoolAreaCode"
			}, {
				data : "schoolPhone"
			}, {
				data : "schoolCoachOffice"
			}, {
				data : "schoolFax"
			} ],
			order : [ 1, 'asc' ],
			tableTools : {
				sRowSelect : "os",
				sRowSelector : 'td:first-child',
				aButtons : [ {
					sExtends : "editor_create",
					editor : editor
				}, {
					sExtends : "editor_edit",
					editor : editor
				}, {
					sExtends : "editor_remove",
					editor : editor
				} ]
			}
		});
	});
</script>
<body>
	<br>
	<table id="example" class="display" cellspacing="0">
		<thead>
			<tr>
			<th></th>
				<th>ets</th>
				<th>schoolName</th>
				<th>schoolAddress</th>
				<th>schoolCity</th>
				<th>schoolState</th>
				<th>schoolZip</th>
				<th>schoolAreaCode</th>
				<th>schoolPhone</th>
				<th>schoolCoachOffice</th>
				<th>schoolFax</th>
			</tr>
		</thead>
		<tbody>
				<tr>
					<td colspan="5" class="dataTables_empty">Loading data from
						server</td>
				</tr>
		</tbody>
	</table>
</body>
</html>