package org.mddg.domain.request;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerFieldsRequest extends Request {

    private String firstName;
    private String lastName;
    private String email;
    private Integer addressId;
    private Integer storeId;

    public boolean isFirstNameParam(String param) {
        return super.isParam(param, "--first-name=", "-f=");
    }

    public boolean isLastNameParam(String param) {
        return super.isParam(param, "--last-name=", "-l=");
    }

    public boolean isEmailParam(String param) {
        return super.isParam(param, "--email=", "-e=");
    }

    public boolean isAddressParam(String param) {
        return super.isParam(param, "--address-id=", "-a");
    }

    public boolean isStoreParam(String param) {
        return super.isParam(param, "--store-id=", "-s=");
    }

    // Expects the param to be a valid first name parameter
    public void setFirstNameParam(String param) {
        this.firstName = param.split("=")[1];
    }

    // Expects the param to be a valid last name parameter
    public void setLastNameParam(String param) {
        this.lastName = param.split("=")[1];
    }

    // Expects the param to be a valid email parameter
    public void setEmailParam(String param) {
        this.email = param.split("=")[1];
    }

    // Expects the param to be a valid email parameter
    public void setAddressParam(String param) {
        this.addressId = Integer.valueOf(param.split("=")[1]);
    }

    // Expects the param to be a valid email parameter
    public void setStoreParam(String param) {
        this.storeId = Integer.valueOf(param.split("=")[1]);
    }

    public boolean hasAnyValue() {
        return firstName != null ||
            lastName != null ||
            email != null ||
            addressId != null ||
            storeId != null;
    }

    public String getQuery() {
        return "(first_name, last_name, email, address_id, store_id)" +
            " VALUES " +
            "(?, ?, ?, ?, ?)";
    }

    public String getUpdateQuery() {
        StringBuilder builder = new StringBuilder("SET ");
        List<String> available = new ArrayList<>(
            List.of(
                firstName != null ? "first_name = ?" : "",
                lastName != null ? "last_name = ?" : "",
                email != null ? "email = ?" : "",
                addressId != null ? "address_id = ?" : "",
                storeId != null ? "store_id = ?" : ""
            )
        ).stream().filter(s -> !s.equals("")).collect(Collectors.toList());
        builder.append(String.join(", ", available));

        return builder.toString();
    }

    public int injectValuesIntoStatement(
        PreparedStatement statement, boolean nullIfEmpty
    ) throws SQLException {
        int counter = 1;

        if (maybeInjectStringValueInPosition(statement, nullIfEmpty, counter, firstName)) {
            counter++;
        }
        if (maybeInjectStringValueInPosition(statement, nullIfEmpty, counter, lastName)) {
            counter++;
        }
        if (maybeInjectStringValueInPosition(statement, nullIfEmpty, counter, email)) {
            counter++;
        }
        if (maybeInjectIntegerValueInPosition(statement, nullIfEmpty, counter, addressId)) {
            counter++;
        }
        if (maybeInjectIntegerValueInPosition(statement, nullIfEmpty, counter, storeId)) {
            counter++;
        }

        return counter;
    }

    private boolean maybeInjectStringValueInPosition(
        PreparedStatement statement, boolean nullIfEmpty, int position, String value
    ) throws SQLException {
        if (value != null) {
            statement.setString(position, value);
            return true;
        } else if (nullIfEmpty) {
            statement.setNull(position, Types.NULL);
            return true;
        }
        return false;
    }

    private boolean maybeInjectIntegerValueInPosition(
        PreparedStatement statement, boolean nullIfEmpty, int position, Integer value
    ) throws SQLException {
        if (value != null) {
            statement.setInt(position, value);
            return true;
        } else if (nullIfEmpty) {
            statement.setNull(position, Types.NULL);
            return true;
        }
        return false;
    }
}
