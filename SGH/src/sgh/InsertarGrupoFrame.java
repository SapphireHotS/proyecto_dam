/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgh;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author bogda
 */
public class InsertarGrupoFrame extends javax.swing.JFrame {

    private Connection cn;
    private Statement st;
    boolean fotoSubida=false;
    private String path="";
    public InsertarGrupoFrame() {
        initComponents();
        conexionBD();
        configurarVentana();

    }

    private void configurarVentana() {
        this.setResizable(false);
        setTitle("Insertar grupo");
        ImageIcon icon = new ImageIcon(getClass().getResource("/resources/logo_sgh.png"));
        this.setIconImage(icon.getImage());
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                FrameInsertarProductos frame = new FrameInsertarProductos();
                frame.setVisible(true);
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnInsertar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        btnSubirImg = new javax.swing.JButton();
        labelFoto = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(580, 310));

        jPanel1.setBackground(new java.awt.Color(0, 153, 51));

        btnInsertar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/btn_insertar_mesa.jpg"))); // NOI18N
        btnInsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Nombre del grupo:");

        jTextField1.setBackground(new java.awt.Color(255, 255, 255));
        jTextField1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(0, 0, 0));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        btnSubirImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/btn_subir_imagen.jpg"))); // NOI18N
        btnSubirImg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubirImgActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(btnSubirImg, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInsertar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSubirImg, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addComponent(btnInsertar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(labelFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void btnSubirImgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubirImgActionPerformed
        JFileChooser filechooser = new JFileChooser();
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("Imágenes", "png", "jpg", "jpeg");
        filechooser.addChoosableFileFilter(fnef);
        int openDialog = filechooser.showOpenDialog(null);
        if (openDialog == filechooser.APPROVE_OPTION) {
            File selectedFile = filechooser.getSelectedFile();
            path = selectedFile.getAbsolutePath();
            if (path.endsWith("jpg") || path.endsWith("jpeg") || path.endsWith("png")) {
                if ((selectedFile.length() / 1024) > 64) {
                    JOptionPane.showMessageDialog(null, "El tamaño máximo son 64KB."); 
                    fotoSubida=false;
                } else {
                    ImageIcon ii = new ImageIcon(selectedFile.getAbsolutePath());
                    int x = ii.getImage().getWidth(this);
                    int y = ii.getImage().getHeight(this);
                    Image nuevoicono = null;
                    if (x > y) {
                        nuevoicono = ii.getImage().getScaledInstance(170, 170 * y / x, Image.SCALE_DEFAULT);
                    } else {
                        nuevoicono = ii.getImage().getScaledInstance(170 * x / y, 170, Image.SCALE_DEFAULT);
                    }
                    labelFoto.setIcon(new ImageIcon(nuevoicono));
                    fotoSubida=true;
                }

            } else {
                JOptionPane.showMessageDialog(null, "Por favor, selecciona una imagen");
                fotoSubida=false;
            }

        }
    }//GEN-LAST:event_btnSubirImgActionPerformed

    private void btnInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarActionPerformed
        if(!jTextField1.getText().equals("") && fotoSubida){
            Boolean existe = verSiExiste(jTextField1.getText().toString());
            if(existe){
                JOptionPane.showMessageDialog(null, "El grupo ya existe. Por favor, cambia el nombre.");
            }else{
                try {
                    InputStream inputstream = new FileInputStream(new File(path));
                    String sql = "INSERT into grupos VALUES(null,?,?);";
                    PreparedStatement pst = cn.prepareStatement(sql);
                    pst.setString(1, jTextField1.getText().toString());
                    pst.setBlob(2,inputstream);
                    int updateData= pst.executeUpdate();
                    if(updateData>0){
                        JOptionPane.showMessageDialog(null, "El grupo se ha creado correctamente.");
                        FrameInsertarProductos frame = new FrameInsertarProductos();
                        frame.setVisible(true);
                        this.dispose();
                    }else{
                        JOptionPane.showMessageDialog(null, "Error al introducir el grupo en la base de datos.");
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(InsertarGrupoFrame.class.getName()).log(Level.SEVERE, null, ex);
                }catch(SQLException sqle){
                    sqle.printStackTrace();
                }
            }
            
            
            
            
            
            
            
        }else{
            JOptionPane.showMessageDialog(null, "Por favor, introduce un nombre y una foto.");
        }
            
    }//GEN-LAST:event_btnInsertarActionPerformed

    private boolean verSiExiste(String nombreGrupo){
        String sql =" select nombre from grupos where nombre=\'"+nombreGrupo+"\'";
        try{
            ResultSet rss = st.executeQuery(sql);
            if(rss.next()){
                return true;
            }else{
                return false;
            }
            
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return true;
    }
    
    
    
    
    
    
    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInsertar;
    private javax.swing.JButton btnSubirImg;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel labelFoto;
    // End of variables declaration//GEN-END:variables
}
