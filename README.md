# UNO por terminal

Juego de cartas **UNO** para jugar por terminal, desarrollado en **Java 17** con **Maven**. Para 2 o más jugadores que comparten un mismo ordenador, con baraja completa, cartas especiales, comodines y turnos con sentido horario o antihorario.

## Cómo se juega

1. Indica cuántos jugadores participan (mínimo 2) y sus nombres.
2. Se reparten 7 cartas a cada jugador y se pone una carta en la mesa.
3. En su turno cada jugador puede ver su mano y elegir entre **jugar una carta** o **robar**.
4. Una carta se puede jugar si coincide en **color** o en **valor** con la de la mesa, o si es un **comodín**.
5. **Gana** el primero que se queda sin cartas.

| Tipo de carta | Cartas |
|---|---|
| Números | 0 al 9 en cuatro colores (verde, rojo, azul y amarillo) |
| Especiales | +2, salto y reversa (cambia el sentido de los turnos) |
| Comodines | Cambio de color y +4 |

Las cartas se muestran a color en la terminal. Pulsa ENTER para pasar de un turno al siguiente.

## Cómo ejecutarlo

Requisitos: JDK 17 o superior y Maven.

```bash
git clone https://github.com/Pitiyankee/uno-terminal-java.git
cd uno-terminal-java
mvn compile exec:java
```

También se puede abrir la carpeta en IntelliJ IDEA y ejecutar `UNO.Main`.

### Dibujos ASCII de las cartas (opcional)

Si existe una carpeta `ascii_art/` junto al proyecto con un archivo de texto por carta (`0.txt` a `9.txt`, `masDOS.txt`, `masCUATRO.txt`, `reverse.txt`, `prohibido.txt` y `cambioColor.txt`), las cartas se dibujan como arte ASCII a color. Si no existe, el juego dibuja cada carta en **modo texto**:

```
+-------------------+
|       AZUL        |
|         4         |
+-------------------+
```

## Aspectos técnicos

- **Herencia y clase abstracta:** `Carta` es abstracta y de ella heredan `CartaNumero`, `CartaEspecial` y `CartaComodin`, cada una con su `toString`.
- **Interfaces:** `Efecto` (aplica el efecto de una carta a la partida), `Colorable` (cambio de color de un comodín) y `Jugable` (acciones de un jugador).
- **Polimorfismo:** la partida trata todas las cartas como `Carta` y cada tipo aplica su propio comportamiento.
- **Colecciones:** baraja, descarte y manos con `ArrayList`, y barajado aleatorio.
- **Turnos circulares:** el sentido de juego se invierte con la reversa y el turno se calcula con `Math.floorMod`.
- **Un único lector de teclado** compartido por todas las clases (`Entrada`).
- **Salida a color** con secuencias ANSI.

## Estructura

```
src/main/java/UNO/
├── Main.java          Registro de jugadores, reparto y bucle de la partida
├── Partida.java       Turnos, sentido, validación de cartas y victoria
├── Jugador.java       Mano del jugador y acciones
├── Baraja.java        Generación y barajado
├── Carta.java         Clase abstracta base
├── CartaNumero.java · CartaEspecial.java · CartaComodin.java
├── Efecto.java · Colorable.java · Jugable.java      Interfaces
└── Entrada.java       Lector de teclado compartido
```

## Autor

Pedro Bandes · [@Pitiyankee](https://github.com/Pitiyankee) · [LinkedIn](https://www.linkedin.com/in/pedrobandesduran)
