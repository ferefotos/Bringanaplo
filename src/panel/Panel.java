package panel;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Párbeszédpanelek
 * @author Fere
 */
public class Panel {

    /**
     * Hibaüzenet megjelenítése párbeszédpanelen
     * @param cim: a panel címe
     * @param uzenet: a panelen megjelenő üzenet 
     */
    public static void hiba(String cim, String uzenet) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(cim);
        alert.setHeaderText(null);
        alert.setContentText(uzenet);
        alert.showAndWait();
    }

    /**
     * Megerősítés kérése igen/nem párbeszédpanelen
     * @param cim: a panel címe
     * @param uzenet: a panelen megjelenő üzenet
     * @return: true, ha az Igen gombot választották,
     *          egyébként false
     */
    public static boolean igennem(String cim, String uzenet) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(cim);
        alert.setHeaderText(null);
        alert.setContentText(uzenet);
        ButtonType btIgen = new ButtonType("Igen");
        ButtonType btNem = new ButtonType("Nem");
        alert.getButtonTypes().setAll(btIgen, btNem);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == btIgen;
    }

}
