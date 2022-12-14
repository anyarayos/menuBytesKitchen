package com.example.menubytes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Task extends AsyncTask<String, String, Object> {

    public static final String UPDATE_REJECT_ORDER = "UPDATE_ORDER_REJECTED";
    public static final String VALIDATE_MANAGER_CREDS = "VALIDATEMANAGER" ;
    private AsyncResponse asyncResponse;
    private String method;
    private Connection connection;
    private PreparedStatement statement;
    private SqlStatements sqlStatements = new SqlStatements();
    private ResultSet resultSet;
    private String TAG = "TASK_DEBUG";
    public static String RETRIEVE_ALL_ORDERS = "retrieveAllOrders";
    public static String RETRIEVE_ORDER_ITEMS = "retrieveOrderItemsByID";
    public static String PREPARE_ORDER = "prepareOrder";
    public static String SERVE_ORDER = "acceptOrder";
    public static String REJECT_ORDER = "rejectOrder";
    public static final String CHECK_USER_NAME_EXISTENCE = "checkUsernameExistence";
    public static final String CHECK_USER_NAME_PASSWORD = "checkUsernamePassword";
    public static final String UPDATE_LOGIN_TIME = " updateLogInTime" ;
    private static final String UPDATE_LOGOUT_TIME = "updateLogOutTime";


    public Task(String method) {
        this.method = method;
    }

    public Task(String method, AsyncResponse asyncResponse) {
        this.method = method;
        this.asyncResponse = asyncResponse;
    }

    private void setConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://192.168.254.131:3306/menubytes", "admin", "admin");
        } catch (Exception e) {
            Log.i("DATABASE CONNECTION:", e.toString());
        }
    }

    public static  void disconnect(ResultSet rs, PreparedStatement stat, Connection cn){
        String TAG = "disconnect";
        try{
            if(rs!=null) rs.close();
        }catch(SQLException sqlEx){
            Log.d(TAG, "resultset disconnection error ");
        }
        try{
            if(stat!=null) stat.close();
        }catch(SQLException sqlEx){
            Log.d(TAG, "statement disconnection error ");

        }
        try{
            if(cn!=null) cn.close();
        }catch(SQLException sqlEx){
            Log.d(TAG, "connection disconnection error ");

        }
    }

    @SuppressLint("WrongThread")
    @Override
    protected Object doInBackground(String... params) {
        setConnection();
        if(connection!=null){try {
            if(connection!=null){
                if(method.equals(RETRIEVE_ALL_ORDERS)){
                    ArrayList<OrderQueueClassList> queueArrayList = new ArrayList<>();
                    statement = connection.prepareStatement(sqlStatements.getRetrieveAllOrders());
                    resultSet = statement.executeQuery();

                    if (!resultSet.isBeforeFirst()) {
                        Log.d(TAG, "RETRIEVE_ALL_ORDERS NO DATA FOUND");
                    } else {
                        Log.d(TAG, "RETRIEVE_ALL_ORDERS DATA FOUND");
                        while (resultSet.next()) {
                            queueArrayList.add(new OrderQueueClassList(
                                    resultSet.getString(1),
                                    resultSet.getString(2),
                                    resultSet.getString(3),
                                    resultSet.getString(4)));
                        }
                        return queueArrayList;
                    }
                }
                if(method.equals(RETRIEVE_ORDER_ITEMS)){
                    ArrayList<MealsInOrderClassList> mealInOrder = new ArrayList<>();
                    statement = connection.prepareStatement(sqlStatements.getRetrieveOrderItemsByID());
                    int user_id = Integer.valueOf(params[0]);
                    statement.setInt(1,user_id);
                    resultSet = statement.executeQuery();
                    if (!resultSet.isBeforeFirst()) {
                        Log.d(TAG, "RETRIEVE_ORDER_ITEMS NO DATA FOUND");
                    } else {
                        Log.d(TAG, "RETRIEVE_ORDER_ITEMS DATA FOUND");
                        while (resultSet.next()) {
                            mealInOrder.add(new MealsInOrderClassList(
                                    resultSet.getString(1),
                                    resultSet.getString(2),
                                    resultSet.getBoolean(3)
                            , resultSet.getString(4)));
                        }
                        return mealInOrder;
                    }
                }
                if(method.equals(PREPARE_ORDER)){
                    statement = connection.prepareStatement(sqlStatements.getPrepareOrder());
                    int order_id = Integer.valueOf(params[0]);
                    statement.setInt(1,order_id);
                    statement.executeUpdate();
                }
                if(method.equals(SERVE_ORDER)){
                    statement = connection.prepareStatement(sqlStatements.getServeOrder());
                    int order_id = Integer.valueOf(params[0]);
                    statement.setInt(1,order_id);
                    statement.executeUpdate();
                }
                if(method.equals(REJECT_ORDER)){
                    statement = connection.prepareStatement(sqlStatements.getRejectOrder());
                    int order_id = Integer.valueOf(params[0]);
                    statement.setInt(1,order_id);
                    statement.executeUpdate();
                }

                if(method.equals(UPDATE_REJECT_ORDER)){
                    statement = connection.prepareStatement(sqlStatements.getUpdateOrderRejected());
                    int order_id = Integer.valueOf(params[0]);
                    statement.setInt(1,order_id);
                    statement.executeUpdate();
                }

                if(method.equals(CHECK_USER_NAME_EXISTENCE)){
                    statement = connection.prepareStatement(sqlStatements.getCheckUsernameExistence());
                    String user_id = null;
                    String username = params[0];
                    statement.setString(1, username);

                    resultSet = statement.executeQuery();
                    if (!resultSet.isBeforeFirst()) {
                        Log.d(TAG, "CHECK_USER_NAME_EXISTENCE : NO DATA FOUND");
                    } else {
                        Log.d(TAG, "CHECK_USER_NAME_EXISTENCE: DATA FOUND");
                        while (resultSet.next()) {
                            user_id = resultSet.getString(1);
                        }
                        return user_id;
                    }
                }

                if(method.equals(CHECK_USER_NAME_PASSWORD)){
                    statement = connection.prepareStatement(sqlStatements.getCheckUsernamePassword());
                    String user_id = null;
                    String username = params[0];
                    String password = params[1];
                    statement.setString(1, username);
                    statement.setString(2, password);
                    resultSet = statement.executeQuery();
                    if (!resultSet.isBeforeFirst()) {
                        Log.d(TAG, "CHECK_USER_NAME_PASSWORD : NO DATA FOUND");
                    } else {
                        Log.d(TAG, "CHECK_USER_NAME_PASSWORD: DATA FOUND");
                        while (resultSet.next()) {
                            user_id = resultSet.getString(1);
                        }
                        return user_id;
                    }
                }

                if(method.equals(UPDATE_LOGIN_TIME)){
                    statement = connection.prepareStatement(sqlStatements.getUpdateLogInTime());
                    String user_id = params[0];
                    statement.setString(1,user_id);
                    statement.executeUpdate();
                }

                if(method.equals(UPDATE_LOGOUT_TIME)){
                    statement = connection.prepareStatement(sqlStatements.getUpdateLogOutTime());
                    String user_id = params[0];
                    statement.setString(1,user_id);
                    statement.executeUpdate();
                }

                if(method.equals(VALIDATE_MANAGER_CREDS)){
                    statement = connection.prepareStatement(sqlStatements.getValidateManager());
                    String user_id = null;
                    String username = params[0];
                    String password = params[1];
                    statement.setString(1, username);
                    statement.setString(2, password);
                    resultSet = statement.executeQuery();
                    if (!resultSet.isBeforeFirst()) {
                        Log.d(TAG, "CHECK_USER_NAME_PASSWORD : NO DATA FOUND");
                    } else {
                        Log.d(TAG, "CHECK_USER_NAME_PASSWORD : DATA FOUND");
                        while (resultSet.next()) {
                            user_id = resultSet.getString(1);
                        }
                        return user_id;
                    }
                }

                disconnect(resultSet,statement,connection);}
        }
        catch (SQLException e) {
            e.printStackTrace();
        }}

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        try {
            super.onPostExecute(o);
            if(o!=null){
                asyncResponse.onFinish(o);
            }else{asyncResponse.onFinish(null);}
        }
        catch (Exception e){
            Log.i("onPostExecute", e.toString());
        }
    }

}
