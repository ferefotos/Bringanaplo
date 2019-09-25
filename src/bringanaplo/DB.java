package bringanaplo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.ObservableList;
import static panel.Panel.hiba;

/**
 *
 * @author Fere
 */
public class DB {
    final String db = "jdbc:mysql://localhost:3306/bringa" +
                      "?useUnicode=true&characterEncoding=UTF-8";
    final String user = "fere";		
    final String pass = "Anima680";
    
    public void beolvas(ObservableList<Honap> tabla, String s){
        try(Connection kapcs=DriverManager.getConnection(db, user, pass);
            PreparedStatement ekp = kapcs.prepareStatement(s)){
            
            tabla.clear();
            ResultSet eredmeny = ekp.executeQuery();
            int ossztav=0;
            while (eredmeny.next()) {
                ossztav+=eredmeny.getInt("tav");
                tabla.add( new Honap(
                     eredmeny.getDate("datum"),
                     eredmeny.getInt("tav"),
                     eredmeny.getString("ido"),
                     eredmeny.getString("note"),
                     ossztav
                ));
            }
            
        }catch(SQLException ex){
            hiba("Hiba!", ex.getMessage());
        }
    }
    
    public String hozzaad(Date datum, int tav, String ido, String note) {
        String s = "INSERT INTO naplo (datum, tav, ido, note) "
                 + "VALUES(?,?,?,?);";

        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
             PreparedStatement ekp = kapcs.prepareStatement(s)) {
            ekp.setDate(1, datum);            
            ekp.setInt(2, tav);
            ekp.setString(3, ido);
            ekp.setString(4, note);
            ekp.executeUpdate();
            return "";
        } catch (SQLException ex) {
            return ex.getMessage();
        }
    }
    public String modosit(Date datum, int tav, String ido, String note, Date sorDatum) {
        String s = "UPDATE naplo SET datum=?, tav=?, ido=?, note=? WHERE datum=?;";

        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
             PreparedStatement ekp = kapcs.prepareStatement(s)) {
            ekp.setDate(1, datum);            
            ekp.setInt(2, tav);
            ekp.setString(3, ido);
            ekp.setString(4, note);
            ekp.setDate(5, sorDatum);
            ekp.executeUpdate();
            return "";
        } catch (SQLException ex) {
            return ex.getMessage();
        }
    }
    
    public String torol(Date sorDatum) {
        String s = "DELETE FROM naplo WHERE datum=?;";
        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)) {
            ekp.setDate(1, sorDatum);
            ekp.executeUpdate();
            return "";
        } catch (SQLException ex) {
            return ex.getMessage();
        }                
    }
    // Az első rekord dátumának lekérdezése
    public Date elsoDatum(){
        String s ="SELECT datum FROM naplo ORDER BY datum LIMIT 1;";
            Date d = Date.valueOf("2000-01-01");
            
            try(Connection kapcs=DriverManager.getConnection(db, user, pass);
            PreparedStatement ekp = kapcs.prepareStatement(s)){
            ResultSet eredmeny = ekp.executeQuery();
            while (eredmeny.next()) {
                d = eredmeny.getDate("datum");
            }
            
            }catch(SQLException ex){
                hiba("Hiba!", ex.getMessage());
            }
            return d;
    }
    
}
