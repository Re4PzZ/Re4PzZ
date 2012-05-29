import org.powerbot.game.api.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Environment;

/**
 * Fills vials at the GE
 * Start anywhere in the grand exchange
 *
 * @author xcvd
 */
@Manifest(name = "Vial Filler", description = "Fills vials. By xcvd", version = 1.5d, authors = {"xcvd"}, website="http://www.powerbot.org/community/topic/672724-xcvds-vial-filler-40-60k-an-hour/")
public class VialFiller extends ActiveScript{

    @Override
    protected void setup() {
        log.info("" + Environment.getStorageDirectory());
        stop();
    }

}