package co.mastersindia.autotax;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {
    private TinyDB database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        database=new TinyDB(LoginActivity.this);
        Button bRegister=(Button)findViewById(R.id.b_register);
        Button bLogin=(Button)findViewById(R.id.b_login);
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean setupCookie=database.getBoolean("setup");
                if(setupCookie){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent = new Intent(LoginActivity.this, SetupActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }
}
