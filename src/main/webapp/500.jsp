<%@ page import="static com.walking.carpractice.servlet.ErrorHandlingServlet.ERROR_MESSAGE_ATTRIBUTE_KEY" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>Title</title>
</head>
<body>
<h1><%= request.getAttribute(ERROR_MESSAGE_ATTRIBUTE_KEY) == null
        ? "Неизвестная ошибка"
        : request.getAttribute(ERROR_MESSAGE_ATTRIBUTE_KEY) %></h1>
</body>
</html>
