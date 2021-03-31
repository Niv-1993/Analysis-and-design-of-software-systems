package Business.EmployeePKG;

import Business.Type.RoleType;
import Business.Type.ShiftType;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Regular extends Employee{
    final static Logger log = Logger.getLogger(Regular.class);
    public Regular(int EID, String name, int[] bankDetails, int salary, RoleType role, LocalDate startWorkDate, int[] terms) {
        super(EID, name, bankDetails, salary, role, startWorkDate, terms);
    }

    @Override
    public Employee addEmployee(int newEID, String name, int[] bankDetails, int salary, RoleType role, LocalDate startWorkDate, int[] terms, Map<Integer, Employee> employees) throws Exception {
        throw new Exception("You are not allowed to add any new employee to this branch");
    }

    @Override
    public void fireEmployee(int fireEID, Map<Integer, Employee> employees) throws Exception {
        throw new Exception("You are not allowed to fire any employee from this branch");
    }

    @Override
    public void updateEmployeeName(Employee updateE, String newName) throws Exception {
        throw new Exception("You are not allowed to change the name of any employee from this branch");
    }

    @Override
    public void updateEmployeeSalary(Employee updateE, int newSalary) throws Exception {
        throw new Exception("You are not allowed to change the salary of any employee from this branch");
    }

    @Override
    public void updateEmployeeBANum(Employee updateE, int newAccountNumber) throws Exception {
        throw new Exception("You are not allowed to update bank account of any employee from this branch");
    }

    @Override
    public void updateEmployeeBABranch(Employee updateE, int newBranch) throws Exception {
        throw new Exception("You are not allowed to update bank account of any employee from this branch");
    }

    @Override
    public void updateEmployeeBAID(Employee updateE, int newBankID) throws Exception {
        throw new Exception("You are not allowed to update bank account of any employee from this branch");
    }

    @Override
    public void updateEmployeeEducationFund(Employee updateE, int newEducationFund) throws Exception {
        throw new Exception("You are not allowed to update terms of employment of any employee from this branch");
    }

    @Override
    public void updateEmployeeDaysOff(Employee updateE, int newAmount) throws Exception {
        throw new Exception("You are not allowed to update terms of employment of any employee from this branch");
    }

    @Override
    public void updateEmployeeSickDays(Employee updateE, int newAmount) throws Exception {
        throw new Exception("You are not allowed to update terms of employment of any employee from this branch");
    }

    @Override
    public Business.ShiftPKG.Shift createShift(Map<RoleType, Integer> rolesAmount, LocalDate date, ShiftType shiftType, Map<RoleType, List<String[]>> employees, ShiftController shiftController) throws Exception {
        throw new Exception("You are not allowed to create shifts in this branch");
    }

    @Override
    public List<Business.ShiftPkG.Shift> getShiftsAndEmployees(ShiftController shiftController) throws Exception {
        throw new Exception("You are not allowed to request this kind of data in this branch");
    }


    @Override
    public void removeEmpFromShift(int SID, int removeEID, ShiftController shiftController) throws Exception {
        throw new Exception("You are not allowed to remove anyone from shifts");
    }

    @Override
    public void addEmpToShift(int SID, int addEID, RoleType role, String name, ShiftController shiftController) throws Exception {
        throw new Exception("You are not allowed to add anyone to shifts");
    }

    @Override
    public void updateAmountRole(int SID, RoleType role, int newAmount, ShiftController shiftController) throws Exception {
        throw new Exception("You are not allowed to update any shift");
    }

    @Override
    public void updateEmpRole(int SID, int EID, RoleType newRole, ShiftController shiftController) throws Exception {
        throw new Exception("You are not allowed to update any shift");
    }
}
