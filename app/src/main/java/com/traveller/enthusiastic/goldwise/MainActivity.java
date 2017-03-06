package com.traveller.enthusiastic.goldwise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.volley.NukeSSLCerts;
import com.traveller.enthusiastic.goldwise.helper.NavigationHelper;
import com.traveller.enthusiastic.goldwise.helper.Utility;
import com.traveller.enthusiastic.networkUtils.Volly;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.security.cert.CertificateException;

public  class MainActivity extends AppCompatActivity {
    Toolbar myToolbar;
    NavigationHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
       // new NukeSSLCerts().nuke();
        try {
            Volly.init(getApplication(),"saudasadaf@gmail.com");
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (java.security.cert.CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //  new NukeSSLCerts().nuke();


    }
    protected void init() {
        myToolbar   = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator( getResources().getDrawable(R.drawable.back) );
        setTitleToolbar("Retirement Goal");
        helper = new NavigationHelper(this);
        helper.gotoFragment(getString(R.string.nav_tab),null);

    }
    protected   void setTitleToolbar(String title){
        if(getSupportActionBar()!= null) {
            getSupportActionBar().setTitle(title);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Utility.showToast(getBaseContext(),"Home");
                finish();
                return true;
            case R.id.menu_setting:
                Utility.showToast(getBaseContext(),"Setting");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
