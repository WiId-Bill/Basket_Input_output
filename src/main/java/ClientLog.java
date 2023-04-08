import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ClientLog {

   private List<String[]> log = new ArrayList<>();
        public void log(int productNum, int amount){
        String[] arrOfPairs = new String[2];
        arrOfPairs[0] = Integer.toString(productNum);
        arrOfPairs[1] = Integer.toString(amount);
        log.add(arrOfPairs);
    }

    public void exportAsCSV(File txtFile){
        if(!txtFile.exists()){

        log.add(0,new String[] {"productNum, amount"});
        }
        try (CSVWriter writer = new CSVWriter(new FileWriter(txtFile, true), ',', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, "\n")) {
                writer.writeAll(log);
            } catch (IOException e) {
                e.printStackTrace();
        }
    }
}
