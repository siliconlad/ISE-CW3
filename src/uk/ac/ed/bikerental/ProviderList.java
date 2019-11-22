package uk.ac.ed.bikerental;

import java.util.ArrayList;

public final class ProviderList {
    private static final ProviderList INSTANCE = new ProviderList();
    private ArrayList<Provider> providerList;

    private ProviderList() {}

    public void addProvider(Provider provider) {
        assert !providerList.contains(provider);

        providerList.add(provider);
    }

    public ArrayList<Quote> getQuotes(Query query) {
        ArrayList<Quote> quotes = new ArrayList<Quote>();
        Location queryLocation = query.getLocation();

        for (Provider provider : providerList) {
        }

        return quotes;
    }

    public static ProviderList getInstance() {
        return INSTANCE;
    }
}
