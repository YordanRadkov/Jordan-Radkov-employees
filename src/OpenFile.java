import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-HH-dd");

        if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            //get the file
            java.io.File file = fileChooser.getSelectedFile();

            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(", ");
                employeeId = values[0];
                projectId = values[1];
                dateFrom = values[2];
                dateTo = values[3];
                if(dateTo.equals("NULL")){
                    Date date = new Date();
                    dateTo = dateFormat.format(date);
                }
                EmployeesList.add(employee = new Employee(employeeId, projectId, dateFrom, dateTo));
                System.out.println(employeeId + " " + projectId + " " + dateFrom + " " + dateTo);
            }

            employee.findCommonProjects(EmployeesList);
            employee.removeRepeatedElement();
            employee.sortedByProjectIdAndByDaysWorked();
            employee.sumDaysWorked();

            System.out.println();
            System.out.println("Processed file information");

            employee.printInformation();
            System.out.println();
            employee.findingPairOfEmployees();
        }
    }
}
