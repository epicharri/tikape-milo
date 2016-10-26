package tikape.runko.domain;
 
public class Aihealue implements Comparable<Aihealue>{
 
    private Integer id;
    private String nimi;
 
    public Aihealue(Integer id, String nimi) {
        this.id = id;
        this.nimi = nimi;
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
 
    @Override
    public int compareTo(Aihealue o) {
       return this.nimi.compareTo(o.getNimi());
       
    }
}
