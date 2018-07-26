import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


/**
 * Class OpenFile reads the file and processes the read information
 *
 */

public class OpenFile {

    //Declare Variable
    JFileChooser fileChooser = new JFileChooser();

    /**
     * File processing
     * Get the file
     * Read the file.
     * the received information assigns to the variables.
     * records objects in the list
     * invokes employee class methods to perform the required processing
     */

    public  void pickMe() throws IOException {
        String employeeId;
        String projectId;
        String dateFrom;
        String dateTo;
        String line = null;
        Employee employee = null;
        ArrayList<Employee> EmployeesList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            //get the file
            java.io.File file = fileChooser.getSelectedFile();

            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            System.out.println("The contents of the file");
            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(", ");
                employeeId = values[0];
                projectId = values[1];
                dateFrom = values[2];
                dateTo = values[3];
                if(dateTo.equals("NULL")){
                    LocalDate today = LocalDate.now();
                    dateTo = today.toString();
                }
                EmployeesList.add(employee = new Employee(employeeId, projectId, dateFrom, dateTo));
                System.out.println(employeeId + " " + projectId + " " + dateFrom + " " + dateTo);
            }


            employee.sumEquals(EmployeesList);

            employee.findCommonProjects(EmployeesList);

            employee.removeRepeatedElement();

            employee.sortedByProjectIdAndByDaysWorked();

            System.out.println("Common days worked");
            employee.findCommonDaysWorked();

            employee.sumDaysWorkedTogether();

            employee.sortByDaysWorked();


            employee.fintPairEmployees();

            System.out.println("Finding the pair of workers which have worked together for the longest time and their common projects");
            employee.printResultList();
        }
    }
}
