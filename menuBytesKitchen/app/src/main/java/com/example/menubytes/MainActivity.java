package com.example.menubytes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private Timer autoUpdate;

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

        prepareBtn = findViewById(R.id.buttonPrepareThis);
        sendBtn = findViewById(R.id.buttonServeThis);
        rejectBtn = findViewById(R.id.buttonRejectThis);

        pendingListView = findViewById(R.id.incomingOrderLV);
        mealInOrderListView = findViewById(R.id.mealsInOrderLV);

        orderNumTxt = findViewById(R.id.txtViewOrderNum);
        tableNumTxt = findViewById(R.id.txtViewTableNum);
        orderStatusTxt = findViewById(R.id.txtViewOrderStatus);;


        //dummy data for queue
        //queueArrayList.add(new OrderQueueClassList("001","01","3","IN QUEUE"));
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
                Toast.makeText(MainActivity.this, order_id, Toast.LENGTH_SHORT).show();
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
                if(ORDER_ID!=null){
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
                if(ORDER_ID!=null){
                    Task serveOrder = new Task(Task.SERVE_ORDER);
                    serveOrder.execute(ORDER_ID);
                    orderNumTxt.setText(null);
                    tableNumTxt.setText(null);
                    orderStatusTxt.setText(null);
                }
            }
        });

        rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealInOrder.clear();
                ORDER_ID = orderNumTxt.getText().toString();
                mealsInOrderAdapter = new MealsInOrderClassAdapter(MainActivity.this,R.layout.meals_order_list,mealInOrder);
                mealInOrderListView.setAdapter(mealsInOrderAdapter);
                if(ORDER_ID!=null){
                    //Task prepareOrder = new Task(Task.PREPARE_ORDER);
                    //prepareOrder.execute(ORDER_ID);
                    orderNumTxt.setText(null);
                    tableNumTxt.setText(null);
                    orderStatusTxt.setText(null);
                }
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
        Toast.makeText(this, "refreshed", Toast.LENGTH_SHORT).show();
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