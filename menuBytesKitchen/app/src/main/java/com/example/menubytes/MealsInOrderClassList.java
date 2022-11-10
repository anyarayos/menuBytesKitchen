package com.example.menubytes;

public class MealsInOrderClassList {

    private String OrderName;
    private String OrderQty;
    private boolean has_addons;

    public MealsInOrderClassList() {

    }

    public MealsInOrderClassList(String orderQty,String orderName) {
        OrderName = orderName;
        OrderQty = orderQty;
    }

    public MealsInOrderClassList(String orderQty, String orderName,  boolean has_addons) {
        OrderName = orderName;
        OrderQty = orderQty;
        this.has_addons = has_addons;
    }

    public boolean isHas_addons() {
        return has_addons;
    }

    public void setHas_addons(boolean has_addons) {
        this.has_addons = has_addons;
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
