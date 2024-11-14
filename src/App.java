import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class App {
    private JPanel pnlMain;
    private JRadioButton rbCustomer;
    private JRadioButton rbClerk;
    private JRadioButton rbManager;
    private JTextField tfName;
    private JTextField tfAge;
    private JTextField tfMonths;
    private JTextField tfSalary;
    private JTextField tfLoad;
    private JTextArea taPersons;
    private JButton btnSavePerson;
    private JButton btnClear;
    private JButton btnSayHi;
    private JButton btnLoadPerson;
    private JButton btnReward;
    private JButton btnSave;
    private JButton btnLoad;

    private final List<Person> persons;

    public App() {
        persons = new ArrayList<>();
        setupUI();
        setupListeners();
        taPersons.setEditable(false);
    }

    private void setupUI() {
        JFrame frame = new JFrame("Person Manager");
        frame.setContentPane(pnlMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void setupListeners() {
        btnSavePerson.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                savePerson();
            }
        });

        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        btnSayHi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sayHi();
            }
        });

        btnLoadPerson.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadPerson();
            }
        });

        btnReward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                giveReward();
            }
        });

        rbCustomer.addActionListener(e -> toggleEmployeeFields(false));
        rbClerk.addActionListener(e -> toggleEmployeeFields(true));
        rbManager.addActionListener(e -> toggleEmployeeFields(true));
    }

    private void savePerson() {
        String name = tfName.getText();
        String ageText = tfAge.getText();

        try {
            int age = Integer.parseInt(ageText);
            String role = getSelectedRole();

            if (role != null && !name.isEmpty()) {
                Person person = createPerson(name, age, role);
                if (person != null) {
                    persons.add(person);
                    taPersons.append(person.toString() + "\n");
                    clearFields();
                }
            } else {
                showError("Please enter a name and select a role.");
            }
        } catch (NumberFormatException e) {
            showError("Invalid age input. Please enter a valid number.");
            tfAge.setText("");
        }
    }

    private void loadPerson() {
        String loadText = tfLoad.getText();

        try {
            int index = Integer.parseInt(loadText);
            if (index < 0 || index >= persons.size()) throw new IndexOutOfBoundsException();

            Person person = persons.get(index);
            tfName.setText(person.getName());
            tfAge.setText(String.valueOf(person.getAge()));

            if (person instanceof Customer) {
                rbCustomer.setSelected(true);
                toggleEmployeeFields(false);
            } else if (person instanceof Clerk) {
                rbClerk.setSelected(true);
                toggleEmployeeFields(true);
                tfMonths.setText(String.valueOf(((Clerk) person).monthsWorked));
                tfSalary.setText(String.valueOf(((Clerk) person).salary));
            } else if (person instanceof Manager) {
                rbManager.setSelected(true);
                toggleEmployeeFields(true);
                tfMonths.setText(String.valueOf(((Manager) person).monthsWorked));
                tfSalary.setText(String.valueOf(((Manager) person).salary));
            }

        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            showError("Invalid input. Please enter a valid index.");
        }
    }

    private void toggleEmployeeFields(boolean enable) {
        tfMonths.setEnabled(enable);
        tfSalary.setEnabled(enable);
    }

    private String getSelectedRole() {
        if (rbCustomer.isSelected()) return "Customer";
        if (rbClerk.isSelected()) return "Clerk";
        if (rbManager.isSelected()) return "Manager";
        return null;
    }

    private void clearFields() {
        tfName.setText("");
        tfAge.setText("");
        tfMonths.setText("");
        tfSalary.setText("");
        tfLoad.setText("");
        rbCustomer.setSelected(false);
        rbClerk.setSelected(false);
        rbManager.setSelected(false);
    }

    private void sayHi() {
        String role = getSelectedRole();
        if (role != null) {
            JOptionPane.showMessageDialog(pnlMain, "Hello! You are logged in as " + role + ".");
        } else {
            showError("Please select a role first.");
        }
    }

    private void giveReward() {
        String loadText = tfLoad.getText();

        try {
            int index = Integer.parseInt(loadText);
            if (index < 0 || index >= persons.size()) throw new IndexOutOfBoundsException();

            Person person = persons.get(index);
            if (person instanceof Employee) {
                double reward = ((Employee) person).calculateThirteenthMonth();
                JOptionPane.showMessageDialog(pnlMain, person.getName() + "'s thirteenth month salary is: " + reward);
            } else {
                showError("Only employees are eligible for rewards.");
            }
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            showError("Invalid input. Please enter a valid index.");
        }
    }

    private Person createPerson(String name, int age, String role) {
        switch (role) {
            case "Customer":
                return new Customer(name, age);
            case "Clerk":
                int monthsWorkedClerk = Integer.parseInt(tfMonths.getText());
                double salaryClerk = Double.parseDouble(tfSalary.getText());
                return new Clerk(name, age, monthsWorkedClerk, salaryClerk);
            case "Manager":
                int monthsWorkedManager = Integer.parseInt(tfMonths.getText());
                double salaryManager = Double.parseDouble(tfSalary.getText());
                return new Manager(name, age, monthsWorkedManager, salaryManager);
            default:
                return null;
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(pnlMain, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App());
    }
}