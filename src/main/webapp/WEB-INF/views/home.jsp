<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<style>
ul {
	list-style-type: none;
	margin: 0;
	padding: 0;
	overflow: hidden;
}

li {
	float: left;
}

a:link,a:visited {
	display: block;
	width: 120px;
	font-weight: bold;
	color: #FFFFFF;
	background-color: #98bf21;
	text-align: center;
	padding: 4px;
	text-decoration: none;
	text-transform: uppercase;
}

a:hover,a:active {
	background-color: #7A991A;
}
</style>
</head>
<body>
	<div id="main_menu">
		<ul>
			<li><a href="#" id="load_players"> admin home only</a></li>
			<li><a href="#" id="load_home2"> Players </a></li>
			<li><a href="<c:url value="/admin/adminList" />"> admin
					list, use can't see it</a></li>
			<li><a href="<c:url value="/user/userList" />"> user list</a></li>
			<li><a href="<c:url value="/players/user/player" />"> player</a></li>
			<li><a href="<c:url value="/returnjsonpage" />">
					returnjsonpage</a></li>
			<li><a href="<c:url value="/tryjson" />"> tryjson</a></li>
			<li><form action="<c:url value='/j_spring_security_logout' />"
					method='POST'>
					<input type="submit" value="Logout" />
				</form></li>
		</ul>
	</div>
	<div id="content"></div>
</body>
<script type="text/javascript" charset="utf8"
	src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-2.1.0.min.js"></script>
<script type="text/javascript" charset="utf8"
	src="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf8"
	src="https://datatables.net/release-datatables/extras/ColReorder/media/js/ColReorder.js"></script>

<script type="text/javascript" charset="utf8"
	src="https://editor.datatables.net/release/DataTables/extras/TableTools/media/js/TableTools.js"></script>

<script type="text/javascript" charset="utf8"
	src="https://datatables.net/release-datatables/extras/TableTools/media/js/ZeroClipboard.js"></script>

<script type="text/javascript">
	$("#load_players").on("click", function() {
		var html = 'home1';
		$('#content').load(html);
	});
	$("#load_home2").on("click", function() {
		var html = 'home2';
		$('#content').load(html);
	});
</script>
</html>