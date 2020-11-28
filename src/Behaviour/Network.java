package Behaviour;





import com.sun.corba.se.impl.orbutil.graph.Graph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import java.io.*;
import java.util.Set;


public class Network {

    public static final int OUTPUT_SIZE = 4;
    public static final int LAYER_CARDS_OWN_SIZE = 34;
    public static final int LAYER_CARDS_OTHER_SIZE = 85;
    public static final int LAYER_ACTION_SIZE = 126;
    public static final int LAYER_DATA_SIZE = 24;
    public static final int LAYER_1_SIZE = 170;
    public static final int LAYER_2_SIZE = 170;

    private Layer cardsOwnLayer;
    private Layer cardsOtherLayer;
    private Layer actionLayer;
    private Layer dataLayer;
    private Layer outPutLayer;
    private Layer hiddenLayer_1;
    private Layer hiddenLayer_2;
    private Layer bias_1;
    private Layer bias_2;
    private Layer bias_output;


    private SimpleDirectedWeightedGraph<GraphNode, MyWeightedEdge> graph;

    public Network(){
        bias_1 = new Layer(1);
        bias_1.addToInput(0,Double.MAX_VALUE);
        bias_2 = new Layer(1);
        bias_2.addToInput(0,Double.MAX_VALUE);
        bias_output = new Layer(1);
        bias_output.addToInput(0,Double.MAX_VALUE);
        cardsOtherLayer = new Layer(LAYER_CARDS_OTHER_SIZE);
        cardsOwnLayer = new Layer(LAYER_CARDS_OWN_SIZE);
        actionLayer = new Layer(LAYER_ACTION_SIZE);
        dataLayer = new Layer(LAYER_DATA_SIZE);
        outPutLayer = new Layer(OUTPUT_SIZE);
        hiddenLayer_1 = new Layer(LAYER_1_SIZE);
        hiddenLayer_2 = new Layer(LAYER_2_SIZE);
        graph = new SimpleDirectedWeightedGraph<>(MyWeightedEdge.class);

        addVertexs(bias_1);
        addVertexs(bias_2);
        addVertexs(bias_output);
        addVertexs(cardsOtherLayer);
        addVertexs(cardsOwnLayer);
        addVertexs(actionLayer);
        addVertexs(dataLayer);
        addVertexs(hiddenLayer_1);
        addVertexs(hiddenLayer_2);
        addVertexs(outPutLayer);
        connectLayers(cardsOwnLayer,hiddenLayer_1);
        connectLayers(cardsOtherLayer,hiddenLayer_1);
        connectLayers(dataLayer,hiddenLayer_1);
        connectLayers(actionLayer,hiddenLayer_1);
        connectLayers(hiddenLayer_1,hiddenLayer_2);
        connectLayers(hiddenLayer_2,outPutLayer);
        connectLayers(bias_1,hiddenLayer_1);
        connectLayers(bias_2,hiddenLayer_2);
        connectLayers(bias_output,outPutLayer);


    }


    public void addVertexs(Layer layer){
        GraphNode[] nodes = layer.getNodeList();
        for(int i = 0;i< nodes.length;i++){
            graph.addVertex(nodes[i]);
        }
    }
    public void connectLayers(Layer in,Layer out){
        GraphNode[] start = in.getNodeList();
        GraphNode[] end = out.getNodeList();

        for(int i = 0;i< start.length;i++){
            for (int j = 0;j< end.length;j++){
                MyWeightedEdge e = graph.addEdge(start[i],end[j]);
                graph.setEdgeWeight(e,(Math.random()*2) - 1 );
            }
        }
    }

    public void updateOwnCardInputs(double[] inputs)throws Exception{
        if(inputs.length !=  cardsOwnLayer.getNodeList().length){
            throw new Exception("Input size not matching layer size");
        }
        GraphNode[] inputNodes = cardsOwnLayer.getNodeList();
        for(int i = 0;i < inputs.length; i++ ){
            inputNodes[i].setOutputValue(inputs[i]);
        }
    }

    public void updateOtherCardInputs(double[] inputs)throws Exception{
        if(inputs.length !=  cardsOtherLayer.getNodeList().length){
            throw new Exception("Input size not matching layer size");
        }
        GraphNode[] inputNodes = cardsOtherLayer.getNodeList();
        for(int i = 0;i < inputs.length; i++ ){
            inputNodes[i].setOutputValue(inputs[i]);
        }
    }
    public void updateDataInputs(double[] inputs)throws Exception{
        if(inputs.length !=  dataLayer.getNodeList().length){
            throw new Exception("Input size not matching layer size");
        }
        GraphNode[] inputNodes = dataLayer.getNodeList();
        for(int i = 0;i < inputs.length; i++ ){
            inputNodes[i].setOutputValue(inputs[i]);
        }
    }

    public void updateActionInputs(double[] inputs)throws Exception{
        if(inputs.length !=  actionLayer.getNodeList().length){
            throw new Exception("Input size not matching layer size");
        }
        GraphNode[] inputNodes = actionLayer.getNodeList();
        for(int i = 0;i < inputs.length; i++ ){
            inputNodes[i].setOutputValue(inputs[i]);
        }
    }

    public void computeOutputs(){
        GraphNode[] layer_1 = hiddenLayer_1.getNodeList();
        GraphNode[] layer_2 = hiddenLayer_2.getNodeList();
        GraphNode[] layer_out= outPutLayer.getNodeList();

        for(int i = 0;i < layer_1.length; i++){
            Set<MyWeightedEdge> edgeSet = graph.incomingEdgesOf(layer_1[i]);
            for(MyWeightedEdge edge : edgeSet){
                layer_1[i].addToInput((edge.getWeight()) * ((GraphNode)edge.getSource()).getOutput());
            }
        }

        for(int i = 0; i< layer_2.length ;i++ ){
            Set<MyWeightedEdge> edgeSet = graph.incomingEdgesOf(layer_2[i]);
            for(MyWeightedEdge edge : edgeSet){
                layer_2[i].addToInput((edge.getWeight()) * ((GraphNode)edge.getSource()).getOutput());
            }
        }
        for(int i = 0; i< layer_out.length ;i++ ){
            Set<MyWeightedEdge> edgeSet = graph.incomingEdgesOf(layer_out[i]);
            for(MyWeightedEdge edge : edgeSet){
                layer_out[i].addToInput((edge.getWeight()) * ((GraphNode)edge.getSource()).getOutput());
            }
        }

    }

    public double[] getOutputs(){
        double [] vals = new double[OUTPUT_SIZE];
        GraphNode[] outNodes = outPutLayer.getNodeList();

        for(int i = 0; i< OUTPUT_SIZE;i++){
            vals[i] = outNodes[i].getOutput();
        }
        return vals;

    }

    @Override
    public String toString() {
        return graph.toString();
    }
}
