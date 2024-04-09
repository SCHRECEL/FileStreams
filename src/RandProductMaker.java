import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class RandProductMaker extends JFrame {
    private JTextField nameField, descriptionField, idField, costField, recordCountField;
    private RandomAccessFile randomAccessFile;
    private int recordCount = 0;

    public RandProductMaker() {
        setTitle("Random Product Maker");
        setSize(400, 250);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 5, 5));

        panel.add(new JLabel("Name: "));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Description: "));
        descriptionField = new JTextField();
        panel.add(descriptionField);

        panel.add(new JLabel("ID: "));
        idField = new JTextField();
        panel.add(idField);

        panel.add(new JLabel("Cost: "));
        costField = new JTextField();
        panel.add(costField);

        JButton addButton = new JButton("Add Product");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });
        panel.add(addButton);

        panel.add(new JLabel("Record Count: "));
        recordCountField = new JTextField();
        recordCountField.setEditable(false);
        panel.add(recordCountField);

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panel.add(quitButton);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

        try {
            randomAccessFile = new RandomAccessFile("products.dat", "rw");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addProduct() {
        try {
            String name = padString(nameField.getText(), 35);
            String description = padString(descriptionField.getText(), 75);
            String id = padString(idField.getText(), 6);
            double cost = Double.parseDouble(costField.getText());

            randomAccessFile.seek(randomAccessFile.length());
            randomAccessFile.writeUTF(name);
            randomAccessFile.writeUTF(description);
            randomAccessFile.writeUTF(id);
            randomAccessFile.writeDouble(cost);

            recordCount++;
            recordCountField.setText(Integer.toString(recordCount));

            clearFields();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String padString(String input, int length) {
        StringBuilder paddedString = new StringBuilder(input);
        while (paddedString.length() < length) {
            paddedString.append(" ");
        }
        return paddedString.substring(0, length);
    }

    private void clearFields() {
        nameField.setText("");
        descriptionField.setText("");
        idField.setText("");
        costField.setText("");
        nameField.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RandProductMaker().setVisible(true);
            }
        });
    }
}
