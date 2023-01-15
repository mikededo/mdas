package org.mddg.app;

import java.sql.*;
import org.mddg.domain.request.CustomerFieldsRequest;

public class CreateStoreCustomer {

    private final Connection connection;

    public CreateStoreCustomer(Connection connection) {
        this.connection = connection;
    }

    public Integer run(CustomerFieldsRequest request) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("INSERT INTO customer ")
            .append(request.getQuery());

        try {
            PreparedStatement statement = connection.prepareStatement(
                queryBuilder.toString(), Statement.RETURN_GENERATED_KEYS
            );
            request.injectValuesIntoStatement(statement, true);

            int newRows = statement.executeUpdate();
            if (newRows > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
