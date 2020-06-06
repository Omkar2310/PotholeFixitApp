package com.example.sample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class cam extends AppCompatActivity {
    ImageView imgv;
    Button btn;
    String addresss;
    String currentphotopath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam);
        imgv=(ImageView)findViewById(R.id.imgcam);
        btn=(Button)findViewById(R.id.btncam);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Intent in=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //startActivityForResult(in,0);

                String fileName = "photo";
                File sto = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                try {
                    File imageFile = File.createTempFile(fileName, ".jpg", sto);
                    currentphotopath=imageFile.getAbsolutePath();

                    Uri uri=FileProvider.getUriForFile(cam.this,"com.example.sample.fileprovider",imageFile);
                    Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
                    startActivityForResult(intent,1);
                } catch (Exception ioo) {

                }
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
     //   Bitmap bitmap=(Bitmap)data.getExtras().get("data");
       // imgv.setImageBitmap(bitmap);
        Bitmap bitmap= BitmapFactory.decodeFile(currentphotopath);
        imgv.setImageBitmap(bitmap);
        /*if (requestCode==1 && requestCode == RESULT_OK)
        {
           bitmap= BitmapFactory.decodeFile(currentphotopath);
            imgv.setImageBitmap(bitmap);
            Toast.makeText(getApplicationContext(),"fsdf",Toast
            .LENGTH_LONG);
        }*/


        Button th=(Button)findViewById(R.id.btncam);
        th.setText("Proceed");
        th.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(ActivityCompat.checkSelfPermission(cam.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(cam.this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION}, 101);

                }

        gprec mGPS=new gprec(cam.this);
        if(mGPS.canGetLocation ){
            mGPS.getLocation();
          //  ty.setText("Lat"+mGPS.getLatitude()+"Lon"+mGPS.getLongitude());

            if(mGPS.getLatitude()==Double.parseDouble("0.0") && mGPS.getLongitude()==Double.parseDouble("0.0") )
            {
                int i;
                   for( i=0;i<10;i++)
                   {
                        gprec sa=new gprec(cam.this);
                            if(sa.canGetLocation )
                            {
                                sa.getLocation();
                                if(sa.getLatitude()!=Double.parseDouble("0.0") && sa.getLongitude()!=Double.parseDouble("0.0") )
                                {

                                    Geocoder geo;
                                    List<Address> addressList;
                                    geo=new Geocoder(cam.this, Locale.getDefault());

                                    try {
                                        addressList=geo.getFromLocation(sa.getLatitude(),sa.getLongitude(),1);
                                        String add=addressList.get(0).getAddressLine(0);
                                        String area=addressList.get(0).getLocality();
                                        String city=addressList.get(0).getAdminArea();
                                        String country=addressList.get(0).getCountryName();
                                        String postal=addressList.get(0).getPostalCode();
                                        addresss=add+", "+area+", "+city+", "+country+", "+postal;
                                        Toast.makeText(getApplicationContext(),addresss,Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(),Main3Activity.class));
                                        break;

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
                   }
                    if(i==10){
                        ((Button) findViewById(R.id.btncam)).setText("Try Again");
                        Toast.makeText(getApplicationContext(),"can't get location",Toast.LENGTH_SHORT).show();
                    }
            }
            else{

                Geocoder geo;
                List<Address> addressList;
                geo=new Geocoder(cam.this, Locale.getDefault());

                try {
                    addressList=geo.getFromLocation(mGPS.getLatitude(),mGPS.getLongitude(),1);
                    String add=addressList.get(0).getAddressLine(0);
                    String area=addressList.get(0).getLocality();
                    String city=addressList.get(0).getAdminArea();
                    String country=addressList.get(0).getCountryName();
                    String postal=addressList.get(0).getPostalCode();
                    addresss=add+", "+area+", "+city+", "+country+", "+postal;
                    Toast.makeText(getApplicationContext(),addresss,Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),Main3Activity.class));

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        }
        else{
            //ty.setText("Unabletofind");

            if(ActivityCompat.checkSelfPermission(cam.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(cam.this, new String[]
                        {Manifest.permission.ACCESS_FINE_LOCATION}, 101);

            }
            new gprec(cam.this).showSettingsAlert();
          // Button b= ((Button) findViewById(R.id.btncam)).setText("try again");
          // b.setOnClickListener(new View.OnClickListener() {
            //   @Override
              // public void onClick(View v) {


                   mGPS=new gprec(cam.this);
                   if(mGPS.canGetLocation ){
                       mGPS.getLocation();
                       //  ty.setText("Lat"+mGPS.getLatitude()+"Lon"+mGPS.getLongitude());

                       if(mGPS.getLatitude()==Double.parseDouble("0.0") && mGPS.getLongitude()==Double.parseDouble("0.0") )
                       {
                           int i;
                           for( i=0;i<10;i++)
                           {
                               gprec sa=new gprec(cam.this);
                               if(sa.canGetLocation )
                               {
                                   sa.getLocation();
                                   if(sa.getLatitude()!=Double.parseDouble("0.0") && sa.getLongitude()!=Double.parseDouble("0.0") )
                                   {

                                       Geocoder geo;
                                       List<Address> addressList;
                                       geo=new Geocoder(cam.this, Locale.getDefault());

                                       try {
                                           addressList=geo.getFromLocation(sa.getLatitude(),sa.getLongitude(),1);
                                           String add=addressList.get(0).getAddressLine(0);
                                           String area=addressList.get(0).getLocality();
                                           String city=addressList.get(0).getAdminArea();
                                           String country=addressList.get(0).getCountryName();
                                           String postal=addressList.get(0).getPostalCode();
                                           addresss=add+", "+area+", "+city+", "+country+", "+postal;
                                           Toast.makeText(getApplicationContext(),addresss,Toast.LENGTH_LONG).show();
                                           break;

                                       } catch (IOException e) {
                                           e.printStackTrace();
                                       }

                                   }
                               }
                           }
                           if(i==10){
                               Toast.makeText(getApplicationContext(),"can't get location",Toast.LENGTH_SHORT).show();
                           }
                       }
                       else{


                           Geocoder geo;
                           List<Address> addressList;
                           geo=new Geocoder(cam.this, Locale.getDefault());

                           try {
                               addressList=geo.getFromLocation(mGPS.getLatitude(),mGPS.getLongitude(),1);
                               String add=addressList.get(0).getAddressLine(0);
                               String area=addressList.get(0).getLocality();
                               String city=addressList.get(0).getAdminArea();
                               String country=addressList.get(0).getCountryName();
                               String postal=addressList.get(0).getPostalCode();
                               addresss=add+", "+area+", "+city+", "+country+", "+postal;
                               Toast.makeText(getApplicationContext(),addresss,Toast.LENGTH_LONG).show();

                           } catch (IOException e) {
                               e.printStackTrace();
                           }


                       }

                   }


               }
           //});

        }










        });
    }
}
