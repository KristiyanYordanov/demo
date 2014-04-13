<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/css/jquery.dataTables.css">
</head>
<body>
	<h3>Message : ${message}</h3>
	<h3>Username : ${username}</h3>
	<br> ${player}
	<br>
	<!-- <br> ${players} -->

	<table cellpadding="0" cellspacing="0" border="0" class="display" id="example">
	<thead>
		<tr>
			<th>name</th>
			<th>location</th>
			<th>pos(s)</th>
			<th>starsn</th>
			<th>height </th>
			<th>fortyDash </th>
			<th>weight </th>
			<th>gradYear </th>
			<th>rating </th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td colspan="5" class="dataTables_empty">Loading data from server</td>
		</tr>
	</tbody>
	<tfoot>
		<tr>
			<th>name</th>
			<th>location</th>
			<th>pos(s)</th>
			<th>starsn</th>
			<th>height </th>
			<th>fortyDash </th>
			<th>weight </th>
			<th>gradYear </th>
			<th>rating </th>
		</tr>
	</tfoot>
</table>

	<script type="text/javascript" charset="utf8"
		src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-2.1.0.min.js"></script>
	<script type="text/javascript" charset="utf8"
		src="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/jquery.dataTables.js"></script>
	<script>
	$(document).ready(function() {
	    $('#example').dataTable( {
	        "bProcessing": true,
	        "bServerSide": true,
	        "sAjaxSource" : "/spring-jpa/getjsonexample"
	    } );
	} );
	</script>
</body>
</html>