package hr.fer.oprpp1.hw05.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;


public class Crypto {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Option expected (checksha, encrypt, decrypt)");
            return;
        }
        switch (args[0]){
            case "checksha" -> {
                if (args.length < 2) {
                    System.out.println("File not specified");
                } else if (args.length > 2) {
                    System.out.println("Too many arguments");
                    return;
                }
                System.out.println("Please provide expected sha-256 digest for " + args[1] + ": ");
                System.out.print("> ");
                Scanner sc = new Scanner(System.in);
                String expectedHash = sc.nextLine();
                Path p = Paths.get(args[1]);

                String actualHash = checkSHA256(expectedHash, p);
                if(actualHash == null) {
                    System.out.println("Digesting completed. Digest of " + p + " matches expected digest.");
                } else {
                    System.out.println("Digesting completed. Digest of " + p + " does not match expected digest." +
                            "\nDigest was: " + actualHash);
                }
            }
            case "encrypt" -> {
                if (args.length < 2) {
                    System.out.println("File not specified");
                } else if (args.length > 2) {
                    System.out.println("Too many arguments");
                    return;
                }
            }
            case "decrypt" -> {System.out.println(1);}
        }
    }

    private static String checkSHA256(String expectedHash, Path p) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("A problem occurred during hash calculation");
            System.exit(1);
        }

        try (InputStream is = Files.newInputStream(p)) {
            byte[] buffer = new byte[1024];
            int r;
            while (true) {
                r = is.read(buffer);
                if (r < 1) break;
                md.update(buffer, 0, r);
            }
        } catch (IOException e) {
            System.out.println("A problem occurred while reading the file");
            System.exit(1);
        }
        String actualHash = Util.byteToHex(md.digest());

        if(actualHash.equals(expectedHash)) {
            return null;
        }
        return actualHash;
    }
}

