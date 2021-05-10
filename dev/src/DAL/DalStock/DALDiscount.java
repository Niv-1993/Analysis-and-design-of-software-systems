package DAL.DalStock;

import DAL.DALObject;
import DAL.DalController;
import Utility.Tuple;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class DALDiscount extends DALObject {

    protected int _discountID;
    protected float _percent;
    protected String _start;
    protected String _end;
    protected int storeID;
    private int typeId;
    protected String tableName="Discount";


    public DALDiscount(DalController dc,int storeID,int discountID,int typeId,float percent,Date start,Date end) {
        super(dc);
        _discountID=discountID;
        _percent=percent;
        String pattern="dd-MM-yyyy";
        DateFormat df=new SimpleDateFormat(pattern);
        _start=df.format(start);
        _end=df.format(end);
        this.storeID=storeID;
        this.typeId=typeId;
    }

    public DALDiscount(DalController dc) {
        super(dc);
    }

    @Override
    public String getCreate() {
        return "CREATE TABLE IF NOT EXISTS Discount (\n" +
                "\tstoreID INTEGER NOT NULL UNIQUE,\n" +
                "\tdiscountID INTEGER NOT NULL UNIQUE,\n" +
                "\ttypeID INTEGER,\n" +
                "\tcategoryID INTEGER,\n" +
                "\tsupplierID INTEGER,\n" +
                "\tpercent DOUBLE NOT NULL,\n" +
                "\tstartDate VARCHAR NOT NULL,\n" +
                "\tendDate VARCHAR NOT NULL,\n" +
                "\tPRIMARY KEY (storeID, discountID),\n" +
                "\tUNIQUE (storeID, discountID),\n" +
                "\tFOREIGN KEY (storeID) REFERENCES StoreController(storeID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "\tFOREIGN KEY (storeID,typeID) REFERENCES ProductType(storeID,typeID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "\tFOREIGN KEY (storeID,categoryID) REFERENCES Category(storeID,categoryID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE\n" +
                "\tFOREIGN KEY (storeID,supplierID) REFERENCES Supplier(storeID,supplierID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE\n" +
                ");";
    }

    @Override
    public String getSelect() {
        return """
                SELECT *\s
                FROM Discount\s
                WHERE storeID=? AND discountID=?;\s
                """;
    }

    @Override
    public String getDelete() {
        return """
                DELETE FROM Discount\s
                WHERE storeID=? AND discountID=?; \s
                """;
    }

    @Override
    public String getUpdate() {
        return null;
    }

    @Override
    public String getInsert() {
        return """
                INSERT OR REPLACE INTO Discount VALUES (?,?,?,?,?,?,?,?);
                """;
    }

    public int getID() {
        return _discountID;
    }

    public float getPercent() {
        return _percent;
    }

    public Date getStartDate() {
        try {
            return new SimpleDateFormat("dd-MM-yyyy").parse(_start);
        }
        catch (Exception e){
            throw new IllegalArgumentException("DateBug");
        }
    }

    public Date getEndDate() {
        try {
            return new SimpleDateFormat("dd-MM-yyyy").parse(_end);
        }
        catch (Exception e){
            throw new IllegalArgumentException("DateBug");
        }
    }

    public void setTypeID(int i){
        String query= """
                UPDATE Discount \s
                SET typeID=?
                WHERE storeID=? AND discountID=?;
                """;
        List<Tuple<Object,Class>> params=prepareList(i,storeID,_discountID);
        try {
            DC.noSelect(query,params);
        }
        catch (Exception e){
            throw new IllegalArgumentException("fail");
        }
        typeId=i;
    }
    public void removeTypeID(int i) {
        String query= """
                UPDATE Discount \s
                SET typeID=?
                WHERE storeID=? AND discountID=? AND typeId=?;
                """;
        List<Tuple<Object,Class>> params=prepareList(0,storeID,_discountID,i);
        try {
            DC.noSelect(query,params);
        }
        catch (Exception e){
            throw new IllegalArgumentException("fail");
        }
        typeId=0;
    }
    protected List<Tuple<Object,Class>> prepareList(Object... o){
        List<Tuple<Object,Class>> params=new ArrayList<>();
        for (Object o1:o){
            params.add(new Tuple<>(o1,o1.getClass()));
        }
        return params;
    }




}
