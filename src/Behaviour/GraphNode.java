package Behaviour;

import java.io.Serializable;

public class GraphNode implements Serializable {
    private double inputValue;
    private double outputValue;

    public GraphNode(){}
    /*
    * Using sigmoid function
    * */
    public void calcOutputValue(){
        outputValue = ( 1 / ( 1 + Math.pow( Math.E, -inputValue)));
    }

    public void resetInput() {
        inputValue = 0;
    }

    public void addToInput(double value) {
        inputValue += value;
    }

    public void setOutputValue(double value){
        outputValue = value;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public double getOutput() {
        calcOutputValue();
        return outputValue;
    }
}
