import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.HashSet;

public class VotingSystem {

    private static final HashMap<String, String[]> users = new HashMap<>(); // Store ID -> [name, password]
    private static final HashSet<String> completedVoters = new HashSet<>();

    private static final String[] candidates = { "Faizan Zeeshan", "Jawwad Karim", "Faiz Gull", "Izhan Shahid",
            "Kabir Amir" };
    private static final int[] votes = new int[candidates.length];

    public static void main(String[] args) {
        // Predefined user database
        users.put("192-2024", new String[] { "Umer Arshad", "password1" });
        users.put("193-2024", new String[] { "Izhan Shahid", "password2" });
        users.put("245-2024", new String[] { "M Kabir", "password3" });
        users.put("1-2024", new String[] { "Ammar Ahmed", "password4" });
        users.put("110-2024", new String[] { "Faiz Gull", "password5" });
        users.put("248-2024", new String[] { "Ayesha Shuja", "password6" });
        users.put("200-2024", new String[] { "Azeema Shabeer", "password7" });

        // Main frame setup
        JFrame frame = new JFrame("Online Voting System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(200, 220, 240));

        displayMainPage(frame);
    }

    private static void displayMainPage(JFrame frame) {
        frame.getContentPane().removeAll();

        JLabel heading = new JLabel("ONLINE VOTING SYSTEM", SwingConstants.CENTER);
        heading.setBounds(150, 50, 500, 50);
        heading.setFont(new Font("Arial", Font.BOLD, 30));
        heading.setForeground(new Color(50, 50, 150));
        frame.add(heading);

        JLabel about = new JLabel("Vote for your Class Representative!", SwingConstants.CENTER);
        about.setBounds(100, 150, 600, 30);
        about.setFont(new Font("Arial", Font.PLAIN, 18));
        frame.add(about);

        JButton startButton = new JButton("Start");
        startButton.setBounds(300, 300, 200, 50);
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.setBackground(new Color(50, 150, 250));
        startButton.setForeground(Color.WHITE);

        startButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                startButton.setBackground(new Color(30, 130, 230));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                startButton.setBackground(new Color(50, 150, 250));
            }
        });

        startButton.addActionListener(e -> displayLoginSignupPage(frame));
        frame.add(startButton);

        frame.repaint();
        frame.setVisible(true);
    }

    private static void displayLoginSignupPage(JFrame frame) {
        frame.getContentPane().removeAll();

        JLabel heading = new JLabel("Login or Sign Up", SwingConstants.CENTER);
        heading.setBounds(200, 50, 400, 50);
        heading.setFont(new Font("Arial", Font.BOLD, 28));
        heading.setForeground(new Color(50, 50, 150));
        frame.add(heading);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(200, 200, 150, 50);
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.setBackground(new Color(50, 150, 250));
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(e -> displayLoginPage(frame));
        frame.add(loginButton);

        JButton signupButton = new JButton("Sign Up");
        signupButton.setBounds(400, 200, 150, 50);
        signupButton.setFont(new Font("Arial", Font.BOLD, 18));
        signupButton.setBackground(new Color(50, 150, 250));
        signupButton.setForeground(Color.WHITE);
        signupButton.addActionListener(e -> displaySignupPage(frame));
        frame.add(signupButton);

        JLabel loginDesc = new JLabel("Already registered? Login to cast your vote.", SwingConstants.CENTER);
        loginDesc.setBounds(150, 270, 500, 30);
        loginDesc.setFont(new Font("Arial", Font.PLAIN, 16));
        frame.add(loginDesc);

        JLabel signupDesc = new JLabel("Not registered? Sign up with your university ID.", SwingConstants.CENTER);
        signupDesc.setBounds(150, 310, 500, 30);
        signupDesc.setFont(new Font("Arial", Font.PLAIN, 16));
        frame.add(signupDesc);

        addBackButton(frame, () -> displayMainPage(frame));

        frame.repaint();
        frame.setVisible(true);
    }

    private static void displaySignupPage(JFrame frame) {
        frame.getContentPane().removeAll();

        JLabel heading = new JLabel("Sign Up", SwingConstants.CENTER);
        heading.setBounds(200, 50, 400, 50);
        heading.setFont(new Font("Arial", Font.BOLD, 28));
        heading.setForeground(new Color(50, 50, 150));
        frame.add(heading);

        // ID dropdown
        JLabel idLabel = new JLabel("Select University ID:");
        idLabel.setBounds(200, 150, 200, 30);
        idLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        frame.add(idLabel);

        // Filter out already signed-up users' IDs
        JComboBox<String> idComboBox = new JComboBox<>(users.keySet().toArray(new String[0]));
        idComboBox.setBounds(400, 150, 200, 30);
        frame.add(idComboBox);

        // Name dropdown (will update based on selected ID)
        JLabel nameLabel = new JLabel("Select Name:");
        nameLabel.setBounds(200, 200, 200, 30);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        frame.add(nameLabel);

        JComboBox<String> nameComboBox = new JComboBox<>();
        nameComboBox.setBounds(400, 200, 200, 30);
        frame.add(nameComboBox);

        // Update name dropdown when an ID is selected
        idComboBox.addActionListener(e -> {
            String selectedId = (String) idComboBox.getSelectedItem();
            if (selectedId != null) {
                nameComboBox.removeAllItems();
                nameComboBox.addItem(users.get(selectedId)[0]); // Add the name associated with the selected ID
            }
        });

        // Password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(200, 250, 200, 30);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        frame.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(400, 250, 200, 30);
        frame.add(passwordField);

        // Show password checkbox
        JCheckBox showPassword = new JCheckBox("Show Password");
        showPassword.setBounds(400, 290, 200, 30);
        showPassword.setFont(new Font("Arial", Font.PLAIN, 16));
        showPassword.addActionListener(e -> {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0); // Show password
            } else {
                passwordField.setEchoChar('*'); // Hide password
            }
        });
        frame.add(showPassword);

        // Set the action to be triggered when Enter is pressed
        passwordField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String selectedId = (String) idComboBox.getSelectedItem();
                    String selectedName = (String) nameComboBox.getSelectedItem();
                    String password = new String(passwordField.getPassword());

                    // Validate if the selected name matches the ID
                    if (selectedId != null && selectedName != null && users.get(selectedId)[0].equals(selectedName)) {
                        users.put(selectedId, new String[] { selectedName, password });
                        JOptionPane.showMessageDialog(frame, "Account created successfully. Please log in.", "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        displayLoginSignupPage(frame); // Go back to login page after successful sign-up
                    } else {
                        JOptionPane.showMessageDialog(frame, "Name and ID do not match!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Submit button
        JButton submitButton = new JButton("Create Account");
        submitButton.setBounds(350, 350, 150, 40);
        submitButton.setFont(new Font("Arial", Font.BOLD, 18));
        submitButton.setBackground(new Color(50, 150, 250));
        submitButton.setForeground(Color.WHITE);
        submitButton.addActionListener(e -> {
            String selectedId = (String) idComboBox.getSelectedItem();
            String selectedName = (String) nameComboBox.getSelectedItem();
            String password = new String(passwordField.getPassword());

            if (selectedId != null && selectedName != null && users.get(selectedId)[0].equals(selectedName)) {
                users.put(selectedId, new String[] { selectedName, password });
                JOptionPane.showMessageDialog(frame, "Account created successfully. Please log in.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                displayLoginSignupPage(frame); // Go back to login page after successful sign-up
            } else {
                JOptionPane.showMessageDialog(frame, "Name and ID do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        frame.add(submitButton);

        addBackButton(frame, () -> displayLoginSignupPage(frame));

        frame.repaint();
        frame.setVisible(true);
    }

    private static void displayLoginPage(JFrame frame) {
        frame.getContentPane().removeAll();

        JLabel heading = new JLabel("Login", SwingConstants.CENTER);
        heading.setBounds(200, 50, 400, 50);
        heading.setFont(new Font("Arial", Font.BOLD, 28));
        heading.setForeground(new Color(50, 50, 150));
        frame.add(heading);

        JLabel idLabel = new JLabel("University ID:");
        idLabel.setBounds(200, 200, 100, 30);
        idLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        frame.add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(300, 200, 300, 30);
        frame.add(idField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(200, 250, 100, 30);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        frame.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(300, 250, 300, 30);
        frame.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(350, 350, 100, 40);
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.setBackground(new Color(50, 150, 250));
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(e -> {
            String id = idField.getText();
            String password = new String(passwordField.getPassword());

            if (users.containsKey(id) && users.get(id)[1].equals(password)) {
                JOptionPane.showMessageDialog(frame, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                if (!completedVoters.contains(id)) {
                    displayVotingPage(frame, id);
                } else {
                    JOptionPane.showMessageDialog(frame, "You have already voted.", "Information",
                            JOptionPane.INFORMATION_MESSAGE);
                    displayMainPage(frame); // Return to the main page after voting
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid ID or password. Please try again.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        frame.add(loginButton);

        addBackButton(frame, () -> displayLoginSignupPage(frame));

        frame.repaint();
        frame.setVisible(true);
    }

    private static void displayVotingPage(JFrame frame, String userId) {
        frame.getContentPane().removeAll();

        JLabel heading = new JLabel("Vote for your Class Representative", SwingConstants.CENTER);
        heading.setBounds(150, 50, 500, 50);
        heading.setFont(new Font("Arial", Font.BOLD, 28));
        heading.setForeground(new Color(50, 50, 150));
        frame.add(heading);

        JLabel voteLabel = new JLabel("Select your candidate:", SwingConstants.CENTER);
        voteLabel.setBounds(250, 150, 300, 30);
        voteLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        frame.add(voteLabel);

        JComboBox<String> candidatesComboBox = new JComboBox<>(candidates);
        candidatesComboBox.setBounds(250, 200, 300, 40);
        frame.add(candidatesComboBox);

        JButton voteButton = new JButton("Vote");
        voteButton.setBounds(350, 300, 100, 40);
        voteButton.setFont(new Font("Arial", Font.BOLD, 18));
        voteButton.setBackground(new Color(50, 150, 250));
        voteButton.setForeground(Color.WHITE);
        voteButton.addActionListener(e -> {
            String selectedCandidate = (String) candidatesComboBox.getSelectedItem();
            if (selectedCandidate != null) {
                // Find the index of the selected candidate
                int candidateIndex = -1;
                for (int i = 0; i < candidates.length; i++) {
                    if (candidates[i].equals(selectedCandidate)) {
                        candidateIndex = i;
                        break;
                    }
                }

                if (candidateIndex != -1) {
                    votes[candidateIndex]++;
                    completedVoters.add(userId); // Mark the user as voted

                    // Show winning ratio
                    StringBuilder ratioMessage = new StringBuilder("Winning Ratio:\n");
                    int totalVotes = 0;
                    for (int v : votes) {
                        totalVotes += v;
                    }

                    for (int i = 0; i < candidates.length; i++) {
                        double ratio = totalVotes > 0 ? (double) votes[i] / totalVotes * 100 : 0;
                        ratioMessage.append(candidates[i]).append(": ").append(String.format("%.2f", ratio))
                                .append("%\n");
                    }

                    JOptionPane.showMessageDialog(frame, ratioMessage.toString(), "Voting Summary",
                            JOptionPane.INFORMATION_MESSAGE);
                    displayMainPage(frame); // Return to the main page after voting
                }
            }
        });

        frame.add(voteButton);
        addBackButton(frame, () -> displayLoginPage(frame));

        frame.repaint();
        frame.setVisible(true);
    }

    private static void addBackButton(JFrame frame, Runnable action) {
        JButton backButton = new JButton("Back");
        backButton.setBounds(10, 10, 100, 30);
        backButton.setFont(new Font("Arial", Font.PLAIN, 16));
        backButton.setBackground(new Color(200, 200, 250));
        backButton.setForeground(Color.BLACK);
        backButton.addActionListener(e -> action.run());
        frame.add(backButton);
    }
}
