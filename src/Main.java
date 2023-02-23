import java.sql.*;
import java.util.Scanner;


public class Main {

    public static void create(String[] ui){
        try {
            Connection connection= DriverManager.getConnection("jdbc:postgresql://localhost:5432/payrollDB", "postgres", "MJordan23");
            PreparedStatement statement = connection.prepareStatement("insert into employee (first_name, last_name, gender, date_of_birth) VALUES (?,?,?,?)");
            statement.setString(1, ui[0]);
            statement.setString(2, ui[1]);
            statement.setString(3, ui[2]);
            statement.setString(4, ui[3]);
            statement.executeUpdate();
            System.out.println("Employee added");

        }catch( Exception e){
            System.out.println(e);
            System.out.println("Unable to add employee");
        }
    }

    public static void create_pay(double hour_rate, double hours, double ot_hours, double reg_pay,double ot_pay, double gross_sal, double net_sal,int emp_id){
        try {
            Connection connection= DriverManager.getConnection("jdbc:postgresql://localhost:5432/payrollDB", "postgres", "MJordan23");
            PreparedStatement statement = connection.prepareStatement("insert into pay_route (hour_rate, hours, ot_hours, reg_pay,ot_pay, gross_sal,net_sal,emp_id) VALUES (?,?,?,?,?,?,?,?)");
            statement.setDouble(1, hour_rate);
            statement.setDouble(2, hours);
            statement.setDouble(3, ot_hours);
            statement.setDouble(4, reg_pay);
            statement.setDouble(5, ot_pay);
            statement.setDouble(6, gross_sal);
            statement.setDouble(7, net_sal);
            statement.setInt(8, emp_id);

//
            statement.executeUpdate();
            System.out.println("Employee pay route added.");

        }catch( Exception e){
            System.out.println(e);
            System.out.println("Unable to add employee pay route.");
        }
    }

    public static void edit_pay(double hour_rate, double hours, double ot_hours, double reg_pay,double ot_pay, double gross_sal, double net_sal,int emp_id){
        try {
            Connection connection= DriverManager.getConnection("jdbc:postgresql://localhost:5432/payrollDB", "postgres", "MJordan23");
            PreparedStatement statement = connection.prepareStatement("UPDATE pay_route SET hour_rate= ?, hours= ?, ot_hours= ?, reg_pay= ?,ot_pay= ?, gross_sal= ?,net_sal= ? WHERE emp_id = ?");
            statement.setDouble(1, hour_rate);
            statement.setDouble(2, hours);
            statement.setDouble(3, ot_hours);
            statement.setDouble(4, reg_pay);
            statement.setDouble(5, ot_pay);
            statement.setDouble(6, gross_sal);
            statement.setDouble(7, net_sal);
            statement.setInt(8, emp_id);

//
            statement.executeUpdate();
            System.out.println("Employee pay route has been modified.");

        }catch( Exception e){
            System.out.println(e);
            System.out.println("Unable to modify employee pay route.");
        }
    }

    public static void delete_emp(String first, String last, Integer empID ){
        try {
            Connection connection= DriverManager.getConnection("jdbc:postgresql://localhost:5432/payrollDB", "postgres", "MJordan23");
            PreparedStatement statement = connection.prepareStatement("DELETE from employee WHERE first_name= ? AND last_name = ?;" +
                    "DELETE from pay_route where emp_id = ?");

            statement.setString(1,first);
            statement.setString(2,last);
            statement.setInt(3,empID);

            statement.executeUpdate();
            System.out.println("Employee deleted");

        }catch( Exception e){
            System.out.println(e);
            System.out.println("Could not find employee to delete.");
        }
    };

    public static String login(String[] ui){
        try{
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/payrollDB, postgres, MJordan23");
            PreparedStatement statement = connection.prepareStatement("SELECT email, password FROM register");
            ResultSet result = statement.executeQuery();
            while(result.next()){
                if (result.getString("email").equals(ui[0]) && result.getString("password").equals(ui[1])){
                    return ui[0];
                };
            }
            result.close();
            statement.close();
            return "Unsuccessful";
        }catch (Exception e){
            System.out.println(e);
            System.out.println("Unable to login");
            return "Unsuccessful";
        }

    }
    public static void filter_emp(String first, String last){
        try{
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/payrollDB", "postgres", "MJordan23");
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM employee WHERE first_name = ? AND last_name = ?");
            while (rs.next())
            {
                System.out.println("Employee Found");
                System.out.println(rs.getString(1));
            }
            rs.close();
            st.close();
        }catch (Exception ex){
            System.out.println(ex);
            System.out.println("Unable to find employee.");
        }

    }

    public static void show_emps(){
        try{
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/payrollDB", "postgres", "MJordan23");
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT employee.*,pay_route.hour_rate, pay_route.hours, pay_route.ot_hours, pay_route.reg_pay, pay_route.ot_pay, pay_route.gross_sal,pay_route.net_sal from employee LEFT JOIN pay_route on employee.id = pay_route.emp_id");
            System.out.println("All Employees");
            while (rs.next())
            {
                System.out.println("---------------");
                //System.out.println("ID: " + rs.getString(1));
                if (rs.getInt(6) == 0) {
                    String s = String.format("Employee ID: %d\n Employee name: %s %s\n Gender: %s\n Date of Birth %s",
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5));
                    System.out.println(s);
                }else{
                    System.out.println("---------------");
                    String s = String.format("Employee ID: %d\n" +
                                    " Employee name: %s %s\n " +
                                    "Gender: %s\n" +
                                    " Date of Birth: %s\n" +
                                    " Hourly Rate: %d\n " +
                                    "Hours Worked: %d\n " +
                                    "Overtime Hours: %d\n " +
                                    "Regular Pay: %d\n " +
                                    "Overtime Pay: %d\n" +
                                    "Gross Pay: %d\n " +
                                    "Net Pay: %d",
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getInt(6),
                            rs.getInt(7),
                            rs.getInt(8),
                            rs.getInt(9),
                            rs.getInt(10),
                            rs.getInt(11),
                            rs.getInt(12)
                    );

                    System.out.println(s);
                }
            }
            rs.close();
            st.close();
        }catch (SQLException ex){
            ex.printStackTrace();
            System.exit(1);
        }

    }


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int ui;
        System.out.println("Welcome to Alpha Automation ");
        do {
            System.out.println("What action would you like to do?");
            System.out.println("1. Add Employee");
            System.out.println("2. Create Employees' Payroll");
            System.out.println("3. Edit Employees' Payroll");
            System.out.println("4. Delete Employee (All data) ");
            System.out.println("5. Show All Employees");
            System.out.println("6. Quit");
            System.out.print("> ");
            ui = input.nextInt();
            input.nextLine();
            switch (ui) {
                case 1:
                    System.out.print("Employee first name: ");
                    String emp_firstname = input.nextLine();

                    System.out.print("Employee last name: ");
                    String emp_lastname = input.nextLine();

                    System.out.print("Employee gender: ");
                    String emp_gender = input.nextLine();

                    System.out.print("Employee date of birth (yyyy-mm-dd) : ");
                    String emp_dob = input.nextLine();

                    create(new String[] {emp_firstname, emp_lastname, emp_gender, emp_dob});
                    break;

                case 2:
                    System.out.print("Enter Employees ID: ");
                    int empID = input.nextInt();
                    // ask the user for the employee's hourly rate
                    System.out.print("Enter hourly rate: ");
                    double hourlyRate = input.nextDouble();
                    // ask the user for the number of hours worked
                    System.out.print("Enter hours worked: ");
                    double hoursWorked = input.nextDouble();
                    // ask the user for any overtime hours worked
                    System.out.print("Enter overtime hours worked: ");
                    double overtimeHours = input.nextDouble();
                    // calculate the employee's gross pay
                    double regularPay = hoursWorked * hourlyRate;
                    double overtimePay = overtimeHours * (hourlyRate * 3);
                    double grossPay = regularPay + overtimePay;
                    // calculate the employee's net pay (assuming a flat 20% tax rate)
                    double netPay = grossPay * 0.8;
                    create_pay(hourlyRate,hoursWorked,overtimeHours,regularPay,overtimePay,grossPay,netPay, empID );
                    break;

                case 3:

                    System.out.print("Enter Employees ID: ");
                     empID = input.nextInt();
                    // ask the user for the employee's hourly rate
                    System.out.print("Enter hourly rate: ");
                     hourlyRate = input.nextDouble();
                    // ask the user for the number of hours worked
                    System.out.print("Enter hours worked: ");
                     hoursWorked = input.nextDouble();
                    // ask the user for any overtime hours worked
                    System.out.print("Enter overtime hours worked: ");
                     overtimeHours = input.nextDouble();
                    // calculate the employee's gross pay
                     regularPay = hoursWorked * hourlyRate;
                     overtimePay = overtimeHours * (hourlyRate * 3);
                     grossPay = regularPay + overtimePay;
                    // calculate the employee's net pay (assuming a flat 20% tax rate)
                     netPay = grossPay * 0.8;
                    edit_pay(hourlyRate,hoursWorked,overtimeHours,regularPay,overtimePay,grossPay,netPay, empID );
                    break;

                case 4:
                    System.out.print("Employee first name: ");
                    String emp_first = input.nextLine();

                    System.out.print("Employee last name: ");
                    String emp_last = input.nextLine();

                    System.out.print("Employee ID: ");
                    int emp_id = input.nextInt();
                    delete_emp(emp_first, emp_last, emp_id);
                    break;

                case 5:
                    show_emps();
                    break;

            }

        }while (ui != 6);
    }
}