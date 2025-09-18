/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */


//////////KATHIA///////////////////////////////////////////
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
    
/////////////////////////////MISHEL/////////////////////////////////////////////////
 
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
///////////ALISSON//////////
    JFrame ventana = new JFrame("Dashboard de Procesos");
    ventana.setSize(900, 600);
    ventana.setLocationRelativeTo(null);
    ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    JPanel panelGrafica = new JPanel() {
        Map<String, Double> procesos = obtenerProcesosDesdeWindows();
        Map<String, Integer> alturasAnimadas = new LinkedHashMap<>();
        Random rand = new Random();

        Color[] colores = {
            new Color(0x3B82F6), new Color(0x10B981), new Color(0xF59E0B),
            new Color(0x8B5CF6), new Color(0xEF4444), new Color(0x06B6D4),
            new Color(0x22C55E), new Color(0x6366F1), new Color(0xEAB308),
            new Color(0xEC4899)
        };

        {
            if (procesos.isEmpty()) {
                procesos.put("operaApp.exe", 320.0);
                procesos.put("FChrome.exe", 210.0);
                procesos.put("TestJava.exe", 180.0);
                procesos.put("DummyExplorer.exe", 95.0);
                procesos.put("Service.exe", 60.0);
            }

            for (String nombre : procesos.keySet()) {
                alturasAnimadas.put(nombre, rand.nextInt(100));
            }

            new Timer(500, e -> {
                double usoCPU = obtenerUsoCPU();
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

            List<Map.Entry<String, Double>> top = procesos.entrySet().stream()
                    .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                    .limit(10).toList();

            double total = top.stream().mapToDouble(Map.Entry::getValue).sum();

            // Título
            g2.setColor(Color.DARK_GRAY);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 18));
            g2.drawString("Distribución de Memoria por Proceso", 300, 40);

            // Donut chart
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

            // Donut centro blanco
            g2.setColor(Color.WHITE);
            g2.fillOval(x + 60, y + 60, w - 120, h - 120);

            // Leyenda
            int leyendaY = 100;
            i = 0;
            for (Map.Entry<String, Double> entry : top) {
                g2.setColor(colores[i % colores.length]);
                g2.fillRoundRect(420, leyendaY, 15, 15, 4, 4);
                g2.setColor(Color.BLACK);
                g2.drawString(entry.getKey() + String.format(" (%.1f MB)", entry.getValue()), 445, leyendaY + 12);
                leyendaY += 22;
                i++;
            }

            // Barras animadas con gradiente
            int barraX = 100;
            int baseY = 420;
            int anchoBarra = 24;
            int espacio = 35;
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

            // CPU info
            g2.setColor(Color.DARK_GRAY);
            g2.drawString("Actividad de procesos (CPU)", 100, baseY + 30);
            g2.drawString(String.format("CPU: %.1f%%", obtenerUsoCPU() * 100), 100, baseY + 50);
        }

        private double obtenerUsoCPU() {
            try {
                com.sun.management.OperatingSystemMXBean osBean =
                    (com.sun.management.OperatingSystemMXBean) java.lang.management.ManagementFactory.getOperatingSystemMXBean();
                return osBean.getSystemCpuLoad();
            } catch (Exception e) {
                return 0.5;
            }
        }
    };

    ventana.add(panelGrafica);
    ventana.setVisible(true);
    
    }//GEN-LAST:event_GRAFICActionPerformed

    private void nuevatareaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevatareaActionPerformed
        // TODO add your handling code here:
////////////////////MISHEL///////////////////////////////////////////////////                              
 

    }//GEN-LAST:event_nuevatareaActionPerformed

    private void ConfiguracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConfiguracionActionPerformed
      
       // TODO add your handling code here:       
      /////////// //MISHEL////////////////////////////////
 
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
