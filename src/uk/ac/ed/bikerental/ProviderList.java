package uk.ac.ed.bikerental;

import java.util.Collection;

public final class ProviderList {
    private static final ProviderList INSTANCE = new ProviderList();
    private Collection<Provider> providerList;

    private ProviderList() {}

    public void addProvider(Provider provider) {
        assert !providerList.contains(provider);

        providerList.add(provider);
    }

    public Collection<Quote> getQuotes(Query query) {
        Collection<Quote> quotes;
        for (Provider provider : providerList) {
            Quote quote = provider.createQuote(query);
            quotes.add(quote);
        }
        return quotes;
    }

    public static ProviderList getInstance() {
        return INSTANCE;
    }
}
