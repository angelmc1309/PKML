package View;

import Controller.Controller;

import java.util.Scanner;


public class AplicationLauncher {

    public static void main(String[] args) {
        Controller controller = new Controller();
        Scanner sc = new Scanner(System.in);
        controller.startGame();
        String action = "";
        System.out.println(controller.boardToString());
        while(action != "QUIT"){
            try {
                action = sc.next();
                switch (action) {
                    case "F": {
                        controller.fold();
                        break;
                    }
                    case "C": {
                        controller.call();
                        break;
                    }
                    case "CK": {
                        controller.check();
                        break;
                    }
                    case "R": {
                        float amount;
                        amount = sc.nextFloat();
                        controller.raise(amount);
                        break;
                    }
                    case "A": {
                        controller.allIn();
                        break;
                    }
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            System.out.println(controller.boardToString());

        }
    }


}
