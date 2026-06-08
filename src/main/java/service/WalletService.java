package service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import wallet.PaytmWallet;
import wallet.PhonePeWallet;
import wallet.WalletOperations;


public class WalletService {

    private final Map<String, WalletOperations> walletRegistry = new LinkedHashMap<>();

    public WalletOperations linkWallet(String customerId, String type) {

        String key = key(customerId, type);

        WalletOperations existing = walletRegistry.get(key);
        if (existing != null) {
            return existing;
        }

        WalletOperations wallet;
        if ("PAYTM".equalsIgnoreCase(type)) {
            wallet = new PaytmWallet(0);
        } else if ("PHONEPE".equalsIgnoreCase(type)) {
            wallet = new PhonePeWallet(0);
        } else {
            throw new IllegalArgumentException(
                    "Unknown wallet type: '" + type + "'. Use PAYTM or PHONEPE.");
        }

        walletRegistry.put(key, wallet);
        System.out.println("Linked new " + type.toUpperCase()
                + " wallet for customer " + customerId);
        return wallet;
    }

    public WalletOperations findWallet(String customerId, String type) {

        WalletOperations wallet = walletRegistry.get(key(customerId, type));
        if (wallet == null) {
            throw new IllegalStateException(
                    "No " + type.toUpperCase()
                            + " wallet linked to customer " + customerId);
        }
        return wallet;
    }

    public Map<String, WalletOperations> getAllWallets() {
        return Collections.unmodifiableMap(walletRegistry);
    }

    private String key(String customerId, String type) {
        return customerId + ":" + type.toUpperCase();
    }
}
