package co.mastersindia.autotax;

import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
;

public class SetupUnitActivity extends AppCompatActivity {
    private TinyDB database;
    private String busname,constn,nature,type,state,status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_unit);
        database=new TinyDB(this);

        //Business list to business spinner
        ArrayList<String>busArray=new ArrayList<>();
        busArray=database.getListString("business_list");
        ArrayList<String>busNameArray=new ArrayList<>();
        for(int i=0;i<busArray.size();i++){
            ArrayList<String>temp=new ArrayList<>();
            temp=database.getListString(busArray.get(i));
            String name=temp.get(0);
            busNameArray.add(name);
        }
        Spinner sel_busunit=(Spinner)findViewById(R.id.spin_busunit);

        ArrayList<Map<String, String>> data = new ArrayList<Map<String, String>>();
        for(int i=0;i<busNameArray.size();i++){
            Map<String, String> datum = new HashMap<String, String>(2);
            datum.put("name", busNameArray.get(i));
            datum.put("pan", busArray.get(i));
            data.add(datum);
        }
        SimpleAdapter adapter = new SimpleAdapter(SetupUnitActivity.this, data, android.R.layout.simple_list_item_2, new String[] {"name", "pan"}, new int[] {android.R.id.text1, android.R.id.text2});
        sel_busunit.setAdapter(adapter);

        //TextInput layout and edittext validation
        final TextInputLayout t_gstin=(TextInputLayout)findViewById(R.id.but_gstin);
        final TextInputLayout t_buname=(TextInputLayout)findViewById(R.id.but_buname);
        final TextInputLayout t_gstname=(TextInputLayout)findViewById(R.id.but_gstname);
        final TextInputLayout t_gover=(TextInputLayout)findViewById(R.id.but_gover);

        final EditText e_gstin=(EditText)findViewById(R.id.bue_gstin);
        final EditText e_buname=(EditText)findViewById(R.id.bue_buname);
        final EditText e_gstname=(EditText)findViewById(R.id.bue_gstname);
        final EditText e_gover=(EditText)findViewById(R.id.bue_gover);

        Spinner s_const=(Spinner)findViewById(R.id.spin_const);
        Spinner s_nature=(Spinner)findViewById(R.id.spin_nature);
        Spinner s_type=(Spinner)findViewById(R.id.spin_type);
        Spinner s_state=(Spinner)findViewById(R.id.spin_state);
        Spinner s_status=(Spinner)findViewById(R.id.spin_status);
        final ArrayList<String> finalBusArray = busArray;
        sel_busunit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                busname= finalBusArray.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        s_const.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                constn=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        s_nature.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                nature=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        s_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        s_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                state=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        s_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                status=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        e_gstin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(validateGSTIN(""+charSequence)){
                    t_gstin.setErrorEnabled(false);
                    Drawable img = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_done_correct,null);
                    e_gstin.setCompoundDrawablesWithIntrinsicBounds( null, null, img, null);
                }
                else{
                    t_gstin.setError("Invalid GSTIN");
                    Drawable img = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_close_error,null);
                    e_gstin.setCompoundDrawablesWithIntrinsicBounds( null, null, img, null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        FloatingActionButton b_done=(FloatingActionButton)findViewById(R.id.fab_done);
        b_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateGSTIN(e_gstin.getText().toString())){
                    t_gstin.setErrorEnabled(false);
                }
                else{
                    t_gstin.setError("Invalid GSTIN");
                    return;
                }
                if(validatetext(e_buname.getText().toString())){
                    t_buname.setErrorEnabled(false);
                }
                else{
                    t_buname.setError("Invalid Business unit name");
                    return;
                }
                if(validatetext(e_gstname.getText().toString())){
                    t_gstname.setErrorEnabled(false);
                }
                else{
                    t_gstname.setError("Invalid GST username");
                    return;
                }
                if(validatetext(e_gover.getText().toString())){
                    t_gover.setErrorEnabled(false);
                }
                else{
                    t_gover.setError("Invalid turnover");
                    return;
                }
                //Save Business unit data
                String gstin=e_gstin.getText().toString();
                String buname=e_buname.getText().toString();
                String gstuname=e_gstname.getText().toString();
                String tover=e_gover.getText().toString();
                ArrayList<String>buArray=new ArrayList<String>();
                buArray.add(gstin);
                buArray.add(buname);
                buArray.add(gstuname);
                buArray.add(tover);
                buArray.add(busname);
                buArray.add(constn);
                buArray.add(nature);
                buArray.add(type);
                buArray.add(state);
                buArray.add(status);

                String bname=busname+"_unit";
                ArrayList<String>bArray=new ArrayList<String>();
                bArray=database.getListString(bname);
                if(bArray.contains(gstin)){
                    Toast.makeText(SetupUnitActivity.this, "Business with same GSTIN already exists", Toast.LENGTH_SHORT).show();
                }
                else{
                    bArray.add(gstin);
                    database.putListString(bname,bArray);
                    database.putListString(gstin,buArray);
                    Toast.makeText(SetupUnitActivity.this, "Business unit added", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean validateGSTIN(String s){
        Pattern pattern = Pattern.compile("[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}");//12ASDFG3332S1Z1
        Matcher matcher = pattern.matcher(s);
        if (matcher.matches()) {
            return true;
        }
        else{
            return false;
        }
    }
    public boolean validatetext(String s){
        return s.length()>0;
    }
}
