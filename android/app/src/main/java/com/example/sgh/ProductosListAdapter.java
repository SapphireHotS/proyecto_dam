package com.example.sgh;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ProductosListAdapter extends ArrayAdapter<Producto> {

    private static final String TAG = "ProductosListAdapter";
    private Context mContext;
    int mResource;

    public ProductosListAdapter(Context context, int resource, ArrayList<Producto> productos) {
        super(context, resource, productos);
        this.mContext = context;
        mResource= resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String nombreprod= getItem(position).getNombre();
        Bitmap imagen = getItem(position).getImagen();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView= inflater.inflate(mResource, parent, false);

        TextView nombreTV= (TextView) convertView.findViewById(R.id.txtProd);
        ImageView imagenIV= (ImageView) convertView.findViewById(R.id.imgProd);
        imagenIV.setImageBitmap(imagen);
        nombreTV.setText(nombreprod);



        return convertView;
    }
}
