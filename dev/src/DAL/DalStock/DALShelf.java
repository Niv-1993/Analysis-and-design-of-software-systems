package DAL.DalStock;

import BusinessLayer.StockBusiness.instance.Location;
import DAL.DALObject;
import DAL.DalController;
import Utility.Tuple;

import java.util.ArrayList;
import java.util.List;

public class DALShelf extends DALObject {
    private int _shelfID;
    private int _cur;
    private int _typeID=0;
    private int _maxAmount;
    private int isStorage;
    int storeId;
    String tableName="Shelf";

    public DALShelf(){
        super(null);
    }

    public DALShelf(Integer storeID,int id, Integer typeID, Integer isStorage, Integer curr, Integer max, DalController dc){
        super(dc);
        this.storeId=storeID;
        _typeID=typeID;
        this.isStorage=isStorage;
        _cur=curr;
        _maxAmount=max;
        _shelfID=id;
    }

    public String getCreate() {
        return "CREATE TABLE IF NOT EXISTS Shelf (\n" +
                "\tstoreID INTEGER NOT NULL,\n" +
                "\tshelfID INTEGER NOT NULL,\n" +
                "\tlocation INTEGER NOT NULL,\n" +
                "\ttypeID INTEGER NOT NULL,\n" +
                "\tcurr INTEGER NOT NULL,\n" +
                "\tmaximum INTEGER NOT NULL,\n" +
                "\tPRIMARY KEY (storeID,shelfID, location),\n" +
                "\tFOREIGN KEY (typeID) REFERENCES Product(typeID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "\tFOREIGN KEY (storeID) REFERENCES Product(storeID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE\n" +
                ");";
    }

    public String getSelect() {
        return """
                SELECT * \s
                FROM ? \s
                WHERE storeID=? AND shelfID=?;\s
                """;
    }

    public String getDelete() {
        return """
                DELETE FROM ? \s
                WHERE storeID=? AND shelfID=?;\s
                """;
    }

    public String getUpdate() {
        return null;
    }

    public String getInsert() {
        return """
                INSERT INTO ? \s
                VALUE (?,?,?,?,?,?);
                """;
    }
    public int getID(){return _shelfID;}
    public int getCur(){return _cur;}
    public int getMax(){
        return _maxAmount;
    }
    public void setCur(int cur){
        String query= """
                UPDATE ? \s
                SET curr=?
                WHERE storeID=? AND shelfID=?;
                """;
        List<Tuple<Object,Class>> params=prepareList(tableName,cur,storeId,_shelfID);
        try {
            DC.noSelect(query,params);
        }
        catch (Exception e){
            throw new IllegalArgumentException("fail");
        }
        _cur=cur;
    }
    public int get_typeID(){return _typeID;}
    public void setTypeID(int t){}

    protected List<Tuple<Object,Class>> prepareList(Object... o){
        List<Tuple<Object,Class>> params=new ArrayList<>();
        for (Object o1:o){
            params.add(new Tuple<>(o1,o1.getClass()));
        }
        return params;
    }
    public boolean isStorage(){
        return isStorage==1;
    }
    public Location getLocation(){
        if (isStorage())
            return Location.Storage;
        else
            return Location.Shelves;
    }

}
