package Behaviour;




public class PlayerBehaviour {
    private Network network;

    public PlayerBehaviour(){
        network = new Network();
    }
    public PlayerBehaviour(Network network){
        this.network = network;
    }

    public int getAction(){
        network.computeOutputs();
        double[] outputs = network.getOutputs();
        int index = 0;
        double maxValue = 0;
        for(int i = 0; i < outputs.length - 1;i++){
            if(outputs[i] > maxValue){
                maxValue = outputs[i];
                index = i;
            }
        }

        return index;
    }
    public double getRaisePercent(){
        return network.getOutputs()[network.getOutputs().length - 1 ];
    }

    public void updateDataInputs(double [] data) throws Exception{
        network.updateDataInputs(data);
    }

    public void updateActionInputs(double[] data)throws Exception{
        network.updateActionInputs(data);
    }
    public void updateOwnCardInputs(double [] data)throws Exception{
        network.updateOwnCardInputs(data);

    }
    public void updateOtherCardInputs(double [] data)throws Exception{
        network.updateOtherCardInputs(data);
    }


}
