package BusinessLayer.SupplierBusiness;

import DAL.DALObject;
import DAL.DalSuppliers.DalQuantityDocument;
import DAL.DalSuppliers.DalSupplierCard;
import DAL.Mapper;
import Utility.Tuple;
import Utility.Util;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class QuantityDocument {
    DalQuantityDocument dalQuantityDocument;
    final static Logger log=Logger.getLogger(QuantityDocument.class);

    public QuantityDocument(int itemId, int minimalAmount , int discount, int branchId){
        //dalQuantityDocument = Util.initDal(DalQuantityDocument.class, 0 , minimalAmount, discount);
        List<Tuple<Object,Class>> list=new ArrayList<>();
        list.add(new Tuple<>(itemId,Integer.class));
        list.add(new Tuple<>(minimalAmount,Double.class));
        list.add(new Tuple<>(discount,String.class));
        list.add(new Tuple<>(branchId,Integer.class));
        Mapper map=Mapper.getMap();
        map.setItem(DalQuantityDocument.class,list);
        List<Integer> keyList=new ArrayList<>();
        keyList.add(itemId);
        DALObject check =map.getItem(DalQuantityDocument.class ,keyList);
        if (DalQuantityDocument.class==null || check==null ||(check.getClass()!=DalQuantityDocument.class)){
            String s="the instance that return from Mapper is null";
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
        else{
            log.info("create new Object");
            dalQuantityDocument = (DalQuantityDocument) check;
        }
    }

    public void updateMinimalAmountOfQD(int minimalAmount) throws Exception {
        if(minimalAmount < 0) throw new Exception("minimal amount must be a positive number");
        dalQuantityDocument.updateMinimalAmountOfQD(minimalAmount);
    }

    public void updateDiscountOfQD(int discount) throws Exception {
        if(discount < 0) throw new Exception("discount amount must be a positive number");
        dalQuantityDocument.updateDiscountOfQD(discount);
    }

    public int getMinimalAmount(){
        return dalQuantityDocument.getMinimalAmount();
    }

    public int getDiscount(){
        return dalQuantityDocument.getDiscount();
    }
}
