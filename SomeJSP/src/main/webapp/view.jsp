<%-- 
    Document   : index
    Created on : XX.XX.2021, XX:XX:XX
    Author     : SharpSchnitzel
--%>

<%@page import="com.mycompany.somejsp.DatabaseHandler"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.request.contextPath}/src/semantic.min.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/src/style.css" rel="stylesheet" type="text/css"/>
        <script src="${pageContext.request.contextPath}/src/jquery-3.6.0.min.js" type="text/javascript"></script>
        <title>JSP Page</title>
    </head>
    <body>
      <div class="ui top fixed menu">
        <div class="item"><i class="large th icon"></i></div>
        <div class="right menu">
          <div class="item">
            <a class="ui small primary right labeled icon button" href="${pageContext.request.contextPath}/logout">GTFO <i class="sign out alternate icon"></i></a>
          </div>
        </div>
      </div>
      <div class="ui divided view grid m0 h100">
        <div class="row">
          <div class="ten wide column">
            <c:if test="${not empty sessionScope.problems}">
              <c:forEach items="${sessionScope.problems}" var="problem">
                <div class="ui warning message">
                    <div class="content">${problem}</div>
                </div>
              </c:forEach>
            </c:if>
              <table class="ui striped celled selectable table">
                <thead>
                  <tr>
                    <th class="collapsing center aligned">#</th>
                    <th class="center aligned">Title</th>
                    <th class="collapsing center aligned">Population</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${requestScope.cities}" var="city">
                    <tr>
                      <td class="collapsing center aligned">${city.getId()}</td>
                      <td><i class="city icon"></i>${city.getTitle()}</td>
                      <td class="collapsing">${city.getPopulation()}</td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
          </div>
          <div class="six wide column">
            <div class="ui stacked segment">
              <form class="ui form" method="POST" action="data">
                <div class="field">
                  <input type="text" name="title" placeholder="title...">
                </div>
                <div class="field">
                  <input type="text" name="population" placeholder="population...">
                </div>
                <div class="ui divider"></div>
                <div class="field">
                    <button class="ui facebook right labeled fluid icon button">
                      SV <i class="save icon"></i>
                    </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </body>
</html>

<% 
    request.getSession().removeAttribute("problems");
%>
