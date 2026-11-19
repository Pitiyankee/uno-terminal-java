package UNO;

import java.util.ArrayList;
import java.util.Scanner;

public class Partida {

    // --- ATRIBUTOS ---
    private ArrayList<Jugador> jugadores;
    private Baraja mazo;
    private Baraja descarte;
    private boolean sentido;
    private int turno;

    // --- CONSTRUCTOR ---
    public Partida(ArrayList<Jugador> jugadores, Baraja mazo) {
        this.jugadores = jugadores;
        this.mazo = mazo;
        this.descarte = new Baraja(); // Este es el descarte
        this.sentido = true; // Empezamos girando hacia la derecha
        this.turno = 0; // Empieza el primer jugador
    }

    // --- GETTERS Y SETTERS ---
    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public Baraja getMazo() {
        return mazo;
    }

    public Baraja getDescarte() {
        return descarte;
    }

    public boolean isSentido() {
        return sentido;
    }

    public void setSentido(boolean sentido) {
        this.sentido = sentido;
    }

    public int getTurno() {
        return turno;
    }

    // --- LÓGICA DEL JUEGO ---

    // 1. Iniciar Partida: Prepara el mazo de descarte
    public void iniciarPartida() {
        // Robamos la primera carta para iniciar el juego
        Carta primera = this.mazo.robarCarta();

        // Regla: si la primera carta es un comodín, se devuelve al mazo y se saca otra
        while (primera instanceof CartaComodin) {
            this.mazo.getCartas().add(primera); // La devolvemos al fondo
            this.mazo.barajar();
            primera = this.mazo.robarCarta();
        }

        // La ponemos en la mesa
        this.descarte.getCartas().add(primera);
        System.out.println("Partida iniciada. Primera carta: " + primera.toString());
    }

    // 2. Reponer Descarte: Si se acaba el mazo, usamos el descarte
    private void reponerDescarte() {
        if (this.mazo.getCartas().isEmpty()) {
            System.out.println("¡Se acabó el mazo! Barajando descarte...");

            // Guardamos la carta de arriba (la que está en juego)
            Carta cartaEnJuego = this.descarte.robarCarta();

            // Pasamos todas las cartas del descarte al mazo
            while (!this.descarte.getCartas().isEmpty()) {
                this.mazo.getCartas().add(this.descarte.robarCarta());
            }

            // Barajeamos el nuevo mazo de cartas
            this.mazo.barajar();

            // Devolvemos la carta en juego al descarte
            this.descarte.getCartas().add(cartaEnJuego);
        }
    }

    // 3. Cambiar Turno: La lógica circular
    public void cambiarTurno() {
        if (this.sentido) { // Sentido Horario (+)
            this.turno++;
        } else { // Sentido Antihorario (-)
            this.turno--;
        }

        // Usamos floorMod para que el turno siempre sea válido (0, 1, 2...)
        // Si turno es -1, floorMod lo convierte al último jugador.
        this.turno = Math.floorMod(this.turno, this.jugadores.size());
    }

    // 4. Saltar Turno (Simplemente llama a cambiar turno una vez extra)
    public void saltarTurno() {
        System.out.println("¡Salto de turno!");
        cambiarTurno();
    }

    // 5. Obtener quién es el siguiente (para los +2 o +4)
    public Jugador getSiguienteJugador() {
        int siguienteTurno;
        if (this.sentido) {
            siguienteTurno = this.turno + 1;
        } else {
            siguienteTurno = this.turno - 1;
        }
        // Ajustamos el número para que no se salga del array
        int index = Math.floorMod(siguienteTurno, this.jugadores.size());
        return this.jugadores.get(index);
    }

    // 6. Validar si una carta se puede tirar
    public boolean esCartaValida(Carta carta) {

        // Miramos la carta que está arriba del descarte (posición 0)
        if (this.descarte.getCartas().isEmpty()) return true;

        Carta tope = this.descarte.getCartas().get(0);

        // A) Comodines siempre valen
        if (carta instanceof CartaComodin) return true;

        // B) Mismo color
        if (carta.getColor().equals(tope.getColor())) return true;

        // C) Mismo valor (Ej: 5 rojo sobre 5 azul)
        if (carta.getValor().equals(tope.getValor())) return true;

        return false; // Si no cumple nada, no vale
    }

    // 7. Verificar Victoria
    public boolean verificarVictoria() {
        for (Jugador j : this.jugadores) {
            if (j.getMano().isEmpty()) {
                System.out.println("¡GANADOR: " + j.getNombre() + "!");
                return true; // Alguien ganó
            }
        }
        return false; // Nadie ha ganado aún
    }

    // 8. EL TURNO DEL JUGADOR (Interacción con usuario)
    public void turno() {
        Jugador jugadorActual = this.jugadores.get(this.turno);
        Carta cartaTope = this.descarte.getCartas().get(0);

        System.out.println("\n---------------------------------");
        System.out.println("Turno de: " + jugadorActual.getNombre() + " (ID: " + jugadorActual.getIdJugador() + ")");
        System.out.println("Carta en la mesa:");
        System.out.println(cartaTope); // Muestra el dibujo

        // Preguntar si quiere ver su mano
        jugadorActual.mostrarEstado();

        // Preguntar acción
        Scanner scanner = Entrada.SCANNER;
        System.out.println("Elige acción: [1] Jugar Carta | [2] Robar");
        String opcion = scanner.nextLine();

        if (opcion.equals("1")) {
            // JUGAR CARTA
            System.out.print("Introduce el número de la carta (índice 0, 1...): ");
            try {
                int indice = Integer.parseInt(scanner.nextLine());

                // Comprobamos que el índice existe
                if (indice >= 0 && indice < jugadorActual.getMano().size()) {
                    Carta cartaElegida = jugadorActual.getMano().get(indice);

                    if (esCartaValida(cartaElegida)) {
                        jugadorActual.jugarCarta(cartaElegida, this); // Juega la carta
                        cambiarTurno(); // Solo pasa el turno si jugó bien
                    } else {
                        System.out.println("❌ CARTA INVÁLIDA: No coincide color ni valor.");
                        // No cambiamos turno, vuelve a intentar
                    }
                } else {
                    System.out.println("❌ Número de carta incorrecto.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Por favor, escribe un número válido.");
            }

        } else if (opcion.equals("2")) {
            // ROBAR CARTA
            reponerDescarte(); // Verificar si hay cartas antes de robar
            jugadorActual.robarCarta(this.mazo);
            System.out.println("Has robado una carta.");
            cambiarTurno(); // Pasa turno al robar
        } else {
            System.out.println("Opción no válida. Pierdes el turno por despistado.");
            cambiarTurno();
        }
    }
}