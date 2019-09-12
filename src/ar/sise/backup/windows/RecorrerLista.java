/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.sise.backup.windows;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author LeaRC
 */
public abstract class RecorrerLista {
    
    private static int numFiles;
    private static int numDirs;

    public RecorrerLista() {
    }
    
    public static void RecorrerLista(File[] listFiles) throws IOException {
        if(listFiles != null && listFiles.length > 0){
            for (int i = 0; i < listFiles.length; i++) {
                File file = listFiles[i];
                if(file.isDirectory()){
                    numDirs++;
                    System.out.println("Directorio :" + file.getPath());
                    System.out.println(file.getPath().substring(3));
                    RecorrerLista(listFiles[i].listFiles());
                }else{
                    numFiles++;
                    System.out.println("Archivo :" + file.getName());
                }
                
            }
        
        
        }
    }
        
    public static int getNumFiles() {
        return numFiles;
    }

    public static int getNumDirs() {
        return numDirs;
    }
   
}
