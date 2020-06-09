/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgh;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

/**
 *
 * @author bogda
 */
public class BloquearDesbloquear extends SwingWorker<Void,Void>{
    private String nombre;
    private JPanel panel1,panel2;
    private JPanelBackground panel3;
    public BloquearDesbloquear(JPanel panel1, JPanel panel2,JPanelBackground panel3, String nombre){
        this.nombre=nombre;
        this.panel1=panel1;
        this.panel2=panel2;
        this.panel3=panel3;
    }
    
    
    
    @Override
    protected Void doInBackground() throws Exception {
        Connection cn;
        Statement st = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sgh_bd", "root", "");
            st = cn.createStatement();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo realizar la conexión con la base de datos.");
            e.printStackTrace();
        }
        while(true){
            String sql = "select nombre from bloqueada;";
            ResultSet rss = st.executeQuery(sql);
            if(rss.next()){
                if(rss.getString("nombre").equals("")){
                    panel1.setVisible(false);
                    panel2.setVisible(false);
                    panel3.setVisible(true);
                    nombre=rss.getString("nombre");
                }else{
                    panel1.setVisible(true);
                    panel2.setVisible(true);
                    panel3.setVisible(false);
                    nombre=rss.getString("nombre");
                }
            }
            
            
            Thread.sleep(1000);
        }
        
        
    }
}
