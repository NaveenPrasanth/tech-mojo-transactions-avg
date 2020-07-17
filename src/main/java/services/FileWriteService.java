package services;

import metadata.Metadata;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriteService {
    static File output_file;
    static BufferedWriter output_fr;

    static File error_file;
    static BufferedWriter error_fr;


    /**
     *
     * @throws IOException
     */
    static private void createWriter() throws IOException {
        output_file = new File(Metadata.outputPath);
        output_fr = new BufferedWriter(new FileWriter(output_file, false));
        error_file = new File(Metadata.errorOutputPath);
        error_fr = new BufferedWriter(new FileWriter(error_file, false));
    }

    /**
     *
     * @param line
     * @throws IOException
     */
    public static void appendToOutputFile(String line) throws IOException {
        if (output_fr == null) createWriter();
        output_fr.write(line);
        output_fr.newLine();
    }

    /**
     *
     * @param line
     * @throws IOException
     */
    public static void appendToErrorFile(String line) throws IOException {
        if (error_file == null) createWriter();
        error_fr.write(line);
        error_fr.newLine();
    }

    /**
     *
     * @throws IOException
     */
    public static void closeWriter() throws IOException {
        if(output_fr != null)
            output_fr.close();
        if(error_fr != null)
            error_fr.close();
    }
}
