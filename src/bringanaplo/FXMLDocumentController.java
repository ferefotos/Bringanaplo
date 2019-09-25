package bringanaplo;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.TreeMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;
import panel.Panel;
import static panel.Panel.hiba;
import static panel.Panel.igennem;


/**
 *
 * @author Fere
 */


public class FXMLDocumentController implements Initializable {
    
    @FXML
    private DatePicker dpTol;

    @FXML
    private DatePicker dpIg;

    @FXML
    private ComboBox<String> cbxHonap;

    @FXML
    private DatePicker dpDatum;

    @FXML
    private TextField txtTav;

    @FXML
    private TextField txtOra;

    @FXML
    private TextField txtPerc;
    
    @FXML
    private Button btnThisYear;
    
    @FXML
    private Label lblStat1;
    
    @FXML
    private Label lblStat2;

    @FXML
    private TableView<Honap> tblHonapok;

    @FXML
    private TableColumn<Honap, Integer> oEv;

    @FXML
    private TableColumn<Honap, String> oHonap;

    @FXML
    private TableColumn<Honap, Integer> oTav;

    @FXML
    private TableColumn<Honap, Integer> oOssztav;

    @FXML
    private TableColumn<Honap, String> oIdo;

    @FXML
    private TableColumn<Honap, Double> oAVG;

    @FXML
    private TableColumn<Honap, String> oNote;

    @FXML
    private ComboBox<String> cbxRel;

    @FXML
    private TextField txtTavSz;

    @FXML
    private TextField txtNote;

    @FXML
    void hozzaad() {
        LocalDate d=dpDatum.getValue();
        if(d == null || d.isAfter(LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()))){
            Panel.hiba("Hiba!", "Hibás dátum!");
            dpDatum.requestFocus();
            return;
        }
        String s = txtTav.getText(); 
        int tav;
        try {
            tav = Integer.parseInt(s);  
            if (tav < 0 || tav > 2500) {
                Panel.hiba("Hiba!", "Hibás táv!");
                txtTav.requestFocus();
                return;
            }
        } catch (NumberFormatException ex) { 
            Panel.hiba("Hiba!", "Hibás táv!");
            txtTav.requestFocus();
            return;
        }
        s = txtOra.getText(); 
        int ora;
        try {
            ora = Integer.parseInt(s);  
            if (ora < 0 || ora > 200) {
                Panel.hiba("Hiba!", "Hibás idő!");
                txtOra.requestFocus();
                return;
            }
        } catch (NumberFormatException ex) { 
            Panel.hiba("Hiba!", "Hibás idő!");
            txtOra.requestFocus();
            return;
        }
        s = txtPerc.getText(); 
        int perc;
        try {
            perc = Integer.parseInt(s);  
            if (perc < 0 || perc > 59) {
                Panel.hiba("Hiba!", "Hibás idő!");
                txtPerc.requestFocus();
                return;
            }
        } catch (NumberFormatException ex) { 
            Panel.hiba("Hiba!", "Hibás idő!");
            txtPerc.requestFocus();
            return;
        }
        if(tav==0 && ora+perc!=0 /*|| ora+perc==0 && tav!=0 || tav/(ora+perc/60)>60*/){
            Panel.hiba("Hiba!", "Nem reális út és idő adatok!");
                txtOra.requestFocus();
                return;
        }
        
        String note=txtNote.getText();
        if (note.length()>50) {
            hiba("Hiba!", "Túl sok karakter!");
            txtNote.requestFocus();
            return;
        }
           
        s = ab.hozzaad(Date.valueOf(d), tav, timeFormat(ora, perc), note); 
        if (s.isEmpty()) { 
            beolvas(); 
            uj(); 
        } else {
            if(s.contains("Duplicate entry"))
                Panel.hiba("Hiba!", "Erre a hónapra már van bejegyzés! " +s.substring(17,24));
            else    
                Panel.hiba("Hiba!", s); 
        }
        
    }

    @FXML
    void modosit() {
        int index = tblHonapok.getSelectionModel().getSelectedIndex();
        if (index == -1) 
            return;
        Date sorDatum = tblHonapok.getItems().get(index).getDatum(); //A kijelölt sorhoz tartozó dátum
        
        LocalDate d=dpDatum.getValue(); 
        if(d == null || d.isAfter(LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()))){
            Panel.hiba("Hiba!", "Hibás dátum!");
            dpDatum.requestFocus();
            return;
        }
        String s = txtTav.getText(); 
        int tav;
        try {
            tav = Integer.parseInt(s);  
            if (tav < 0 || tav > 2500) {
                Panel.hiba("Hiba!", "Hibás táv!");
                txtTav.requestFocus();
                return;
            }
        } catch (NumberFormatException ex) { 
            Panel.hiba("Hiba!", "Hibás táv!");
            txtTav.requestFocus();
            return;
        }
        s = txtOra.getText(); 
        int ora;
        try {
            ora = Integer.parseInt(s);  
            if (ora < 0 || ora > 200) {
                Panel.hiba("Hiba!", "Hibás idő!");
                txtOra.requestFocus();
                return;
            }
        } catch (NumberFormatException ex) { 
            Panel.hiba("Hiba!", "Hibás idő!");
            txtOra.requestFocus();
            return;
        }
        s = txtPerc.getText(); 
        int perc;
        try {
            perc = Integer.parseInt(s);  
            if (perc < 0 || perc > 59) {
                Panel.hiba("Hiba!", "Hibás idő!");
                txtPerc.requestFocus();
                return;
            }
        } catch (NumberFormatException ex) { 
            Panel.hiba("Hiba!", "Hibás idő!");
            txtPerc.requestFocus();
            return;
        }
        if(tav==0 && ora+perc!=0 /*|| ora+perc==0 && tav!=0 || tav/(ora+perc/60)>60*/){
            Panel.hiba("Hiba!", "Nem reális út és idő adatok!");
                txtOra.requestFocus();
                return;
        }
        
        String note=txtNote.getText();
        if (note.length()>50) {
            hiba("Hiba!", "Túl sok karakter!");
            txtNote.requestFocus();
            return;
        }
        
        s = ab.modosit(Date.valueOf(d), tav, timeFormat(ora, perc), note, sorDatum); 
        if (s.isEmpty()) { 
            beolvas(); 
            for (int i = 0; i < tblHonapok.getItems().size(); i++) { 
                if (tblHonapok.getItems().get(i).getDatum().toLocalDate().equals(d)) {
                    tblHonapok.getSelectionModel().select(i);
                    break; 
                }
            } 
 
        } else {
            if(s.contains("Duplicate entry"))
                Panel.hiba("Hiba!", "Erre a hónapra már van bejegyzés! " +s.substring(17,24));
            else    
                Panel.hiba("Hiba!", s); 
        }

    }

    @FXML
    void torol() {
        int index = tblHonapok.getSelectionModel().getSelectedIndex();
        if (index == -1) 
            return;
        if (!igennem("Törlés", "Biztosan törölni szeretnéd ezt a rekordot?")) 
            return;
        Date sorDatum = tblHonapok.getItems().get(index).getDatum(); //A kijelölt sorhoz tartozó dátum
        String v = ab.torol(sorDatum);
        if (v.isEmpty())  
            beolvas();
        else 
            hiba("Hiba!",v);
    }
    
    @FXML
    void uj() {
        dpDatum.setValue(LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth())); //Az aktuális hónap utolsó napja
        txtTav.clear();
        txtOra.setText("00");
        txtPerc.setText("00");
        txtNote.clear();
    }
    
    @FXML
    void reset() {
        cbxHonap.setValue("");
        txtTavSz.setText("0");
    }
    
    @FXML
    void minusYear() {
        dpTol.setValue(dpTol.getValue().minusYears(1));
        dpIg.setValue(dpIg.getValue().minusYears(1));
    }

    @FXML
    void plusYear() {
        dpTol.setValue(dpTol.getValue().plusYears(1));
        dpIg.setValue(dpIg.getValue().plusYears(1));
    }
    // Az elmúlt 1 évet állítja ba
    @FXML
    void lastYear() {
        dpIg.setValue(LocalDate.now().minusMonths(1));
        dpTol.setValue(LocalDate.now().minusYears(1));
    }
    /*
    * Aktuális év 01.01-12.31-ig.
    * Kezdő dátumnak valójában 01.31-et állít be, de miután meghívásra kerül a beolvas() metódus, 
    * abban a kezdő dátum mindig a beállított hónap 1. napjára van állítva. 
    */
    @FXML
    void thisYear() {
        dpIg.setValue(LocalDate.now().with(TemporalAdjusters.lastDayOfYear()));
        dpTol.setValue(dpIg.getValue().minusMonths(11)); 
    }
    //Az összes adat lekérdezése. Az első bejegyzés dátumától az elmúlt hónapig       
    @FXML
    void allYear() {
        dpTol.setValue(ab.elsoDatum().toLocalDate());
        //dpTol.setValue(LocalDate.of(2013, 1, 1));
        dpIg.setValue(LocalDate.now().minusMonths(1));
    }

    @FXML
    void Import() {
        FileChooser fajlvalaszto = new FileChooser();
        fajlvalaszto.setTitle("Importálás");
        ExtensionFilter szuro = new ExtensionFilter("CSV (pontosvesszővel tagolt)", "*.csv");
        fajlvalaszto.getExtensionFilters().add(szuro);
        fajlvalaszto.setInitialDirectory(new File(System.getProperty("user.dir")));
        Window ablak = tblHonapok.getScene().getWindow();
        File f = fajlvalaszto.showOpenDialog(ablak);
        if(f != null) {
            try (Scanner be = new Scanner(f.getAbsoluteFile(), "UTF-8")){ 
                String hibak="";
                while (be.hasNextLine()) {
                    String sor []=be.nextLine().split(";");
                    Date datum = Date.valueOf(sor[0]);
                    String note="";
                    if(sor.length==4)
                        note=sor[3];
                    String h = ab.hozzaad(datum, Integer.parseInt(sor[1]), sor[2], note); 
                    if(!h.isEmpty()){
                        if(h.contains("Duplicate entry"))
                            hibak += "Erre a hónapra már van bejegyzés! " + h.substring(17,24) +" \n";
                        else    
                            Panel.hiba("Hiba!", h); 
                    }
                }
                beolvas();
                if(!hibak.isEmpty())
                    hiba("Importálás OK.", "Az Importálás végrehajtva az alábbi hibás sorok kivételével: \n\n" +hibak);
                else 
                    hiba("Importálás OK.", "Az importálás sikeresen végrehajtva. Hiba: 0");
                
            } catch(IOException ex){
                hiba("Hiba", ex.getMessage());
            }
        }    

    }
   
    @FXML
    void export() {
        FileChooser fajlvalaszto = new FileChooser();
        //ablak címe
        fajlvalaszto.setTitle("Exportálás");
        //fájltípus
        FileChooser.ExtensionFilter szuro = new FileChooser.ExtensionFilter("CSV (pontosvesszővel tagolt)", "*.csv");
        fajlvalaszto.getExtensionFilters().add(szuro);
        //induló mappa
        fajlvalaszto.setInitialDirectory(new File(System.getProperty("user.dir")));
        //hozzá tartozó ablak
        Window ablak = tblHonapok.getScene().getWindow();
        //megnyitás
        File f = fajlvalaszto.showSaveDialog(ablak);
        //ha kiválasztottak egy fájlt, kiírás:
        if(f != null) {
            try (PrintWriter ki = new PrintWriter(f.getAbsoluteFile(), "UTF-8")){
                for (Honap h : tblHonapok.getItems()) {
                    ki.println(h.getDatum() + ";" + h.getTav() + ";" + h.getIdo() + ";" +h.getNote()); 
                }           
            } catch(IOException ex){
                hiba("Hiba", ex.getMessage());
            }
        }    
    }

    DB ab = new DB();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        oEv.setCellValueFactory(new PropertyValueFactory<>("ev")); 
        oHonap.setCellValueFactory(new PropertyValueFactory<>("honap")); 
        oTav.setCellValueFactory(new PropertyValueFactory<>("tav")); 
        oOssztav.setCellValueFactory(new PropertyValueFactory<>("osszTav")); 
        oIdo.setCellValueFactory(new PropertyValueFactory<>("ido")); 
        oAVG.setCellValueFactory(new PropertyValueFactory<>("AVG")); 
        oNote.setCellValueFactory(new PropertyValueFactory<>("note")); 

        dpTol.setValue(LocalDate.now().with(TemporalAdjusters.firstDayOfYear()));
        dpIg.setValue(LocalDate.now().with(TemporalAdjusters.lastDayOfYear()));
        dpDatum.setValue(LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()));
        txtOra.setText("00");
        txtPerc.setText("00");     
        cbxHonap.getItems().addAll("","Január","Február","Március","Április","Május","Június",
                "Július","Augusztus","Szeptember","Október","November", "December");
        cbxHonap.setValue("");
        cbxRel.getItems().addAll(" > mint"," < mint");
        cbxRel.setValue(" > mint");
        txtTavSz.setText("0");
        btnThisYear.setText("" + LocalDate.now().getYear());
        
        beolvas();
        
        dpTol.valueProperty().addListener(e -> beolvas());
        dpIg.valueProperty().addListener(e -> beolvas());
        cbxHonap.valueProperty().addListener(e -> beolvas());
        cbxRel.valueProperty().addListener(e -> beolvas());
        txtTavSz.textProperty().addListener(e -> beolvas());
        dpDatum.valueProperty().addListener(e -> setLastDay());
        
        tblHonapok.getSelectionModel().selectedIndexProperty().addListener(
                (o,regi,uj) -> tablabol(uj.intValue()) 
        );
    }
    /*******************************************************************************/
    private void tablabol(int i){
        if (i == -1)
            return;
        Honap h = tblHonapok.getItems().get(i);
        dpDatum.setValue(h.getDatum().toLocalDate());
        txtTav.setText("" + h.getTav());
        txtOra.setText("" + h.getIdo().substring(0,2));
        txtPerc.setText("" + h.getIdo().substring(3));
        txtNote.setText(h.getNote());
   }
    private String timeFormat(int ora, int perc){
        String ido=ora+":";
        if(ora<10)
            ido="0"+ido;
        if(perc<10)
            ido+="0";
        ido+=perc;
        
        return ido;
    }
    /*Bármilyen dátumot választunk, a választott hónap utolsó napját állítja be. */
    private void setLastDay(){
        if(dpDatum.getValue().getDayOfMonth() != dpDatum.getValue().lengthOfMonth())
            dpDatum.setValue(dpDatum.getValue().withDayOfMonth(dpDatum.getValue().lengthOfMonth()));   
    }
    //A hónap száma és neve párosításához. A hónap (magyar) neve alapján megkaphatjuk az hányadik hónap
    private TreeMap<String, Integer> honapszam = new TreeMap<String, Integer>(){{
        put("Január", 1); put("Február", 2); put("Március", 3); put("Április", 4); put("Május", 5); put("Június", 6);
        put("Július", 7); put("Augusztus", 8); put("Szeptember", 9); put("Október", 10); put("November", 11); put("December", 12);
        }};

    private void beolvas(){
        //Bármilyen dátumot választunk, a választott hónap 1. napját állítja be
        if(dpTol.getValue().getDayOfMonth()!=1)
            dpTol.setValue(dpTol.getValue().withDayOfMonth(1));
        //Bármilyen dátumot választunk, a választott hónap utolsó napját állítja be
        if(dpIg.getValue().getDayOfMonth() != dpIg.getValue().lengthOfMonth())
            dpIg.setValue(dpIg.getValue().withDayOfMonth(dpIg.getValue().lengthOfMonth()));         
        
        String filter="AND tav";
        if(cbxRel.getValue().equals(" > mint"))
            filter+=">";
        else
            filter+="<";
        if(txtTavSz.getText().isEmpty())  
            filter+=0;
        else
            filter+=Integer.parseInt(txtTavSz.getText());
        
        if(!cbxHonap.getValue().isEmpty())
            filter+=" AND MONTH(datum)=" + honapszam.get(cbxHonap.getValue());
               
        String s="SELECT datum, tav, ido, note FROM naplo "
               + "WHERE datum BETWEEN '" 
               + dpTol.getValue() + "' AND '" +dpIg.getValue()
               + "' " + filter 
               + " ;";
       
        ab.beolvas(tblHonapok.getItems(), s);
        
        stat();
         
    }
    // Statisztika
    private void stat(){
        // A statisztika fejlécen a beállított szűrési feltételek megjelenítéséhez
        String honap="";
        if(!cbxHonap.getValue().isEmpty())
            honap=" / " + cbxHonap.getValue();
        String tavSzuro=""; 
        if(!txtTavSz.getText().equals("0")){
            if(cbxRel.getValue().equals("több"))
                tavSzuro = "  havi táv > " +txtTavSz.getText() +" km";
            else
                tavSzuro = "  havi táv < " +txtTavSz.getText() +" km";
        }
        // Statisztika fejléc kiírása  
        lblStat1.setText("Statisztika: "+ String.format("%s", dpTol.getValue()).replace('-', '.') + " - " 
                                        + String.format("%s", dpIg.getValue()).replace('-', '.') + honap +"\n" 
                                        + tavSzuro);
        //csak ha van lekért adat
        if(tblHonapok.getItems().size() != 0){
            //Távolság és átlagsebesség min. és max. kiválasztás
            Honap h=tblHonapok.getItems().get(0);
            int minTav = 10000;
            String d_minT = h.getEv() + " " + h.getHonap();
            int maxTav = h.getTav();;
            String d_maxT = "";
            double minAVG = 100;
            String d_minA = h.getEv() + " " + h.getHonap();
            double maxAVG = h.getAVG();
            String d_maxA = "";

            for (int i = 0; i < tblHonapok.getItems().size(); i++) {
                h=tblHonapok.getItems().get(i);
                if(minTav > h.getTav()){
                    minTav = h.getTav();
                    d_minT = h.getEv() + " " +h.getHonap();
                }
                if(maxTav < h.getTav()){
                    maxTav = h.getTav();
                    d_maxT = h.getEv() + " " +h.getHonap();
                }
                if(minAVG > h.getAVG()){
                    minAVG = h.getAVG();
                    d_minA = h.getEv() + " " +h.getHonap();
                }
                if(maxAVG < h.getAVG()){
                    maxAVG = h.getAVG();
                    d_maxA = h.getEv() + " " +h.getHonap();
                } 
            }
            /* Ha nincs rögzítve a vizsgált időszakban idő adat, akkor az átlagsebesség 0.0 minden hónapban. 
              Ekker azt írja ki, hogy nincs adat */ 
            if(maxAVG == 0){
                d_minA = " n. a. ";
                d_maxA = " n. a. ";
            }

            /* Éves összesítés: 
             * TreeMap-ben évenként összesíti a megtett távot és időt.*/
            TreeMap<Integer, Integer> ev_tav=new TreeMap<>();
            TreeMap<Integer, Integer> ev_perc=new TreeMap<>();

            for (Honap ho : tblHonapok.getItems()) {
                int ev = ho.getEv();
                int tav = ho.getTav();
                if(ev_tav.get(ev) != null)
                    tav += ev_tav.get(ev);
                ev_tav.put(ev, tav); 
                int perc=ho.getPerc();
                if(ev_perc.get(ev) != null)
                    perc += ev_perc.get(ev);
                ev_perc.put(ev, perc);
            }
            //év statisztika összeállítása 
            String evstat="";
            for (Integer ev : ev_tav.keySet()) {
                double avg=0;
                if(ev_perc.get(ev)!=0)
                    avg = (double)Math.round(ev_tav.get(ev)*6000/ev_perc.get(ev))/100;
                evstat += String.format("%5d: %9d km  %10s  %9.2f km/h\n", ev, ev_tav.get(ev), 
                                        timeFormat(ev_perc.get(ev)/60, ev_perc.get(ev)%60), avg);
            }
            // Statisztikák kiírása
            lblStat2.setText(
                    "Összes megtett út: " +tblHonapok.getItems().get(tblHonapok.getItems().size()-1).getOsszTav() +" km"
                    + "\nlegrövidebb táv: " + minTav + " km, " + d_minT
                    + "\nleghosszabb táv: " + maxTav + " km, " + d_maxT
                    + "\nlegkisebb átlag: " + minAVG + " km/h, " + d_minA
                    + "\nlegnagyobb átlag: " + maxAVG + " km/h, " + d_maxA
                    + "\n\nÉves összesítés: \n" + evstat               
            );
            //Grafikon
            charts();
        }else{  //Ha nincs lekért adat, akkor üres legyen a statisztika és a grafikonok
            lblStat2.setText("");
            bChart.getData().clear();
            lChart.getData().clear();
        }
        
    }
    
    @FXML
    private BarChart<String, Number> bChart;

     @FXML
    private LineChart<String, Number> lChart;

     
    private void charts(){
         /* BarChart ***************************************************************
          * Éves szériákban oszlopdiagramon mutatja a havonta megtett távolságokat */
         bChart.setTitle("táv / hónap");
         //Egy ArrayList-be kerülnek a szérialisták
         List<XYChart.Series<String, Number>> seriesList = new ArrayList<>();
         int ev = dpTol.getValue().getYear();
         while (ev <= dpIg.getValue().getYear()) {
             XYChart.Series<String, Number> series = new XYChart.Series<>(); // 1 széria tárolására
             series.setName("" + ev);
             for (Honap h : tblHonapok.getItems()) {
                 if(h.getEv()==ev)
                     series.getData().add(new XYChart.Data<>(h.getHonap(), h.getTav())); //Adatok hozzáadása a listához           
             }
             seriesList.add(series); //Széria hozzáadása a szérialistához. (ArrayList)
             ev++;
         }
         bChart.getData().clear(); //Előbb törölni kell a grafikonról a korábbi adatokat
         for (XYChart.Series<String, Number> series : seriesList) {  //Hozzáadjuk a szériákat a grafikonhoz 
             bChart.getData().add(series);      
         }

        /* LineChart ***************************************************************************************** 
         * Éves szériákban vonaldiagramon havonta mutatja az időszakban megtett összes (göngyölt) távolságot */

        /* TreeMap a TreeMap-ben... A belsőben a hónapokhoz tartozó göngyölt távolság van tárolva. Ez egy másik 
         * Treemapben van, ahol a kulcs az év. Tehát évenként vannak a havi göngyölt távolságok. */
        TreeMap<Integer, TreeMap<String, Integer>> evek=new TreeMap<>();
        for (ev = dpTol.getValue().getYear(); ev < dpIg.getValue().getYear()+1; ev++) {
            int otav=0;
            TreeMap<String, Integer> honapok=new TreeMap<>(); //A belső TreeMap, melyben hónaponként tároljuk a göngyölt távolságot 
            for (Honap h : tblHonapok.getItems()) {
                if(h.getEv() == ev){
                    otav += h.getTav();
                    String honap = h.getHonap();
                    honapok.put(honap, otav);
                    evek.put(ev, honapok);
                }
            }
        }
            
        lChart.setTitle("össztáv / hónap");    
            
        seriesList.removeAll(seriesList); //ugyanazt a listát használjuk, de előbb ki kell üríteni.
        ev = dpTol.getValue().getYear();
        while (ev <= dpIg.getValue().getYear()) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("" + ev);
            for (Honap h : tblHonapok.getItems()) {             
                if(h.getEv()==ev){
                    TreeMap<String, Integer> honapok =evek.get(ev); //Az adott évhez tartozó hónapok (belső TeeMap)
                    series.getData().add(new XYChart.Data<>(h.getHonap(), honapok.get(h.getHonap())));
                }                
            }
            seriesList.add(series);
            ev++;
        }
         
       lChart.getData().clear();
       for (XYChart.Series<String, Number> series : seriesList) {
           lChart.getData().add(series);  
       }
       
      
                           
     }
}
