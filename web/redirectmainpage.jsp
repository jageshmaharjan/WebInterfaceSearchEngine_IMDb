<%@ page import="java.util.Date" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="MyPackage.RankingModelVSM" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="BLL.BLLMovies" %>
<%@ page import="BLL.BLLActor" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<head>
    <title>Your Movies...</title>
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
    <div class="container-fluid">
        <div class="jumbotron jumbotron-fluid " style="height: 10px">
            <div class="row">
                <form action="redirectmainpage.jsp" method="post">
                <div class="col-xs-12 col-md-8">
                    <input type="text" class="form-control" align="center"  id="search" placeholder="Search your Movie here" name="searchtext" value=" <%= request.getParameter("searchtext")%> ">
                </div>
                <div class="col-xs-6 col-md-4">
                    <button type="submit" class="btn btn-primary" name="Search" value="Search">Search Movies</button>
                </div>
                </form>
            </div>
        </div>
        <div>
            <%--<table class="table table-striped">--%>

                <%
                    BLLMovies bllmovie = new BLLMovies();

                    List<Integer> movielst = new ArrayList<>();
                    String search_query = request.getParameter("searchtext");

                    movielst = RankingModelVSM.rankProcessing(search_query);

                    System.out.println(movielst);
                    ResultSet rs;

                    for (Integer mlst : movielst)
                    {
                        BLLActor bllact = new BLLActor();
                        List<String> actorsByMovie = new ArrayList<>();
                        actorsByMovie = bllact.getAllActorsByMovie(mlst);

                        %><tr><td><%
                        String moviename;
                        rs = bllmovie.getAllMoviesFromMovies(mlst);
                        while (rs.next())
                        {
                        %>
                            <div class="panel panel-default">
                            <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-6 col-sm-1" > <%= rs.getString("ReleaseDate") %></div>
                                <div class="col-xs-6 col-sm-9">
                                    <a href="<%= rs.getString("url")%>">
                                    <h4 class="list-group-item-heading"> <%=  rs.getString("MovieName")  %></h4></a>
                                </div>
                                <div class="col-xs-6 col-sm-2"><%= rs.getString("Rating") %></div>
                            </div>
                            </div>
                            <div class="panel-body">
                            <div class="row">
                                <div class="col-xs-6 col-sm-12">
                                    <p class="list-group-item-text"> <%= rs.getString("Storyline") %> </p>
                                </div>
                            </div>
                            </div>
                            <div class="row">
                                <p class="col-xs-6 col-sm-12">
                                    <%= actorsByMovie %>
                                </p>
                            </div>
                            </div>
                        <%
                        }
                        %>
                        </td></tr>
                    <%
                    }
                %>

            <%--</table>--%>
        </div>
    </div>

</body>
</html>
