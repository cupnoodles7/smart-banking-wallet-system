package wallet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaytmWallet
        extends AbstractWallet {

    // Logger object
    private static final Logger logger =
            LoggerFactory.getLogger(PaytmWallet.class);

    public PaytmWallet(double balance) {

        super(balance);

        logger.info(
                "Paytm wallet created with balance: {}",
                balance
        );
    }
}