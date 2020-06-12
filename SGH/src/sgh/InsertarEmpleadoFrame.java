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
import java.security.MessageDigest;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
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
public class InsertarEmpleadoFrame extends javax.swing.JFrame {

    private Connection cn;
    private Statement st;
    private String path = "";
    boolean fotoSubida = false;

    public InsertarEmpleadoFrame() {
        initComponents();
        conexionBD();
        configurarVentana();

    }

    private void configurarVentana() {
        this.setResizable(false);
        setTitle("Insertar empleado");
        ImageIcon icon = new ImageIcon(getClass().getResource("/resources/logo_sgh.png"));
        this.setIconImage(icon.getImage());
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                EmpleadosFrame frame = new EmpleadosFrame();
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
        btnSubirImg = new javax.swing.JButton();
        labelFoto = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        labelApellidos = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        labelTelefono = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        labelFecha = new javax.swing.JTextField();
        labelCorreo = new javax.swing.JTextField();
        labelNombre = new javax.swing.JTextField();
        jCheckBox = new javax.swing.JCheckBox();
        labelPassword = new javax.swing.JPasswordField();

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
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Nombre:");

        btnSubirImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/btn_subir_imagen.jpg"))); // NOI18N
        btnSubirImg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubirImgActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Apellidos:");

        labelApellidos.setBackground(new java.awt.Color(255, 255, 255));
        labelApellidos.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        labelApellidos.setForeground(new java.awt.Color(0, 0, 0));
        labelApellidos.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        labelApellidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                labelApellidosActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Teléfono:");

        labelTelefono.setBackground(new java.awt.Color(255, 255, 255));
        labelTelefono.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        labelTelefono.setForeground(new java.awt.Color(0, 0, 0));
        labelTelefono.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Trabaja desde:");

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Correo electrónico:");

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Administrador:");

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Contraseña:");

        labelFecha.setBackground(new java.awt.Color(255, 255, 255));
        labelFecha.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        labelFecha.setForeground(new java.awt.Color(0, 0, 0));
        labelFecha.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        labelFecha.setText("21/05/2020");

        labelCorreo.setBackground(new java.awt.Color(255, 255, 255));
        labelCorreo.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        labelCorreo.setForeground(new java.awt.Color(0, 0, 0));
        labelCorreo.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        labelCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                labelCorreoActionPerformed(evt);
            }
        });

        labelNombre.setBackground(new java.awt.Color(255, 255, 255));
        labelNombre.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        labelNombre.setForeground(new java.awt.Color(0, 0, 0));
        labelNombre.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        jCheckBox.setBackground(new java.awt.Color(0, 153, 51));

        labelPassword.setBackground(new java.awt.Color(255, 255, 255));
        labelPassword.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        labelPassword.setText("jPasswordField1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnSubirImg, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(80, 80, 80)
                        .addComponent(btnInsertar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelPassword))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(labelFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(labelCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                            .addGap(6, 6, 6)
                                            .addComponent(labelTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(labelNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(labelApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                    .addComponent(labelPassword))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnInsertar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSubirImg, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
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
                if ((selectedFile.length() / 1024 / 1024) > 40) {
                    JOptionPane.showMessageDialog(null, "El tamaño máximo son 40MB.");
                    fotoSubida = false;
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
                    fotoSubida = true;
                }

            } else {
                JOptionPane.showMessageDialog(null, "Por favor, selecciona una imagen");
                fotoSubida = false;
            }

        }
    }//GEN-LAST:event_btnSubirImgActionPerformed

    private void btnInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarActionPerformed
        Boolean sePuede = true;

        if (labelNombre.getText().equals("") || labelNombre.getText().length() > 20) {
            sePuede = false;
        }
        if (labelApellidos.getText().equals("") || labelApellidos.getText().length() > 25) {
            sePuede = false;
        }
        if (labelTelefono.getText().equals("") || labelNombre.getText().length() > 10) {
            sePuede = false;
        }
        if (labelCorreo.getText().equals("") || labelNombre.getText().length() > 35) {
            sePuede = false;
        }
        if (labelFecha.getText().equals("")) {
            sePuede = false;
        }

        //FALTA FECHA
        if (labelPassword.getText().equals("")) {
            sePuede = false;
        }
        if(!fotoSubida){
            sePuede=false;
        }
        if (sePuede) {
            try {
                InputStream inputstream = new FileInputStream(new File(path));
                String sql = "INSERT INTO usuarios VALUES(?,?,?,?,?,?,?,?);";
                PreparedStatement pst = cn.prepareStatement(sql);
                Date fecha = new SimpleDateFormat("dd/MM/yyyy").parse(labelFecha.getText().toString());
                pst.setString(1, labelNombre.getText().toString());
                pst.setString(2, labelApellidos.getText().toString());
                pst.setString(3, labelTelefono.getText().toString());
                pst.setDate(4, new java.sql.Date(fecha.getTime()));
                pst.setBlob(5, inputstream);
                pst.setString(6, labelCorreo.getText().toString());
                pst.setString(7,md5(labelPassword.getText().toString()));
                pst.setBoolean(8,jCheckBox.isSelected());
                int updateData = pst.executeUpdate();
                if (updateData > 0) {
                    JOptionPane.showMessageDialog(null, "El empleado se ha ingresado correctamente.");
                    EmpleadosFrame frame = new EmpleadosFrame();
                    frame.setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al introducir el empleado en la base de datos.");
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(InsertarEmpleadoFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor rellena los campos. Si están completos, el nombre tiene un tamaño máximo de 20 caracteres,"
                    + "apellidos 25, teléfono 10 y correo 35. Por favor, verifica esos campos.");
        }


    }//GEN-LAST:event_btnInsertarActionPerformed

    private void labelApellidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_labelApellidosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_labelApellidosActionPerformed

    private void labelCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_labelCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_labelCorreoActionPerformed


    private String md5(String password) {
        try {
            String hashPassword;
            byte[] bytesPassword = password.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytesHash = md.digest(bytesPassword);
            StringBuilder sb = new StringBuilder(2 * bytesHash.length);
            for (byte b : bytesHash) {
                sb.append(String.format("%02x", b & 0xff));
            }
            hashPassword = sb.toString();
            return hashPassword;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInsertar;
    private javax.swing.JButton btnSubirImg;
    private javax.swing.JCheckBox jCheckBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField labelApellidos;
    private javax.swing.JTextField labelCorreo;
    private javax.swing.JTextField labelFecha;
    private javax.swing.JLabel labelFoto;
    private javax.swing.JTextField labelNombre;
    private javax.swing.JPasswordField labelPassword;
    private javax.swing.JTextField labelTelefono;
    // End of variables declaration//GEN-END:variables
}
