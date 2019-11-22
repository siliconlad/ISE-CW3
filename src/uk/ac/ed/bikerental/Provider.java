package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.HashSet;

public class Provider {
    private String name;
    private Location location;
    private BigDecimal depositRate;
    private HashSet<Provider> partners;

    public Provider(String name, Location location, BigDecimal depositRate) {}

    public Quote createQuote(Query query) {}

    public String getName() {}

    public Location getLocation() {}

    public BigDecimal getDepositRate() {}

    public void addPartner() {}

    public Boolean isPartner(Provider partner) {

    }
}
