package com.example.sgh;

public class Pedido {
    private int idmesa;
    private int idproducto;
    private String nombreprod;
    private int cantidad;
    private double preciou;
    private double preciot;


    public Pedido(){
        this.idmesa=0;
        this.idproducto=0;
        this.nombreprod="";
        this.cantidad=0;
        this.preciou=0;
        this.preciot=0;
    }


    public Pedido(int idmesa, int idproducto, String nombreprod, int cantidad, double preciou, double preciot ){
        this.idmesa=idmesa;
        this.idproducto=idproducto;
        this.nombreprod=nombreprod;
        this.cantidad=cantidad;
        this.preciou=preciou;
        this.preciot=preciot;
    }

    public int getIdmesa() {
        return idmesa;
    }

    public void setIdmesa(int idmesa) {
        this.idmesa = idmesa;
    }

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public String getNombreprod() {
        return nombreprod;
    }

    public void setNombreprod(String nombreprod) {
        this.nombreprod = nombreprod;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPreciou() {
        return preciou;
    }

    public void setPreciou(double preciou) {
        this.preciou = preciou;
    }

    public double getPreciot() {
        return preciot;
    }

    public void setPreciot(double preciot) {
        this.preciot = preciot;
    }
}
