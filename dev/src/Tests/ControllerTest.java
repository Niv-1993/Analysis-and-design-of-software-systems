import BussinessLayer.*;
import DataLayer.*;
import ServiceLayer.Controller;
import ServiceLayer.Objects.DriverServiceDTO;
import ServiceLayer.Objects.TransportationServiceDTO;
import ServiceLayer.Objects.TruckServiceDTO;
import enums.Area;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.Date;
import java.time.*;
import java.util.stream.Stream;

class ControllerTest {
    private DataBase db;
    private Controller controller;
    private TransportationServiceDTO t;

    @BeforeEach
    void setUp() {

        db = DataBase.initForTests();
        LicenseDTO l1 = new LicenseDTO(1000);
        LicenseDTO l2 = new LicenseDTO(2000);
        LicenseDTO l3 = new LicenseDTO(3000);
        LicenseDTO l4 = new LicenseDTO(4000);
        LicenseDTO l5 = new LicenseDTO(5000);

        db.addLicense(l1);
        db.addLicense(l2);
        db.addLicense(l3);
        db.addLicense(l4);
        db.addLicense(l5);

        DriverDTO d1 = new DriverDTO(1, "Meir malka", l1);
        DriverDTO d2 = new DriverDTO(2, "Ami vanunu", l2);
        DriverDTO d3 = new DriverDTO(3, "Roi rozenberg", l3);
        DriverDTO d4 = new DriverDTO(4, "Avi rozen", l4);
        db.addDriver(d1);
        db.addDriver(d2);
        db.addDriver(d3);
        db.addDriver(d4);


        TruckDTO t1 = new TruckDTO(12345678, l1, 10000, 1500, "Jumpy");
        TruckDTO t2 = new TruckDTO(25954557, l2, 10000, 1000, "Kangoo");
        TruckDTO t3 = new TruckDTO(68465185, l3, 10000, 1200, "Sprinter");
        TruckDTO t4 = new TruckDTO(98775656, l5, 10000, 1000, "Rapid");

        db.addTruck(t1);
        db.addTruck(t2);
        db.addTruck(t3);
        db.addTruck(t4);

        ShippingAreaDTO south = new ShippingAreaDTO(Area.Sout);
        ShippingAreaDTO north = new ShippingAreaDTO(Area.North);
        ShippingAreaDTO central = new ShippingAreaDTO(Area.Central);

        db.addShippingArea(south);
        db.addShippingArea(north);
        db.addShippingArea(central);
        ItemDTO doritos = (new ItemDTO(9876, "Doritos"));
        ItemDTO tapuchips = (new ItemDTO(8569, "Tapuchips"));
        ItemDTO bamba = (new ItemDTO(7458, "Bamba"));
        ItemDTO nachos = (new ItemDTO(3685, "Nachos"));
        ItemDTO cocaCola = (new ItemDTO(1548, "Coca Cola"));
        ItemDTO water = (new ItemDTO(8759, "Water"));
        ItemDTO salt = (new ItemDTO(7895, "Salt"));
        ItemDTO sugar = (new ItemDTO(6258, "Sugar"));
        ItemDTO tea = (new ItemDTO(5489, "Tea"));
        ItemDTO eggsL = (new ItemDTO(3254, "Eggs L x12"));
        ItemDTO milk = (new ItemDTO(9875, "Milk"));

        db.addItem(doritos);
        db.addItem(tapuchips);
        db.addItem(bamba);
        db.addItem(nachos);
        db.addItem(cocaCola);
        db.addItem(water);
        db.addItem(salt);
        db.addItem(sugar);
        db.addItem(tea);
        db.addItem(eggsL);
        db.addItem(milk);

        AddressDTO ad1 = (new AddressDTO(9, "moshe rahim", "Holon"));
        AddressDTO ad2 = (new AddressDTO(19, "Hanna senesh", "kiryat gat"));
        AddressDTO ad3 = (new AddressDTO(90, "HaAvot", "Ramat Gan"));
        AddressDTO ad4 = (new AddressDTO(43, "Emek hasofrim ", "Netivot"));
        AddressDTO ad5 = (new AddressDTO(15, "shualey shimshon", "Ofaquim"));
        AddressDTO ad6 = (new AddressDTO(24, "Jabutinski", "Beer Sheva"));
        AddressDTO ad7 = (new AddressDTO(7, "Ha'Orgim", "Beer Sheva"));
        db.addAddress(ad1);
        db.addAddress(ad2);
        db.addAddress(ad3);
        db.addAddress(ad4);
        db.addAddress(ad5);
        db.addAddress(ad6);
        db.addAddress(ad7);

        SupplierDTO s1 = (new SupplierDTO("0527745862", "Amit Nahum", 9845, ad1, central));
        SupplierDTO s2 = (new SupplierDTO("0548569574", "Omer Shalom", 8542, ad2, north));
        SupplierDTO s3 = (new SupplierDTO("0506328574", "Ofer Neeman", 2648, ad3, south));
        BranchDTO b1 = (new BranchDTO("0506895718", "Yogev Halom", 1, ad4, central));
        BranchDTO b2 = (new BranchDTO("0528759462", "Ami Barlev", 2, ad5, central));

        db.addSupplier(s1);
        db.addSupplier(s2);
        db.addSupplier(s3);
        db.addBranch(b1);
        db.addBranch(b2);
        controller = Controller.initial();
        controller.clearTrans();
        t = controller.createNewTransportation();
    }


    //test 1:
    @DisplayName("should add a Truck")
    @ParameterizedTest
    @MethodSource("TrucksArgsAdd")
    void shouldAddTruck(int id,int lc,int maxWeight,int netWeight,String model) {
        TruckServiceDTO truck= new TruckServiceDTO(id,lc,maxWeight,netWeight,model);
        t.setTruck(truck);
        controller.setTruckOnTransportation(t);
        Assertions.assertEquals(truck, controller.getTransportation(t.getId()).getTruck());
    }
    private static Stream<Arguments>
    TrucksArgsAdd() {
        return Stream.of(Arguments.of(12345678,1000,10000,1500,"Jumpy"),Arguments.of(25954557, 2000, 10000, 1000, "Kangoo"),
                Arguments.of(68465185, 3000, 10000, 1200, "Sprinter"), Arguments.of(98775656, 5000, 10000, 1000, "Rapid")
        );
    }
    @DisplayName("Should fail to add truck")
    @ParameterizedTest
    @MethodSource("TrucksArgsNotAdd")
    void shouldNotAdd(int id,int lc,int maxWeight,int netWeight,String model) {
        TruckServiceDTO truck = new TruckServiceDTO(id,lc,maxWeight,netWeight,model);
        t.setTruck(truck);
        Exception exp = Assertions.assertThrows(IllegalArgumentException.class, () -> controller.setTruckOnTransportation(t));
        String mess = exp.getMessage();
        Assertions.assertTrue(mess.contains("Truck"));
    }
    private static Stream<Arguments>
    TrucksArgsNotAdd() {
        return Stream.of(Arguments.of(123456787,1000,10000,1500,"Jumpy"),Arguments.of(259545567, 2000, 10000, 1000, "Kangoo"),
                Arguments.of(684665185, 3000, 10000, 1200, "Sprinter"), Arguments.of(3, 4000, 10000, 1000, "Rapid")
        );
    }
    //test 2:
    @DisplayName("add Driver")
    @ParameterizedTest
    @MethodSource("addDriverArgs")
    void shouldAddDriver(int id,String name,int lc){
        //before checking
        t.setTruck(new TruckServiceDTO(12345678,1000,10000,1500,"Jumpy"));
        controller.setTruckOnTransportation(t);
        DriverServiceDTO expected = new DriverServiceDTO(id,name,lc);
        t.setDriver(expected);
        controller.setDriverOnTransportation(t);
        Assertions.assertEquals(expected,t.getDriver());
    }
    private static Stream<Arguments>
    addDriverArgs() {
        return Stream.of(Arguments.of(1, "Meir malka", 1000), Arguments.of(2, "Ami vanunu", 2000),
                Arguments.of(3, "Roi rozenberg", 3000), Arguments.of(4, "Avi rozen", 4000)
        );
    }

    @DisplayName("driver does not exist test")
    @ParameterizedTest
    @MethodSource("DriversTestParameters")
    void driverDontExist(int id,String name,int lc){
        DriverServiceDTO d = new DriverServiceDTO(id+20,name,lc);
        t.setDriver(d);
        t.setTruck(new TruckServiceDTO(12345678,1000,10000,1500,"Jumpy"));
        controller.setTruckOnTransportation(t);
        t.setDriver(d);
        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, ()->controller.setDriverOnTransportation(t));
        String msg = ex.getMessage();
        Assertions.assertTrue(msg.contains("exist"));
        Assertions.assertNotEquals(controller.getTransportation(t.getId()).getDriver(),t.getDriver());
        //not make it ok
        db.addDriver(new DriverDTO(id,name,new LicenseDTO(lc)));
        controller.addDriver(new Driver(id+20,name,new License(lc)));
        controller.setDriverOnTransportation(t);
        Assertions.assertEquals(t.getDriver(), controller.getTransportation(t.getId()).getDriver());
    }
    @DisplayName("driver before truck test")
    @ParameterizedTest
    @MethodSource("DriversTestParameters")
    void driverBeforeTruck(int id,String name,int lc) {
        DriverServiceDTO d = new DriverServiceDTO(id,name,lc);
        t.setDriver(d);
        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, ()->controller.setDriverOnTransportation(t));
        String msg = ex.getMessage();
        Assertions.assertTrue(msg.contains("truck"));
        //check that the driver didnt change
        Assertions.assertNotEquals(controller.getTransportation(t.getId()).getDriver(),t.getDriver());
        //set default truck
        t.setTruck(new TruckServiceDTO(12345678,1000,10000,1500,"Jumpy"));
        controller.setTruckOnTransportation(t);
        controller.setDriverOnTransportation(t);
        //after add a truck
        Assertions.assertEquals(controller.getTransportation(t.getId()).getDriver(),t.getDriver());
    }
    @DisplayName("mismatch driver license test")
    @ParameterizedTest
    @MethodSource("DriversTestParameters")
    void mismatchDriverLicense(int id,String name,int lc) {
        DriverServiceDTO d = new DriverServiceDTO(id,name,lc);
        t.setDriver(d);
        t.setTruck(new TruckServiceDTO(98775656, 5000, 10000, 1000, "Rapid"));
        controller.setTruckOnTransportation(t);
        t.setDriver(d);
        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, ()->controller.setDriverOnTransportation(t));
        String msg = ex.getMessage();
        Assertions.assertTrue(msg.contains("license"));
        //check that the driver didnt change
        Assertions.assertNotEquals(controller.getTransportation(t.getId()).getDriver(),t.getDriver());
        t.setTruck(new TruckServiceDTO(12345678,1000,10000,1500,"Jumpy"));
        controller.setTruckOnTransportation(t);
        controller.setDriverOnTransportation(t);
        //after add a truck
        Assertions.assertEquals(controller.getTransportation(t.getId()).getDriver(),t.getDriver());
    }
    private static Stream<Arguments>
    DriversTestParameters() {
        return Stream.of(Arguments.of(1, "Meir malka", 1000), Arguments.of(2, "Ami vanunu", 2000),
                Arguments.of(3, "Roi rozenberg", 3000), Arguments.of(4, "Avi rozen", 4000)
        );
    }

    @DisplayName("fail an success setDate")
    @ParameterizedTest
    @MethodSource("DateTestParameters")
    void dateTest(String fl,String sc){
        Assertions.assertNull(controller.getTransportation(t.getId()).getDate());
        LocalDate date = LocalDate.parse(fl);
        t.setDate(date);
        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () ->controller.setTransportationDate(t));
        String msg = ex.getMessage();
        Assertions.assertTrue(msg.contains("date"));

        LocalDate date1 = LocalDate.parse(sc);
        t.setDate(date1);
        controller.setTransportationDate(t);
        Assertions.assertEquals(controller.getTransportation(t.getId()).getDate(),date1);
    }
    private static Stream<Arguments>
    DateTestParameters() {
        return Stream.of(Arguments.of("2021-01-01", "2050-11-11"), Arguments.of(LocalDate.now().minusDays(1).toString(), LocalDate.now().toString())
        );
    }

    @DisplayName("fail an success setLeavingTime")
    @ParameterizedTest
    @MethodSource("LeavingTimeParameters")
    void leavingTimeTests(String sc,String fl){
        t.setDate(LocalDate.now());
        controller.setTransportationDate(t);

        Assertions.assertNull(controller.getTransportation(t.getId()).getLeavingTime());

        t.setLeavingTime(LocalTime.parse(fl));
        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () ->controller.setTransportationLeavingTime(t));

        String msg = ex.getMessage();
        Assertions.assertTrue(msg.contains("time"));

        t.setLeavingTime(LocalTime.parse(sc));
        controller.setTransportationLeavingTime(t);
        Assertions.assertEquals(controller.getTransportation(t.getId()).getLeavingTime(),t.getLeavingTime());

    }

    private static Stream<Arguments>
    LeavingTimeParameters() {
        return Stream.of(Arguments.of( "23:59",LocalTime.now().minusMinutes(1).toString()),Arguments.of( "23:59","00:01"));
    }

}