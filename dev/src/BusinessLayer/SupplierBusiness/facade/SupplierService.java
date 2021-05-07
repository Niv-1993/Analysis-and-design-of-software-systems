package BusinessLayer.SupplierBusiness.facade;

import BusinessLayer.SupplierBusiness.SupplierController;
import BusinessLayer.SupplierBusiness.ISupplierService;
import BusinessLayer.SupplierBusiness.facade.outObjects.*;



import java.time.LocalDate;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class SupplierService implements ISupplierService {
    private SupplierController supplierController;

    public SupplierService() {
        supplierController = new SupplierController();
    }

    @Override
    public response LoadData() {
       supplierController.load();
       return new response();
    }

    @Override
    public response deleteData() { return new response(); }

    @Override
    public Tresponse<SupplierCard> showSupplier(int supplierBN) {
        BusinessLayer.SupplierBusiness.SupplierCard supplierCard;
        try {
            supplierCard = supplierController.showSupplier(supplierBN);
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        if (supplierCard != null)
            return new Tresponse<>(new SupplierCard(supplierCard));
        return new Tresponse<>("ERROR: There is no such supplier");
    }

    @Override
    public response addSupplier(String supplierName, int bankNumber , int branchNumber, int bankAccount, String payWay) {
        try{
          supplierController.addSupplier(supplierName,bankNumber ,branchNumber , bankAccount, payWay);
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response removeSupplier(int removeSupplier) {
        try{
            supplierController.removeSupplier(removeSupplier);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public Tresponse<SupplierCard> showSupplierBN(String supplierName) {
        BusinessLayer.SupplierBusiness.SupplierCard supplierCard;
        try {
            supplierCard = supplierController.showSupplierBN(supplierName);
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        if (supplierCard != null) {
            return new Tresponse<>(new SupplierCard(supplierCard));
        }
        return new Tresponse<>("ERROR: There is no such supplier");
    }

    @Override
    public response updateSupplierPayWay(int supplierBN, String payWay) {
        try{
            supplierController.updateSupplierPayWay(supplierBN, payWay);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response updateSupplierBankAccount(int supplierBN,int bankNumber , int branchNumber , int bankAccount) {
        try{
            supplierController.updateSupplierBankAccount(supplierBN,bankNumber , branchNumber , bankAccount);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response addContactPhone(int supplierBN, String phone, String name) {
        try{
            supplierController.addContactPhone(supplierBN, phone, name);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response addContactEmail(int supplierBN, String Email, String name) {
        try{
            supplierController.addContactEmail(supplierBN, Email, name);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response removeContactPhone(int supplierBN, String phone) {
        try{
            supplierController.removeContactPhone(supplierBN, phone);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response removeContactEmail(int supplierBN, String email) {
        try{
            supplierController.removeContactEmail(supplierBN, email);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response updateContactPhone(int supplierBN, String phone , String name) {
        try{
            supplierController.updateContactPhone(supplierBN, phone , name);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response updateContactEmail(int supplierBN, String email , String name) {
        try{
            supplierController.updateContactEmail(supplierBN, email , name);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public Tresponse<List<SupplierCard>> showAllSuppliers() {
        List<BusinessLayer.SupplierBusiness.SupplierCard> supplierCards;
        List<SupplierCard> outSupplierCard = new LinkedList<>();
        try {
            supplierCards = supplierController.showAllSuppliers();
            for(BusinessLayer.SupplierBusiness.SupplierCard supplierCard : supplierCards){
                outSupplierCard.add(new SupplierCard(supplierCard));
            }
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        if(outSupplierCard.size() == 0) return new Tresponse<>("there is no suppliers.");
        return new Tresponse<>(outSupplierCard);

    }

    @Override
    public Tresponse<List<Item>> showAllItemsOfSupplier(int SupplierBN) {
        List<BusinessLayer.SupplierBusiness.Item> items;
        List<Item> outItems = new LinkedList<>();
        try {
            items = supplierController.showAllItemsOfSupplier(SupplierBN);
            for(BusinessLayer.SupplierBusiness.Item item : items){
                outItems.add(new Item(item));
            }
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        if(outItems.size() == 0) return new Tresponse<>("supplier does not have any items.");
        return new Tresponse<>(outItems);
    }

    @Override
    public Tresponse<Item> showItemOfSupplier(int SupplierBN, int itemId) {
        BusinessLayer.SupplierBusiness.Item item;
        try{
            item = supplierController.showItemOfSupplier(SupplierBN, itemId);
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        return new Tresponse<>(new Item(item));
    }

    @Override
    public Tresponse<List<Item>> showAllItemsOfOrder(int SupplierBN, int orderId) {
        List<BusinessLayer.SupplierBusiness.Item> items;
        List<Item> outItems = new LinkedList<>();
        try {
            items = supplierController.showAllItemsOfOrder(SupplierBN , orderId);
            for(BusinessLayer.SupplierBusiness.Item item : items){
                outItems.add(new Item(item));
            }
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        if(outItems.size() == 0) return new Tresponse<>("supplier does not have any orders.");
        return new Tresponse<>(outItems);
    }

    @Override
    public Tresponse<List<Item>> showAllItems() {
        List<BusinessLayer.SupplierBusiness.Item> items;
        List<Item> outItems = new LinkedList<>();
        try {
            items = supplierController.showAllItems();
            for(BusinessLayer.SupplierBusiness.Item item : items){
                outItems.add(new Item(item));
            }
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        if(outItems.size() == 0) return new Tresponse<>("there is np items.");
        return new Tresponse<>(outItems);
    }

    @Override
    public Tresponse<Item> addItem(int supplierBN, String name , double price, int typeID, LocalDate expirationDate) {
        BusinessLayer.SupplierBusiness.Item item;
        try{
            item = supplierController.addItem(supplierBN,name , price, typeID, expirationDate);
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        return new Tresponse<>(new Item(item));
    }

    @Override
    public response removeItem(int supplierBN , int itemId) {
        try{
            supplierController.removeItem(supplierBN , itemId);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response removeItemFromRegularOrder(int supplierBN, int orderId, int itemId) {
        try{
            supplierController.removeItemFromRegularOrder(supplierBN , orderId , itemId);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response removeAmountItemFromRegularOrder(int supplierBN, int orderId, int itemId, int amount) {
        try{
            supplierController.removeAmountItemFromRegularOrder(supplierBN , orderId , itemId , amount);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public Tresponse<Order> addRegularOrder(int supplierBN,int branchID) {
        BusinessLayer.SupplierBusiness.Order order;
        try {
            order = supplierController.addRegularOrder(supplierBN, branchID);
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        return new Tresponse<>(new Order(order));
    }


    @Override
    public response addConstantOrder(int supplierBN,int branchID , Hashtable<Integer, Integer> items) {
        try {
            supplierController.addConstantOrder(supplierBN, branchID , items);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    public Tresponse<Order> addNeededOrder(int typeID,int neededAmount, int branchID) {
        BusinessLayer.SupplierBusiness.Order order;
        try {
            order = supplierController.addNeededOrder(typeID, neededAmount, branchID);
            if (order == null) {
                return new Tresponse<>("ERROR: unsuccessful adding");
            }
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        return new Tresponse<>(new Order(order));
    }

    @Override
    public response addItemToOrder(int supplierBN, int orderId, int itemId , int amount) {
        try{
            supplierController.addItemToOrder(supplierBN, orderId, itemId , amount);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response removeOrder(int supplierBN, int orderId) {
        try{
            supplierController.removeOrder(supplierBN, orderId);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public Tresponse<Order> showOrderOfSupplier(int supplierBN, int orderId) {
        BusinessLayer.SupplierBusiness.Order order;
        try {
            order = supplierController.showOrderOfSupplier(supplierBN, orderId);
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        return new Tresponse<>(new Order(order));
    }

    @Override
    public Tresponse<List<Order>> showAllOrdersOfSupplier(int supplierBN) {
        List<BusinessLayer.SupplierBusiness.Order> orders;
        List<Order> outOrder = new LinkedList<>();
        try {
            orders = supplierController.showAllOrdersOfSupplier(supplierBN);
            for(BusinessLayer.SupplierBusiness.Order order : orders){
                outOrder.add(new Order(order));
            }
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        if(outOrder.size() == 0) return new Tresponse<>("supplier does not have any orders.");
        return new Tresponse<>(outOrder);
    }

    @Override
    public Tresponse<Order> showTotalAmount(int supplierBN, int orderId) {
        BusinessLayer.SupplierBusiness.Order order;
        try{
            order = supplierController.showTotalAmount(supplierBN, orderId);
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        return new Tresponse<>(new Order(order));
    }

    @Override
    public Tresponse<Order> showDeliverTime(int supplierBN, int orderId) {
        BusinessLayer.SupplierBusiness.Order order;
        try{
            order = supplierController.showDeliverTime(supplierBN, orderId);
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        return new Tresponse<>(new Order(order));
    }

    @Override
    public response updateDeliverTime(int supplierBN, int orderId, LocalDate deliverTime){
        try{
            supplierController.updateDeliverTime(supplierBN, orderId, deliverTime);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response addQuantityDocument(int supplierBN, int itemId, int minimalAmount, int discount){
        try{
            supplierController.addQuantityDocument(supplierBN, itemId, minimalAmount,discount);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response removeQuantityDocument(int supplierBN, int itemId) {
        try{
            supplierController.removeQuantityDocument(supplierBN , itemId);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public Tresponse<QuantityDocument> showQuantityDocument(int supplierBN, int itemId) {
        BusinessLayer.SupplierBusiness.QuantityDocument quantityDocument;
        try {
            quantityDocument =  supplierController.showQuantityDocument(supplierBN, itemId);
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        return new Tresponse<>(new QuantityDocument(quantityDocument));
    }

    @Override
    public response updateMinimalAmountOfQD(int supplierBN, int itemId, int minimalAmount) {
        try{
            supplierController.updateMinimalAmountOfQD(supplierBN, itemId, minimalAmount);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response updateDiscountOfQD(int supplierBN, int itemId, int discount) {
        try{
            supplierController.updateDiscountOfQD(supplierBN, itemId, discount);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response addSupplierAgreement(int supplierBN, int minimalAmount, int discount, boolean constantTime, boolean shipToUs) {
        try{
            supplierController.addSupplierAgreement(supplierBN, minimalAmount, discount, constantTime, shipToUs);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public Tresponse<SupplierAgreement> showSupplierAgreement(int supplierBN) {
        BusinessLayer.SupplierBusiness.SupplierAgreement supplierAgreement;
        try {
            supplierAgreement = supplierController.showSupplierAgreement(supplierBN);
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        if (supplierAgreement != null) {
            return new Tresponse<>(new SupplierAgreement(supplierAgreement));
        }
        return new Tresponse<>("ERROR: There is no such supplier agreement");
    }

    @Override
    public response updateMinimalAmountOfSA(int supplierBN, int minimalAmount) {
        try{
            supplierController.updateMinimalAmountOfSA(supplierBN, minimalAmount);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response updateDiscountOfSA(int supplierBN, int discount) {
        try{
            supplierController.updateDiscountOfSA(supplierBN, discount);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response updateConstantTime(int supplierBN, boolean constantTime) {
        try{
            supplierController.updateConstantTime(supplierBN, constantTime);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response updateShipToUs(int supplierBN, boolean ShipToUs) {
        try{
            supplierController.updateShipToUs(supplierBN, ShipToUs);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response updatePrice(int supplierBN, int itemId, double price) {
        try{
            supplierController.updatePrice(supplierBN , itemId , price);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }
}
