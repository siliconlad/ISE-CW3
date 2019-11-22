package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Collection;

public class Provider {
    private String name;
    private Location location;
    private BigDecimal depositRate;
    private HashSet<Provider> partners;
    private Collection<Bike> bikes;

    public Provider(String name, Location location, BigDecimal depositRate) {}

    public Quote createQuote(Query query) {}

    public String getName() {}

    public Location getLocation() {}

    public BigDecimal getDepositRate() {}

    public void addPartner() {}

    public Boolean isPartner(Provider partner) {
    }
    public void addBikeToStock(Bike bike) {
        assert !this.bikes.contains(bike);

        this.bikes.add(bike);
    }
}
