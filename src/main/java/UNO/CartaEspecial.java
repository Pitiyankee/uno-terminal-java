package UNO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CartaEspecial extends Carta implements Efecto {

    public CartaEspecial(String color, String valor) {
        super(color, valor);
    }

    @Override
    public void aplicarEfecto(Partida partida) {
        switch (this.valor) {
            case "masDOS":
                System.out.println("¡El siguiente jugador chupa 2 cartas!");
                Jugador siguiente = partida.getSiguienteJugador();
                siguiente.robarCarta(partida.getMazo());
                siguiente.robarCarta(partida.getMazo());
                partida.saltarTurno();
                break;
            case "prohibido":
                System.out.println("¡Salto de turno!");
                partida.saltarTurno();
                break;
            case "reverse":
                System.out.println("¡Cambio de sentido!");
                partida.setSentido(!partida.isSentido());
                break;
        }
    }

    @Override
    public String toString() {
        String colorAnsi = Carta.RESET;
        if (this.color.equals("verde")) colorAnsi = Carta.VERDE;
        else if (this.color.equals("rojo")) colorAnsi = Carta.ROJO;
        else if (this.color.equals("azul")) colorAnsi = Carta.AZUL;
        else if (this.color.equals("amarillo")) colorAnsi = Carta.AMARILLO;

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
