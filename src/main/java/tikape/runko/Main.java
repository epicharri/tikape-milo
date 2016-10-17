package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.*;

public class Main {
// TESTATAAN ENSIN GITTIÄ
    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:foorumi.db");
        database.init();
        
        ViestiDao vDao = new ViestiDao(database);
        ViestiketjuDao vkDao= new ViestiketjuDao(database);
        AihealueDao aDao = new AihealueDao(database);
        
        get("/aihealueet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("aihealueet", aDao.findAll());

            return new ModelAndView(map, "aihealueet");
        }, new ThymeleafTemplateEngine());
        
        get("/aihealueet/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viestiketjut", vkDao.findByAihelue(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "viestiketjut");
        }, new ThymeleafTemplateEngine());
        
        get("/aihealueet/viestiketju/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viestit", vDao.findByViestiketju(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "viestit");
        }, new ThymeleafTemplateEngine());
        
        

//        OpiskelijaDao opiskelijaDao = new OpiskelijaDao(database);
//
//        get("/", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("viesti", "tervehdys");
//
//            return new ModelAndView(map, "index");
//        }, new ThymeleafTemplateEngine());
//
//        get("/opiskelijat", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("opiskelijat", opiskelijaDao.findAll());
//
//            return new ModelAndView(map, "opiskelijat");
//        }, new ThymeleafTemplateEngine());
//
//        get("/opiskelijat/:id", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("opiskelija", opiskelijaDao.findOne(Integer.parseInt(req.params("id"))));
//
//            return new ModelAndView(map, "opiskelija");
//        }, new ThymeleafTemplateEngine());
    }
}
