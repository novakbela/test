package main.java.challenge.model;

import java.util.List;

public class ResponseWrapper {

    private List<PurchaseImpl> purchases;

    public List<PurchaseImpl> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<PurchaseImpl> purchases) {
        this.purchases = purchases;
    }

    @Override
    public String toString() {
        return "ResponseWrapper{" +
                "purchases=" + purchases +
                '}';
    }
}
