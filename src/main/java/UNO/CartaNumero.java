package UNO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CartaNumero extends Carta {

    public CartaNumero(String color, String valor) {
        super(color, valor);
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
            // Los dibujos están en la carpeta ascii_art/ (un .txt por carta)
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