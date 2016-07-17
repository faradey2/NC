
public class EmployeeImpl implements Employee{
    private String _firstName;
    private String _lastName;
    private int _salary;
    private Employee _manager;
    private Employee _topManager;

    public EmployeeImpl(){
        _firstName = "name";
        _lastName = "surname";
        _salary = 1000;
    }

    @Override
    public int getSalary() {
        return _salary;
    }

    @Override
    public void increaseSalary(int value) {
        if(value < 0) return;
        _salary += value;
    }

    @Override
    public String getFirstName() {
        return _firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        _firstName = firstName;
    }

    @Override
    public String getLastName() {
        return _lastName;
    }

    @Override
    public void setLastName(String lastName) {
        _lastName = lastName;
    }

    @Override
    public String getFullName() {
        return _firstName + ' ' + _lastName;
    }

    @Override
    public void setManager(Employee manager) {
        _manager = manager;
    }

    @Override
    public String getManagerName() {
        try{
            String m = _manager.getFirstName() + ' ' + _manager.getLastName();
            return m;
        }
        catch (Exception e){
            return "No manager";
        }
    }


    @Override
    public Employee getTopManager() {
        if(this.getManagerName() == "No manager")
            return this;
        else return _manager.getTopManager();
    }
}
