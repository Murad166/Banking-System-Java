import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

class BankAccount {
    String name;
    int accountNumber;
    double balance;

    public BankAccount(String name, int accountNumber) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = 0.0;
    }

    public void deposit(double amount) {
        if (amount > 0) balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public String toString() {
        return "Account Number: " + accountNumber + ", Name: " + name + ", Balance: $" + balance;
    }
}

public class BankingSystem {
    private JFrame frame;
    private JTextField nameField, accField, amountField;
    private JTextArea outputArea;
    private HashMap<Integer, BankAccount> accounts = new HashMap<>();

    public BankingSystem() {
        frame = new JFrame("Banking System");
        frame.setSize(650, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Account Number:"));
        accField = new JTextField();
        inputPanel.add(accField);

        inputPanel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        inputPanel.add(amountField);

        JButton createBtn = new JButton("Create Account");
        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton viewBtn = new JButton("View Account");

        inputPanel.add(createBtn);
        inputPanel.add(depositBtn);
        inputPanel.add(withdrawBtn);
        inputPanel.add(viewBtn);

        frame.add(inputPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        frame.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // Action Listeners
        createBtn.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                int accNo = Integer.parseInt(accField.getText().trim());
                if (accounts.containsKey(accNo)) {
                    outputArea.append("Account already exists!\n");
                } else {
                    BankAccount acc = new BankAccount(name, accNo);
                    accounts.put(accNo, acc);
                    outputArea.append("Account created: " + acc + "\n");
                }
            } catch (Exception ex) {
                outputArea.append("Invalid input!\n");
            }
        });

        depositBtn.addActionListener(e -> {
            try {
                int accNo = Integer.parseInt(accField.getText().trim());
                double amount = Double.parseDouble(amountField.getText().trim());
                if (accounts.containsKey(accNo)) {
                    accounts.get(accNo).deposit(amount);
                    outputArea.append("Deposited $" + amount + " into account " + accNo + "\n");
                } else {
                    outputArea.append("Account not found!\n");
                }
            } catch (Exception ex) {
                outputArea.append("Invalid input!\n");
            }
        });

        withdrawBtn.addActionListener(e -> {
            try {
                int accNo = Integer.parseInt(accField.getText().trim());
                double amount = Double.parseDouble(amountField.getText().trim());
                if (accounts.containsKey(accNo)) {
                    boolean success = accounts.get(accNo).withdraw(amount);
                    if (success) {
                        outputArea.append("Withdrew $" + amount + " from account " + accNo + "\n");
                    } else {
                        outputArea.append("Insufficient balance or invalid amount!\n");
                    }
                } else {
                    outputArea.append("Account not found!\n");
                }
            } catch (Exception ex) {
                outputArea.append("Invalid input!\n");
            }
        });

        viewBtn.addActionListener(e -> {
            try {
                int accNo = Integer.parseInt(accField.getText().trim());
                if (accounts.containsKey(accNo)) {
                    outputArea.append(accounts.get(accNo).toString() + "\n");
                } else {
                    outputArea.append("Account not found!\n");
                }
            } catch (Exception ex) {
                outputArea.append("Invalid input!\n");
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BankingSystem::new);
    }
}
