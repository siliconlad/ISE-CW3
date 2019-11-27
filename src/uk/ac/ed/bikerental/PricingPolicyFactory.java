package uk.ac.ed.bikerental;

import java.util.ArrayList;
import java.math.BigDecimal;

public class PricingPolicyFactory {
    private MultidayPolicy pricingPolicyInstance;
    private DefaultPricingPolicy defaultPolicyInstance;

    public void PricingPolicyFactory() {
        defaultPolicyInstance = new DefaultPricingPolicy();
    }

    public PricingPolicy getPricingPolicy() {
        if (pricingPolicyInstance == null) {
            return defaultPolicyInstance;
        }
        return pricingPolicyInstance;
    }

    public void setupPricingPolicy(ArrayList<BigDecimal> discountPolicy) {
        pricingPolicyInstance = new MultidayPolicy(discountPolicy);
    }
}
