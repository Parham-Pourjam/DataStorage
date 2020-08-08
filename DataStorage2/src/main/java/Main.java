public class Main {
    public static void main(String[] args) {



        Employee employee = new Employee();

        try {
            employee.readData();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
