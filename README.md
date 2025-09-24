# Auto Key Presser

A Java application that automatically presses a sequence of keys at specified intervals for a specified duration. Available in both **GUI** and **Command Line** interfaces.

## Features

- **🖥️ GUI Interface**: Easy-to-use graphical interface with real-time controls
- **💻 Command Line Interface**: Traditional CLI for automation scripts
- **Character Sequences**: Press any sequence of characters (letters, numbers, special characters)
- **Flexible Intervals**: Set intervals in seconds (s), minutes (m), or hours (h)
- **Optional Duration**: Set a specific duration or run indefinitely until stopped
- **Real-time Feedback**: Shows cycle count and characters being pressed
- **Cross-platform**: Works on Windows, macOS, and Linux
- **Easy Launchers**: Batch and shell scripts for easy access

## Requirements

- Java 8 or higher
- GUI environment (the application uses Java Robot class which requires a display)

## Compilation

```bash
# Compile the single Main.java file (contains both interfaces)
javac src/Main.java
```

## Quick Start

### Option 1: Use the Launcher (Recommended)
```bash
# Windows
launcher.bat

# Linux/macOS
chmod +x launcher.sh
./launcher.sh
```

### Option 2: Direct Launch

#### GUI Interface
```bash
# Launch GUI interface
java -cp src Main --gui
```

#### Command Line Interface
```bash
java -cp src Main <characters> <interval> [duration]
```

### Parameters

1. **characters** (required): String of characters to press in sequence
2. **interval** (required): How often to repeat the sequence
   - Format: number + unit (s=seconds, m=minutes, h=hours)
   - Examples: "2s", "30s", "1m", "2h"
3. **duration** (optional): How long to run the application
   - Format: same as interval
   - If not specified, runs indefinitely until stopped with Ctrl+C

## GUI Interface Features

The GUI provides an intuitive interface with the following components:

- **🔤 Characters Field**: Enter comma-separated characters like `'a','b','c'` 
- **🔄 Character Cycling**: Each interval presses the NEXT character in sequence (a → b → c → a...)
- **⏱️ Interval Controls**: Set interval with spinner and dropdown (seconds/minutes/hours)
- **⏰ Duration Controls**: Set duration or choose infinite mode
- **▶️ Control Buttons**: Start, Stop, and Clear Log buttons
- **📋 Real-time Log**: Shows timestamped activity with colored output
- **📊 Status Bar**: Current operation status with color indicators

### Character Input Format (GUI)
- Use comma-separated format: `'a','b','c'` or `a,b,c`
- Each character will be pressed individually on each interval
- Characters cycle automatically: a → b → c → a → b → c...

### GUI Screenshots
![GUI Interface Features](gui-features.png)
*The GUI interface showing configuration options and real-time logging*

## Command Line Examples

```bash
# Press entire "hello" string every 2 seconds for 10 seconds
java -cp src Main "hello" "2s" "10s"

# Press array sequence ['a','b','c'] as string "abc" every 1 second
java -cp src Main "['a','b','c']" "1s" "5s"

# Press "test123" every 30 seconds indefinitely
java -cp src Main "test123" "30s"

# Launch GUI interface (for character cycling)
java -cp src Main --gui
```

## GUI Examples

In the GUI, enter comma-separated characters for cycling behavior:

```
Input: 'a','b','c'
Interval: 2s
Result: 
  - At 0s: Press 'a'
  - At 2s: Press 'b' 
  - At 4s: Press 'c'
  - At 6s: Press 'a' (cycle repeats)
  - At 8s: Press 'b'
  - etc...
```

## Supported Characters

The application supports:
- **Letters**: a-z, A-Z
- **Numbers**: 0-9
- **Special Characters**: space, period, comma, semicolon, quotes, brackets, etc.
- **Shift Characters**: !, @, #, $, %, ^, &, *, (, ), _, +, :, ", <, >, ?, {, }, |, ~

## How It Works

1. The application uses Java's `Robot` class to simulate key presses
2. It parses the command line arguments for characters, interval, and duration
3. Characters are pressed in sequence with a small delay between each character
4. The sequence repeats after the specified interval
5. The application stops after the duration expires or when interrupted with Ctrl+C

## Safety Notes

- **Use Responsibly**: This tool can interfere with other applications
- **Test First**: Always test with short durations before longer runs
- **Stop Method**: Press Ctrl+C to stop the application at any time
- **Focus**: The keys will be sent to whatever application currently has focus

## Troubleshooting

### Security Warnings
Some operating systems may show security warnings when using the Robot class. You may need to:
- Grant accessibility permissions on macOS
- Run with appropriate privileges on Linux
- Allow the application through security software on Windows

### Character Not Working
If a specific character isn't being pressed correctly:
1. Check if the character is in the supported list
2. Ensure your keyboard layout matches the expected layout
3. Some special characters may behave differently based on system locale

## Interface Comparison

| Feature | GUI Interface | Command Line Interface |
|---------|---------------|------------------------|
| **Character Behavior** | 🔄 Cycles through each character | 📝 Presses entire string sequence |
| **Input Format** | `'a','b','c'` (comma-separated) | `"hello"` or `"['a','b','c']"` |
| **Ease of Use** | ✅ Point and click | ⚡ Quick for automation |
| **Real-time Control** | ✅ Start/Stop anytime | ❌ Must restart |
| **Visual Feedback** | ✅ Colored logs & status | ✅ Text output |
| **Configuration** | ✅ Dropdowns & spinners | 💻 Command arguments |
| **Suitable For** | Interactive character cycling | Scripts & text automation |

## Example Outputs

### Command Line Output
```
Auto Key Presser Started
Characters: hello
Interval: 2s
Duration: 10s
Press Ctrl+C to stop

Starting key presses...
Cycle 1: hello
Cycle 2: hello
Cycle 3: hello
Cycle 4: hello
Cycle 5: hello

Duration completed. Stopping...
```

### GUI Log Output (Cycling Mode)
```
[14:23:15] === Auto Key Presser Started (Cycling Mode) ===
[14:23:15] Characters: ['a', 'b', 'c']
[14:23:15] Interval: 2 seconds
[14:23:15] Duration: 10 seconds
[14:23:15] Starting in 3 seconds...
[14:23:18] Key pressing started!
[14:23:18] Press 1: 'a' (position 1 of 3)
[14:23:20] Press 2: 'b' (position 2 of 3)
[14:23:22] Press 3: 'c' (position 3 of 3)
[14:23:24] Press 4: 'a' (position 1 of 3)
[14:23:25] Duration completed. Stopping...
```

## License

This project is provided as-is for educational and legitimate automation purposes. Please use responsibly and in accordance with your local laws and the terms of service of any applications you interact with.
