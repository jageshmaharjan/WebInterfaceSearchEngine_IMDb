<%@ page import="MyPackage.RankingModelVSM" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="BLL.*" %>
<%@ page import="BO.BOMovie" %>
<%@ page import="java.util.*" %>
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
    <%
        String stxt = request.getParameter("searchtext");
    %>

    <div class="container-fluid">
        <div class="jumbotron jumbotron-fluid " style="height: 10px">
            <div class="row">
                <form action="redirectmainpage.jsp" method="post">
                    <div class="col-xs-12 col-md-1">
                        <img src="images/smalllogoJ.jpg" >
                    </div>
                <div class="col-xs-12 col-md-9">
                    <input type="text" class="form-control" align="center"  id="search" placeholder="Search your Movie here" name="searchtext" value="<%= request.getParameter("searchtext")%>" >
                </div>
                <div class="col-xs-6 col-md-2">
                    <button type="submit" class="btn btn-primary" name="Search" value="Search">
                        <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                        Search Movies
                    </button>
                </div>
                </form>
            </div>
        </div>
        <div>
            <%

//                List<Integer> movielst = new ArrayList<Integer>();
//                String search_query = request.getParameter("searchtext");
//                movielst = RankingModelVSM.rankProcessing(search_query);

                List<Integer> num = new ArrayList<Integer>();
                for (int i=1;i<100;i++)
                {
                    num.add(i);
                }

                BLLMovies bllmovie = new BLLMovies();
                List<BOMovie> bomovelst = new ArrayList<BOMovie>();
                bomovelst = bllmovie.getAllMoviesFromMovies(num);


                Map<Integer, List<String> > actor = new HashMap<Integer, List<String> >();
                BLLActor bllActor = new BLLActor();
                actor = bllActor.getAllActorsFromMovies(num);

                Map<Integer,List<String>> director = new HashMap<Integer,List<String>>();
                BLLDirector bllDirector = new BLLDirector();
                director = bllDirector.getAllDirectorByMovieID(num);

                Map<Integer,List<String>> genre = new HashMap<Integer,List<String>>();
                BLLGenre bllGenre = new BLLGenre();
                genre = bllGenre.getAllGenreByMovieID(num);

                Map<Integer,List<String>> language = new HashMap<Integer,List<String>>();
                BLLLanguage bllLanguage = new BLLLanguage();
                language = bllLanguage.getAllLanguageByMovieID(num);


                for (BOMovie bom : bomovelst)
                {
            %>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-xs-6 col-sm-1" > <%= "Release: " + bom.getReleasedate() %></div>
                        <div class="col-xs-6 col-sm-9">
                            <a href="<%= bom.getMovieurl() %>">
                                <h4 class="list-group-item-heading"> <%=  bom.getMoviename()  %></h4></a>
                            <%= "Dir: " + director.get(bom.getMovieid()) %>
                        </div>
                        <div class="col-xs-6 col-sm-2"><%= "Rating: " + bom.getRating()  %></div>
                    </div>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-sm-1">
                            <img src=" <%= bom.getCoverurl() %>">
                        </div>
                        <div class="col-sm-11">
                            <div class="row">
                                <div class="col-xs-6 col-sm-12">
                                    <p class="list-group-item-text"> <%= bom.getStoryline() %> </p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-6 col-sm-11">
                                    <%= "\t Starting: " + actor.get(bom.getMovieid()) %>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-6 col-sm-12">
                                    <%= "\t Genre: " + genre.get(bom.getMovieid()) %>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-6 col-sm-12">
                                    <%= "\t Language: "+ language.get(bom.getMovieid()) %>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%
                }
            %>
        </div>
    </div>

</body>
</html>
