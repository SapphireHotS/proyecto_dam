package com.example.sgh;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PedidosListAdapter extends ArrayAdapter<Pedido> {

    private static final String TAG = "PedidosListAdapter";
    private Context mContext;
    int mResource;

    public PedidosListAdapter(Context context, int resource, ArrayList<Pedido> pedidos) {
        super(context, resource, pedidos);
        this.mContext = context;
        mResource= resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int idmesa= getItem(position).getIdmesa();
        int idprod= getItem(position).getIdproducto();
        String nombreprod= getItem(position).getNombreprod();
        int cantidad= getItem(position).getCantidad();
        double preciou= getItem(position).getPreciou();
        double preciot = getItem(position).getPreciot();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView= inflater.inflate(mResource, parent, false);

        TextView cantTV= (TextView) convertView.findViewById(R.id.itemCant);
        TextView nombreTV= (TextView) convertView.findViewById(R.id.itemDescrip);
        TextView preciouTV= (TextView) convertView.findViewById(R.id.itemPU);
        TextView preciotTV= (TextView) convertView.findViewById(R.id.itemTotal);

        cantTV.setText(Integer.toString(cantidad));
        nombreTV.setText(nombreprod);
        preciouTV.setText(Double.toString(preciou)+"€");
        preciotTV.setText(Double.toString(preciot)+"€");



        return convertView;
    }
}
