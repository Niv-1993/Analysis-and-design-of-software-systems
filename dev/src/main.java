import BusinessLayer.StockBusiness.Fcade.StorageService;
import BusinessLayer.StockBusiness.Fcade.outObjects.ProductType;
import BusinessLayer.StockBusiness.StoreController;
import DAL.DalStock.DALStoreController;
import DAL.Mapper;
import Presentation.StockCLI;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class main {
    final static Logger log=Logger.getLogger(main.class);
    public static void main(String[] args) {

       // StockCLI cli= new StockCLI();
       // cli.setup();

        Mapper map = Mapper.getMap("test.db");
        List<Integer> a=new ArrayList<>();
        a.add(1);
        DALStoreController sc=(DALStoreController) map.getItem(new DALStoreController(),a );
    }
}
