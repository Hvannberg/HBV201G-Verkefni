/*
Ebba Þóra Hvannberg ebba@hi.is
 */
package is.hi.vinnsla;

import is.hi.utlit.Mynd;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Er gagnaklasi fyrir töfluna yfir veðurspár
 *
 * @author Ebba Þóra Hvannberg ebba@hi.is
 *
 * Háskóli Íslands
 */
public class Vedur {

    private final SimpleStringProperty w; // veðurlýsing
    private final SimpleStringProperty F; // Vindhraði
    private final SimpleStringProperty T; // Hitastig
    private final SimpleStringProperty D; // Vindátt
    private final ObjectProperty<LocalDate> dagsetning;
    private final ObjectProperty<LocalTime> timi;
    private final ObjectProperty<Mynd> myndAfVedri; // Mynd af veðurlýsingu

    private final static ArrayList<Mynd> myndir = new ArrayList<Mynd>(Arrays.asList(Mynd.Alskyjad, Mynd.Heidskirt,
            Mynd.Snjokoma, Mynd.Lettskyjad, Mynd.LitilshattarSnjokoma, Mynd.Skyjad,
            Mynd.LitilshattarSlydda, Mynd.Snjoel));

    public ObjectProperty<Mynd> getMyndAfVedri() {
        return myndAfVedri;
    }

    public void setMyndAfVedri(Mynd m) {
        myndAfVedri.set(m);
    }

    public final ObjectProperty<Mynd> myndAfVedriProperty() {
        return this.myndAfVedri;
    }

    public Vedur(Nidurstodur.AllarStodvar.Vedurstod.SpaStodvar.Vedurspa s) {
        this.w = new SimpleStringProperty(s.getW());
        this.F = new SimpleStringProperty(String.valueOf(s.getF()));
        this.T = new SimpleStringProperty(String.valueOf(s.getT()));
        this.D = new SimpleStringProperty(String.valueOf(s.getD()));

        DateTimeFormatter formatter
                = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate dags = LocalDate.parse(s.getFtime(), formatter);
        this.dagsetning = new SimpleObjectProperty<>(dags);
        LocalTime t = LocalTime.parse(s.getFtime(), formatter);
        this.timi = new SimpleObjectProperty<>(t);
        this.myndAfVedri = new SimpleObjectProperty<>();

        Mynd m = finnaMynd(s.getW());
        if (m != null) {
            this.myndAfVedri.set(m);
        }
    }

    public String getW() {
        return w.get();
    }

    public String getF() {
        return F.get();
    }

    public String getT() {
        return T.get();
    }

    public String getD() {
        return D.get();
    }

    public LocalDate getDagsetning() {
        return dagsetning.get();
    }

    public LocalTime getTimi() {
        return timi.get();
    }

    /**
     * Leitar að mynd sem er merkt með strengnum w 
     * @param w lýsing á mynd 
     * @return skilar viðeigandi mynd 
     */
    private Mynd finnaMynd(String w) {
        for (Mynd m : myndir) {
            if (m.getGildi().equals(w)) {
                return m;
            }
        }
        return null;
    }
}
