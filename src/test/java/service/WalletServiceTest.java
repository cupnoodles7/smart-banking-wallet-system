package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import wallet.PaytmWallet;
import wallet.PhonePeWallet;
import wallet.WalletOperations;


public class WalletServiceTest {

    private final WalletService walletService = new WalletService();

    @Test
    public void testLinkPaytmWallet() {
        WalletOperations wallet = walletService.linkWallet("cust123", "PAYTM");
        assertNotNull(wallet);
        assertInstanceOf(PaytmWallet.class, wallet);
    }

    public void testLinkPhonePeWallet() {
        WalletOperations wallet = walletService.linkWallet("cust123", "PHONEPE");
        assertNotNull(wallet);
        assertInstanceOf(PhonePeWallet.class, wallet);
    }

    public void testLinkWalletIsCaseSensitive() {
        WalletOperations wallet1 = walletService.linkWallet("cust123", "paytm");
        WalletOperations wallet2 = walletService.linkWallet("cust123", "PAYTM");
        assertSame(wallet1, wallet2);
    }
    

    @Test
    public void testLinkSameWalletTwiceReturnsSameInstance() {
        WalletOperations wallet1 = walletService.linkWallet("cust123", "PAYTM");
        WalletOperations wallet2 = walletService.linkWallet("cust123", "PAYTM");
        assertSame(wallet1, wallet2);

    }

    @Test
    void testDifferentCustomersGetDifferentWallets(){
        WalletOperations w1 = walletService.linkWallet("C1", "PAYTM");
        WalletOperations w2 = walletService.linkWallet("C2", "PAYTM");

        assertNotSame(w1, w2);
    }

    @Test
    void testSameCustomerDifferentTypesGetDifferentWallets() {
        WalletOperations paytm   = walletService.linkWallet("C1", "PAYTM");
        WalletOperations phonepe = walletService.linkWallet("C1", "PHONEPE");

        assertNotSame(paytm, phonepe);
    }

    @Test
    void testLinkUnknownTypeThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> walletService.linkWallet("C1", "GPAY"));
    }

    //find wallet tests
    @Test
    void testFindWalletReturnsLinkedInstance(){
        WalletOperations linked = walletService.linkWallet("C1", "PAYTM");
        WalletOperations found  = walletService.findWallet("C1", "PAYTM");

        assertSame(linked, found);
    }

    @Test
    void testFindWalletIsCaseInsensitive(){
        WalletOperations linked = walletService.linkWallet("C1", "paytm");
        WalletOperations found  = walletService.findWallet("C1", "PAYTM");

        assertSame(linked, found);
    }


    @Test
    void testFindWalletNotLinkedThrows() {
        assertThrows(IllegalStateException.class,
                () -> walletService.findWallet("C1", "PAYTM"));
    }


    //getAllWallets tests
    @Test
    void testGetAllWalletsEmptyInitially() {
        assertTrue(walletService.getAllWallets().isEmpty());
    }

    @Test
    void testGetAllWalletsCountsLinkedWallets() {
        walletService.linkWallet("C1", "PAYTM");
        walletService.linkWallet("C1", "PHONEPE");
        walletService.linkWallet("C2", "PAYTM");

        assertEquals(3, walletService.getAllWallets().size());
    }

    @Test
    void testGetAllWalletsIsUnmodifiable() {
        assertThrows(UnsupportedOperationException.class,
                () -> walletService.getAllWallets().put("x", null));
    }
}
