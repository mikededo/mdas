package org.mddg.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.mddg.domain.request.CustomerFieldsRequest;

public class UpdateCustomer {

    private final Connection connection;

    public UpdateCustomer(Connection connection) {
        this.connection = connection;
    }

    public void run(CustomerFieldsRequest request, Integer customerId) {
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE customer ")
            .append(request.getUpdateQuery())
            .append(" WHERE customer_id = ?");

        try {
            PreparedStatement statement = connection.prepareStatement(builder.toString());
            int injected = request.injectValuesIntoStatement(statement, false);
            statement.setInt(injected, customerId);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
