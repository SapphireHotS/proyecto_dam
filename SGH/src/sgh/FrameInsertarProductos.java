package sgh;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author bogda
 */
public class FrameInsertarProductos extends javax.swing.JFrame {
    private Connection cn;
    private Statement st;
    private String primerGrupo="";
    private ArrayList<JButton> btnGrupos = new ArrayList<JButton>();
    private ArrayList<JButton> btnProductos = new ArrayList<JButton>();
    private String nombreGrupo;
    public FrameInsertarProductos() {
        initComponents();
        conexionBD();
        configurarVentana();
        cargarGrupos();
    }

    
    private void configurarVentana() {
        this.setResizable(false);
        setTitle("Editor de productos");
        ImageIcon icon = new ImageIcon(getClass().getResource("/resources/logo_sgh.png"));
        this.setIconImage(icon.getImage());
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                dispose();
            }
        });

    }
    
    
    private void conexionBD() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sgh_bd", "root", "");
            st = cn.createStatement();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo realizar la conexión con la base de datos.");
            e.printStackTrace();
        }
    }
    
    
    private void cargarGrupos() {
        try {
            String sql = "select * from grupos;";
            ResultSet rss = st.executeQuery(sql);
            boolean cargarPrimero=true;
            while (rss.next()) {
                String nombre = rss.getString("nombre");
                InputStream is = rss.getBinaryStream("foto");
                BufferedImage bufImg = null;
                bufImg = ImageIO.read(is);
                Image image = bufImg;
                ImageIcon icon = new ImageIcon(image);
                JButton botonprueba = new JButton();
                colocarBoton(botonprueba,1,1, icon, nombre, panelGrupos);
                btnGrupos.add(botonprueba);
                if(cargarPrimero){
                    primerGrupo=nombre;
                    cargarPrimero=false;
                }
            }
            ActionListener listenerBtnGrupos = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Object source = e.getSource();
                    if (source instanceof JButton) {
                        JButton btn = (JButton) source;
                        String textoBtn = btn.getText();
                        cargarProductos(textoBtn);
                        nombreGrupo=textoBtn;
                    }
                }
            };
            for (JButton btn : btnGrupos) {
                btn.addActionListener(listenerBtnGrupos);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        cargarProductos(primerGrupo);
        nombreGrupo=primerGrupo;

    }
    
    
    private void cargarProductos(String categoria) {
        
        
        for(JButton btn: btnProductos){
            btn.setVisible(false);
            btn=null;
        }
        btnProductos.clear();
        panelProductos.removeAll();
        
        try {//select en la base de datos
            String sql = "select * from productos p join grupos g on p.id_grupo=g.id where g.nombre='" + categoria + "';";
            ResultSet rss = st.executeQuery(sql);
            int x=5;
            int y=5;
            while (rss.next()) {
                String nombre = rss.getString("nombrecorto");
                InputStream is = rss.getBinaryStream("fotoprod");
                BufferedImage bufImg = null;
                bufImg = ImageIO.read(is);
                Image image = bufImg;
                ImageIcon icon = new ImageIcon(image);
                JButton botonprueba = new JButton();
                colocarBoton(botonprueba,x,y, icon, nombre, panelProductos);
                x+=80;
                if(x==565){
                    x=5;
                    y+=100;
                }
                btnProductos.add(botonprueba);
                nombre = null;//Limpio recursos para liberar RAM. Sin esto, cada vez que recargaba me aumentaba 100MB de uso de RAM.
                is = null;
                icon = null;
                image = null;
                bufImg = null;
                System.gc();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    
    
    
    
    
    
    
    
    private void colocarBoton(JButton botonprueba,int xx, int yy, ImageIcon icono, String nombre, JPanel panel) {
        botonprueba.setBounds(xx, yy, 80, 80);
        botonprueba.setOpaque(false);
        botonprueba.setContentAreaFilled(false);
        botonprueba.setBorderPainted(false);
        botonprueba.setHorizontalTextPosition(JButton.CENTER);
        botonprueba.setVerticalTextPosition(JButton.BOTTOM);
        botonprueba.setText(nombre);
        botonprueba.setFont(new Font("Arial", Font.BOLD, 9));
        int x = icono.getImage().getWidth(this);
        int y = icono.getImage().getHeight(this);
        Image nuevoicono = null;
        if (x > y) {
            nuevoicono = icono.getImage().getScaledInstance(70, 70 * y / x, Image.SCALE_DEFAULT);
        } else {
            nuevoicono = icono.getImage().getScaledInstance(70 * x / y, 70, Image.SCALE_DEFAULT);
        }
        botonprueba.setIcon(new ImageIcon(nuevoicono));

        panel.add(botonprueba);
    }
    
    
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelGrupos = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        panelProductos = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelGrupos.setBackground(new java.awt.Color(51, 153, 0));
        panelGrupos.setLayout(new java.awt.GridLayout(0, 2));
        jScrollPane1.setViewportView(panelGrupos);

        jPanel2.setBackground(new java.awt.Color(255, 153, 0));

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/btn_borrar_grp.jpg"))); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/btn_insertar_prod.jpg"))); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/btn_borrar_prod.jpg"))); // NOI18N
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/btn_insertar_grp.jpg"))); // NOI18N
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        panelProductos.setBackground(new java.awt.Color(204, 204, 0));
        panelProductos.setLayout(new java.awt.GridLayout(5, 5));
        jScrollPane2.setViewportView(panelProductos);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        InsertarGrupoFrame frame = new InsertarGrupoFrame();
        frame.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        InsertarProductoFrame frame = new InsertarProductoFrame(nombreGrupo);
        frame.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog (null, "Estás seguro de que quieres borrar el grupo "+ nombreGrupo+" y todos sus productos?","Warning",dialogButton);
        if(dialogResult == JOptionPane.YES_OPTION){
            String sql1="DELETE from productos WHERE id_grupo="+buscarID(nombreGrupo);
            String sql2 = "DELETE from grupos WHERE nombre=\'"+nombreGrupo+"\';";
            try {
                st.executeUpdate(sql1);
                st.executeUpdate(sql2);
                FrameInsertarProductos frame = new FrameInsertarProductos();
                frame.setVisible(true);
                this.dispose();
            } catch (SQLException ex) {
                Logger.getLogger(FrameInsertarProductos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        BorrarProductoFrame frame = new BorrarProductoFrame();
        frame.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton10ActionPerformed
    private int buscarID(String nombreGrupo){
        String sql =" select id from grupos where nombre=\'"+nombreGrupo+"\'";
        try{
            ResultSet rss = st.executeQuery(sql);
            if(rss.next()){
                return rss.getInt("id");
            }
            
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return -1;
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panelGrupos;
    private javax.swing.JPanel panelProductos;
    // End of variables declaration//GEN-END:variables
}
