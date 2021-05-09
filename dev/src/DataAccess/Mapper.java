package DataAccess;

import org.apache.log4j.Logger;
import java.sql.*;

public class Mapper {
    private final static Logger log = Logger.getLogger(Mapper.class);
    private static String dbname = "database.db";
    private int currBranchID;

    public Mapper() {
    }


    protected static Connection connect() throws Exception {
        Class.forName("org.sqlite.JDBC");
        String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\" + dbname;
        Connection conn;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            log.warn("failed to make SQL connection");
            throw new Exception("cant connect");
        }
        return conn;
    }

    public void updateIntInt(int id, int value, String column, String idn, String tableName) {
        String query = String.format("UPDATE %s SET %s= ? WHERE %s= ?", tableName, column, idn);
        try (Connection con = connect();
             PreparedStatement pre = con.prepareStatement(query)) {
            pre.setInt(1, value);
            pre.setInt(2, id);
            pre.executeUpdate();
        } catch (Exception e) {
            System.out.println("[updateIntInt] ->" +e.getMessage());
        }
    }

    public void updateIntboolean(int id, boolean value, String column, String idn, String tableName) {
        String query = String.format("UPDATE %s SET %s= ? WHERE %s= ?", tableName, column, idn);
        try (Connection con = connect();
             PreparedStatement pre = con.prepareStatement(query)) {
            pre.setBoolean(1, value);
            pre.setInt(2, id);
            pre.executeUpdate();
        } catch (Exception e) {
            System.out.println("[updateIntboolean] ->" +e.getMessage());
        }
    }


    public void updateIntString(int id, String value, String column, String idn, String tableName) {
        String query = String.format("UPDATE %s SET %s= ? WHERE %s= ?", tableName, column, idn);
        try (Connection con = connect();
             PreparedStatement pre = con.prepareStatement(query)) {
            pre.setString(1, value);
            pre.setInt(2, id);
            pre.executeUpdate();
        } catch (Exception e) {
            System.out.println("[updateIntString] ->" +e.getMessage());
        }
    }

    public int getNextID(String tableName,String column) {
        int nextID = 1;
        ResultSet res;
        String query = String.format("SELECT Max(%s)+1 as nextID FROM %s", column, tableName);
        try (Connection con = connect(); PreparedStatement pre = con.prepareStatement(query)) {
            res = pre.executeQuery();
            if (res.next())
                nextID = res.getInt("nextID") == 0? 1: res.getInt("nextID");
        } catch (Exception e) {
            System.out.println("[getNextID] ->" +e.getMessage());
        }
        return nextID;
    }

    public void setCurrBranchID(int currBranchID) {
        this.currBranchID = currBranchID;
    }

    public int getCurrBranchID() {
        return currBranchID;
    }

    public void createTables() {
        try (Connection con = connect();
             Statement s = con.createStatement()) {
            s.addBatch(getCreateEmployee());
            s.addBatch(getCreateConstConstraint());
            s.addBatch(getCreateTempConstraint());
            s.addBatch(getCreateShift());
            s.addBatch(getCreateDriver());
            s.addBatch(getCreateBranches());
            s.addBatch(getCreateRolesAndEmps());
            s.addBatch(getCreateShiftsAndEmps());
            s.addBatch(getCreateShiftsAndRolesAmount());
            s.addBatch(getCreateDefaults());
            s.addBatch(getCreateBranches());
            s.addBatch(getCreateItem());
            s.addBatch(getCreateSupplierItems());
            s.addBatch(getCreateTrucks());
            s.addBatch(getCreateTransportations());
            s.addBatch(getCreateSuppliersItemsOnTran());
            s.addBatch(getCreateBranchesItemsOnTran());
            s.addBatch(getCreateSuppliers());
            s.executeBatch();
        } catch (Exception e) {
            System.out.println("[createTables] ->"+e.getMessage());
        }
    }

    private String getCreateEmployee() {
        return "CREATE TABLE IF NOT EXISTS \"Employees\" (\n" +
                "\t\"EID\"\tINTEGER UNIQUE,\n" +
                "\t\"Name\"\tTEXT NOT NULL,\n" +
                "\t\"StartWorkingDate\"\tTEXT NOT NULL,\n" +
                "\t\"Salary\"\tINTEGER NOT NULL,\n" +
                "\t\"BankID\"\tINTEGER NOT NULL,\n" +
                "\t\"BranchNumber\"\tINTEGER NOT NULL,\n" +
                "\t\"AccountNumber\"\tINTEGER NOT NULL,\n" +
                "\t\"EducationFund\"\tINTEGER NOT NULL,\n" +
                "\t\"DaysOff\"\tINTEGER NOT NULL,\n" +
                "\t\"SickDays\"\tINTEGER NOT NULL,\n" +
                "\t\"Active\"\tINTEGER NOT NULL DEFAULT 1,\n" +
                "\t\"BID\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"EID\",\"BID\"),\n" +
                "\tFOREIGN KEY(\"BID\") REFERENCES \"Branches\"(\"BID\") ON DELETE SET NULL\n" +
                ");";
    }

    private String getCreateConstConstraint() {
        return "CREATE TABLE IF NOT EXISTS \"ConstConstraints\" (\n" +
                "\t\"CID\"\tINTEGER,\n" +
                "\t\"EID\"\tINTEGER NOT NULL,\n" +
                "\t\"ShiftType\"\tTEXT NOT NULL,\n" +
                "\t\"DayOfWeek\"\tTEXT NOT NULL,\n" +
                "\t\"Reason\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"CID\"),\n" +
                "\tFOREIGN KEY(\"EID\") REFERENCES \"Employees\"(\"EID\") ON DELETE CASCADE\n" +
                ");";
    }

    private String getCreateTempConstraint() {
        return "CREATE TABLE IF NOT EXISTS \"TempConstraints\" (\n" +
                "\t\"CID\"\tINTEGER,\n" +
                "\t\"EID\"\tINTEGER NOT NULL,\n" +
                "\t\"ShiftType\"\tTEXT NOT NULL,\n" +
                "\t\"Date\"\tTEXT NOT NULL,\n" +
                "\t\"Reason\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"CID\"),\n" +
                "\tFOREIGN KEY(\"EID\") REFERENCES \"Employees\"(\"EID\") ON DELETE CASCADE\n" +
                ");";
    }

    private String getCreateShift() {
        return "CREATE TABLE IF NOT EXISTS \"Shifts\" (\n" +
                "\t\"SID\"\tINTEGER,\n" +
                "\t\"Date\"\tTEXT NOT NULL,\n" +
                "\t\"ShiftType\"\tTEXT NOT NULL,\n" +
                "\t\"WasSelfMake\"\tINTEGER NOT NULL,\n" +
                "\t\"BID\"\tINTEGER NOT NULL,\n" +
                "\tPRIMARY KEY(\"SID\"),\n" +
                "\tFOREIGN KEY(\"BID\") REFERENCES \"Branches\"(\"BID\") ON DELETE CASCADE\n" +
                ");\n";
    }

    private String getCreateDriver() {
        return "CREATE TABLE IF NOT EXISTS \"Drivers\" (\n" +
                "\t\"EID\"\tINTEGER,\n" +
                "\t\"License\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"EID\"),\n" +
                "\tFOREIGN KEY(\"EID\") REFERENCES \"Employees\"(\"EID\") ON DELETE CASCADE\n" +
                ");";
    }

    private String getCreateBranches() {
        return "CREATE TABLE IF NOT EXISTS \"Branches\" (\n" +
                "\t\"BID\"\tINTEGER,\n" +
                "\t\"Street\"\tTEXT NOT NULL,\n" +
                "\t\"City\"\tTEXT NOT NULL,\n" +
                "\t\"Number\"\tINTEGER NOT NULL,\n" +
                "\t\"Enter\"\tINTEGER NOT NULL,\n" +
                "\t\"Area\"\tTEXT NOT NULL,\n" +
                "\t\"ContactName\"\tINTEGER NOT NULL,\n" +
                "\t\"Phone\"\tINTEGER NOT NULL,\n" +
                "\tPRIMARY KEY(\"BID\")\n" +
                ");";
    }

    private String getCreateRolesAndEmps() {
        return "CREATE TABLE IF NOT EXISTS \"RolesAndEmployees\" (\n" +
                "\t\"EID\"\tINTEGER,\n" +
                "\t\"Role\"\tTEXT,\n" +
                "\tFOREIGN KEY(\"EID\") REFERENCES \"Employees\"(\"EID\") ON DELETE CASCADE,\n" +
                "\tPRIMARY KEY(\"EID\",\"Role\")\n" +
                ");";
    }

    private String getCreateShiftsAndEmps() {
        return "CREATE TABLE IF NOT EXISTS \"ShiftsAndEmployees\" (\n" +
                "\t\"SID\"\tINTEGER,\n" +
                "\t\"EID\"\tINTEGER,\n" +
                "\t\"Role\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"SID\",\"EID\",\"Role\"),\n" +
                "\tFOREIGN KEY(\"EID\") REFERENCES \"Employees\"(\"EID\") ON DELETE CASCADE,\n" +
                "\tFOREIGN KEY(\"SID\") REFERENCES \"Shifts\"(\"SID\") ON DELETE CASCADE\n" +
                ");";
    }

    private String getCreateShiftsAndRolesAmount() {
        return "CREATE TABLE IF NOT EXISTS \"ShiftsAndRolesAmount\" (\n" +
                "\t\"SID\"\tINTEGER,\n" +
                "\t\"Role\"\tINTEGER,\n" +
                "\t\"Amount\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"SID\",\"Role\"),\n" +
                "\tFOREIGN KEY(\"SID\") REFERENCES \"Shifts\"(\"SID\") ON DELETE CASCADE\n" +
                ");\n";
    }

    private String getCreateDefaults(){
        return "CREATE TABLE IF NOT EXISTS \"Defaults\" (\n" +
                "\t\"ShiftType\"\tTEXT,\n" +
                "\t\"Role\"\tTEXT,\n" +
                "\t\"Amount\"\tINTEGER,\n" +
                "\t\"BID\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"BID\",\"Role\",\"ShiftType\"),\n" +
                "\tFOREIGN KEY(\"BID\") REFERENCES \"Branches\"(\"BID\") ON DELETE CASCADE\n" +
                ");";
    }
    private String getCreateBranchesItemsOnTran() {
        return "CREATE TABLE IF NOT EXISTS \"BranchesItemsOnTran\" (\n" +
                "\t\"BranID\"\"\tNUMERIC,\n" +
                "\t\"TranID\"\tNUMERIC,\n" +
                "\t\"ItemID\"\tNUMERIC,\n" +
                "\tPRIMARY KEY(\"BranID\",\"TranID\",\"ItemID\"),\n" +
                "\tFOREIGN KEY(\"TranID\") REFERENCES \"Transportations\"(\"ID\") ,\n" +
                "\tFOREIGN KEY(\"BranID\") REFERENCES \"Branches\"(\"BID\") ,\n" +
                "\tFOREIGN KEY(\"ItemID\") REFERENCES \"Items\"(\"ID\") \n"+
                ");";
    }
    private String getCreateItem() {
        return "CREATE TABLE IF NOT EXISTS \"Items\" (\n" +
                "\t\"ID\"\tNUMERIC,\n" +
                "\t\"Name\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"ID\"),\n" +
                ");";
    }
    private String getCreateSupplierItems() {
        return "CREATE TABLE IF NOT EXISTS \"SupplierItems\" (\n" +
                "\t\"ItemID\"\tNUMERIC,\n" +
                "\t\"SupID\"\tNUMERIC,\n" +
                "\tPRIMARY KEY(\"ItemID\",\"SupID\"),\n" +
                "\tFOREIGN KEY(\"ItemID\") REFERENCES \"Items\"(\"ID\") \n"+
                ");";
    }
    private String getCreateSuppliersItemsOnTran() {
        return "CREATE TABLE IF NOT EXISTS \"SupplierItemsOnTran\" (\n" +
                "\t\"SupID\"\"\tNUMERIC,\n" +
                "\t\"TranID\"\tNUMERIC,\n" +
                "\t\"ItemID\"\tNUMERIC,\n" +
                "\tPRIMARY KEY(\"SupID\",\"TranID\",\"ItemID\"),\n" +
                "\tFOREIGN KEY(\"SupID\") REFERENCES \"Suppliers\"(\"ID\") ,\n" +
                "\tFOREIGN KEY(\"TranID\") REFERENCES \"Transportations\"(\"ID\") ,\n" +
                "\tFOREIGN KEY(\"ItemID\") REFERENCES \"Items\"(\"ID\") \n"+
                ");";
    }
    private String getCreateSuppliers() {
        return "CREATE TABLE IF NOT EXISTS \"Suppliers\" (\n" +
                "\t\"ID\"\"\tNUMERIC,\n" +
                "\t\"Street\"\tTEXT NOT NULL,\n" +
                "\t\"City\"\tTEXT NOT NULL,\n" +
                "\t\"Number\"\"\tINTEGER NOT NULL,\n" +
                "\t\"Enter\"\tINTEGER NOT NULL,\n" +
                "\t\"Area\"\tTEXT NOT NULL,\n" +
                "\t\"ContactName\"\tTEXT NOT NULL,\n" +
                "\t\"Phone\"\tINTEGER NOT NULL,\n" +
                "\tPRIMARY KEY(\"SID\")\n" + ");";
    }
    private String getCreateTransportations() {
        return "CREATE TABLE IF NOT EXISTS \"Transportations\" (\n" +
                "\t\"ID\"\"\tINTEGER,\n" +
                "\t\"Area\"\tTEXT,\n" +
                "\t\"Date\"\"\tTEXT,\n" +
                "\t\"LeavingTime\"\tTEXT,\n" +
                "\t\"Wieght\"\tINTEGER,\n" +
                "\t\"driverID\"\tNUMERIC,\n" +
                "\t\"trackID\"\tNUMERIC,\n" +
                "\tPRIMARY KEY(\"ID\")\n" +
                "\tFOREIGN KEY(\"trackID\") REFERENCES \"Trucks\"(\"ID\") ,\n" +
                "\tFOREIGN KEY(\"driverID\") REFERENCES \"Drivers\"(\"EID\") \n"+
                ");";
    }
    private String getCreateTrucks() {
        return "CREATE TABLE IF NOT EXISTS \"Trucks\" (\n" +
                "\t\"ID\"\"\tNUMERIC,\n" +
                "\t\"MaxWeight\"\tINTEGER,\n" +
                "\t\"Model\"\"\tTEXT,\n" +
                "\t\"NetWeight\"\tINTEGER,\n" +
                "\t\"Liecense\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"ID\")\n" +
                ");";
    }




}
