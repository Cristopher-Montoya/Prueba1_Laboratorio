/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruba1_laboratorio;

/**
 *
 * @author Josue
 */
import java.util.List;
import java.util.Random;

public abstract class JuegoAhorcadoAzar extends JuegoAhorcadoBase {
    private AdminPalabrasSecretas admin;

    public JuegoAhorcadoAzar(AdminPalabrasSecretas admin) {
        this.admin = admin;
        inicializarPalabraSecreta();
    }

    @Override
    public void inicializarPalabraSecreta() {
        List<String> palabras = admin.getPalabras();
        Random random = new Random();
        palabraSecreta = palabras.get(random.nextInt(palabras.size()));
        palabraActual = "_".repeat(palabraSecreta.length());
        intentos = 6; // Número de intentos predeterminado
    }

    @Override
    public void jugar() {
        // La lógica del juego se manejará en la interfaz gráfica.
    }

    @Override
    public void actualizarPalabraActual(char letra) {
        StringBuilder nuevaPalabra = new StringBuilder(palabraActual);
        for (int i = 0; i < palabraSecreta.length(); i++) {
            if (palabraSecreta.charAt(i) == letra) {
                nuevaPalabra.setCharAt(i, letra);
            }
        }
        palabraActual = nuevaPalabra.toString();
    }

    @Override
    public boolean verificarLetra(char letra) {
        return palabraSecreta.indexOf(letra) >= 0;
    }

    @Override
    public boolean hasGanado() {
        return palabraActual.equals(palabraSecreta);
    }
}
