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
	function filterGlobal() {
		$('#example').DataTable().search($('#global_filter').val(),
		$('#global_regex').prop('checked'),
		$('#global_smart').prop('checked')).draw();
	}

	function filterColumn(i) {
		$('#example').DataTable().column(i).search(
		$('#col' + i + '_filter').val(),
		$('#col' + i + '_regex').prop('checked'),
		$('#col' + i + '_smart').prop('checked')).draw();
	}
	$(document).ready(function() {
		//filter and hide
		$('.nav-toggle').click(function() {
			//get collapse content selector
			var collapse_content_selector = $(this).attr('href');

			//make the collapse content to be shown or hide
			var toggle_switch = $(this);
			$(collapse_content_selector).toggle(function() {
				if ($(this).css('display') == 'none') {
					//change the button label to be 'Show'
					toggle_switch.html('Filter the table');
				} else {
					//change the button label to be 'Hide'
					toggle_switch.html('Hide Filter');
				}
			});
		});
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
				label : "name:",
				name : "name:"
			}, {
				label : "state:",
				name : "state:"
			}, {
				label : "schoolName:",
				name : "schoolName:"
			}, {
				label : "schoolCity:",
				name : "schoolCity:"
			}, {
				label : "maxprepsUrl:",
				name : "maxprepsUrl:"
			}, {
				label : "pos:",
				name : "pos:"
			}, {
				label : "height:",
				name : "height:"
			}, {
				label : "fortyDash:",
				name : "fortyDash:"
			}, {
				label : "weight:",
				name : "weight:"
			}, {
				label : "stars:",
				name : "stars:"
			}, {
				label : "rating:",
				name : "rating:"
			}, {
				label : "gradYear:",
				name : "gradYear:"
			}, {
				label : "GP:",
				name : "GP:"
			}, {
				label : "Avg:",
				name : "Avg:"
			}, {
				label : "OBP:",
				name : "OBP:"
			}, {
				label : "H:",
				name : "H:"
			}, {
				label : "RBI:",
				name : "RBI:"
			}, {
				label : "R:",
				name : "R:"
			}, {
				label : "SB:",
				name : "SB:"
			}, {
				label : "AB:",
				name : "AB:"
			}, {
				label : "SLG:",
				name : "SLG:"
			}, {
				label : "PA:",
				name : "PA:"
			}, {
				label : "FP:",
				name : "FP:"
			}, {
				label : "K:",
				name : "K:"
			}, {
				label : "IP:",
				name : "IP:"
			} ]
		});

		// Activate an inline edit on click of a table cell
		$('#example').on('click', 'tbody td:not(:first-child)', function(e) {
			editor.inline(this);
		});

		$('#example').DataTable({
			//dom: "Tfrtip",
			dom : '<"top"ilpT<"clear">>rt<"bottom"ilp<"clear">>',
			"aLengthMenu" : [ [ 10, 25, 50, -1 ], [ 10, 25, 50, "All" ] ],
			"scrollY" : 400,
			"scrollX" : true,
			ajax : {
				url : "players/playersjson",
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
				data : "name"
			}, {
				data : "state"
			}, {
				data : "schoolName"
			}, {
				data : "schoolCity"
			}, {
				data : "maxprepsUrl"
			}, {
				data : "pos"
			}, {
				data : "height"
			}, {
				data : "fortyDash"
			}, {
				data : "weight"
			}, {
				data : "stars"
			}, {
				data : "rating"
			}, {
				data : "gradYear"
			}, {
				data : "GP"
			}, {
				data : "Avg"
			}, {
				data : "OBP"
			}, {
				data : "H"
			}, {
				data : "RBI"
			}, {
				data : "R"
			}, {
				data : "SB"
			}, {
				data : "AB"
			}, {
				data : "SLG"
			}, {
				data : "PA"
			}, {
				data : "FP"
			}, {
				data : "K"
			}, {
				data : "IP"
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

		$('input.global_filter').on('keyup click', function() {
			filterGlobal();
		});

		$('input.column_filter').on('keyup click', function() {
			filterColumn($(this).parents('tr').attr('data-column'));
		});
	});
</script>
</head>
<body>
	<section class="round-border">
		<div>
			<button style="display: inline-block; float: left;" href="#collapse1"
				class="nav-toggle">Filter the table</button>
			<p>
				<a href="/spring-jpa/players/csvexport">Export</a>
			</p>
		</div>
		<div id="collapse1" style="display: none">
			<table>
				<tbody>
					<tr id="filter_col1" data-column="0">
						<td>Name:</td>
						<td align="center"><input type="text" class="column_filter"
							id="col0_filter"></td>
					</tr>
					<tr id="filter_col2" data-column="1">
						<td>State:</td>
						<td><input type="text" class="column_filter" id="col1_filter"></td>
					</tr>
					<tr id="filter_col3" data-column="2">
						<td>School Name:</td>
						<td><input type="text" class="column_filter" id="col2_filter"></td>
					</tr>
					<tr id="filter_col4" data-column="3">
						<td>School City:</td>
						<td><input type="text" class="column_filter" id="col3_filter"></td>
					</tr>
					<tr id="filter_col5" data-column="4">
						<td>Pos:</td>
						<td><input type="text" class="column_filter" id="col4_filter"></td>
					</tr>
				</tbody>
			</table>
		</div>
	</section>

	<table id="example" class="display" cellspacing="0">
		<thead>
			<tr>
				<th></th>
				<th>name</th>
				<th>state</th>
				<th>schoolName</th>
				<th>schoolCity</th>
				<th>maxprepsUrl</th>
				<th>pos</th>
				<th>height</th>
				<th>fortyDash</th>
				<th>weight</th>
				<th>stars</th>
				<th>rating</th>
				<th>gradYear</th>
				<th>GP</th>
				<th>Avg</th>
				<th>OBP</th>
				<th>H</th>
				<th>RBI</th>
				<th>R</th>
				<th>SB</th>
				<th>AB</th>
				<th>SLG</th>
				<th>PA</th>
				<th>FP</th>
				<th>K</th>
				<th>IP</th>
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