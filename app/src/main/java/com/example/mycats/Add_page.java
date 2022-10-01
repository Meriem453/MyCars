package com.example.mycats;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.textfield.TextInputEditText;

public class Add_page extends AppCompatActivity {

    TextInputEditText edit_name;
    TextInputEditText edit_color;
    TextInputEditText edit_dpl;
    TextInputEditText edit_desc;
    ImageView edit_image;
    Button btn_ok;
   Button tab_delete,tab_edit,tab_photo,tab_exit;
    String name,color,dpl,desc;
    String img;

    Car new_car=null;

    static final int REQ_CODE=5;
    static final String NCAR_KEY="new_car";
    static final String CCAR_KEY="current_car";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);


        edit_image=findViewById(R.id.edit_img);
        edit_name=findViewById(R.id.enter_name);
        edit_color=findViewById(R.id.enter_color);
        edit_dpl=findViewById(R.id.enter_dpl);
        edit_desc=findViewById(R.id.enter_desc);

        tab_delete=findViewById(R.id.tab_delete);
        tab_edit=findViewById(R.id.tab_edit);
        tab_photo=findViewById(R.id.tab_photo);
        tab_exit=findViewById(R.id.tab_exit);
        btn_ok=findViewById(R.id.btn_ok);
        btn_ok.setVisibility(View.INVISIBLE);
        // to prevent the user change datas
        edit_desc.setEnabled(false);
        edit_dpl.setEnabled(false);
        edit_color.setEnabled(false);
        edit_name.setEnabled(false);

      //  to let the user change datas
                   tab_edit.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           edit_desc.setEnabled(true);
                           edit_dpl.setEnabled(true);
                           edit_color.setEnabled(true);
                           edit_name.setEnabled(true);
                           btn_ok.setVisibility(View.VISIBLE);}
                  });
        //to save the new datas
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edit_desc.setEnabled(false);
                edit_dpl.setEnabled(false);
                edit_color.setEnabled(false);
                edit_name.setEnabled(false);
                btn_ok.setVisibility(View.INVISIBLE);
                name = edit_name.getText().toString();
                color = edit_color.getText().toString();
                dpl = edit_dpl.getText().toString();
                desc = edit_desc.getText().toString();



            } });

        //to go back
        tab_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tnt=new Intent(getBaseContext(),Main_page.class);
                new_car=new Car(color,name,img,dpl,desc);
                tnt.putExtra(NCAR_KEY,new_car);
                setResult(RESULT_OK,tnt);
              startActivity(tnt);
            }
        });
     //to delete this car
        tab_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        //to bring a photo
        tab_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent tnt=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
               if(tnt.resolveActivity(getPackageManager()) != null){
                   startActivityForResult(tnt,REQ_CODE);
              }


            }
        });
        //to recieve the car's data

        /*edit_desc.setText(desc);
        edit_dpl.setText(dpl);
        edit_color.setText(color);
        edit_name.setText(name);
        edit_image.setImageBitmap(BitmapFactory.decodeFile(img));*/

        Intent editcar=getIntent();
        if(editcar!= null){
         Car current_car=(Car)editcar.getSerializableExtra(CCAR_KEY);
         if(current_car != null){
            edit_desc.setText(current_car.getDesc());
            edit_dpl.setText(current_car.getDpl());
            edit_color.setText(current_car.getColor());
            edit_name.setText(current_car.getName());
            edit_image.setImageBitmap(BitmapFactory.decodeFile(current_car.getImg()));

           /* name=current_car.getName();
            color=current_car.getColor();
            dpl=current_car.getDpl();
            desc=current_car.getDesc();
            img=current_car.getImg();*/
         }
        }




    }
  //to match the photo to the IV
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Add_page.REQ_CODE && resultCode==RESULT_OK){
            Uri uri=data.getData();
            if(uri !=null){
            String[] pc= {MediaStore.Images.Media.DATA};
            Cursor c=getContentResolver().query(uri,pc,null,null,null);
            c.moveToFirst();
            int ci=c.getColumnIndex(pc[0]);
            String pp=c.getString(ci);
            c.close();
            img=pp;
            edit_image.setImageBitmap(BitmapFactory.decodeFile(pp));}
        }

        }
}