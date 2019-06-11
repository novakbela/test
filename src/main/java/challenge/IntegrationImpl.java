package main.java.challenge;

import com.flexionmobile.codingchallenge.integration.Integration;
import com.flexionmobile.codingchallenge.integration.Purchase;
import com.google.gson.Gson;
import main.java.challenge.exception.IntegrationException;
import main.java.challenge.model.PurchaseImpl;
import main.java.challenge.model.ResponseWrapper;
import main.java.challenge.util.IntegrationUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class IntegrationImpl implements Integration {

    IntegrationUtil util = new IntegrationUtil();

    @Override
    public Purchase buy(String s) {
        PurchaseImpl purchase = null;
        HttpURLConnection conn = null;
        String itemId = s;
        if (util.isNullOrBlank(itemId)) {
            util.logError(new IntegrationException(getProperty("error.buy.1")));
        } else {
            try {
                URL url = util.assembleUrl(getProperty("buy.path") + "/" + itemId);
                BufferedReader reader = openConnection(conn, url, "buy.http.method");
                purchase = new Gson().fromJson(reader, PurchaseImpl.class);
            } catch (IOException e) {
                util.logError(e);
            } finally {
                close(conn);
            }
        }
        return purchase;
    }

    @Override
    public List<Purchase> getPurchases() {
        ResponseWrapper purchases = null;
        HttpURLConnection conn = null;
        try {
            URL url = util.assembleUrl(getProperty("purchases.path"));
            BufferedReader reader = openConnection(conn, url, "purchases.http.method");
            purchases = new Gson().fromJson(reader, ResponseWrapper.class);
            System.out.println("Response purchase list: " + purchases);
        } catch (IOException e) {
            util.logError(e);
        } finally {
            close(conn);
        }
        return castImplToPurchases(purchases.getPurchases());
    }

    @Override
    public void consume(Purchase purchase) {
        HttpURLConnection conn = null;
        if (purchase == null || util.isNullOrBlank(purchase.getId())) {
            util.logError(new IntegrationException("Error: purchase or purchase id is null when calling consume()"));
        } else {
            try {
                URL url = util.assembleUrl( getProperty("consume.path") + "/" + purchase.getId());
                openConnection(conn, url, "consume.http.method");
            } catch (IOException e) {
                util.logError(e);
            } finally {
                close(conn);
            }
        }
    }

    private BufferedReader openConnection(HttpURLConnection conn, URL url, String method) throws IOException {
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(getProperty(method));
        if (conn.getResponseCode() != 200) {
            util.logError(conn);
        }
        return new BufferedReader(new InputStreamReader(conn.getInputStream()));
    }

    private List<Purchase> castImplToPurchases(List<PurchaseImpl> purchases) {
        return (List<Purchase>)(List<?>) purchases;
    }

    private String getProperty(String key) {
        return util.getProperty(key);
    }

    private void close(HttpURLConnection conn) {
        if (conn != null) {
            conn.disconnect();
        }
    }


}
