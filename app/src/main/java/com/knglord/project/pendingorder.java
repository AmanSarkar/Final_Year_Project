package com.knglord.project;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

public class pendingorder extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private TextView et;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder);
        editnmae();
        drawerLayout = (DrawerLayout) findViewById(R.id.dl);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open, R.string.close);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        //drawerLayout.clearFocus();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        toolbar.setTitle("My Account");
        setSupportActionBar(toolbar);
        toolbar.setCollapsible(false);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id= menuItem.getItemId();
                DrawerLayout drawer = findViewById(R.id.dl);
                switch (id) {
                    case R.id.Home:
                        Intent homeintent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(homeintent);
                        break;
                    case R.id.login:
                        Intent login=new Intent(getApplicationContext(),login.class);
                        startActivity(login);
                        break;
                    case R.id.Account:
                        Intent accountintent = new Intent(getApplicationContext(), account.class);
                        startActivity(accountintent);
                        break;
                    case R.id.Myorder:
                        Intent myorderintent = new Intent(getApplicationContext(),myorder.class);
                        break;
                    case R.id.Pendingorder:
                        drawer.closeDrawers();
                        break;
                    case R.id.Helpcenter:
                        Intent helpintent = new Intent(getApplicationContext(),helpcenter.class);
                        startActivity(helpintent);
                        break;
                    case R.id.Myref:
                        Intent myrefintent = new Intent(getApplicationContext(),myref.class);
                        startActivity(myrefintent);
                        break;
                    case R.id.Aboutus:
                        Intent aboutintent = new Intent(getApplicationContext(), aboutus.class);
                        startActivity(aboutintent);
                        break;
                    case R.id.Logout:
                        drawer.closeDrawers();
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });
    }

    private void editnmae() {
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }
}
