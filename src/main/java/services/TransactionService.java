package services;

import metadata.Metadata;
import metadata.StringConstants.TRANSACTION_TYPE;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TransactionService {

    public static class TransactionConsumer {
        final static int TRANSACTION_ID = 0;
        final static int DATE = 1;
        final static int TIME = 2;
        final static int TYPE = 3;
        final static int LINE_NUMBER = 4;

        /**
         *
         * @param line
         */
        public static void processTransaction(String[] line) {
            try {
                parseTransaction(line);
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }
        }

        /**
         *
         * @param line
         * @throws ParseException
         */
        private static void parseTransaction(String[] line) throws ParseException, IOException {
            long lineNumber = Long.parseLong(line[LINE_NUMBER]);

            //sample input had weird delimiters in date, so taking from the file
            String dateDelimiter = findDateDelimiter(line[DATE]);

            // convert date to Date object to get time in milliseconds
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy"+dateDelimiter+"MM"+dateDelimiter+"dd HH:mmaa");
            String date_data = line[DATE]+" "+line[TIME];
            Date properDate = dateFormat.parse(date_data);
            TRANSACTION_TYPE type = line[TYPE].toLowerCase().equals("start") ? TRANSACTION_TYPE.START: TRANSACTION_TYPE.END;

            applyLineToMetaData(line[TRANSACTION_ID], properDate.getTime(), type, lineNumber);
        }

        /**
         * Core logic to calculate avg time based on previously seen time and current time
         * @param transId
         * @param time
         * @param type
         * @param lineNumber
         */
        private static void applyLineToMetaData(String transId, long time, TRANSACTION_TYPE type, long lineNumber) throws IOException {
            if (type == TRANSACTION_TYPE.START && Metadata.getTransactionTime(transId) == null){
                Metadata.setTransactionTime(transId, time);
            }
            else if (type == TRANSACTION_TYPE.END && Metadata.getTransactionTime(transId) != null){
                long startTime = (long) Metadata.getTransactionTime(transId);
                long avgTimeInSeconds = (time - startTime)/1000;

                //write to output file
                String output = transId+" : "+ avgTimeInSeconds+ " seconds";
                System.out.println(output);
                FileWriteService.appendToOutputFile(output);
                // cleanup data
                Metadata.removeTransaction(transId);
            }
            else{
                String errorText = "Anomaly at transaction: "+ transId+ " at line number: " + lineNumber;
                FileWriteService.appendToErrorFile(errorText);
                System.out.println(errorText);
            }
        }

        /**
         *
         * @param date
         * @return
         */
        static String findDateDelimiter(String date){
            final Pattern p = Pattern.compile("[0-9]{4}(.)[0-9]{2}.[0-9]{2}");
            Matcher m = p.matcher(date);
            if(m.find()){
                return m.group(1);
            }
            else {
                return "–";
            }

    }
    }
}
