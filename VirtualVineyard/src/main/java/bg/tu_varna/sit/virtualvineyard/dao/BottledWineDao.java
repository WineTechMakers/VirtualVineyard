package bg.tu_varna.sit.virtualvineyard.dao;

import bg.tu_varna.sit.virtualvineyard.entities.BottledWine;

public class BottledWineDao extends AbstractDAO<BottledWine> {
    public BottledWineDao() {
        setEntityClass(BottledWine.class);
    }
}
