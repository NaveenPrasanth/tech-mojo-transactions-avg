package services;

import metadata.CustomExceptions;
import metadata.Metadata;
import metadata.StringConstants;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Consumer;

import static metadata.CustomExceptions.InvalidInputException;

public class FileReaderService {
    /**
     *
     * @param inputPath
     * @throws IOException
     */
    public static void readFileFromPathAndProcess(String inputPath, String outputPath) throws IOException  {
        Metadata.outputPath = outputPath;
        // derive error file name from output file
        String error_output;
        String[] outSplit = outputPath.split("\\.");
        if(outSplit.length>1) {
            outSplit[outSplit.length-2] = outSplit[outSplit.length-2]+"_errors";
            error_output = String.join(".", outSplit);
        }
        else
            error_output = outputPath+"_errors";
        Metadata.errorOutputPath = error_output;

        FileInputStream inputStream = null;
        Scanner sc = null;
        try {
            // boiler plate code to read a file line by line
            inputStream = new FileInputStream(inputPath);
            sc = new Scanner(inputStream, "UTF-8");
            long lineNumber = 1;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                try{
                    // check if line fits our template
                    String [] valid_line = checkIfValid(line, lineNumber);

                    // add line number to the valid line string array because java consumer takes only one input
                    valid_line = Arrays.copyOf(valid_line, valid_line.length +1);
                    valid_line[valid_line.length -1] = String.valueOf(lineNumber);

                    // forward the valid line to consumer for processing
                    Consumer<String[]> transactionsConsumer = TransactionService.TransactionConsumer::processTransaction;
                    transactionsConsumer.accept(valid_line);
                }catch (CustomExceptions.InvalidInputException e){
                    System.out.println(e.getMessage());
                }
                lineNumber ++;
            }
            // note that Scanner suppresses exceptions
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            FileWriteService.closeWriter();
            if (inputStream != null) {
                inputStream.close();
            }
            if (sc != null) {
                sc.close();
            }
        }
    }

    /**
     * Checks if the given line fits our template
     * @param line
     * @param lineNumber
     * @return
     * @throws InvalidInputException
     */
    static String[] checkIfValid(String line, long lineNumber) throws InvalidInputException, IOException {
        line = applyTransactionRegex(line);
        String[] splitLine = line.split(StringConstants.SPLIT_DELIMITER);
        if (splitLine.length != 4){
            String errorText = "Template problems found at Line: "+ lineNumber;
            FileWriteService.appendToErrorFile(errorText);
            throw new CustomExceptions.InvalidInputException(errorText);
        }
        return splitLine;
    }

    /**
     * Removes unncessary spaces and unseen ASCII characters like \t and \n from the line
     * @param line
     * @return
     */
    static String applyTransactionRegex(String line){
        return line.replaceAll(StringConstants.TRANSACTIONS_REGEX, StringConstants.EMPTY_STRING);
    }

}
