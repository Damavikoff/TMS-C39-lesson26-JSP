<%-- 
    Document   : login
    Created on : XX.XX.2021, XX:XX:XX
    Author     : SharpSchnitzel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.request.contextPath}/src/semantic.min.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/src/style.css" rel="stylesheet" type="text/css"/>
        <script src="${pageContext.request.contextPath}/src/jquery-3.6.0.min.js" type="text/javascript"></script>
        <title>LGN</title>
    </head>
    <body>
        <div class="ui middle aligned center aligned login grid h100 m0">
            <div class="column">
                <h2 class="ui large blue header">
                    <i class="user icon"></i>
                    <div class="content">LGN</div>
                </h2>
                <div class="ui stacked segment">
                    <form class="ui form" method="POST" action="login">
                        <div class="field">
                            <div class="ui left icon input">
                                <i class="user icon"></i>
                                <input type="text" name="user" placeholder="login...">
                            </div>
                        </div>
                        <div class="field">
                            <div class="ui left icon input">
                                <i class="lock icon"></i>
                                <input type="password" name="password" placeholder="password...">
                            </div>
                        </div>
                        <div class="ui divider"></div>
                        <div class="field">
                            <button class="ui facebook right labeled fluid icon button">
                                Get In <i class="sign in alternate icon"></i>
                            </button>
                        </div>
                    </form>
                </div>
                <c:forEach items="${sessionScope.problems}" var="problem">
                    <div class="ui warning message">
                        <div class="content">${problem}</div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </body>
</html>

<% 
    request.getSession().removeAttribute("problems");
%>
