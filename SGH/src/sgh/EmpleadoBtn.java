/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgh;

import java.awt.Color;
import java.awt.Font;
import java.sql.Date;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author bogda
 */
public class EmpleadoBtn extends JButton {
    private String nombre,apellidos,telefono,correo;
    private Date fecha;
    private Boolean admin;
    private ImageIcon icono;
    public EmpleadoBtn(){
        this.nombre=null;
        this.apellidos=null;
        this.telefono=null;
        this.correo=null;
        this.fecha=null;
        this.admin=null;
        this.icono=null;
        
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public ImageIcon getIcono() {
        return icono;
    }

    public void setIcono(ImageIcon icon) {
        this.icono = icon;
    }
    public EmpleadoBtn(String nombre, String apellidos, String telefono, String correo, Date fecha, Boolean admin, ImageIcon icono){
        this.nombre=nombre;
        this.apellidos=apellidos;
        this.telefono=telefono;
        this.correo=correo;
        this.fecha=fecha;
        this.admin=admin;
        this.icono=icono;
        this.setBounds(1, 1, 170, 50);
        this.setText(nombre);
        //this.setIcon(icono);
        this.setFont(new Font("Arial", Font.BOLD, 30));
        this.setForeground(Color.BLACK);
        this.setBorderPainted(false);
        this.setContentAreaFilled(false);
        //this.setFocusPainted(false);
        this.setOpaque(false);
    }
    
    
    
    
}
