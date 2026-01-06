package bg.tu_varna.sit.virtualvineyard;

import bg.tu_varna.sit.virtualvineyard.dao.*;
import bg.tu_varna.sit.virtualvineyard.entities.*;
import bg.tu_varna.sit.virtualvineyard.enums.BottleType;
import bg.tu_varna.sit.virtualvineyard.enums.WarehouseContentType;
import bg.tu_varna.sit.virtualvineyard.models.Administrator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBInit
{
    private static final Logger logger = LogManager.getLogger(DBInit.class);
    public static void init()
    {
        initAdmin();
        initWarehouseTypes();
        initBottlesTypes();
    }

    private static void initAdmin(){
        Person testAdmin = PersonDAO.findByUsername("admin");
        if(testAdmin != null)
            return;
        PersonDAO pd = new PersonDAO();
        testAdmin = new Administrator("admin", "0000000000", "admin", "1234");
        pd.create(testAdmin);
        logger.info("Admin initialized with credentials admin-1234");
    }

    private static void initWarehouseTypes(){
        WarehouseTypeDAO wtd = new WarehouseTypeDAO();

        for (WarehouseContentType type : WarehouseContentType.values())
        {
            if (wtd.findByName(type.toString()) == null) {
                wtd.create(new WarehouseType(type.toString()));
            }
        }
        logger.info("Warehouse types initialized");
    }

    private static void initBottlesTypes(){
        BottleDAO bd = new BottleDAO();
        WarehouseDAO wd = new WarehouseDAO();

        for (Warehouse w : wd.findByContentType(WarehouseContentType.BOTTLE_ONLY))
            for (BottleType type : BottleType.values())
                if (bd.findByWarehouseAndType(w, type) == null)
                    bd.create(new Bottle(type, 0, w));
        logger.info("Bottle types initialized");
    }
}
