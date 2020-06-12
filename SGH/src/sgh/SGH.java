package sgh;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class SGH {
    
    public static void main(String[] args) {
        Connection cn;
        Statement st;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sgh_bd", "root", "");
            st = cn.createStatement();
            LoginFrame frame1= new LoginFrame();
            frame1.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo realizar la conexión con la base de datos.","Error de conexión", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        
        //LoginSuccess frame1 = new LoginSuccess("Valentin","pepe@pepe.es",true);
        //frame1.setVisible(true);
    }
    
}
