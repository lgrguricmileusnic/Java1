package hr.fer.zemris.java.gui.calc.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.DoubleBinaryOperator;

public class CalcModelImpl implements CalcModel{
    private boolean editable;
    private short sign;
    private String currentInput;
    private Double currentValue;
    private String frozenValue;
    private Double activeOperand;
    private DoubleBinaryOperator pendingOperation;
    private Set<CalcValueListener> listeners;
    public CalcModelImpl() {
        editable = true;
        sign = 1;
        currentInput = "";
        currentValue = 0.0;
        frozenValue = null;
        activeOperand = null;
    }

    /**
     * Prijava promatrača koje treba obavijestiti kada se
     * promijeni vrijednost pohranjena u kalkulatoru.
     *
     * @param l promatrač; ne smije biti <code>null</code>
     * @throws NullPointerException ako je za <code>l</code> predana vrijednost <code>null</code>
     */
    @Override
    public void addCalcValueListener(CalcValueListener l) {
        Objects.requireNonNull(l);
        if(listeners == null) {
            listeners = new HashSet<>();
        }
        listeners.add(l);
    }

    /**
     * Odjava promatrača s popisa promatrača koje treba
     * obavijestiti kada se promijeni vrijednost
     * pohranjena u kalkulatoru.
     *
     * @param l promatrač; ne smije biti <code>null</code>
     * @throws NullPointerException ako je za <code>l</code> predana vrijednost <code>null</code>
     */
    @Override
    public void removeCalcValueListener(CalcValueListener l) {
        Objects.requireNonNull(l);
        if(listeners == null) {
            return;
        }
        listeners.remove(l);
    }

    /**
     * Vraća trenutnu vrijednost koja je pohranjena u kalkulatoru.
     *
     * @return vrijednost pohranjena u kalkulatoru
     */
    @Override
    public double getValue() {
        return currentValue * sign;
    }

    /**
     * Upisuje decimalnu vrijednost u kalkulator. Vrijednost smije
     * biti i beskonačno odnosno NaN. Po upisu kalkulator
     * postaje needitabilan.
     *
     * @param value vrijednost koju treba upisati
     */
    @Override
    public void setValue(double value) {
        this.currentValue = Math.abs(value);
        currentInput = String.valueOf(currentValue);
        if(value < 0) {
            sign = -1;
        } else {
            sign = 1;
        }
        sign = (short) (value < 0 ? -1 : 1);
        freezeValue(String.valueOf(value));
        editable = false;
        notifyListeners();
    }

    /**
     * Vraća informaciju je li kalkulator editabilan (drugim riječima,
     * smije li korisnik pozivati metode {@link #swapSign()},
     * {@link #insertDecimalPoint()} te {@link #insertDigit(int)}).
     *
     * @return <code>true</code> ako je model editabilan, <code>false</code> inače
     */
    @Override
    public boolean isEditable() {
        return editable;
    }

    /**
     * Resetira trenutnu vrijednost na neunesenu i vraća kalkulator u
     * editabilno stanje.
     */
    @Override
    public void clear() {
        editable = true;
        currentInput = "";
        currentValue = 0.0;
        sign = 1;
        notifyListeners();
    }

    /**
     * Obavlja sve što i {@link #clear()}, te dodatno uklanja aktivni
     * operand i zakazanu operaciju.
     */
    @Override
    public void clearAll() {
        clear();
        activeOperand = null;
        pendingOperation = null;
    }

    /**
     * Mijenja predznak unesenog broja.
     *
     * @throws CalculatorInputException ako kalkulator nije editabilan
     */
    @Override
    public void swapSign() throws CalculatorInputException {
        if(!editable) throw new CalculatorInputException("Model not editable");
        sign = (short) -sign;
        freezeValue(null);
        notifyListeners();
    }

    /**
     * Dodaje na kraj trenutnog broja decimalnu točku.
     *
     * @throws CalculatorInputException ako nije još unesena niti jedna znamenka broja,
     *                                  ako broj već sadrži decimalnu točku ili ako kalkulator nije editabilan
     */
    @Override
    public void insertDecimalPoint() throws CalculatorInputException {
        if(!editable) throw new CalculatorInputException("Model not editable");
        if (currentInput.isEmpty()) throw new CalculatorInputException("No digits inputted before decimal point");
        if(currentInput.contains(".")) throw new CalculatorInputException("Decimal point already present");
        currentInput += ".";
        freezeValue(null);
        notifyListeners();
    }

    /**
     * U broj koji se trenutno upisuje na kraj dodaje poslanu znamenku.
     * Ako je trenutni broj "0", dodavanje još jedne nule se potiho
     * ignorira.
     *
     * @param digit znamenka koju treba dodati
     * @throws CalculatorInputException ako bi dodavanjem predane znamenke broj postao prevelik za konačan prikaz u tipu {@link Double}, ili ako kalkulator nije editabilan.
     * @throws IllegalArgumentException ako je <code>digit &lt; 0</code> ili <code>digit &gt; 9</code>
     */
    @Override
    public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
        if (!editable) throw new CalculatorInputException("Model not editable.");
        if(currentInput.equals("0") && digit == 0) return;
        try{
            Double temp = Double.parseDouble(currentInput + digit);
            if(temp.isInfinite() || temp.isNaN()) {
                throw new CalculatorInputException("Value cannot be represented as a Double");
            }
            currentValue = temp;
        }catch (NumberFormatException e) {
            throw new CalculatorInputException("Input doesn't match any supported number format: " + currentInput + digit);
        }
        if(currentInput.equals("0")) currentInput = "";
        currentInput = currentInput + digit;
        freezeValue(null);
        notifyListeners();
    }

    /**
     * Provjera je li upisan aktivni operand.
     *
     * @return <code>true</code> ako je aktivani operand upisan, <code>false</code> inače
     */
    @Override
    public boolean isActiveOperandSet() {
        return activeOperand != null;
    }

    /**
     * Dohvat aktivnog operanda.
     *
     * @return aktivni operand
     * @throws IllegalStateException ako aktivni operand nije postavljen
     */
    @Override
    public double getActiveOperand() throws IllegalStateException {
        if(!isActiveOperandSet()) throw new IllegalStateException("Active operand not set");
        return activeOperand;
    }

    /**
     * Metoda postavlja aktivni operand na predanu vrijednost.
     * Ako kalkulator već ima postavljen aktivni operand, predana
     * vrijednost ga nadjačava.
     *
     * @param activeOperand vrijednost koju treba pohraniti kao aktivni operand
     */
    @Override
    public void setActiveOperand(double activeOperand) {
        this.activeOperand = activeOperand;
    }

    /**
     * Uklanjanje zapisanog aktivnog operanda.
     */
    @Override
    public void clearActiveOperand() {
        activeOperand = null;
    }

    /**
     * Dohvat zakazane operacije.
     *
     * @return zakazanu operaciju, ili <code>null</code> ako nema zakazane operacije
     */
    @Override
    public DoubleBinaryOperator getPendingBinaryOperation() {
        return pendingOperation;
    }

    /**
     * Postavljanje zakazane operacije. Ako zakazana operacija već
     * postoji, ovaj je poziv nadjačava predanom vrijednošću.
     *
     * @param op zakazana operacija koju treba postaviti; smije biti <code>null</code>
     */
    @Override
    public void setPendingBinaryOperation(DoubleBinaryOperator op) {
        pendingOperation = op;
    }

    @Override
    public void freezeValue(String value) {
        frozenValue = value;
    }

    @Override
    public boolean hasFrozenValue() {
        return frozenValue != null;
    }

    @Override
    public String toString() {
        if(hasFrozenValue()) return frozenValue;
        if(currentInput.isEmpty()) return (sign > 0 ? "" : "-") + "0";
        return (sign > 0 ? "" : "-") + currentInput;
    }


    private void notifyListeners() {
        listeners.forEach(l -> l.valueChanged(this));
    }
}
