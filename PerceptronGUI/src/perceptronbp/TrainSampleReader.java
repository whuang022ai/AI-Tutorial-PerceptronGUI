
package perceptronbp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


/**
 * TrainSampleReader
 * 
 * @author whuang022
 */
public class TrainSampleReader 
{    
    public static ArrayList< TrainSample>getCSVTrainData(String filePath){
        ArrayList< TrainSample> trainDatas = new ArrayList< >();
        try (FileReader fr = new FileReader(filePath)) {
            BufferedReader br = new BufferedReader(fr);
            while (br.ready())
            {
                String line=br.readLine();
                String [] chunk=line.split(",");
                double []feature=new double[chunk.length-1];
                for(int i=0;i<chunk.length-1;i++){
                    double value = Double.parseDouble(chunk[i]);
                    feature[i]=value;
                }
                TrainSample sp=new TrainSample();
                sp.feature=feature;
                sp.desire= Double.parseDouble(chunk[chunk.length-1]);
                trainDatas.add(sp);
            }
       }catch(Exception e){
       }
       return trainDatas;
    }
}
