<%--
  Created by IntelliJ IDEA.
  User: jagesh
  Date: 05/28/2016
  Time: 9:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Date" %>
<html lang="en">
  <head>
    <title>Sentiment Classification of Movie</title>
      <link rel="shortcut icon" href="images/logoJ.jpg" type="image/jpg" />
      <meta charset="utf-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <meta name="viewport" content="width=device-width, initial-scale=1">

      <link href="bootstrap-3.3.6-dist/css/bootstrap.min.css" rel="stylesheet">

      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
      <script src="images/scripts.js"></script>
      <script src="bootstrap-3.3.6-dist/js/jquery-2.1.4.js"></script>
  </head>
  <body>
  <h1 class="text-center">Movie Sentiment Classification</h1>
  <div class="container-fluid">
      <img src="images/logoJ.jpg" class="center-block">
      <br>
  </div>

      <form action="redirectmainpage.jsp" method="post">
          <div style="font-size: large" align="center" > Find Movies Matching Your Sentiments</div>
              <br>
      <div class="container-fluid">
          <div class="row">
              <div class="col-md-6 col-md-offset-3" ><input type="text" class="form-control" align="center"  id="search" placeholder="Search your Movie here" name="searchtext"></div>
          <br><br>
          <div class="row">
              <%--<div class="col-xs-4" ></div>--%>
              <div class="col-md-6 col-md-offset-3" align="center" >
                  <button type="submit" class="btn btn-primary" name="Search"  value="Search">
                      <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                      Search Movies
                  </button>
              </div>
              <%--<div class="col-xs-4" ></div>--%>
          </div>
          </div>
      </div>
          <br><br>
      </form>
      <div class="clearfix visible-xs-block"></div>
  </div>
  </body>
</html>
