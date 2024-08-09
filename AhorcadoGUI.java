package prueba1_laboratorio;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Jayma
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AhorcadoGUI extends JFrame {

    private JuegoAhorcadoBase juego;
    private JLabel palabraLabel;
    private JLabel intentosLabel;
    private JTextField letraTextField;
    private JButton adivinarButton;
    private JTextArea resultadoArea;
    private JTextArea listaPalabrasArea;
    private JTextField palabraTextField;
    private JButton agregarPalabraButton;
    private JButton establecerFijoButton;
    private AdminPalabrasSecretas admin;
    private String palabraFija;

    public AhorcadoGUI() {
        setTitle("Juego del Ahorcado");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior para mostrar la palabra y los intentos restantes
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 2));

        palabraLabel = new JLabel("Palabra: ");
        intentosLabel = new JLabel("Intentos restantes: ");
        letraTextField = new JTextField();
        adivinarButton = new JButton("Adivinar");

        topPanel.add(palabraLabel);
        topPanel.add(intentosLabel);
        topPanel.add(new JLabel("Ingresa una letra: "));
        topPanel.add(letraTextField);

        add(topPanel, BorderLayout.NORTH);

        // Área de texto para mostrar resultados y mensajes
        resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);
        add(new JScrollPane(resultadoArea), BorderLayout.CENTER);

        // Panel inferior con botones para seleccionar el tipo de juego
        JPanel bottomPanel = new JPanel();
        JButton juegoFijoButton = new JButton("Juego Fijo");
        JButton juegoAzarButton = new JButton("Juego Azar");
        JButton administrarButton = new JButton("Administrar");

        bottomPanel.add(juegoFijoButton);
        bottomPanel.add(juegoAzarButton);
        bottomPanel.add(administrarButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // Acción para iniciar el juego con palabra fija
        juegoFijoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (palabraFija == null) {
                    resultadoArea.append("Primero debes establecer una palabra fija en la pantalla de administración.\n");
                    return;
                }
                iniciarJuegoFijo();
            }
        });

        // Acción para iniciar el juego con palabra al azar
        juegoAzarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarJuegoAzar();
            }
        });

        // Acción para mostrar la pantalla de administración
        administrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPantallaAdministracion();
            }
        });

        // Acción para adivinar una letra
        adivinarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jugar();
            }
        });

        topPanel.add(adivinarButton);
        setVisible(true);
    }

    private void iniciarJuegoFijo() {
        juego = new JuegoAhorcadoFijo(palabraFija) {
            @Override
            public void Jugar() {
                throw new UnsupportedOperationException("Not supported yet."); // Placeholder for overridden method
            }
        };
        resultadoArea.setText("Iniciado juego con palabra fija.\n");
        actualizarPantalla();
        desbloquearJuego(); // Permitir jugar después de iniciar un nuevo juego
    }

    private void iniciarJuegoAzar() {
        if (admin == null) {
            resultadoArea.append("Primero debes administrar las palabras.\n");
            return;
        }
        juego = new JuegoAhorcadoAzar(admin) {
            @Override
            public void Jugar() {
                throw new UnsupportedOperationException("Not supported yet."); // Placeholder for overridden method
            }
        };
        resultadoArea.setText("Iniciado juego con palabra al azar.\n");
        actualizarPantalla();
        desbloquearJuego(); // Permitir jugar después de iniciar un nuevo juego
    }

    private void jugar() {
        if (juego == null) {
            resultadoArea.append("Primero selecciona un tipo de juego.\n");
            return;
        }

        String letra = letraTextField.getText().trim();
        if (letra.length() != 1) {
            resultadoArea.append("Ingresa solo una letra.\n");
            letraTextField.setText("");
            return;
        }

        char letraChar = letra.charAt(0);
        if (juego.verificarLetra(letraChar)) {
            resultadoArea.append("¡Correcto!\n");
            juego.actualizarPalabraActual(letraChar);
        } else {
            resultadoArea.append("Incorrecto.\n");
            juego.intentos--; // Resta de intentos en caso de letra incorrecta
        }

        actualizarPantalla();

        if (juego.hasGanado()) {
            resultadoArea.append("¡Felicidades! Has adivinado la palabra: " + juego.palabraSecreta + "\n");
            bloquearJuego();
        } else if (juego.intentos <= 0) {
            resultadoArea.append("Lo siento, has perdido. La palabra era: " + juego.palabraSecreta + "\n");
            bloquearJuego();
        }
    }

    private void actualizarPantalla() {
        if (juego != null) {
            palabraLabel.setText("Palabra: " + juego.palabraActual);
            intentosLabel.setText("Intentos restantes: " + juego.intentos);
        }
        letraTextField.setText("");
        letraTextField.requestFocus();
    }

    private void bloquearJuego() {
        adivinarButton.setEnabled(false);
        letraTextField.setEnabled(false);
    }
    
    private void desbloquearJuego() {
        adivinarButton.setEnabled(true);
        letraTextField.setEnabled(true);
    }

    private void mostrarPantallaAdministracion() {
        JFrame adminFrame = new JFrame("Administrar Palabras");
        adminFrame.setSize(600, 400);
        adminFrame.setLayout(new BorderLayout());

        JPanel adminPanel = new JPanel();
        adminPanel.setLayout(new GridLayout(3, 2));

        admin = new AdminPalabrasSecretas();
        palabraTextField = new JTextField();
        agregarPalabraButton = new JButton("Agregar Palabra");
        establecerFijoButton = new JButton("Establecer Palabra Fija");

        adminPanel.add(new JLabel("Nueva Palabra: "));
        adminPanel.add(palabraTextField);
        adminPanel.add(agregarPalabraButton);
        adminPanel.add(establecerFijoButton);

        listaPalabrasArea = new JTextArea();
        listaPalabrasArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(listaPalabrasArea);

        adminFrame.add(adminPanel, BorderLayout.NORTH);
        adminFrame.add(scrollPane, BorderLayout.CENTER);

        // Acción para agregar palabra
        agregarPalabraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String palabra = palabraTextField.getText().trim();
                if (!palabra.isEmpty()) {
                    admin.agregarPalabra(palabra);
                    listaPalabrasArea.append(palabra + "\n");
                    palabraTextField.setText("");
                } else {
                    JOptionPane.showMessageDialog(adminFrame, "Por favor, ingresa una palabra.");
                }
            }
        });

        // Acción para establecer palabra fija
        establecerFijoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (admin.getPalabras().isEmpty()) {
                    JOptionPane.showMessageDialog(adminFrame, "No hay palabras para establecer como fija.");
                    return;
                }
                String palabra = JOptionPane.showInputDialog(adminFrame, "Selecciona la palabra fija:");
                if (palabra != null && !palabra.isEmpty() && admin.getPalabras().contains(palabra)) {
                    palabraFija = palabra;
                    resultadoArea.append("Palabra fija establecida: " + palabraFija + "\n");
                } else {
                    JOptionPane.showMessageDialog(adminFrame, "Palabra no válida o no encontrada.");
                }
            }
        });

        adminFrame.setVisible(true);
    }
}
