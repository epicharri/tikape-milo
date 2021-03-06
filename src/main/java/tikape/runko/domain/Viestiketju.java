
package tikape.runko.domain;
import tikape.runko.domain.Aihealue;

public class Viestiketju {
    
    private Integer id;
    private String otsikko;
    private Aihealue aihealue;

    public Aihealue getAihealue() {
        return aihealue;
    }

    public void setAihealue(Aihealue aihealue) {
        this.aihealue = aihealue;
    }
    
    public Viestiketju(int id, String otsikko, Aihealue aihealue){
        this.id=id;
        this.otsikko=otsikko;
        this.aihealue=aihealue;
    }

    public Integer getId() {
        return id;
    }

    public String getOtsikko() {
        return otsikko;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
    }
    
}
