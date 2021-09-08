package com.knglord.project;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class account extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    public ImageView profile;
    private Button bt;
    public EditText uname,number,email,address;
    public DatabaseReference mref;
    String mail;
    public String emailval,nameval,addval,numberval;
    private static String TAG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);
        FirebaseUser xuser = FirebaseAuth.getInstance().getCurrentUser();
        mail = xuser.getEmail();
        mref = FirebaseDatabase.getInstance().getReference("users");
        bt=findViewById(R.id.btnupdate);
        uname=findViewById(R.id.editname);
        number=findViewById(R.id.editnumber);
        email=findViewById(R.id.editemail);
        address=findViewById(R.id.editaddress);
        //email.setText(xuser.getEmail());
        editnmae();
        uploadimage();
        update();
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
                        Intent homeintent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(homeintent);
                        break;
                    case R.id.login:
                        Intent login=new Intent(getApplicationContext(),login.class);
                        startActivity(login);
                        break;
                    case R.id.Account:
                        drawer.closeDrawers();
                        break;
                    case R.id.Myorder:
                        Intent myorderintent = new Intent(account.this,myorder.class);
                        startActivity(myorderintent);
                        break;
                    case R.id.Pendingorder:
                        Intent pendingintent = new Intent(getApplicationContext(),pendingorder.class);
                        startActivity(pendingintent);
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
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                    if (Objects.equals(ds.child("email").getValue(), mail)) {
                        uname.setText(ds.child("name").getValue(String.class));
                        email.setText(ds.child("email").getValue(String.class));
                        number.setText(ds.child("number").getValue(String.class));
                        address.setText(ds.child("add").getValue(String.class));
                        //profile.setImageBitmap((Bitmap) ds.child("propic").getValue());
                    }

            }
            @Override
            public void onCancelled(DatabaseError error) {
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

    void uploadimage() {
        profile=(ImageView)findViewById(R.id.profilepic);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }
    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(account.this);
        builder.setTitle("Add Photo");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);
                    profile.setImageBitmap(bitmap);
                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 99, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                profile.setImageBitmap(thumbnail);
            }

        }
    }

    public void update() {
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addval= address.getText().toString();
                numberval= number.getText().toString();
                nameval= uname.getText().toString();
                //emailval= email.getText().toString();
                mref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren())
                            if (Objects.equals(ds.child("email").getValue(), mail)) {
                                Log.d(TAG,"name"+emailval);
                                ds.getRef().child("add").setValue(addval);
                                ds.getRef().child("name").setValue(nameval);
                                ds.getRef().child("number").setValue(numberval);
                                //ds.getRef().child("propic").setValue(profile);
                                //Log.d(TAG,"pic "+profile);
                            }
                        
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });
            }
        });

    }
}