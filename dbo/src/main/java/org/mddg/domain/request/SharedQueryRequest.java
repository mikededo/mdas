package org.mddg.domain.request;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SharedQueryRequest extends Request {

    private Integer limit;
    private Order order;

    public boolean isLimitParam(String param) {
        return super.isParam(param, "--limit=", "-l=");
    }

    public boolean isOrderParam(String param) {
        return super.isParam(param, "--order=", "-o=");
    }

    // Expects the param to be a valid order parameter
    public void setLimitParam(String param) {
        this.limit = Integer.valueOf(param.split("=")[1]);
    }

    // Expects the param to be a valid order parameter
    public void setOrderParam(String param) {
        this.order = param.split("=")[1].equals("DESC") ? Order.DESC : Order.ASC;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getLimitQuery() {
        if (this.limit == null || this.limit.equals(0)) {
            return "";
        }

        return "LIMIT ?";
    }

    public void injectLimitIntoQuery(PreparedStatement statement, Integer position)
        throws SQLException {
        if (this.limit != null) {
            statement.setInt(position == null ? 1 : position, this.limit);
        }
    }

    public String getOrderQuery(String... fields) {
        if (this.order == null) {
            return "";
        }

        return "ORDER BY "
            + String.join(", ", fields)
            + (fields.length > 0 ? " " : "")
            + this.order;
    }

    public boolean hasLimitSet() {
        return this.limit != null;
    }

    public boolean hasOrderSet() {
        return this.order != null;
    }

    public boolean isOrderASC() {
        return this.order != null && this.order.equals(Order.ASC);
    }

    public static enum Order {
        ASC,
        DESC
    }
}

