package uk.ac.ed.bikerental;

import java.util.ArrayList;
import java.math.BigDecimal;

public class ValuationPolicyFactory {
    private DefaultValuationPolicy defaultPolicyInstance;

    public ValuationPolicyFactory() {
        defaultPolicyInstance = new DefaultValuationPolicy();
    }

    public ValuationPolicy getValuationPolicy() {
        return defaultPolicyInstance;
    }
}
