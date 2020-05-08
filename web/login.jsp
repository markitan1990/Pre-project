<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>ListUsers</title>
</head>
<body>
<form action="admin" method="post">
    <input type="text" name="name" placeholder="Name" required>
    <input type="text" name="lastName" placeholder="Last Name" required>
    <input type="text" name="password" placeholder="Password" required>
    <button type="submit" name="Sign">Sign Up</button>
</form>
</body>
</html>