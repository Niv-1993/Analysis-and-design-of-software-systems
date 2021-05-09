package BussinessLayer;

import DataLayer.*;
import enums.Pair;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

public class DataControl {

    private BranchMapper branchMapper;
    private ItemMapper itemMapper;
    private SupplierMapper supplierMapper;
    private TruckMapper truckMapper;
    private DriverMapper driverMapper;
    private TransportationMapper transportationMapper;
    static  private String dbName;

    public DataControl(){
        dbName="databaseDemo.db";
        branchMapper=BranchMapper.getMapper(dbName);
        itemMapper=ItemMapper.getMapper(dbName);
        supplierMapper=SupplierMapper.getMapper(dbName);
        truckMapper=TruckMapper.getMapper(dbName);
        transportationMapper=TransportationMapper.getMapper(dbName);
        driverMapper=DriverMapper.getMapper(dbName);

    }

    public List< Branch> getBranches() throws Exception {
        return branchMapper.getBranches();
    }
    public List<Supplier> getSuppliers() throws Exception {
        return supplierMapper.getSuppliers();
    }
    public List<Item> getItems() throws Exception {
        return itemMapper.getItems();
    }
    public List<Truck> getTrucks() throws Exception {
        return truckMapper.getTrucks();
    }
    public HashMap<Long, Driver> getDrivers() throws Exception{
        return driverMapper.selectAll();
    }
    public List< Transportation> getTransportations() throws Exception {
        return transportationMapper.getTransportations(truckMapper,itemMapper,supplierMapper,branchMapper);
    }

    public Driver getDriver(long id) throws Exception{
        return driverMapper.select(id);
    }
    public Branch getBranch(int id) throws Exception {
        return branchMapper.getBranch(id);
    }
    public Supplier getSupplier(int id) throws Exception {
        return supplierMapper.getSupplier(id);
    }
    public Item getItem(long id) throws Exception {
        return itemMapper.getItem(id);
    }
    public Truck getTruck(long id) throws Exception {
        return truckMapper.getTruck(id);
    }
    public Transportation getTransportation(long id) throws Exception {
        return transportationMapper.getTransportation(id,truckMapper,itemMapper,supplierMapper,branchMapper);
    }
    public List<Item> getItemsBySupplier(int id) throws Exception {
        return itemMapper.selectBySupplier(id);
    }

    public void addTransportation(long idCounter, Transportation tra) {
        transportationMapper.addTransportation(idCounter,tra);
    }

    public List<Transportation> getTransportationsList() throws Exception {
        return transportationMapper.getTransportations(truckMapper,itemMapper,supplierMapper,branchMapper);
    }

    public void remove(long idCounter) {
        transportationMapper.remove(idCounter);
    }

    public void saveTransportation(long id) throws Exception {
        transportationMapper.saveTransportation(id);
    }

    public void setDriverOnTrans(long transId, Driver driver) {

        transportationMapper.setDriverOnTrans(transId,driver);
    }
    public void setTruckOnTrans(long transId, Truck truck) {

        transportationMapper.setTruckOnTrans(transId,truck);
    }

    public void setDeliveryItems(long transId, HashMap<Branch, List<Pair<Item, Integer>>> deliveryItems) {

        transportationMapper.setDeliveryItems(transId,deliveryItems);
    }

    public void setSuppliersItems(long transId, HashMap<Supplier, List<Pair<Item, Integer>>> s) {
        transportationMapper.setSuppliersItems(transId,s);
    }

    public void setTime(long id, LocalTime leavingTime) {
        transportationMapper.setTime(id,leavingTime);
    }

    public void setWeight(long id, int weight) {
        transportationMapper.setWeight(id,weight);
    }

    public void setDate(long id, LocalDate date) {
        transportationMapper.setDate(id,date);
    }

    public long getCurrID() throws Exception {
        return transportationMapper.getCurrId();
    }
}