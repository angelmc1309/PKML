import Behaviour.BehaviourController;
import junit.framework.TestCase;

public class TestNetwork extends TestCase {
    public void testNetwork()throws Exception{
        BehaviourController behaviourController = new BehaviourController();
        behaviourController.setMaxHands(1000);
        behaviourController.nonStopMode();
    }

}
