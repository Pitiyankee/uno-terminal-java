package UNO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner; // No olvidar que esto es super Necesario para pedir el color

public class CartaComodin extends Carta implements Efecto, Colorable {

    public CartaComodin(String color, String valor) {
        super(color, valor);
    }

    @Override
    public void cambiarColor(String nuevoColor) {
        this.color = nuevoColor;
    }

    @Override
    public void aplicarEfecto(Partida partida) {
        Scanner scanner = Entrada.SCANNER;
        System.out.println("¡Has jugado un COMODÍN!");

        // 1. Pedir al usuario el nuevo color
        System.out.println("Elige el nuevo color (rojo, verde, azul, amarillo): ");
        String nuevoColor = scanner.nextLine().toLowerCase();

        // Aqui hacemos una verificacion para validar que escriba un color correcto
        while (!nuevoColor.equals("rojo") && !nuevoColor.equals("verde") &&
                !nuevoColor.equals("azul") && !nuevoColor.equals("amarillo")) {
            System.out.println("Color no válido. Intenta de nuevo (rojo, verde, azul, amarillo): ");
            nuevoColor = scanner.nextLine().toLowerCase();
        }

        // Cambiamos el color de ESTA carta
        this.cambiarColor(nuevoColor);
        System.out.println("El color del juego ahora es: " + nuevoColor.toUpperCase());

        // 2. Si es la carta +4, aplicamos la penitencia o castigo
        if (this.valor.equals("masCUATRO")) {
            System.out.println("¡Y el siguiente jugador chupa 4 cartas!");
            Jugador siguiente = partida.getSiguienteJugador();

            // aqui se Roba 4 veces
            for (int i = 0; i < 4; i++) {
                siguiente.robarCarta(partida.getMazo());
            }

            // aqui el jugador Pierde su turno
            partida.saltarTurno();
        }
    }

    @Override
    public String toString() {
        String colorAnsi = Carta.RESET;

        // Usamos switch para asignar el color del dibujo
        switch (this.color) {
            case "verde":
                colorAnsi = Carta.VERDE;
                break;
            case "rojo":
                colorAnsi = Carta.ROJO;
                break;
            case "azul":
                colorAnsi = Carta.AZUL;
                break;
            case "amarillo":
                colorAnsi = Carta.AMARILLO;
                break;
            default:
                colorAnsi = Carta.NEGRO;
                break; // Si aún es negro
        }

        String arteAscii = "";
        try {
            BufferedReader lector = new BufferedReader(new FileReader("ascii_art/" + this.valor + ".txt"));
            String linea;
            while ((linea = lector.readLine()) != null) {
                arteAscii += colorAnsi + linea + Carta.RESET + "\n";
            }
            lector.close();
        } catch (IOException e) {
            return Carta.cartaEnTexto(this.color, this.valor, colorAnsi);
        }
        return arteAscii;
    }
}
