package co.mastersindia.autotax.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.mastersindia.autotax.dataHandlers.ItemDatabaseHandler;
import co.mastersindia.autotax.R;
import co.mastersindia.autotax.TinyDB;
import co.mastersindia.autotax.dataHandlers.OrderDataHandler;
import co.mastersindia.autotax.dataHandlers.SupDatabaseHandler;
import co.mastersindia.autotax.model.ItemDataModel;
import co.mastersindia.autotax.model.Order_model;
import co.mastersindia.autotax.model.Sup_Model;
import co.mastersindia.autotax.scanner.SimpleScannerActivity;

/**
 * Created by Pandu on 9/2/2017.
 */

public class Goods_fragment extends Fragment {
    private Boolean fav=false;
    String type="Good";
    ItemDatabaseHandler db;
    private TinyDB database;
    private ListView gList;
    private TextView empty_text;
    private EditText search,e_code;
    int dType=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fraglayout_goods, container, false);
    }
    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        setHasOptionsMenu(true);
        database=new TinyDB(getActivity());
        db= new ItemDatabaseHandler(getActivity());

        gList=view.findViewById(R.id.goods_list);
        gList.setTextFilterEnabled(true);
        empty_text=(TextView)view.findViewById(R.id.goods_text);
        search=(EditText)view.findViewById(R.id.goods_search);
        showData(view,"fav","DESC");

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_additem) {
            final Dialog addItem_dialog=new Dialog(getActivity());
            addItem_dialog.setCancelable(true);
            addItem_dialog.setContentView(R.layout.dialog_additems);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(addItem_dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            addItem_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            addItem_dialog.show();
            addItem_dialog.getWindow().setAttributes(lp);

            final ImageButton bFav=(ImageButton)addItem_dialog.findViewById(R.id.d_favbutton);
            bFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(fav){fav=false;}else{fav=true;}
                    if(fav){
                        bFav.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_star_checked,null));
                    }else{
                        bFav.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_star_unchecked,null));
                    }
                    Toast.makeText(getActivity(), ""+fav, Toast.LENGTH_SHORT).show();
                }
            });


            FloatingActionButton bAdd=(FloatingActionButton)addItem_dialog.findViewById(R.id.d_additem);
            final TextInputLayout ti_name=(TextInputLayout)addItem_dialog.findViewById(R.id.ti_name);
            final TextInputLayout ti_sp=(TextInputLayout)addItem_dialog.findViewById(R.id.ti_sp);
            final TextInputLayout ti_stock=(TextInputLayout)addItem_dialog.findViewById(R.id.ti_stock);
            final TextInputLayout ti_igst=(TextInputLayout)addItem_dialog.findViewById(R.id.ti_isgt);
            final TextInputLayout ti_cgst=(TextInputLayout)addItem_dialog.findViewById(R.id.ti_cgst);
            final TextInputLayout ti_sgst=(TextInputLayout)addItem_dialog.findViewById(R.id.ti_sgst);
            final TextInputLayout ti_cess=(TextInputLayout)addItem_dialog.findViewById(R.id.ti_cess);
            final TextInputLayout ti_code=(TextInputLayout)addItem_dialog.findViewById(R.id.ti_code);
            //Optional values
            TextInputLayout ti_unit=(TextInputLayout)addItem_dialog.findViewById(R.id.ti_unit);
            final TextInputLayout ti_descp=(TextInputLayout)addItem_dialog.findViewById(R.id.ti_descp);
            final TextInputLayout ti_disc=(TextInputLayout)addItem_dialog.findViewById(R.id.ti_discount);

            final EditText e_name=addItem_dialog.findViewById(R.id.d_itemname);
            final EditText e_sp=(EditText)addItem_dialog.findViewById(R.id.d_sp);
            final EditText e_stock=(EditText)addItem_dialog.findViewById(R.id.e_stock);
            final EditText e_igst=(EditText)addItem_dialog.findViewById(R.id.d_igst);
            final EditText e_cgst=(EditText)addItem_dialog.findViewById(R.id.d_cgst);
            final EditText e_sgst=(EditText)addItem_dialog.findViewById(R.id.d_sgst);
            final EditText e_cess=(EditText)addItem_dialog.findViewById(R.id.d_cess);
            final EditText e_disc=(EditText)addItem_dialog.findViewById(R.id.d_discount);
            e_code=(EditText)addItem_dialog.findViewById(R.id.d_code);
            Spinner s_disc=(Spinner)addItem_dialog.findViewById(R.id.disc_spinner);
            ImageButton scan=(ImageButton)addItem_dialog.findViewById(R.id.btn_scan);
            scan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(getActivity(),SimpleScannerActivity.class);
                    startActivityForResult(i,1);
                }
            });
            final double[] disc = {0.0};
            e_disc.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String gd=e_disc.getText().toString();
                    if(dType==0&&!gd.equals("")&&!e_sp.getText().toString().equals("")){
                        disc[0] =Double.parseDouble(e_sp.getText().toString())*(Double.parseDouble(gd)/100);
                        ti_disc.setError("Discount: "+ disc[0]);
                    }
                    else if(dType==1&&!gd.equals("")&&!e_sp.getText().toString().equals("")){
                        disc[0] =Double.parseDouble(e_sp.getText().toString())-Double.parseDouble(gd);
                        ti_disc.setErrorEnabled(false);
                    }else{
                        ti_descp.setErrorEnabled(false);
                        disc[0]=0.0;
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            s_disc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    switch (i){
                        case 0:
                            dType=0;
                            break;
                        case 1:
                            dType=1;
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            //Optional values
            final EditText e_unit=(EditText)addItem_dialog.findViewById(R.id.d_unit);
            final EditText e_descp=(EditText)addItem_dialog.findViewById(R.id.d_descp);

            RadioGroup type_group=(RadioGroup)addItem_dialog.findViewById(R.id.d_rgroup);
            type_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                    RadioButton rbutton=(RadioButton)addItem_dialog.findViewById(i);
                    type=rbutton.getText().toString();
                }
            });

            bAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(validateText(e_name.getText().toString())){
                        ti_name.setError("Invalid name!");
                        e_name.requestFocus();
                        return;
                    }
                    else{ti_name.setErrorEnabled(false);}
                    if(e_sp.getText().toString().isEmpty()){
                        ti_sp.setError("Invalid price!");
                        e_sp.requestFocus();
                        return;
                    }else{ ti_sp.setErrorEnabled(false);}
                    if(e_stock.getText().toString().isEmpty()){
                        ti_stock.setError("Invalid. Or enter 0!");
                        e_stock.requestFocus();
                        return;
                    }else{ ti_sp.setErrorEnabled(false);}
                    if(e_igst.getText().toString().isEmpty()){
                        ti_igst.setError("Invalid. Or enter 0");
                        e_igst.requestFocus();
                        return;
                    }else{ ti_igst.setErrorEnabled(false);}
                    if(e_cgst.getText().toString().isEmpty()){
                        ti_cgst.setError("Invalid. Or enter 0");
                        e_cgst.requestFocus();
                        return;
                    }else{ ti_cgst.setErrorEnabled(false);}
                    if(e_sgst.getText().toString().isEmpty()){
                        e_sgst.requestFocus();
                        ti_sgst.setError("Invalid. Or enter 0");
                        return;
                    }else{ ti_sgst.setErrorEnabled(false);}
                    if(e_cess.getText().toString().isEmpty()){
                        ti_cess.setError("Invalid. Or enter 0");
                        e_cess.requestFocus();
                        return;
                    }else{ ti_cess.setErrorEnabled(false);}
                    String name=e_name.getText().toString();
                    String code=e_code.getText().toString();
                    String unit=e_unit.getText().toString();
                    double price= Double.parseDouble(e_sp.getText().toString());
                    double stock= Double.parseDouble(e_stock.getText().toString());
                    String descp=e_descp.getText().toString();
                    double igst= Double.parseDouble(e_igst.getText().toString());
                    double cgst= Double.parseDouble(e_cgst.getText().toString());
                    double sgst= Double.parseDouble(e_sgst.getText().toString());
                    double cess= Double.parseDouble(e_cess.getText().toString());

                    //Save item data from here
                    int i=database.getInt("i_count");
                    db.addContact(new ItemDataModel(i,name,fav.toString(),type,unit,price,code,descp,igst,cgst,sgst,cess,stock,stock,stock, disc[0]));
                    Toast.makeText(getActivity(), "Item added successfully", Toast.LENGTH_SHORT).show();
                    showData(view,"fav","DESC");
                    database.putInt("i_count",i+1);
                    addItem_dialog.dismiss();

                }
            });
        }
        if(id==R.id.action_filter){
            final View view=getActivity().findViewById(R.id.action_filter);
            PopupMenu popup = new PopupMenu(getActivity(),view);
            popup.getMenuInflater().inflate(R.menu.menu_sort_goods, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    int itemid=item.getItemId();
                    if(itemid==R.id.sort_fav){
                        showData(view,"fav","DESC");
                    }
                    if(itemid==R.id.sort_estock){
                        showData(view,"s_final","DESC");
                    }
                    if(itemid==R.id.sort_bynaz){
                        showData(view,"name","ASC");
                    }
                    if(itemid==R.id.sort_bynza){
                        showData(view,"name","DESC");
                    }
                    if(itemid==R.id.sort_byplh){
                        showData(view,"price","ASC");
                    }
                    if(itemid==R.id.sort_byphl){
                        showData(view,"price","DESC");
                    }
                    return true;
                }
            });
            popup.show();
        }
        return super.onOptionsItemSelected(item);
    }
    private boolean validateText(String s) {
        return s.equals("");
    }

    public void showData(final View view, String fText, String ftype) {
        final List<ItemDataModel> items = db.getAllGoods(fText, ftype);
        if (db.getItemsCount() == 0) {
            empty_text.setVisibility(View.VISIBLE);
        } else {
            empty_text.setVisibility(View.GONE);
        }
        final ArrayList<Map<String, String>> data = new ArrayList<Map<String, String>>();

        for (ItemDataModel item : items) {
            Map<String, String> datum = new HashMap<String, String>(2);
            datum.put("name", item.getName());
            datum.put("price", "\u20B9" + item.getPrice());
            datum.put("available", "" + item.getS_final() + "/" + item.getS_present());
            if (item.getFav().equals("true")) {
                datum.put("url", "" + R.drawable.ic_star_checked);
            } else {
                datum.put("url", "");
            }
            data.add(datum);
            if (Double.parseDouble("" + item.getS_final()) == 0.0) {
                datum.put("soldout", "Sold out");
            } else {
                datum.put("soldout", "");
            }

        }

        final SimpleAdapter adapter = new SimpleAdapter(view.getContext(), data, R.layout.item_row, new String[]{"name", "price", "available", "url", "soldout"}, new int[]{R.id.item_name, R.id.item_price, R.id.item_available, R.id.item_star, R.id.item_soldout});
        gList.setAdapter(adapter);
        gList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, final View view, final int i, long l) {
                String favtext="";
                if(items.get(i).getFav().equals("true")){favtext="Unmark Favourite";}else{favtext = "Mark Favourite";}
                CharSequence options[] = new CharSequence[] {"View", "Edit", "Delete", "Place order",favtext};
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Options");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                final ItemDataModel selItem = db.getItem(items.get(i).getId());
                                ArrayList<Map<String, String>> selData = new ArrayList<Map<String, String>>();
                                Map<String, String> item=new HashMap<String, String>(2);
                                item.put("text1","Name");
                                item.put("text2",""+selItem.getName());
                                selData.add(item);item=new HashMap<String, String>(2);
                                item.put("text1","Type");
                                item.put("text2",""+selItem.getType());
                                selData.add(item);item=new HashMap<String, String>(2);
                                if(selItem.getUnit().equals("")){
                                    item.put("text1","Unit");
                                    item.put("text2","NA");
                                    selData.add(item);item=new HashMap<String, String>(2);
                                }
                                else{
                                    item.put("text1","Unit");
                                    item.put("text2",""+selItem.getUnit());
                                    selData.add(item);item=new HashMap<String, String>(2);
                                }


                                item.put("text1","Price");
                                item.put("text2",""+selItem.getPrice());
                                selData.add(item);item=new HashMap<String, String>(2);

                                if(selItem.getCode().equals("")){
                                    item.put("text1","Barcode");
                                    item.put("text2","NA");
                                    selData.add(item);item=new HashMap<String, String>(2);
                                }
                                else{
                                    item.put("text1","Barcode");
                                    item.put("text2",""+selItem.getCode());
                                    selData.add(item);item=new HashMap<String, String>(2);
                                }
                                if(selItem.getDescp().equals("")){
                                    item.put("text1","Description");
                                    item.put("text2","NA");
                                    selData.add(item);item=new HashMap<String, String>(2);
                                }
                                else{
                                    item.put("text1","Description");
                                    item.put("text2",""+selItem.getDescp());
                                    selData.add(item);item=new HashMap<String, String>(2);
                                }

                                item.put("text1","IGST");
                                item.put("text2",""+selItem.getIgst());
                                selData.add(item);item=new HashMap<String, String>(2);
                                item.put("text1","CGST");
                                item.put("text2",""+selItem.getCgst());
                                selData.add(item);item=new HashMap<String, String>(2);
                                item.put("text1","SGST");
                                item.put("text2",""+selItem.getSgst());
                                selData.add(item);item=new HashMap<String, String>(2);
                                item.put("text1","CESS");
                                item.put("text2",""+selItem.getCess());
                                selData.add(item);item=new HashMap<String, String>(2);
                                item.put("text1","Total stock added");
                                item.put("text2",""+selItem.getS_tot());
                                selData.add(item);item=new HashMap<String, String>(2);
                                item.put("text1","Last added stock");
                                item.put("text2",""+selItem.getS_present());
                                selData.add(item);item=new HashMap<String, String>(2);
                                item.put("text1","Present stock");
                                item.put("text2",""+selItem.getS_final());
                                selData.add(item);

                                SimpleAdapter detailsadapter = new SimpleAdapter(view.getContext(), selData,android.R.layout.simple_list_item_2, new String[] {"text1","text2"}, new int[] { android.R.id.text1,android.R.id.text2 });

                                final Dialog viewItem_dialog=new Dialog(getActivity());
                                viewItem_dialog.setCancelable(true);
                                viewItem_dialog.setContentView(R.layout.viewdialog_items);
                                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                                lp.copyFrom(viewItem_dialog.getWindow().getAttributes());
                                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                                viewItem_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                viewItem_dialog.show();
                                viewItem_dialog.getWindow().setAttributes(lp);

                                ListView itemList=(ListView)viewItem_dialog.findViewById(R.id.dialog_good_list);
                                itemList.setAdapter(detailsadapter);
                                FloatingActionButton done=(FloatingActionButton)viewItem_dialog.findViewById(R.id.dialog_done);
                                done.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        viewItem_dialog.dismiss();
                                    }
                                });
                                break;
                            case 1:
                                final Dialog edititem_dialog=new Dialog(getActivity());
                                edititem_dialog.setCancelable(true);
                                edititem_dialog.setContentView(R.layout.edititem_dialog);
                                WindowManager.LayoutParams lp2 = new WindowManager.LayoutParams();
                                lp2.copyFrom(edititem_dialog.getWindow().getAttributes());
                                lp2.width = WindowManager.LayoutParams.MATCH_PARENT;
                                lp2.height = WindowManager.LayoutParams.WRAP_CONTENT;
                                edititem_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                edititem_dialog.show();
                                edititem_dialog.getWindow().setAttributes(lp2);

                                final TextInputLayout ti_name=(TextInputLayout)edititem_dialog.findViewById(R.id.ti_name);
                                final TextInputLayout ti_sp=(TextInputLayout)edititem_dialog.findViewById(R.id.ti_sp);
                                final TextInputLayout ti_stock=(TextInputLayout)edititem_dialog.findViewById(R.id.ti_stock);
                                TextInputLayout ti_code=(TextInputLayout)edititem_dialog.findViewById(R.id.ti_code);
                                final TextInputLayout ti_disc=(TextInputLayout)edititem_dialog.findViewById(R.id.ti_discount);
                                final TextInputLayout ti_igst=(TextInputLayout)edititem_dialog.findViewById(R.id.ti_isgt);
                                final TextInputLayout ti_sgst=(TextInputLayout)edititem_dialog.findViewById(R.id.ti_sgst);
                                final TextInputLayout ti_cgst=(TextInputLayout)edititem_dialog.findViewById(R.id.ti_cgst);
                                final TextInputLayout ti_cess=(TextInputLayout)edititem_dialog.findViewById(R.id.ti_cess);
                                TextInputLayout ti_unit=(TextInputLayout)edititem_dialog.findViewById(R.id.ti_unit);
                                final TextInputLayout ti_descp=(TextInputLayout)edititem_dialog.findViewById(R.id.ti_descp);

                                final ItemDataModel selItemupdate = db.getItem(items.get(i).getId());
                                final int id=selItemupdate.getId();
                                final String name=selItemupdate.getName();
                                final String fav=selItemupdate.getFav();
                                final String type=selItemupdate.getType();
                                final String unit=selItemupdate.getUnit();
                                final double sp=selItemupdate.getPrice();
                                final String Code=selItemupdate.getCode();
                                final String descp=selItemupdate.getDescp();
                                final double igst= selItemupdate.getIgst();
                                final double cgst= selItemupdate.getCgst();
                                final double sgst= selItemupdate.getSgst();
                                final double cess= selItemupdate.getCess();
                                final double tot= selItemupdate.getS_tot();
                                final double present= selItemupdate.getS_present();
                                final double sfinal= selItemupdate.getS_final();
                                final double discp= selItemupdate.getDisc();


                                final EditText e_name=edititem_dialog.findViewById(R.id.d_itemname);
                                e_name.setText(name);
                                final EditText e_sp=(EditText)edititem_dialog.findViewById(R.id.d_sp);
                                e_sp.setText(""+sp);
                                final EditText e_stock=(EditText)edititem_dialog.findViewById(R.id.e_stock);
                                e_stock.setText(""+present);
                                final EditText e_igst=(EditText)edititem_dialog.findViewById(R.id.d_igst);
                                e_igst.setText(""+igst);
                                final EditText e_cgst=(EditText)edititem_dialog.findViewById(R.id.d_cgst);
                                e_cgst.setText(""+cgst);
                                final EditText e_sgst=(EditText)edititem_dialog.findViewById(R.id.d_sgst);
                                e_sgst.setText(""+sgst);
                                final EditText e_cess=(EditText)edititem_dialog.findViewById(R.id.d_cess);
                                e_cess.setText(""+cess);
                                final EditText e_disc=(EditText)edititem_dialog.findViewById(R.id.d_discount);
                                e_disc.setText(""+discp);
                                final EditText e_unit=(EditText)edititem_dialog.findViewById(R.id.d_unit);
                                e_unit.setText(unit);
                                final EditText e_descp=(EditText)edititem_dialog.findViewById(R.id.d_descp);
                                e_descp.setText(descp);
                                e_code=(EditText)edititem_dialog.findViewById(R.id.d_code);
                                e_code.setText(Code);
                                ImageButton scan=(ImageButton)edititem_dialog.findViewById(R.id.btn_scan);
                                scan.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent i=new Intent(getActivity(),SimpleScannerActivity.class);
                                        startActivityForResult(i,1);
                                    }
                                });

                                Spinner disc_spinner=(Spinner)edititem_dialog.findViewById(R.id.disc_spinner);
                                disc_spinner.setSelection(1);
                                disc_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        switch (i){
                                            case 0:
                                                dType=0;
                                                break;
                                            case 1:
                                                dType=1;
                                                break;
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                                final double[] disc = {0.0};
                                e_disc.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                        String gd=e_disc.getText().toString();
                                        if(dType==0&&!gd.equals("")&&!e_sp.getText().toString().equals("")){
                                            disc[0] =Double.parseDouble(e_sp.getText().toString())*(Double.parseDouble(gd)/100);
                                            ti_disc.setError("Discount: "+ disc[0]);
                                        }
                                        else if(dType==1&&!gd.equals("")&&!e_sp.getText().toString().equals("")){
                                            disc[0] =Double.parseDouble(e_sp.getText().toString())-Double.parseDouble(gd);
                                            ti_disc.setErrorEnabled(false);
                                        }else{
                                            ti_disc.setErrorEnabled(false);
                                            disc[0]=0.0;
                                        }
                                    }

                                    @Override
                                    public void afterTextChanged(Editable editable) {

                                    }
                                });
                                FloatingActionButton d_done=(FloatingActionButton)edititem_dialog.findViewById(R.id.d_additem);
                                d_done.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if(validateText(e_name.getText().toString())){
                                            ti_name.setError("Invalid name!");
                                            e_name.requestFocus();
                                            return;
                                        }
                                        else{ti_name.setErrorEnabled(false);}
                                        if(e_sp.getText().toString().isEmpty()){
                                            ti_sp.setError("Invalid price!");
                                            e_sp.requestFocus();
                                            return;
                                        }else{ ti_sp.setErrorEnabled(false);}
                                        if(e_stock.getText().toString().isEmpty()){
                                            ti_stock.setError("Invalid. Or enter 0!");
                                            e_stock.requestFocus();
                                            return;
                                        }else{ ti_sp.setErrorEnabled(false);}
                                        if(e_igst.getText().toString().isEmpty()){
                                            ti_igst.setError("Invalid. Or enter 0");
                                            e_igst.requestFocus();
                                            return;
                                        }else{ ti_igst.setErrorEnabled(false);}
                                        if(e_cgst.getText().toString().isEmpty()){
                                            ti_cgst.setError("Invalid. Or enter 0");
                                            e_cgst.requestFocus();
                                            return;
                                        }else{ ti_cgst.setErrorEnabled(false);}
                                        if(e_sgst.getText().toString().isEmpty()){
                                            e_sgst.requestFocus();
                                            ti_sgst.setError("Invalid. Or enter 0");
                                            return;
                                        }else{ ti_sgst.setErrorEnabled(false);}
                                        if(e_cess.getText().toString().isEmpty()){
                                            ti_cess.setError("Invalid. Or enter 0");
                                            e_cess.requestFocus();
                                            return;
                                        }else{ ti_cess.setErrorEnabled(false);}
                                        String new_name=e_name.getText().toString();
                                        String new_code=e_code.getText().toString();
                                        String new_unit=e_unit.getText().toString();
                                        double new_price= Double.parseDouble(e_sp.getText().toString());
                                        double new_stock= Double.parseDouble(e_stock.getText().toString());
                                        String new_descp=e_descp.getText().toString();
                                        double new_igst= Double.parseDouble(e_igst.getText().toString());
                                        double new_cgst= Double.parseDouble(e_cgst.getText().toString());
                                        double new_sgst= Double.parseDouble(e_sgst.getText().toString());
                                        double new_cess= Double.parseDouble(e_cess.getText().toString());

                                        ItemDatabaseHandler idm=new ItemDatabaseHandler(getActivity());
                                        idm.updateitem(new ItemDataModel(id,new_name,fav,type,new_unit,new_price,new_code,new_descp,new_igst,new_cgst,new_sgst,new_cess,tot+new_stock,new_stock,new_stock,disc[0]));
                                        Toast.makeText(getActivity(), "Item updated successfully", Toast.LENGTH_SHORT).show();
                                        edititem_dialog.dismiss();
                                        showData(view,"fav","DESC");
                                    }
                                });

                                break;
                            case 2:
                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Delete")
                                        .setMessage("Confirm Item deletion. Continue?")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                ItemDataModel newdelidm=new ItemDataModel();
                                                newdelidm=items.get(i);
                                                db.deleteItem(newdelidm);
                                                Toast.makeText(getActivity(), "Item deleted!", Toast.LENGTH_SHORT).show();
                                                showData(view,"fav","DESC");
                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();
                                break;
                            case 3:
                                SupDatabaseHandler dbs=new SupDatabaseHandler(getActivity());
                                if(dbs.getSuppliersCount()==0){
                                    Toast.makeText(getActivity(), "No supplier data found. Edit item and manually update stock", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    final Dialog placeorder_dialog=new Dialog(getActivity());
                                    placeorder_dialog.setCancelable(true);
                                    placeorder_dialog.setContentView(R.layout.placeorder_dialog);
                                    WindowManager.LayoutParams lpp = new WindowManager.LayoutParams();
                                    lpp.copyFrom(placeorder_dialog.getWindow().getAttributes());
                                    lpp.width = WindowManager.LayoutParams.MATCH_PARENT;
                                    lpp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                                    placeorder_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                    placeorder_dialog.show();
                                    placeorder_dialog.getWindow().setAttributes(lpp);

                                    final TextInputLayout t_qty=(TextInputLayout)placeorder_dialog.findViewById(R.id.t_qty);
                                    final EditText e_qty=(EditText)placeorder_dialog.findViewById(R.id.e_qty);
                                    FloatingActionButton fabDone=(FloatingActionButton)placeorder_dialog.findViewById(R.id.fab_send);
                                    Spinner supSpin=(Spinner)placeorder_dialog.findViewById(R.id.s_supplier);

                                    final ArrayList<Integer> idlist=new ArrayList<Integer>();
                                    ArrayList<String> namelist=new ArrayList<String>();
                                    List<Sup_Model>supplierlist=dbs.getAllSuppliers();
                                    for(Sup_Model sups:supplierlist){
                                        idlist.add(sups.getId());
                                        namelist.add(sups.getBname());
                                    }
                                    final int[] index = {0};
                                    String[]nameArray=namelist.toArray(new String[namelist.size()]);
                                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nameArray);
                                    supSpin.setAdapter(dataAdapter);supSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            index[0] =i;
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });
                                    fabDone.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if(e_qty.getText().toString().equals("")){
                                                t_qty.setError("Invalid Quantity");
                                            }
                                            else{
                                                double qty=Double.parseDouble(e_qty.getText().toString());
                                                t_qty.setErrorEnabled(false);
                                                int oid=database.getInt("o_id");
                                                OrderDataHandler dbhandler=new OrderDataHandler(getActivity());
                                                final ItemDataModel selItem = db.getItem(items.get(i).getId());
                                                String name=selItem.getName();
                                                SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                                Date date = Calendar.getInstance().getTime();
                                                String dateString=fmt.format(date);
                                                dbhandler.addOrder(new Order_model(oid,name,qty,dateString,idlist.get(index[0]),"Submitted"));
                                                selItem.setS_tot(selItem.getS_tot()+qty);
                                                selItem.setS_present(selItem.getS_present()+qty);
                                                selItem.setS_final(selItem.getS_final()+qty);
                                                db.updateitem(selItem);
                                                database.putInt("o_id",oid+1);
                                                showData(view,"fav","DESC");
                                                Toast.makeText(getActivity(), "Order placed and Stock updated", Toast.LENGTH_SHORT).show();
                                                placeorder_dialog.dismiss();
                                            }
                                        }
                                    });
                                }
                                break;
                            case 4:
                                if(items.get(i).getFav().equals("true")){
                                    ItemDataModel newidm=new ItemDataModel();
                                    newidm=items.get(i);
                                    newidm.setFav("false");
                                    db.updateitem(newidm);
                                    showData(view,"fav","DESC");
                                }
                                else{
                                    ItemDataModel newidm=new ItemDataModel();
                                    newidm=items.get(i);
                                    newidm.setFav("true");
                                    db.updateitem(newidm);
                                    showData(view,"fav","DESC");
                                }
                                break;
                        }
                    }
                });
                builder.show();
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>0){
                    adapter.getFilter().filter(charSequence);
                }
                else{
                    adapter.getFilter().filter("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                if(db.checkBarcode(result)){
                    Toast.makeText(getActivity(), "Item or the barcode already exists!", Toast.LENGTH_SHORT).show();
                }
                else{
                    e_code.setText(""+result);
                }

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }


}
