package com.example.mycats;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Car_Adapter extends RecyclerView.Adapter<Car_Adapter.CarHolder> {
    Context c;
    onCardClickListener listener;
    ArrayList<Car> cars=new ArrayList<>();
    DataBase da;
    Car car=null;



    public Car_Adapter (Context c, onCardClickListener listener

    ){ this.c=c;
    this.listener =listener;
            da=new DataBase(c);
            cars=da.getCars();
    }

    @NonNull
    @Override
    public CarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.card_view,null,false);

       v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                listener.onCardClick(car);
            }
        });
        if(v!=null){
            CarHolder carHolder=new CarHolder(v);

            return carHolder;}
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CarHolder holder, int position) {

        if(cars.size()!=0){
        car =cars.get(position);}
        if(car!=null &&  holder!=null){
            this.car=car;
            holder.name.setText(car.getName());
            holder.color.setText(car.getColor());
            holder.dpl.setText(car.getDpl());
            holder.img.setImageBitmap(BitmapFactory.decodeFile(car.getImg()));  }
        else{
            Toast.makeText(c, "cars is null", Toast.LENGTH_SHORT).show();
        }


    }
    public void addCar(Car car){
        da.addCar(car);
        cars=da.getCars();
    }
    public void removeCar(Car car) {
        da.removeCar(car);
        cars=da.getCars();
    }

    @Override
    public int getItemCount() {

        return cars.size();
        }




    public class CarHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView color;
        TextView dpl;
        ImageView img;
        public CarHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.text_name);
            color=itemView.findViewById(R.id.text_color);
            dpl=itemView.findViewById(R.id.text_dpl);
            img=itemView.findViewById(R.id.image_view);
        }
    }



}
