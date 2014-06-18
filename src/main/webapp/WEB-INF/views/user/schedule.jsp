<html>
<head>
<title>Scout Automated Adviser</title>
<style type="text/css">
.buttonSubmit {
	background-color: #ccc;
	-moz-border-radius: 5px;
	-webkit-border-radius: 5px;
	border-radius: 6px;
	color: #fff;
	font-family: 'Oswald';
	font-size: 20px;
	text-decoration: none;
	cursor: poiner;
	border: none;
}

.buttonSubmit:hover {
	border: none;
	background: #A8DDE8;
	box-shadow: 0px 0px 1px #777;
}
</style>
</head>
<body>
	<div>
		<button class="buttonSubmit" value="Submit" onclick="startMaxpreps()">Start Maxpreps</button>
		<br>
		<button class="buttonSubmit" value="Submit" onclick="stopMaxpreps()">Stop Maxpreps</button>
	</div>
	<div>
		<button class="buttonSubmit" value="Submit" onclick="startHudl()">Start Hudl</button>
		<br>
		<button class="buttonSubmit" value="Submit" onclick="stopHudl()">Stop Hudl</button>
	</div>
	
	
	<script>
	function startMaxpreps() {
		$.ajax({ 
		    url: "/spring-jpa/schedule/startmaxpreps", 
		    type: 'GET', 
		    dataType: 'json', 
		    contentType: 'application/json',
		    mimeType: 'application/json',
		    success: function(data) { 
		    },
		});
	}
	function stopMaxpreps() {
		$.ajax({ 
		    url: "/spring-jpa/schedule/stopmaxpreps", 
		    type: 'GET', 
		    dataType: 'json', 
		    contentType: 'application/json',
		    mimeType: 'application/json',
		    success: function(data) { 
		    },
		});
	}
	function startHudl() {
		$.ajax({ 
		    url: "/spring-jpa/schedule/startHudl", 
		    type: 'GET', 
		    dataType: 'json', 
		    contentType: 'application/json',
		    mimeType: 'application/json',
		    success: function(data) { 
		    },
		});
	}
	function stopHudl() {
		$.ajax({ 
		    url: "/spring-jpa/schedule/stopHudl", 
		    type: 'GET', 
		    dataType: 'json', 
		    contentType: 'application/json',
		    mimeType: 'application/json',
		    success: function(data) { 
		    },
		});
	}
	</script>
</body>
</html>