import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Main {
    
    // GUI Component class embedded within Main
    static class AutoKeyPresserGUI extends JFrame {
        private JTextField charactersField;
        private JSpinner intervalSpinner;
        private JComboBox<String> intervalUnitCombo;
        private JSpinner durationSpinner;
        private JComboBox<String> durationUnitCombo;
        private JCheckBox infiniteDurationCheck;
        private JButton startButton;
        private JButton stopButton;
        private JTextArea logArea;
        private JLabel statusLabel;
        
        private Thread keyPressingThread;
        private volatile boolean isRunning = false;
        private Robot robot;
        
        private static final String SECONDS = "seconds";
        private static final String MINUTES = "minutes";
        private static final String HOURS = "hours";
        
        public AutoKeyPresserGUI() {
            try {
                robot = new Robot();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, 
                    "Failed to initialize Robot: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
            
            initializeGUI();
        }
        
        private void initializeGUI() {
            setTitle("Auto Key Presser");
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setSize(600, 500);
            setLocationRelativeTo(null);
            
            // Create main panel
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
            
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
            panel.setBorder(BorderFactory.createTitledBorder("Configuration"));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.anchor = GridBagConstraints.WEST;
            
            // Characters field
            gbc.gridx = 0; gbc.gridy = 0;
            panel.add(new JLabel("Characters:"), gbc);
            
            gbc.gridx = 1; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
            charactersField = new JTextField("'a','b','c'", 20);
            charactersField.setToolTipText("Enter comma-separated characters: 'a','b','c' (cycles through each character)");
            panel.add(charactersField, gbc);
            
            // Interval
            gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
            panel.add(new JLabel("Interval:"), gbc);
            
            gbc.gridx = 1;
            intervalSpinner = new JSpinner(new SpinnerNumberModel(2, 1, 999, 1));
            intervalSpinner.setToolTipText("How often to repeat the character sequence");
            panel.add(intervalSpinner, gbc);
            
            gbc.gridx = 2;
            intervalUnitCombo = new JComboBox<>(new String[]{SECONDS, MINUTES, HOURS});
            panel.add(intervalUnitCombo, gbc);
            
            // Duration
            gbc.gridx = 0; gbc.gridy = 2;
            panel.add(new JLabel("Duration:"), gbc);
            
            gbc.gridx = 1;
            durationSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 999, 1));
            durationSpinner.setToolTipText("How long to run the key pressing");
            panel.add(durationSpinner, gbc);
            
            gbc.gridx = 2;
            durationUnitCombo = new JComboBox<>(new String[]{SECONDS, MINUTES, HOURS});
            panel.add(durationUnitCombo, gbc);
            
            gbc.gridx = 3;
            infiniteDurationCheck = new JCheckBox("Infinite");
            infiniteDurationCheck.setToolTipText("Run indefinitely until stopped manually");
            infiniteDurationCheck.addActionListener(e -> {
                boolean infinite = infiniteDurationCheck.isSelected();
                durationSpinner.setEnabled(!infinite);
                durationUnitCombo.setEnabled(!infinite);
            });
            panel.add(infiniteDurationCheck, gbc);
            
            return panel;
        }
        
        private JPanel createControlPanel() {
            JPanel panel = new JPanel(new FlowLayout());
            panel.setBorder(BorderFactory.createTitledBorder("Control"));
            
            startButton = new JButton("Start");
            startButton.setPreferredSize(new Dimension(100, 30));
            startButton.addActionListener(this::startKeyPressing);
            
            stopButton = new JButton("Stop");
            stopButton.setPreferredSize(new Dimension(100, 30));
            stopButton.addActionListener(this::stopKeyPressing);
            
            JButton clearLogButton = new JButton("Clear Log");
            clearLogButton.setPreferredSize(new Dimension(100, 30));
            clearLogButton.addActionListener(e -> logArea.setText(""));
            
            panel.add(startButton);
            panel.add(stopButton);
            panel.add(clearLogButton);
            
            return panel;
        }
        
        private JPanel createLogPanel() {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(BorderFactory.createTitledBorder("Log"));
            
            logArea = new JTextArea(10, 50);
            logArea.setEditable(false);
            logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
            logArea.setBackground(Color.BLACK);
            logArea.setForeground(Color.GREEN);
            
            JScrollPane scrollPane = new JScrollPane(logArea);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            
            panel.add(scrollPane, BorderLayout.CENTER);
            
            return panel;
        }
        
        private JPanel createStatusPanel() {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panel.setBorder(BorderFactory.createLoweredBevelBorder());
            
            statusLabel = new JLabel("Ready");
            statusLabel.setForeground(Color.BLUE);
            panel.add(statusLabel);
            
            return panel;
        }
        
        private void startKeyPressing(ActionEvent e) {
            if (isRunning) return;
            
            // Validate and parse input
            String rawInput = charactersField.getText().trim();
            if (rawInput.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter characters to press.", 
                    "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            char[] characterArray;
            try {
                characterArray = parseGUICharacters(rawInput);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Invalid character format: " + ex.getMessage(), 
                    "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Get configuration
            int intervalValue = (Integer) intervalSpinner.getValue();
            String intervalUnit = (String) intervalUnitCombo.getSelectedItem();
            long intervalMillis = convertToMillis(intervalValue, intervalUnit);
            
            long durationMillis = -1; // Infinite by default
            if (!infiniteDurationCheck.isSelected()) {
                int durationValue = (Integer) durationSpinner.getValue();
                String durationUnit = (String) durationUnitCombo.getSelectedItem();
                durationMillis = convertToMillis(durationValue, durationUnit);
            }
            
            // Start key pressing in separate thread
            isRunning = true;
            updateButtonStates();
            
            final long finalDurationMillis = durationMillis;
            keyPressingThread = new Thread(() -> runKeyPressingCycle(characterArray, intervalMillis, finalDurationMillis));
            keyPressingThread.start();
        }
        
        private void stopKeyPressing(ActionEvent e) {
            if (keyPressingThread != null && isRunning) {
                isRunning = false;
                keyPressingThread.interrupt();
                SwingUtilities.invokeLater(() -> {
                    appendLog("Stopped by user");
                    updateStatus("Stopped", Color.RED);
                    updateButtonStates();
                });
            }
        }
        
        private void runKeyPressing(String characters, long intervalMillis, long durationMillis) {
            SwingUtilities.invokeLater(() -> {
                appendLog("=== Auto Key Presser Started ===");
                appendLog("Characters: " + characters);
                appendLog("Interval: " + formatTime(intervalMillis));
                if (durationMillis > 0) {
                    appendLog("Duration: " + formatTime(durationMillis));
                } else {
                    appendLog("Duration: Infinite (until stopped)");
                }
                appendLog("Starting in 3 seconds...");
                updateStatus("Starting...", Color.ORANGE);
            });
            
            try {
                // Initial delay
                Thread.sleep(3000);
                
                SwingUtilities.invokeLater(() -> {
                    appendLog("Key pressing started!");
                    updateStatus("Running", Color.GREEN);
                });
                
                long startTime = System.currentTimeMillis();
                int cycleCount = 0;
                
                while (isRunning) {
                    // Check duration
                    if (durationMillis > 0 && (System.currentTimeMillis() - startTime) >= durationMillis) {
                        SwingUtilities.invokeLater(() -> {
                            appendLog("Duration completed. Stopping...");
                            updateStatus("Completed", Color.BLUE);
                        });
                        break;
                    }
                    
                    // Press keys
                    cycleCount++;
                    final int currentCycle = cycleCount;
                    
                    StringBuilder sequenceLog = new StringBuilder("Cycle " + currentCycle + ": ");
                    char[] charArray = characters.toCharArray();
                    for (int i = 0; i < charArray.length; i++) {
                        if (!isRunning) break;
                        char c = charArray[i];
                        pressKey(c);
                        sequenceLog.append(c);
                        if (i < charArray.length - 1) {
                            sequenceLog.append(" -> ");
                        }
                        Thread.sleep(50); // Small delay between characters
                    }
                    sequenceLog.append(" (completed)");
                    
                    SwingUtilities.invokeLater(() -> appendLog(sequenceLog.toString()));
                    
                    if (!isRunning) break;
                    
                    // Wait for interval
                    Thread.sleep(intervalMillis);
                }
                
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                SwingUtilities.invokeLater(() -> appendLog("Interrupted"));
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> {
                    appendLog("Error: " + ex.getMessage());
                    updateStatus("Error", Color.RED);
                });
            } finally {
                isRunning = false;
                SwingUtilities.invokeLater(() -> {
                    updateStatus("Ready", Color.BLUE);
                    updateButtonStates();
                });
            }
        }
        
        private void runKeyPressingCycle(char[] characterArray, long intervalMillis, long durationMillis) {
            SwingUtilities.invokeLater(() -> {
                appendLog("=== Auto Key Presser Started (Cycling Mode) ===");
                StringBuilder charList = new StringBuilder();
                for (int i = 0; i < characterArray.length; i++) {
                    charList.append("'").append(characterArray[i]).append("'");
                    if (i < characterArray.length - 1) {
                        charList.append(", ");
                    }
                }
                appendLog("Characters: [" + charList + "]");
                appendLog("Interval: " + formatTime(intervalMillis));
                if (durationMillis > 0) {
                    appendLog("Duration: " + formatTime(durationMillis));
                } else {
                    appendLog("Duration: Infinite (until stopped)");
                }
                appendLog("Starting in 3 seconds...");
                updateStatus("Starting...", Color.ORANGE);
            });
            
            try {
                // Initial delay
                Thread.sleep(3000);
                
                SwingUtilities.invokeLater(() -> {
                    appendLog("Key pressing started!");
                    updateStatus("Running", Color.GREEN);
                });
                
                long startTime = System.currentTimeMillis();
                int pressCount = 0;
                int currentCharIndex = 0;
                
                while (isRunning) {
                    // Check duration
                    if (durationMillis > 0 && (System.currentTimeMillis() - startTime) >= durationMillis) {
                        SwingUtilities.invokeLater(() -> {
                            appendLog("Duration completed. Stopping...");
                            updateStatus("Completed", Color.BLUE);
                        });
                        break;
                    }
                    
                    // Press current character
                    char currentChar = characterArray[currentCharIndex];
                    pressKey(currentChar);
                    
                    pressCount++;
                    final int currentPress = pressCount;
                    final char finalChar = currentChar;
                    final int finalIndex = currentCharIndex;
                    
                    SwingUtilities.invokeLater(() -> 
                        appendLog("Press " + currentPress + ": '" + finalChar + "' (position " + (finalIndex + 1) + " of " + characterArray.length + ")")
                    );
                    
                    // Move to next character in cycle
                    currentCharIndex = (currentCharIndex + 1) % characterArray.length;
                    
                    if (!isRunning) break;
                    
                    // Wait for interval
                    Thread.sleep(intervalMillis);
                }
                
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                SwingUtilities.invokeLater(() -> appendLog("Interrupted"));
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> {
                    appendLog("Error: " + ex.getMessage());
                    updateStatus("Error", Color.RED);
                });
            } finally {
                isRunning = false;
                SwingUtilities.invokeLater(() -> {
                    updateStatus("Ready", Color.BLUE);
                    updateButtonStates();
                });
            }
        }
        
        private void pressKey(char c) {
            try {
                int keyCode = getKeyCodeForGUI(c);
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
                SwingUtilities.invokeLater(() -> appendLog("Error pressing key '" + c + "': " + e.getMessage()));
            }
        }
        
        private long convertToMillis(int value, String unit) {
            return switch (unit.toLowerCase()) {
                case SECONDS -> value * 1000L;
                case MINUTES -> value * 60L * 1000L;
                case HOURS -> value * 60L * 60L * 1000L;
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
            charactersField.setEnabled(!isRunning);
            intervalSpinner.setEnabled(!isRunning);
            intervalUnitCombo.setEnabled(!isRunning);
            durationSpinner.setEnabled(!isRunning && !infiniteDurationCheck.isSelected());
            durationUnitCombo.setEnabled(!isRunning && !infiniteDurationCheck.isSelected());
            infiniteDurationCheck.setEnabled(!isRunning);
        }
        
        private int getKeyCodeForGUI(char c) {
            return getKeyCode(c); // Use the main class method
        }
    }
    
    public static void main(String[] args) {
        // Check if GUI mode is requested
        if (args.length == 1 && (args[0].equals("--gui") || args[0].equals("-g"))) {
            // Set look and feel
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                // Use default look and feel if system L&F is not available
            }
            
            // Launch GUI
            SwingUtilities.invokeLater(() -> {
                new AutoKeyPresserGUI().setVisible(true);
            });
            return;
        }
        
        if (args.length < 2) {
            System.out.println("Auto Key Presser - Command Line Interface");
            System.out.println();
            System.out.println("Usage: java Main <characters> <interval> [duration]");
            System.out.println("   OR: java Main --gui  (launch GUI interface)");
            System.out.println();
            System.out.println("Examples:");
            System.out.println("  java Main \"hello\" \"2s\" \"10s\" - Press 'hello' every 2 seconds for 10 seconds");
            System.out.println("  java Main \"['a','b','c']\" \"1s\" \"5s\" - Press a,b,c sequence every 1 second for 5 seconds");
            System.out.println("  java Main \"[h,e,l,l,o]\" \"2s\" - Press h,e,l,l,o sequence every 2 seconds indefinitely");
            System.out.println("  java Main \"test\" \"30s\" - Press 'test' every 30 seconds indefinitely");
            System.out.println("  java Main --gui - Launch graphical interface");
            System.out.println();
            System.out.println("Character formats:");
            System.out.println("  String: \"hello\" or \"test123\"");
            System.out.println("  Array: \"['a','b','c']\" or \"[h,e,l,l,o]\"");
            System.out.println("Time formats: s=seconds, m=minutes, h=hours");
            return;
        }
        
        try {
            // Parse characters (support both string and array format)
            String characters = parseCharacters(args[0]);
            
            // Parse interval
            long intervalMillis = parseTimeToMillis(args[1]);
            
            // Parse duration (optional, default is infinite)
            long durationMillis = -1; // -1 means infinite
            if (args.length >= 3) {
                durationMillis = parseTimeToMillis(args[2]);
            }
            
            // Create robot for key simulation
            Robot robot = new Robot();
            
            System.out.println("Auto Key Presser Started");
            System.out.println("Characters: " + characters);
            System.out.println("Interval: " + args[1]);
            if (durationMillis > 0) {
                System.out.println("Duration: " + args[2]);
            } else {
                System.out.println("Duration: Infinite (until stopped)");
            }
            System.out.println("Press Ctrl+C to stop");
            System.out.println();
            
            // Add a small delay before starting
            Thread.sleep(2000);
            System.out.println("Starting key presses...");
            
            long startTime = System.currentTimeMillis();
            int cycleCount = 0;
            
            while (true) {
                // Check if duration has been exceeded
                if (durationMillis > 0 && (System.currentTimeMillis() - startTime) >= durationMillis) {
                    System.out.println("\nDuration completed. Stopping...");
                    break;
                }
                
                // Press each character in the sequence
                cycleCount++;
                System.out.print("Cycle " + cycleCount + ": ");
                
                char[] charArray = characters.toCharArray();
                for (int i = 0; i < charArray.length; i++) {
                    char c = charArray[i];
                    pressKey(robot, c);
                    System.out.print(c);
                    if (i < charArray.length - 1) {
                        System.out.print(" -> ");
                    }
                    Thread.sleep(50); // Small delay between characters
                }
                
                System.out.println(" (completed)");
                
                // Wait for the specified interval
                Thread.sleep(intervalMillis);
            }
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Parses character input string to handle both regular strings and array format
     * Supports formats like "hello", "['a','b','c']", "[h,e,l,l,o]"
     */
    private static String parseCharacters(String input) {
        input = input.trim();
        
        // Check if input is in array format like ['a','b','c'] or [a,b,c]
        if (input.startsWith("[") && input.endsWith("]")) {
            // Remove brackets
            String content = input.substring(1, input.length() - 1).trim();
            
            if (content.isEmpty()) {
                throw new IllegalArgumentException("Empty character array");
            }
            
            // Split by comma and process each character
            String[] parts = content.split(",");
            StringBuilder result = new StringBuilder();
            
            for (String part : parts) {
                part = part.trim();
                
                // Remove quotes if present
                if ((part.startsWith("'") && part.endsWith("'")) || 
                    (part.startsWith("\"") && part.endsWith("\""))) {
                    part = part.substring(1, part.length() - 1);
                }
                
                if (part.length() == 1) {
                    result.append(part);
                } else if (part.length() > 1) {
                    // If it's a multi-character string, add each character
                    result.append(part);
                } else {
                    throw new IllegalArgumentException("Invalid character in array: '" + part + "'");
                }
            }
            
            return result.toString();
        }
        
        // Regular string input
        return input;
    }
    
    /**
     * Parses GUI comma-separated character input
     * Supports formats like "'a','b','c'" or "a,b,c"
     */
    private static char[] parseGUICharacters(String input) {
        input = input.trim();
        
        if (input.isEmpty()) {
            throw new IllegalArgumentException("Empty character input");
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
            } else if (part.length() == 0) {
                throw new IllegalArgumentException("Empty character at position " + (i + 1));
            } else {
                throw new IllegalArgumentException("Invalid character at position " + (i + 1) + ": '" + part + "' (must be single character)");
            }
        }
        
        return result;
    }
    
    /**
     * Parses time string to milliseconds
     * Supports formats like "5s", "2m", "1h"
     */
    private static long parseTimeToMillis(String timeStr) {
        timeStr = timeStr.toLowerCase().trim();
        
        if (timeStr.isEmpty()) {
            throw new IllegalArgumentException("Time string cannot be empty");
        }
        
        char unit = timeStr.charAt(timeStr.length() - 1);
        String numberPart = timeStr.substring(0, timeStr.length() - 1);
        
        try {
            long value = Long.parseLong(numberPart);
            
            switch (unit) {
                case 's': // seconds
                    return value * 1000;
                case 'm': // minutes
                    return value * 60 * 1000;
                case 'h': // hours
                    return value * 60 * 60 * 1000;
                default:
                    // If no unit specified, assume seconds
                    return Long.parseLong(timeStr) * 1000;
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid time format: " + timeStr + ". Use format like '5s', '2m', '1h'");
        }
    }
    
    /**
     * Simulates pressing a key using Robot
     */
    private static void pressKey(Robot robot, char c) {
        try {
            // Convert character to key code
            int keyCode = getKeyCode(c);
            
            if (keyCode != -1) {
                // Handle shift modifier for uppercase letters
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
            System.err.println("Error pressing key '" + c + "': " + e.getMessage());
        }
    }
    
    /**
     * Converts character to KeyEvent key code
     */
    private static int getKeyCode(char c) {
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
            
            // Shift characters
            case '!': return KeyEvent.VK_1; // Shift + 1
            case '@': return KeyEvent.VK_2; // Shift + 2
            case '#': return KeyEvent.VK_3; // Shift + 3
            case '$': return KeyEvent.VK_4; // Shift + 4
            case '%': return KeyEvent.VK_5; // Shift + 5
            case '^': return KeyEvent.VK_6; // Shift + 6
            case '&': return KeyEvent.VK_7; // Shift + 7
            case '*': return KeyEvent.VK_8; // Shift + 8
            case '(': return KeyEvent.VK_9; // Shift + 9
            case ')': return KeyEvent.VK_0; // Shift + 0
            case '_': return KeyEvent.VK_MINUS; // Shift + -
            case '+': return KeyEvent.VK_EQUALS; // Shift + =
            case ':': return KeyEvent.VK_SEMICOLON; // Shift + ;
            case '"': return KeyEvent.VK_QUOTE; // Shift + '
            case '<': return KeyEvent.VK_COMMA; // Shift + ,
            case '>': return KeyEvent.VK_PERIOD; // Shift + .
            case '?': return KeyEvent.VK_SLASH; // Shift + /
            case '{': return KeyEvent.VK_OPEN_BRACKET; // Shift + [
            case '}': return KeyEvent.VK_CLOSE_BRACKET; // Shift + ]
            case '|': return KeyEvent.VK_BACK_SLASH; // Shift + \
            case '~': return KeyEvent.VK_BACK_QUOTE; // Shift + `
            
            default:
                System.err.println("Unsupported character: '" + c + "'");
                return -1;
        }
    }
    
    /**
     * Checks if character requires shift modifier
     */
    private static boolean isShiftCharacter(char c) {
        String shiftChars = "!@#$%^&*()_+:\"<>?{}|~";
        return shiftChars.indexOf(c) != -1;
    }
}