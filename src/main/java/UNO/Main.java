package UNO;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = Entrada.SCANNER;

        // 1. Mensaje de bienvenida
        System.out.println("************************************************************************");
        System.out.println("* BIENVENIDO AL JUEGO DE UNO DISEÑADO POR PEDRO PITIYANKEE EN TERMINAL *");
        System.out.println("************************************************************************");

        // 2. Pedir el número de jugadores (Mínimo 2)
        int numJugadores = 0;
        while (numJugadores < 2) {
            System.out.print("¿Cuántos jugadores van a participar? (mínimo 2): ");
            try {
                numJugadores = Integer.parseInt(scanner.nextLine());
                if (numJugadores < 2) {
                    System.out.println("⚠️ Error: Necesitas al menos 2 jugadores.");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Por favor, introduce un número válido.");
            }
        }

        // 3. Aqui se crean los jugadores
        ArrayList<Jugador> listaJugadores = new ArrayList<>();

        for (int i = 1; i <= numJugadores; i++) {
            System.out.print("Nombre del jugador " + i + ": ");
            String nombre = scanner.nextLine();
            listaJugadores.add(new Jugador(nombre));
        }

        // 4. Aqui se genera la baraja y barajar
        System.out.println("\nGenerando baraja y mezclando cartas...");
        Baraja barajaPrincipal = new Baraja();
        barajaPrincipal.generarBaraja();
        barajaPrincipal.barajar();

        // 5. aqui se realiza la reparticion de 7 cartas a cada jugador
        System.out.println("Repartiendo 7 cartas a cada jugador...");
        for (Jugador j : listaJugadores) {
            for (int k = 0; k < 7; k++) {
                j.robarCarta(barajaPrincipal);
            }
        }

        // 6. Crear e Iniciar la Partida
        Partida partida = new Partida(listaJugadores, barajaPrincipal);
        partida.iniciarPartida();

        // 7. BUCLE DEL JUEGO: Sigue mientras nadie gane
        // El método verificarVictoria devuelve true si alguien gana, así que usamos ! (mientras NO gane nadie)
        while (!partida.verificarVictoria()) {
            partida.turno();

            // Un pequeño respiro para leer lo que pasó antes del siguiente turno
            System.out.println("(Presiona ENTER para continuar al siguiente turno...)");
            scanner.nextLine();
        }

        System.out.println("*********************************");
        System.out.println("* ¡FIN DEL JUEGO! HASTA LUEGO        *");
        System.out.println("*********************************");
    }
}