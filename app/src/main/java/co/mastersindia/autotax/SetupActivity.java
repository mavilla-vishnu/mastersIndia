package co.mastersindia.autotax;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetupActivity extends AppCompatActivity {
    private TinyDB database;
    private String constitution="";
    private String type="";
    private String GSTINFORMAT_REGEX = "[0-9]{2}[a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}[1-9A-Za-z]{1}[Z]{1}[0-9a-zA-Z]{1}";
    private String GSTIN_CODEPOINT_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        database=new TinyDB(SetupActivity.this);

        //Check and Setup null array
        if(database.getListString("business_list")==null){
            ArrayList<String> temparray=new ArrayList<>();
            database.putListString("business_list",temparray);
        }

        Intent i=getIntent();
        final String cookie=i.getStringExtra("cookie");

        final TextInputLayout tset_name=(TextInputLayout)findViewById(R.id.tset_name);
        final TextInputLayout tset_gstin=(TextInputLayout)findViewById(R.id.tset_gstin);
        final TextInputLayout tset_dor=(TextInputLayout)findViewById(R.id.tset_dor);
        final EditText eset_name=(EditText)findViewById(R.id.eset_name);
        final EditText eset_gstin=(EditText)findViewById(R.id.eset_gstin);
        final EditText eset_dor=(EditText)findViewById(R.id.eset_dor);
        final Spinner s_const=(Spinner)findViewById(R.id.s_const);
        final Spinner s_type=(Spinner)findViewById(R.id.s_type);
        s_const.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                constitution=adapterView.getItemAtPosition(i).toString();
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
        eset_dor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    Calendar mcurrentDate=Calendar.getInstance();
                    int mYear=mcurrentDate.get(Calendar.YEAR);
                    int mMonth=mcurrentDate.get(Calendar.MONTH);
                    int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog mDatePicker=new DatePickerDialog(SetupActivity.this, new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                            // TODO Auto-generated method stub
                            String day="";String month="";
                            if(selectedday<10){day="0"+selectedday;}else{day=""+selectedday;}
                            if(selectedmonth<10){month="0"+selectedmonth;}else{month=""+selectedmonth;}
                            eset_dor.setText(""+day+"/"+month+"/"+selectedyear);
                            eset_dor.setSelection(eset_dor.getText().toString().length());
                            if(validatedate(""+eset_dor.getText().toString())){
                                tset_dor.setErrorEnabled(false);
                                Drawable img = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_done_correct,null);
                                eset_dor.setCompoundDrawablesWithIntrinsicBounds( null, null, img, null);
                            }else{
                                Drawable img = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_close_error,null);
                                eset_dor.setCompoundDrawablesWithIntrinsicBounds( null, null, img, null);
                            }
                        }
                    },mYear, mMonth, mDay);
                    mDatePicker.setTitle("Select date");
                    mDatePicker.show();
                }
            }
        });

        eset_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(validatetext(""+charSequence)){
                    tset_name.setErrorEnabled(false);
                    Drawable img = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_done_correct,null);
                    eset_name.setCompoundDrawablesWithIntrinsicBounds( null, null, img, null);
                }else{
                    Drawable img = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_close_error,null);
                    eset_name.setCompoundDrawablesWithIntrinsicBounds( null, null, img, null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        eset_gstin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(validateGSTIN(""+charSequence)){
                    tset_gstin.setErrorEnabled(false);
                    Drawable img = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_done_correct,null);
                    eset_gstin.setCompoundDrawablesWithIntrinsicBounds( null, null, img, null);
                }else{
                    Drawable img = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_close_error,null);
                    eset_gstin.setCompoundDrawablesWithIntrinsicBounds( null, null, img, null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        eset_dor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(validatedate(""+charSequence)){
                    tset_dor.setErrorEnabled(false);
                    Drawable img = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_done_correct,null);
                    eset_dor.setCompoundDrawablesWithIntrinsicBounds( null, null, img, null);
                }else{
                    Drawable img = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_close_error,null);
                    eset_dor.setCompoundDrawablesWithIntrinsicBounds( null, null, img, null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Button bnext=(Button)findViewById(R.id.set_bnext);
        bnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validatetext(eset_name.getText().toString())){
                    tset_name.setErrorEnabled(false);
                }
                else{
                    tset_name.setError("Invalid name!");
                    return;
                }
                if(validateGSTIN(eset_gstin.getText().toString().toUpperCase())){
                    tset_gstin.setErrorEnabled(false);
                }
                else{
                    tset_gstin.setError("Invalid GSTIN number!");
                    return;
                }
                if(validatedate(eset_dor.getText().toString())){
                    tset_dor.setErrorEnabled(false);
                }
                else{
                    tset_dor.setError("Invalid DOR. DD/MM/YYYY!");
                    return;
                }

                //Save business data
                ArrayList<String> busArray=new ArrayList<>();
                busArray=database.getListString("business_list");
                if(busArray.contains(eset_gstin.getText().toString())){
                    Toast.makeText(SetupActivity.this, "Business already exists", Toast.LENGTH_SHORT).show();
                }
                else{
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    Date now = new Date();
                    String datenow = formatter.format(now);
                    ArrayList<String> busTemp=new ArrayList<>();
                    busTemp.add(eset_name.getText().toString());//0
                    busTemp.add(eset_gstin.getText().toString());//1
                    busTemp.add(eset_dor.getText().toString());//2
                    busTemp.add(constitution);//3
                    busTemp.add(type);//4
                    busTemp.add(datenow);//5
                    //Save business details with name
                    busArray.add(""+eset_gstin.getText().toString());
                    database.putListString("business_list",busArray);
                    database.putListString(""+eset_gstin.getText().toString(),busTemp);
                    database.putBoolean("setup",true);
                    Toast.makeText(SetupActivity.this, "Business added", Toast.LENGTH_SHORT).show();
                    if(cookie==null){
                        Intent intent = new Intent(SetupActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        finish();
                    }
                }

            }
        });
    }

    public boolean validatetext(String s){
        return s.length()>0;
    }
    public boolean validateGSTIN(String s){
        boolean isValidFormat = false;
        if (checkPattern(s, GSTINFORMAT_REGEX)) {
            try {
                isValidFormat = verifyCheckDigit(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return isValidFormat;

    }
    private boolean verifyCheckDigit(String gstinWCheckDigit) throws Exception {
        Boolean isCDValid = false;
        String newGstninWCheckDigit = getGSTINWithCheckDigit(
                gstinWCheckDigit.substring(0, gstinWCheckDigit.length() - 1));

        if (gstinWCheckDigit.trim().equals(newGstninWCheckDigit)) {
            isCDValid = true;
        }
        return isCDValid;
    }
    public boolean checkPattern(String inputval, String regxpatrn) {
        boolean result = false;
        if ((inputval.trim()).matches(regxpatrn)) {
            result = true;
        }
        return result;
    }

    /**
     * Method to get the check digit for the gstin (without checkdigit)
     *
     * @param gstinWOCheckDigit
     * @return : GSTIN with check digit
     * @throws Exception
     */
    public String getGSTINWithCheckDigit(String gstinWOCheckDigit) throws Exception {
        int factor = 2;
        int sum = 0;
        int checkCodePoint = 0;
        char[] cpChars;
        char[] inputChars;

        try {
            if (gstinWOCheckDigit == null) {
                throw new Exception("GSTIN supplied for checkdigit calculation is null");
            }
            cpChars = GSTIN_CODEPOINT_CHARS.toCharArray();
            inputChars = gstinWOCheckDigit.trim().toUpperCase().toCharArray();

            int mod = cpChars.length;
            for (int i = inputChars.length - 1; i >= 0; i--) {
                int codePoint = -1;
                for (int j = 0; j < cpChars.length; j++) {
                    if (cpChars[j] == inputChars[i]) {
                        codePoint = j;
                    }
                }
                int digit = factor * codePoint;
                factor = (factor == 2) ? 1 : 2;
                digit = (digit / mod) + (digit % mod);
                sum += digit;
            }
            checkCodePoint = (mod - (sum % mod)) % mod;
            return gstinWOCheckDigit + cpChars[checkCodePoint];
        } finally {
            inputChars = null;
            cpChars = null;
        }
    }
    public boolean validatedate(String s){
        return s.length()>=10;
    }


}
