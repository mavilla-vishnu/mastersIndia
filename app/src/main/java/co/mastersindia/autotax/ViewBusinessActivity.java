package co.mastersindia.autotax;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewBusinessActivity extends AppCompatActivity {
    private TinyDB database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_business);
        database=new TinyDB(this);
        Intent intent=getIntent();
        String key=intent.getStringExtra("busname");
        ArrayList busArray=new ArrayList();
        busArray=database.getListString(""+key);
        String name=busArray.get(0).toString();
        String pan=busArray.get(1).toString();
        String dor=busArray.get(2).toString();
        String sconst=busArray.get(3).toString();
        String type=busArray.get(4).toString();
        String cdate=busArray.get(5).toString();

        TextView vb_name=(TextView)findViewById(R.id.busview_name);vb_name.setText(name);
        TextView vb_pan=(TextView)findViewById(R.id.busview_pan);vb_pan.setText(pan);
        TextView vb_dor=(TextView)findViewById(R.id.busview_dor);vb_dor.setText(dor);
        TextView vb_const=(TextView)findViewById(R.id.busview_const);vb_const.setText(sconst);
        TextView vb_type=(TextView)findViewById(R.id.busview_type);vb_type.setText(type);
        TextView vb_cdate=(TextView)findViewById(R.id.busview_cdate);vb_cdate.setText(cdate);


    }
}
