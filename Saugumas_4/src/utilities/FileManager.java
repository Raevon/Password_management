package utilities;

import securities.AESEncryption;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileManager {
    private static String directory;
    static
    {
        directory = System.getProperty("user.dir") + "\\data\\";
        File file = new File(directory);
        //file.mkdir();
        System.out.println(Path.of(directory));
        if(!file.exists()){
            file.mkdir();

            try {
                AESEncryption.encryptFile("", getAesPath("users"));
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }
        }
    }

    public static String[] getLinesByName(String name) throws FileNotFoundException {
        String path = getCsvPath(name);
        return getLinesByPath(path);
    }

    public static String[] getLinesByPath(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner myReader = new Scanner(file);
        List<String> lines = new ArrayList<>();
        while (myReader.hasNextLine()) {
            lines.add(myReader.nextLine());
        }
        myReader.close();

        String[] lineArray = new String[lines.size()];
        lines.toArray(lineArray);
        return lineArray;
    }

    public static void createFile(String name) throws IOException {
        File file = new File(getCsvPath(name));
        file.createNewFile();
    }

    public static void appendFile(String name, String data) throws IOException {
        Files.write(Path.of(getCsvPath(name)), data.getBytes(), StandardOpenOption.APPEND);
    }

    public static void writeFile(String name, String data) throws IOException {
        File file = new File(getCsvPath(name));
        file.delete();
        Files.write(Path.of(getCsvPath(name)), data.getBytes(), StandardOpenOption.CREATE);
    }

    /**
     * Prefixes directory and postfixes .csv to the name and returns it
      * @param name
     * @return
     */
    public static String getCsvPath(String name){
        return directory + name + ".csv";
    }

    /**
     * Prefixes directory and postfixes .csv.Aes to the name and returns it
     * @param name
     * @return
     */
    public static String getAesPath(String name){
        return directory + name + ".aes";
    }

}
