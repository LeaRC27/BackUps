/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.sise.backup.windows;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.TreePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author LeaRC
 */
public class HiloCopia implements Runnable{
    
    final static Logger logger = LogManager.getLogger(HiloCopia.class);

    private String urlDestino;
    private JTextField jTextField;
    private JTree explorador;
    private boolean keepPath;

    private int numfiles;
    private int totalFiles;
    private int fails;
    private int totalFails;
    

    public HiloCopia(JTextField jTextField, JTree explorador, boolean keepPath) {
        this.jTextField = jTextField;
        this.explorador = explorador;
        this.keepPath = keepPath;
        numfiles=0;
        fails=0;
        totalFails=0;
        totalFiles=0;
    }
    
    
    
    @Override
    public void run() {
        
        try {
            urlDestino = jTextField.getText();
            //jTextField.setEnabled(false);
            int[] rows = explorador.getSelectionRows();
            if(rows.length>0){
                
                String pathDestino;
                for (int i = 0; i < rows.length; i++) {
                    TreePath treePath = explorador.getPathForRow(rows[i]);

                    Object[] objPath = treePath.getPath();
                    String dir=objPath[1].toString();
                    for(int j=2; j<objPath.length;j++){
                        dir=dir.concat(objPath[j].toString()+"\\");
                    }
                    String dirOrigen= dir;

                    pathDestino = (keepPath)?urlDestino.concat("\\"+dir.substring(3)+"\\"):urlDestino.concat("\\"+objPath[objPath.length-1].toString()+"\\");

                    logger.warn("KEEPPATH -- " + urlDestino.concat("\\"+dir.substring(3)+"\\"));

                    logger.warn("NO KEEP -- "+ urlDestino.concat("\\"+objPath[objPath.length-1].toString()+"\\"));

                    crearCarpetaDestino(pathDestino);

                    RecorrerLista(dirOrigen,pathDestino);

                    logger.info("Cantidad de archivos "+numfiles +"  FAILS : "+fails + " en la carpeta " + objPath[2].toString());
                    totalFiles+=numfiles;
                    totalFails+=fails;

                    numfiles =0;fails =0;
                }
                JOptionPane.showMessageDialog(null, "Cantidad total de archivos "+totalFiles +"  FAILS : "+totalFails);
                logger.warn("Wala!!");
                totalFails=0; totalFiles=0;
            }
            //jTextField.setEnabled(true);
        } catch (Throwable ex) {
            java.util.logging.Logger.getLogger(HiloCopia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void crearCarpetaDestino(String path){
        File directionTemp = new File(path);
	// Make Folder
	if (!directionTemp.exists()) {
            directionTemp.mkdirs();
        }
    }
    
    public void RecorrerLista(String dirOrigen, String destino){
        File[] listFiles = new File(dirOrigen).listFiles();
        if(listFiles != null && listFiles.length > 0){
            for (int i = 0; i < listFiles.length; i++) {
                File file = listFiles[i];
                
                    if(file.isDirectory()){
                        if(keepPath){

                            //Crea el path original, dentro de la carpeta destino seleccionada
                            crearCarpetaDestino(destino.concat(file.getAbsolutePath().substring(dirOrigen.length())));
                            RecorrerLista(listFiles[i].getAbsolutePath(), destino.concat(file.getAbsolutePath().substring(dirOrigen.length())));
                            
                        }else{
                            RecorrerLista(listFiles[i].getAbsolutePath(), destino);
                        }
                    }else{
                    try {
                        if(keepPath){
                            //Copia cada archivo en la ruta seleccionada, pero con su path original
                            Files.copy(file.toPath(), FileSystems.getDefault().getPath(destino.concat(file.getAbsolutePath().substring(dirOrigen.length()))) , StandardCopyOption.COPY_ATTRIBUTES);
                            numfiles++;
                        }else{
                            File file2 = ifFileexists(file,destino);
                            Files.copy(file.toPath(), FileSystems.getDefault().getPath(destino.concat(file2.getName())) , StandardCopyOption.COPY_ATTRIBUTES);
                            numfiles++;
                        }
                    } catch (IOException ex) {
                        java.util.logging.Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                    }catch(StringIndexOutOfBoundsException e){
                        fails++;
                        logger.warn( e.getMessage());
                    }
                    
                    }
                } 
                
            }
    }
    
    public File ifFileexists(File file,String destino){
        File fileTemp = new File(destino.concat(file.getName()));
	int i =0;
        while(fileTemp.exists()){
            int lenghtName;
            if(file.getName().lastIndexOf(".")!=-1){
                lenghtName = file.getName().lastIndexOf(".");
                String extension = file.getName().substring(lenghtName);

                String name = file.getName().subSequence(0,lenghtName).toString().concat(" - Copia "+i).concat(extension);
                fileTemp = new File(destino.concat(name));

            }else{
                String name = file.getName().concat(" - Copia "+i);
                fileTemp = new File(destino.concat(name));
            }
            i++;
        }
	        
        return fileTemp;
    }
    
}
