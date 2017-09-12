package co.mastersindia.autotax.scanner;

/**
 * Created by Sandeep-PC on 9/7/2017.
 */
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import co.mastersindia.autotax.R;
import co.mastersindia.autotax.SettingsActivity;
import co.mastersindia.autotax.services.FloatingWidgetService;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.view.View.GONE;


public class SimpleScannerActivity extends BaseScannerActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    FloatingActionButton fDone,fRefresh;
    LinearLayout content_layout;
    TextView scanresulttext,codeType;
    String cookie="";
    private static int MY_REQUEST_CODE=1011;
    public static String PACKAGE_NAME;
    private SharedPreferences permissionStatus;
    private boolean sentToSettings = false;

    @Override
    public void onCreate(Bundle state) {

        super.onCreate(state);
        setContentView(R.layout.activity_simple_scanner);
        //setupToolbar();
        Bundle extras=getIntent().getExtras();
        if(extras!=null){
            cookie=extras.getString("cookie");
        }
        PACKAGE_NAME = getApplicationContext().getPackageName();
        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this);
        contentFrame.addView(mScannerView);
        fDone=(FloatingActionButton)findViewById(R.id.fab_done);
        fRefresh=(FloatingActionButton)findViewById(R.id.fab_refresh);
        scanresulttext=(TextView)findViewById(R.id.scanResultText);
        codeType=(TextView)findViewById(R.id.textType);
        content_layout=(LinearLayout)findViewById(R.id.contentControls);
        content_layout.setVisibility(GONE);
        permissionStatus = getSharedPreferences("permissionStatus",MODE_PRIVATE);

    }

    @Override
    public void onResume() {
        super.onResume();
        checkpermissions();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }
    public void checkpermissions(){
        if(ActivityCompat.checkSelfPermission(SimpleScannerActivity.this,Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(SimpleScannerActivity.this, Manifest.permission.CAMERA)) {
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(SimpleScannerActivity.this);
                builder.setTitle("Need Camera Permission");
                builder.setMessage("This app needs Camera permission to scan barcode.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(SimpleScannerActivity.this, new String[]{Manifest.permission.CAMERA}, MY_REQUEST_CODE);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else if (permissionStatus.getBoolean(Manifest.permission.CAMERA,false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(SimpleScannerActivity.this);
                builder.setTitle("Need Camera Permission");
                builder.setMessage("This app needs Camera permission to scan Barcode.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, MY_REQUEST_CODE);
                        Toast.makeText(getBaseContext(), "Go to Permissions to Grant Camera Permission", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                //just request the permission
                ActivityCompat.requestPermissions(SimpleScannerActivity.this, new String[]{Manifest.permission.CAMERA}, MY_REQUEST_CODE);
            }

            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(Manifest.permission.CAMERA,true);
            editor.apply();
        }
        else{
            mScannerView.setResultHandler(this);
            mScannerView.startCamera();
        }
    }

    @Override
    public void handleResult(final Result rawResult) {
//        Toast.makeText(this, "Contents = " + rawResult.getText() +
//                ", Format = " + rawResult.getBarcodeFormat().toString(), Toast.LENGTH_SHORT).show();
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        shakeItBaby();
        if(cookie.equals("just")){
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result",rawResult.getText());
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }
        else{
            scanresulttext.setText(""+rawResult.getText());
            codeType.setText("Barcode type: "+rawResult.getBarcodeFormat().toString());
            content_layout.setVisibility(View.VISIBLE);
            fDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result",rawResult.getText());
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
            });
            fRefresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    content_layout.setVisibility(GONE);
                    mScannerView.resumeCameraPreview(SimpleScannerActivity.this);
                }
            });
        }



        // Note:
        // * Wait 2 seconds to resume the preview.
        // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
        // * I don't know why this is the case but I don't have the time to figure out.
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mScannerView.resumeCameraPreview(SimpleScannerActivity.this);
//            }
//        }, 2000);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_REQUEST_CODE) {
            //Check if the permission is granted or not.
            if (resultCode == RESULT_OK) {
                mScannerView.setResultHandler(this);
                mScannerView.startCamera();
            } else { //Permission is not available
                Toast.makeText(this, "Camera permission not available. Cannot scan barcode.", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void shakeItBaby() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(150,10));
        } else {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(150);
        }
    }
}