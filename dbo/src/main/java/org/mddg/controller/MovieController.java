package org.mddg.controller;

import java.util.List;
import org.mddg.MySqlConnection;
import org.mddg.app.GetMoviesFromGenre;
import org.mddg.domain.Movie;

public class MovieController extends Controller {

    public MovieController() {
        super();
    }

    public void getMoviesFromGenre(String[] args) {
        if (args.length < 1) {
           p("Not enough arguments: <genre> [--limit=<limit> --order=[ASC|DESC]]");
        }

        List<Movie> result = new GetMoviesFromGenre(MySqlConnection.getConnection())
            .run(args[0], parseQueryParams(args));

        if (result.size() == 0) {
            p("No movies found for genre: " + args[0]);
            return;
        }

        p("Movies found for genre: " + args[0]);
        for (Movie movie : result) {
            p(movie.getTitle());
        }
    }
}
