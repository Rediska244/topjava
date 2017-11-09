<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>

<table border="5">
    <thead>
    <tr>
        <th>DateTime</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Exceed</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${list}" var="ml">
        <tr>
                <td>${ml.dateTime}</td>
                <td>${ml.description}</td>
                <td>${ml.calories}</td>
                <td>${ml.exceed}></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>