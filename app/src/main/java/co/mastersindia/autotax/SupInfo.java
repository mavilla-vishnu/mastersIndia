package co.mastersindia.autotax;

import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.mastersindia.autotax.adapter.Order_adapter;
import co.mastersindia.autotax.dataHandlers.OrderDataHandler;
import co.mastersindia.autotax.dataHandlers.SupDatabaseHandler;
import co.mastersindia.autotax.model.Order_model;
import co.mastersindia.autotax.model.Sup_Model;

public class SupInfo extends AppCompatActivity {
    private Sup_Model supModel;
    private SupDatabaseHandler supHandler;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private OrderDataHandler db;
    private Order_model omodel;
    private List<Order_model> orderList = new ArrayList<>();
    private TextView t_empty;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sup_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        supModel=new Sup_Model();
        supHandler=new SupDatabaseHandler(this);
        db=new OrderDataHandler(this);
        omodel=new Order_model();
        mAdapter=new Order_adapter(orderList);

        Bundle extras=getIntent().getExtras();
        id=extras.getInt("sup_id");
        supModel=supHandler.getSupplier(id);

        getSupportActionBar().setTitle(supModel.getBname());
        getSupportActionBar().setSubtitle(supModel.getName());



        TextView t_name=(TextView)findViewById(R.id.si_name);
        TextView t_mobile=(TextView)findViewById(R.id.si_mobile);
        TextView t_mail=(TextView)findViewById(R.id.si_mail);
        TextView t_gstin=(TextView)findViewById(R.id.si_gstin);
        TextView t_address=(TextView)findViewById(R.id.si_address);
        t_empty=(TextView)findViewById(R.id.si_emptytext);

        t_name.setText(supModel.getName());
        t_mobile.setText(supModel.getContact());
        t_mail.setText(supModel.getMail());
        t_gstin.setText(supModel.getGstin());
        t_address.setText(supModel.getAddress());

        ImageView call=(ImageView)findViewById(R.id.si_call);
        ImageView message=(ImageView)findViewById(R.id.si_messg);
        ImageView mail=(ImageView)findViewById(R.id.si_email);
        recyclerView=(RecyclerView)findViewById(R.id.si_recyclerview);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(SupInfo.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        showorders();
    }
    public void showorders(){
        if(db.getOrdersCount()==0){
            t_empty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        else{
            t_empty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            final List<Order_model> items = db.getOrderBysupplier(id);
            for (Order_model item : items) {
                orderList.add(item);
            }
            mAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            //onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
