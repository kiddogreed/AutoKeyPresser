# 🎯 Auto Key Presser# Auto Key Presser



A powerful Java application that automatically presses keyboard keys and performs mouse actions in customizable patterns. Perfect for automation, testing, or repetitive tasks.A Java application that automatically presses a sequence of keys at specified intervals for a specified duration. Available in both **GUI** and **Command Line** interfaces.



## 📋 Table of Contents## Features



- [Features](#-features)- **🖥️ GUI Interface**: Easy-to-use graphical interface with real-time controls

- [Quick Start](#-quick-start)- **💻 Command Line Interface**: Traditional CLI for automation scripts

- [Installation](#-installation)- **Character Sequences**: Press any sequence of characters (letters, numbers, special characters)

- [How to Use](#-how-to-use)- **Flexible Intervals**: Set intervals in seconds (s), minutes (m), or hours (h)

- [Parameters Guide](#-parameters-guide)- **Optional Duration**: Set a specific duration or run indefinitely until stopped

- [Examples](#-examples)- **Real-time Feedback**: Shows cycle count and characters being pressed

- [Troubleshooting](#-troubleshooting)- **Cross-platform**: Works on Windows, macOS, and Linux

- [Future Improvements](#-future-improvements)- **Easy Launchers**: Batch and shell scripts for easy access

- [Technical Requirements](#-technical-requirements)

- [License](#-license)## Requirements



## ✨ Features- Java 8 or higher

- GUI environment (the application uses Java Robot class which requires a display)

- **Character Array Input**: Press sequences of characters in a cycling pattern

- **Flexible Timing**: Set duration and intervals in seconds, minutes, or hours## Compilation

- **Mouse Actions**: Move cursor in any direction (up, down, left, right)

- **Special Keys**: Insert spaces and new lines automatically```bash

- **Real-time Control**: Start, stop, and monitor activity in real-time# Compile the single Main.java file (contains both interfaces)

- **Visual Feedback**: Colored logs with timestamps and detailed informationjavac src/Main.java

- **Cross-platform**: Works on Windows, macOS, and Linux```



## 🚀 Quick Start

### Prerequisites
- Java 8 or higher installed
- GUI environment (for desktop interface)

### Running the Application

#### Method 1: GUI Interface (Recommended)
```bash
# Navigate to project directory
cd AutoKeyPresser

# Compile the application
javac src/Main.java

# Run the GUI interface
java -cp src Main
```

#### Method 2: Command Line Interface
```bash
# Run with parameters: characters, interval, duration (optional)
java -cp src Main "A,B,C" "1s" "30s"

# Examples:
java -cp src Main "hello" "2s" "10s"      # Type "hello" every 2 seconds for 10 seconds
java -cp src Main "1,2,3" "1s"           # Cycle through 1,2,3 every second indefinitely
```

#### Method 3: Using Launcher Scripts
```bash
# Windows
launcher.bat

# Linux/macOS
chmod +x launcher.sh
./launcher.sh
```

### For First-Time Users
1. **Compile**: Run `javac src/Main.java` to compile the application
2. **Launch**: Run `java -cp src Main` to open the GUI
3. **Configure**: Enter your character sequence (e.g., `A,B,C`)
4. **Set Timing**: Choose duration and interval
5. **Prepare Target**: Open a text editor (like Notepad)
6. **Start**: Click the "▶️ Start Key Pressing" button
7. **Switch**: Quickly switch to your text editor to see the automation
## 💾 Installation

### Option 1: From Source (Recommended for Development)
1. **Clone or download** the project files
2. **Install Java 8 or higher** if not already installed
3. **Open terminal/command prompt** in the project directory
4. **Compile**: `javac src/Main.java`
5. **Run**: `java -cp src Main`

### Option 2: Pre-built JAR (When Available)
1. **Download** `AutoKeyPresser.jar`
2. **Double-click** the JAR file or run `java -jar AutoKeyPresser.jar`
3. **Requires** Java Runtime Environment

### Option 3: Standalone Executable (When Available)
- **Windows**: Download `AutoKeyPresser.exe` - No Java required
- **Cross-platform**: Use Java JAR version

## 📖 How to Use

### Step 1: Launch the Application
```bash
# Compile the application
javac src/Main.java

# Run the GUI interface
java -cp src Main
```

### Step 2: Configure Parameters

#### 1. Character Array
- **Format**: Comma-separated characters
- **Examples**: 
  - `A,B,C` - Cycles through A → B → C → A...
  - `'1','2','3'` - Cycles through numbers
  - `h,e,l,l,o` - Spells out "hello" one character at a time

#### 2. Program Duration
- **Purpose**: Total time the application will run
- **Format**: Number + Unit (seconds/minutes/hours)
- **Examples**: `30 seconds`, `5 minutes`, `1 hour`

#### 3. Key Press Interval
- **Purpose**: Time between each character press
- **Format**: Number + Unit (seconds/minutes/hours)
- **Examples**: `1 second`, `30 seconds`, `2 minutes`

#### 4. Optional Actions (Advanced)
- **Enable**: Check the box to activate actions
- **Actions**: Choose what happens after each character:
  - **Hover Up**: Move mouse cursor up 10 pixels
  - **Hover Down**: Move mouse cursor down 10 pixels
  - **Hover Left**: Move mouse cursor left 10 pixels
  - **Hover Right**: Move mouse cursor right 10 pixels
  - **Space Bar**: Press space key
  - **Next Line**: Press Enter key

### Step 3: Prepare Your Target Application
1. Open the application where you want the keys to appear (e.g., Notepad, Word, text editor)
2. Click in the text area to ensure it's focused
3. Return to Auto Key Presser

### Step 4: Start the Process
1. Click the "▶️ Start Key Pressing" button
2. Quickly switch to your target application
3. Watch the automated key presses begin

## 🔧 Troubleshooting

### Common Issues and Solutions

#### "Java command not found"
```bash
# Check if Java is installed
java -version

# If not installed, download from:
# https://www.oracle.com/java/technologies/downloads/
```

#### "Class not found" or compilation errors
```bash
# Make sure you're in the right directory
cd AutoKeyPresser

# Compile again
javac src/Main.java

# Verify the .class file was created
ls src/Main.class  # Linux/macOS
dir src\Main.class # Windows
```

#### Application launches but keys don't appear
1. **Check target application focus** - Click in the text area
2. **Try a simple test** - Use Notepad with characters `A,B,C`
3. **Check permissions** - Some applications may block automated input
4. **Verify timing** - Use longer intervals like `2 seconds` for testing

#### GUI doesn't open
1. **Check Java version** - Requires Java 8 or higher with GUI support
2. **Try headless mode** - Use command line interface instead
3. **Check display** - Ensure you're not using SSH without X forwarding

## 💻 Command Line Examples

```bash
# Press entire "hello" string every 2 seconds for 10 seconds
java -cp src Main "hello" "2s" "10s"

# Press array sequence ['a','b','c'] as string "abc" every 1 second
java -cp src Main "['a','b','c']" "1s" "5s"

# Press "test123" every 30 seconds indefinitely
java -cp src Main "test123" "30s"
```



### Step 4: Start the Process```

1. Click "▶ Start Key Pressing"Input: 'a','b','c'

2. The application will count down 3 secondsInterval: 2s

3. Switch to your target application quicklyResult: 

4. Watch the characters appear automatically  - At 0s: Press 'a'

  - At 2s: Press 'b' 

### Step 5: Monitor and Control  - At 4s: Press 'c'

- **Activity Log**: View real-time progress with timestamps  - At 6s: Press 'a' (cycle repeats)

- **Status Bar**: See current operation status  - At 8s: Press 'b'

- **Stop Button**: Click "⏹ Stop" to halt immediately  - etc...

- **Clear Log**: Clear the activity log anytime```



## 📊 Parameters Guide## Supported Characters



### Character Array FormatsThe application supports:

- **Letters**: a-z, A-Z

| Input Format | Result | Description |- **Numbers**: 0-9

|--------------|--------|-------------|- **Special Characters**: space, period, comma, semicolon, quotes, brackets, etc.

| `A,B,C` | A → B → C → A... | Simple letters |- **Shift Characters**: !, @, #, $, %, ^, &, *, (, ), _, +, :, ", <, >, ?, {, }, |, ~

| `'1','2','3'` | 1 → 2 → 3 → 1... | Numbers with quotes |

| `h,e,l,l,o` | h → e → l → l → o → h... | Spell out words |## How It Works

| `X,Y,Z` | X → Y → Z → X... | Any characters |

1. The application uses Java's `Robot` class to simulate key presses

### Time Units2. It parses the command line arguments for characters, interval, and duration

3. Characters are pressed in sequence with a small delay between each character

| Unit | Examples | Use Case |4. The sequence repeats after the specified interval

|------|----------|----------|5. The application stops after the duration expires or when interrupted with Ctrl+C

| **seconds** | 1-999 seconds | Quick tests, fast typing |

| **minutes** | 1-999 minutes | Medium duration tasks |## Safety Notes

| **hours** | 1-999 hours | Long-running automation |

- **Use Responsibly**: This tool can interfere with other applications

### Action Effects- **Test First**: Always test with short durations before longer runs

- **Stop Method**: Press Ctrl+C to stop the application at any time

| Action | Effect | Best Used For |- **Focus**: The keys will be sent to whatever application currently has focus

|--------|--------|---------------|

| **Hover Up** | Mouse moves up | Drawing, UI testing |## Troubleshooting

| **Hover Down** | Mouse moves down | Scrolling simulation |

| **Hover Left** | Mouse moves left | Navigation testing |### Security Warnings

| **Hover Right** | Mouse moves right | Menu interaction |Some operating systems may show security warnings when using the Robot class. You may need to:

| **Space Bar** | Adds spaces | Word separation |- Grant accessibility permissions on macOS

| **Next Line** | New line | List creation, formatting |- Run with appropriate privileges on Linux

- Allow the application through security software on Windows

## 🎯 Examples

### Character Not Working

### Example 1: Simple Text EntryIf a specific character isn't being pressed correctly:

```1. Check if the character is in the supported list

Character Array: H,e,l,l,o2. Ensure your keyboard layout matches the expected layout

Duration: 10 seconds3. Some special characters may behave differently based on system locale

Interval: 1 second

Actions: Disabled## Interface Comparison

```

**Result**: Types "Hello" character by character every second for 10 seconds| Feature | GUI Interface | Command Line Interface |

|---------|---------------|------------------------|

### Example 2: Spaced Words| **Character Behavior** | 🔄 Cycles through each character | 📝 Presses entire string sequence |

```| **Input Format** | `'a','b','c'` (comma-separated) | `"hello"` or `"['a','b','c']"` |

Character Array: A,B,C| **Ease of Use** | ✅ Point and click | ⚡ Quick for automation |

Duration: 15 seconds  | **Real-time Control** | ✅ Start/Stop anytime | ❌ Must restart |

Interval: 2 seconds| **Visual Feedback** | ✅ Colored logs & status | ✅ Text output |

Actions: ✅ Enable → Space Bar| **Configuration** | ✅ Dropdowns & spinners | 💻 Command arguments |

```| **Suitable For** | Interactive character cycling | Scripts & text automation |

**Result**: Types "A B C A B C..." with spaces between letters

## Example Outputs

### Example 3: List Creation

```### Command Line Output

Character Array: '1','2','3'```

Duration: 30 secondsAuto Key Presser Started

Interval: 3 seconds  Characters: hello

Actions: ✅ Enable → Next LineInterval: 2s

```Duration: 10s

**Result**: Creates a numbered list:Press Ctrl+C to stop

```

1Starting key presses...

2Cycle 1: hello

3Cycle 2: hello

1Cycle 3: hello

2Cycle 4: hello

3Cycle 5: hello

...

```Duration completed. Stopping...

```

### Example 4: Mouse Movement Pattern

```### GUI Log Output (Cycling Mode)

Character Array: X,Y```

Duration: 20 seconds[14:23:15] === Auto Key Presser Started (Cycling Mode) ===

Interval: 2 seconds[14:23:15] Characters: ['a', 'b', 'c']

Actions: ✅ Enable → Hover Right[14:23:15] Interval: 2 seconds

```[14:23:15] Duration: 10 seconds

**Result**: Types X, moves mouse right, types Y, moves mouse right, repeats[14:23:15] Starting in 3 seconds...

[14:23:18] Key pressing started!

## 🛠️ Troubleshooting[14:23:18] Press 1: 'a' (position 1 of 3)

[14:23:20] Press 2: 'b' (position 2 of 3)

### Common Issues[14:23:22] Press 3: 'c' (position 3 of 3)

[14:23:24] Press 4: 'a' (position 1 of 3)

#### "Failed to initialize Robot"[14:23:25] Duration completed. Stopping...

- **Cause**: Security restrictions or missing permissions```

- **Solution**: 

  - Run as administrator (Windows)## License

  - Grant accessibility permissions (macOS)

  - Check security software settingsThis project is provided as-is for educational and legitimate automation purposes. Please use responsibly and in accordance with your local laws and the terms of service of any applications you interact with.


#### Keys not appearing in target application
- **Cause**: Target application not focused
- **Solution**: 
  - Click in the target application's text area
  - Ensure no other applications are blocking input
  - Try running as administrator

#### Mouse movements not working
- **Cause**: Screen resolution or permission issues
- **Solution**:
  - Check display settings
  - Ensure mouse is not locked by other software
  - Try different movement actions

#### Application not starting
- **Cause**: Java not installed or wrong version
- **Solution**:
  - Download Java Runtime Environment (JRE) 8 or higher
  - Use the standalone executable version
  - Check system compatibility

### Performance Tips

1. **For better performance**: Use shorter intervals (1-2 seconds)
2. **For system stability**: Avoid very long durations without monitoring
3. **For accuracy**: Ensure target application remains focused
4. **For testing**: Start with short durations (10-30 seconds)

## 🔮 Future Improvements

### Planned Features

#### Version 2.0
- [ ] **Custom Key Sequences**: Support for function keys (F1-F12), Ctrl combinations
- [ ] **Mouse Clicking**: Left/right click actions at current position
- [ ] **Variable Intervals**: Different timing for each character
- [ ] **Sound Notifications**: Audio alerts for start/stop/completion
- [ ] **Profile Saving**: Save and load frequently used configurations

#### Version 2.1
- [ ] **Hotkey Support**: Global hotkeys to start/stop without switching windows
- [ ] **Window Targeting**: Automatically focus specific applications
- [ ] **Macro Recording**: Record mouse and keyboard sequences
- [ ] **Conditional Logic**: If-then rules based on screen content
- [ ] **Multi-threading**: Run multiple sequences simultaneously

#### Version 2.2
- [ ] **Image Recognition**: Trigger actions based on screen images
- [ ] **API Integration**: REST API for external control
- [ ] **Plugin System**: Custom action plugins
- [ ] **Scheduling**: Time-based automation triggers
- [ ] **Cloud Sync**: Sync configurations across devices

### Performance Enhancements
- [ ] **Memory Optimization**: Reduce RAM usage for long-running sessions
- [ ] **CPU Efficiency**: Optimize timing mechanisms
- [ ] **Battery Saving**: Lower power consumption on laptops
- [ ] **Multi-platform**: Native packages for all operating systems

### User Experience
- [ ] **Dark Theme**: Modern dark UI option
- [ ] **Accessibility**: Screen reader support, keyboard navigation
- [ ] **Internationalization**: Multiple language support
- [ ] **Tutorial Mode**: Interactive getting-started guide
- [ ] **Statistics**: Usage analytics and performance metrics

## 💻 Technical Requirements

### Minimum System Requirements
- **OS**: Windows 7/8/10/11, macOS 10.10+, or Linux (Ubuntu 16.04+)
- **RAM**: 256 MB available memory
- **Disk**: 50 MB free space
- **Java**: JRE 8 or higher (for .jar version)

### Recommended System Requirements
- **OS**: Windows 10/11, macOS 11+, or Linux (Ubuntu 20.04+)
- **RAM**: 512 MB available memory
- **Disk**: 100 MB free space
- **Java**: JRE 11 or higher
- **Display**: 1024x768 minimum resolution

### Development Requirements
- **JDK**: Java Development Kit 8 or higher
- **IDE**: Any Java IDE (IntelliJ IDEA, Eclipse, VS Code)
- **Build Tools**: Maven or Gradle (optional)
- **Git**: For version control

## 🔧 Building from Source

### Prerequisites
```bash
# Check Java version
java -version
javac -version

# Should show Java 8 or higher
```

### Compilation Steps
```bash
# 1. Clone repository
git clone https://github.com/kiddogreed/AutoKeyPresser.git
cd AutoKeyPresser

# 2. Compile source code
javac src/Main.java

# 3. Run application
java -cp src Main

# 4. Create JAR file (if jar command available)
jar cfe AutoKeyPresser.jar Main -C src .

# 5. Run JAR file
java -jar AutoKeyPresser.jar
```

### Creating Standalone Executable
```bash
# Using jpackage (Java 14+)
jpackage --input . --name AutoKeyPresser --main-jar AutoKeyPresser.jar --main-class Main --type exe

# Or use launch4j (Windows)
# Download launch4j and create wrapper executable
```

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📞 Support

- **Issues**: [GitHub Issues](https://github.com/kiddogreed/AutoKeyPresser/issues)
- **Documentation**: This README file
- **Email**: [Contact the developer](mailto:your-email@example.com)

## 🏆 Acknowledgments

- Built with Java Swing for cross-platform compatibility
- Uses Robot class for reliable keyboard and mouse automation
- Inspired by the need for simple automation tools
- Community feedback and feature requests

## 🔧 Technical Documentation

For developers and advanced users who want to understand how the application works internally:

### 📚 Available Documentation

#### **[TECHNICAL_DOCS.md](TECHNICAL_DOCS.md)** - Complete Technical Guide
- **Application Architecture**: High-level design and component structure
- **Code Structure**: Detailed explanation of classes and methods
- **Core Algorithms**: Character parsing, key pressing engine, timing system
- **Threading Model**: How the application handles concurrent operations
- **GUI Implementation**: Layout management and user interface design
- **Character Processing**: How characters are mapped to key codes
- **Error Handling**: Exception handling and recovery mechanisms
- **Extension Points**: How to add new features and customize the application

#### **Key Technical Highlights**

##### **🏗️ Architecture Overview**
```java
Main.java (Single-file application)
├── Main class                 // Entry point & CLI interface
└── AutoKeyPresserUI class    // Complete GUI implementation
    ├── UI Components         // Swing-based interface
    ├── Robot Integration     // Java AWT Robot for key simulation
    ├── Threading Management  // Background processing
    └── Event Handling        // User interaction management
```

##### **⚙️ Core Engine Process**
1. **Input Parsing**: Character array validation and conversion
2. **Timing Calculation**: Duration and interval processing  
3. **Thread Creation**: Background execution thread
4. **Key Simulation**: Robot class integration for system-level key presses
5. **Progress Tracking**: Real-time logging and status updates
6. **Graceful Shutdown**: Clean thread termination and resource cleanup

##### **🧵 Threading Strategy**
- **Main Thread (EDT)**: GUI events and user interactions
- **Background Thread**: Key pressing loop and timing management
- **Thread-Safe Communication**: Volatile flags and SwingUtilities
- **Clean Shutdown**: Proper thread lifecycle management

##### **🎯 Key Features Explained**
- **Character Cycling**: Array index management with modulo arithmetic
- **Precise Timing**: Drift correction using system clock
- **Cross-Platform Keys**: Java KeyEvent constants for compatibility
- **Shift Key Handling**: Automatic detection for uppercase and symbols
- **Mouse Integration**: Cursor position tracking and movement
- **Error Recovery**: Graceful handling of system restrictions

#### **🔍 For Different Audiences**

##### **Developers & Contributors**
- Study `TECHNICAL_DOCS.md` for complete implementation details
- Review threading model for concurrent programming patterns
- Examine error handling strategies for robust application design
- Understand extension points for adding new features

##### **System Administrators** 
- Learn about Robot class permissions and security considerations
- Understand system-level integration requirements
- Review cross-platform compatibility implementation

##### **Computer Science Students**
- Study GUI programming with Java Swing
- Learn thread-safe programming techniques
- Examine real-world application of design patterns
- Understand system-level programming with Robot class

##### **Automation Engineers**
- Understand timing precision and accuracy mechanisms
- Learn character-to-keycode mapping strategies
- Study error handling in automation scenarios
- Review extensibility for custom automation needs

### 📖 Reading Guide

1. **Start with**: [TECHNICAL_DOCS.md](TECHNICAL_DOCS.md) - Main technical documentation
2. **Then review**: Source code `src/Main.java` with documentation as reference
3. **For specific topics**: Use the technical docs table of contents
4. **For implementation**: Follow code examples and algorithm explanations

### 💡 Learning Outcomes

After reviewing the technical documentation, you'll understand:

- ✅ **Java Swing Architecture**: Complete GUI application structure
- ✅ **Threading in GUI Applications**: Background processing with UI updates
- ✅ **System Integration**: Using Robot class for automation
- ✅ **Error Handling Patterns**: Robust exception management
- ✅ **Timing and Precision**: Accurate interval management
- ✅ **Cross-Platform Development**: Java compatibility considerations
- ✅ **User Experience Design**: Real-time feedback and logging
- ✅ **Code Organization**: Single-file application structure

---

**⚠️ Disclaimer**: This tool is for legitimate automation and testing purposes. Please use responsibly and in accordance with your local laws and the terms of service of applications you interact with.