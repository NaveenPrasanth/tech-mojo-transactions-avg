import org.junit.Test;
import static org.junit.Assert.*;
import services.FileReaderService;
import utils.FileHash;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class SimpleInputTest {
    @Test
    public void testWithDefaultInput() throws IOException, NoSuchAlgorithmException {
        String defaultDataPath = "src/test/java/data/simpleInputTest_input.txt";
        String defaultOutputPath = "src/test/java/outputs/transaction_output.txt";
        FileReaderService.readFileFromPathAndProcess(defaultDataPath, defaultOutputPath);
        //assertEquals(true, true);
        assertEquals(FileHash.getFileHash(defaultOutputPath), FileHash.getFileHash("src/test/java/data/simpleInputTest_expectedOut.txt"));
    }




}
