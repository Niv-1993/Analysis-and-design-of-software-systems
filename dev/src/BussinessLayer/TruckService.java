package BussinessLayer;

import DataLayer.DataController;

import java.util.*;

//bar
public class TruckService {

    private final DataController dataController;
    private HashMap<Integer,Truck> trucks;

    public TruckService(){
        trucks=new HashMap<>();
        dataController=DataController.init();
    }
    public List<Truck> getTrucks() { return new ArrayList<Truck>(trucks.values());}


    public List<Truck> getTrucksByWeight(int add) throws Exception {
        List<Truck> t = new LinkedList<>();
        for (Map.Entry<Integer,Truck> entry: trucks.entrySet()) {
            Truck truck = entry.getValue();
            if(truck.getMaxWeight()>= truck.getNetWeight()+add){
                t.add(truck);
            }
        }
        if(t.isEmpty()) {
            throw new Exception("there is no compatible truck for mission");
        }
        return t;
    }

    @Override
    public String toString() {
        StringBuilder acc = new StringBuilder();
        for (Map.Entry<Integer,Truck> entry: trucks.entrySet()) {
            Truck t = entry.getValue();
            acc.append(t);
        }
        return acc.toString();
    }

    public void removeTruck(Truck tru){
        trucks.remove(tru);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TruckService that = (TruckService) o;
        return Objects.equals(trucks, that.trucks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trucks);
    }

    public Truck getTruck(int truckId) {
        if(!trucks.containsKey(truckId)){
            throw new IllegalArgumentException("truck with id: "+ truckId + "is not exist");
        }
        return trucks.get(truckId);
    }
}
