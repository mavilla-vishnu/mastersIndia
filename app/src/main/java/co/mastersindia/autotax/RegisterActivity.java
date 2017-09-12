package co.mastersindia.autotax;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    TextInputLayout fname,lname,email,pno,pass,cpass,bname;
    EditText e_fname,e_lname,e_email,e_pno,e_pass,e_cpass,e_bname;
    String busType="";
    RadioGroup typeGroup;
    Dialog busyDialog;public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        typeGroup=(RadioGroup)findViewById(R.id.r_type);
        busyDialog=new Dialog(RegisterActivity.this);
        busyDialog.setCancelable(false);
        busyDialog.setContentView(R.layout.busydialog_layout);
        typeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                RadioButton r=(RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
                busType=r.getText().toString();
            }
        });

        fname=(TextInputLayout) findViewById(R.id.tir_fname);
        lname=(TextInputLayout) findViewById(R.id.tir_lname);
        email=(TextInputLayout) findViewById(R.id.tir_email);
        pno=(TextInputLayout) findViewById(R.id.tir_pno);
        pass=(TextInputLayout) findViewById(R.id.tir_password);
        cpass=(TextInputLayout) findViewById(R.id.tir_cpassword);
        bname=(TextInputLayout) findViewById(R.id.tir_bname);

        e_fname=(EditText)findViewById(R.id.er_fname);
        e_lname=(EditText)findViewById(R.id.er_lname);
        e_email=(EditText)findViewById(R.id.er_email);
        e_pno=(EditText)findViewById(R.id.er_pno);
        e_pass=(EditText)findViewById(R.id.er_pass);
        e_cpass=(EditText)findViewById(R.id.er_cpass);
        e_bname=(EditText)findViewById(R.id.er_bname);

        e_fname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!validateText(""+charSequence)){
                    fname.setError("Invalid Name");
                    Drawable img = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_close_error,null);
                    e_fname.setCompoundDrawablesWithIntrinsicBounds( null, null, img, null);
                }
                else{
                    Drawable img = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_done_correct,null);
                    e_fname.setCompoundDrawablesWithIntrinsicBounds( null, null, img, null);
                    fname.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        e_lname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!validateText(""+charSequence)){
                    lname.setError("Invalid Name");
                    Drawable img = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_close_error,null);
                    e_lname.setCompoundDrawablesWithIntrinsicBounds( null, null, img, null);
                }
                else{
                    Drawable img = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_done_correct,null);
                    e_lname.setCompoundDrawablesWithIntrinsicBounds( null, null, img, null);
                    lname.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        e_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!validateEmail(""+charSequence)){
                    email.setError("Invalid Email");
                    Drawable img = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_close_error,null);
                    e_email.setCompoundDrawablesWithIntrinsicBounds( null, null, img, null);
                }
                else{
                    Drawable img = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_done_correct,null);
                    e_email.setCompoundDrawablesWithIntrinsicBounds( null, null, img, null);
                    email.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        e_pno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!validatePhno(""+charSequence)){
                    Drawable img = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_close_error,null);
                    e_pno.setCompoundDrawablesWithIntrinsicBounds( null, null, img, null);
                    pno.setError("Invalid Mobile");
                }
                else{
                    Drawable img = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_done_correct,null);
                    e_pno.setCompoundDrawablesWithIntrinsicBounds( null, null, img, null);
                    pno.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        e_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!validatePassword(""+charSequence)){
                    Drawable img = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_close_error,null);
                    e_pass.setCompoundDrawablesWithIntrinsicBounds( null, null, img, null);
                    pass.setError("Invalid.Min 6 characters required");
                }
                else{
                    Drawable img = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_done_correct,null);
                    e_pass.setCompoundDrawablesWithIntrinsicBounds( null, null, img, null);
                    pass.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        e_cpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!validateCPassword(e_pass.getText().toString(),""+charSequence)){
                    cpass.setError("Passwords does not match");
                    Drawable img = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_close_error,null);
                    e_cpass.setCompoundDrawablesWithIntrinsicBounds( null, null, img, null);
                }
                else{
                    Drawable img = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_done_correct,null);
                    e_cpass.setCompoundDrawablesWithIntrinsicBounds( null, null, img, null);
                    cpass.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        e_bname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!validateText(""+charSequence)){
                    Drawable img = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_close_error,null);
                    e_bname.setCompoundDrawablesWithIntrinsicBounds( null, null, img, null);
                    bname.setError("Invalid Business name");
                }
                else{
                    Drawable img = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_done_correct,null);
                    e_bname.setCompoundDrawablesWithIntrinsicBounds( null, null, img, null);
                    bname.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        FloatingActionButton register=(FloatingActionButton)findViewById(R.id.fab_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String s_fname=e_fname.getText().toString();
                final String s_lname=e_lname.getText().toString();
                final String s_email=e_email.getText().toString();
                final String s_pno=e_pno.getText().toString();
                final String s_pass=e_pass.getText().toString();
                String s_cpass=e_cpass.getText().toString();
                final String s_bname=e_bname.getText().toString();

                if(!validateText(s_fname)){
                    fname.setError("Invalid Name");
                    return;
                }
                else{
                    fname.setErrorEnabled(false);
                }
                if(!validateText(""+s_lname)){
                    lname.setError("Invalid Name");
                    return;
                }
                else{
                    lname.setErrorEnabled(false);
                }
                if(!validateEmail(""+s_email)){
                    email.setError("Invalid Email");
                    return;
                }
                else{
                    email.setErrorEnabled(false);
                }
                if(!validatePhno(""+s_pno)){
                    pno.setError("Invalid Mobile");
                    return;
                }
                else{
                    pno.setErrorEnabled(false);
                }
                if(!validatePassword(""+s_pass)){
                    pass.setError("Invalid.Min 6 characters required");
                    return;
                }
                else{
                    pass.setErrorEnabled(false);
                }
                if(!validateCPassword(e_pass.getText().toString(),""+s_cpass)){
                    cpass.setError("Passwords does not match");
                    return;
                }
                else{
                    cpass.setErrorEnabled(false);
                }
                if(!validateText(""+s_bname)){
                    bname.setError("Invalid Business name");
                    return;
                }
                else{
                    bname.setErrorEnabled(false);
                }

                //Validation success. make post request
                busyDialog.show();
                new AsyncTask<Void,Void,Void>(){
                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            OkHttpClient client = new OkHttpClient();
                            JSONObject json=new JSONObject();

                            JSONObject datajson=new JSONObject();
                            datajson.put("f_name",s_fname);
                            datajson.put("l_name",s_lname);
                            datajson.put("email",s_email);
                            datajson.put("password",s_pass);
                            datajson.put("mobile",s_pno);
                            datajson.put("business_type",busType);
                            datajson.put("business_name",s_bname);

                            json.put("User",datajson);

                            RequestBody body = RequestBody.create(JSON, json.toString());
                            Request request = new Request.Builder()
                                    .url("http://client.mastersindia.co/UserRegistration")
                                    .post(body)
                                    .build();
                            Response response = client.newCall(request).execute();

                            String finalResponse = response.body().string();
                            Log.e("LOGRESULT",finalResponse);
                            JSONObject result_object=new JSONObject(response.body().string());
                            String status=result_object.getJSONObject("status").toString();
                            Toast.makeText(RegisterActivity.this, ""+status, Toast.LENGTH_SHORT).show();busyDialog.dismiss();
                            Log.e("status",""+status);

                        }catch (Exception e){
                            Log.d("LoginActivity",e+"");
                        }
                        return null;
                    }
                }.execute();

            }
        });
    }
    public boolean validateText(String s){
        return !s.equals("");
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
    public boolean validatePassword(String s){
        if(s.length()<6){
            return false;
        }
        return true;
    }
    public boolean validateCPassword(String s1,String s2){
        if(!s1.equals(s2)){
            return false;
        }
        return true;
    }
}
