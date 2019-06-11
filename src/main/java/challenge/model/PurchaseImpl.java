package main.java.challenge.model;

import com.flexionmobile.codingchallenge.integration.Purchase;

public class PurchaseImpl implements Purchase {

    private String id;
    private boolean consumed;
    private String itemId;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean getConsumed() {
        return consumed;
    }

    @Override
    public String getItemId() {
        return itemId;
    }

    @Override
    public String toString() {
        return "PurchaseImpl{" +
                "id='" + id + '\'' +
                ", consumed=" + consumed +
                ", itemId='" + itemId + '\'' +
                '}';
    }
}
