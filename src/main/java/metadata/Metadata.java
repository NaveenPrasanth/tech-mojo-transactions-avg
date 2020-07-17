package metadata;
import java.util.HashMap;
public class Metadata {

    private static HashMap transactionTimeMap = new HashMap<String, Long>();
    public static String outputPath = "";
    public static String errorOutputPath = "";
    /**
     *
     * @param transactionId
     * @return
     */
    public static Object getTransactionTime(String transactionId) {
        return transactionTimeMap.get(transactionId);
    }

    /**
     *
     * @param transactionId
     * @param time
     */
    public static void setTransactionTime(String transactionId, long time) {
        transactionTimeMap.put(transactionId, time);
    }

    /**
     *
     * @param transactionId
     */
    public static void removeTransaction(String transactionId){
        transactionTimeMap.remove(transactionId);
    }

}
