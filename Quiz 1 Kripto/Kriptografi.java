import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Kriptografi extends JFrame {
    
    private JTextArea inputArea, outputArea;
    private JButton encryptButton, decryptButton, fileButton;
    private JComboBox<String> cipherComboBox;
    private JTextField keyField;  
    private JFileChooser fileChooser;
    
    public Kriptografi() {
        setTitle("Quiz Kriptografi");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
                
        JPanel panel = new JPanel(new GridLayout(4, 1));  
        
        String[] ciphers = {"Vigenere Cipher", "Playfair Cipher", "Hill Cipher"};
        cipherComboBox = new JComboBox<>(ciphers);
        panel.add(cipherComboBox);
        
        keyField = new JTextField("Masukkan kunci (min 12 karakter)");
        panel.add(keyField);
                
        inputArea = new JTextArea("Masukkan kata-kata disini");
        JScrollPane inputScrollPane = new JScrollPane(inputArea);
        panel.add(inputScrollPane);
                
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane outputScrollPane = new JScrollPane(outputArea);
        panel.add(outputScrollPane);
                
        add(panel, BorderLayout.CENTER);
                
        JPanel buttonPanel = new JPanel();
        encryptButton = new JButton("Encrypt");
        decryptButton = new JButton("Decrypt");
        fileButton = new JButton("Upload File");
        
        buttonPanel.add(encryptButton);
        buttonPanel.add(decryptButton);
        buttonPanel.add(fileButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
                
        fileChooser = new JFileChooser();
                
        encryptButton.addActionListener(e -> encryptMessage());
        decryptButton.addActionListener(e -> decryptMessage());
        fileButton.addActionListener(e -> uploadFile());
    }
    
    private void encryptMessage() {
        String cipher = (String) cipherComboBox.getSelectedItem();
        String input = inputArea.getText();
        String key = keyField.getText();
        
        if (key.length() < 12) {
            JOptionPane.showMessageDialog(this, "Kunci harus memiliki panjang minimal 12 karakter!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String result = "";
        
        switch (cipher) {
            case "Vigenere Cipher":
                result = VigenereCipher.encrypt(input, key);
                break;
            case "Playfair Cipher":
                result = PlayfairCipher.encrypt(input, key);
                break;
            case "Hill Cipher":
                result = HillCipher.encrypt(input, key);
                break;
        }
        
        outputArea.setText(result);
    }
    
    private void decryptMessage() {
        String cipher = (String) cipherComboBox.getSelectedItem();
        String input = inputArea.getText();
        String key = keyField.getText();
        
        if (key.length() < 12) {
            JOptionPane.showMessageDialog(this, "Kunci harus memiliki panjang minimal 12 karakter!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String result = "";
        
        switch (cipher) {
            case "Vigenere Cipher":
                result = VigenereCipher.decrypt(input, key);
                break;
            case "Playfair Cipher":
                result = PlayfairCipher.decrypt(input, key);
                break;
            case "Hill Cipher":
                result = HillCipher.decrypt(input, key);
                break;
        }
        
        outputArea.setText(result);
    }
    
    private void uploadFile() {
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                inputArea.read(br, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Kriptografi().setVisible(true));
    }
}
