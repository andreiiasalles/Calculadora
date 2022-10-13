import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    private int somatorio = 0;
    private ServerSocket serverSocket;

    private int getSomatorio() {
        return somatorio;
    }

    private void setSomatorio(int somatorio) {
        this.somatorio = somatorio;
    }

    public void calcularSomatorio(int numero){
        int acm = getSomatorio();
        this.setSomatorio(acm+numero);
    }

    private void criarServidorSocket(int porta) throws IOException {
        serverSocket = new ServerSocket(porta);
    }

    private Socket esperaConexao() throws IOException {
        Socket socket = serverSocket.accept();
        return socket;
    }

    //receber numeros do cliente e somar um numero por mensagem, ao receber os numeros, controi somatorio com os recebidos
    private void trataConexao(Socket socket) throws IOException {
        try {
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            int numero = input.readInt();

            while (numero != 0){
                this.calcularSomatorio(numero);
                System.out.println("O somatório é: " + this.getSomatorio());
                numero = input.readInt();
                System.out.println("O valor do numero é: " + numero);

            }

            output.writeInt(this.getSomatorio());
            output.flush();
            System.out.println("O valor total é: " + this.getSomatorio());

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {

            fechaSocket(socket);
        }
    }



    private void fechaSocket(Socket s) throws IOException {
        s.close();
    }

    public static void main(String[] args) {
        try {
            Servidor servidor = new Servidor();
            System.out.println("Aguardando conexão...");
            servidor.criarServidorSocket(14015);
            Socket socket = servidor.esperaConexao();
            System.out.println("Cliente conectado.");
            servidor.trataConexao(socket);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}