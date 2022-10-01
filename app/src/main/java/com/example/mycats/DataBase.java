package com.example.mycats;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;
import android.widget.Toast;

import androidx.annotation.Nullable;



import java.util.ArrayList;

public class DataBase extends SQLiteOpenHelper {
private Context context;
    public DataBase(@Nullable Context context) {
        super(context,"cats", null, 1);
        this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL("CREATE TABLE 'cars' ('name' VARCHAR NOT NULL,'color' VARCHAR NOT NULL,'desci' VARCHAR,'image' VARCHAR,'dpl' VARCHAR NOT NULL );");
        }catch(SQLiteException e){
            Toast.makeText(context, "can not create the table", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public ArrayList<Car> getCars(){
        SQLiteDatabase dba=null;
        try {dba=getWritableDatabase();}
        catch(SQLiteException c){
            Toast.makeText(context, "can't find the db", Toast.LENGTH_SHORT).show();
        }
        ArrayList<Car> array=new ArrayList<>();
        Cursor c=dba.rawQuery("SELECT * FROM 'cars'",null);

       if(c.moveToFirst()){
            do{ String name=c.getString(0);
            String color=c.getString(1);
                String desc =c.getString(2);
                 String img=c.getString(3);
                String dpl=c.getString(4);
                Car newcar=new Car(color,name,img,dpl,desc);
                array.add(newcar);
            }while(c.moveToNext());
            c.close();
        }


        return array;

    }
    public boolean addCar (Car car){
        SQLiteDatabase db=null;
        try { db=getWritableDatabase();    }
        catch(SQLiteException a){
            Toast.makeText(context, "can't find a db", Toast.LENGTH_SHORT).show();
            return false;
        }

        ContentValues cv=new ContentValues();
        cv.put("name",car.getName());
        cv.put("color",car.getColor());
        cv.put("desci",car.getDesc());
        cv.put("image",car.getImg());
        cv.put("dpl",car.getDpl());
        if(db.insert("cars",null,cv) == 0){
            Toast.makeText(context,"can't insert the car",Toast.LENGTH_LONG);
            return false;}

        return true;
    }
public boolean removeCar(Car car){

    SQLiteDatabase db=null;
    try { db=getWritableDatabase();    }
    catch(SQLiteException a){
        Toast.makeText(context, "can't find a db", Toast.LENGTH_SHORT).show();
        return false;
    }
    String args []={car.name};
    if(db.delete("cars","name=?",args)==0){
        Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
        return false;

    }
    return true;
}






}
