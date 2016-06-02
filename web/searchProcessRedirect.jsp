<%@ page import="java.util.*" %>
<%@ page import="MyPackage.RankingModelVSM" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="BLL.*" %><%--
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
//
//        movielst = RankingModelVSM.rankProcessing(search_query);

        if (search_query != null)
        {
            search_query = request.getParameter("searchtext");
            movielst = RankingModelVSM.rankProcessing(search_query);
            response.sendRedirect("redirectmainpage.jsp");
        }
        else
        {
//            isPostBack(search_query);
            search_query = request.getParameter("searchtextin");
            movielst = RankingModelVSM.rankProcessing(search_query);
            response.sendRedirect("redirectmainpage.jsp");
        }

    %>
</body>
</html>
<%!
    private void isPostBack(String search_query) throws SQLException, ClassNotFoundException
    {
        BLLMovies bllmovie = new BLLMovies();

        List<Integer> movielst = new ArrayList<>();

        movielst = RankingModelVSM.rankProcessing(search_query);

        //System.out.println(movielst);
        ResultSet rs;

        for (Integer mlst : movielst)
        {
            BLLLanguage blllang = new BLLLanguage();
            List<String> lang_lst = new ArrayList<>();
            lang_lst = blllang.getAllLanguageByMovieID(mlst);


            BLLGenre bllgenre = new BLLGenre();
            List<String> genre_lst = new ArrayList<>();
            genre_lst = bllgenre.getAllGenreByMovieID(mlst);

            BLLDirector blldir = new BLLDirector();
            List<String> dir_lst = new ArrayList<>();
            dir_lst = blldir.getAllDirectorByMovieID(mlst);

            BLLActor bllact = new BLLActor();
            List<String> actorsByMovie = new ArrayList<>();
            actorsByMovie = bllact.getAllActorsByMovie(mlst);

            String moviename;
            rs = bllmovie.getAllMoviesFromMovies(mlst);
            while (rs.next())
            {
                rs.getString("ReleaseDate");
                rs.getString("url");
                rs.getString("MovieName");
                rs.getString("Rating");
                rs.getString("coverpicurl");

                rs.getString("Storyline");
            }

        }
    }
%>