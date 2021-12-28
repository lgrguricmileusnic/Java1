package hr.fer.zemris.java.gui.calc.model;

import java.util.function.DoubleBinaryOperator;

public class CalcModelImpl implements CalcModel{

    /**
     * Prijava promatrača koje treba obavijestiti kada se
     * promijeni vrijednost pohranjena u kalkulatoru.
     *
     * @param l promatrač; ne smije biti <code>null</code>
     * @throws NullPointerException ako je za <code>l</code> predana vrijednost <code>null</code>
     */
    @Override
    public void addCalcValueListener(CalcValueListener l) {

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

    }

    /**
     * Vraća trenutnu vrijednost koja je pohranjena u kalkulatoru.
     *
     * @return vrijednost pohranjena u kalkulatoru
     */
    @Override
    public double getValue() {
        return 0;
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
        return false;
    }

    /**
     * Resetira trenutnu vrijednost na neunesenu i vraća kalkulator u
     * editabilno stanje.
     */
    @Override
    public void clear() {

    }

    /**
     * Obavlja sve što i {@link #clear()}, te dodatno uklanja aktivni
     * operand i zakazanu operaciju.
     */
    @Override
    public void clearAll() {

    }

    /**
     * Mijenja predznak unesenog broja.
     *
     * @throws CalculatorInputException ako kalkulator nije editabilan
     */
    @Override
    public void swapSign() throws CalculatorInputException {

    }

    /**
     * Dodaje na kraj trenutnog broja decimalnu točku.
     *
     * @throws CalculatorInputException ako nije još unesena niti jedna znamenka broja,
     *                                  ako broj već sadrži decimalnu točku ili ako kalkulator nije editabilan
     */
    @Override
    public void insertDecimalPoint() throws CalculatorInputException {

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

    }

    /**
     * Provjera je li upisan aktivni operand.
     *
     * @return <code>true</code> ako je aktivani operand upisan, <code>false</code> inače
     */
    @Override
    public boolean isActiveOperandSet() {
        return false;
    }

    /**
     * Dohvat aktivnog operanda.
     *
     * @return aktivni operand
     * @throws IllegalStateException ako aktivni operand nije postavljen
     */
    @Override
    public double getActiveOperand() throws IllegalStateException {
        return 0;
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

    }

    /**
     * Uklanjanje zapisanog aktivnog operanda.
     */
    @Override
    public void clearActiveOperand() {

    }

    /**
     * Dohvat zakazane operacije.
     *
     * @return zakazanu operaciju, ili <code>null</code> ako nema zakazane operacije
     */
    @Override
    public DoubleBinaryOperator getPendingBinaryOperation() {
        return null;
    }

    /**
     * Postavljanje zakazane operacije. Ako zakazana operacija već
     * postoji, ovaj je poziv nadjačava predanom vrijednošću.
     *
     * @param op zakazana operacija koju treba postaviti; smije biti <code>null</code>
     */
    @Override
    public void setPendingBinaryOperation(DoubleBinaryOperator op) {

    }
}
