<%@ page import="MyPackage.RankingModelVSM" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="BLL.*" %>
<%@ page import="BO.BOMovie" %>
<%@ page import="java.util.*" %>
<%@ page import="MyPackage.SentimentAnalyzer" %>
<%@ page import="io.indico.api.text.Emotion" %>
<%@ page import="MyPackage.RankingObject" %>
<%@ page import="BO.BOActor" %>
<%@ page import="BO.BOSentiment" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<head>
    <title>Your Movies</title>
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
        <%
            SentimentAnalyzer sentiAnalyser = new SentimentAnalyzer();
            String query;
            Map<Emotion, Double> senti_score = new HashMap<>();
            if (!request.getParameter("searchtext").equals(""))
            {
                query = request.getParameter("searchtext");
                senti_score = sentiAnalyser.getSentiment(query);
            }
            //this piece of code uses the movie_sentiment
//            List<Integer> docIds = new ArrayList<>();
//            docIds = sentiAnalyser.getDocIds(senti_score);

        %>
        <div>
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <h6 align="center">
                        Sentiment of Your Query is <%= ((request.getParameter("searchtext").equals(null))?"No Sentiment":senti_score).toString() %>
                    </h6>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-4">
                    <h4 align="center">Romance</h4>
                    <div class="embed-responsive embed-responsive-16by9">
                        <iframe class="embed-responsive-item" src="https://www.youtube.com/embed/4axVlG-Ahxk"></iframe>
                    </div>
                </div>
                <div class="col-sm-4">
                    <h4 align="center">Action </h4>
                    <div class="embed-responsive embed-responsive-16by9">
                        <iframe class="embed-responsive-item" src="https://www.youtube.com/embed/z4UDNzXD3qA"></iframe>
                    </div>
                </div>
                <div class="col-sm-4">
                    <h4 align="center">Comedy </h4>
                    <div class="embed-responsive embed-responsive-16by9">
                        <iframe class="embed-responsive-item" src="https://www.youtube.com/embed/eDr1MD2a1aM"></iframe>
                    </div>
                </div>
            </div>
        </div>
            <br>
            <%
                List<Integer> movielst = new ArrayList<Integer>();
                String search_query = request.getParameter("searchtext");
                movielst = RankingObject.rank(search_query);

                BLLMovies bllmovie = new BLLMovies();
                List<BOMovie> bomovelst = new ArrayList<BOMovie>();
                bomovelst = bllmovie.getAllMoviesFromMovies(movielst);

                Map<Integer, List<String> > actor = new HashMap<Integer, List<String> >();
                List<BOActor> actorslst = new ArrayList<>();
                BLLActor bllActor = new BLLActor();
                actorslst = bllActor.getAllActorsFromMovies(movielst);

                Map<Integer,List<String>> director = new HashMap<Integer,List<String>>();
                BLLDirector bllDirector = new BLLDirector();
                director = bllDirector.getAllDirectorByMovieID(movielst);

                Map<Integer,List<String>> genre = new HashMap<Integer,List<String>>();
                BLLGenre bllGenre = new BLLGenre();
                genre = bllGenre.getAllGenreByMovieID(movielst);

                Map<Integer,List<String>> language = new HashMap<Integer,List<String>>();
                BLLLanguage bllLanguage = new BLLLanguage();
                language = bllLanguage.getAllLanguageByMovieID(movielst);

                List<BOSentiment> movie_sentiment = new ArrayList<>();
                BLLSentiment bllsenti = new BLLSentiment();
                movie_sentiment = bllsenti.getSentimentsFromMovie(movielst);

                int i = 0;
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
                                    <%= "\t Starting: " + actorslst.get(i).getActors() %>
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
                    i++;
                }
            %>
            <ul class="pagination">
                <%
                    int movie_per_pg = movielst.size()/10;
                    int index = 1;
                    for (int j=0;j<movielst.size(); j+=movie_per_pg)
                    {
                %>
                        <li><a href="#"> <%= index %> </a> </li>
                <%
                        index++;
                    }
                %>
            </ul>
        </div>
    </div>

</body>
</html>
