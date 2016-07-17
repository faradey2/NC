import java.util.*;

public class BusinessCardImpl implements BusinessCard {

    String name;
    String lastName;
    String department;
    Calendar birthDate;
    char gender;
    int salary;
    String phoneNumber;

    public void setInfo (String name,String lastName,String department, Calendar birthDate,char gender,int salary,String phoneNumber){
        this.name = name;
        this.lastName = lastName;
        this.department = department;
        this.birthDate = birthDate;
        this.gender = gender;
        this.salary = salary;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public BusinessCard getBusinessCard(Scanner scanner) {
        String line = scanner.nextLine();
        String[] parse = line.split(";");
//        Name;Last name;Department;Birth date;Gender;Salary;Phone number
        if (parse.length < 7) throw new NoSuchElementException();
        if (!parse[3].matches("[0-9]{2}-[0-9]{2}-[0-9]{4}")) throw new InputMismatchException();
        if (!(parse[4].equals("F") || parse[4].equals("M"))) throw new InputMismatchException();
        if (!parse[5].matches("[0-9]{1,9}")|| Integer.parseInt(parse[5]) < 0) throw new InputMismatchException();
        if (!parse[6].matches("[0-9]{10}")) throw new InputMismatchException();

        BusinessCardImpl bc = new BusinessCardImpl();


        String[] bd = parse[3].split("-");
        int dd = Integer.parseInt(bd[0]);
        int mm = Integer.parseInt(bd[1]);
        int yy = Integer.parseInt(bd[2]);
        if(dd<0||mm<0||dd>31||mm>12)    throw new InputMismatchException();
        Calendar calendar = new GregorianCalendar(yy, mm, dd);
        bc.setInfo(parse[0], parse[1], parse[2], calendar, parse[4].charAt(0), Integer.parseInt(parse[5]), parse[6]);
        return bc;
    }

    @Override
    public String getEmployee() { return name + ' ' + lastName; }

    @Override
    public String getDepartment() {
        return department;
    }

    @Override
    public int getSalary() {
        return salary;
    }

    @Override
    public int getAge() {
        GregorianCalendar now = new GregorianCalendar();
        int age = now.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
        if(now.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR))
            age--;
        return age;
    }

    @Override
    public String getGender() {
        if(gender == 'F')return "Female";
        else    return "Male";
    }

    @Override
    public String getPhoneNumber() {
        return "+7 "+phoneNumber.substring(0,3)+'-'+phoneNumber.substring(3,6)+'-'+phoneNumber.substring(6,8)+'-'+phoneNumber.substring(8,10);
    }
}