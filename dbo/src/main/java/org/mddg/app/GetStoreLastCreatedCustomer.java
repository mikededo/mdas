package org.mddg.app;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import org.mddg.domain.Pair;
import org.mddg.domain.Store;
import org.mddg.domain.User;
import org.mddg.domain.request.SharedQueryRequest;
import org.mddg.domain.request.SharedQueryRequest.Order;

public class GetStoreLastCreatedCustomer {

    private Connection connection;

    public GetStoreLastCreatedCustomer(Connection connection) {
        this.connection = connection;
    }

    public List<Pair<Store, User>> run(SharedQueryRequest request) {
        if (!request.hasOrderSet()) {
            request.setOrder(Order.DESC);
        }
        // Ensure limit is always 1
        request.setLimit(1);

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT c.first_name, c.last_name, c.email, c.create_date, s.store_id ")
            .append("FROM customer c ")
            .append("JOIN store s ON c.store_id = s.store_id ")
            .append("WHERE c.customer_id = (")
            .append("SELECT customer_id FROM customer WHERE store_id = c.store_id ")
            .append(request.getOrderQuery("create_date"))
            .append(" ")
            .append(request.getLimitQuery())
            .append(")");

        try{
            PreparedStatement statement = connection.prepareStatement(queryBuilder.toString());
            request.injectLimitIntoQuery(statement, null);
            ResultSet set = statement.executeQuery();

            List<Pair<Store, User>> result = new ArrayList<>();
            while (set.next()) {
               result.add(Pair.of(
                   new Store(set.getInt("store_id")),
                   new User(
                       set.getString("first_name"),
                       set.getString("last_name"),
                       set.getString("email"),
                       set.getDate("create_date")
                   )
               ));
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
