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
import co.mastersindia.autotax.model.Sup_Model;

public class Sup_adapter extends RecyclerView.Adapter<Sup_adapter.MyViewHolder> {

    private List<Sup_Model> supplierList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView bname,name;

        public MyViewHolder(View view) {
            super(view);
            bname = (TextView) view.findViewById(R.id.first_text_view);
            name = (TextView) view.findViewById(R.id.second_text_view);
        }
    }


    public Sup_adapter(List<Sup_Model> supplierList) {
        this.supplierList = supplierList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.supcust_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Sup_Model movie = supplierList.get(position);
        holder.bname.setText(movie.getBname());
        holder.name.setText(movie.getName());
        Log.e("RVIEW",""+movie.getBname());
    }

    @Override
    public int getItemCount() {
        return supplierList.size();
    }
}