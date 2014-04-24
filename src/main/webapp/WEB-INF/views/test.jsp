<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/css/jquery.dataTables.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Spring MVC - Upload File</title>
</head>
<body>
	<!--  Form 2 -->
	<i>Uploading File With Ajax</i>
	<br />
	<form id="form2" method="post" action="/spring-jpa/cont/upload"
		enctype="multipart/form-data">
		<!-- File input -->
		<input name="file2" id="file2" type="file" /><br />
	</form>

	<button value="Submit" onclick="uploadFormData()">Upload</button>
	<i>Using FormData Object</i>

	<div id="demo"></div>
</body>
<script type="text/javascript" charset="utf8"
	src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-2.1.0.min.js"></script>
<script type="text/javascript" charset="utf8"
	src="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/jquery.dataTables.js"></script>
<script type="text/javascript">
	function uploadFormData() {
		$('#result').html('');
		var oMyForm = new FormData();
		oMyForm.append("file", file2.files[0]);
		var aryColTableChecked = new Array();

		$
				.ajax({
					url : 'http://localhost:8080/spring-jpa/players/upload',
					data : oMyForm,
					dataType : 'text',
					processData : false,
					contentType : false,
					type : 'POST',
					success : function(data) {
						$('#demo')
								.html(
										'<table cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table>');
						$
								.ajax({
									url : "/spring-jpa/players/header",
									type : 'GET',
									success : function(data) {
										var s = data.split(",");
										for (var i = 0; i < s.length; i++) {
											aryColTableChecked.push(s[i]);
										}
										var aryJSONColTable = [];
										for (var i = 0; i < aryColTableChecked.length; i++) {
											aryJSONColTable
													.push({
														"sTitle" : aryColTableChecked[i],
														"aTargets" : [ i ]
													});
										}
										var a = [
										            /* Reduced data set */
										            [ "Trident", "Internet Explorer 4.0", "Win 95+", 4, "X" ],
										            [ "Trident", "Internet Explorer 5.0", "Win 95+", 5, "C" ],
										            [ "Trident", "Internet Explorer 5.5", "Win 95+", 5.5, "A" ],
										            [ "Trident", "Internet Explorer 6.0", "Win 98+", 6, "A" ],
										            [ "Trident", "Internet Explorer 7.0", "Win XP SP2+", 7, "A" ],
										            [ "Gecko", "Firefox 1.5", "Win 98+ / OSX.2+", 1.8, "A" ],
										            [ "Gecko", "Firefox 2", "Win 98+ / OSX.2+", 1.8, "A" ],
										            [ "Gecko", "Firefox 3", "Win 2k+ / OSX.3+", 1.9, "A" ],
										            [ "Webkit", "Safari 1.2", "OSX.3", 125.5, "A" ],
										            [ "Webkit", "Safari 1.3", "OSX.3", 312.8, "A" ],
										            [ "Webkit", "Safari 2.0", "OSX.4+", 419.3, "A" ],
										            [ "Webkit", "Safari 3.0", "OSX.4+", 522.1, "A" ]
										        ];
										var a = [
										            /* Reduced data set */
										            [ "Trident", "Internet Explorer 4.0", "Win 95+", 4, "X" ],
										            [ "Trident", "Internet Explorer 5.0", "Win 95+", 5, "C" ],
										            [ "Trident", "Internet Explorer 5.5", "Win 95+", 5.5, "A" ],
										            [ "Trident", "Internet Explorer 6.0", "Win 98+", 6, "A" ],
										            [ "Trident", "Internet Explorer 7.0", "Win XP SP2+", 7, "A" ],
										            [ "Gecko", "Firefox 1.5", "Win 98+ / OSX.2+", 1.8, "A" ],
										            [ "Gecko", "Firefox 2", "Win 98+ / OSX.2+", 1.8, "A" ],
										            [ "Gecko", "Firefox 3", "Win 2k+ / OSX.3+", 1.9, "A" ],
										            [ "Webkit", "Safari 1.2", "OSX.3", 125.5, "A" ],
										            [ "Webkit", "Safari 1.3", "OSX.3", 312.8, "A" ],
										            [ "Webkit", "Safari 2.0", "OSX.4+", 419.3, "A" ],
										            [ "Webkit", "Safari 3.0", "OSX.4+", 522.1, "A" ]
										        ];
										oTable = $('#example')
												.dataTable(
														{
															//"bProcessing" : true,
															//"bServerSide" : true,
															 "aaData": [
															            /* Reduced data set */
															            [ "Trident", "Internet Explorer 4.0", "Win 95+", 4, "X" ],
															            [ "Trident", "Internet Explorer 5.0", "Win 95+", 5, "C" ],
															            [ "Trident", "Internet Explorer 5.5", "Win 95+", 5.5, "A" ],
															            [ "Trident", "Internet Explorer 6.0", "Win 98+", 6, "A" ],
															            [ "Trident", "Internet Explorer 7.0", "Win XP SP2+", 7, "A" ],
															            [ "Gecko", "Firefox 1.5", "Win 98+ / OSX.2+", 1.8, "A" ],
															            [ "Gecko", "Firefox 2", "Win 98+ / OSX.2+", 1.8, "A" ],
															            [ "Gecko", "Firefox 3", "Win 2k+ / OSX.3+", 1.9, "A" ],
															            [ "Webkit", "Safari 1.2", "OSX.3", 125.5, "A" ],
															            [ "Webkit", "Safari 1.3", "OSX.3", 312.8, "A" ],
															            [ "Webkit", "Safari 2.0", "OSX.4+", 419.3, "A" ],
															            [ "Webkit", "Safari 3.0", "OSX.4+", 522.1, "A" ]
															        ],
															"aoColumnDefs" : aryJSONColTable
														});
									},
									error : function(data, status, er) {
										alert("error: " + data + " status: "
												+ status + " er:" + er);
									}
								});

					}
				});
	}
</script>
</html>