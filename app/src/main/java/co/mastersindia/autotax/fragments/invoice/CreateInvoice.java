package co.mastersindia.autotax.fragments.invoice;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import co.mastersindia.autotax.dataHandlers.ItemDatabaseHandler;
import co.mastersindia.autotax.R;
import co.mastersindia.autotax.TinyDB;
import co.mastersindia.autotax.model.ItemDataModel;
import co.mastersindia.autotax.scanner.SimpleScannerActivity;

/**
 * Created by Pandu on 8/22/2017.
 */


public class CreateInvoice extends Fragment {
    ItemDatabaseHandler db;
    private TinyDB database;
    EditText e_d_code;
    Dialog additem_dialog;
    TableLayout root;
    ArrayList<String>cart=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fraglayout_createinvoice, container, false);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Add Invoice");
        setHasOptionsMenu(true);
        database=new TinyDB(getActivity());
        db= new ItemDatabaseHandler(getActivity());
        additem_dialog=new Dialog(getActivity(),R.style.TransparentDialogTheme);

        //Bill rootview
        root=(TableLayout)view.findViewById(R.id.cart_table);

        FloatingActionButton fabAdd=(FloatingActionButton)view.findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                additem_dialog.setCancelable(true);
                additem_dialog.setContentView(R.layout.cart_seloptions);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(additem_dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                additem_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                additem_dialog.show();
                additem_dialog.getWindow().setAttributes(lp);

                Button b_pick=(Button)additem_dialog.findViewById(R.id.dialog_pick);
                Button b_scan=(Button)additem_dialog.findViewById(R.id.dialog_scan);
                b_pick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        additem_dialog.dismiss();
                    }
                });
                b_scan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(getActivity(),SimpleScannerActivity.class);
                        i.putExtra("cookie","just");
                        startActivityForResult(i,1);
                        additem_dialog.dismiss();
                    }
                });
            }
        });

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_cart, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                int i=db.getItemByCode(result);
                if(i>0){
                    pickItem(i);
                }
                else{
                    Toast.makeText(getActivity(), "Not found", Toast.LENGTH_SHORT).show();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getActivity(), "Error reading Barcode", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void pickItem(final int id){
        final ItemDataModel idm=db.getItem(id);
        final Dialog selected_dialog=new Dialog(getActivity());
        selected_dialog.setCancelable(true);
        selected_dialog.setContentView(R.layout.cart_additem);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(selected_dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        selected_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        selected_dialog.show();
        selected_dialog.getWindow().setAttributes(lp);

        TextView iname=(TextView)selected_dialog.findViewById(R.id.d_iname);iname.setText(idm.getName());
        TextView istock=(TextView)selected_dialog.findViewById(R.id.d_istock);istock.setText(""+idm.getS_present()+"/"+idm.getS_final());
        TextView iigst=(TextView)selected_dialog.findViewById(R.id.d_igst);iigst.setText(""+idm.getIgst());
        TextView icgst=(TextView)selected_dialog.findViewById(R.id.d_cgst);icgst.setText(""+idm.getCgst());
        TextView isgst=(TextView)selected_dialog.findViewById(R.id.d_sgst);isgst.setText(""+idm.getSgst());
        TextView icess=(TextView)selected_dialog.findViewById(R.id.d_cess);icess.setText(""+idm.getCess());

        Button b_plus=(Button)selected_dialog.findViewById(R.id.d_plus);
        Button b_minus=(Button)selected_dialog.findViewById(R.id.d_minus);

        final EditText e_qty=(EditText)selected_dialog.findViewById(R.id.d_qty);
        final EditText e_ac=(EditText)selected_dialog.findViewById(R.id.d_ac);
        final EditText e_ad=(EditText)selected_dialog.findViewById(R.id.d_ad);

        FloatingActionButton close=(FloatingActionButton)selected_dialog.findViewById(R.id.fab_close);
        final FloatingActionButton done=(FloatingActionButton)selected_dialog.findViewById(R.id.fab_done);
        b_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String val=e_qty.getText().toString();
                if(val.equals("")){
                    e_qty.setText("1");
                }
                else{
                    e_qty.setText(""+(Double.parseDouble(val)+1));
                }
            }
        });
        b_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String val=e_qty.getText().toString();
                if(val.equals("0")||val.equals("")){
                    e_qty.setText("0");
                }
                else{
                    e_qty.setText(""+(Double.parseDouble(val)-1));
                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected_dialog.dismiss();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(e_qty.getText().toString().equals("")||Double.parseDouble(e_qty.getText().toString())==0){
                    Toast.makeText(getActivity(), "Enter valid Quantity", Toast.LENGTH_SHORT).show();
                }else{
                    double discprice=idm.getPrice()-idm.getDisc();
                    String ad=e_ad.getText().toString();if(ad.equals("")){ad="0.0";}
                    String ac=e_ac.getText().toString();if(ac.equals("")){ac="0.0";}
                    JSONObject jo=new JSONObject();
                    try {
                        jo.put("id",id);
                        jo.put("name",""+idm.getName());
                        jo.put("pricec",idm.getPrice());
                        jo.put("discp",discprice);
                        jo.put("qty",Double.parseDouble(e_qty.getText().toString()));
                        jo.put("igst",idm.getIgst());
                        jo.put("cgst",""+idm.getCgst());
                        jo.put("sgst",""+idm.getSgst());
                        jo.put("cess",""+idm.getCess());
                        jo.put("ed",Double.parseDouble(ad));
                        jo.put("ec",Double.parseDouble(ac));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    cart.add(jo.toString());
                    showData();
                    Toast.makeText(getActivity(), "Item added to cart", Toast.LENGTH_SHORT).show();selected_dialog.dismiss();
                }
            }
        });
    }
    public void showData(){
        root.removeAllViews();
        String color="#ffffff";
        for(int i=0;i<cart.size();i++){
            LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = vi.inflate(R.layout.cart_row, null);
            LinearLayout llroot=(LinearLayout)v.findViewById(R.id.ll_row);
            llroot.setBackgroundColor(Color.parseColor(color));
            TextView title=(TextView)v.findViewById(R.id.cart_title);
            TextView price=(TextView)v.findViewById(R.id.cart_price);
            TextView qty=(TextView)v.findViewById(R.id.cart_qty);
            TextView fprice=(TextView)v.findViewById(R.id.cart_fprice);
            TextView discprice=(TextView)v.findViewById(R.id.cart_discprice);

            try {
                JSONObject jo=new JSONObject(cart.get(i));
                title.setText(jo.get("name").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            root.addView(v);
            if(color.equals("#ffffff")){color="#e9e7e7";}else{color="#ffffff";}
        }
    }

}
