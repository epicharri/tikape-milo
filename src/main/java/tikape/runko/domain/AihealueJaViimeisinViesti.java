
package tikape.runko.domain;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import tikape.runko.database.Database;

public class AihealueJaViimeisinViesti {
    
    private Integer id;
    private String nimi;
    private Database database;
    private String aika;
    private String viimeisinViesti;

    public AihealueJaViimeisinViesti(Database database, Integer id, String nimi, String viimeisinViesti, String aika) throws SQLException{
        this.id = id;
        this.nimi = nimi;
        
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT Viesti.id FROM Viesti, Viestiketju, Aihealue "
                + "WHERE Aihealue.id = Viestiketju.aihealue "
                + "AND Viestiketju.id = Viesti.viestiketju "
                + "AND Aihealue.id = ? "
                + "ORDER BY(Viesti.aika) DESC "
                + "LIMIT 1");
        stmt.setObject(1, this.id);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            
        }

        this.aika = rs.getString("aika");
        this.viimeisinViesti = rs.getString("sisalto");

        rs.close();
        stmt.close();
        connection.close();
        
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public String getAika() {
        return aika;
    }

    public void setAika(String aika) {
        this.aika = aika;
    }

    public String getViimeisinViesti() {
        return viimeisinViesti;
    }

    public void setViimeisinViesti(String viimeisinViesti) {
        this.viimeisinViesti = viimeisinViesti;
    }
    
}
