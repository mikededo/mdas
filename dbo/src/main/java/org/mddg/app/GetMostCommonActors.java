package org.mddg.app;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.mddg.domain.Actor;
import org.mddg.domain.request.SharedQueryRequest;
import org.mddg.domain.request.SharedQueryRequest.Order;

public class GetMostCommonActors {

    private final Connection connection;

    public GetMostCommonActors(Connection connection) {
        this.connection = connection;
    }

    public List<Actor> run(SharedQueryRequest args) {
        if (!args.hasOrderSet()) {
            args.setOrder(Order.DESC);
        }

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT first_name, last_name, count(*) as count ")
            .append("FROM actor ")
            .append("JOIN film_actor ON actor.actor_id = film_actor.actor_id ")
            .append("GROUP BY actor.actor_id ")
            .append(args.getOrderQuery("count"))
            .append(" ")
            .append(args.getLimitQuery());

        try {
            PreparedStatement statement = connection.prepareStatement(queryBuilder.toString());
            args.injectLimitIntoQuery(statement, null);
            ResultSet set = statement.executeQuery();

            List<Actor> result = new ArrayList<>();
            while (set.next()) {
                result.add(new Actor(
                    set.getString("first_name"), set.getString("last_name"), set.getInt("count")
                ));
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
