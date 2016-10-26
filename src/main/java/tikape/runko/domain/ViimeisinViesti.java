package tikape.runko.domain;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViimeisinViesti implements Comparable<ViimeisinViesti>{

    private Integer id;
    private String nimi;
    private String aika;
    private String sisalto;
    private int viestienMaara;

    public ViimeisinViesti(Integer id, String nimi, String sisalto, String aika) throws SQLException {

        this.id = id;
        this.nimi = nimi;
        this.sisalto = sisalto;
        this.aika = aika;

    }

    public ViimeisinViesti(Integer id, String nimi, String sisalto, String aika, int viestienMaara) throws SQLException {

        this.id = id;
        this.nimi = nimi;
        this.sisalto = sisalto;
        this.aika = aika;
        this.viestienMaara = viestienMaara;

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

    public int getViestienMaara() {
        return viestienMaara;
    }

    public void setViestienMaara(int viestienMaara) {
        this.viestienMaara = viestienMaara;
    }

    @Override
    public int compareTo(ViimeisinViesti o) {
        
        
        return o.aika.compareTo(this.aika);
    }

}
