/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */


//////////KATHIA///////////////////////////////////////////
import java.util.ArrayList;
import java.util.List;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
//////////////////////////////////////KATHIA/////////////////////////////////////////////////////////
public class main extends javax.swing.JFrame {
    private DefaultTableModel modelo;
    private JTable miTablaProcesos;

    public main() {
        initComponents();
        getContentPane().setBackground(Color.WHITE);
        this.setLocationRelativeTo(null);
        No_procesos.setEditable(false);
        configurarTabla();
        mostrar_procesos();
    }

    private void configurarTabla() {
        modelo = new DefaultTableModel(new Object[][]{},
            new String[]{"Aplicaciones", "Nombre", "PID", "Tipo de sesión", "Número de sesión", "memoria"}) {
            Class[] types = new Class[]{
                ImageIcon.class, String.class, String.class, String.class, String.class, String.class
            };
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }

            public Class<?> getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };

        jtabla_datos.setModel(modelo);

        DefaultTableCellRenderer Alinear = new DefaultTableCellRenderer();
        Alinear.setHorizontalAlignment(SwingConstants.RIGHT);
        for (int col = 2; col <= 5; col++) {
            jtabla_datos.getColumnModel().getColumn(col).setCellRenderer(Alinear);
        }
    }
////////////////////////////////////////PABLO///////////////////////////////////////////////////////////////////
    private void mostrar_procesos() {
        modelo.setRowCount(0);

        try {
            Process p = Runtime.getRuntime().exec(new String[]{"cmd", "/c", "tasklist /FO CSV /NH"});
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream(), "Cp1252"));
            String line;
            while ((line = input.readLine()) != null) {
                String[] parts = line.split("\",\"");
                if (parts.length >= 5) {
                    String nombreExe = parts[0].replace("\"", "").trim();
                    String pid = parts[1].replace("\"", "").trim();
                    String tipoSesion = parts[2].replace("\"", "").trim();
                    String numSesion = parts[3].replace("\"", "").trim();
                    String memoria = parts[4].replace("\"", "").trim();

                    Object[] fila = new Object[]{
                        obtenerIconoDeExe(nombreExe),
                        nombreExe, pid, tipoSesion, numSesion, memoria
                    };
                    modelo.addRow(fila);
                }
            }
            input.close();
            No_procesos.setText(String.valueOf(modelo.getRowCount()));
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
/////////////////////////////MISHEL/////////////////////////////////////////////////
    public static ImageIcon obtenerIconoDeExe(String nombreProceso) {
    try {
        File f = new File("C:\\Windows\\System32\\" + nombreProceso);
        if (f.exists()) {
            javax.swing.Icon icon = FileSystemView.getFileSystemView().getSystemIcon(f);
            return escalarIcono(icon, 16, 16);
        }
    } catch (Exception e) {
        // ignorar
    }

// Ícono genérico: logo de Windows clásico sin fondo
int size = 16;
BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
Graphics2D g2 = img.createGraphics();
g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

// No se dibuja fondo: transparencia

// Tamaño de cada panel
int gap = 1;
int w = (size - gap * 3) / 2;
int h = (size - gap * 3) / 2;

// Panel superior izquierdo (rojo)
g2.setColor(new Color(198, 0, 0));
g2.fillRect(gap, gap, w, h);

// Panel superior derecho (verde)
g2.setColor(new Color(0, 128, 0));
g2.fillRect(gap * 2 + w, gap, w, h);

// Panel inferior izquierdo (azul)
g2.setColor(new Color(0, 102, 204));
g2.fillRect(gap, gap * 2 + h, w, h);

// Panel inferior derecho (amarillo)
g2.setColor(new Color(255, 204, 0));
g2.fillRect(gap * 2 + w, gap * 2 + h, w, h);

g2.dispose();
return new ImageIcon(img);
}
// Método auxiliar para escalar íconos de Windows
private static ImageIcon escalarIcono(javax.swing.Icon icon, int w, int h) {
    BufferedImage img = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics g = img.createGraphics();
    icon.paintIcon(null, g, 0, 0);
    g.dispose();
    return new ImageIcon(img.getScaledInstance(w, h, Image.SCALE_SMOOTH));
}
 
//////////////////////////////////ALISSON//////////////////////////////////////////////////////////////////////////////
public static Map<String, Double> obtenerProcesosDesdeWindows() {
        Map<String, Double> procesos = new LinkedHashMap<>();
        try {
            Process proceso = Runtime.getRuntime().exec(new String[]{"cmd", "/c", "tasklist /FO CSV /NH"});
            BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream(), "Cp1252"));
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split("\",\"");
                if (partes.length >= 5) {
                    String nombre = partes[0].replace("\"", "").trim();
                    String memoriaStr = partes[4].replace("\"", "").replace("K", "").replace(",", "").trim();
                    if (!memoriaStr.isEmpty()) {
                        try {
                            double memoriaKB = Double.parseDouble(memoriaStr);
                            double memoriaMB = memoriaKB / 1024;
                            if (memoriaMB > 0) {
                                procesos.put(nombre, memoriaMB);
                            }
                        } catch (NumberFormatException ignored) {}
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return procesos;
    }
////////////////////////////////PABLO//////////////////////////////////////////////////////////////
    void LimpiarTabla() {
        modelo.setRowCount(0);
    }
///////////////////////////KATHIA/////////////////////////////////////////////////////////
public void Matar_proceso() {
        int fila = jtabla_datos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "ERROR, No se ha seleccionado ningún proceso", "Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String pid = String.valueOf(modelo.getValueAt(fila, 2));
        try {
            Process hijo = Runtime.getRuntime().exec("taskkill /F /PID " + pid);
            hijo.waitFor();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jtabla_datos = new javax.swing.JTable();
        jIniciar_procesos = new javax.swing.JButton();
        jterminar_procesos = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        No_procesos = new javax.swing.JTextField();
        GRAFIC = new javax.swing.JButton();
        nuevatarea = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        Configuracion = new javax.swing.JButton();
        Eficiencia = new javax.swing.JButton();
        Vista = new javax.swing.JButton();
        Historial = new javax.swing.JButton();
        arranque = new javax.swing.JButton();
        usuario = new javax.swing.JButton();
        detalles = new javax.swing.JButton();
        servicios = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setFocusCycleRoot(false);

        jtabla_datos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jtabla_datos.setFont(new java.awt.Font("Footlight MT Light", 1, 14)); // NOI18N
        jtabla_datos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "PID", "Tipo de sesión ", "Número de sesión", "Uso de memoria"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jtabla_datos);

        jIniciar_procesos.setText("Actualizar");
        jIniciar_procesos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jIniciar_procesosActionPerformed(evt);
            }
        });

        jterminar_procesos.setText("⛔Finalizar Tarea");
        jterminar_procesos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jterminar_procesosActionPerformed(evt);
            }
        });

        jLabel2.setText("TOTAL DE PROCESOS: ");

        No_procesos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                No_procesosActionPerformed(evt);
            }
        });

        GRAFIC.setText("📈");
        GRAFIC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GRAFICActionPerformed(evt);
            }
        });

        nuevatarea.setText("➕Ejecutar Nueva Tarea ");
        nuevatarea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevatareaActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Showcard Gothic", 0, 18)); // NOI18N
        jLabel1.setText("PROCESOS");

        jButton1.setForeground(new java.awt.Color(204, 204, 255));

        jButton2.setForeground(new java.awt.Color(204, 204, 255));

        Configuracion.setText("Config");
        Configuracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConfiguracionActionPerformed(evt);
            }
        });

        Eficiencia.setText("🍃Modo de Eficiencia");

        Vista.setText(". . .");
        Vista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VistaActionPerformed(evt);
            }
        });

        Historial.setText("Historial de aplicaciones");
        Historial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HistorialActionPerformed(evt);
            }
        });

        arranque.setText("aplicaciones de arranque");
        arranque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                arranqueActionPerformed(evt);
            }
        });

        usuario.setText("Usuarios");

        detalles.setText("Detalles");

        servicios.setText("Servicios");
        servicios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serviciosActionPerformed(evt);
            }
        });

        jLabel3.setText("ALISSON");

        jLabel4.setText("KATHIA");

        jLabel5.setText("PABLO");

        jLabel6.setText("Alisson");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(No_procesos, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jIniciar_procesos)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(GRAFIC, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(Historial)
                                            .addComponent(jLabel4)
                                            .addComponent(arranque)
                                            .addComponent(usuario)
                                            .addComponent(jLabel5)
                                            .addComponent(detalles)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(jLabel6))
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(Configuracion)
                                                .addComponent(servicios)))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 909, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(202, 202, 202)
                        .addComponent(nuevatarea)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jterminar_procesos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Eficiencia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Vista)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addGap(129, 129, 129))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 920, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap()))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(No_procesos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jIniciar_procesos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nuevatarea)
                    .addComponent(jterminar_procesos)
                    .addComponent(Eficiencia)
                    .addComponent(Vista)
                    .addComponent(jLabel1))
                .addGap(20, 20, 20)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 9, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(GRAFIC)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Historial)
                        .addGap(18, 18, 18)
                        .addComponent(arranque)
                        .addGap(14, 14, 14)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(usuario)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(detalles)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(servicios)
                        .addGap(49, 49, 49)
                        .addComponent(Configuracion)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jIniciar_procesosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jIniciar_procesosActionPerformed
//////PABLO/////
        LimpiarTabla();//limpia la tabla antes de insertr todos los procesos
        mostrar_procesos();//llama al procedimiento de mostrar procesos y los coloca en la tabla

    }//GEN-LAST:event_jIniciar_procesosActionPerformed

    private void jterminar_procesosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jterminar_procesosActionPerformed
//////KATHIA//////////
        Matar_proceso();//llama al procedimiento de terminar un proceso
        LimpiarTabla();//limpia la tabla antes de colocar los procesos despues de haber terminado uno
        mostrar_procesos();//coloca de nuevo los procesos que quedaron sin los que se acaban de terminar
    }//GEN-LAST:event_jterminar_procesosActionPerformed

    private void No_procesosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_No_procesosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_No_procesosActionPerformed

    private void GRAFICActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GRAFICActionPerformed
//MISHEL
    JFrame ventana = new JFrame("Dashboard de Procesos");
    ventana.setSize(950, 650);
    ventana.setLocationRelativeTo(null);
    ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    JPanel panelGrafica = new JPanel() {
        Map<String, Double> procesos = obtenerProcesosDesdeWindows();
        Map<String, Integer> alturasAnimadas = new LinkedHashMap<>();
        Random rand = new Random();

        double ramTotal = 8.0;
        double discoTotal = 512.0;
        double redMax = 100.0;

        // Valores actuales
        double ramUsada = 3.5;
        double discoUsado = 120.0;
        double redRx = 0.0;
        double redTx = 0.0;

        // Valores objetivo (para interpolación)
        double objetivoRam = ramUsada;
        double objetivoDisco = discoUsado;
        double objetivoRedRx = redRx;
        double objetivoRedTx = redTx;

        Color[] colores = {
            new Color(0x1F2937), new Color(0x374151), new Color(0x4B5563),
            new Color(0x2563EB), new Color(0x059669), new Color(0xD97706),
            new Color(0x6B7280), new Color(0x0EA5E9), new Color(0x7C3AED),
            new Color(0xDC2626)
        };

        {
            for (String nombre : procesos.keySet()) {
                alturasAnimadas.put(nombre, rand.nextInt(100));
            }

            new Timer(500, e -> {
                double usoCPU = obtenerUsoCPU();

                //  nuevos procesos
                if (rand.nextDouble() < 0.3 && procesos.size() < 15) {
                    String nuevoProceso = "Proceso" + rand.nextInt(1000) + ".exe";
                    double memoria = 50 + rand.nextDouble() * 300;
                    procesos.put(nuevoProceso, memoria);
                    alturasAnimadas.put(nuevoProceso, rand.nextInt(100));
                }

                //  cierre de procesos
                if (rand.nextDouble() < 0.2 && procesos.size() > 5) {
                    java.util.List<String> claves = new java.util.ArrayList<>();
                    for (String clave : procesos.keySet()) {
                        claves.add(clave);
                    }
                    String eliminar = claves.get(rand.nextInt(claves.size()));
                    procesos.remove(eliminar);
                    alturasAnimadas.remove(eliminar);
                }

                // Actualizar memoria  por proceso
                for (String nombre : procesos.keySet()) {
                    double nuevaMemoria = 50 + rand.nextDouble() * 300;
                    procesos.put(nombre, nuevaMemoria);
                }

                // Nuevos objetivos para RAM, disco y red
                objetivoRam = 2.5 + rand.nextDouble() * 5.0;
                objetivoDisco = 100 + rand.nextDouble() * 300;
                objetivoRedRx = rand.nextDouble() * redMax;
                objetivoRedTx = rand.nextDouble() * redMax;

                // Interpolación suave
                ramUsada += (objetivoRam - ramUsada) * 0.3;
                discoUsado += (objetivoDisco - discoUsado) * 0.3;
                redRx += (objetivoRedRx - redRx) * 0.3;
                redTx += (objetivoRedTx - redTx) * 0.3;

                // Actualizar animación de barras
                for (String nombre : alturasAnimadas.keySet()) {
                    int actual = alturasAnimadas.get(nombre);
                    int nuevo = (int) (usoCPU * 150 + rand.nextInt(30));
                    int interpolado = actual + (nuevo - actual) / 2;
                    alturasAnimadas.put(nombre, interpolado);
                }

                repaint();
            }).start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 14));

            // Fondo cuadriculado
            g2.setColor(new Color(230, 230, 230));
            for (int gx = 0; gx < getWidth(); gx += 40) g2.drawLine(gx, 0, gx, getHeight());
            for (int gy = 0; gy < getHeight(); gy += 40) g2.drawLine(0, gy, getWidth(), gy);

            // Top 8 procesos por memoria
            List<Map.Entry<String, Double>> top = procesos.entrySet().stream()
                    .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                    .limit(8).toList();

            double total = top.stream().mapToDouble(Map.Entry::getValue).sum();

            g2.setColor(Color.DARK_GRAY);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 18));
            g2.drawString("Distribución de Memoria por Proceso", 280, 40);

            // Gráfico circular (donut)
            int x = 100, y = 80, w = 280, h = 280;
            double inicio = 0;
            int i = 0;
            for (Map.Entry<String, Double> entry : top) {
                double porcentaje = entry.getValue() / total;
                double angulo = porcentaje * 360;
                g2.setColor(colores[i % colores.length]);
                g2.fillArc(x, y, w, h, (int) inicio, (int) angulo);
                inicio += angulo;
                i++;
            }
            g2.setColor(Color.WHITE);
            g2.fillOval(x + 60, y + 60, w - 120, h - 120);

            // Leyenda (columna derecha)
            int leyendaX = 420, leyendaY = 100;
            i = 0;
            for (Map.Entry<String, Double> entry : top) {
                g2.setColor(colores[i % colores.length]);
                g2.fillRoundRect(leyendaX, leyendaY, 15, 15, 4, 4);

                g2.setColor(Color.BLACK);
                g2.drawString(entry.getKey() + String.format(" (%.1f MB)", entry.getValue()), leyendaX + 25, leyendaY + 12);

                leyendaY += 25;
                i++;
            }

            // Barras CPU/memoria
            int barraX = 100;
            int baseY = 420;
            int cantidad = top.size();
            int espacio = Math.max(50, (getWidth() - 200) / cantidad);
            int anchoBarra = Math.min(35, espacio - 15);
            i = 0;
            for (Map.Entry<String, Double> entry : top) {
                int altura = alturasAnimadas.get(entry.getKey());
                int xBarra = barraX + i * espacio;

                GradientPaint grad = new GradientPaint(xBarra, baseY - altura, colores[i % colores.length],
                                                       xBarra, baseY, colores[i % colores.length].darker());
                g2.setPaint(grad);
                g2.fillRoundRect(xBarra, baseY - altura, anchoBarra, altura, 6, 6);

                g2.setColor(Color.BLACK);
                g2.drawRoundRect(xBarra, baseY - altura, anchoBarra, altura, 6, 6);
                g2.drawString(String.format("%.1f MB", entry.getValue()), xBarra - 10, baseY - altura - 5);
                i++;
            }

            // Datos del sistema alineados en bloque vertical
            int infoX = 100, infoY = baseY + 50;
            g2.setColor(Color.DARK_GRAY);
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            g2.drawString("Actividad de procesos (CPU)", infoX, infoY);
            g2.drawString(String.format("CPU: %.1f%%", obtenerUsoCPU() * 100), infoX, infoY + 25);
            g2.drawString(String.format("RAM: %.1f GB / %.1f GB", ramUsada, ramTotal), infoX, infoY + 50);
            g2.drawString(String.format("Disco: %.1f GB / %.1f GB", discoUsado, discoTotal), infoX, infoY + 75);
            g2.drawString(String.format("Red: ↓ %.1f Mbps ↑ %.1f Mbps", redRx, redTx), infoX, infoY + 100);
        }
// USO DE CPU REAL 
        private double obtenerUsoCPU() {
            try {
                com.sun.management.OperatingSystemMXBean osBean =
                    (com.sun.management.OperatingSystemMXBean) java.lang.management.ManagementFactory.getOperatingSystemMXBean();
                return osBean.getSystemCpuLoad();
            } catch (Exception e) {
                return 0.5;
            }
        }

        private Map<String, Double> obtenerProcesosDesdeWindows() {
            Map<String, Double> datos = new LinkedHashMap<>();
            datos.put("operaApp.exe", 320.0);
            datos.put("FChrome.exe", 210.0);
            datos.put("TestJava.exe", 180.0);
            datos.put("DummyExplorer.exe", 95.0);
            datos.put("Service.exe", 60.0);
            datos.put("Monitor.exe", 140.0);
            datos.put("Updater.exe", 110.0);
            datos.put("Logger.exe", 75.0);
            datos.put("Backup.exe", 50.0);
            datos.put("Firewall.exe", 45.0);
            return datos;
        }
    };

    JButton botonActualizar = new JButton("Actualizar Datos");
    botonActualizar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    botonActualizar.setBackground(new Color(0x2563EB));
    botonActualizar.setForeground(Color.WHITE);

    // Botón solo fuerza repintado
    botonActualizar.addActionListener(e -> panelGrafica.repaint());

    ventana.setLayout(new BorderLayout());
    ventana.add(panelGrafica, BorderLayout.CENTER);
    ventana.add(botonActualizar, BorderLayout.SOUTH);

    ventana.setVisible(true);

    
    }//GEN-LAST:event_GRAFICActionPerformed

    private void nuevatareaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevatareaActionPerformed
        // TODO add your handling code here:
////////////////////MISHEL///////////////////////////////////////////////////                              
 JDialog dialogo = new JDialog(this, "Crear nueva tarea", true);
    dialogo.setSize(420, 180);
    dialogo.setLayout(null);
    dialogo.setLocationRelativeTo(this);

    JLabel lblTexto = new JLabel("Escriba el nombre del programa, carpeta, documento o recurso de Internet:");
    lblTexto.setBounds(20, 10, 380, 20);
    dialogo.add(lblTexto);

    JLabel lblAbrir = new JLabel("Abrir:");
    lblAbrir.setBounds(20, 40, 50, 20);
    dialogo.add(lblAbrir);

    JTextField campo = new JTextField();
    campo.setBounds(70, 40, 320, 25);
    dialogo.add(campo);

    JCheckBox admin = new JCheckBox("Crear esta tarea con privilegios administrativos");
    admin.setBounds(20, 70, 360, 20);
    dialogo.add(admin);

    JButton ejecutar = new JButton("Aceptar");
    ejecutar.setBounds(300, 100, 90, 30);
    dialogo.add(ejecutar);

    ejecutar.addActionListener(ev -> {
        String comando = campo.getText().trim();
        if (!comando.isEmpty()) {
            try {
                String[] cmd = admin.isSelected()
                    ? new String[]{"cmd", "/c", "start", "cmd", "/k", comando}
                    : new String[]{"cmd", "/c", comando};
                Runtime.getRuntime().exec(cmd);
                dialogo.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialogo, "Error al ejecutar la tarea", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    });

    dialogo.setVisible(true);

    }//GEN-LAST:event_nuevatareaActionPerformed

    private void ConfiguracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConfiguracionActionPerformed
      
       // TODO add your handling code here:       
      /////////// //MISHEL////////////////////////////////
      JDialog config = new JDialog(this, "Configuración", true);
    config.setSize(400, 220);
    config.setLayout(null);
    config.setLocationRelativeTo(this);

    JLabel lblTema = new JLabel("Tema de la aplicación:");
    lblTema.setBounds(20, 20, 150, 20);
    config.add(lblTema);

    String[] opcionesTema = {"Claro", "Oscuro", "Usar configuración del sistema"};
    JComboBox<String> comboTema = new JComboBox<>(opcionesTema);
    comboTema.setBounds(180, 20, 180, 25);
    config.add(comboTema);

    JLabel lblInicio = new JLabel("Página de inicio predeterminada:");
    lblInicio.setBounds(20, 60, 200, 20);
    config.add(lblInicio);

    String[] paginas = {"Procesos", "Rendimiento", "Usuarios", "Detalles"};
    JComboBox<String> comboInicio = new JComboBox<>(paginas);
    comboInicio.setBounds(180, 60, 180, 25);
    config.add(comboInicio);

    JButton guardar = new JButton("Guardar ⚙️");
    guardar.setBounds(260, 120, 100, 30);
    config.add(guardar);

    guardar.addActionListener(e -> {
        String temaSeleccionado = (String) comboTema.getSelectedItem();

        try {
            switch (temaSeleccionado) {
                case "Claro":
                    UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                    break;
                case "Oscuro":
                    UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");
                    break;
                case "Usar configuración del sistema":
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    break;
            }

            // Actualiza todos los componentes abiertos
            SwingUtilities.updateComponentTreeUI(this);
            this.pack(); // opcional: ajusta tamaño si cambia el estilo

            JOptionPane.showMessageDialog(config, "Tema aplicado: " + temaSeleccionado);
            config.dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(config, "Error al aplicar el tema", "Error", JOptionPane.ERROR_MESSAGE);
        }
    });

    config.setVisible(true);
 
    }//GEN-LAST:event_ConfiguracionActionPerformed

    private void HistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HistorialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_HistorialActionPerformed

    private void VistaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VistaActionPerformed
//////////////////////ALISSON///////////////////////////////////////////
    JPopupMenu menuVista = new JPopupMenu();

    JMenuItem agruparTipo = new JMenuItem("Agrupar por tipo");
    JMenuItem vistaCompacta = new JMenuItem("Vista compacta");
    JMenuItem contraerTodo = new JMenuItem("Ocultar procesos en segundo plano");
    JMenuItem restaurarVista = new JMenuItem("Restaurar vista completa");

    JTable tabla = this.jtabla_datos; // 
    DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();

    // Agrupar por tipo (ordenar por columna 0: nombre)
    agruparTipo.addActionListener(e -> {
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(modelo);
        sorter.setSortKeys(List.of(new RowSorter.SortKey(0, SortOrder.ASCENDING)));
        tabla.setRowSorter(sorter);
        JOptionPane.showMessageDialog(null, "Procesos agrupados por tipo.");
    });

    // Vista compacta (oculta columnas excepto "Nombre" y "Memoria")
    vistaCompacta.addActionListener(e -> {
        TableColumnModel columnas = tabla.getColumnModel();
        for (int i = 0; i < columnas.getColumnCount(); i++) {
            String nombre = columnas.getColumn(i).getHeaderValue().toString();
            if (!nombre.equalsIgnoreCase("Nombre") && !nombre.equalsIgnoreCase("Memoria")) {
                columnas.getColumn(i).setMinWidth(0);
                columnas.getColumn(i).setMaxWidth(0);
                columnas.getColumn(i).setWidth(0);
            }
        }
        JOptionPane.showMessageDialog(null, "Vista compacta activada.");
    });

    // Ocultar procesos en segundo plano (filtrar por tipo)
    contraerTodo.addActionListener(e -> {
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(modelo);
        sorter.setRowFilter(new RowFilter<TableModel, Integer>() {
            @Override
            public boolean include(RowFilter.Entry<? extends TableModel, ? extends Integer> entry) {
                String tipo = entry.getStringValue(1); // columna 1: tipo de proceso
                return tipo.equalsIgnoreCase("Aplicación");
            }
        });
        tabla.setRowSorter(sorter);
        JOptionPane.showMessageDialog(null, "Procesos en segundo plano ocultos.");
    });

    // Restaurar vista completa (quitar filtros y mostrar todas las columnas)
    restaurarVista.addActionListener(e -> {
        tabla.setRowSorter(null); // quita filtros

        TableColumnModel columnas = tabla.getColumnModel();
        for (int i = 0; i < columnas.getColumnCount(); i++) {
            columnas.getColumn(i).setMinWidth(15);
            columnas.getColumn(i).setMaxWidth(500);
            columnas.getColumn(i).setWidth(100);
        }
        JOptionPane.showMessageDialog(null, "Vista restaurada.");
    });

    menuVista.add(agruparTipo);
    menuVista.add(vistaCompacta);
    menuVista.add(contraerTodo);
    menuVista.add(restaurarVista);

    Component fuente = (Component) evt.getSource();
    menuVista.show(fuente, 0, fuente.getHeight());

    }//GEN-LAST:event_VistaActionPerformed

    private void arranqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_arranqueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_arranqueActionPerformed

    private void serviciosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serviciosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_serviciosActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new main().setVisible(true);
            }
        });
    }
//
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Configuracion;
    private javax.swing.JButton Eficiencia;
    private javax.swing.JButton GRAFIC;
    private javax.swing.JButton Historial;
    private javax.swing.JTextField No_procesos;
    private javax.swing.JButton Vista;
    private javax.swing.JButton arranque;
    private javax.swing.JButton detalles;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jIniciar_procesos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtabla_datos;
    private javax.swing.JButton jterminar_procesos;
    private javax.swing.JButton nuevatarea;
    private javax.swing.JButton servicios;
    private javax.swing.JButton usuario;
    // End of variables declaration//GEN-END:variables
}
