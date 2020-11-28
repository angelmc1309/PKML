package Behaviour;

public class Layer {
    GraphNode[] layerNodes;

    public Layer(int size){
        layerNodes = new GraphNode[size];
        for(int i = 0;i< size;i++){
            layerNodes[i] = new GraphNode();
        }
    }

    public GraphNode[] getNodeList() {
        return layerNodes;
    }

    public void computeOutputs(){
        for(int i = 0;i< layerNodes.length;i++){
            layerNodes[i].calcOutputValue();
        }
    }
    public void resetInputs(){
        for(int i = 0;i< layerNodes.length;i++){
            layerNodes[i].resetInput();
        }
    }

    public void addToInput(int index,double value){
        layerNodes[index].addToInput(value);
    }

}
