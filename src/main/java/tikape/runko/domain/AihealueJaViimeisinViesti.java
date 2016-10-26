
package tikape.runko.domain;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AihealueJaViimeisinViesti {
    
    private Integer id;
    private String nimi;
    private String aika;
    private String sisalto;

    public AihealueJaViimeisinViesti(Integer id, String nimi, String sisalto, String aika) throws SQLException{

        this.id = id;
        this.nimi = nimi;
        this.sisalto = sisalto;
        this.aika = aika;
        
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

    public String getSisalto() {
        return sisalto;
    }

    public void setSisalto(String sisalto) {
        this.sisalto = sisalto;
    }

    
}
