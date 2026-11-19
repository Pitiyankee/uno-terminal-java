package UNO;

import java.util.Scanner;

/** Único lector de teclado del juego, compartido por todas las clases. */
public final class Entrada {

    public static final Scanner SCANNER = new Scanner(System.in);

    private Entrada() {
    }
}
