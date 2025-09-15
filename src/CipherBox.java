import javax.swing.*;
import java.awt.*;
import java.util.Base64;

public class CipherBox extends JFrame {

    private JTextField messageField, keyField, outputField;
    private JRadioButton encryptBtn, decryptBtn;
    private ButtonGroup modeGroup;

    public CipherBox() {
        setTitle("CipherBox – Encrypt & Decrypt Messages");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Panel with background
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(34, 40, 49)); // dark background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Heading
        JLabel heading = new JLabel("CipherBox – Encrypt & Decrypt Messages", JLabel.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 20));
        heading.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        mainPanel.add(heading, gbc);

        // Message label + field
        JLabel label1 = new JLabel("Enter Message:");
        label1.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        mainPanel.add(label1, gbc);

        messageField = new JTextField();
        gbc.gridx = 1; gbc.gridy = 1;
        mainPanel.add(messageField, gbc);

        // Key label + field
        JLabel label2 = new JLabel("Enter Key:");
        label2.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(label2, gbc);

        keyField = new JTextField();
        gbc.gridx = 1; gbc.gridy = 2;
        mainPanel.add(keyField, gbc);

        // Encrypt/Decrypt Options
        JLabel label3 = new JLabel("Choose Mode:");
        label3.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(label3, gbc);

        JPanel radioPanel = new JPanel();
        radioPanel.setBackground(new Color(34, 40, 49));
        encryptBtn = new JRadioButton("Encrypt");
        decryptBtn = new JRadioButton("Decrypt");
        encryptBtn.setForeground(Color.WHITE);
        decryptBtn.setForeground(Color.WHITE);
        encryptBtn.setBackground(new Color(34, 40, 49));
        decryptBtn.setBackground(new Color(34, 40, 49));
        modeGroup = new ButtonGroup();
        modeGroup.add(encryptBtn);
        modeGroup.add(decryptBtn);
        radioPanel.add(encryptBtn);
        radioPanel.add(decryptBtn);

        gbc.gridx = 1; gbc.gridy = 3;
        mainPanel.add(radioPanel, gbc);

        // Result label + field
        JLabel label4 = new JLabel("Result:");
        label4.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 4;
        mainPanel.add(label4, gbc);

        outputField = new JTextField();
        outputField.setEditable(false);
        gbc.gridx = 1; gbc.gridy = 4;
        mainPanel.add(outputField, gbc);

        // Buttons panel
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(34, 40, 49));

        JButton showBtn = new JButton("Show Message");
        JButton resetBtn = new JButton("Reset");
        JButton quitBtn = new JButton("Exit");

        // Add actions
        showBtn.addActionListener(e -> showResult());
        resetBtn.addActionListener(e -> reset());
        quitBtn.addActionListener(e -> System.exit(0));

        // Style buttons
        JButton[] buttons = {showBtn, resetBtn, quitBtn};
        for (JButton b : buttons) {
            b.setBackground(new Color(0, 173, 181));
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        }

        btnPanel.add(showBtn);
        btnPanel.add(resetBtn);
        btnPanel.add(quitBtn);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        mainPanel.add(btnPanel, gbc);

        add(mainPanel);
        setVisible(true);
    }

    // Encode function
    private String encode(String key, String msg) {
        StringBuilder enc = new StringBuilder();
        for (int i = 0; i < msg.length(); i++) {
            char msgChar = msg.charAt(i);
            char keyChar = key.charAt(i % key.length());
            char encChar = (char) ((msgChar + keyChar) % 256);
            enc.append(encChar);
        }
        return Base64.getUrlEncoder().encodeToString(enc.toString().getBytes());
    }

    // Decode function
    private String decode(String key, String code) {
        StringBuilder dec = new StringBuilder();
        String enc = new String(Base64.getUrlDecoder().decode(code));
        for (int i = 0; i < enc.length(); i++) {
            char encChar = enc.charAt(i);
            char keyChar = key.charAt(i % key.length());
            char decChar = (char) ((256 + encChar - keyChar) % 256);
            dec.append(decChar);
        }
        return dec.toString();
    }

    // Show result
    private void showResult() {
        String msg = messageField.getText();
        String k = keyField.getText();

        if (encryptBtn.isSelected()) {
            outputField.setText(encode(k, msg));
        } else if (decryptBtn.isSelected()) {
            try {
                outputField.setText(decode(k, msg));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input for decryption.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please choose Encrypt or Decrypt.");
        }
    }

    // Reset function
    private void reset() {
        messageField.setText("");
        keyField.setText("");
        outputField.setText("");
        modeGroup.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CipherBox::new);
    }
}
