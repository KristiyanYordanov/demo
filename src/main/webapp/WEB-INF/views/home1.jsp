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

	<table id="example">
		<thead>
			<tr>
				<th class="site_name">Name</th>
				<th>Url</th>
				<th>Type</th>
				<th>Last modified</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>

	<script type="text/javascript" charset="utf8"
		src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" charset="utf8"
		src="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/jquery.dataTables.min.js"></script>
	<script>
		$(document).ready(function() {
			$("#example").dataTable({
				"bServerSide" : true,
				"sAjaxSource" : "/spring-jpa/resources/exampejson.json",
				"aoColumns" : [ {
					"mData" : "name",
					"sTitle" : "Site name"
				}, {
					"mData" : "url",
					"mRender" : function(url, type, full) {
						return '<a href="'+url+'">' + url + '</a>';
					}
				}, {
					"mData" : "editor.name"
				}, {
					"mData" : "editor.phone"
				}, {
					"mData" : "editor",
					"mRender" : function(data) {
						return data.email.join("<br>");
					}
				} ]
			});
		});
	</script>
</body>
</html>