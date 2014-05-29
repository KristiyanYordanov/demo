<html>
<head>
<title>Scout Automated Adviser</title>
<style type="text/css">
</style>
</head>
<body>
	<div>
		<button value="Submit" onclick="startMaxpreps()">Start Maxpreps</button>
		<br>
		<button value="Submit" onclick="stopMaxpreps()">Stop Maxpreps</button>
	</div>
	<div>
		<button value="Submit" onclick="startHudl()">Start Hudl</button>
		<br>
		<button value="Submit" onclick="stopHudl()">Stop Hudl</button>
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
		    error:function(data,status,er) { 
		        alert("error: "+data+" status: "+status+" er:"+er);
		    }
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
		    error:function(data,status,er) { 
		        alert("error: "+data+" status: "+status+" er:"+er);
		    }
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
		    error:function(data,status,er) { 
		        alert("error: "+data+" status: "+status+" er:"+er);
		    }
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
		    error:function(data,status,er) { 
		        alert("error: "+data+" status: "+status+" er:"+er);
		    }
		});
	}
	</script>
</body>
</html>