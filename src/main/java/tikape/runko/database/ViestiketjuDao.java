package tikape.runko.database;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Aihealue;
import tikape.runko.domain.Viestiketju;
 
public class ViestiketjuDao implements Dao<Viestiketju, Integer> {
 
    private Database database;
    private AihealueDao aihealueDao;
 
    public ViestiketjuDao(Database database) {
        this.database = database;
        this.aihealueDao = new AihealueDao(database);
    }
 
    @Override
    public Viestiketju findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM "
                + "Viestiketju WHERE id = ?");
        stmt.setObject(1, key);
 
        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
 
        int id = rs.getInt("id");
        String otsikko = rs.getString("otsikko");
 
        Aihealue aihealue = this.aihealueDao.findOne(rs.getInt("aihealue"));
 
        rs.close();
        stmt.close();
        connection.close();
 
        return new Viestiketju(id, otsikko, aihealue);
    }
 
    @Override
    public List<Viestiketju> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM "
                + "Viestiketju");
        ResultSet rs = stmt.executeQuery();
 
        List<Viestiketju> viestit = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String otsikko = rs.getString("otsikko");
 
            Aihealue aihealue = this.aihealueDao.findOne(rs.getInt("aihealue"));
 
            viestit.add(new Viestiketju(id, otsikko, aihealue));
        }
 
        rs.close();
        stmt.close();
        connection.close();
 
        return viestit;
    }
 
    @Override
    public void delete(Integer key) throws SQLException {
 
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Viestiketju WHERE id = ?");
        stmt.setString(1, key.toString());
        stmt.executeQuery();
        stmt.close();
        connection.close();
 
    }
 
    public List<Viestiketju> findByAihelue(Integer aihealueId) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM "
                + "Viestiketju WHERE aihealue LIKE ?");
        stmt.setObject(1, Integer.toString(aihealueId));
        ResultSet rs = stmt.executeQuery();
        Aihealue aihealue = this.aihealueDao.findOne(aihealueId);
       
        List<Viestiketju> viestit = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String otsikko = rs.getString("otsikko");
           
 
            viestit.add(new Viestiketju(id, otsikko, aihealue));
        }
        System.out.println("VIESTIKETJUT: " + viestit);
        rs.close();
        stmt.close();
        connection.close();
 
        return viestit;
    }
   
    public int uusinViestiketju() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT MAX(id) FROM Viestiketju");
 
        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
 
        int id = rs.getInt(1);
       
        rs.close();
        stmt.close();
        connection.close();
       
        return id;
       
    }
 
    public void createViestiketju(Integer aihealueId, String otsikko, String nimimerkki, String sisalto) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Viestiketju(aihealue, otsikko) "
                + "VALUES( ?, ?)");
        
        stmt.setString(1, aihealueId.toString());
        stmt.setString(2, otsikko);
        stmt.executeUpdate();
 
       
        ViestiDao v = new ViestiDao(this.database);
        v.createViesti(this.uusinViestiketju(), nimimerkki, sisalto);
        stmt.close();
        connection.close();
    }
 
}