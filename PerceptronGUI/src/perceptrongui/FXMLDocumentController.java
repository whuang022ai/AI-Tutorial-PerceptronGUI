
package perceptrongui;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import perceptronbp.TrainSampleReader;
import perceptronbp.Perceptron;
import perceptronbp.TrainSample;

/**
 * GUI Controller
 * @author whuang022
 */
public class FXMLDocumentController implements Initializable {
    String path="";
    XYChart.Series series = new XYChart.Series();
    int i=0;
    @FXML  LineChart<String, Integer>ErrorChart;
    @FXML public void  start(){     
    series.getData().clear();
    ErrorChart.getData().clear();
    double learningRate=0.3;
    int times=100;
    try {
            final TextInputDialog textInputDialog = new TextInputDialog("");
            textInputDialog.setTitle("Setting");
            textInputDialog.setHeaderText("Learning Rate");
            textInputDialog.setContentText("Please input the learning rate?"); 
            final Optional<String> opt = textInputDialog.showAndWait();
            String rtn;
            try{
                rtn = opt.get();
                learningRate=Double.parseDouble(rtn);
            }catch(NumberFormatException ex){
                rtn = null;
            }
           final TextInputDialog textInputDialog2 = new TextInputDialog("");
            textInputDialog2.setTitle("Setting");
            textInputDialog2.setHeaderText("Learning Times");
            textInputDialog2.setContentText("Please input the learning times ?"); 
            final Optional<String> opt2 = textInputDialog2.showAndWait();
            String rtn2;
           try{
                rtn2 = opt2.get();
                times=Integer.parseInt(rtn2);
            }catch(NumberFormatException ex){
                rtn2 = null;
           }
           ErrorChart.setCreateSymbols(false);
           series.setName("Sum of Square Error");
           ArrayList< TrainSample> trainDatas=TrainSampleReader.getCSVTrainData(path);
           System.out.println(path);
           Perceptron perceptron=new Perceptron(trainDatas.get(0).feature.length);
           ArrayList< XYChart.Data> errors=new ArrayList< >();
           int k=0;
           for(int j=0;j<times;j++){
               double error=0;
               for(int dim=0;dim<trainDatas.size();dim++){
                   Random ran = new Random();
                   int shift=ran.nextInt(trainDatas.size());
                   dim=(dim+shift)%trainDatas.size();
                   error+=Math.abs(perceptron.SGDStep(trainDatas.get(dim).feature, trainDatas.get(dim).desire, learningRate));
               } 
               errors.add( new XYChart.Data(k+"",  error));
               k++;
           }
            series.getData().addAll(errors);
            ErrorChart.getData().add(series);
            System.out.println("The weights:");
            System.out.println(Arrays.toString(perceptron.getWeight()));
            System.out.println("\nThe bias:");
            System.out.print(perceptron.getBias()+" ");
       } catch (Exception ex) {
           Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    @FXML public void  openData(){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File For Training");
        File file = chooser.showOpenDialog(new Stage());
        path=file.getAbsolutePath();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
}
