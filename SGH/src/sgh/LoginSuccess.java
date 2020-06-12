package sgh;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author bogda
 */
public class LoginSuccess extends JFrame {
    String nombre;
    Boolean esAdmin;
    public LoginSuccess(String nombre, String correo, Boolean esAdmin){
        this.nombre=nombre;
        this.esAdmin=esAdmin;
        initComponents();
    }
    
    
    
    public void initComponents(){
        setSize(960,540);
        setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");
        JPanelBackground panel1 = new JPanelBackground();//panel principal, uso una clase heredada
        panel1.setBackground("/resources/fondoDegradado1.png");//para poder poner un background con una imagen
        panel1.setLayout(null);
        this.getContentPane().add(panel1);
        ImageIcon icon = new ImageIcon(getClass().getResource("/resources/logo_sgh.png"));
        this.setIconImage(icon.getImage());
        
        
        JLabel imagenSGH = new JLabel();
        ImageIcon iconoPrueba = new javax.swing.ImageIcon(getClass().getResource("/resources/logoSGH.png"));
        Image imagenPrueba = iconoPrueba.getImage();
        Image imagenModificada = imagenPrueba.getScaledInstance(240,240,java.awt.Image.SCALE_SMOOTH);
        Icon iconoFinal1= new ImageIcon(imagenModificada);
        imagenSGH.setIcon(iconoFinal1);
        imagenSGH.setBounds(365, -10, 300, 320);
        panel1.add(imagenSGH);
        
        JLabel labelBienvenido = new JLabel("Bienvenido ");
        labelBienvenido.setFont(new Font("Calibri", Font.BOLD, 25));
        labelBienvenido.setForeground(Color.cyan);
        labelBienvenido.setBounds(380,275,150,50);
        panel1.add(labelBienvenido);
        
        JLabel labelCorreo = new JLabel(nombre+"!");
        labelCorreo.setFont(new Font("Calibri", Font.BOLD, 25));
        labelCorreo.setForeground(Color.cyan);
        labelCorreo.setBounds(510,275,150,50);
        panel1.add(labelCorreo);
        
        
        JButton botonLanzarApp = new JButton();
        crearBoton(botonLanzarApp,200,350,"/resources/lanzarAplicacion.jpg");
        botonLanzarApp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FramePrincipal fp = new FramePrincipal();
                fp.setVisible(true);
                dispose();
            }
        });
        
        panel1.add(botonLanzarApp);
        
        
        
        JButton botonEditMesas = new JButton();
        crearBoton(botonEditMesas,350,350,"/resources/editarMesas.jpg");
        panel1.add(botonEditMesas);
        botonEditMesas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(esAdmin){
                    FrameEditMesas fp = new FrameEditMesas();
                    fp.setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(null, "Necesitas ser administrador para acceder aquí.");
                }
                
                
            }
        });
        
        
        JButton botonEditProd = new JButton();
        crearBoton(botonEditProd,500,350,"/resources/editarProductos.jpg");
        panel1.add(botonEditProd);
        
        botonEditProd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(esAdmin){
                    FrameInsertarProductos fp = new FrameInsertarProductos();
                fp.setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(null, "Necesitas ser administrador para acceder aquí.");
                }
                
                
            }
        });
        
        
        
        
        JButton botonGestEmp = new JButton();
        crearBoton(botonGestEmp,650,350,"/resources/gestionarEmpleados.jpg");
        panel1.add(botonGestEmp);
        botonGestEmp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(esAdmin){
                    EmpleadosFrame empFrame = new EmpleadosFrame();
                    empFrame.setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(null, "Necesitas ser administrador para acceder aquí.");
                }
                
                
                
            }
        });
        
    }
    
    
    
    private void crearBoton(JButton jbutton, int x, int y, String resource){//botón, X inicial, Y inicial, ruta de la imagen
        JButton botonEditProd = new JButton();
        jbutton.setBounds(x,y,100,100);
        ImageIcon iconoPrueba = new javax.swing.ImageIcon(getClass().getResource(resource));
        Image imagenPrueba = iconoPrueba.getImage();
        Image imagenModificada = imagenPrueba.getScaledInstance(100,100,java.awt.Image.SCALE_SMOOTH);
        ImageIcon iconoFinal2= new ImageIcon(imagenModificada);
        jbutton.setIcon(iconoFinal2);
        jbutton.setBackground(null);
        jbutton.setContentAreaFilled(false);
    }
    
    
    
    
    
    
    
    
    
    
    
}
