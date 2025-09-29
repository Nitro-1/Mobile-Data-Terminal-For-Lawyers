    package ui;

    import database.DatabaseManager;
    import java.awt.*;
    import java.sql.Connection;
    import javax.swing.*;

    public class DOJDashboard extends JFrame {
        private Connection connection;
        private JPanel contentPanel;
        private CardLayout cardLayout;

        public DOJDashboard(Connection connection) {
            if (connection == null) {
                throw new IllegalArgumentException("Connection cannot be null");
            }
            this.connection = connection;
            initializeUI();
        }

        private void initializeUI() {
            setTitle("DOJ MDT Dashboard");
            setSize(1200, 800);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());

            JPanel sidebar = createSidebar();

            contentPanel = new JPanel();
            cardLayout = new CardLayout();
            contentPanel.setLayout(cardLayout);
            contentPanel.setBackground(Color.WHITE);  

            JPanel welcomePanel = createWelcomePanel();
            contentPanel.add(welcomePanel, "WELCOME");

            contentPanel.add(new CasesUI(connection).getPanel(), "CASES");
            contentPanel.add(new ClientUI(connection).getPanel(), "CLIENTS");
            contentPanel.add(new CourtHearingsUI(connection).getPanel(), "HEARINGS");
            contentPanel.add(new ActivityLogsUI(connection).getPanel(), "LOGS");
            contentPanel.add(new LawyerUI(connection).getPanel(), "LAWYERS");

            add(sidebar, BorderLayout.WEST);
            add(contentPanel, BorderLayout.CENTER);
        }

        private JPanel createSidebar() {
            JPanel sidebar = new JPanel(new GridLayout(6, 1, 10, 10));
            sidebar.setPreferredSize(new Dimension(400, 1200));
            sidebar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            sidebar.setBackground(new Color(240, 240, 240));  // Light gray background for sidebar

            JButton casesButton = createStyledButton("Cases", Color.BLACK);
            JButton clientsButton = createStyledButton("Clients", Color.BLACK);
            JButton courtHearingsButton = createStyledButton("Court Hearings", Color.BLACK);
            JButton activityLogsButton = createStyledButton("Activity Logs", Color.BLACK);
            JButton lawyersButton = createStyledButton("Lawyers", Color.BLACK);
            JButton logoutButton = createStyledButton("Logout", new Color(0, 0, 0));  // Red logout button

            sidebar.add(casesButton);
            sidebar.add(clientsButton);
            sidebar.add(courtHearingsButton);
            sidebar.add(activityLogsButton);
            sidebar.add(lawyersButton);
            sidebar.add(logoutButton);

            casesButton.addActionListener(e -> cardLayout.show(contentPanel, "CASES"));
            clientsButton.addActionListener(e -> cardLayout.show(contentPanel, "CLIENTS"));
            courtHearingsButton.addActionListener(e -> cardLayout.show(contentPanel, "HEARINGS"));
            activityLogsButton.addActionListener(e -> cardLayout.show(contentPanel, "LOGS"));
            lawyersButton.addActionListener(e -> cardLayout.show(contentPanel, "LAWYERS"));
            logoutButton.addActionListener(e -> logout());

            return sidebar;
        }

        private JButton createStyledButton(String text, Color bgColor) {
            JButton button = new JButton(text);
            button.setPreferredSize(new Dimension(220, 60));
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.setFocusPainted(false);
            button.setBackground(bgColor);
            button.setForeground(Color.BLACK);  
            button.setBorder(BorderFactory.createRaisedBevelBorder());

            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    button.setBackground(bgColor.darker());
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    button.setBackground(bgColor);
                }
            });

            return button;
        }

        private JPanel createWelcomePanel() {
            JPanel welcomePanel = new JPanel(new BorderLayout(20, 20));
            welcomePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            welcomePanel.setBackground(Color.WHITE);  

            JLabel headerLabel = new JLabel("Welcome to DOJ MDT Dashboard", SwingConstants.CENTER);
            headerLabel.setFont(new Font("Arial", Font.BOLD, 36));
            headerLabel.setForeground(Color.BLACK);  
            welcomePanel.add(headerLabel, BorderLayout.NORTH);

            JPanel statsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
            statsPanel.setBackground(Color.WHITE);  
            statsPanel.add(createStatCard("Active Cases", "0"));
            statsPanel.add(createStatCard("Pending Hearings", "0"));
            statsPanel.add(createStatCard("Total Clients", "0"));
            statsPanel.add(createStatCard("Recent Activities", "0"));
            welcomePanel.add(statsPanel, BorderLayout.CENTER);

            return welcomePanel;
        }

        private JPanel createStatCard(String title, String value) {
            JPanel card = new JPanel(new BorderLayout(10, 10));
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200)),
                    BorderFactory.createEmptyBorder(15, 15, 15, 15)
            ));
            card.setBackground(Color.WHITE);  

            JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
            titleLabel.setForeground(Color.BLACK);  

            JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
            valueLabel.setFont(new Font("Arial", Font.BOLD, 28));
            valueLabel.setForeground(Color.BLACK);  

            card.add(titleLabel, BorderLayout.NORTH);
            card.add(valueLabel, BorderLayout.CENTER);

            return card;
        }

        private void logout() {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                SwingUtilities.invokeLater(() -> {
                    JFrame loginFrame = new JFrame("DOJ Mobile Data Terminal - Login");
                    loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    loginFrame.setSize(600, 400);
                    loginFrame.setLocationRelativeTo(null);

                    LoginUI loginUI = new LoginUI(connection);
                    loginFrame.setContentPane(loginUI.getPanel());

                    loginFrame.setVisible(true);
                });
            }
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Connection connection = DatabaseManager.getConnection();
                if (connection == null) {
                    JOptionPane.showMessageDialog(null, "Database connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }

                new DOJDashboard(connection).setVisible(true);
            });
        }
    }   