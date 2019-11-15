package uk.ac.ed.bikerental;

/**
 * A location specified by a postcode and a street address.
 */
public class Location {
    private String postcode;
    private String address;

    public Location(String postcode, String address) {
        assert postcode.length() >= 6;
        this.postcode = postcode;
        this.address = address;
    }

    public boolean isNearTo(Location other) {
        String otherPostCode = other.getPostcode();
        String otherSub = otherPostCode.substring(0,2);
        String thisSub = this.postcode.substring(0,2);

        if (thisSub.startsWith(otherSub)) {
            return true;
        }
        return false;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getAddress() {
        return address;
    }

    // You can add your own methods here
}
