import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Main {
    
    static class AutoKeyPresserUI extends JFrame {
        private JTextField characterArrayField;
        private JSpinner durationSpinner;
        private JComboBox<String> durationUnitCombo;
        private JSpinner intervalSpinner;
        private JComboBox<String> intervalUnitCombo;
        private JComboBox<String> actionsCombo;
        private JCheckBox enableActionsCheck;
        private JButton startButton;
        private JButton stopButton;
        private JTextArea logArea;
        private JLabel statusLabel;
        
        private Thread keyPressingThread;
        private volatile boolean isRunning = false;
        private Robot robot;
        
        public AutoKeyPresserUI() {
            try {
                robot = new Robot();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, 
                    "Failed to initialize Robot: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
            
            initializeUI();
        }
        
        private void initializeUI() {
            setTitle("Auto Key Presser - Character Array Input");
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setSize(700, 600);
            setLocationRelativeTo(null);
            
            // Create main panel
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
            
            // Create input panel
            JPanel inputPanel = createInputPanel();
            
            // Create control panel
            JPanel controlPanel = createControlPanel();
            
            // Create log panel
            JPanel logPanel = createLogPanel();
            
            // Create status panel
            JPanel statusPanel = createStatusPanel();
            
            // Add panels to main panel
            mainPanel.add(inputPanel, BorderLayout.NORTH);
            mainPanel.add(controlPanel, BorderLayout.CENTER);
            mainPanel.add(logPanel, BorderLayout.SOUTH);
            
            add(mainPanel, BorderLayout.CENTER);
            add(statusPanel, BorderLayout.SOUTH);
            
            // Set initial state
            updateButtonStates();
        }
        
        private JPanel createInputPanel() {
            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBorder(BorderFactory.createTitledBorder("Configuration Parameters"));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(8, 8, 8, 8);
            gbc.anchor = GridBagConstraints.WEST;
            
            // Parameter 1: Character Array
            gbc.gridx = 0; gbc.gridy = 0;
            JLabel arrayLabel = new JLabel("1. Character Array:");
            arrayLabel.setFont(arrayLabel.getFont().deriveFont(Font.BOLD));
            panel.add(arrayLabel, gbc);
            
            gbc.gridx = 1; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
            characterArrayField = new JTextField("A,B,C", 25);
            characterArrayField.setToolTipText("Enter characters separated by comma: A,B,C or 'A','B','C'");
            panel.add(characterArrayField, gbc);
            
            // Parameter 2: Duration
            gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
            JLabel durationLabel = new JLabel("2. Program Duration:");
            durationLabel.setFont(durationLabel.getFont().deriveFont(Font.BOLD));
            panel.add(durationLabel, gbc);
            
            gbc.gridx = 1;
            durationSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 999, 1));
            durationSpinner.setToolTipText("How long the program should run");
            panel.add(durationSpinner, gbc);
            
            gbc.gridx = 2;
            durationUnitCombo = new JComboBox<>(new String[]{"seconds", "minutes", "hours"});
            panel.add(durationUnitCombo, gbc);
            
            // Parameter 3: Interval
            gbc.gridx = 0; gbc.gridy = 2;
            JLabel intervalLabel = new JLabel("3. Key Press Interval:");
            intervalLabel.setFont(intervalLabel.getFont().deriveFont(Font.BOLD));
            panel.add(intervalLabel, gbc);
            
            gbc.gridx = 1;
            intervalSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
            intervalSpinner.setToolTipText("Time between each character press");
            panel.add(intervalSpinner, gbc);
            
            gbc.gridx = 2;
            intervalUnitCombo = new JComboBox<>(new String[]{"seconds", "minutes", "hours"});
            panel.add(intervalUnitCombo, gbc);
            
            // Parameter 4: Optional Actions
            gbc.gridx = 0; gbc.gridy = 3;
            JLabel actionsLabel = new JLabel("4. Optional Actions:");
            actionsLabel.setFont(actionsLabel.getFont().deriveFont(Font.BOLD));
            panel.add(actionsLabel, gbc);
            
            gbc.gridx = 1;
            enableActionsCheck = new JCheckBox("Enable");
            enableActionsCheck.setToolTipText("Enable additional actions after each character press");
            enableActionsCheck.addActionListener(e -> actionsCombo.setEnabled(enableActionsCheck.isSelected()));
            panel.add(enableActionsCheck, gbc);
            
            gbc.gridx = 2;
            actionsCombo = new JComboBox<>(new String[]{
                "Hover Up", "Hover Down", "Hover Left", "Hover Right", 
                "Space Bar", "Next Line"
            });
            actionsCombo.setEnabled(false);
            actionsCombo.setToolTipText("Select action to perform after each character press");
            panel.add(actionsCombo, gbc);
            
            return panel;
        }
        
        private JPanel createControlPanel() {
            JPanel panel = new JPanel(new FlowLayout());
            panel.setBorder(BorderFactory.createTitledBorder("Control"));
            
            startButton = new JButton("START");
            startButton.setPreferredSize(new Dimension(160, 35));
            startButton.setBackground(new Color(76, 175, 80));
            startButton.setForeground(Color.WHITE);
            startButton.setFont(startButton.getFont().deriveFont(Font.BOLD));
            startButton.addActionListener(this::startKeyPressing);
            
            stopButton = new JButton("⏹ Stop");
            stopButton.setPreferredSize(new Dimension(100, 35));
            stopButton.setBackground(new Color(244, 67, 54));
            stopButton.setForeground(Color.WHITE);
            stopButton.setFont(stopButton.getFont().deriveFont(Font.BOLD));
            stopButton.addActionListener(this::stopKeyPressing);
            
            JButton clearLogButton = new JButton("🗑 Clear Log");
            clearLogButton.setPreferredSize(new Dimension(120, 35));
            clearLogButton.addActionListener(event -> {
                // Clear the log area
                logArea.setText("");
            });
            
            panel.add(startButton);
            panel.add(stopButton);
            panel.add(clearLogButton);
            
            return panel;
        }
        
        private JPanel createLogPanel() {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(BorderFactory.createTitledBorder("Activity Log"));
            
            logArea = new JTextArea(12, 60);
            logArea.setEditable(false);
            logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
            logArea.setBackground(new Color(45, 45, 45));
            logArea.setForeground(new Color(0, 255, 0));
            
            JScrollPane scrollPane = new JScrollPane(logArea);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            
            panel.add(scrollPane, BorderLayout.CENTER);
            
            return panel;
        }
        
        private JPanel createStatusPanel() {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panel.setBorder(BorderFactory.createLoweredBevelBorder());
            
            statusLabel = new JLabel("Ready - Open a text editor and click Start");
            statusLabel.setForeground(new Color(33, 150, 243));
            statusLabel.setFont(statusLabel.getFont().deriveFont(Font.BOLD));
            panel.add(statusLabel);
            
            return panel;
        }
        
        private void startKeyPressing(ActionEvent e) {
            if (isRunning) return;
            
            // Parse and validate character array
            String arrayInput = characterArrayField.getText().trim();
            if (arrayInput.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter character array (e.g., A,B,C)", 
                    "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            char[] characterArray;
            try {
                characterArray = parseCharacterArray(arrayInput);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Invalid character array format: " + ex.getMessage(), 
                    "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Get duration and interval
            int durationValue = (Integer) durationSpinner.getValue();
            String durationUnit = (String) durationUnitCombo.getSelectedItem();
            long durationMillis = convertToMillis(durationValue, durationUnit);
            
            int intervalValue = (Integer) intervalSpinner.getValue();
            String intervalUnit = (String) intervalUnitCombo.getSelectedItem();
            long intervalMillis = convertToMillis(intervalValue, intervalUnit);
            
            // Get optional actions
            String selectedAction = null;
            if (enableActionsCheck.isSelected()) {
                selectedAction = (String) actionsCombo.getSelectedItem();
            }
            
            // Start key pressing
            isRunning = true;
            updateButtonStates();
            
            final String finalAction = selectedAction;
            keyPressingThread = new Thread(() -> runKeyPressing(characterArray, durationMillis, intervalMillis, finalAction));
            keyPressingThread.start();
        }
        
        private void stopKeyPressing(ActionEvent e) {
            if (keyPressingThread != null && isRunning) {
                isRunning = false;
                keyPressingThread.interrupt();
                SwingUtilities.invokeLater(() -> {
                    appendLog("❌ Stopped by user");
                    updateStatus("Stopped", new Color(244, 67, 54));
                    updateButtonStates();
                });
            }
        }
        
        private void runKeyPressing(char[] characterArray, long durationMillis, long intervalMillis, String action) {
            SwingUtilities.invokeLater(() -> {
                appendLog("🚀 === Auto Key Presser Started ===");
                appendLog("📝 Character Array: [" + arrayToString(characterArray) + "]");
                appendLog("⏱️ Duration: " + formatTime(durationMillis));
                appendLog("⏰ Interval: " + formatTime(intervalMillis));
                if (action != null) {
                    appendLog("🎯 Action: " + action);
                }
                appendLog("⚠️ Make sure a text editor is open and focused!");
                appendLog("🕐 Starting in 3 seconds...");
                updateStatus("Starting...", new Color(255, 152, 0));
            });
            
            try {
                // Initial delay
                Thread.sleep(3000);
                
                SwingUtilities.invokeLater(() -> {
                    appendLog("✅ Key pressing started!");
                    updateStatus("Running - Pressing keys", new Color(76, 175, 80));
                });
                
                long startTime = System.currentTimeMillis();
                int currentCharIndex = 0;
                final int[] totalPresses = {0}; // Use array to make it effectively final
                
                while (isRunning && (System.currentTimeMillis() - startTime) < durationMillis) {
                    // Press current character
                    char currentChar = characterArray[currentCharIndex];
                    pressKey(currentChar);
                    
                    // Execute optional action if specified
                    if (action != null) {
                        executeAction(action);
                    }
                    
                    totalPresses[0]++;
                    final int finalPresses = totalPresses[0];
                    final char finalChar = currentChar;
                    final int finalIndex = currentCharIndex;
                    final String actionInfo = (action != null) ? " + " + action : "";
                    
                    SwingUtilities.invokeLater(() -> 
                        appendLog("⌨️ Press " + finalPresses + ": '" + finalChar + "' (position " + (finalIndex + 1) + "/" + characterArray.length + ")" + actionInfo)
                    );
                    
                    // Move to next character in cycle
                    currentCharIndex = (currentCharIndex + 1) % characterArray.length;
                    
                    if (!isRunning) break;
                    
                    // Wait for interval
                    Thread.sleep(intervalMillis);
                }
                
                SwingUtilities.invokeLater(() -> {
                    if (isRunning) {
                        appendLog("⏰ Duration completed. Total presses: " + totalPresses[0]);
                        updateStatus("Completed", new Color(33, 150, 243));
                    }
                });
                
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                SwingUtilities.invokeLater(() -> appendLog("⚠️ Interrupted"));
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> {
                    appendLog("❌ Error: " + ex.getMessage());
                    updateStatus("Error", new Color(244, 67, 54));
                });
            } finally {
                isRunning = false;
                SwingUtilities.invokeLater(() -> {
                    updateStatus("Ready - Open a text editor and click Start", new Color(33, 150, 243));
                    updateButtonStates();
                });
            }
        }
        
        private char[] parseCharacterArray(String input) {
            input = input.trim();
            
            // Remove brackets if present
            if (input.startsWith("[") && input.endsWith("]")) {
                input = input.substring(1, input.length() - 1);
            }
            
            // Split by comma
            String[] parts = input.split(",");
            char[] result = new char[parts.length];
            
            for (int i = 0; i < parts.length; i++) {
                String part = parts[i].trim();
                
                // Remove quotes if present
                if ((part.startsWith("'") && part.endsWith("'")) || 
                    (part.startsWith("\"") && part.endsWith("\""))) {
                    part = part.substring(1, part.length() - 1);
                }
                
                if (part.length() == 1) {
                    result[i] = part.charAt(0);
                } else if (part.isEmpty()) {
                    throw new IllegalArgumentException("Empty character at position " + (i + 1));
                } else {
                    throw new IllegalArgumentException("Invalid character at position " + (i + 1) + ": '" + part + "' (must be single character)");
                }
            }
            
            return result;
        }
        
        private void pressKey(char c) {
            try {
                int keyCode = getKeyCode(c);
                if (keyCode != -1) {
                    boolean needsShift = Character.isUpperCase(c) || isShiftCharacter(c);
                    
                    if (needsShift) {
                        robot.keyPress(KeyEvent.VK_SHIFT);
                    }
                    
                    robot.keyPress(keyCode);
                    robot.keyRelease(keyCode);
                    
                    if (needsShift) {
                        robot.keyRelease(KeyEvent.VK_SHIFT);
                    }
                }
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> appendLog("❌ Error pressing key '" + c + "': " + e.getMessage()));
            }
        }
        
        private void executeAction(String action) {
            try {
                switch (action) {
                    case "Hover Up":
                        Point currentPos = MouseInfo.getPointerInfo().getLocation();
                        robot.mouseMove(currentPos.x, currentPos.y - 10);
                        break;
                    case "Hover Down":
                        currentPos = MouseInfo.getPointerInfo().getLocation();
                        robot.mouseMove(currentPos.x, currentPos.y + 10);
                        break;
                    case "Hover Left":
                        currentPos = MouseInfo.getPointerInfo().getLocation();
                        robot.mouseMove(currentPos.x - 10, currentPos.y);
                        break;
                    case "Hover Right":
                        currentPos = MouseInfo.getPointerInfo().getLocation();
                        robot.mouseMove(currentPos.x + 10, currentPos.y);
                        break;
                    case "Space Bar":
                        robot.keyPress(KeyEvent.VK_SPACE);
                        robot.keyRelease(KeyEvent.VK_SPACE);
                        break;
                    case "Next Line":
                        robot.keyPress(KeyEvent.VK_ENTER);
                        robot.keyRelease(KeyEvent.VK_ENTER);
                        break;
                    default:
                        SwingUtilities.invokeLater(() -> appendLog("⚠️ Unknown action: " + action));
                        break;
                }
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> appendLog("❌ Error executing action '" + action + "': " + e.getMessage()));
            }
        }
        
        private int getKeyCode(char c) {
            switch (Character.toLowerCase(c)) {
                // Letters
                case 'a': return KeyEvent.VK_A;
                case 'b': return KeyEvent.VK_B;
                case 'c': return KeyEvent.VK_C;
                case 'd': return KeyEvent.VK_D;
                case 'e': return KeyEvent.VK_E;
                case 'f': return KeyEvent.VK_F;
                case 'g': return KeyEvent.VK_G;
                case 'h': return KeyEvent.VK_H;
                case 'i': return KeyEvent.VK_I;
                case 'j': return KeyEvent.VK_J;
                case 'k': return KeyEvent.VK_K;
                case 'l': return KeyEvent.VK_L;
                case 'm': return KeyEvent.VK_M;
                case 'n': return KeyEvent.VK_N;
                case 'o': return KeyEvent.VK_O;
                case 'p': return KeyEvent.VK_P;
                case 'q': return KeyEvent.VK_Q;
                case 'r': return KeyEvent.VK_R;
                case 's': return KeyEvent.VK_S;
                case 't': return KeyEvent.VK_T;
                case 'u': return KeyEvent.VK_U;
                case 'v': return KeyEvent.VK_V;
                case 'w': return KeyEvent.VK_W;
                case 'x': return KeyEvent.VK_X;
                case 'y': return KeyEvent.VK_Y;
                case 'z': return KeyEvent.VK_Z;
                
                // Numbers
                case '0': return KeyEvent.VK_0;
                case '1': return KeyEvent.VK_1;
                case '2': return KeyEvent.VK_2;
                case '3': return KeyEvent.VK_3;
                case '4': return KeyEvent.VK_4;
                case '5': return KeyEvent.VK_5;
                case '6': return KeyEvent.VK_6;
                case '7': return KeyEvent.VK_7;
                case '8': return KeyEvent.VK_8;
                case '9': return KeyEvent.VK_9;
                
                // Special characters
                case ' ': return KeyEvent.VK_SPACE;
                case '.': return KeyEvent.VK_PERIOD;
                case ',': return KeyEvent.VK_COMMA;
                case ';': return KeyEvent.VK_SEMICOLON;
                case '\'': return KeyEvent.VK_QUOTE;
                case '[': return KeyEvent.VK_OPEN_BRACKET;
                case ']': return KeyEvent.VK_CLOSE_BRACKET;
                case '\\': return KeyEvent.VK_BACK_SLASH;
                case '/': return KeyEvent.VK_SLASH;
                case '=': return KeyEvent.VK_EQUALS;
                case '-': return KeyEvent.VK_MINUS;
                case '`': return KeyEvent.VK_BACK_QUOTE;
                
                default:
                    SwingUtilities.invokeLater(() -> appendLog("⚠️ Unsupported character: '" + c + "'"));
                    return -1;
            }
        }
        
        private boolean isShiftCharacter(char c) {
            String shiftChars = "!@#$%^&*()_+:\"<>?{}|~";
            return shiftChars.indexOf(c) != -1;
        }
        
        private long convertToMillis(int value, String unit) {
            return switch (unit.toLowerCase()) {
                case "seconds" -> value * 1000L;
                case "minutes" -> value * 60L * 1000L;
                case "hours" -> value * 60L * 60L * 1000L;
                default -> value * 1000L;
            };
        }
        
        private String formatTime(long millis) {
            long seconds = millis / 1000;
            if (seconds < 60) {
                return seconds + " seconds";
            } else if (seconds < 3600) {
                return (seconds / 60) + " minutes";
            } else {
                return (seconds / 3600) + " hours";
            }
        }
        
        private String arrayToString(char[] array) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length; i++) {
                sb.append("'").append(array[i]).append("'");
                if (i < array.length - 1) {
                    sb.append(", ");
                }
            }
            return sb.toString();
        }
        
        private void appendLog(String message) {
            String timestamp = java.time.LocalTime.now().toString().substring(0, 8);
            logArea.append("[" + timestamp + "] " + message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        }
        
        private void updateStatus(String status, Color color) {
            statusLabel.setText(status);
            statusLabel.setForeground(color);
        }
        
        private void updateButtonStates() {
            startButton.setEnabled(!isRunning);
            stopButton.setEnabled(isRunning);
            
            // Disable input fields while running
            characterArrayField.setEnabled(!isRunning);
            durationSpinner.setEnabled(!isRunning);
            durationUnitCombo.setEnabled(!isRunning);
            intervalSpinner.setEnabled(!isRunning);
            intervalUnitCombo.setEnabled(!isRunning);
            enableActionsCheck.setEnabled(!isRunning);
            actionsCombo.setEnabled(!isRunning && enableActionsCheck.isSelected());
        }
    }
    
    public static void main(String[] args) {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            // Use default look and feel
        }
        
        SwingUtilities.invokeLater(() -> {
            new AutoKeyPresserUI().setVisible(true);
        });
    }
}
