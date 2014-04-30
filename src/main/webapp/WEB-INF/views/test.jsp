<html>
<head>
<link rel="stylesheet" type="text/css"
	href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/css/jquery.dataTables.css">
</head>
<style type="text/css">
</style>
<body>
	<div>
		<button value="Submit" onclick="startMaxpreps()">Start Maxpreps</button>
		<br>
		<button value="Submit" onclick="stopMaxpreps()">Stop Maxpreps</button>
	</div>
	
	
	<script>
	function startMaxpreps() {
		$.ajax({ 
		    url: "/spring-jpa/schedule/startmaxpreps", 
		    type: 'GET', 
		    dataType: 'json', 
		    //data: "{\"name\":\"hmkcode\",\"id\":2}", 
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
		    //data: "{\"name\":\"hmkcode\",\"id\":2}", 
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