package ravi.developer.com.delendemo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import ravi.developer.com.delendemo.R;
import ravi.developer.com.delendemo.model.Order;

public class OrderDescriptionActivity extends AppCompatActivity {

    private CircleImageView imageView;
    private Button cancel_btn;
    private TextView status, serviceman_name,date,chargelabel,chargedata,gstlabel,gstdata,totallabel,totaldata,servicereq,address;
    private Order order;
    private FirebaseFirestore db;
    private final String TAG = "OrderDesc : ";
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_description_activity);
        db = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(OrderDescriptionActivity.this);
        progressDialog.setMessage("Please Wait..");
        init();

        order= (Order) getIntent().getParcelableExtra("orderkey");
        setTitle(order.getServiceType()+" - "+order.getOrderID());
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        status.setText(order.getStatus());
        date.setText(order.getDate());
        servicereq.setText(order.getServiceRequired());
        address.setText(order.getAddress());
        if(order.getStatus().equalsIgnoreCase("pending"))
        {
            cancel_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressDialog.show();
                    db.collection("order")
                            .document(order.getDocumentId())
                            .update(getString(R.string.status),"Cancelled")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        status.setText("Cancelled");
                                        cancel_btn.setVisibility(View.GONE);
                                        progressDialog.dismiss();

                                    } else {
                                        Log.w(TAG, "Error getting documents.", task.getException());
                                        progressDialog.dismiss();
                                    }
                                }
                            });
                }
            });
            chargedata.setVisibility(View.GONE);
            chargelabel.setVisibility(View.GONE);
            gstdata.setVisibility(View.GONE);
            gstlabel.setVisibility(View.GONE);
            totaldata.setVisibility(View.GONE);
            totallabel.setVisibility(View.GONE);
        }
        else  if(order.getStatus().equalsIgnoreCase("cancelled"))
        {
            cancel_btn.setVisibility(View.GONE);
            chargedata.setVisibility(View.GONE);
            chargelabel.setVisibility(View.GONE);
            gstdata.setVisibility(View.GONE);
            gstlabel.setVisibility(View.GONE);
            totaldata.setVisibility(View.GONE);
            totallabel.setVisibility(View.GONE);
        }
        else
        {
            cancel_btn.setVisibility(View.GONE);
            chargedata.setText(order.getCharges());
            String gstAmount = String.valueOf((Float.valueOf(order.getCharges()) * 0.18));
            gstdata.setText(gstAmount);
            String totalAmount = String.valueOf((Float.valueOf(order.getCharges()) + Float.valueOf(gstAmount)));
            totaldata.setText(totalAmount);
            serviceman_name.setText(order.getServiceManName());
            Picasso.get().load(order.getDp()).into(imageView);
        }
    }

    private void init()
    {
        imageView = findViewById(R.id.orderdesc_dp);
        cancel_btn = findViewById(R.id.orderdesc_cancel_btn);
        status = findViewById(R.id.orderdesc_status);
        serviceman_name = findViewById(R.id.orderdesc_serviceman_name);
        date = findViewById(R.id.orderdesc_date_data);
        chargelabel = findViewById(R.id.orderdesc_charge_label);
        chargedata = findViewById(R.id.orderdesc_charge_data);
        gstlabel = findViewById(R.id.orderdesc_gst_label);
        gstdata = findViewById(R.id.orderdesc_gst_data);
        totallabel = findViewById(R.id.orderdesc_total_label);
        totaldata = findViewById(R.id.orderdesc_total_datat);
        servicereq = findViewById(R.id.orderdesc_servicereq);
        address = findViewById(R.id.orderdesc_address);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(OrderDescriptionActivity.this,MyOrdersActivity.class));
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(OrderDescriptionActivity.this,MyOrdersActivity.class));
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
