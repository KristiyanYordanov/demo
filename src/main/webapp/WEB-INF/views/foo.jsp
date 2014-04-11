<html>
<head>
</head>
<body>

<div>foo</div>

</body>
  <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script type="text/javascript" >
$(document).ready(function(){
  sendAjax();
});
 
function sendAjax() {
 
$.ajax({ 
    url: "/spring-mvc-json/rest/cont/person", 
    type: 'POST', 
    dataType: 'json', 
    data: "{\"name\":\"hmkcode\",\"id\":2}", 
    contentType: 'application/json',
    mimeType: 'application/json',
    success: function(data) { 
        alert(data.id + " " + data.name);
    },
    error:function(data,status,er) { 
        alert("error: "+data+" status: "+status+" er:"+er);
    }
});
}
</script>
</html>