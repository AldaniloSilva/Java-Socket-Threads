/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketcomthreads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Alipio
 */
public class FilaSingleton {

    // ThreadGestaoMensagemAuditoria threadRecebeMensagem;
    //ThreadEnviaMensagem threadEnviaMensagem;
    public FilaSingleton() {

    }

    private final static Object OBJETO = new Object();
    public static boolean LigaThread = true;
    public static FilaSingleton _instance;

    public static FilaSingleton Instancia() {
        if (_instance == null) {
            _instance = new FilaSingleton();

        }
        return _instance;
    }

    private final ConcurrentLinkedQueue<String> fila = new ConcurrentLinkedQueue<>();
    private final List<ThreadImpressora> listaImpressora = new  ArrayList<>();
    //private static String mensagem;

    public void EnvioMensagem(String mensagem) {
        synchronized (OBJETO) {
            this.fila.add(mensagem);
        }
    }

    public String ConsumirMensagem() {
        synchronized (OBJETO) {
           // String mensagem = this.fila.remove();
          String mensagem = this.fila.poll();
            return mensagem;
        }
    }

    public int StatusFila() {
        synchronized (OBJETO) {
            return this.fila.size();
        }
    }

    public void AtivarThread() throws InterruptedException {
        LigaThread = true;
         for (int i = 0; i < 5; i++) {
                ThreadImpressora impressora = new ThreadImpressora();
                System.out.println("Iniciando impressora " + (i +1));
                impressora.setName("Impressora " + (i + 1));
                impressora.start();
                listaImpressora.add(impressora);
            }

    }

   public void DesativarThread() {
        try {
            LigaThread = false;
            System.out.println("Finalizando o Programa");
            for(ThreadImpressora item : listaImpressora){
                item.join(1000);
                
                 if (item.isAlive()) {
                    item.interrupt();
                }
                
            }
            
            
        

        } catch (InterruptedException e) {
            System.out.println(e.toString());
        }

    }

}
