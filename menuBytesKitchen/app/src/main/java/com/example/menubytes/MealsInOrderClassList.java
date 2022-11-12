package com.example.menubytes;

public class MealsInOrderClassList {

    private String OrderName;
    private String OrderQty;
    private boolean has_addons;
    private String flavors;

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

    public MealsInOrderClassList(String orderQty, String orderName,  boolean has_addons, String flavors) {
        OrderName = orderName;
        OrderQty = orderQty;
        this.has_addons = has_addons;
        this.flavors = flavors;
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

    public String getFlavors() {
        return flavors;
    }

    public void setFlavors(String flavors) {
        this.flavors = flavors;
    }
}
