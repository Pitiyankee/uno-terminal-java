package UNO;

import java.util.ArrayList;
import java.util.Scanner; // Para preguntar al usuario

public class Jugador implements Jugable {

    // ATRIBUTOS
    private String nombre;
    private ArrayList<Carta> mano;
    private int idJugador;

    // Esta es mi Variable estática: Es compartida por todos los jugadores para contar 0, 1, 2...
    private static int idJugadorGenerator = 0;

    // Este seria mi CONSTRUCTOR
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.mano = new ArrayList<>();

        // Aqui Asignamos el ID actual y luego sumamos 1 para el siguiente jugador
        this.idJugador = idJugadorGenerator;
        idJugadorGenerator++;
    }

    // --- GETTERS Y SETTERS ---
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Carta> getMano() {
        return mano;
    }

    public void setMano(ArrayList<Carta> mano) {
        this.mano = mano;
    }

    public int getIdJugador() {
        return idJugador;
    }

    // --- MÉTODOS DE LA INTERFAZ JUGABLE ---

    @Override
    public void jugarCarta(Carta carta, Partida partida) {
        // 1. Comprobar que la carta está en la mano
        if (this.mano.contains(carta)) {

            // 2. Quitarla de la mano
            this.mano.remove(carta);

            // 3. Ponerla en el mazo de descarte
            partida.getDescarte().getCartas().add(0, carta);

            System.out.println(this.nombre + " ha jugado: " + carta.getValor() + " " + carta.getColor());

            // 4. Si la carta tiene efectos (es Especial o Comodín), se activan
            if (carta instanceof Efecto) {
                ((Efecto) carta).aplicarEfecto(partida);
            }

        } else {
            System.out.println("No tienes esa carta en tu mano.");
        }
    }

    @Override
    public void robarCarta(Baraja baraja) {
        // Robamos del mazo
        Carta cartaRobada = baraja.robarCarta();

        if (cartaRobada != null) {
            // Añadimos carta a la mano
            this.mano.add(cartaRobada);
            // System.out.println(this.nombre + " ha robado una carta.");
        } else {
            System.out.println("No quedan cartas en el mazo para robar.");
        }
    }

    @Override
    public void mostrarEstado() {
        // Se pregunta al jugador si quiere ver su mano
        Scanner scanner = Entrada.SCANNER;
        System.out.println("Jugador " + this.nombre + " (" + this.idJugador + ")");
        System.out.print("¿Quieres ver tus cartas? (s/n): ");
        String respuesta = scanner.nextLine();

        if (respuesta.equalsIgnoreCase("s")) {
            System.out.println("--- TU MANO ---");
            int i = 0;
            for (Carta c : this.mano) {
                // Mostramos el índice para que los jugadores puedan elegir "0", "1", etc.
                System.out.println("[" + i + "] " + c.getColor() + " " + c.getValor());
                i++;
            }
            System.out.println("---------------");
        }
    }
}
