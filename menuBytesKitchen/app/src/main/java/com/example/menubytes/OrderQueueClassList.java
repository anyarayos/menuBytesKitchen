package com.example.menubytes;

public class OrderQueueClassList {
    String OrderNum, TableNum, OrderQty, OrderTotalPrice, OrderStatus;

    public OrderQueueClassList() {
    }

    public OrderQueueClassList(String orderNum, String tableNum, String orderQty, String orderTotalPrice, String orderStatus) {
        OrderNum = orderNum;
        TableNum = tableNum;
        OrderQty = orderQty;
        OrderTotalPrice = orderTotalPrice;
        OrderStatus = orderStatus;
    }

    public OrderQueueClassList(String orderNum, String tableNum, String orderQty, String orderStatus) {
        OrderNum = orderNum;
        TableNum = tableNum;
        OrderQty = orderQty;
        OrderStatus = orderStatus;
    }



    public String getOrderNum() {
        return OrderNum;
    }

    public void setOrderNum(String orderNum) {
        OrderNum = orderNum;
    }

    public String getTableNum() {
        return TableNum;
    }

    public void setTableNum(String tableNum) {
        TableNum = tableNum;
    }

    public String getOrderQty() {
        return OrderQty;
    }

    public void setOrderQty(String orderQty) {
        OrderQty = orderQty;
    }

    public String getOrderTotalPrice() {
        return OrderTotalPrice;
    }

    public void setOrderTotalPrice(String orderTotalPrice) {
        OrderTotalPrice = orderTotalPrice;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }
}
