package Behaviour;

import org.jgrapht.graph.DefaultWeightedEdge;

public class MyWeightedEdge extends DefaultWeightedEdge {
    @Override
    public String toString() {
        return Double.toString(super.getWeight());
    }
    public double getWeight(){
        return super.getWeight();
    }
    public Object getSource(){
        return super.getSource();
    }
}
