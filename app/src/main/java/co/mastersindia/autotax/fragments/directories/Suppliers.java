package co.mastersindia.autotax.fragments.directories;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import co.mastersindia.autotax.R;
import co.mastersindia.autotax.RecyclerTouchListener;
import co.mastersindia.autotax.SupInfo;
import co.mastersindia.autotax.TinyDB;
import co.mastersindia.autotax.adapter.Sup_adapter;
import co.mastersindia.autotax.dataHandlers.SupDatabaseHandler;
import co.mastersindia.autotax.model.ItemDataModel;
import co.mastersindia.autotax.model.Sup_Model;

import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.id.input;
import static android.view.View.GONE;

/**
 * Created by Pandu on 8/22/2017.
 */

public class Suppliers extends Fragment {
    private String type="";
    private String state="";
    private String country="";
    private SupDatabaseHandler db;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Sup_Model> supplierList = new ArrayList<>();
    public static final float ALPHA_FULL = 1.0f;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fraglayout_supplier, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Suppliers");
        db=new SupDatabaseHandler(getActivity());
        mAdapter = new Sup_adapter(supplierList);
        recyclerView = (RecyclerView) view.findViewById(R.id.sup_view);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        //Loading supplier data
        final TextView emptyText=(TextView)view.findViewById(R.id.sup_emptytext);
        if(db.getSuppliersCount()==0){
            emptyText.setVisibility(View.VISIBLE);
        }
        else{
            emptyText.setVisibility(View.GONE);
            showSuppliers();
        }
        //Recyclerview swipe listener
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ) {
                    Drawable background;
                    Drawable xMark;
                    int xMarkMargin;
                    boolean initiated;

                    private void init() {
                        background = new ColorDrawable(Color.RED);
                        xMark = ContextCompat.getDrawable(getActivity(), R.drawable.ic_delete_white);
                        xMark.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                        xMarkMargin = (int) getActivity().getResources().getDimension(R.dimen.ic_clear_margin);
                        initiated = true;
                    }
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }
                    @Override
                    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
                        builder.setTitle("Delete Supplier")
                                .setMessage("Are you sure you want to delete this supplier?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        db.deleteSupplier(supplierList.get(viewHolder.getAdapterPosition()));
                                        supplierList.remove(viewHolder.getAdapterPosition());
                                        if(supplierList.size()==0){
                                            emptyText.setVisibility(View.VISIBLE);
                                        }
                                        mAdapter.notifyDataSetChanged();
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    mAdapter.notifyDataSetChanged();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                    @Override
                    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                        View itemView = viewHolder.itemView;
                        if (viewHolder.getAdapterPosition() == -1) {
                            return;
                        }
                        if (!initiated) {
                            init();
                        }
                        background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                        background.draw(c);
                        int itemHeight = itemView.getBottom() - itemView.getTop();
                        int intrinsicWidth = xMark.getIntrinsicWidth();
                        int intrinsicHeight = xMark.getIntrinsicWidth();

                        int xMarkLeft = itemView.getRight() - xMarkMargin - intrinsicWidth;
                        int xMarkRight = itemView.getRight() - xMarkMargin;
                        int xMarkTop = itemView.getTop() + (itemHeight - intrinsicHeight)/2;
                        int xMarkBottom = xMarkTop + intrinsicHeight;
                        xMark.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom);

                        xMark.draw(c);

                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Sup_Model movie = supplierList.get(position);
                Intent i=new Intent(getActivity(), SupInfo.class);
                i.putExtra("sup_id",movie.getId());
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        FloatingActionButton fab_add=(FloatingActionButton)view.findViewById(R.id.fab_add);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog addsup_dialog=new Dialog(getActivity());
                addsup_dialog.setCancelable(true);
                addsup_dialog.setContentView(R.layout.addsup_dialog);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(addsup_dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                addsup_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                addsup_dialog.show();
                addsup_dialog.getWindow().setAttributes(lp);

                Spinner spin_type=(Spinner)addsup_dialog.findViewById(R.id.sup_spin_type);
                Spinner spin_state=(Spinner)addsup_dialog.findViewById(R.id.sup_spin_state);
                Spinner spin_country=(Spinner)addsup_dialog.findViewById(R.id.sup_spin_country);
                spin_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        type=adapterView.getItemAtPosition(i).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                spin_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        state=adapterView.getItemAtPosition(i).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                spin_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        country=adapterView.getItemAtPosition(i).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                final EditText e_gst=(EditText)addsup_dialog.findViewById(R.id.sup_gstin);
                final EditText e_bname=(EditText)addsup_dialog.findViewById(R.id.sup_bname);
                final EditText e_contact=(EditText)addsup_dialog.findViewById(R.id.sup_contact);
                final EditText e_mail=(EditText)addsup_dialog.findViewById(R.id.sup_mail);
                final EditText e_name=(EditText)addsup_dialog.findViewById(R.id.sup_name);
                final EditText e_addr=(EditText)addsup_dialog.findViewById(R.id.sup_address);
                final EditText e_pin=(EditText)addsup_dialog.findViewById(R.id.sup_pin);

                final TextInputLayout tl_gst=(TextInputLayout)addsup_dialog.findViewById(R.id.tl_gstin);
                final TextInputLayout tl_bname=(TextInputLayout)addsup_dialog.findViewById(R.id.tl_bname);
                final TextInputLayout tl_contact=(TextInputLayout)addsup_dialog.findViewById(R.id.tl_contact);
                final TextInputLayout tl_mail=(TextInputLayout)addsup_dialog.findViewById(R.id.tl_mail);
                final TextInputLayout tl_name=(TextInputLayout)addsup_dialog.findViewById(R.id.tl_name);
                final TextInputLayout tl_addr=(TextInputLayout)addsup_dialog.findViewById(R.id.tl_addr);
                final TextInputLayout tl_pin=(TextInputLayout)addsup_dialog.findViewById(R.id.tl_pin);

                FloatingActionButton sup_save=(FloatingActionButton)addsup_dialog.findViewById(R.id.sup_save);
                sup_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String s_gst=e_gst.getText().toString();
                        String s_bname=e_bname.getText().toString();
                        String s_contact=e_contact.getText().toString();
                        String s_mail=e_mail.getText().toString();
                        String s_name=e_name.getText().toString();
                        String s_addr=e_addr.getText().toString();
                        String s_pin=e_pin.getText().toString();
                        if(validatetext(s_bname)){
                            tl_bname.setErrorEnabled(false);
                        }else{
                            tl_bname.setError("Invalid Name");
                            return;
                        }
                        if(validatePhno(s_contact)){
                            tl_contact.setErrorEnabled(false);
                        }else{
                            tl_contact.setError("Invalid contact number");
                            return;
                        }
                        if(validateEmail(s_mail)){
                            tl_mail.setErrorEnabled(false);
                        }
                        else{
                            tl_mail.setError("Invalid Mail ID");
                            return;
                        }
                        if(validatetext(s_name)){
                            tl_name.setErrorEnabled(false);
                        }else{
                            tl_name.setError("Invalid Name");
                            return;
                        }if(validatetext(s_addr)){
                            tl_addr.setErrorEnabled(false);
                        }else{
                            tl_addr.setError("Invalid address");
                            return;
                        }
                        if(validatetext(s_pin)){
                            tl_pin.setErrorEnabled(false);
                        }else{
                            tl_pin.setError("Invalid Pincode");
                            return;
                        }
                        //Validation complete
                        TinyDB database=new TinyDB(getActivity());
                        int i=database.getInt("s_count");
                        db.addSupplier(new Sup_Model(i,type,s_gst,s_bname,s_contact,s_mail,s_name,s_addr+", "+state+", "+country+" - "+s_pin));
                        database.putInt("s_count",i+1);
                        Toast.makeText(getActivity(), "Supplier added successfully", Toast.LENGTH_SHORT).show();
                        supplierList.clear();
                        emptyText.setVisibility(GONE);
                        mAdapter.notifyDataSetChanged();
                        showSuppliers();
                        addsup_dialog.dismiss();
                    }
                });

            }
        });
    }
    private void showSuppliers() {
        final List<Sup_Model> items = db.getAllSuppliers();
        for (Sup_Model item : items) {
            supplierList.add(item);
        }
        mAdapter.notifyDataSetChanged();
    }

    public boolean validatetext(String s){
        return s.length()>0;
    }
    public boolean validateEmail(String s){
        return android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches();
    }
    public boolean validatePhno(String s){
        if(s.length()<10){
            return false;
        }
        return true;
    }
    private int convertDpToPx(int dp){
        return Math.round(dp * (getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}

