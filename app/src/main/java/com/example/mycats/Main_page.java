package com.example.mycats;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class Main_page extends AppCompatActivity  {


    RecyclerView rv;
    SearchView s;
    Car_Adapter ca;
   static final int  REQ_CODE_PERMISSION=1;
    static final int  REQ_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
      Button add_btn=findViewById(R.id.btn_add);

        //create the recycler view
        rv=findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager lm;
        lm=new GridLayoutManager(getBaseContext(),2);
        rv.setLayoutManager(lm);

        //get the perm. to accesse the photos
        int a= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int b=PackageManager.PERMISSION_GRANTED;
        if( a != b ){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},REQ_CODE_PERMISSION);
        }
        //create new adapter
        ca=new Car_Adapter(getBaseContext(), new onCardClickListener() {
            @Override
            public void onCardClick(Car car) {
                Intent tnt=new Intent(getBaseContext(),Add_page.class);
                tnt.putExtra(Add_page.CCAR_KEY,car);
                ca.removeCar(car);
                ca.notifyDataSetChanged();
                startActivity(tnt);
            }
        });
         rv.setAdapter(ca);
         //to edit the car
        Intent editcar=getIntent();
        if(editcar!= null){
            Car current_car=(Car)editcar.getSerializableExtra(Add_page.NCAR_KEY);
            if(current_car != null){
                ca.addCar(current_car);
                ca.notifyDataSetChanged();
            }

    }
         //to add a car
      add_btn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent tnt=new Intent(getBaseContext(),Add_page.class);
                  startActivityForResult(tnt,REQ_CODE);

          }

      });



}



    //to create and control the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        s= (SearchView) menu.findItem(R.id.search).getActionView();
        s.setSubmitButtonEnabled(true);
        s.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        return false;
    }


    //to exit the app when the perm. is denied


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if ((requestCode == REQ_CODE_PERMISSION) && (grantResults.length > 0) && (grantResults[0] != PackageManager.PERMISSION_GRANTED)) {
            System.exit(0);
        } }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         if(requestCode==Main_page.REQ_CODE  &&  resultCode==RESULT_OK){

          Car new_car=(Car) data.getSerializableExtra(Add_page.NCAR_KEY);
          if(new_car!=null){
          ca.addCar(new_car);
             ca.notifyDataSetChanged();
          }
         } }
}
