import Controller.Controller;
import junit.framework.TestCase;

public class ControllerTest extends TestCase {
    public void testRound(){
        Controller controller = new Controller();
        controller.startGame();
        try {
            System.out.println(controller.boardToString());
            controller.fold();
            controller.raise(500);
            controller.fold();
            controller.call();
            controller.fold();
            controller.fold();

            System.out.println(controller.boardToString());

            controller.check();
            controller.check();

            controller.raise(200);
            controller.call();
            System.out.println(controller.boardToString());
            controller.check();
            controller.check();


            System.out.println(controller.boardToString());

            controller.allIn();
            controller.allIn();
            controller.allIn();
            controller.allIn();
            System.out.println(controller.boardToString());
            controller.allIn();
            System.out.println(controller.boardToString());
            controller.allIn();
            System.out.println(controller.boardToString());


        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
