package com.example.menubytes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private Timer autoUpdate;

    String userManagerTemp, passMangerTemp;

    ListView pendingListView;
    ListView mealInOrderListView;
    ArrayList<OrderQueueClassList> queueArrayList = new ArrayList<>();
    ArrayList<MealsInOrderClassList> mealInOrder = new ArrayList<>();
    OrderQueueAdapter orderQueueAdapter;
    MealsInOrderClassAdapter mealsInOrderAdapter;
    Button prepareBtn, sendBtn, rejectBtn;
    TextView orderNumTxt,tableNumTxt;
    TextView orderStatusTxt;

    String ORDER_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        Bundle intentExtras = intent.getExtras();
        String user_id = intentExtras.getString("user_id");

        Task updateLoginTime = new Task(Task.UPDATE_LOGIN_TIME);
        updateLoginTime.execute(user_id);

        prepareBtn = findViewById(R.id.buttonPrepareThis);
        sendBtn = findViewById(R.id.buttonServeThis);
        rejectBtn = findViewById(R.id.buttonRejectThis);

        pendingListView = findViewById(R.id.incomingOrderLV);
        mealInOrderListView = findViewById(R.id.mealsInOrderLV);

        orderNumTxt = findViewById(R.id.txtViewOrderNum);
        tableNumTxt = findViewById(R.id.txtViewTableNum);
        orderStatusTxt = findViewById(R.id.txtViewOrderStatus);;

        Task retrieveAllOrders = new Task(Task.RETRIEVE_ALL_ORDERS, new AsyncResponse() {
            @Override
            public void onFinish(Object output) {
                if(output==null){
                    queueArrayList.clear();
                    orderQueueAdapter = new OrderQueueAdapter(MainActivity.this,R.layout.pending_list,queueArrayList);
                    pendingListView.setAdapter(orderQueueAdapter);
                }
                if(output!=null){
                    queueArrayList = (ArrayList<OrderQueueClassList>) output;
                    orderQueueAdapter = new OrderQueueAdapter(MainActivity.this,R.layout.pending_list,queueArrayList);
                    pendingListView.setAdapter(orderQueueAdapter);
                }
            }
        });
        retrieveAllOrders.execute();


        pendingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*return ArrayList<MealsInOrderClassList> mealInOrder*/

                String order_id = queueArrayList.get(position).getOrderNum();
                ORDER_ID = order_id;
                //Toast.makeText(MainActivity.this, order_id, Toast.LENGTH_SHORT).show();
                Task retrieveOrderItems = new Task(Task.RETRIEVE_ORDER_ITEMS, new AsyncResponse() {
                    @Override
                    public void onFinish(Object output) {
                        if(output==null){
                            mealInOrder.clear();
                            mealsInOrderAdapter = new MealsInOrderClassAdapter(MainActivity.this,R.layout.meals_order_list,mealInOrder);
                            mealInOrderListView.setAdapter(mealsInOrderAdapter);
                        }
                        if(output!=null){
                            mealInOrder = (ArrayList<MealsInOrderClassList>) output;
                            mealsInOrderAdapter = new MealsInOrderClassAdapter(MainActivity.this,R.layout.meals_order_list,mealInOrder);
                            mealInOrderListView.setAdapter(mealsInOrderAdapter);
                        }
                    }
                });
                retrieveOrderItems.execute(order_id);
                orderNumTxt.setText(queueArrayList.get(position).getOrderNum());
                tableNumTxt.setText(queueArrayList.get(position).getTableNum());
                orderStatusTxt.setText(queueArrayList.get(position).getOrderStatus());
            }
        });

        prepareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ORDER_ID = orderNumTxt.getText().toString();
                mealsInOrderAdapter = new MealsInOrderClassAdapter(MainActivity.this,R.layout.meals_order_list,mealInOrder);
                mealInOrderListView.setAdapter(mealsInOrderAdapter);
                if (ORDER_ID.equals("")) {
                    Toast.makeText(MainActivity.this, "No Order Found!", Toast.LENGTH_SHORT).show();
                } else {
                    Task prepareOrder = new Task(Task.PREPARE_ORDER);
                    prepareOrder.execute(ORDER_ID);
                    orderStatusTxt.setText("PREPARING");
                }
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealInOrder.clear();
                ORDER_ID = orderNumTxt.getText().toString();
                mealsInOrderAdapter = new MealsInOrderClassAdapter(MainActivity.this,R.layout.meals_order_list,mealInOrder);
                mealInOrderListView.setAdapter(mealsInOrderAdapter);
                if (ORDER_ID.equals("")) {
                    Toast.makeText(MainActivity.this, "No Order Found!", Toast.LENGTH_SHORT).show();
                } else {
                    Task serveOrder = new Task(Task.SERVE_ORDER);
                    serveOrder.execute(ORDER_ID);
                    orderNumTxt.setText(null);
                    tableNumTxt.setText(null);
                    orderStatusTxt.setText(null);
                }
            }
        });

        Dialog voidDialog;
        voidDialog = new Dialog(this);
        voidDialog.setContentView(R.layout.dialog_void);
        voidDialog.getWindow().setBackgroundDrawable(this.getDrawable(R.drawable.dialog_background));
        //voidDialog.setCancelable(false);
        voidDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        //USERNAME AND PASSWORD INSTANTIATE
        EditText editTextUserManager = voidDialog.findViewById(R.id.userNameManager);
        EditText editTextPassManager = voidDialog.findViewById(R.id.passwordManager);

        rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealInOrder.clear();
                ORDER_ID = orderNumTxt.getText().toString();
                mealsInOrderAdapter = new MealsInOrderClassAdapter(MainActivity.this,R.layout.meals_order_list,mealInOrder);
                mealInOrderListView.setAdapter(mealsInOrderAdapter);
                if (ORDER_ID.equals("")) {
                    Toast.makeText(MainActivity.this, "No Order Found!", Toast.LENGTH_SHORT).show();
                } else {
                    //INSERT NOTIFY MANAGER CODES HERE
                    voidDialog.show();
                }

            }
        });

        Button dialogButtonVoid = voidDialog.findViewById(R.id.voidButton);
        dialogButtonVoid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userManagerTemp = editTextUserManager.getText().toString();
                passMangerTemp = editTextPassManager.getText().toString();

                //SETTING TEMPORARY CONDITION. SHOULD BE NOT HARD CODED
                /*TODO: user manager*/
                if(!userManagerTemp.equals("") && !passMangerTemp.equals("")){
                    Task validateManagerCreds = new Task(Task.VALIDATE_MANAGER_CREDS, new AsyncResponse() {
                        @Override
                        public void onFinish(Object output) {
                            if(output!=null){
                                Task rejectOrder = new Task(Task.REJECT_ORDER);
                                rejectOrder.execute(ORDER_ID);
                                Task rejectUpdateorder = new Task(Task.UPDATE_REJECT_ORDER);
                                rejectUpdateorder.execute(ORDER_ID);
                                orderNumTxt.setText(null);
                                tableNumTxt.setText(null);
                                orderStatusTxt.setText(null);
                                voidDialog.dismiss();
                            }else{
                                Toast.makeText(MainActivity.this, "Manager Verification failed. Void Request unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });validateManagerCreds.execute(userManagerTemp,passMangerTemp);
                }else{
                    Toast.makeText(MainActivity.this, "Please enter username and password!", Toast.LENGTH_SHORT).show();
                }

//                if (userManagerTemp.equals("manager") && passMangerTemp.equals("manager")) {
//                    Task rejectOrder = new Task(Task.REJECT_ORDER);
//                    rejectOrder.execute(ORDER_ID);
//                    Task rejectUpdateorder = new Task(Task.UPDATE_REJECT_ORDER);
//                    rejectUpdateorder.execute(ORDER_ID);
//                    orderNumTxt.setText(null);
//                    tableNumTxt.setText(null);
//                    orderStatusTxt.setText(null);
//                    voidDialog.dismiss();
//                }
//                else {
//                    Toast.makeText(MainActivity.this, "Manager Verification failed. Void Request unsuccessful", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        autoUpdate = new Timer();
        autoUpdate.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        update();
                    }
                });
            }
        }, 0, 3000); // updates each 3 secs
    }

    private void update(){
        //Toast.makeText(this, "refreshed", Toast.LENGTH_SHORT).show();
        Task retrieveAllOrders = new Task(Task.RETRIEVE_ALL_ORDERS, new AsyncResponse() {
            @Override
            public void onFinish(Object output) {
                if(output==null){
                    queueArrayList.clear();
                    orderQueueAdapter = new OrderQueueAdapter(MainActivity.this,R.layout.pending_list,queueArrayList);
                    pendingListView.setAdapter(orderQueueAdapter);
                    mealInOrder.clear();
                    mealsInOrderAdapter = new MealsInOrderClassAdapter(MainActivity.this,R.layout.meals_order_list,mealInOrder);
                    mealInOrderListView.setAdapter(mealsInOrderAdapter);
                }
                if(output!=null){
                    queueArrayList = (ArrayList<OrderQueueClassList>) output;
                    orderQueueAdapter = new OrderQueueAdapter(MainActivity.this,R.layout.pending_list,queueArrayList);
                    pendingListView.setAdapter(orderQueueAdapter);
                }
            }
        });
        retrieveAllOrders.execute();
    }

    @Override
    public void onPause() {
        autoUpdate.cancel();
        super.onPause();
    }
}