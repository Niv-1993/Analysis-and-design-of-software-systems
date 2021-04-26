package BusinessLayer.SupplierBusiness.facade.outObjects;

public class SupplierAgreement {
    private final int minimalAmount;
    private final int discount;
    private final boolean constantTime;
    private final boolean shipToUs;


    public SupplierAgreement(BusinessLayer.SupplierBusiness.SupplierAgreement SA) {
        minimalAmount = SA.getMinimalAmount();
        discount = SA.getDiscount();
        constantTime = SA.getConstantTime();
        shipToUs = SA.getShipToUs();
    }

    public String toString() {
        return "Supplier Agreement: \n" +
                "\tminimal amount: " + minimalAmount + "\n" +
                "\tdiscount: " + discount + "\n" +
                "\tconstant time: " + constantTime + " \n" +
                "\tship to us: " + shipToUs;
    }

    public String toStringShipToUs(){
        return "\tship to us: " + shipToUs + "\n";
    }
}
