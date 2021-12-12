package hr.fer.oprpp1.hw05.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;


/**
 * Command-line application used for checking SHA-256 digests and
 * encrypting or decrypting files using the AES algorithm.
 */
public class Crypto {
    /**
     * Main function of this command-line application
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Option expected (checksha, encrypt, decrypt)");
            return;
        }
        switch (args[0]) {
            case "checksha" -> {
                if (args.length < 2) {
                    System.out.println("File not specified");
                    return;
                } else if (args.length > 2) {
                    System.out.println("Too many arguments");
                    return;
                }
                System.out.println("Please provide expected sha-256 digest for " + args[1] + ": ");
                System.out.print("> ");
                Scanner sc = new Scanner(System.in);
                String expectedHash = sc.nextLine();
                Path p = Paths.get(args[1]);

                String actualHash = calculateSHA256(p);
                if (actualHash.equalsIgnoreCase(expectedHash)) {
                    System.out.println("Digesting completed. Digest of " + p + " matches expected digest.");
                } else {
                    System.out.println("Digesting completed. Digest of " + p + " does not match expected digest." +
                            "\nDigest was: " + actualHash);
                }
            }
            case "encrypt", "decrypt" -> {
                if (args.length < 3) {
                    System.out.println("Too few arguments");
                    return;
                } else if (args.length > 3) {
                    System.out.println("Too many arguments");
                    return;
                }
                Scanner sc = new Scanner(System.in);
                System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
                System.out.print("> ");
                String psswd = sc.nextLine();

                System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
                System.out.print("> ");
                String initVector = sc.nextLine();
                byte[] psswdd = Util.hextobyte(psswd);
                SecretKeySpec keySpec = new SecretKeySpec(psswdd, "AES");
                AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(initVector));
                Cipher cipher = null;

                try {
                    cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                } catch (NoSuchAlgorithmException | NoSuchPaddingException ignore) {
                }
                try {
                    cipher.init(args[0].equals("encrypt") ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
                } catch (InvalidAlgorithmParameterException | InvalidKeyException ignore) {
                }

                Path in = Paths.get(args[1]);
                Path out = Paths.get(args[2]);
                processCipher(cipher, in, out);
                System.out.println((args[0].equals("encrypt") ? "Encryption" : "Decryption") + " completed. " +
                        "Generated file " + args[2] + " based on file " + args[1] + ".");
            }
        }
    }

    /**
     * Helper function which handles encryption and decryption of files using the AES algorithm.
     *
     * @param cipher {@code Cipher} object to be used for processing the input file
     * @param in     {@code Path} representation of input file
     * @param out    {@code Path} representation of output file
     */
    private static void processCipher(Cipher cipher, Path in, Path out) {
        try (InputStream is = Files.newInputStream(in);
             OutputStream os = Files.newOutputStream(out)) {
            byte[] buffer = new byte[4096];
            int r;
            while ((r = is.read(buffer)) > 1) {
                os.write(cipher.update(buffer, 0, r));
            }
            try {
                os.write(cipher.doFinal());
            } catch (IllegalBlockSizeException | BadPaddingException ignore) {
                System.out.println("A problem occurred during encrypting/decrypting.");
                System.exit(1);
            }
        } catch (IOException e) {
            System.out.println("A problem occurred while reading the input file");
            System.exit(1);
        }
    }


    /**
     * Helper function used for calculating SHA-256 digest of the input file.
     *
     * @param p {@code Path} representation of input file
     * @return hex representation of calculated SHA-256 digest
     */
    private static String calculateSHA256(Path p) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("A problem occurred during hash calculation");
            System.exit(1);
        }

        try (InputStream is = Files.newInputStream(p)) {
            byte[] buffer = new byte[4096];
            int r;
            while (true) {
                r = is.read(buffer);
                System.out.println(r);
                if (r < 1) break;
                md.update(buffer, 0, r);
            }
        } catch (IOException e) {
            System.out.println("A problem occurred while reading the file");
            System.exit(1);
        }
        return Util.bytetohex(md.digest());
    }


}

