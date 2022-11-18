import java.nio.file.Path;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        switch (args[0]) {
            case "encode": {
                CaesarCipher caesarCipher = new CaesarCipher(args[1]);
                System.out.println("Введите ключ: ");
                System.out.println("Зашифрованное сообщение: " + caesarCipher.getEncryptMessage(Integer.parseInt(args[2])));
                break;
            }
            case "decode": {
                CaesarCipher caesarCipher = new CaesarCipher(args[1]);
                System.out.println("Введите ключ: ");
                System.out.println("Расшифрованное сообщение: " + caesarCipher.getDecryptMessage(Integer.parseInt(args[2])));
                break;
            }
            case "bruteForce": {
                CaesarCipher caesarCipher = new CaesarCipher(args[1]);
                System.out.println("Ключ: " + caesarCipher.bruteForce());
            }
            case "exit":
                System.exit(200);
            default:
                System.out.println("Неверное значение режима");
                break;
        }
    }
}
