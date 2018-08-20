package ravi.developer.com.delendemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ravi.developer.com.delendemo.R;

public class DashBoardActivity extends AppCompatActivity {

    private Button myOrders, requestService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_board_activity);
        myOrders = findViewById(R.id.dashboard_my_orders);
        requestService = findViewById(R.id.dashboard_request_service);
        myOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoardActivity.this, MyOrdersActivity.class));
            }
        });
        requestService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoardActivity.this, CreateOrderActivity.class));
            }
        });
    }
}
