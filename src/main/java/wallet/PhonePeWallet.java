package wallet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Marker subclass
public class PhonePeWallet
        extends AbstractWallet {

    private static final Logger logger =
            LoggerFactory.getLogger(PhonePeWallet.class);

    public PhonePeWallet(double balance) {

        super(balance);

        logger.info(
                "PhonePe wallet created with balance: {}",
                balance
        );
    }
}