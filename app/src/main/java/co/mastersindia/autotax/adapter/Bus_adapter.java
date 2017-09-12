package co.mastersindia.autotax.adapter;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import co.mastersindia.autotax.R;
import co.mastersindia.autotax.SetupUnitActivity;
import co.mastersindia.autotax.ViewBusinessActivity;
import co.mastersindia.autotax.model.Bus_model;

/**
 * Created by Pandu on 8/31/2017.
 */

public class Bus_adapter extends RecyclerView.Adapter<Bus_adapter.MyViewHolder> {

    private List<Bus_model> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, descp, pan,cdate;
        private ImageButton bOptions;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.bus_title);
            descp = (TextView) view.findViewById(R.id.bus_descp);
            pan = (TextView) view.findViewById(R.id.bus_pan);
            cdate = (TextView) view.findViewById(R.id.bus_cdate);
            bOptions=(ImageButton)view.findViewById(R.id.bus_options);
            bOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    PopupMenu popup = new PopupMenu(view.getContext(), view);
                    popup.getMenuInflater().inflate(R.menu.menu_busoptions, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            Bus_model mm=moviesList.get(Integer.parseInt(String.valueOf(getAdapterPosition())));
                            String key =mm.getGstin();
                            int itemid=item.getItemId();
                            if(itemid==R.id.action_view){
                                Intent i=new Intent(view.getContext(), ViewBusinessActivity.class);
                                i.putExtra("busname",key);
                                view.getContext().startActivity(i);
                            }
//                            if(itemid==R.id.action_addunit){
//                                Intent i=new Intent(view.getContext(), SetupUnitActivity.class);
//                                view.getContext().startActivity(i);
//                            }
                            return true;
                        }
                    });
                    popup.show();

                }
            });
        }
    }


    public Bus_adapter(List<Bus_model> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.business_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Bus_model movie = moviesList.get(position);
        holder.title.setText(movie.getTitle());
        holder.descp.setText(movie.getDescp());
        holder.pan.setText("GSTIN: "+movie.getGstin());
        holder.cdate.setText("Added on: "+movie.getCdate());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}