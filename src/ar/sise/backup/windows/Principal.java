/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.sise.backup.windows;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 *
 * @author LeaRC
 */
public class Principal extends javax.swing.JFrame {

    final static Logger logger = LogManager.getLogger(Principal.class);
    
    /**
     * Creates new form Principal
     */
    private boolean keepPath=false;
    
    private DefaultMutableTreeNode Raiz;
    
    private HiloCopia hiloCopia;
    private Thread thiloCopia;
    
    public Principal() {
        initComponents();
        
        jButton2.setVisible(false);
        
        
        Raiz=new DefaultMutableTreeNode("Raiz");
        explorador=new JTree(Raiz);
        explorador.setExpandsSelectedPaths(true);
        DefaultTreeCellRenderer render=(DefaultTreeCellRenderer)explorador.getCellRenderer();
        render.setLeafIcon(new ImageIcon(this.getClass().getResource("../Imagenes/raiz.png")));
        render.setOpenIcon(new ImageIcon(this.getClass().getResource("../Imagenes/carpetaopen.png")));
        render.setClosedIcon(new ImageIcon(this.getClass().getResource("../Imagenes/raiz.png")));
        explorador.addTreeExpansionListener(new TreeExpansionListener(){
            @Override
            public void treeExpanded(TreeExpansionEvent e) {
                try{
                    DefaultMutableTreeNode sel=(DefaultMutableTreeNode)e.getPath().getLastPathComponent();
                    for(int i=0;i<sel.getChildCount();i++){
                        DefaultMutableTreeNode nieto=(DefaultMutableTreeNode)sel.getChildAt(i);
                        agregarHijos(nieto);
                    }
                }catch(NullPointerException npe){
                    logger.error(npe.getMessage());
                }
            }

            @Override
            public void treeCollapsed(TreeExpansionEvent event) {

            }
        });        
        jScrollPane2.setViewportView(explorador);
        pack();
       
    }
        
    public void agregarHijos(DefaultMutableTreeNode padre) {
        try{
            if(padre!=Raiz){
                File fpadre=obtenerRuta(padre);
                if(fpadre.isDirectory()){
                    if(fpadre.list()!= null && fpadre.list().length>0){
                        for(int i=0;i<fpadre.list().length;i++){
                            DefaultMutableTreeNode hijo=new DefaultMutableTreeNode(fpadre.list()[i]);
                            padre.add(hijo);
                        }
                    }
                }
            }
        }catch(NullPointerException ex){
            logger.error(ex.getMessage());
        }
    }
 
    public File obtenerRuta(DefaultMutableTreeNode p){
       String ruta="";
       for(int i=0;i<p.getPath().length-1;i++){
           ruta=ruta+p.getPath()[i+1]+"\\";
       }
       File f=new File(ruta);
       return f;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        explorador = new javax.swing.JTree();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("BackUps Image&Videos");
        setPreferredSize(new java.awt.Dimension(600, 550));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jScrollPane2.setViewportView(explorador);

        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Destino");

        jCheckBox1.setText("Conservar Sub-Directorios");
        jCheckBox1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBox1StateChanged(evt);
            }
        });

        jButton2.setText("Stop");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Examinar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextField1.setText("jTextField1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(255, 255, 255)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)))
                .addGap(159, 228, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton3)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
        if(thiloCopia == null){
            hiloCopia = new HiloCopia(jTextField1, explorador, keepPath);
            thiloCopia = new Thread(hiloCopia);
            thiloCopia.start();
                    
        }else if(thiloCopia.isAlive()){
            logger.info("START");
            thiloCopia.resume();
        }
        //jButton1.setVisible(false);
        //jButton2.setVisible(true);
        hiloState state = new hiloState();
        Thread newtThread = new Thread(state);
        newtThread.start();
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jCheckBox1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBox1StateChanged
        // TODO add your handling code here:
        keepPath = jCheckBox1.isSelected();
    }//GEN-LAST:event_jCheckBox1StateChanged

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        thiloCopia.suspend();
        jButton1.setVisible(true);
        jButton2.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        List<String> listRoots = new ArrayList();
        for(int i=0;i<File.listRoots().length;i++){
            DefaultMutableTreeNode root=new DefaultMutableTreeNode(File.listRoots()[i]);
            Raiz.add(root);
            
            listRoots.add(root.getUserObject().toString());
        }
        jTextField1.setText("D:\\Prueba");
                
    }//GEN-LAST:event_formWindowOpened

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
        JFileChooser fileChooser = new JFileChooser();
            
                //Directorio raiz al abrir el programa
                fileChooser.setCurrentDirectory(new File(jTextField1.getText()));
                //Permite seleccionar archivos y directorios
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                //Permite selleccionar varios archivos a la vez
                fileChooser.setMultiSelectionEnabled(false);
                //Permite ver archivos ocultos
                fileChooser.setFileHidingEnabled(true);


                fileChooser.setFileSystemView(FileSystemView.getFileSystemView());

                try {
                    /*            //Filtro de archivos de imagen
                    fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Images", "bmp","gif","jpeg","jpg","png","psd","ai","cdr","dwg","svg","raw","nef"));
                    //Filtro de archivos de video
                    fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Videos", "asf","lsf","asx","bik","smk","div","divx","dvd","wob","ivf",
                    "m1v","mp2v","mp4","mpa","mpe","mpeg","mpg","mpv2","mov","qt","qtt","rpm","wm","wmv","avi"));
                    fileChooser.setAcceptAllFileFilterUsed(true);
                    */

                    int status = fileChooser.showOpenDialog(jScrollPane2);
                    if(status == JFileChooser.APPROVE_OPTION){
                        jTextField1.setText(fileChooser.getSelectedFile().getCanonicalPath());
                    }
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
    }//GEN-LAST:event_jButton3ActionPerformed
       
    class hiloState implements Runnable{

        public hiloState() {
        }
                
        @Override
        public void run() {
            jButton1.setVisible(false);
            jButton2.setVisible(true);
            while(thiloCopia.isAlive()){
                jTextField1.setEnabled(false);
            }
            if(!thiloCopia.isAlive()) logger.info(("El hiloCopia ha terminado"));jTextField1.setEnabled(true);
            jButton1.setVisible(true);
            jButton2.setVisible(false);
            thiloCopia=null;
        }
    
    }
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
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree explorador;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
