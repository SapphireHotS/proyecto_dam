package com.example.sgh;

import android.graphics.Bitmap;
import android.media.Image;
import android.widget.ImageView;

public class Producto {
    private String nombre;
    private Bitmap imagen;

    public Producto(){
        nombre=null;
        imagen=null;
    }

    public Producto(String nombre, Bitmap imagen){
        this.nombre=nombre;
        this.imagen=imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }
}
