package com.jhw.simulation.dots.utils;

import com.vividsolutions.jts.geom.LineString;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Jesús Hernández Barrios
 * @author Margaret Sánchez Martínez
 */
public class Utility_Class {

    private final static Icon help = new ImageIcon("media/icons/help.png");
    private final static Icon deny = new ImageIcon("media/icons/deny.png");

    //ensure the non-instantiability
    private Utility_Class() {
    }

    public static double lineStringLength(LineString l) {
        int earthRadio = 6378137;//this is the earth radio in the Ecuador
        return l.getLength() * (Math.PI / 180) * earthRadio;
    }

    public static void validateDoubles(KeyEvent evt) {
        if (!Character.isDigit(evt.getKeyChar()) && evt.getKeyChar() != '.') {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
    }

    public static void validateIntegers(KeyEvent evt) {
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }

    public static void writeSizePassed(JTextField jTextField, int TamMax, KeyEvent evt) {
        if (jTextField.getText().length() >= TamMax) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
    }

    public static void jopHelp(String sms) {
        JOptionPane.showMessageDialog(null, sms, "Ayuda", JOptionPane.ERROR_MESSAGE, help);
    }

    public static boolean jopContinue(String text) {
        return 0 == JOptionPane.showConfirmDialog(null, text, "Continuar", 0, JOptionPane.INFORMATION_MESSAGE, help);
    }

    public static void jopError(String sms) {
        JOptionPane.showMessageDialog(null, "Error. " + sms, "Error", JOptionPane.ERROR_MESSAGE, deny);
    }

    public static void pastSizeWriting(Component jTextField, int TamMax, KeyEvent evt) {
        if (((JTextField) jTextField).getText().length() >= TamMax) {
            evt.consume();
        }
    }

    public static void updateComboBox(JComboBox combo, List<String> nombres) {
        combo.removeAllItems();
        for (String nombre : nombres) {
            combo.addItem(nombre);
        }
    }

    public static String jopExportName() {
        return JOptionPane.showInputDialog("Introduzca el nombre del fichero");
    }
}
