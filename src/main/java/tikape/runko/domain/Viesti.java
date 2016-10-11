/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;
import tikape.runko.domain.Viestiketju;
import tikape.runko.domain.Aihealue;
/**
 *
 * @author wame
 */
public class Viesti {

    private Integer id;
    private Viestiketju viestiketju;
    private String nimimerkki;
    private String sisalto;
    private String aika;

    public Viesti(Integer id, Viestiketju viestiketju, String nimimerkki, String sisalto, String aika) {
        this.id = id;
        this.nimimerkki = nimimerkki;
        this.sisalto = sisalto;
        this.aika = aika;
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
    
    public String getAika() {
        return aika;
    }

    public void setAika(String aika) {
        this.aika = aika;
    }
    

}
