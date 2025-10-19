# 📖 Auto Key Presser - User Manual

A comprehensive guide for using Auto Key Presser application for keyboard and mouse automation.

## 🎯 Getting Started

### What is Auto Key Presser?
Auto Key Presser is a desktop application that automatically presses keyboard keys and performs mouse actions in customizable patterns. It's perfect for:
- **Testing applications**: Simulate user input for software testing
- **Automation tasks**: Automate repetitive keyboard/mouse actions  
- **Gaming**: Automate repetitive game actions (where permitted)
- **Data entry**: Speed up form filling or data input tasks
- **Presentations**: Create dynamic typing effects

### System Requirements
- **Operating System**: Windows 7+, macOS 10.10+, or Linux (Ubuntu 16.04+)
- **Memory**: 256 MB RAM minimum
- **Java**: Version 8 or higher (not required for standalone executable)
- **Display**: 1024x768 minimum resolution
- **Permissions**: Administrator rights may be required for some actions

## 🖥️ Interface Overview

### Main Window Components

```
┌─────────────────────────────────────────────────────────────┐
│ Auto Key Presser - Character Array Input          [×][□][─] │
├─────────────────────────────────────────────────────────────┤
│ Configuration Parameters                                    │
│ ┌─────────────────────────────────────────────────────────┐ │
│ │ 1. Character Array: [A,B,C ]                            │ │
│ │ 2. Program Duration: [10] [seconds ▼]                   │ │
│ │ 3. Key Press Interval: [1] [seconds ▼]                  │ │
│ │ 4. Optional Actions: □ Enable [Hover Up ▼]              │ │
│ └─────────────────────────────────────────────────────────┘ │
├─────────────────────────────────────────────────────────────┤
│ Control                                                     │
│ [▶ Start Key Pressing] [⏹ Stop] [🗑 Clear Log]              │
├─────────────────────────────────────────────────────────────┤
│ Activity Log                                                │
│ ┌─────────────────────────────────────────────────────────┐ │
│ │ [14:23:15] 🚀 === Auto Key Presser Started ===          │ │
│ │ [14:23:15] 📝 Character Array: ['A', 'B', 'C']          │ │
│ │ [14:23:18] ⌨️ Press 1: 'A' (position 1/3)               │ │
│ │ [14:23:19] ⌨️ Press 2: 'B' (position 2/3)               │ │
│ └─────────────────────────────────────────────────────────┘ │
├─────────────────────────────────────────────────────────────┤
│ Ready - Open a text editor and click Start                  │
└─────────────────────────────────────────────────────────────┘
```

### Color Coding
- **Green Text**: Successfully started operations
- **Red Text**: Errors or stopped operations
- **Orange Text**: Starting/initializing operations
- **Blue Text**: Completed or ready status
- **Black Background**: Activity log area for better readability

## 📝 Parameter Configuration

### 1. Character Array (Required)

#### Purpose
Defines which characters will be pressed in sequence.

#### Input Formats
| Format | Example | Result |
|--------|---------|--------|
| Simple | `A,B,C` | Types A → B → C → A... |
| Quoted | `'1','2','3'` | Types 1 → 2 → 3 → 1... |
| Mixed | `X,Y,Z` | Types X → Y → Z → X... |
| Numbers | `1,2,3,4,5` | Types 1 → 2 → 3 → 4 → 5 → 1... |
| Letters | `h,e,l,l,o` | Spells hello one character at a time |

#### Rules
- **Separator**: Always use commas (`,`) between characters
- **Single Characters**: Each element must be exactly one character
- **Quotes**: Optional but recommended for clarity (`'A','B','C'`)
- **Spaces**: Spaces are valid characters if included
- **Case Sensitive**: `A` and `a` are different characters

#### Examples
```
Valid:
✅ A,B,C
✅ 'X','Y','Z'
✅ 1,2,3,4,5
✅ h,e,l,l,o, ,w,o,r,l,d
✅ !,@,#,$,%

Invalid:
❌ ABC (missing commas)
❌ A B C (spaces instead of commas)
❌ 'AB','CD' (multiple characters)
❌ A,,C (empty element)
```

### 2. Program Duration (Required)

#### Purpose
Sets how long the entire program will run before automatically stopping.

#### Components
- **Number Field**: Enter a positive integer (1-999)
- **Unit Dropdown**: Choose seconds, minutes, or hours

#### Examples
| Input | Duration | Best For |
|-------|----------|----------|
| `30 seconds` | 30 seconds | Quick tests |
| `5 minutes` | 5 minutes | Medium tasks |
| `2 hours` | 2 hours | Long automation |
| `1 minute` | 1 minute | Demonstrations |

#### Guidelines
- **Short durations**: Good for testing and verification
- **Long durations**: Monitor the process periodically
- **Very long durations**: Consider system resources and stability

### 3. Key Press Interval (Required)

#### Purpose
Controls the time delay between each individual character press.

#### Components
- **Number Field**: Enter a positive integer (1-999)
- **Unit Dropdown**: Choose seconds, minutes, or hours

#### Examples
| Input | Frequency | Use Case |
|-------|-----------|----------|
| `1 second` | 1 character per second | Normal typing simulation |
| `500 milliseconds` | 2 characters per second | Fast typing |
| `2 seconds` | 1 character every 2 seconds | Slow, deliberate input |
| `30 seconds` | 1 character every 30 seconds | Periodic input |

#### Considerations
- **Faster intervals**: May overwhelm target applications
- **Slower intervals**: Better for applications that need processing time
- **System performance**: Faster intervals use more system resources

### 4. Optional Actions (Advanced)

#### Purpose
Performs additional actions after each character is pressed.

#### Enable Checkbox
- **Checked**: Actions will be performed after each character
- **Unchecked**: Only characters will be pressed

#### Available Actions

##### Mouse Movement Actions
| Action | Effect | Movement |
|--------|--------|----------|
| **Hover Up** | Moves mouse cursor upward | 10 pixels up |
| **Hover Down** | Moves mouse cursor downward | 10 pixels down |
| **Hover Left** | Moves mouse cursor left | 10 pixels left |
| **Hover Right** | Moves mouse cursor right | 10 pixels right |

##### Keyboard Actions
| Action | Effect | Key Pressed |
|--------|--------|-------------|
| **Space Bar** | Adds space character | Space key |
| **Next Line** | Creates new line | Enter key |

#### Action Examples

##### Example 1: Creating Spaced Text
```
Character Array: A,B,C
Action: Space Bar
Result: A B C A B C A B C...
```

##### Example 2: Creating a List
```
Character Array: 1,2,3
Action: Next Line
Result:
1
2
3
1
2
3
...
```

##### Example 3: Mouse Drawing Pattern
```
Character Array: X,O
Action: Hover Right
Result: Types X, moves mouse right, types O, moves mouse right, repeats
```

## 🚀 Operating the Application

### Step-by-Step Process

#### Step 1: Launch Application
1. **Double-click** the application icon
2. **Wait** for the interface to load
3. **Verify** all controls are visible and responsive

#### Step 2: Configure Parameters
1. **Enter character array** in the first field
2. **Set program duration** using number and dropdown
3. **Set key press interval** using number and dropdown
4. **Optionally enable actions** and select an action type

#### Step 3: Prepare Target Application
1. **Open the target application** (Notepad, Word, browser, etc.)
2. **Click in the text input area** to ensure it's focused
3. **Position the cursor** where you want text to appear
4. **Minimize or arrange windows** so you can access both applications

#### Step 4: Start the Process
1. **Return to Auto Key Presser**
2. **Click "▶ Start Key Pressing"** button
3. **Watch the 3-second countdown** in the log
4. **Quickly switch to target application** before countdown ends
5. **Observe the automated input** beginning

#### Step 5: Monitor Progress
- **Activity Log**: Shows real-time progress with timestamps
- **Status Bar**: Displays current operation state
- **Character Sequence**: Each press is logged with position information

#### Step 6: Stop or Complete
- **Manual Stop**: Click "⏹ Stop" button anytime
- **Automatic Stop**: Process stops when duration is reached
- **Emergency Stop**: Use Ctrl+Alt+Delete if application becomes unresponsive

### Control Buttons

#### ▶ Start Key Pressing
- **Function**: Begins the automated process
- **State Changes**: Button becomes disabled, other inputs lock
- **Countdown**: 3-second delay before actual key pressing starts
- **Requirements**: All parameters must be valid

#### ⏹ Stop
- **Function**: Immediately halts the process
- **State Changes**: Re-enables all controls
- **Safety**: Safe to use at any time during operation
- **Effect**: Logs stop reason and time

#### 🗑 Clear Log
- **Function**: Clears all text from activity log
- **Availability**: Always available
- **Use**: Clean up before starting new session
- **No Effect**: Does not affect current operation

### Activity Log

#### Log Entry Format
```
[HH:MM:SS] 📝 Message content
```

#### Log Message Types
| Icon | Type | Example |
|------|------|---------|
| 🚀 | Start | `=== Auto Key Presser Started ===` |
| 📝 | Configuration | `Character Array: ['A', 'B', 'C']` |
| ⏱️ | Timing | `Duration: 30 seconds` |
| 🎯 | Action | `Action: Space Bar` |
| ⚠️ | Warning | `Make sure a text editor is open and focused!` |
| 🕐 | Countdown | `Starting in 3 seconds...` |
| ✅ | Success | `Key pressing started!` |
| ⌨️ | Key Press | `Press 1: 'A' (position 1/3)` |
| ❌ | Error/Stop | `Stopped by user` |
| ⏰ | Completion | `Duration completed. Total presses: 15` |

#### Reading the Log
- **Timestamps**: Help track timing and performance
- **Icons**: Quick visual identification of message types
- **Details**: Comprehensive information about each action
- **Scroll**: Automatically scrolls to show latest messages

## 💡 Usage Scenarios

### Scenario 1: Software Testing

#### Objective
Test input validation in a form application.

#### Setup
```
Character Array: A,B,C,1,2,3,!,@,#
Duration: 2 minutes
Interval: 3 seconds
Actions: Next Line (to test each character on new line)
```

#### Process
1. Open the application being tested
2. Navigate to the input field
3. Start Auto Key Presser
4. Observe how the application handles each character type
5. Document any validation errors or unexpected behavior

### Scenario 2: Gaming Automation

#### Objective
Automate repetitive key sequences in a game (where permitted).

#### Setup
```
Character Array: w,a,s,d
Duration: 10 minutes
Interval: 1 second
Actions: Disabled
```

#### Process
1. Launch the game
2. Position character appropriately
3. Start Auto Key Presser
4. Monitor game state periodically
5. Stop if game state changes unexpectedly

### Scenario 3: Data Entry Assistance

#### Objective
Speed up repetitive data entry tasks.

#### Setup
```
Character Array: Y,e,s
Duration: 5 minutes  
Interval: 2 seconds
Actions: Space Bar (to separate entries)
```

#### Process
1. Open the data entry application
2. Position cursor in first field
3. Start Auto Key Presser
4. Manually navigate between fields as needed
5. Monitor for completion

### Scenario 4: Presentation Effects

#### Objective
Create animated typing effect for presentations.

#### Setup
```
Character Array: H,e,l,l,o, ,W,o,r,l,d,!
Duration: 30 seconds
Interval: 2 seconds
Actions: Disabled
```

#### Process
1. Open presentation software
2. Navigate to text area
3. Start Auto Key Presser during presentation
4. Characters appear to be typed live
5. Engage audience with "live coding" effect

## ⚠️ Safety and Best Practices

### Before Starting
- [ ] **Test with short durations** (10-30 seconds) first
- [ ] **Save important work** in target applications
- [ ] **Close unnecessary applications** to avoid conflicts
- [ ] **Disable screen savers** and power saving modes
- [ ] **Ensure stable power supply** for long operations

### During Operation
- [ ] **Monitor the process** periodically
- [ ] **Keep Stop button accessible** for emergency halt
- [ ] **Don't move the mouse** if using mouse actions
- [ ] **Avoid switching applications** unexpectedly
- [ ] **Watch for error messages** in the log

### After Operation
- [ ] **Verify results** in target application
- [ ] **Save any generated content** immediately
- [ ] **Review log messages** for any issues
- [ ] **Close applications properly** when finished

### Troubleshooting Common Issues

#### Issue: Keys Not Appearing
**Symptoms**: Auto Key Presser runs but no keys appear in target application

**Solutions**:
1. **Check Focus**: Ensure target application window is active and focused
2. **Click in Text Area**: Make sure cursor is in an editable text field
3. **Check Permissions**: Run Auto Key Presser as administrator
4. **Disable Interference**: Close other automation software temporarily
5. **Test Different Application**: Try with simple text editor (Notepad)

#### Issue: Mouse Actions Not Working
**Symptoms**: Characters appear but mouse doesn't move

**Solutions**:
1. **Check Mouse Lock**: Ensure no other software is controlling the mouse
2. **Verify Action Selection**: Confirm correct action is selected from dropdown
3. **Check Screen Resolution**: Mouse movements might be too small to notice
4. **Test Movement**: Try different hover directions
5. **Restart Application**: Close and reopen Auto Key Presser

#### Issue: Application Becomes Unresponsive
**Symptoms**: Interface freezes or doesn't respond to clicks

**Solutions**:
1. **Wait Briefly**: Sometimes temporary lag occurs with system operations
2. **Use Stop Button**: Try clicking Stop button if responsive
3. **Force Close**: Use Task Manager (Ctrl+Shift+Esc) to end process
4. **Check System Resources**: Monitor CPU and memory usage
5. **Restart Computer**: If system becomes unstable

#### Issue: Wrong Characters Appear
**Symptoms**: Different characters than specified appear in target

**Solutions**:
1. **Check Keyboard Layout**: Verify system keyboard layout matches expected
2. **Verify Character Array**: Review input for typos or formatting errors
3. **Test with Simple Characters**: Try basic letters (A,B,C) first
4. **Check Special Characters**: Some symbols may require different input methods
5. **Update Application**: Ensure latest version is installed

#### Issue: Timing Issues
**Symptoms**: Characters appear too fast, too slow, or irregularly

**Solutions**:
1. **Adjust Interval**: Modify key press interval to appropriate speed
2. **Check System Load**: High CPU usage can affect timing accuracy
3. **Close Background Apps**: Reduce system load by closing unnecessary programs
4. **Test Different Intervals**: Try various timing settings to find optimal speed
5. **Monitor Performance**: Watch system resources during operation

## 📊 Performance Optimization

### System Requirements for Optimal Performance

#### Minimum Configuration
- **CPU**: 1 GHz processor
- **RAM**: 256 MB available memory
- **OS**: Windows 7, macOS 10.10, or Linux equivalent
- **Java**: Version 8 or higher

#### Recommended Configuration
- **CPU**: 2 GHz dual-core processor
- **RAM**: 1 GB available memory
- **OS**: Latest stable operating system version
- **Java**: Version 11 or higher
- **Storage**: SSD for faster application loading

### Tips for Better Performance

#### Short-term Operations (< 5 minutes)
- Use any interval timing
- Monitor system resources lightly
- Test extensively before long runs

#### Medium-term Operations (5-60 minutes)
- Use intervals ≥ 1 second
- Monitor every 10-15 minutes
- Close unnecessary applications
- Save work frequently

#### Long-term Operations (> 1 hour)
- Use intervals ≥ 2 seconds
- Monitor every 30 minutes
- Ensure stable power supply
- Consider system cooling
- Plan for breaks/interruptions

### Resource Management
- **Memory**: Application uses minimal RAM (~50MB)
- **CPU**: Higher frequency operations use more CPU
- **Disk**: Minimal disk usage except for logging
- **Network**: No network usage required

This comprehensive user manual should help users understand and effectively use Auto Key Presser for their automation needs.