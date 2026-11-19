package UNO;

import java.util.ArrayList;
import java.util.Collections;

public class Baraja {

    private ArrayList<Carta> cartas;

    public Baraja() {
        this.cartas = new ArrayList<>();
    }

    public void generarBaraja() {
        String[] colores = {"verde", "rojo", "azul", "amarillo"};

        for (String color : colores) {
            // 1. NÚMEROS (0-9)
            for (int i = 0; i <= 9; i++) {
                this.cartas.add(new CartaNumero(color, String.valueOf(i)));
                this.cartas.add(new CartaNumero(color, String.valueOf(i)));
            }

            // 2. ESPECIALES
            // Usamos los nombres de archivo exactos: "masDOS", "prohibido", "reverse"
            for (int i = 0; i < 2; i++) {
                this.cartas.add(new CartaEspecial(color, "masDOS"));    // Era +2
                this.cartas.add(new CartaEspecial(color, "prohibido")); // Era Salta turno
                this.cartas.add(new CartaEspecial(color, "reverse"));   // Era Cambio sentido
            }
        }

        // 3. COMODINES (Color negro es seleccionado por defecto)
        // Usamos los nombres: "cambioColor" y "masCUATRO"
        for (int i = 0; i < 4; i++) {
            this.cartas.add(new CartaComodin("negro", "cambioColor"));
            this.cartas.add(new CartaComodin("negro", "masCUATRO"));
        }
    }

    public void barajar() {
        Collections.shuffle(this.cartas);
    }

    public Carta robarCarta() {
        if (this.cartas.isEmpty()) return null;
        return this.cartas.remove(0);
    }

    // Este metodo es necesario para ver si todo funciona al ejecutar el codigo
    public ArrayList<Carta> getCartas() {
        return cartas;
    }

    @Override
    public String toString() {
        String resultado = "";
        for (Carta c : this.cartas) {
            resultado += c.toString() + "\n";
        }
        return resultado;
    }
}
