package co.mastersindia.autotax.fragments.directories;

import android.app.Dialog;
import android.app.Fragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import co.mastersindia.autotax.R;

/**
 * Created by Pandu on 8/22/2017.
 */

public class Customer extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fraglayout_customer, container, false);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Customer");
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_add, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_filter){

        }
        if(id==R.id.action_additem){
            final Dialog addcust_dialog=new Dialog(getActivity());
            addcust_dialog.setCancelable(true);
            addcust_dialog.setContentView(R.layout.addcust_dialog);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(addcust_dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            addcust_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            addcust_dialog.show();
            addcust_dialog.getWindow().setAttributes(lp);

            CheckBox shippingCheck=(CheckBox)addcust_dialog.findViewById(R.id.shipping_check);
            final LinearLayout shipping_layout=(LinearLayout)addcust_dialog.findViewById(R.id.layout_shipping);
            shippingCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b){
                        shipping_layout.setVisibility(View.GONE);
                    }
                    else{
                        shipping_layout.setVisibility(View.VISIBLE);
                    }
                }
            });


        }
        return super.onOptionsItemSelected(item);
    }
}
