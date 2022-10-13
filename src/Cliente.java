import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;


public class Cliente {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 14015);

            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            Scanner teclado = new Scanner(System.in);
            System.out.print("Digite um número");

            while (teclado.hasNextInt()){
                int numero = teclado.nextInt();
                output.writeInt(numero);
                output.flush();
                if(numero == 0){
                    int somatorio = input.readInt();
                    System.out.println("O valor total é: " + somatorio);
                    break;
                }
            }

            input.close();
            output.close();
            socket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}