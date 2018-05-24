
package perceptronbp;

/**
 *  Perceptron 
 * 
 * @author whuang022
 */
public class Perceptron{
    
   private double [] weight;
   private double bias;
   public Perceptron(int dimension){
       weight=new double [dimension];      
       bias=0;  
   }
   public double []getWeight(){
       return weight;
   }
   public double getBias(){
       return bias;
   }
   private  double net(double [] weight,double [] xi,double bias){
        double net=0;
        for(int i=0;i<xi.length;i++){
          net+= xi[i]* weight[i];
        }
        net+=bias;
        return net;
   }
   private  double o (double net){
       return sigmoid(net) ;
   }
   private   double sigmoid(double net){
        return (1/( 1 + Math.pow(Math.E,(-1*net))));
   }
   private double e(double d,double o){
       return d-o;
   }
   private  double sqareE(double e){
       double sqareE=e*e;
       return sqareE;
   }  
   public  double SGDStep(double [] xi,double d,double learningRate){
       double net=net( weight,xi,bias);
       double o=o(net);
       double e=e(d,o);
       double squareE=sqareE(e);
       for(int i=0;i< weight.length;i++){
            double deltaWeight=e*o*(1-o)*xi[i];
            weight[i]+=learningRate*deltaWeight;
       }
       double deltaBias=e*o*(1-o)*1;
       bias+=learningRate*deltaBias;      
       return squareE;
   }
}
