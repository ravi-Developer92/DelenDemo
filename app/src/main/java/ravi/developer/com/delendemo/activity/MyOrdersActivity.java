package ravi.developer.com.delendemo.activity;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import ravi.developer.com.delendemo.adapter.MyOrder_Adapter;
import ravi.developer.com.delendemo.R;
import ravi.developer.com.delendemo.model.Order;

public class MyOrdersActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private final String TAG = "MyOrderActivity :";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MyOrder_Adapter orderAdapter;
    private ProgressDialog progressDialog;
    private Order order;
    private ArrayList<Order> orderArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_orders_activity);
        recyclerView = findViewById(R.id.myorder_recyclerview);
        layoutManager = new LinearLayoutManager(MyOrdersActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        progressDialog = new ProgressDialog(MyOrdersActivity.this);
        progressDialog.setMessage("Please Wait..");

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        db = FirebaseFirestore.getInstance();
        progressDialog.show();

        db.collection("order")
                //.document("7jAjbodYbLPN4BfzpTTX")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            orderArrayList = new ArrayList<>();
                           for(DocumentSnapshot document : task.getResult()) {
                               Log.d(TAG, document.getId() + " => " + document.getData());
                               order = new Order(document.get(getString(R.string.orderid)).toString(),document.get(getString(R.string.servicetype)).toString(),
                                       document.get(getString(R.string.date)).toString(),document.get(getString(R.string.charges)).toString(),
                                       document.get(getString(R.string.servicereq)).toString(),document.get(getString(R.string.status)).toString(),
                                       document.get(getString(R.string.servicemanname)).toString(),document.get(getString(R.string.sm_dp)).toString(),
                                       document.get(getString(R.string.address)).toString(),document.getId());
                               orderArrayList.add(order);
                           }
                           orderAdapter = new MyOrder_Adapter(MyOrdersActivity.this,orderArrayList);
                           recyclerView.setAdapter(orderAdapter);
                                progressDialog.dismiss();

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
