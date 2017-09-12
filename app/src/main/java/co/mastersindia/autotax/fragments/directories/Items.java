package co.mastersindia.autotax.fragments.directories;

/**
 * Created by Pandu on 8/27/2017.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.mastersindia.autotax.R;
import co.mastersindia.autotax.adapter.PageAdapter;

/**
 * Created by Pandu on 8/22/2017.
 */

public class Items extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fraglayout_items, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Items");


        TabLayout tabLayout = (TabLayout)view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Goods"));
        tabLayout.addTab(tabLayout.newTab().setText("Services"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
        final PageAdapter adapter = new PageAdapter(getActivity().getFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//        ListView iLv=(ListView)view.findViewById(R.id.itemsListView);
//        List<ItemDataModel> items = db.getAllitems();
//        Toast.makeText(view.getContext(), ""+db.getItemsCount(), Toast.LENGTH_SHORT).show();
//        ArrayList<Map<String, String>> data = new ArrayList<Map<String, String>>();
//
//        for (ItemDataModel item : items) {
//            Map<String, String> datum = new HashMap<String, String>(2);
//            datum.put("name", item.getName());
//            datum.put("price", "\u20B9"+item.getPrice());
//            datum.put("available", ""+item.getS_final());
//            data.add(datum);
//        }
//
//        SimpleAdapter adapter = new SimpleAdapter(view.getContext(), data, R.layout.item_row, new String[] {"name", "price","available"}, new int[] {R.id.item_name, R.id.item_price,R.id.item_available});
//        iLv.setAdapter(adapter);
    }
}