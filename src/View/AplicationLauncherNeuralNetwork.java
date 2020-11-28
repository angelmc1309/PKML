package View;

import Behaviour.BehaviourController;

import java.util.Scanner;

public class AplicationLauncherNeuralNetwork {
    public static void main(String[] args) {
        BehaviourController behaviourController;
        try {
            behaviourController = new BehaviourController();
        }catch (Exception e){
            System.out.println("Errors while creating the neural network");
            return;
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Bienvenido al proyecto de inteligencia artificial de Ángel Morales y Changhao Wang!\n");
        System.out.println("El codigo se puede ejecutar en dos modos: \n\nEn el primer modo los jugadores realizan su " +
                "jugada una vez introducimos n por consola y el siguiente jugador espera hasta que lo volvamos a indicar." +
                "\n\nEn el segundo modo los jugadores juegan automáticamente hasta llegar a un cierto número de manos." +
                "\n\nIntroduce 1 para el primer modo, 2 para el segundo\n");
        int mode = sc.nextInt();
        String aux;
        if(mode == 1){
            while(true) {
                System.out.println("Introduce n para que la inteligencia artificial realice su jugada. Introduce q para acabar el programa");
                aux = sc.next();
                behaviourController.decideMovement();
                if(aux.equals("q")){
                    return;
                }
            }
        }
        else if(mode == 2){
            System.out.println("Cuantas manos quieres que jueguen las inteligencias artificiales?");
            int h = sc.nextInt();
            behaviourController.setMaxHands(h);
            behaviourController.nonStopMode();
        }

    }
}