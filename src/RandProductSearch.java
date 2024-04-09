import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandProductSearch extends JFrame {
    private JTextField searchField;
    private JTextArea resultArea;
    private RandomAccessFile randomAccessFile;

    public RandProductSearch() {
        setTitle("Random Product Search");
        setSize(400, 250);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 5, 5));

        panel.add(new JLabel("Search by Name: "));
        searchField = new JTextField();
        panel.add(searchField);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchProducts();
            }
        });
        panel.add(searchButton);

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panel.add(quitButton);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.NORTH);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.CENTER);

        try {
            randomAccessFile = new RandomAccessFile("products.dat", "r");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void searchProducts() {
        String searchTerm = searchField.getText().trim();
        if (!searchTerm.isEmpty()) {
            try {
                randomAccessFile.seek(0);
                resultArea.setText(""); // Clear previous results
                while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
                    String name = randomAccessFile.readUTF().trim();
                    String description = randomAccessFile.readUTF().trim();
                    String id = randomAccessFile.readUTF().trim();
                    double cost = randomAccessFile.readDouble();

                    if (name.toLowerCase().contains(searchTerm.toLowerCase())) {
                        resultArea.append("Name: " + name + "\n");
                        resultArea.append("Description: " + description + "\n");
                        resultArea.append("ID: " + id + "\n");
                        resultArea.append("Cost: $" + cost + "\n\n");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a search term.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RandProductSearch().setVisible(true);
            }
        });
    }
}
