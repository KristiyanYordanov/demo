<html>
<head>
<title>Scout Automated Adviser</title>
</head>
<body>

<div>tryjson</div>
	
	<button onclick="sendAjax()">Click me</button>
<div id="id"></div>
<div id="name"></div>
<div id="location"></div>	
</body>
  <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script type="text/javascript" >


 
function sendAjax() {
 
$.ajax({ 
    url: "/spring-jpa/returnjsonpage", 
    type: 'POST', 
    dataType: 'json', 
    //data: "{\"name\":\"hmkcode\",\"id\":2}", 
    contentType: 'application/json',
    mimeType: 'application/json',
    success: function(data) { 
    	document.getElementById("id").innerHTML = data.id;
    	document.getElementById("name").innerHTML = data.name;
    	document.getElementById("location").innerHTML = data.location;
    },
    error:function(data,status,er) { 
        alert("error: "+data+" status: "+status+" er:"+er);
    }
});
}
</script>
</html>