package bg.tu_varna.sit.virtualvineyard;

public class Main {
    public static void main(String[] args)
    {
        //PersonDAO.testwriting();
        //WineSystem a = new WineSystem();
        //a.authenticate("John Doe", "f");

//        Person admin = new Administrator("admin", "2234567890", "adminc", "12345");
//        PersonDAO dao = new PersonDAO();
//        //dao.create(admin);
//        admin = dao.findOne(18L);
        DBInit.init();
        //Person lol = PersonDAO.authenticate("admin", "1234");
        //System.out.println(lol);
    }
}