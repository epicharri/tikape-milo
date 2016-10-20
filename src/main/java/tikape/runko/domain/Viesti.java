/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;
import tikape.runko.domain.Viestiketju;
import tikape.runko.domain.Aihealue;
import java.sql.Timestamp;
import java.util.Date;

public class Viesti {

    private Integer id;
    private Viestiketju viestiketju;
    private String nimimerkki;
    private String sisalto;
    private Timestamp aika;
    private java.util.Date paiva;

    public Viesti(Integer id, Viestiketju viestiketju, String nimimerkki, String sisalto, String aika) {
        this.id = id;
        this.nimimerkki = nimimerkki;
        this.sisalto = sisalto;
        this.paiva = new java.util.Date();
        this.aika = new Timestamp(paiva.getTime());
    }

    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public Viestiketju getViestiketju() {
        return viestiketju;
    }

    public void setViestiketju(Viestiketju viestiketju) {
        this.viestiketju = viestiketju;
    }
    
    public String getNimimerkki() {
        return nimimerkki;
    }

    public void setNimimerkki(String nimimerkki) {
        this.nimimerkki = nimimerkki;
    }
    
    public String getSisalto() {
        return sisalto;
    }

    public void setSisalto(String sisalto) {
        this.sisalto = sisalto;
    }
    
    public Timestamp getAika() {
        return aika;
    }

    public void setAika(Timestamp aika) {
        this.aika = aika;
    }
    

}
