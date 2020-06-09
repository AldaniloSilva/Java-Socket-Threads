/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketcomthreads;

import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alipio
 */
public class ThreadImpressora extends Thread {
    
    @Override
    public void run(){
        
        while(FilaSingleton.LigaThread){
            
            
            try {
                if(FilaSingleton.Instancia().StatusFila() != 0){
                    String mensagem = FilaSingleton.Instancia().ConsumirMensagem();
                    if (mensagem != null)
                        System.out.printf("%s - Impressão pela %s = %s\n", Instant.now().toString(), getName(),mensagem);
                }
                
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadImpressora.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        while(FilaSingleton.Instancia().StatusFila() != 0)
        {
            System.out.printf("%s - Impressão pela %s = %s\n", Instant.now().toString(), getName(), FilaSingleton.Instancia().ConsumirMensagem());
        }
        
    }

    
}
