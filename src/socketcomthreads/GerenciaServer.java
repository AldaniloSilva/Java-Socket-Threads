/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketcomthreads;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alipio
 */
public class GerenciaServer extends Thread {

    private static ServerSocket server;
    private static final int PORT = 9876;
    private boolean servidorLigado;
    private List<GerenciaClient> conexoesClientes = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void run() {
        try {
            servidorLigado = true;
            server = new ServerSocket(PORT);

            while (servidorLigado) {
                System.out.println("Aguardando Conexão...");
                Socket socket = server.accept();
                iniciaSocket(socket);
                Thread.sleep(10);

            }
            if (!server.isClosed()) {
                server.close();
            }

        } catch (IOException | InterruptedException ex) {
            if (ex.getMessage().equals("socket closed")) {
                System.out.println("Conexão do servidor fechada!");

            } else {
                Logger.getLogger(GerenciaServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void encerrar() throws IOException {
        //Fecha todas as sockets abertas
        finalizaSocket();
        //Desliga o loop para parar de ouvir novos clientes
        servidorLigado = false;
        //Desliga o servidor
        server.close();

    }

    public void iniciaSocket(Socket socket) {
        GerenciaClient client = new GerenciaClient();
        client.setClientSocket(socket);
        conexoesClientes.add(client);
        client.start();
    }
    
    public void finalizaSocket() throws IOException {
        try{
        for(GerenciaClient socket  : conexoesClientes){
           socket.fechaSocket();
        }}catch (IOException ex){
            Logger.getLogger(GerenciaServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
