package com.example.myapp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

interface CalculatorOperations {
    double add(double a, double b);
    double subtract(double a, double b);
    double multiply(double a, double b);
    double divide(double a, double b);
    double modulus(double a, double b);
    double power(double a, double b);
    double average(double a, double b);
    double squareRoot(double a);
    double square(double a);
    double reciprocal(double a);
}

class Calculator implements CalculatorOperations {
    public double add(double a, double b) { return a + b; }
    public double subtract(double a, double b) { return a - b; }
    public double multiply(double a, double b) { return a * b; }
    public double divide(double a, double b) { return a / b; }
    public double modulus(double a, double b) { return a % b; }
    public double power(double a, double b) { return Math.pow(a, b); }
    public double average(double a, double b) { return (a + b) / 2.0; }
    public double squareRoot(double a) { return Math.sqrt(a); }
    public double square(double a) { return a * a; }
    public double reciprocal(double a) { return 1.0 / a; }
}

public class App implements ActionListener {
    private JFrame frame;
    private JPanel panel;
    private JTextField t1, t2, tresult;
    private JTextArea historyArea;
    private JButton addBtn, subBtn, mulBtn, divBtn, modBtn, powBtn, avgBtn;
    private JButton sqrtBtn, squareBtn, reciprocalBtn, signBtn, clearBtn, backBtn;
    private JButton memoryAddBtn, memorySubBtn, memoryRecallBtn, memoryClearBtn, exitBtn;
    private Calculator calculator;
    private double memory = 0.0;
    private JButton memoryInfo;

    public App() {
        calculator = new Calculator();
        frame = new JFrame("Java OOP Calculator - Enhanced");
        frame.setSize(850, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        panel = new JPanel();
        panel.setLayout(null);

        JLabel l1 = new JLabel("First Number:");
        t1 = new JTextField(10);
        JLabel l2 = new JLabel("Second Number:");
        t2 = new JTextField(10);
        JLabel l3 = new JLabel("Result:");
        tresult = new JTextField(10);
        tresult.setEditable(false);

        addBtn = new JButton("ADD");
        subBtn = new JButton("SUBTRACT");
        mulBtn = new JButton("MULTIPLY");
        divBtn = new JButton("DIVIDE");
        modBtn = new JButton("MODULUS");
        powBtn = new JButton("POWER");
        avgBtn = new JButton("AVERAGE");
        sqrtBtn = new JButton("SQUARE ROOT");
        squareBtn = new JButton("x²");
        reciprocalBtn = new JButton("1/x");
        signBtn = new JButton("+/-");
        clearBtn = new JButton("CLEAR");
        backBtn = new JButton("BACKSPACE");
        memoryAddBtn = new JButton("M+");
        memorySubBtn = new JButton("M-");
        memoryRecallBtn = new JButton("MR");
        memoryClearBtn = new JButton("MC");
        exitBtn = new JButton("EXIT");

        l1.setBounds(35, 25, 110, 25); t1.setBounds(145, 25, 180, 25);
        l2.setBounds(35, 60, 120, 25); t2.setBounds(145, 60, 180, 25);
        l3.setBounds(35, 95, 100, 25); tresult.setBounds(145, 95, 390, 25);

        addBtn.setBounds(35, 135, 95, 32);
        subBtn.setBounds(140, 135, 110, 32);
        mulBtn.setBounds(260, 135, 110, 32);
        divBtn.setBounds(380, 135, 95, 32);
        modBtn.setBounds(35, 175, 95, 32);
        powBtn.setBounds(140, 175, 110, 32);
        avgBtn.setBounds(260, 175, 110, 32);
        sqrtBtn.setBounds(380, 175, 130, 32);
        squareBtn.setBounds(35, 215, 95, 32);
        reciprocalBtn.setBounds(140, 215, 110, 32);
        signBtn.setBounds(260, 215, 110, 32);
        clearBtn.setBounds(380, 215, 95, 32);
        backBtn.setBounds(35, 255, 110, 32);

        memoryAddBtn.setBounds(155, 255, 55, 32);
        memorySubBtn.setBounds(220, 255, 55, 32);
        memoryRecallBtn.setBounds(285, 255, 55, 32);
        memoryClearBtn.setBounds(350, 255, 55, 32);
        exitBtn.setBounds(415, 255, 95, 32);

        JLabel historyLabel = new JLabel("Calculation History");
        historyLabel.setBounds(560, 25, 180, 25);
        historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setLineWrap(true);
        JScrollPane historyScroll = new JScrollPane(historyArea);
        historyScroll.setBounds(555, 55, 260, 330);

        JButton clearHistoryBtn = new JButton("CLEAR HISTORY");
        clearHistoryBtn.setBounds(555, 395, 130, 32);
        clearHistoryBtn.addActionListener(e -> historyArea.setText(""));

        memoryInfo = new JButton("Memory: 0");
        memoryInfo.setBounds(695, 395, 120, 32);
        memoryInfo.setEnabled(false);

        JButton[] buttons = {addBtn, subBtn, mulBtn, divBtn, modBtn, powBtn, avgBtn,
                sqrtBtn, squareBtn, reciprocalBtn, signBtn, clearBtn, backBtn,
                memoryAddBtn, memorySubBtn, memoryRecallBtn, memoryClearBtn, exitBtn};
        for (JButton button : buttons) {
            button.addActionListener(this);
            panel.add(button);
        }

        panel.add(l1); panel.add(t1); panel.add(l2); panel.add(t2);
        panel.add(l3); panel.add(tresult);
        panel.add(historyLabel); panel.add(historyScroll);
        panel.add(clearHistoryBtn); panel.add(memoryInfo);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private double getNumber(JTextField field) {
        return Double.parseDouble(field.getText().trim());
    }

    private void setResult(double value) {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            tresult.setText("Invalid result");
        } else if (value == (long) value) {
            tresult.setText(String.valueOf((long) value));
        } else {
            tresult.setText(String.valueOf(value));
        }
    }

    private void addHistory(String text) {
        historyArea.append(text + "\n");
    }

    private void putResultInFirstField() {
        if (!tresult.getText().isEmpty() && !tresult.getText().startsWith("Error")
                && !tresult.getText().equals("Invalid Input") && !tresult.getText().equals("Invalid result")) {
            t1.setText(tresult.getText());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == exitBtn) { System.exit(0); }
        if (source == clearBtn) { t1.setText(""); t2.setText(""); tresult.setText(""); return; }
        if (source == backBtn) {
            String text = t1.getText();
            if (!text.isEmpty()) t1.setText(text.substring(0, text.length() - 1));
            return;
        }
        if (source == signBtn) {
            try { t1.setText(String.valueOf(-getNumber(t1))); }
            catch (NumberFormatException ex) { tresult.setText("Invalid Input"); }
            return;
        }
        if (source == memoryRecallBtn) { t1.setText(format(memory)); return; }
        if (source == memoryClearBtn) { memory = 0.0; updateMemoryDisplay(); return; }
        if (source == memoryAddBtn || source == memorySubBtn) {
            try {
                double value = getNumber(tresult.getText().isEmpty() ? t1 : tresult);
                if (source == memoryAddBtn) memory += value; else memory -= value;
                updateMemoryDisplay();
                return;
            } catch (NumberFormatException ex) { tresult.setText("Invalid Input"); return; }
        }

        try {
            double num1 = getNumber(t1);
            double result;
            String operation;

            if (source == sqrtBtn) {
                if (num1 < 0) { tresult.setText("Error: Negative input"); return; }
                result = calculator.squareRoot(num1); operation = "√" + format(num1);
            } else if (source == squareBtn) {
                result = calculator.square(num1); operation = format(num1) + "²";
            } else if (source == reciprocalBtn) {
                if (num1 == 0) { tresult.setText("Error: Divide by 0"); return; }
                result = calculator.reciprocal(num1); operation = "1/" + format(num1);
            } else {
                double num2 = getNumber(t2);
                if (source == addBtn) { result = calculator.add(num1, num2); operation = format(num1) + " + " + format(num2); }
                else if (source == subBtn) { result = calculator.subtract(num1, num2); operation = format(num1) + " - " + format(num2); }
                else if (source == mulBtn) { result = calculator.multiply(num1, num2); operation = format(num1) + " × " + format(num2); }
                else if (source == divBtn) { if (num2 == 0) { tresult.setText("Error: Divide by 0"); return; } result = calculator.divide(num1, num2); operation = format(num1) + " ÷ " + format(num2); }
                else if (source == modBtn) { if (num2 == 0) { tresult.setText("Error: Mod by 0"); return; } result = calculator.modulus(num1, num2); operation = format(num1) + " % " + format(num2); }
                else if (source == powBtn) { result = calculator.power(num1, num2); operation = format(num1) + " ^ " + format(num2); }
                else if (source == avgBtn) { result = calculator.average(num1, num2); operation = "avg(" + format(num1) + ", " + format(num2) + ")"; }
                else return;
            }

            setResult(result);
            addHistory(operation + " = " + tresult.getText());
        } catch (NumberFormatException ex) {
            tresult.setText("Invalid Input");
        }
    }

    private void updateMemoryDisplay() {
        memoryInfo.setText("Memory: " + format(memory));
    }

    private String format(double value) {
        return value == (long) value ? String.valueOf((long) value) : String.valueOf(value);
    }

    public static void main(String[] args) { new App(); }
}
