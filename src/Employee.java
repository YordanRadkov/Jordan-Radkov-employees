
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Class Employee defines methods to process information that will be extracted from a file
 *
 */
public class Employee {

    // Declare variable
    public String employeeId;
    public String projectId;
    public String dateFromStr;
    public String dateToStr;
    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // format for date
    public LocalDate dateFrom;
    public LocalDate dateTo;


    public long days;      //variable for the days that an employee worked
    public double sumDays;  //variable for total days worked together
    public ArrayList<Employee> commonProjectsList = new ArrayList<>(); // list for common projects
    public static ArrayList<Employee> resultList = new ArrayList<>();  // list for the employees who have worked the most together
    public Employee emp;
    public static ArrayList<Employee> employeesList = new ArrayList<>();

    //variables vor new constructor
    public String empIdNew1; // variable for id for first employee
    public String empIdnew2; // variable for id for second employee
    public String projectIdNew; // variable for id for project
    public Long sumDaysWorkded; // variable for sum for days worked




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
        this.sumDays = 0;



        this.dateFrom = LocalDate.parse(dateFromStr, formatter);
        this.dateTo = LocalDate.parse(dateToStr, formatter);
        this.days = ChronoUnit.DAYS.between(dateFrom, dateTo);
    }//end constructor


    /**
     * constructor for employee pairs
     * @param empIdNew1
     * @param empIdnew2
     * @param projectIdNew
     * @param sumDaysWorkded
     */
    public Employee(String empIdNew1, String empIdnew2, String projectIdNew, Long sumDaysWorkded) {

        this.empIdNew1 = empIdNew1;
        this.empIdnew2 = empIdnew2;
        this.projectIdNew = projectIdNew;
        this.sumDaysWorkded = sumDaysWorkded;

    }

    /**
     * Sum the days worked for employees which work in one project more than one
     * removes the repeating element
     * @param list
     */

    public void sumEquals(ArrayList<Employee> list) {

        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if ((list.get(i).employeeId.equals(list.get(j).employeeId)) && list.get(i).projectId.equals(list.get(j).projectId)) {
                    list.get(i).days = list.get(i).days + list.get(j).days;
                    list.remove(j);
                }
            }
        }
        
    }//end 

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
     * find days worked together
     * put them in array list
     */

    public void findCommonDaysWorked() {
        long sumDays = 0;

        for (int i = 0; i < commonProjectsList.size(); i++) {
            for (int j = i + 1; j < commonProjectsList.size(); j++) {
                if (commonProjectsList.get(i).projectId.equals(commonProjectsList.get(j).projectId)) {
                    if (commonProjectsList.get(i).days > commonProjectsList.get(j).days) {
                        sumDays = commonProjectsList.get(j).days;
                    } else if (commonProjectsList.get(i).days == commonProjectsList.get(j).days) {
                                sumDays = commonProjectsList.get(i).days;
                    } else if (commonProjectsList.get(i).days < commonProjectsList.get(j).days)
                                sumDays = commonProjectsList.get(i).days;

                    String id1 = commonProjectsList.get(i).employeeId;
                    String id2 = commonProjectsList.get(j).employeeId;
                    String project = commonProjectsList.get(i).projectId;

                    employeesList.add(emp = new Employee(id1, id2, project, sumDays));
                }
            }
        }

        printAfterProcessing();
    }//end findCommonDaysWorked

    /**
     * Sum days worked of employees which work together in common projects
     */

    public void sumDaysWorkedTogether() {

        for (int i = 0; i < employeesList.size(); i++) {
            for (int j = i + 1; j < employeesList.size(); j++){
                if ((employeesList.get(i).empIdNew1.equals(employeesList.get(j).empIdNew1))
                        && employeesList.get(i).empIdnew2.equals(employeesList.get(j).empIdnew2)){

                    employeesList.get(i).sumDaysWorkded = employeesList.get(i).sumDaysWorkded + employeesList.get(j).sumDaysWorkded;
                }
            }
        }
    }//end sumDaysWorkedTogether


    /**
     * sort by days worked
     */
    public void sortByDaysWorked() {

        Comparator<Employee> bySuma = (p1, p2) -> Long.compare(p1.sumDaysWorkded, p2.sumDaysWorkded);
        employeesList.sort(bySuma.reversed());

    } // end sortByDaysWorked

    /**
     * Finds the pair of employees which work for the longest time together
     */

    public void fintPairEmployees(){


        Long max = employeesList.get(0).sumDaysWorkded;
        int index = 0;

        for (int i = 0; i < employeesList.size(); i++) {
            if (employeesList.get(i).sumDaysWorkded > max) {
                max = employeesList.get(i).sumDaysWorkded;
                index = i;
            }
        }

        // add the projects that the pair of employees worked together
        for(int i=0; i<employeesList.size(); i++){
            if((employeesList.get(index).empIdNew1.equals(employeesList.get(i).empIdNew1) && employeesList.get(index).empIdnew2.equals(employeesList.get(i).empIdnew2))){
                resultList.add(employeesList.get(i));
            }

        }

    } // end findPairEmployees

    public void printAfterProcessing(){

        for (int i = 0; i < employeesList.size(); i++) {
            System.out.println(employeesList.get(i).empIdNew1 + " " + employeesList.get(i).empIdnew2 + " " + employeesList.get(i).projectIdNew + " "
                               + employeesList.get(i).sumDaysWorkded);
        }
    }//end printAfterProcessing

    public void printResultList(){

        for(int i=0; i<resultList.size(); i++){
            System.out.println(resultList.get(i).empIdNew1 + " " + resultList.get(i).empIdnew2 + " "
                               + resultList.get(i).projectIdNew );
        }
    }

}//end class


