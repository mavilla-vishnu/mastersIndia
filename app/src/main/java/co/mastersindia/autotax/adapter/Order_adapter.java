package co.mastersindia.autotax.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import co.mastersindia.autotax.R;
import co.mastersindia.autotax.model.Order_model;

public class Order_adapter extends RecyclerView.Adapter<Order_adapter.MyViewHolder> {

    private List<Order_model> orderList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView iname,date,qty;

        public MyViewHolder(View view) {
            super(view);
            iname = (TextView) view.findViewById(R.id.order_title);
            date = (TextView) view.findViewById(R.id.order_date);
            qty = (TextView) view.findViewById(R.id.order_qty);
        }
    }


    public Order_adapter(List<Order_model> orderList) {
        this.orderList = orderList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orders_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Order_model movie = orderList.get(position);
        holder.iname.setText(movie.getName());
        holder.date.setText(movie.getDate());
        holder.qty.setText("Qty: "+movie.getQty());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}