package com.example.calculatorx;

/**
 * 
 * @author Andy Klay <klay@fh-brandenburg.de>
 * @author MacYser
 *
 */
public class Rechner {

	public Double add(Double val1, Double val2) {
		return val1 + val2;
	}

	public Double sub(Double val1, Double val2) {
		return val1 - val2;
	}

	public Double mul(Double val1, Double val2) {
		return val1 * val2;
	}

	public Double div(Double val1, Double val2) {
		if (val2 > 0) {
			return val1 / val2;
		} else {
			throw new ArithmeticException("Division with 0 not defined!");
		}
	}
}
