package co.mastersindia.autotax;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import co.mastersindia.autotax.fragments.BusinessManagement;
import co.mastersindia.autotax.fragments.directories.Customer;
import co.mastersindia.autotax.fragments.directories.Items;
import co.mastersindia.autotax.fragments.directories.Suppliers;
import co.mastersindia.autotax.fragments.directories.Tax;
import co.mastersindia.autotax.fragments.invoice.CreateInvoice;
import co.mastersindia.autotax.fragments.invoice.ViewInvoice;
import co.mastersindia.autotax.services.FloatingViewService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public final static int REQUEST_CODE = 10101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        displaySelectedScreen(R.id.nav_createinvoice);

        Bundle extras=getIntent().getExtras();
        if(extras!=null){
            String fraginfo=extras.getString("key");
            if(fraginfo.equals("items")){
                displaySelectedScreen(R.id.nav_items);
            }
            else if(fraginfo.equals("invoice")){
                displaySelectedScreen(R.id.nav_createinvoice);
            }
            else if(fraginfo.equals("business")){
                displaySelectedScreen(R.id.nav_busmgmt);
            }
        }

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        displaySelectedScreen(item.getItemId());
        return true;
    }
    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_createinvoice:
                fragment = new CreateInvoice();
                break;
            case R.id.nav_viewinvoice:
                fragment = new ViewInvoice();
                break;
            case R.id.nav_tax:
                fragment = new Tax();
                break;
            case R.id.nav_items:
                fragment = new Items();
                break;
            case R.id.nav_busmgmt:
                fragment = new BusinessManagement();
                break;
            case R.id.nav_settings:
                Intent i=new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(i);
                break;
            case R.id.nav_customers:
                fragment = new Customer();
                break;
            case R.id.nav_suppliers:
                fragment = new Suppliers();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.content_frame, fragment); // newInstance() is a static factory method.
            transaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}
