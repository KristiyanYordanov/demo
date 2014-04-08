<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<body>
	<h3>Message : ${message}</h3>
	<h3>Username : ${username}</h3>
	home 2 jsp
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<h1>HI admin</h1>
	</sec:authorize>
</body>
</html>