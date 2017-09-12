package co.mastersindia.autotax.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.mastersindia.autotax.dataHandlers.ItemDatabaseHandler;
import co.mastersindia.autotax.R;
import co.mastersindia.autotax.model.ItemDataModel;

/**
 * Created by Pandu on 9/2/2017.
 */

public class Service_fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fraglayout_service, container, false);
    }@Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        ItemDatabaseHandler db=new ItemDatabaseHandler(view.getContext());
        ListView sList=(ListView)view.findViewById(R.id.service_list);
        List<ItemDataModel> items = db.getAllServices();
        ArrayList<Map<String, String>> data = new ArrayList<Map<String, String>>();

        for (ItemDataModel item : items) {
            Map<String, String> datum = new HashMap<String, String>(2);
            datum.put("name", item.getName());
            datum.put("price", "\u20B9"+item.getPrice());
            datum.put("available", ""+item.getS_final());
            data.add(datum);
        }

        SimpleAdapter adapter = new SimpleAdapter(view.getContext(), data, R.layout.item_row, new String[] {"name", "price","available"}, new int[] {R.id.item_name, R.id.item_price,R.id.item_available});
        sList.setAdapter(adapter);
    }
}
