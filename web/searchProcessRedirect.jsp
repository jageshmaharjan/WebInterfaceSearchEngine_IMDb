<%@ page import="java.util.*" %>
<%@ page import="MyPackage.RankingModelVSM" %><%--
  Created by IntelliJ IDEA.
  User: jagesh
  Date: 05/30/2016
  Time: 2:22 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Searching...</title>
</head>
<body>
    <%

        List<Integer> movielst = new ArrayList<>();
        String search_query = request.getParameter("searchtext");

        movielst = RankingModelVSM.rankProcessing(search_query);


    %>
</body>
</html>
