package org.mddg.app;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.mddg.domain.Movie;
import org.mddg.domain.request.SharedQueryRequest;

public class GetMoviesFromGenre {

    private final Connection connection;

    public GetMoviesFromGenre(Connection connection) {
        this.connection = connection;
    }

    public List<Movie> run(String genre, SharedQueryRequest args) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT title FROM film ")
            .append("LEFT JOIN film_category fc ON film.film_id = fc.film_id ")
            .append("LEFT JOIN category c on c.category_id = fc.category_id ")
            .append("WHERE c.name = ? ")
            .append(args.getOrderQuery("title"))
            .append(" ")
            .append(args.getLimitQuery());

        try {
            PreparedStatement statement = connection.prepareStatement(queryBuilder.toString());
            statement.setString(1, genre);
            args.injectLimitIntoQuery(statement, 2);
            ResultSet set = statement.executeQuery();

            List<Movie> result = new ArrayList<>();
            while (set.next()) {
                result.add(new Movie(set.getString("title")));
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
