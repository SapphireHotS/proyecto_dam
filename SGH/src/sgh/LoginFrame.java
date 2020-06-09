package sgh;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import java.security.*;

/**
 *
 * @author bogda
 */
public class LoginFrame extends JFrame {
    public Connection cn;
    public Statement st;
    

    public LoginFrame() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sgh_bd", "root", "");
            st = cn.createStatement();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo realizar la conexión con la base de datos.");
            e.printStackTrace();
        }
        initComponents();
        
    }

    private void initComponents() {
        setSize(960, 540);
        setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");
        JPanelBackground panel1 = new JPanelBackground();//panel principal, uso una clase heredada
        panel1.setBackground("/resources/fondoDegradado1.png");//para poder poner un background con una imagen
        panel1.setLayout(null);
        this.getContentPane().add(panel1);

        JLabel imagenSGH = new JLabel();
        ImageIcon iconoPrueba = new javax.swing.ImageIcon(getClass().getResource("/resources/logoSGH.png"));
        Image imagenPrueba = iconoPrueba.getImage();
        Image imagenModificada = imagenPrueba.getScaledInstance(240, 240, java.awt.Image.SCALE_SMOOTH);
        Icon iconoFinal1 = new ImageIcon(imagenModificada);
        imagenSGH.setIcon(iconoFinal1);
        imagenSGH.setBounds(365, -10, 300, 300);
        panel1.add(imagenSGH);

        JLabel labelCorreo = new JLabel("Correo electrónico: ");
        labelCorreo.setFont(new Font("Calibri", Font.BOLD, 18));
        labelCorreo.setForeground(Color.cyan);
        labelCorreo.setBounds(335, 275, 150, 50);
        panel1.add(labelCorreo);

        TextField tfCorreo = new TextField();
        tfCorreo.setBounds(485, 290, 150, 20);
        panel1.add(tfCorreo);

        JLabel labelPassword = new JLabel("Contraseña:  ");
        labelPassword.setFont(new Font("Calibri", Font.BOLD, 18));
        labelPassword.setBounds(335, 310, 150, 40);
        labelPassword.setForeground(Color.cyan);
        panel1.add(labelPassword);

        JPasswordField tfPassword = new JPasswordField();
        tfPassword.setBounds(485, 320, 150, 20);
        panel1.add(tfPassword);

        JButton botonAceptar = new JButton();
        botonAceptar.setBounds(425, 390, 129, 44);
        iconoPrueba = new javax.swing.ImageIcon(getClass().getResource("/resources/botonLogin.png"));
        imagenPrueba = iconoPrueba.getImage();
        imagenModificada = imagenPrueba.getScaledInstance(150, 60, java.awt.Image.SCALE_SMOOTH);
        Icon iconoFinal2 = new ImageIcon(imagenModificada);
        botonAceptar.setIcon(iconoFinal2);
        botonAceptar.setBackground(null);
        botonAceptar.setContentAreaFilled(false);
        panel1.add(botonAceptar);
        botonAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login(tfCorreo.getText(),tfPassword.getText());
            }
        });

    }

    public void login(String correo, String password) {//El login se realiza con el hash MD5 de la contraseña
        try{
            String hashPassword;
            byte[] bytesPassword = password.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytesHash=md.digest(bytesPassword);
            StringBuilder sb = new StringBuilder(2*bytesHash.length);
            for(byte b : bytesHash){
                sb.append(String.format("%02x", b&0xff));
            }
            hashPassword=sb.toString();
            String sql="select * from usuarios where correo= '"+correo+"' and password='"+hashPassword+"';";
            ResultSet rss= st.executeQuery(sql);
            if(rss.next()){
                
                
                JOptionPane.showMessageDialog(null, "Conexión realizada con éxito.");
                String nombre= rss.getString("nombre");
                Boolean esAdmin=rss.getBoolean("administrador");
                String correoE = rss.getString("correo");
                System.out.println(nombre);
                LoginSuccess loginsuccess = new LoginSuccess(nombre,correoE,esAdmin);
                loginsuccess.setVisible(true);
                this.dispose();
                
            }else{
                JOptionPane.showMessageDialog(null, "Nombre y contraseña incorrectos.");

            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
