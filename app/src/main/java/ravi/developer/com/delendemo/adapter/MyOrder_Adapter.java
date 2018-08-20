package ravi.developer.com.delendemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import ravi.developer.com.delendemo.R;
import ravi.developer.com.delendemo.activity.OrderDescriptionActivity;
import ravi.developer.com.delendemo.model.Order;

public class MyOrder_Adapter extends RecyclerView.Adapter<MyOrder_Adapter.MyViewHolder> {
    Context context;
    ArrayList<Order> orderArrayList;

    public MyOrder_Adapter(Context context, ArrayList<Order> orderArrayList) {
        this.context = context;
        this.orderArrayList = orderArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_cell_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.date.setText(orderArrayList.get(position).getDate());
        holder.serviceType.setText(orderArrayList.get(position).getServiceType());
        holder.status.setText(orderArrayList.get(position).getStatus());
        holder.orderId.setText(orderArrayList.get(position).getOrderID());
        if(!orderArrayList.get(position).getDp().isEmpty())
        {
            Picasso.get().load(orderArrayList.get(position).getDp()).into(holder.circleImageView);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context,OrderDescriptionActivity.class);

                i.putExtra("orderkey",orderArrayList.get(position));

                context.startActivity(i);
                ((AppCompatActivity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView circleImageView;
        TextView orderId, status, date, serviceType;
        public MyViewHolder(View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.order_cell_dp);
            orderId = itemView.findViewById(R.id.order_cell_orderno);
            status = itemView.findViewById(R.id.order_cell_status);
            date = itemView.findViewById(R.id.order_cell_date);
            serviceType = itemView.findViewById(R.id.order_cell_servicetype);
        }
    }
}
