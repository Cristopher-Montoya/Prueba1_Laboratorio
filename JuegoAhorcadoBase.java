/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba1_laboratorio;

/**
 *
 * @author Jayma
 */
public abstract class JuegoAhorcadoBase implements JuegoAhorcado {
    protected String palabraSecreta;
    protected String palabraActual;
    protected int intentos;

    public abstract void inicializarPalabraSecreta();

    public abstract void jugar();

    public abstract void actualizarPalabraActual(char letra);

    public abstract boolean verificarLetra(char letra);

    public abstract boolean hasGanado();
}
