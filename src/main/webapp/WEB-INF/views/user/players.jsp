<html>
<head>
<title>Scout Automated Adviser</title>
<link rel="stylesheet" type="text/css"
	href="resources/css/jquery.dataTables.css.css" />
	
<link rel="stylesheet" type="text/css"
	href="resources/css/dataTables.tableTools.css" />
	
<link rel="stylesheet" type="text/css"
	href="resources/css/dataTables.editor.min.css" />
	
<link rel="stylesheet" type="text/css"
	href="resources/css/font-awesome.css" />
	
<style type="text/css">
#example{
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
	src="resources/js/dataTables.tableTools.js"></script>
<script type="text/javascript" charset="utf8"
	src="resources/js/ZeroClipboard.min.js"></script>
<script type="text/javascript" charset="utf8"
	src="resources/js/dataTables.editor.js"></script>
	
	<script type="text/javascript" class="init">
	var editor; // use a global for the submit and return data rendering in the examples
	 
	$(document).ready(function() {
	    editor = new $.fn.dataTable.Editor( {
	        ajax: {
	            create: {
	                type: 'POST',
	            	dataType: 'json', 
	            	contentType: 'application/json',
	                url:  'players/create'
	            },
	            edit: {
	            	type: 'PUT',
	            	dataType: 'json', 
	            	contentType: 'application/json',
	                url:  'players/edit'
	            },
	            remove: {
	                type: 'DELETE',
	                dataType: 'json', 
	            	contentType: 'application/json',
	                url:  'players/delete'
	            }
	        },
	        table: "#example",
	        //label are for create method
	        fields: [ {
	    		label: "name:",
				name: "name:"},{
				label: "state:",
				name: "state:"},{
				label: "schoolName:",
				name: "schoolName:"},{
				label: "schoolCity:",
				name: "schoolCity:"},{
				label: "maxprepsUrl:",
				name: "maxprepsUrl:"},{
				label: "pos:",
				name: "pos:"},{
				label: "height:",
				name: "height:"},{
				label: "fortyDash:",
				name: "fortyDash:"},{
				label: "weight:",
				name: "weight:"},{
				label: "stars:",
				name: "stars:"},{
				label: "rating:",
				name: "rating:"},{
				label: "gradYear:",
				name: "gradYear:"},{
				label: "GP:",
				name: "GP:"},{
				label: "Avg:",
				name: "Avg:"},{
				label: "OBP:",
				name: "OBP:"},{
				label: "H:",
				name: "H:"},{
				label: "RBI:",
				name: "RBI:"},{
				label: "R:",
				name: "R:"},{
				label: "SB:",
				name: "SB:"},{
				label: "AB:",
				name: "AB:"},{
				label: "SLG:",
				name: "SLG:"},{
				label: "PA:",
				name: "PA:"},{
				label: "FP:",
				name: "FP:"},{
				label: "K:",
				name: "K:"},{
				label: "IP:",
				name: "IP:"}
	        ]
	    } );
	    
	    // Activate an inline edit on click of a table cell
	    $('#example').on( 'click', 'tbody td:not(:first-child)', function (e) {
	        editor.inline( this );
	    } );
	    
	    $('#example').DataTable( {
	        //dom: "Tfrtip",
	     	dom: '<"top"iflpT<"clear">>rt<"bottom"iflp<"clear">>', 
	     	"aLengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]], 
	        ajax: {
	            url: "players/playersjson",
	            type: "GET"
	        },
	        serverSide: true,
	        columns: [
	            { data: null, render: function ( data, type, row ) {
	            	//id data.DT_RowId
	                // Combine the first and last names into a single table field
	                return '';
	            } },
	    		{ data: "name" },
				{ data: "state" },
				{ data: "schoolName" },
				{ data: "schoolCity" },
				{ data: "maxprepsUrl" },
				{ data: "pos" },
				{ data: "height" },
				{ data: "fortyDash" },
				{ data: "weight" },
				{ data: "stars" },
				{ data: "rating" },
				{ data: "gradYear" },
				{ data: "GP" },
				{ data: "Avg" },
				{ data: "OBP" },
				{ data: "H" },
				{ data: "RBI" },
				{ data: "R" },
				{ data: "SB" },
				{ data: "AB" },
				{ data: "SLG" },
				{ data: "PA" },
				{ data: "FP" },
				{ data: "K" },
				{ data: "IP" }
	        ],
	        order: [ 1, 'asc' ],
	        tableTools: {
	            sRowSelect: "os",
	            sRowSelector: 'td:first-child',
	            aButtons: [
	                { sExtends: "editor_create", editor: editor },
	                { sExtends: "editor_edit",   editor: editor },
	                { sExtends: "editor_remove", editor: editor }
	            ]
	        }
	    } );
	    $( "#cssmenu" ).width($( "#example" +200).width());
	    $( "body" ).width($( "#example"+200 ).width());
	} );
	</script>
</head>
<body>
<br>	
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
    </table>
</body>
</html>