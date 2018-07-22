import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class Employee defines methods to process information that will be extracted from a file
 *
 */
public class Employee {

    // Declare variable
    public String employeeId;
    String projectId;
    String dateFromStr;
    String dateToStr;
    public static SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd"); // format for date

    public Date dateFrom;
    public Date dateTo;
    public long different;  //variable for different in two dates
    public float days;      //variable for the days that an employee worked
    public double sumDays;  //variable for total days worked together
    public ArrayList<Employee> commonProjectsList = new ArrayList<>(); // list for common projects
    public static ArrayList<Employee> resultList = new ArrayList<>();  // list for the employees who have worked the most together

    /**
     * Constructor
     * @param employeeId
     * @param projectId
     * @param dateFromStr
     * @param dateToStr
     */
    public Employee(String employeeId, String projectId, String dateFromStr, String dateToStr) {
        this.employeeId = employeeId;
        this.projectId = projectId;
        this.dateFromStr = dateFromStr;
        this.dateToStr = dateToStr;
        this.sumDays =0;


        try {
            this.dateFrom = dateFormat.parse(dateFromStr);
        } catch (ParseException e) {
            System.out.println("Incorrect date");
        }

        try {
            this.dateTo = dateFormat.parse(dateToStr);
        } catch (ParseException e) {
            System.out.println("Incorrect date");
        }

        this.different = dateTo.getTime() - dateFrom.getTime(); //different in milliseconds
        this.days = (different / (1000*60*60*24));   // convert milliseconds in days
    }//end constructor


    /**
     *
     * Find the common projects
     */

    public  void findCommonProjects( ArrayList<Employee> list ){

        for (int i = 0; i < list.size(); i++) {
            for (int j =i+ 1; j<list.size(); j++) {
                if (list.get(i).projectId.equals(list.get(j).projectId)){
                    commonProjectsList.add(list.get(i));
                    commonProjectsList.add(list.get(j));
                }
            }
        }

    }//end findCommonProjects

    /**
     * Remove repeated element from commonProjectList
     */
    public void removeRepeatedElement(){

        Set<Employee> primesWithoutDuplicates = new LinkedHashSet<Employee>(commonProjectsList);
        commonProjectsList.clear();
        commonProjectsList.addAll(primesWithoutDuplicates);

    }//end removeRepeatedElement()

    /**
     * Sort the employees by projectId from small to large and by days worked reversed
     */

    public void sortedByProjectIdAndByDaysWorked(){

        Comparator<Employee> byDays = (p1, p2) -> Float.compare(p1.days,p2.days);
        Comparator<Employee> byProjectId = (p1, p2) -> p1.projectId.compareTo(p2.projectId);
        commonProjectsList.sort(byProjectId.thenComparing(byDays.reversed()));

    }//end sortedByProjectIdAndByDaysWorked()

    /**
     * Sums of days worked for a pair of employees worked together on a joint project.
     * Because they are sorted by projects and days
     * we only sum up the neighboring employees by days worked
     * and record the sum with the employee with more days worked
     */

    public void sumDaysWorked(){

        for(int i = 1; i< commonProjectsList.size(); i++) {
            if (commonProjectsList.get(i-1).projectId.equals(commonProjectsList.get(i).projectId)) {
                commonProjectsList.get(i-1).sumDays = commonProjectsList.get(i).days+ commonProjectsList.get(i-1).days;
            }
        }
    }//end sumDaysWorked

    /**
     * Print information for all employees
     */
    public void printInformation(){

        for(Employee element : commonProjectsList){
            System.out.println(element.employeeId + " " + element.projectId + " " + element.dateFromStr +" "
                    + element.dateToStr + " " + (int)element.days + " " + (int)element.sumDays);
        }
    }

    /**
     * we are looking for the pair of employees who have worked together for the longest time together on a common project
     * finding the greatest sum of days worked together.
     * Take the highest sum index and print information about the employee with this index and the employee after it.
     * Add the employees in the result list.
     */
    public void findingPairOfEmployees(){

        double maxSumDays = 0;
        int indexEmployee =0;
        for(int i = 0; i < commonProjectsList.size(); i++){
            if(commonProjectsList.get(i).sumDays >maxSumDays){
                maxSumDays = commonProjectsList.get(i).sumDays;
                indexEmployee = i;
            }
        }

        System.out.println("Employees who have worked the most together in a joint projectId");

        System.out.println( commonProjectsList.get(indexEmployee).employeeId + " " + commonProjectsList.get(indexEmployee).projectId
                + " " + commonProjectsList.get(indexEmployee).dateFromStr + " " + commonProjectsList.get(indexEmployee).dateToStr + " "
                + (int) commonProjectsList.get(indexEmployee).days + " " + (int) commonProjectsList.get(indexEmployee).sumDays);

        System.out.println(commonProjectsList.get(indexEmployee+1).employeeId + " " + commonProjectsList.get(indexEmployee+1).projectId + " "
                + commonProjectsList.get(indexEmployee+1).dateFromStr + " " + commonProjectsList.get(indexEmployee+1).dateToStr + " "
                + (int) commonProjectsList.get(indexEmployee+1).days + " " + (int) commonProjectsList.get(indexEmployee).sumDays);


        resultList.add(commonProjectsList.get(indexEmployee));
        resultList.add(commonProjectsList.get(indexEmployee+1));

    }//end findingPairOfEmployees
}//end class


