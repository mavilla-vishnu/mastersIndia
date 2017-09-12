package co.mastersindia.autotax.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import co.mastersindia.autotax.R;
import co.mastersindia.autotax.SetupActivity;
import co.mastersindia.autotax.TinyDB;
import co.mastersindia.autotax.adapter.Bus_adapter;
import co.mastersindia.autotax.model.Bus_model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pandu on 8/22/2017.
 */

public class BusinessManagement extends Fragment {
    private TinyDB database;
    private RecyclerView recyclerView;
    private List<Bus_model> movieList = new ArrayList<>();
    private Bus_adapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fraglayout_businessmgnt, container, false);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Business");
        setHasOptionsMenu(true);
        recyclerView = (RecyclerView) view.findViewById(R.id.bus_recyclerview);
        mAdapter = new Bus_adapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        prepareData();
    }
    private void prepareData(){
        movieList.clear();
        database=new TinyDB(getActivity());
        ArrayList busList=new ArrayList();
        busList=database.getListString("business_list");
        for(int i=0;i<busList.size();i++){
            String busName=""+busList.get(i);
            ArrayList busArray=new ArrayList();
            busArray=database.getListString(busName);
            String title=busArray.get(0).toString();
            String descp=busArray.get(3)+", "+busArray.get(4)+", "+busArray.get(2);
            String pan=busArray.get(1).toString();
            String cDate=busArray.get(5).toString();
            Bus_model bmodel=new Bus_model(title,descp,pan,cDate);
            movieList.add(bmodel);
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.menu_add, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_additem) {
            Intent i=new Intent(getActivity(), SetupActivity.class);
            i.putExtra("cookie","bm");
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        prepareData();
    }
}
