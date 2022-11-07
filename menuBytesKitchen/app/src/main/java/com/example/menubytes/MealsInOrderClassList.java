package com.example.menubytes;

public class MealsInOrderClassList {

    private String OrderName;
    private String OrderQty;

    public MealsInOrderClassList() {

    }

    public MealsInOrderClassList(String orderQty,String orderName) {
        OrderName = orderName;
        OrderQty = orderQty;
    }

    public String getOrderName() {
        return OrderName;
    }

    public void setOrderName(String orderName) {
        OrderName = orderName;
    }


    public String getOrderQty() {
        return OrderQty;
    }

    public void setOrderQty(String orderQty) {
        OrderQty = orderQty;
    }

}
