package sgh;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author bogda
 */
public class FramePrincipal extends javax.swing.JFrame {
    private JPanelBackground panelBloqueado;
    private int mesaElegida = 1;
    private float valorCalc1 = 0, valorCalc2 = 0;
    private String operador = "";
    private boolean operacionFinalizada = false;//Sirve para saber si se ha pulsado un operador en la calculadora
    private Connection cn;
    private Statement st;
    private int x, y, w, h;
    private ArrayList<JButton> btnGrupos = new ArrayList<JButton>();
    private ArrayList<JButton> btnProductos = new ArrayList<JButton>();
    private ArrayList<JButton> btnProductosExtras = new ArrayList<JButton>();
    private DefaultTableModel modeloTabla;
    private String nombre;

    public FramePrincipal() {
        conexionBD();
        initComponents();
        arreglarImagenes();
        cargarGrupos();
        configurarTabla();
        //cargarProductos("Refrescos")
        cargarLista(labelMesa2.getText().toString());
        BloquearDesbloquear bloqdesbloq = new BloquearDesbloquear(jPanel2,panelBotones,panelBloqueado,nombre);
        bloqdesbloq.execute();
        
    }
    
    
    
    
    //Inicio de métodos lógicos
    private void cargarGrupos() {
        try {
            String sql = "select * from grupos;";
            ResultSet rss = st.executeQuery(sql);
            int valorW = 15;
            int valorH = 5;
            while (rss.next()) {
                String nombre = rss.getString("nombre");
                InputStream is = rss.getBinaryStream("foto");
                BufferedImage bufImg = null;
                bufImg = ImageIO.read(is);
                Image image = bufImg;
                ImageIcon icon = new ImageIcon(image);
                JButton botonprueba = new JButton();
                if (btnGrupos.size() == 6) {
                    btnGrupos.get(5).setBounds(130, 5, 80, 80);
                    btnGrupos.get(5).setVisible(false);
                    valorW = 15;
                    valorH = 95;
                    ImageIcon iconoPrueba2 = new ImageIcon(getClass().getResource("/resources/flecha_derecha.png"));
                    JButton btnSiguiente2 = new JButton();
                    colocarBoton(btnSiguiente2, 130, 185, iconoPrueba2, "Siguiente", panelGrupos);
                    JButton btnVolver2 = new JButton();
                    iconoPrueba2 = new ImageIcon(getClass().getResource("/resources/flecha_izquierda.png"));
                    colocarBoton(btnVolver2, 15, 5, iconoPrueba2, "Volver", panelGrupos);
                    btnVolver2.setVisible(false);
                    btnSiguiente2.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            for (int ff = 0; ff < 5; ff++) {
                                btnGrupos.get(ff).setVisible(false);
                            }
                            for (int ii = 5; ii < btnGrupos.size(); ii++) {
                                btnGrupos.get(ii).setVisible(true);
                            }
                            btnVolver2.setVisible(true);
                            btnSiguiente2.setVisible(false);
                        }
                    });

                    btnVolver2.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            for (int ff = 0; ff < 5; ff++) {
                                btnGrupos.get(ff).setVisible(true);
                            }
                            for (int ii = 5; ii < btnGrupos.size(); ii++) {
                                btnGrupos.get(ii).setVisible(false);
                            }
                            btnVolver2.setVisible(false);
                            btnSiguiente2.setVisible(true);
                        }
                    });

                }

                if (btnGrupos.size() >= 6) {
                    botonprueba.setVisible(false);
                }

                colocarBoton(botonprueba, valorW, valorH, icon, nombre, panelGrupos);
                btnGrupos.add(botonprueba);
                if (valorW == 15) {
                    valorW = 130;
                } else {
                    if (valorH == 185) {
                        valorW = 15;
                        valorH = 5;
                    } else {
                        valorW = 15;
                        valorH += 90;
                    }

                }
            }
            ActionListener listenerBtnGrupos = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Object source = e.getSource();

                    if (source instanceof JButton) {

                        JButton btn = (JButton) source;
                        String textoBtn = btn.getText();
                        cargarProductos(textoBtn);
                    }
                }
            };
            for (JButton btn : btnGrupos) {
                btn.addActionListener(listenerBtnGrupos);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void colocarBoton(JButton botonprueba, int valorX, int valorY, ImageIcon icono, String nombre, JPanel panel) {
        botonprueba.setBounds(valorX, valorY, 80, 80);
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

    private void cargarProductos(String categoria) {
        if (btnProductos.size() >= 1) {//Con este if compruebo si hay ya botones. Si los hay, que los borre
            for (JButton btn : btnProductos) {
                btn.setVisible(false);
            }
            for (JButton btn : btnProductosExtras) {
                btn.setVisible(false);
            }
            btnProductos.clear();
            btnProductosExtras.clear();
            panelProductos.removeAll();
            panelProductos.revalidate();
            panelProductos.repaint();
            System.gc();
        }
        try {//select en la base de datos
            String sql = "select * from productos p join grupos g on p.id_grupo=g.id where g.nombre='" + categoria + "';";
            ResultSet rss = st.executeQuery(sql);
            int valorW = 15;
            int valorH = 5;
            int numBtnX = 0;

            while (rss.next()) {
                String nombre = rss.getString("nombrecorto");
                InputStream is = rss.getBinaryStream("fotoprod");
                BufferedImage bufImg = null;
                bufImg = ImageIO.read(is);
                Image image = bufImg;
                ImageIcon icon = new ImageIcon(image);
                JButton botonprueba = new JButton();
                botonprueba.setVisible(false);
                if (btnProductos.size() == 12) {
                    valorW = 255;
                    valorH = 5;
                    numBtnX = 2;
                    btnProductos.get(11).setBounds(135, 5, 80, 80);
                }
                colocarBoton(botonprueba, valorW, valorH, icon, nombre, panelProductos);
                btnProductos.add(botonprueba);
                numBtnX++;

                if (numBtnX < 4) {
                    valorW += 120;
                } else {
                    numBtnX = 0;
                    valorW = 15;
                    valorH += 90;
                }
                nombre = null;//Limpio recursos para liberar RAM. Sin esto, cada vez que recargaba me aumentaba 100MB de uso de RAM.
                is = null;
                icon = null;
                image = null;
                bufImg = null;
                System.gc();
            }
            if (btnProductos.size() <= 12) {
                for (JButton btn : btnProductos) {
                    btn.setVisible(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (btnProductos.size() > 12) {//Miro si hay más de 12: si los hay, dibujo el botón siguiente y el de volver.
            int i = 0;
            for (JButton btn : btnProductos) {
                i++;
                if (i < 12) {
                    btn.setVisible(true);
                } else if (i == 12) {
                    ImageIcon iconoPrueba = new ImageIcon(getClass().getResource("/resources/flecha_derecha.png"));
                    JButton btnSiguiente = new JButton();
                    colocarBoton(btnSiguiente, btnProductos.get(10).getX() + 120, btnProductos.get(10).getY(), iconoPrueba, "Siguiente", panelProductos);
                    JButton btnVolver = new JButton();
                    iconoPrueba = new ImageIcon(getClass().getResource("/resources/flecha_izquierda.png"));
                    colocarBoton(btnVolver, 15, 5, iconoPrueba, "Volver", panelProductos);
                    btnVolver.setVisible(false);
                    btnSiguiente.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            for (int ff = 0; ff <= 10; ff++) {
                                btnProductos.get(ff).setVisible(false);
                            }
                            for (int ii = 11; ii < btnProductos.size(); ii++) {
                                btnProductos.get(ii).setVisible(true);
                            }
                            btnVolver.setVisible(true);
                            btnSiguiente.setVisible(false);
                        }
                    });

                    btnVolver.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            for (int ff = 0; ff <= 10; ff++) {
                                btnProductos.get(ff).setVisible(true);
                            }
                            for (int ii = 11; ii < btnProductos.size(); ii++) {
                                btnProductos.get(ii).setVisible(false);
                            }
                            btnVolver.setVisible(false);
                            btnSiguiente.setVisible(true);
                        }
                    });
                    btnProductosExtras.add(btnVolver);
                    btnProductosExtras.add(btnSiguiente);
                    iconoPrueba = null;

                }
            }
        }
        ActionListener listenerBtnProd = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();

                if (source instanceof JButton) {
                    int cantidad = 1;
                    JButton btn = (JButton) source;
                    String textoBtn = btn.getText();
                    if (labelCalculadora.getText().charAt(labelCalculadora.getText().length() - 1) == '*') {
                        String str5 = labelCalculadora.getText().substring(0, labelCalculadora.getText().length() - 1);//quito el *
                        cantidad = Integer.parseInt(str5);//paso de string a int
                        labelCalculadora.setText("0");
                    }

                    int idProd = 0;
                    String sqlBusqueda = "select * from productos where nombrecorto='" + textoBtn + "';";
                    try {
                        ResultSet rss = st.executeQuery(sqlBusqueda);
                        if (rss.next()) {
                            idProd = rss.getInt("id_prod");
                            String nombreprod = rss.getString("nombreprod");
                            Double preciou = rss.getDouble("precio");
                            Double preciot = preciou * cantidad;
                            NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                            DecimalFormat df =(DecimalFormat) nf;
                            df.applyPattern("0.##");
                            String sqlInsert = "INSERT INTO `pedidos` (`idpedido`, `idmesa`, `idproducto`, `nombreprod`, `cantidad`,"
                                    + " `preciou`, `preciot`) VALUES (NULL, '" + labelMesa2.getText() + "', '" + idProd + "', '" + nombreprod + "', '" + cantidad + "', "
                                    + "'" + preciou + "', '" + df.format(preciot) + "');";
                            PreparedStatement preparedStmt = cn.prepareStatement(sqlInsert);
                            preparedStmt.execute();
                            String sqlUpdate = "update mesas set ocupada=true where nummesa="+labelMesa2.getText().toString();
                            st.executeUpdate(sqlUpdate);
                        }
                        rss.close();
                    } catch (Exception ee) {
                        ee.printStackTrace();
                    }
                    cargarLista(labelMesa2.getText());
                }
            }
        };
        for (JButton btn : btnProductos) {
            btn.addActionListener(listenerBtnProd);
        }

    }

    private void dibujarNumCalc(String valor) {
        if (labelCalculadora.getText().toString().equals("0") || (labelCalculadora.getText().charAt(labelCalculadora.getText().length() - 1) == '*'
                || labelCalculadora.getText().charAt(labelCalculadora.getText().length() - 1) == '/'
                || labelCalculadora.getText().charAt(labelCalculadora.getText().length() - 1) == '+'
                || labelCalculadora.getText().charAt(labelCalculadora.getText().length() - 1) == '-'
                || labelCalculadora.getText().charAt(labelCalculadora.getText().length() - 1) == '%')
                || operacionFinalizada) {
            labelCalculadora.setText(valor);
            operacionFinalizada = false;
        } else {
            labelCalculadora.setText(this.labelCalculadora.getText() + valor);
        }

    }

    public String sinCero(float valor) {
        String retorno = "";
        retorno = Float.toString(valor);
        if (valor % 1 == 0) {
            retorno = retorno.substring(0, retorno.length() - 2);
        }

        return retorno;
    }

    private void cargarLista(String mesa) {

        try {//select en la base de datos
            String sql = "select * from pedidos p  where idmesa=" + mesa + ";";//Sin comillas simples al ser un INT en la BD
            ResultSet rss = st.executeQuery(sql);
            String[] info = new String[4];
            Double total = 0.0;
            String unidades, nombreprod, preciou, preciot;
            int rowCount = modeloTabla.getRowCount();
            //Elimino los registros uno a uno, puesto que no hay un método específico para esto.
            for (int i = rowCount - 1; i >= 0; i--) {
                modeloTabla.removeRow(i);
            }
            while (rss.next()) {
                info[0] = Integer.toString(rss.getInt("cantidad"));
                info[1] = rss.getString("nombreprod");
                info[2] = String.format(Double.toString(rss.getDouble("preciou")) + "€");
                info[3] = String.format(Double.toString(rss.getDouble("preciot")) + "€");
                modeloTabla.addRow(info);
                total += rss.getDouble("preciot");
            }
            lblTotal.setText(String.format("%.2f€", total));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void configurarTabla() {
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Uds");
        modeloTabla.addColumn("Descripción");
        modeloTabla.addColumn("P/U");
        modeloTabla.addColumn("Total");
        this.tabla.setModel(modeloTabla);
        this.tabla.getColumnModel().getColumn(0).setPreferredWidth(40);
        this.tabla.getColumnModel().getColumn(1).setPreferredWidth(240);
        this.tabla.getColumnModel().getColumn(2).setPreferredWidth(40);
        this.tabla.getColumnModel().getColumn(3).setPreferredWidth(50);
        jScrollPane1.getViewport().setBackground(Color.YELLOW);

    }
    //Fin código lógica

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBotones = new javax.swing.JPanel();
        btnFactura = new javax.swing.JButton();
        botEmp = new javax.swing.JButton();
        botAnular = new javax.swing.JButton();
        botCobrar = new javax.swing.JButton();
        botTranspMesa = new javax.swing.JButton();
        botMesa = new javax.swing.JButton();
        botSalir = new javax.swing.JButton();
        botAparcar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        panelCalculadora = new javax.swing.JPanel();
        labelCalculadora = new javax.swing.JLabel();
        panelBotCalc = new javax.swing.JPanel();
        botonSiete = new javax.swing.JButton();
        botonOcho = new javax.swing.JButton();
        botonNueve = new javax.swing.JButton();
        botonBorrar = new javax.swing.JButton();
        botonC = new javax.swing.JButton();
        botonCuatro = new javax.swing.JButton();
        botonCinco = new javax.swing.JButton();
        botonSeis = new javax.swing.JButton();
        botonDiv = new javax.swing.JButton();
        botonMult = new javax.swing.JButton();
        botonUno = new javax.swing.JButton();
        botonDos = new javax.swing.JButton();
        botonTres = new javax.swing.JButton();
        botonMenos = new javax.swing.JButton();
        botonMas = new javax.swing.JButton();
        botonCero = new javax.swing.JButton();
        botonCeroCero = new javax.swing.JButton();
        botonComa = new javax.swing.JButton();
        botonMod = new javax.swing.JButton();
        botonIgual = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        panelCuenta = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        labelMesa2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new rojerusan.RSTableMetro();
        jPanel4 = new javax.swing.JPanel();
        panelGrupos = new javax.swing.JPanel();
        panelProductos = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SGH");
        setBackground(new java.awt.Color(0, 153, 255));

        panelBotones.setBackground(new java.awt.Color(255, 153, 0));
        panelBotones.setPreferredSize(new java.awt.Dimension(234, 609));

        btnFactura.setActionCommand("Maximizar\n Pantalla\n");
        btnFactura.setAutoscrolls(true);
        btnFactura.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFacturaActionPerformed(evt);
            }
        });

        botEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botEmpActionPerformed(evt);
            }
        });

        botAnular.setIconTextGap(0);
        botAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botAnularActionPerformed(evt);
            }
        });

        botCobrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botCobrarActionPerformed(evt);
            }
        });

        botTranspMesa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botTranspMesaActionPerformed(evt);
            }
        });

        botMesa.setPreferredSize(new java.awt.Dimension(102, 123));
        botMesa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botMesaActionPerformed(evt);
            }
        });

        botSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botSalirActionPerformed(evt);
            }
        });

        botAparcar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botAparcarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBotonesLayout = new javax.swing.GroupLayout(panelBotones);
        panelBotones.setLayout(panelBotonesLayout);
        panelBotonesLayout.setHorizontalGroup(
            panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBotonesLayout.createSequentialGroup()
                        .addComponent(botAnular, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botMesa, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBotonesLayout.createSequentialGroup()
                        .addComponent(botAparcar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botTranspMesa, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBotonesLayout.createSequentialGroup()
                        .addComponent(botCobrar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBotonesLayout.createSequentialGroup()
                        .addComponent(botEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 28, Short.MAX_VALUE))
        );
        panelBotonesLayout.setVerticalGroup(
            panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botTranspMesa, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botAparcar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botAnular, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botMesa, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botCobrar, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        panelCalculadora.setAlignmentX(0.0F);
        panelCalculadora.setAlignmentY(0.0F);
        panelCalculadora.setPreferredSize(new java.awt.Dimension(295, 260));

        labelCalculadora.setBackground(new java.awt.Color(0, 0, 0));
        labelCalculadora.setFont(new java.awt.Font("Dialog", 1, 21)); // NOI18N
        labelCalculadora.setForeground(new java.awt.Color(255, 255, 255));
        labelCalculadora.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelCalculadora.setText("0");
        labelCalculadora.setOpaque(true);

        panelBotCalc.setBackground(new java.awt.Color(204, 204, 204));
        panelBotCalc.setPreferredSize(new java.awt.Dimension(322, 258));
        panelBotCalc.setLayout(new java.awt.GridLayout(4, 5));

        botonSiete.setAlignmentY(0.0F);
        botonSiete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSieteActionPerformed(evt);
            }
        });
        panelBotCalc.add(botonSiete);

        botonOcho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonOchoActionPerformed(evt);
            }
        });
        panelBotCalc.add(botonOcho);

        botonNueve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonNueveActionPerformed(evt);
            }
        });
        panelBotCalc.add(botonNueve);

        botonBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBorrarActionPerformed(evt);
            }
        });
        panelBotCalc.add(botonBorrar);

        botonC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCActionPerformed(evt);
            }
        });
        panelBotCalc.add(botonC);

        botonCuatro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCuatroActionPerformed(evt);
            }
        });
        panelBotCalc.add(botonCuatro);

        botonCinco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCincoActionPerformed(evt);
            }
        });
        panelBotCalc.add(botonCinco);

        botonSeis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSeisActionPerformed(evt);
            }
        });
        panelBotCalc.add(botonSeis);

        botonDiv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonDivActionPerformed(evt);
            }
        });
        panelBotCalc.add(botonDiv);

        botonMult.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonMultActionPerformed(evt);
            }
        });
        panelBotCalc.add(botonMult);

        botonUno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonUnoActionPerformed(evt);
            }
        });
        panelBotCalc.add(botonUno);

        botonDos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonDosActionPerformed(evt);
            }
        });
        panelBotCalc.add(botonDos);

        botonTres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonTresActionPerformed(evt);
            }
        });
        panelBotCalc.add(botonTres);

        botonMenos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonMenosActionPerformed(evt);
            }
        });
        panelBotCalc.add(botonMenos);

        botonMas.setToolTipText("");
        botonMas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonMasActionPerformed(evt);
            }
        });
        panelBotCalc.add(botonMas);

        botonCero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCeroActionPerformed(evt);
            }
        });
        panelBotCalc.add(botonCero);

        botonCeroCero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCeroCeroActionPerformed(evt);
            }
        });
        panelBotCalc.add(botonCeroCero);

        botonComa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonComaActionPerformed(evt);
            }
        });
        panelBotCalc.add(botonComa);

        botonMod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonModActionPerformed(evt);
            }
        });
        panelBotCalc.add(botonMod);

        botonIgual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonIgualActionPerformed(evt);
            }
        });
        panelBotCalc.add(botonIgual);

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Total:");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel1.setOpaque(true);

        lblTotal.setBackground(new java.awt.Color(0, 0, 0));
        lblTotal.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotal.setText("0€");
        lblTotal.setOpaque(true);

        javax.swing.GroupLayout panelCalculadoraLayout = new javax.swing.GroupLayout(panelCalculadora);
        panelCalculadora.setLayout(panelCalculadoraLayout);
        panelCalculadoraLayout.setHorizontalGroup(
            panelCalculadoraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCalculadoraLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(labelCalculadora, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelBotCalc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        panelCalculadoraLayout.setVerticalGroup(
            panelCalculadoraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCalculadoraLayout.createSequentialGroup()
                .addComponent(labelCalculadora, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(panelBotCalc, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(panelCalculadoraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(lblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        panelCuenta.setBackground(new java.awt.Color(0, 51, 204));
        panelCuenta.setForeground(new java.awt.Color(255, 255, 0));

        jPanel6.setBackground(new java.awt.Color(51, 51, 51));
        jPanel6.setForeground(new java.awt.Color(0, 0, 0));

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Mesa actual:");
        jLabel5.setOpaque(true);

        labelMesa2.setBackground(new java.awt.Color(0, 0, 0));
        labelMesa2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        labelMesa2.setForeground(new java.awt.Color(255, 255, 255));
        labelMesa2.setText("1");
        labelMesa2.setOpaque(true);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(labelMesa2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
            .addComponent(labelMesa2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tabla.setBackground(new java.awt.Color(51, 51, 51));
        tabla.setForeground(new java.awt.Color(102, 102, 102));
        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabla.setToolTipText("");
        tabla.setAltoHead(20);
        tabla.setColorBackgoundHead(new java.awt.Color(255, 102, 102));
        tabla.setColorBordeHead(new java.awt.Color(102, 0, 0));
        tabla.setColorFilasBackgound1(new java.awt.Color(255, 153, 0));
        tabla.setColorFilasBackgound2(new java.awt.Color(204, 102, 0));
        tabla.setColorFilasForeground1(new java.awt.Color(0, 0, 0));
        tabla.setColorFilasForeground2(new java.awt.Color(0, 0, 0));
        tabla.setColorForegroundHead(new java.awt.Color(51, 51, 51));
        tabla.setColorSelBackgound(new java.awt.Color(0, 51, 0));
        tabla.setColorSelForeground(new java.awt.Color(153, 102, 0));
        tabla.setFuenteHead(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tabla.setGridColor(new java.awt.Color(0, 51, 204));
        tabla.setMultipleSeleccion(false);
        tabla.setRowHeight(20);
        tabla.setSelectionForeground(new java.awt.Color(51, 51, 51));
        jScrollPane1.setViewportView(tabla);

        javax.swing.GroupLayout panelCuentaLayout = new javax.swing.GroupLayout(panelCuenta);
        panelCuenta.setLayout(panelCuentaLayout);
        panelCuentaLayout.setHorizontalGroup(
            panelCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCuentaLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelCuentaLayout.setVerticalGroup(
            panelCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCuentaLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(panelCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(panelCalculadora, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelCalculadora, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                    .addComponent(panelCuenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelGrupos.setBackground(new java.awt.Color(0, 153, 0));

        javax.swing.GroupLayout panelGruposLayout = new javax.swing.GroupLayout(panelGrupos);
        panelGrupos.setLayout(panelGruposLayout);
        panelGruposLayout.setHorizontalGroup(
            panelGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 226, Short.MAX_VALUE)
        );
        panelGruposLayout.setVerticalGroup(
            panelGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 276, Short.MAX_VALUE)
        );

        panelProductos.setBackground(new java.awt.Color(255, 204, 51));

        javax.swing.GroupLayout panelProductosLayout = new javax.swing.GroupLayout(panelProductos);
        panelProductos.setLayout(panelProductosLayout);
        panelProductosLayout.setHorizontalGroup(
            panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 528, Short.MAX_VALUE)
        );
        panelProductosLayout.setVerticalGroup(
            panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(panelGrupos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(panelProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelGrupos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 680, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(panelBotones, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(panelBotones, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_botSalirActionPerformed

    private void btnFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFacturaActionPerformed
        try {
            String sql = "select nombre from bloqueada;";
            ResultSet rss;
            try {
                rss = st.executeQuery(sql);
                if(rss.next()){
                nombre=rss.getString("nombre");
                sql=null;
                rss.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(FramePrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            JasperReport reporte=null;
            String path="src\\reportes\\reporte_factura.jasper";
            System.out.println(nombre);
            Map parametro = new HashMap();
            parametro.put("idmesa",labelMesa2.getText().toString());
            parametro.put("empleado",nombre);
            reporte=(JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint jprint = JasperFillManager.fillReport(reporte,parametro,cn);
            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(FramePrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnFacturaActionPerformed

    private void botAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botAnularActionPerformed
        DefaultTableModel model = (DefaultTableModel) this.tabla.getModel();
        
        String nombreprod =tabla.getModel().getValueAt(tabla.getSelectedRow(),1).toString();
        String cantidad= tabla.getModel().getValueAt(tabla.getSelectedRow(),0).toString();
        String sql = "DELETE FROM pedidos WHERE idmesa='" + labelMesa2.getText() + "' AND nombreprod='"+nombreprod+
                "' AND cantidad= '"+cantidad+"' LIMIT 1;";
        try {
            PreparedStatement preparedStmt = cn.prepareStatement(sql);
            preparedStmt.execute();
            cargarLista(labelMesa2.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        cargarLista(labelMesa2.getText());
    }//GEN-LAST:event_botAnularActionPerformed

    private void botonCincoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCincoActionPerformed
        dibujarNumCalc("5");

    }//GEN-LAST:event_botonCincoActionPerformed

    private void botonDivActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonDivActionPerformed
        this.valorCalc1 = Float.parseFloat(labelCalculadora.getText().toString());
        this.operador = "/";
        this.labelCalculadora.setText(labelCalculadora.getText() + "/");
    }//GEN-LAST:event_botonDivActionPerformed

    private void botonSieteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSieteActionPerformed
        dibujarNumCalc("7");
    }//GEN-LAST:event_botonSieteActionPerformed

    private void botonUnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonUnoActionPerformed
        dibujarNumCalc("1");
    }//GEN-LAST:event_botonUnoActionPerformed

    private void botonOchoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonOchoActionPerformed
        dibujarNumCalc("8");
    }//GEN-LAST:event_botonOchoActionPerformed

    private void botonNueveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonNueveActionPerformed
        dibujarNumCalc("9");
    }//GEN-LAST:event_botonNueveActionPerformed

    private void botonBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBorrarActionPerformed
        if (labelCalculadora.getText().toString().equals("0") || (labelCalculadora.getText().charAt(labelCalculadora.getText().length() - 1) == '*'
                || labelCalculadora.getText().charAt(labelCalculadora.getText().length() - 1) == '/'
                || labelCalculadora.getText().charAt(labelCalculadora.getText().length() - 1) == '+'
                || labelCalculadora.getText().charAt(labelCalculadora.getText().length() - 1) == '-'
                || labelCalculadora.getText().charAt(labelCalculadora.getText().length() - 1) == '%')
                || operacionFinalizada
                || labelCalculadora.getText().length() == 1) {
            labelCalculadora.setText("0");
            operacionFinalizada = false;
        } else {
            String str = labelCalculadora.getText();
            str = str.substring(0, str.length() - 1);
            labelCalculadora.setText(str);
            str = null;
        }
    }//GEN-LAST:event_botonBorrarActionPerformed

    private void botonCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCActionPerformed
        labelCalculadora.setText("0");
        valorCalc1 = 0;
        valorCalc2 = 0;
    }//GEN-LAST:event_botonCActionPerformed

    private void botonCuatroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCuatroActionPerformed
        dibujarNumCalc("4");
    }//GEN-LAST:event_botonCuatroActionPerformed

    private void botonSeisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSeisActionPerformed
        dibujarNumCalc("6");
    }//GEN-LAST:event_botonSeisActionPerformed

    private void botonMultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonMultActionPerformed
        this.valorCalc1 = Float.parseFloat(labelCalculadora.getText().toString());
        this.operador = "*";
        this.labelCalculadora.setText(labelCalculadora.getText() + "*");
    }//GEN-LAST:event_botonMultActionPerformed

    private void botonDosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonDosActionPerformed
        dibujarNumCalc("2");
    }//GEN-LAST:event_botonDosActionPerformed

    private void botonTresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonTresActionPerformed
        dibujarNumCalc("3");
    }//GEN-LAST:event_botonTresActionPerformed

    private void botonMenosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonMenosActionPerformed
        this.valorCalc1 = Float.parseFloat(labelCalculadora.getText().toString());
        this.operador = "-";
        this.labelCalculadora.setText(labelCalculadora.getText() + "-");
    }//GEN-LAST:event_botonMenosActionPerformed

    private void botonMasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonMasActionPerformed
        this.valorCalc1 = Float.parseFloat(labelCalculadora.getText().toString());
        this.operador = "+";
        this.labelCalculadora.setText(labelCalculadora.getText() + "+");
    }//GEN-LAST:event_botonMasActionPerformed

    private void botonCeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCeroActionPerformed
        dibujarNumCalc("0");
    }//GEN-LAST:event_botonCeroActionPerformed

    private void botonCeroCeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCeroCeroActionPerformed
        dibujarNumCalc("00");
    }//GEN-LAST:event_botonCeroCeroActionPerformed

    private void botonComaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonComaActionPerformed
        if (!this.labelCalculadora.getText().contains(".")) {
            dibujarNumCalc(".");
        }
    }//GEN-LAST:event_botonComaActionPerformed

    private void botonModActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonModActionPerformed
        this.valorCalc1 = Float.parseFloat(labelCalculadora.getText().toString());
        this.operador = "%";
        this.labelCalculadora.setText(labelCalculadora.getText() + "%");
    }//GEN-LAST:event_botonModActionPerformed

    private void botonIgualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonIgualActionPerformed
        String valorPrueba = labelCalculadora.getText();

        this.valorCalc2 = Float.parseFloat(valorPrueba);
        valorPrueba = null;
        operacionFinalizada = true;
        switch (operador) {
            case "*":
                this.labelCalculadora.setText(sinCero(valorCalc1 * valorCalc2));
                break;
            case "/":
                if (valorCalc2 != 0) {
                    this.labelCalculadora.setText(sinCero(valorCalc1 / valorCalc2));
                    break;
                } else {
                    valorCalc1 = 0;
                    valorCalc2 = 0;
                    operacionFinalizada = false;
                    this.labelCalculadora.setText("0");
                    break;
                }

            case "+":
                this.labelCalculadora.setText(sinCero(valorCalc1 + valorCalc2));
                break;
            case "-":
                this.labelCalculadora.setText(sinCero(valorCalc1 - valorCalc2));
                break;
            case "%":
                this.labelCalculadora.setText(sinCero(valorCalc1 % valorCalc2));
                break;
        }

    }//GEN-LAST:event_botonIgualActionPerformed

    private void botMesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botMesaActionPerformed
        /*if(labelCalculadora.getText().charAt(labelCalculadora.getText().length()-1) == '*'){
            int i= labelCalculadora.getText().charAt(labelCalculadora.getText().length()-2);
            System.out.println(i);
        }*/ //para listener de productos

        if (!((labelCalculadora.getText().charAt(labelCalculadora.getText().length() - 1) == '*'
                || labelCalculadora.getText().charAt(labelCalculadora.getText().length() - 1) == '/'
                || labelCalculadora.getText().charAt(labelCalculadora.getText().length() - 1) == '+'
                || labelCalculadora.getText().charAt(labelCalculadora.getText().length() - 1) == '-'
                || labelCalculadora.getText().charAt(labelCalculadora.getText().length() - 1) == '%')
                || operacionFinalizada)) {
            if(labelCalculadora.getText().toString().equals("0")){
                FrameMesas frameMesas = new FrameMesas();
                frameMesas.setVisible(true);
            }else{
                String sql = "select * from mesas where nummesa="+labelCalculadora.getText().toString();
                try{
                    ResultSet rss = st.executeQuery(sql);
                    if(rss.next()){
                        cargarLista(Integer.toString((int) Math.round(Float.valueOf(labelCalculadora.getText()))));
                        labelMesa2.setText(Integer.toString((int) Math.round(Float.valueOf(labelCalculadora.getText()))));
                        // Si hay un valor con coma, para evitar problemas convierto string a float, float a int(elimino coma) y de nuevo int a string.
                        labelCalculadora.setText("0");
                    }else{
                        JOptionPane.showMessageDialog(this,"La mesa "+ labelCalculadora.getText().toString()+" no existe.");
                        labelCalculadora.setText("0");
                    }
                }catch(Exception e){
                    
                }
            }
            
            
            
        }

    }//GEN-LAST:event_botMesaActionPerformed

    private void botCobrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botCobrarActionPerformed
        String sql = "DELETE FROM pedidos WHERE idmesa='" + labelMesa2.getText() + "';";
        String sql2 = "update mesas set ocupada=false where nummesa="+labelMesa2.getText() + ";";
        try {
            PreparedStatement preparedStmt = cn.prepareStatement(sql);
            preparedStmt.execute();
            cargarLista(labelMesa2.getText());
            st.executeUpdate(sql2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        

    }//GEN-LAST:event_botCobrarActionPerformed

    private void botAparcarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botAparcarActionPerformed
        try{
            String sql = "update bloqueada set nombre=\"\" where idbloqueada=1";
            st.executeUpdate(sql);
        }catch(SQLException e){
            e.printStackTrace();
        }
        
    }//GEN-LAST:event_botAparcarActionPerformed

    private void botTranspMesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botTranspMesaActionPerformed
        if(labelCalculadora.getText().toString().equals("0")){
            JOptionPane.showMessageDialog(this,"Por favor, introduce el número de la mesa.");
        }else{
            try{
                String sql = "select nummesa from mesas where nummesa="+labelCalculadora.getText().toString()+";";
                ResultSet rss = st.executeQuery(sql);
                if(!rss.next()) {
                    JOptionPane.showMessageDialog(this,"La mesa "+ labelCalculadora.getText().toString()+" no existe.");
                }else{
                    sql = "update pedidos set idmesa="+labelCalculadora.getText().toString()+" where idmesa="+labelMesa2.getText().toString()+";";
                    st.executeUpdate(sql);
                    cargarLista(labelMesa2.getText());
                    labelCalculadora.setText("0");
                }
                sql=null;
                rss=null;
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        
        
        
    }//GEN-LAST:event_botTranspMesaActionPerformed

    private void botEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botEmpActionPerformed
        
        
        try{
            String sql = "select nombre from bloqueada;";
            ResultSet rss = st.executeQuery(sql);
            if(rss.next()){
                nombre=rss.getString("nombre");
            }
            sql=null;
            rss.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        InformacionEmpleado info = new InformacionEmpleado(nombre);
        info.setVisible(true);
        
    }//GEN-LAST:event_botEmpActionPerformed

    private void arreglarImagenes() {
        this.setSize(930, 580);
        this.setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        ImageIcon icon = new ImageIcon(getClass().getResource("/resources/logo_sgh.png"));
        this.setIconImage(icon.getImage());
        
        try{
            String sql = "update bloqueada set nombre=\"\" where idbloqueada=1";
            st.executeUpdate(sql);
        }catch(SQLException e){
            e.printStackTrace();
        }
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        panelBloqueado = new JPanelBackground();
        panelBloqueado.setBounds(0, 0, 930, 580);
        panelBloqueado.setBackground("/resources/bloqueada.jpg");
        this.getContentPane().add(panelBloqueado);
        panelBloqueado.setVisible(false);
        ImageIcon iconoPrueba = new javax.swing.ImageIcon(getClass().getResource("/resources/boton1.jpg"));
        Image imagenPrueba = iconoPrueba.getImage();
        Image imagenModificada = imagenPrueba.getScaledInstance(panelBotCalc.getPreferredSize().width / 5, panelBotCalc.getPreferredSize().height / 4, java.awt.Image.SCALE_SMOOTH);
        Icon iconoFinal1 = new ImageIcon(imagenModificada);
        botonUno.setIcon(iconoFinal1);

        iconoPrueba = new javax.swing.ImageIcon(getClass().getResource("/resources/boton2.jpg"));
        imagenPrueba = iconoPrueba.getImage();
        imagenModificada = imagenPrueba.getScaledInstance(panelBotCalc.getPreferredSize().width / 5, panelBotCalc.getPreferredSize().height / 4, java.awt.Image.SCALE_SMOOTH);
        Icon iconoFinal2 = new ImageIcon(imagenModificada);
        botonDos.setIcon(iconoFinal2);

        iconoPrueba = new javax.swing.ImageIcon(getClass().getResource("/resources/boton3.jpg"));
        imagenPrueba = iconoPrueba.getImage();
        imagenModificada = imagenPrueba.getScaledInstance(panelBotCalc.getPreferredSize().width / 5, panelBotCalc.getPreferredSize().height / 4, java.awt.Image.SCALE_SMOOTH);
        Icon iconoFinal3 = new ImageIcon(imagenModificada);
        botonTres.setIcon(iconoFinal3);

        iconoPrueba = new javax.swing.ImageIcon(getClass().getResource("/resources/boton4.jpg"));
        imagenPrueba = iconoPrueba.getImage();
        imagenModificada = imagenPrueba.getScaledInstance(panelBotCalc.getPreferredSize().width / 5, panelBotCalc.getPreferredSize().height / 4, java.awt.Image.SCALE_SMOOTH);
        Icon iconoFinal4 = new ImageIcon(imagenModificada);
        botonCuatro.setIcon(iconoFinal4);

        iconoPrueba = new javax.swing.ImageIcon(getClass().getResource("/resources/boton5.jpg"));
        imagenPrueba = iconoPrueba.getImage();
        imagenModificada = imagenPrueba.getScaledInstance(panelBotCalc.getPreferredSize().width / 5, panelBotCalc.getPreferredSize().height / 4, java.awt.Image.SCALE_SMOOTH);
        Icon iconoFinal5 = new ImageIcon(imagenModificada);
        botonCinco.setIcon(iconoFinal5);

        iconoPrueba = new javax.swing.ImageIcon(getClass().getResource("/resources/boton6.jpg"));
        imagenPrueba = iconoPrueba.getImage();
        imagenModificada = imagenPrueba.getScaledInstance(panelBotCalc.getPreferredSize().width / 5, panelBotCalc.getPreferredSize().height / 4, java.awt.Image.SCALE_SMOOTH);
        Icon iconoFinal6 = new ImageIcon(imagenModificada);
        botonSeis.setIcon(iconoFinal6);

        iconoPrueba = new javax.swing.ImageIcon(getClass().getResource("/resources/boton7.jpg"));
        imagenPrueba = iconoPrueba.getImage();
        imagenModificada = imagenPrueba.getScaledInstance(panelBotCalc.getPreferredSize().width / 5, panelBotCalc.getPreferredSize().height / 4, java.awt.Image.SCALE_SMOOTH);
        Icon iconoFinal7 = new ImageIcon(imagenModificada);
        botonSiete.setIcon(iconoFinal7);

        iconoPrueba = new javax.swing.ImageIcon(getClass().getResource("/resources/boton8.jpg"));
        imagenPrueba = iconoPrueba.getImage();
        imagenModificada = imagenPrueba.getScaledInstance(panelBotCalc.getPreferredSize().width / 5, panelBotCalc.getPreferredSize().height / 4, java.awt.Image.SCALE_SMOOTH);
        Icon iconoFinal8 = new ImageIcon(imagenModificada);
        botonOcho.setIcon(iconoFinal8);

        iconoPrueba = new javax.swing.ImageIcon(getClass().getResource("/resources/boton9.jpg"));
        imagenPrueba = iconoPrueba.getImage();
        imagenModificada = imagenPrueba.getScaledInstance(panelBotCalc.getPreferredSize().width / 5, panelBotCalc.getPreferredSize().height / 4, java.awt.Image.SCALE_SMOOTH);
        Icon iconoFinal9 = new ImageIcon(imagenModificada);
        botonNueve.setIcon(iconoFinal9);

        iconoPrueba = new javax.swing.ImageIcon(getClass().getResource("/resources/boton0.jpg"));
        imagenPrueba = iconoPrueba.getImage();
        imagenModificada = imagenPrueba.getScaledInstance(panelBotCalc.getPreferredSize().width / 5, panelBotCalc.getPreferredSize().height / 4, java.awt.Image.SCALE_SMOOTH);
        Icon iconoFinal0 = new ImageIcon(imagenModificada);
        botonCero.setIcon(iconoFinal0);

        iconoPrueba = new javax.swing.ImageIcon(getClass().getResource("/resources/boton00.jpg"));
        imagenPrueba = iconoPrueba.getImage();
        imagenModificada = imagenPrueba.getScaledInstance(panelBotCalc.getPreferredSize().width / 5, panelBotCalc.getPreferredSize().height / 4, java.awt.Image.SCALE_SMOOTH);
        Icon iconoFinal00 = new ImageIcon(imagenModificada);
        botonCeroCero.setIcon(iconoFinal00);

        iconoPrueba = new javax.swing.ImageIcon(getClass().getResource("/resources/boton+.jpg"));
        imagenPrueba = iconoPrueba.getImage();
        imagenModificada = imagenPrueba.getScaledInstance(panelBotCalc.getPreferredSize().width / 5, panelBotCalc.getPreferredSize().height / 4, java.awt.Image.SCALE_SMOOTH);
        Icon iconoFinal01 = new ImageIcon(imagenModificada);
        botonMas.setIcon(iconoFinal01);

        iconoPrueba = new javax.swing.ImageIcon(getClass().getResource("/resources/boton-.jpg"));
        imagenPrueba = iconoPrueba.getImage();
        imagenModificada = imagenPrueba.getScaledInstance(panelBotCalc.getPreferredSize().width / 5, panelBotCalc.getPreferredSize().height / 4, java.awt.Image.SCALE_SMOOTH);
        Icon iconoFinal02 = new ImageIcon(imagenModificada);
        botonMenos.setIcon(iconoFinal02);

        iconoPrueba = new javax.swing.ImageIcon(getClass().getResource("/resources/botonDiv.jpg"));
        imagenPrueba = iconoPrueba.getImage();
        imagenModificada = imagenPrueba.getScaledInstance(panelBotCalc.getPreferredSize().width / 5, panelBotCalc.getPreferredSize().height / 4, java.awt.Image.SCALE_SMOOTH);
        Icon iconoFinal03 = new ImageIcon(imagenModificada);
        botonDiv.setIcon(iconoFinal03);

        iconoPrueba = new javax.swing.ImageIcon(getClass().getResource("/resources/botonMult.jpg"));
        imagenPrueba = iconoPrueba.getImage();
        imagenModificada = imagenPrueba.getScaledInstance(panelBotCalc.getPreferredSize().width / 5, panelBotCalc.getPreferredSize().height / 4, java.awt.Image.SCALE_SMOOTH);
        Icon iconoFinal04 = new ImageIcon(imagenModificada);
        botonMult.setIcon(iconoFinal04);

        iconoPrueba = new javax.swing.ImageIcon(getClass().getResource("/resources/botonMod.jpg"));
        imagenPrueba = iconoPrueba.getImage();
        imagenModificada = imagenPrueba.getScaledInstance(panelBotCalc.getPreferredSize().width / 5, panelBotCalc.getPreferredSize().height / 4, java.awt.Image.SCALE_SMOOTH);
        Icon iconoFinal05 = new ImageIcon(imagenModificada);
        botonMod.setIcon(iconoFinal05);

        iconoPrueba = new javax.swing.ImageIcon(getClass().getResource("/resources/botonComa.jpg"));
        imagenPrueba = iconoPrueba.getImage();
        imagenModificada = imagenPrueba.getScaledInstance(panelBotCalc.getPreferredSize().width / 5, panelBotCalc.getPreferredSize().height / 4, java.awt.Image.SCALE_SMOOTH);
        Icon iconoFinal06 = new ImageIcon(imagenModificada);
        botonComa.setIcon(iconoFinal06);

        iconoPrueba = new javax.swing.ImageIcon(getClass().getResource("/resources/botonIgual.jpg"));
        imagenPrueba = iconoPrueba.getImage();
        imagenModificada = imagenPrueba.getScaledInstance(panelBotCalc.getPreferredSize().width / 5, panelBotCalc.getPreferredSize().height / 4, java.awt.Image.SCALE_SMOOTH);
        Icon iconoFinal07 = new ImageIcon(imagenModificada);
        botonIgual.setIcon(iconoFinal07);

        iconoPrueba = new javax.swing.ImageIcon(getClass().getResource("/resources/botonBorrar.jpg"));
        imagenPrueba = iconoPrueba.getImage();
        imagenModificada = imagenPrueba.getScaledInstance(panelBotCalc.getPreferredSize().width / 5, panelBotCalc.getPreferredSize().height / 4, java.awt.Image.SCALE_SMOOTH);
        Icon iconoFinal08 = new ImageIcon(imagenModificada);
        botonBorrar.setIcon(iconoFinal08);

        iconoPrueba = new javax.swing.ImageIcon(getClass().getResource("/resources/botonC.jpg"));
        imagenPrueba = iconoPrueba.getImage();
        imagenModificada = imagenPrueba.getScaledInstance(panelBotCalc.getPreferredSize().width / 5, panelBotCalc.getPreferredSize().height / 4, java.awt.Image.SCALE_SMOOTH);
        Icon iconoFinal09 = new ImageIcon(imagenModificada);
        botonC.setIcon(iconoFinal09);

        iconoPrueba = new javax.swing.ImageIcon(getClass().getResource("/resources/mesa.jpg"));
        imagenPrueba = iconoPrueba.getImage();
        imagenModificada = imagenPrueba.getScaledInstance(botMesa.getPreferredSize().width, botMesa.getPreferredSize().height, java.awt.Image.SCALE_SMOOTH);
        Icon iconoFinal10 = new ImageIcon(imagenModificada);
        botMesa.setIcon(iconoFinal10);
        iconoPrueba = null;
        iconoPrueba = new javax.swing.ImageIcon(getClass().getResource("/resources/salir.jpg"));
        imagenPrueba = iconoPrueba.getImage();
        imagenModificada = imagenPrueba.getScaledInstance(botMesa.getPreferredSize().width, botMesa.getPreferredSize().height, java.awt.Image.SCALE_SMOOTH);
        Icon iconoFinal11 = new ImageIcon(imagenModificada);
        botSalir.setIcon(iconoFinal11);
        iconoPrueba = null;
        iconoPrueba = new javax.swing.ImageIcon(getClass().getResource("/resources/empleado.jpg"));
        imagenPrueba = iconoPrueba.getImage();
        imagenModificada = imagenPrueba.getScaledInstance(botMesa.getPreferredSize().width, botMesa.getPreferredSize().height, java.awt.Image.SCALE_SMOOTH);
        Icon iconoFinal12 = new ImageIcon(imagenModificada);
        botEmp.setIcon(iconoFinal12);
        iconoPrueba = null;
        iconoPrueba = new javax.swing.ImageIcon(getClass().getResource("/resources/generarFactura.jpg"));
        imagenPrueba = iconoPrueba.getImage();
        imagenModificada = imagenPrueba.getScaledInstance(botMesa.getPreferredSize().width, botMesa.getPreferredSize().height, java.awt.Image.SCALE_SMOOTH);
        Icon iconoFinal13 = new ImageIcon(imagenModificada);
        btnFactura.setIcon(iconoFinal13);
        iconoPrueba = null;
        iconoPrueba = new javax.swing.ImageIcon(getClass().getResource("/resources/transpasarMesa.jpg"));
        imagenPrueba = iconoPrueba.getImage();
        imagenModificada = imagenPrueba.getScaledInstance(botMesa.getPreferredSize().width, botMesa.getPreferredSize().height, java.awt.Image.SCALE_SMOOTH);
        Icon iconoFinal14 = new ImageIcon(imagenModificada);
        botTranspMesa.setIcon(iconoFinal14);
        iconoPrueba = null;
        iconoPrueba = new javax.swing.ImageIcon(getClass().getResource("/resources/cobrar.jpg"));
        imagenPrueba = iconoPrueba.getImage();
        imagenModificada = imagenPrueba.getScaledInstance(botMesa.getPreferredSize().width, botMesa.getPreferredSize().height, java.awt.Image.SCALE_SMOOTH);
        Icon iconoFinal15 = new ImageIcon(imagenModificada);
        botCobrar.setIcon(iconoFinal15);
        iconoPrueba = null;
        iconoPrueba = new javax.swing.ImageIcon(getClass().getResource("/resources/anular.jpg"));
        imagenPrueba = iconoPrueba.getImage();
        imagenModificada = imagenPrueba.getScaledInstance(botMesa.getPreferredSize().width, botMesa.getPreferredSize().height, java.awt.Image.SCALE_SMOOTH);
        Icon iconoFinal16 = new ImageIcon(imagenModificada);
        botAnular.setIcon(iconoFinal16);
        iconoPrueba = null;
        iconoPrueba = new javax.swing.ImageIcon(getClass().getResource("/resources/apartarTicket.jpg"));
        imagenPrueba = iconoPrueba.getImage();
        imagenModificada = imagenPrueba.getScaledInstance(botMesa.getPreferredSize().width, botMesa.getPreferredSize().height, java.awt.Image.SCALE_SMOOTH);
        Icon iconoFinal17 = new ImageIcon(imagenModificada);
        botAparcar.setIcon(iconoFinal17);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botAnular;
    private javax.swing.JButton botAparcar;
    private javax.swing.JButton botCobrar;
    private javax.swing.JButton botEmp;
    private javax.swing.JButton botMesa;
    private javax.swing.JButton botSalir;
    private javax.swing.JButton botTranspMesa;
    private javax.swing.JButton botonBorrar;
    private javax.swing.JButton botonC;
    private javax.swing.JButton botonCero;
    private javax.swing.JButton botonCeroCero;
    private javax.swing.JButton botonCinco;
    private javax.swing.JButton botonComa;
    private javax.swing.JButton botonCuatro;
    private javax.swing.JButton botonDiv;
    private javax.swing.JButton botonDos;
    private javax.swing.JButton botonIgual;
    private javax.swing.JButton botonMas;
    private javax.swing.JButton botonMenos;
    private javax.swing.JButton botonMod;
    private javax.swing.JButton botonMult;
    private javax.swing.JButton botonNueve;
    private javax.swing.JButton botonOcho;
    private javax.swing.JButton botonSeis;
    private javax.swing.JButton botonSiete;
    private javax.swing.JButton botonTres;
    private javax.swing.JButton botonUno;
    private javax.swing.JButton btnFactura;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelCalculadora;
    private javax.swing.JLabel labelMesa2;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JPanel panelBotCalc;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JPanel panelCalculadora;
    private javax.swing.JPanel panelCuenta;
    private javax.swing.JPanel panelGrupos;
    private javax.swing.JPanel panelProductos;
    private rojerusan.RSTableMetro tabla;
    // End of variables declaration//GEN-END:variables
}
