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
    div.dataTables_wrapper, table {
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

	<table cellpadding="0" cellspacing="0" border="0" class="display" id="example" width="100%">
    <thead>
        <tr>
            <th width="30%">Browser</th>
            <th width="20%">Rendering engine</th>
            <th width="18%">Platform(s)</th>
            <th width="20%">Engine version</th>
            <th width="12%">CSS grade</th>
        </tr>
    </thead>
    <tfoot>
        <tr>
            <th>Browser</th>
            <th>Rendering engine</th>
            <th>Platform(s)</th>
            <th>Engine version</th>
            <th>CSS grade</th>
        </tr>
    </tfoot>
</table>
            
	<script type="text/javascript" charset="utf8"
		src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-2.1.0.min.js"></script>
	<script type="text/javascript" charset="utf8"
		src="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/jquery.dataTables.js"></script>
	<script type="text/javascript" charset="utf8"
		src="https://editor.datatables.net/release/DataTables/extras/TableTools/media/js/TableTools.js"></script>
	<script type="text/javascript" charset="utf8"
		src="resources/dataTables.editor.js"></script>
	<script>
	var editor; // use a global for the submit and return data rendering in the examples
	 
	$(document).ready(function() {
	    editor = new $.fn.dataTable.Editor( {
	        "ajaxUrl": "/spring-jpa/getjsoneditor",
	        "domTable": "#example",
	        "fields": [ {
	                "label": "Browser:",
	                "name": "browser"
	            }, {
	                "label": "Rendering engine:",
	                "name": "engine"
	            }, {
	                "label": "Platform:",
	                "name": "platform"
	            }, {
	                "label": "Version:",
	                "name": "version"
	            }, {
	                "label": "CSS grade:",
	                "name": "grade"
	            }
	        ]
	    } );
	 
	    $('#example').dataTable( {
	        "sDom": "Tfrtip",
	        "sAjaxSource": "php/browsers.php",
	        "aoColumns": [
	            { "mData": "browser" },
	            { "mData": "engine" },
	            { "mData": "platform" },
	            { "mData": "version", "sClass": "center" },
	            { "mData": "grade", "sClass": "center" }
	        ],
	        "oTableTools": {
	            "sRowSelect": "multi",
	            "aButtons": [
	                { "sExtends": "editor_create", "editor": editor },
	                { "sExtends": "editor_edit",   "editor": editor },
	                { "sExtends": "editor_remove", "editor": editor }
	            ]
	        }
	    } );
	} );
	</script>
</body>
</html>