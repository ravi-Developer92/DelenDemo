package ravi.developer.com.delendemo.activity;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import ravi.developer.com.delendemo.R;

public class CreateOrderActivity extends AppCompatActivity {

    private EditText serviceRequired_et, address_et;
    private Button submit;
    private Spinner spinner;
    private FirebaseFirestore db;
    SimpleDateFormat sdf;
    ProgressDialog progressDialog;
    private final String TAG = "CreateOrderActivity :";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_order_activity);
        spinner = findViewById(R.id.create_order_spinner);
        sdf = new SimpleDateFormat("E, MM/dd/yy - hh:mm");
        serviceRequired_et = findViewById(R.id.create_order_service_required_et);
        address_et = findViewById(R.id.create_order_address_et);
        submit = findViewById(R.id.create_order_submit_btn);
        setTitle("Create Order");
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        db = FirebaseFirestore.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!address_et.getText().toString().isEmpty() && !serviceRequired_et.getText().toString().isEmpty())
                {
                    progressDialog = new ProgressDialog(CreateOrderActivity.this);
                    progressDialog.setMessage("Please Wait..");
                    progressDialog.show();
                    Map<String, Object> order = new HashMap<>();
                    order.put(getString(R.string.orderid), generateOrderID());
                    order.put(getString(R.string.date), sdf.format(new Date()));
                    order.put(getString(R.string.servicetype), spinner.getSelectedItem().toString());
                    order.put(getString(R.string.status), "Pending");
                    order.put(getString(R.string.address), address_et.getText().toString());
                    order.put(getString(R.string.servicereq), serviceRequired_et.getText().toString());
                    order.put(getString(R.string.servicemanname), "");
                    order.put(getString(R.string.charges), "");
                    order.put(getString(R.string.sm_dp), "");

                    db.collection("order")
                            .add(order)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    progressDialog.dismiss();
                                    Toast.makeText(CreateOrderActivity.this,"Order Successful", Toast.LENGTH_LONG).show();
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(CreateOrderActivity.this,"Order Failed", Toast.LENGTH_LONG).show();
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });
                }
                else
                    Toast.makeText(CreateOrderActivity.this,"Enter all fields!!", Toast.LENGTH_LONG).show();
            }
        });

    }

    protected String generateOrderID() {
        String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder temp = new StringBuilder();
        Random rnd = new Random();
        while (temp.length() < 8) { // length of the random string.
            int index = (int) (rnd.nextFloat() * CHARS.length());
            temp.append(CHARS.charAt(index));
        }
        String orderID = temp.toString();
        return orderID;

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
