package BussniesLayer;

public class Item{
    private int supplierBN;
    private String category;
    private int itemId;
    private QuantityDocument quantityDocument;
    private double price;

    public Item(int supplierBN , String category , int itemId){
        this.supplierBN = supplierBN;
        this.category = category;
        this.itemId = itemId;
        quantityDocument = null;
        price = 100.00;
    }

    public void addQuantityDocument(int supplierBN, int itemId, int minimalAmount, int discount) {
        quantityDocument = new QuantityDocument(supplierBN, itemId, minimalAmount, discount);
    }

    public void removeQuantityDocument() {
        quantityDocument = null;
    }

    public void showQuantityDocument(int itemId) {

    }

    public void updateMinimalAmountOfQD(int minimalAmount) {
        quantityDocument.updateMinimalAmountOfQD(itemId , minimalAmount);
    }

    public void updateDiscountOfQD(int discount) {
        quantityDocument.updateDiscountOfQD(itemId , discount);
    }

    public int getItemId() {
        return itemId;
    }

    public QuantityDocument getQuantityDocument(){
        return quantityDocument;
    }

    public double getPrice() {
        return price;
    }

}
