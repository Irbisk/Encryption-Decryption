package encryptdecrypt;


import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String command = "enc";
        String line = "";
        String number = "0";
        String alg = "shift";
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-mode")) {
                command = args[i + 1];
            }
            if (args[i].equals("-key")) {
                number = args[i + 1];
            }
            if ("-alg".equals(args[i])) {
                alg = args[i + 1];
            }
            if (args[i].equals("-data")) {
                line = args[i + 1];
            } else if (args[i].equals("-in")) {
                File file = new File(args[i + 1]);
                Scanner scanner = null;
                try {
                    scanner = new Scanner(file);
                    while (scanner.hasNextLine()) {
                        line += scanner.nextLine();
                    }
                    scanner.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        String[] letters = line.split("");
        String result = "";

        int n = Integer.parseInt(number);


        switch (alg) {
            case "unicode":
                Unicode unicode = new Unicode();
                result = unicode.action(letters, n, command);
                break;
            default:
                Shift shift = new Shift();
                result = shift.action(letters, n, command);
        }

        boolean writeToFile = false;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-out")) {
                writeToFile = true;
                File file = new File(args[i + 1]);
                try {
                    FileWriter writer = new FileWriter(file);
                    writer.write(result);
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (!writeToFile) {
            System.out.println(result);
        }
    }
}


interface Algorithm {
    String action(String[] letters, int n, String way);
}

class Shift implements Algorithm {

    @Override
    public String action(String[] letters,int n, String way) {
        String[] newLine = new String[letters.length];
        String result = "";
        if ("enc".equals(way)) {
            for (int i = 0; i < letters.length; i++) {
                if (letters[i].matches("[a-z]")) {
                    int value = letters[i].charAt(0) + n;
                    if (value > 122) {
                        value -= 26;
                    }
                    newLine[i] = Character.toString(value) ;
                    result += newLine[i];
                } else if (letters[i].matches("A-Z")) {
                    int value = letters[i].charAt(0) + n;
                    if (value > 90) {
                        value -= 26;
                    }
                    newLine[i] = Character.toString(value) ;
                    result += newLine[i];
                } else {
                    newLine[i] = letters[i];
                    result += newLine[i];
                }
            }
        } else {
            for (int i = 0; i < letters.length; i++) {
                if (letters[i].matches("[a-z]")) {
                    int value = letters[i].charAt(0) - n;
                    if (value < 97) {
                        value += 26;
                    }
                    newLine[i] = Character.toString(value) ;
                    result += newLine[i];
                } else if (letters[i].matches("A-Z")) {
                    int value = letters[i].charAt(0) - n;
                    if (value < 65) {
                        value += 26;
                    }
                    newLine[i] = Character.toString(value) ;
                    result += newLine[i];
                } else {
                    newLine[i] = letters[i];
                    result += newLine[i];
                }
            }
        }
        return result;
    }
}
class Unicode implements Algorithm {

    @Override
    public String action(String[] letters, int n, String way) {
        String[] newLine = new String[letters.length];
        String result = "";
        if ("enc".equals(way)) {
            for (int i = 0; i < letters.length; i++) {
                int value = letters[i].charAt(0) + n;
                newLine[i] = Character.toString(value);
                result += newLine[i];
            }
        } else {
            for (int i = 0; i < letters.length; i++) {
                int value = letters[i].charAt(0) - n;
                newLine[i] = Character.toString(value);
                result += newLine[i];
            }
        }
        return result;
    }
}
