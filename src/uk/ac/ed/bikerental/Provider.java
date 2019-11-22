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

    public Provider(String name, Location location, BigDecimal depositRate) {
        this.name = name;
        this.location = location;
        this.depositRate = depositRate;
        this.partners = new HashSet<Provider>();
    }

    public Quote createQuote(Query query) {
        // TODO: need bookingList
        assert(false);
    }

    public String getName() {
        return this.name;
    }

    public Location getLocation() {
        return this.location;
    }

    public BigDecimal getDepositRate() {
        return this.depositRate;
    }

    public void addPartner(Provider newPartner) {
        assert !this.partners.contains(newPartner);

        this.partners.add(newPartner);
    }

    public Boolean isPartner(Provider partner) {
        return this.partners.contains(partner);
    }

    public void addBikeToStock(Bike bike) {
        assert !this.bikes.contains(bike);

        this.bikes.add(bike);
    }
}
