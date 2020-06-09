/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketcomthreads;

import java.io.IOException;
import java.util.Scanner;


/**
 *
 * @author Alipio
 */
public class SocketComThreads {

       /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        
        GerenciaServer server = new GerenciaServer();
        server.start();
        FilaSingleton.Instancia().AtivarThread();
        Scanner scan = new Scanner(System.in);
        try{
            boolean sair = false;
            do{
                System.out.println("Servidor iniciado.");
                System.out.println("0 - Sair");
                
                int opcao = scan.nextInt();
                if (opcao == 0)
                    sair = true;
                else
                    System.out.println("Opcao inválida");
                
            }while(!sair);
        }finally{
            scan.close();
            server.encerrar(); 
            FilaSingleton.Instancia().DesativarThread();
            
        }
        
       
    }
    
    
    
   

}
