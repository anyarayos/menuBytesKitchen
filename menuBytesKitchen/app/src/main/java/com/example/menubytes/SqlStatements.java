package com.example.menubytes;

public class SqlStatements {
    private String retrieveAllOrders = "SELECT \n" +
            "orders.order_id, orders.created_by,orderitems.qty,order_status.order_status\n" +
            "FROM orders\n" +
            "JOIN\n" +
            "(SELECT order_id, SUM(quantity) AS qty FROM order_items WHERE order_items.product_id != (15) GROUP BY order_id)\n" +
            "AS orderitems ON orderitems.order_id = orders.order_id\n" +
            "INNER JOIN\n" +
            "order_status ON order_status.order_id = orders.order_id\n" +
            "INNER JOIN\n" +
            "user on user.user_id = user.user_id\n" +
            "LEFT JOIN\n" +
            "payment ON payment.created_by = orders.created_by\n" +
            "WHERE order_status != \"REJECTED\" AND order_status != \"COMPLETED\"\n" +
            "AND EXISTS (SELECT user_name FROM user WHERE user.user_name = orders.created_by)\n" +
            "AND DATE(orders.created_at) = curdate() \n" +
            "GROUP BY orders.order_id;";
    private String retrieveOrderItemsByID = "\n" +
            "SELECT order_items.quantity, \n" +
            "IF((order_items.product_bundle),CONCAT(\"B1G1\",product.product_name),product.product_name) AS name, " +
            "order_items.has_addons,order_items.flavors\n" +
            "FROM order_items\n" +
            "INNER JOIN\n" +
            "product ON order_items.product_id = product.product_id\n" +
            "INNER JOIN\n" +
            "orders ON order_items.order_id = orders.order_id\n" +
            "WHERE order_items.order_id = (?) AND DATE(orders.created_at) = curdate()\n" +
            "AND product_name != \"Shawarma All Meat\"";
    private String serveOrder = "UPDATE order_status\n" +
            "SET order_status = (\"COMPLETED\"),\n" +
            "updated_at = current_timestamp(),\n" +
            "updated_by = \"kitchen\"\n" +
            "WHERE order_id = (?);";
    private String prepareOrder = "UPDATE order_status\n" +
            "SET order_status = (\"PREPARING\"),\n" +
            "updated_at = current_timestamp(),\n" +
            "updated_by = \"kitchen\"\n" +
            "WHERE order_id = (?);";
    private String rejectOrder = "UPDATE order_status\n" +
            "SET order_status = (\"REJECTED\"),\n" +
            "updated_at = current_timestamp(),\n" +
            "updated_by = \"kitchen\"\n" +
            "WHERE order_id = (?);";
    private String updateOrderRejected = "UPDATE orders SET created_by = CONCAT(created_by,\"_\") WHERE order_id=(?)";

    public String getUpdateOrderRejected() {
        return updateOrderRejected;
    }

    private String checkUsernameExistence = "SELECT user_id FROM user WHERE (user_name = (?) and user_type = 'kitchen');";

    private String checkUsernamePassword = "SELECT user_id FROM user WHERE user_name = (?) AND password = (?);";

    private String updateLogInTime = "UPDATE user\n" +
            "SET log_in = current_timestamp()\n" +
            "WHERE user_id = (?)\n" +
            ";";
    private String updateLogOutTime = "UPDATE user\n" +
            "SET log_out = current_timestamp()\n" +
            "WHERE user_id = (?)\n" +
            ";";

    private String validateManager = "SELECT user_id FROM menubytes WHERE user_name = (?) AND password = (?) AND user_type = \"manager\";";

    public String getValidateManager() {
        return validateManager;
    }

    public String getCheckUsernameExistence() {
        return checkUsernameExistence;
    }

    public String getCheckUsernamePassword() {
        return checkUsernamePassword;
    }

    public String getUpdateLogInTime() {
        return updateLogInTime;
    }

    public String getUpdateLogOutTime() {
        return updateLogOutTime;
    }

    public String getRejectOrder() {
        return rejectOrder;
    }

    public String getRetrieveAllOrders() {
        return retrieveAllOrders;
    }

    public String getRetrieveOrderItemsByID() {
        return retrieveOrderItemsByID;
    }

    public String getServeOrder() {
        return serveOrder;
    }

    public String getPrepareOrder() {
        return prepareOrder;
    }
}
