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
public class InsertarProductoFrame extends javax.swing.JFrame {

    private Connection cn;
    private Statement st;
    boolean fotoSubida=false;
    private String path="";
    int idgrupo;
    private String nombreGrupo;
    public InsertarProductoFrame(String nombreGrupo) {
        initComponents();
        conexionBD();
        this.nombreGrupo=nombreGrupo;
        idgrupo=buscarID(nombreGrupo);
        configurarVentana();
        
    }

    private void configurarVentana() {
        this.setResizable(false);
        setTitle("Insertar producto");
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
        
        labelGrupo.setText(nombreGrupo);
        

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
        btnSubirImg = new javax.swing.JButton();
        labelFoto = new javax.swing.JLabel();
        labelGrupo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tfNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tfNombreCorto = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tfPrecio = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        btnSubirImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/btn_subir_imagen.jpg"))); // NOI18N
        btnSubirImg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubirImgActionPerformed(evt);
            }
        });

        labelGrupo.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        labelGrupo.setForeground(new java.awt.Color(0, 0, 0));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Nombre del producto:");

        tfNombre.setBackground(new java.awt.Color(255, 255, 255));
        tfNombre.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        tfNombre.setForeground(new java.awt.Color(0, 0, 0));
        tfNombre.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        tfNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNombreActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Nombre corto:");

        tfNombreCorto.setBackground(new java.awt.Color(255, 255, 255));
        tfNombreCorto.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        tfNombreCorto.setForeground(new java.awt.Color(0, 0, 0));
        tfNombreCorto.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Precio:");

        tfPrecio.setBackground(new java.awt.Color(255, 255, 255));
        tfPrecio.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        tfPrecio.setForeground(new java.awt.Color(0, 0, 0));
        tfPrecio.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        tfPrecio.setText("1.10");
        tfPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfPrecioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnSubirImg, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnInsertar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tfPrecio, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tfNombreCorto))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(labelGrupo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tfNombre))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(labelFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfNombreCorto, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnInsertar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSubirImg, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(labelFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(88, Short.MAX_VALUE))
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
        if(!tfNombre.getText().equals("") &&  !tfNombreCorto.getText().equals("")  && fotoSubida){
            Boolean existe = verSiExiste(tfNombre.getText().toString());
            if(existe){
                JOptionPane.showMessageDialog(null, "El producto ya existe. Por favor, cambia el nombre.");
            }else if(tfNombreCorto.getText().length()>10){
                JOptionPane.showMessageDialog(null, "El nombre corto tiene un máximo de 10 caracteres.");
            }else if(tfNombre.getText().length()>30){
                JOptionPane.showMessageDialog(null, "El nombre tiene un máximo de 30 caracteres.");
            }
            else{
                try {
                    Double precio = Double.valueOf(tfPrecio.getText().toString());
                    InputStream inputstream = new FileInputStream(new File(path));
                    String sql = "INSERT into productos VALUES(null,?,?,?,?,?);";
                    PreparedStatement pst = cn.prepareStatement(sql);
                    pst.setString(1, tfNombre.getText().toString());
                    pst.setString(2,tfNombreCorto.getText().toString());
                    pst.setBlob(3,inputstream);
                    pst.setInt(4,idgrupo);
                    pst.setDouble(5, precio);
                    int updateData= pst.executeUpdate();
                    if(updateData>0){
                        JOptionPane.showMessageDialog(null, "El producto se ha ingresado correctamente.");
                        FrameInsertarProductos frame = new FrameInsertarProductos();
                        frame.setVisible(true);
                        this.dispose();
                    }else{
                        JOptionPane.showMessageDialog(null, "Error al introducir el producto en la base de datos.");
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(InsertarProductoFrame.class.getName()).log(Level.SEVERE, null, ex);
                }catch(SQLException sqle){
                    sqle.printStackTrace();
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, "El precio es con punto. Ejemplo: 3.50");
                }
            }
            
        }else{
            JOptionPane.showMessageDialog(null, "Por favor rellena los campos.");
        }
            
    }//GEN-LAST:event_btnInsertarActionPerformed

    private void tfNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNombreActionPerformed

    private void tfPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfPrecioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfPrecioActionPerformed

    private boolean verSiExiste(String nombreProd){
        String sql =" select nombreprod from productos where nombreprod=\'"+nombreProd+"\'";
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
    private javax.swing.JButton btnInsertar;
    private javax.swing.JButton btnSubirImg;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelFoto;
    private javax.swing.JLabel labelGrupo;
    private javax.swing.JTextField tfNombre;
    private javax.swing.JTextField tfNombreCorto;
    private javax.swing.JTextField tfPrecio;
    // End of variables declaration//GEN-END:variables
}
