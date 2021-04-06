package BusinessLayer;


import BusinessLayer.Type.Category;
import BusinessLayer.Type.ProductType;
import BusinessLayer.Type.SaleDiscount;
import BusinessLayer.Type.SupplierDiscount;
import BusinessLayer.instance.Product;
import reports.Report;

import java.util.Date;
import java.util.List;

public interface iStoreController {
    //res: SC
    public int getID();
    public Report getWeeklyReport();
    public Report getWeeklyReport(int... c);
    public Report getNeededReport();
    public Report getWasteReport();

    public int counterCategory();
    public Category getCategory(int catID);
    public Category addCategory(String name, int superCategory);
    public List<Integer> getCategories();
    public void addProductType(String name, int minAmount, float basePrice, String producer, int supID, int category);
    public List<Integer> getProductTypes();
    public ProductType getProductTypeInfo(int id);
    public int getShelvesAmount(int typeID);
    public int shelvesAmountExist();
    public int storageAmountExist();
    public int getStorageAmount(int typeID);

    //res: Category
    public Category addCategory(String name);
    //res:PT+SC
    public void addSaleProductDiscount(int productTypeID, float percent, Date start, Date end);
    public int counterDiscount();
    public void addSaleCategoryDiscount(int productTypeID, float percent, Date start,Date end);
    public void addSupplierDiscount(int categoryID, float percent, Date start,Date end,int supId);
    public List<SupplierDiscount> getSupplierDiscounts(int typeID);
    public List<SaleDiscount> getSaleDiscounts(int typeID);

    //res:Category
    public void editCategory(int Id, String name, int superCategory);
    public void editCategory(int Id, String name);
    //res:PT
    public void editProductType(int id,String name, int minAmount, float basePrice, String producer, int supID, int category);

    //res: InstanceController+PT
    public void addProduct(int typeID, Date expiration );
    public void removeProduct(int ID);
    public void reportDamage(int ID);
    public Product getProductInfo(int ID);
    public void relocateProduct(int ID, boolean toStorage, int targetShelf);


}
