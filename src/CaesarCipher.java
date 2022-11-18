import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class CaesarCipher {
    private Path filePath;

    private String text;

    private int indexEncrypt = 0;

    private final String symbols = ",.:!?\"-";

    public CaesarCipher(Path path) {
        filePath = path;
        try {
            text = Files.readString(filePath);
        } catch (IOException ex) {
            System.out.println("Ошибка в прочтении файла");
            System.exit(400);
        }
    }

    public CaesarCipher(String text) {
        this.text = text;
    }

    public String getEncryptMessage(int key) {
        key = Math.abs(key);
        byte[] tempArray = text.getBytes(StandardCharsets.UTF_8);
        for (int i = 0; i < text.length(); i++) {
            tempArray[i] = (byte) ((tempArray[i] + key) % 128);
        }
        try {
            Files.write(getNewPathEncrypt(), tempArray);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new String(tempArray);
    }

    public String getDecryptMessage(int key) {
        key = Math.abs(key);
        byte[] tempArray = text.getBytes(StandardCharsets.UTF_8);
        for (int i = 0; i < text.length(); i++) {
                tempArray[i] = (byte) ((tempArray[i] - key + 128 * (key / 128 + 1)) % 128);
        }
        try {
            Files.write(getNewPathDecrypt(), tempArray);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new String(tempArray);
    }

    public int bruteForce() {
        int key;
        for (int i = 0; i < 128; i++) {
            byte[] tempArray = text.getBytes(StandardCharsets.UTF_8);
            for (int j = 0; j < tempArray.length; j++) {
                tempArray[j] = (byte) ((tempArray[j] - i + 128) % 128);
            }
            String tempText = new String(tempArray);
            for (int j = 0; j < tempText.length() - 1; j++) {
                boolean contains = symbols.contains(String.valueOf(tempText.charAt(j)));
                if (!Character.isLetter(tempText.charAt(j))) {
                    if (!(tempText.charAt(j) == ' ')) {
                        if (!contains) {
                            break;
                        }
                    }
                }
                if (contains) {
                    if (!(tempText.charAt(j + 1) == ' '))
                        break;
                }
                if (j == tempText.length() - 2 && symbols.contains(String.valueOf(tempText.charAt(tempText.length() - 1)))) {
                    key = i;
                    System.out.println("Расшифрованное сообщение: " + tempText);
                    try {
                        Files.write(getNewPathBruteForce(key), tempArray);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return key;
                }
            }
        }
        throw new RuntimeException("Сообщение зашифровано неверно");
    }

    private Path getNewPathEncrypt() {
        indexEncrypt++;
        return Path.of(getFilePath().toString()
                .replace(".txt", indexEncrypt + "(encode)" + ".txt"));
    }

    private Path getNewPathDecrypt() {
        return Path.of(getFilePath().toString()
                .replace("(encode)", "")
                .replace(".txt", "(decode).txt"));
    }

    private Path getNewPathBruteForce(int key) {
        return Path.of(getFilePath().toString()
                .replace("(encode)", "")
                .replace(".txt", "(decode key-" + key + ").txt"));
    }

    public Path getFilePath() {
        return filePath;
    }

}
