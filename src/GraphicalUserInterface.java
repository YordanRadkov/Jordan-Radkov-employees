import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


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
        String[] tblHead={"Employee ID #1","Employee ID #2","Project ID","Days worked"};
        DefaultTableModel dtm=new DefaultTableModel(tblHead,0);
        JTable tbl=new JTable(dtm);

        Employee employee = null;
        Object[] rowData = new Object[4];

        int index=0;
        Long max = employee.resultList.get(0).sumDaysWorkded;

        for(int i=0; i<employee.resultList.size(); i++){
            if(employee.resultList.get(i).sumDaysWorkded > max){
                max = employee.resultList.get(i).sumDaysWorkded;
                index = i;
            }
        }

        for(int i=0; i<employee.resultList.size(); i++) {

            rowData[0] = employee.resultList.get(i).empIdNew1;
            rowData[1] = employee.resultList.get(i).empIdnew2;
            rowData[2] = employee.resultList.get(i).projectIdNew;
            rowData[3] = employee.resultList.get(index).sumDaysWorkded;
            dtm.addRow(rowData);

        }
        JScrollPane scrollPane = new JScrollPane(tbl);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(800, 400);
        frame.setVisible(true);
    }
}
