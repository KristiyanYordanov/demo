<html>
<head>
<title>Scout Automated Adviser</title>
<style type="text/css">
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
	                url:  'players/create'
	            },
	            edit: {
	            	dataType: 'json', 
	            	contentType: 'application/json',
	            	//mimeType: 'application/json',
	                type: 'PUT',
	                url:  'players/edit'
	            },
	            remove: {
	                type: 'DELETE',
	                url:  'players/delete'
	            }
	        },
	        table: "#example",
	        //label are for create method
	        fields: [ {
	                label: "name:",
	                name: "name"
	            }, {
	                label: "state:",
	                name: "state"
	            }, {
	                label: "schoolName:",
	                name: "schoolName"
	            }, {
	                label: "schoolCity:",
	                name: "schoolCity"
	            }
	        ]
	    } );
	    
	    // Activate an inline edit on click of a table cell
	    $('#example').on( 'click', 'tbody td:not(:first-child)', function (e) {
	        editor.inline( this );
	    } );
	    
	    $('#example').DataTable( {
	        //dom: "Tfrtip",
	     	dom: '<"top"iflpT<"clear">>rt<"bottom"iflp<"clear">>', 
	        ajax: "players/playersjson",
	        columns: [
	            { data: null, render: function ( data, type, row ) {
	            	//id data.DT_RowId
	                // Combine the first and last names into a single table field
	                return '';
	            } },
	            { data: "name" },
	            { data: "state" },
	            { data: "schoolName" },
	            { data: "schoolCity" } 
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
	} );
	</script>
</head>
<body>
	<table id="example" class="display" cellspacing="0" width="100%">
        <thead>
            <tr>
            	<th></th>
                <th>name</th>
                <th>state</th>
                <th>schoolName</th>
                <th>schoolCity</th>
            </tr>
        </thead>
    </table>
</body>
</html>