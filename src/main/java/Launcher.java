import services.FileReaderService;

import java.io.IOException;

public class Launcher {
    public static void main(String args[]) throws IOException {
        String inputPath = (args.length > 0)? args[0]: "data/transaction_logs.txt";
        String outputPath = (args.length > 1)? args[1]: "data/transaction_output.txt";
        FileReaderService.readFileFromPathAndProcess(inputPath, outputPath );
    }
}
