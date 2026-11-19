package UNO;

public abstract class Carta {

    public static final String VERDE = "\033[32m";
    public static final String ROJO = "\033[31m";
    public static final String AZUL = "\033[34m";
    public static final String AMARILLO = "\033[33m";
    public static final String NEGRO = "\033[30m";
    public static final String RESET = "\033[0m";

    protected String color;
    protected String valor;

    public Carta(String color, String valor) {
        this.color = color;
        this.valor = valor;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getValor() {
        return valor;
    }

    public abstract String toString();

    /**
     * Dibuja la carta en modo texto. Se usa cuando no existe el archivo de arte ASCII
     * correspondiente en la carpeta ascii_art/.
     */
    protected static String cartaEnTexto(String color, String valor, String colorAnsi) {
        String etiqueta;
        switch (valor) {
            case "masDOS":
                etiqueta = "+2";
                break;
            case "masCUATRO":
                etiqueta = "+4";
                break;
            case "reverse":
                etiqueta = "REVERSA";
                break;
            case "prohibido":
                etiqueta = "SALTO";
                break;
            case "cambioColor":
                etiqueta = "CAMBIO DE COLOR";
                break;
            default:
                etiqueta = valor;
        }
        String nombreColor = color.equals("negro") ? "COMODIN" : color.toUpperCase();
        int ancho = 19;
        String borde = "+" + "-".repeat(ancho) + "+";
        return colorAnsi + borde + "\n"
                + centrar(nombreColor, ancho) + "\n"
                + centrar(etiqueta, ancho) + "\n"
                + borde + Carta.RESET + "\n";
    }

    private static String centrar(String texto, int ancho) {
        int izquierda = (ancho - texto.length()) / 2;
        int derecha = ancho - texto.length() - izquierda;
        return "|" + " ".repeat(izquierda) + texto + " ".repeat(derecha) + "|";
    }
}
