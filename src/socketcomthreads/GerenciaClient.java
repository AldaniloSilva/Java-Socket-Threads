/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketcomthreads;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alipio
 */
public class GerenciaClient extends Thread{
    
    private Socket socketConectado;
    private String mensagem;
    
    @Override
    public void run(){
        try {
            InputStream stream = socketConectado.getInputStream();
            try{
                int bytesLidos = 0;
                
                do {
                    byte[] dados = new byte[1024];
                    bytesLidos = stream.read(dados);
                    if(bytesLidos > 0){
                        mensagem = "Messagem Recebida: " + new String(dados);
                        System.out.println(mensagem);
                        FilaSingleton.Instancia().EnvioMensagem(mensagem);
                    }
                        //System.out.println("Message Received: " + new String(dados));
                    
                    
                } while (bytesLidos != -1);
            }finally{
                if (stream != null)
                    stream.close();
                if (socketConectado.isConnected())
                    socketConectado.close();
            }
        } catch (IOException ex) {
            if(ex.getMessage().equals("Socket closed")){
                System.out.println("Conexão cliente fechada!");
            }else{           
            Logger.getLogger(GerenciaClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        }

    
    }
    
    public void setClientSocket(Socket client){
        this.socketConectado = client;
    }
    
    public void fechaSocket() throws IOException{
        if(socketConectado.isConnected())
            socketConectado.close();
    }
}
