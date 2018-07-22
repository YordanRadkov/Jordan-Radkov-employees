import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;

/**
 * Class GraphicalUserInterface creates a user interface to work with the application and launches it
 */
public class GraphicalUserInterface {

    public JFrame frame = new JFrame();

    /**
     * Launch the application
     */

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    GraphicalUserInterface window = new GraphicalUserInterface();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }//end main()

    /**
     * Create the application
     */


    public GraphicalUserInterface() {
        createButton();
    }

    /**
     * Create the button
     */

    private void createButton() {

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JButton btnNewButton = new JButton("Open File");
        btnNewButton.setBounds(150, 80, 180, 43);

        frame.getContentPane().add(btnNewButton);
        frame.setSize(500, 250);
        frame.setVisible(true);

        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OpenFile of = new OpenFile();

                try {
                    of.pickMe();
                    maketable();

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    /**
     * Create table
     */

    public void maketable() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Employee employee = null;

        String str = employee.resultList.get(0).employeeId;
        String str2 = employee.resultList.get(1).employeeId;
        String str3 = employee.resultList.get(0).projectId;
        Double str4 = employee.resultList.get(0).sumDays;
        int change = str4.intValue();

        Vector<String> rowOne = new Vector<String>();
        rowOne.addElement(str);
        rowOne.addElement(str2);
        rowOne.addElement(str3);
        rowOne.addElement(Integer.toString(change));

        Vector<Vector> rowData = new Vector<Vector>();
        rowData.addElement(rowOne);

        Vector<String> columnNames = new Vector<String>();
        columnNames.addElement("Employee ID #1");
        columnNames.addElement("Employee ID #2");
        columnNames.addElement("Project ID");
        columnNames.addElement("Days worked");

        JTable table = new JTable(rowData, columnNames);

        JScrollPane scrollPane = new JScrollPane(table);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(800, 400);
        frame.setVisible(true);
    }
}
