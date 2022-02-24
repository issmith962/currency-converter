package main;

import java.util.Objects;

public class Currency {
    private String currencyCode; 
    private double exchangeRate; 

    public Currency(String currencyCode, double exchangeRate) {
        this.currencyCode = currencyCode; 
        this.exchangeRate = exchangeRate; 
    }

    public String getCurrencyCode() {
        return currencyCode; 
    }

    public double getExchangeRate() {
        return exchangeRate; 
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Currency))
            return false;
        Currency other = (Currency) obj;
        return this.currencyCode.equals(other.getCurrencyCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.currencyCode);
    }
}
