# 📦 Auto Key Presser - Installation Guide

This guide will help you install and distribute Auto Key Presser on different devices without requiring Java installation.

## 🎯 Distribution Options

### Option 1: Standalone Executable (Windows) - RECOMMENDED
Create a Windows executable that includes Java runtime.

### Option 2: Universal JAR File
Works on all platforms but requires Java to be installed.

### Option 3: Platform-Specific Packages
Native packages for Windows, macOS, and Linux.

## 🛠️ Creating Standalone Executables

### Method 1: Using jpackage (Java 14+)

#### Prerequisites
```bash
# Check if you have Java 14 or higher
java -version

# If not, download from: https://adoptium.net/
```

#### Create Windows Executable
```bash
# 1. First, create the JAR file
jar cfe AutoKeyPresser.jar Main -C src .

# 2. Create Windows executable
jpackage \
  --input . \
  --name "Auto Key Presser" \
  --main-jar AutoKeyPresser.jar \
  --main-class Main \
  --type exe \
  --dest dist \
  --win-shortcut \
  --win-menu

# Result: dist/Auto Key Presser-1.0.exe
```

#### Create macOS Application
```bash
jpackage \
  --input . \
  --name "Auto Key Presser" \
  --main-jar AutoKeyPresser.jar \
  --main-class Main \
  --type dmg \
  --dest dist

# Result: dist/Auto Key Presser-1.0.dmg
```

#### Create Linux Package
```bash
jpackage \
  --input . \
  --name "auto-key-presser" \
  --main-jar AutoKeyPresser.jar \
  --main-class Main \
  --type deb \
  --dest dist

# Result: dist/auto-key-presser_1.0-1_amd64.deb
```

### Method 2: Using Launch4j (Windows Only)

#### Download and Setup
1. Download Launch4j from: http://launch4j.sourceforge.net/
2. Install Launch4j
3. Create configuration file

#### Launch4j Configuration
Create `launch4j-config.xml`:
```xml
<launch4jConfig>
  <dontWrapJar>false</dontWrapJar>
  <headerType>gui</headerType>
  <jar>AutoKeyPresser.jar</jar>
  <outfile>AutoKeyPresser.exe</outfile>
  <errTitle>Auto Key Presser</errTitle>
  <cmdLine></cmdLine>
  <chdir>.</chdir>
  <priority>normal</priority>
  <downloadUrl>https://java.com/download</downloadUrl>
  <supportUrl>https://github.com/kiddogreed/AutoKeyPresser</supportUrl>
  <stayAlive>false</stayAlive>
  <restartOnCrash>false</restartOnCrash>
  <manifest></manifest>
  <icon>icon.ico</icon>
  <jre>
    <path>%JAVA_HOME%</path>
    <bundledJre64Bit>false</bundledJre64Bit>
    <bundledJreAsFallback>false</bundledJreAsFallback>
    <minVersion>1.8.0</minVersion>
    <maxVersion></maxVersion>
    <jdkPreference>preferJre</jdkPreference>
    <runtimeBits>64/32</runtimeBits>
  </jre>
</launch4jConfig>
```

#### Build with Launch4j
```bash
# Command line build
launch4jc launch4j-config.xml

# Result: AutoKeyPresser.exe
```

### Method 3: Using GraalVM Native Image

#### Setup GraalVM
```bash
# Download GraalVM from: https://www.graalvm.org/downloads/
# Install native-image component
gu install native-image
```

#### Create Native Executable
```bash
# Compile to native executable
native-image --no-fallback --report-unsupported-elements-at-runtime -cp src Main auto-key-presser

# Result: auto-key-presser (or auto-key-presser.exe on Windows)
```

## 📋 Step-by-Step Distribution Guide

### Step 1: Prepare Your Build Environment

#### Windows
```bash
# Install Java 14+ from Adoptium
# Download from: https://adoptium.net/temurin/releases/

# Verify installation
java -version
javac -version
```

#### macOS
```bash
# Install Java using Homebrew
brew install openjdk@17

# Or download from: https://adoptium.net/temurin/releases/
```

#### Linux (Ubuntu/Debian)
```bash
# Install Java
sudo apt update
sudo apt install openjdk-17-jdk

# Verify installation
java -version
javac -version
```

### Step 2: Compile the Application

```bash
# Navigate to project directory
cd AutoKeyPresser

# Compile source code
javac src/Main.java

# Test the application
java -cp src Main
```

### Step 3: Create JAR File

#### Method A: Using jar command
```bash
# Create manifest file
echo "Main-Class: Main" > manifest.txt

# Create JAR with manifest
jar cfm AutoKeyPresser.jar manifest.txt -C src .

# Test JAR file
java -jar AutoKeyPresser.jar
```

#### Method B: Manual JAR creation
```bash
# Create JAR without manifest
jar cfe AutoKeyPresser.jar Main -C src .

# Test JAR file
java -jar AutoKeyPresser.jar
```

### Step 4: Create Distribution Package

#### For Windows Users (Recommended)
```bash
# Create distribution folder
mkdir -p dist/windows

# Copy JAR file
cp AutoKeyPresser.jar dist/windows/

# Create Windows batch launcher
cat > dist/windows/AutoKeyPresser.bat << 'EOF'
@echo off
cd /d "%~dp0"
if exist "jre\bin\java.exe" (
    "jre\bin\java.exe" -jar AutoKeyPresser.jar
) else (
    java -jar AutoKeyPresser.jar
)
if errorlevel 1 (
    echo Java is required to run this application.
    echo Please install Java from: https://java.com/download
    pause
)
EOF

# Create PowerShell launcher
cat > dist/windows/AutoKeyPresser.ps1 << 'EOF'
$ErrorActionPreference = "Stop"
try {
    if (Test-Path "jre\bin\java.exe") {
        & "jre\bin\java.exe" -jar AutoKeyPresser.jar
    } else {
        & java -jar AutoKeyPresser.jar
    }
} catch {
    Write-Host "Java is required to run this application." -ForegroundColor Red
    Write-Host "Please install Java from: https://java.com/download" -ForegroundColor Yellow
    Read-Host "Press Enter to exit"
}
EOF

# Create installer script
cat > dist/windows/install.bat << 'EOF'
@echo off
echo Installing Auto Key Presser...
mkdir "%APPDATA%\AutoKeyPresser" 2>nul
copy AutoKeyPresser.jar "%APPDATA%\AutoKeyPresser\"
copy AutoKeyPresser.bat "%APPDATA%\AutoKeyPresser\"
echo Installation complete!
echo You can now run the application from: %APPDATA%\AutoKeyPresser\AutoKeyPresser.bat
pause
EOF
```

#### For macOS Users
```bash
# Create distribution folder
mkdir -p dist/macos

# Copy JAR file
cp AutoKeyPresser.jar dist/macos/

# Create macOS launcher script
cat > dist/macos/AutoKeyPresser.command << 'EOF'
#!/bin/bash
cd "$(dirname "$0")"
if [ -x "jre/bin/java" ]; then
    ./jre/bin/java -jar AutoKeyPresser.jar
else
    java -jar AutoKeyPresser.jar
fi
EOF

# Make executable
chmod +x dist/macos/AutoKeyPresser.command

# Create application bundle structure
mkdir -p "dist/macos/Auto Key Presser.app/Contents/MacOS"
mkdir -p "dist/macos/Auto Key Presser.app/Contents/Resources"

# Create Info.plist
cat > "dist/macos/Auto Key Presser.app/Contents/Info.plist" << 'EOF'
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE plist PUBLIC "-//Apple//DTD PLIST 1.0//EN" "http://www.apple.com/DTDs/PropertyList-1.0.dtd">
<plist version="1.0">
<dict>
    <key>CFBundleExecutable</key>
    <string>AutoKeyPresser</string>
    <key>CFBundleIdentifier</key>
    <string>com.autokeyPresser.app</string>
    <key>CFBundleName</key>
    <string>Auto Key Presser</string>
    <key>CFBundleVersion</key>
    <string>1.0</string>
    <key>CFBundlePackageType</key>
    <string>APPL</string>
</dict>
</plist>
EOF

# Copy launcher to app bundle
cp dist/macos/AutoKeyPresser.command "dist/macos/Auto Key Presser.app/Contents/MacOS/AutoKeyPresser"
cp AutoKeyPresser.jar "dist/macos/Auto Key Presser.app/Contents/Resources/"
```

#### For Linux Users
```bash
# Create distribution folder
mkdir -p dist/linux

# Copy JAR file
cp AutoKeyPresser.jar dist/linux/

# Create Linux launcher script
cat > dist/linux/AutoKeyPresser.sh << 'EOF'
#!/bin/bash
cd "$(dirname "$0")"
if [ -x "jre/bin/java" ]; then
    ./jre/bin/java -jar AutoKeyPresser.jar
else
    java -jar AutoKeyPresser.jar
fi
EOF

# Make executable
chmod +x dist/linux/AutoKeyPresser.sh

# Create desktop entry
cat > dist/linux/AutoKeyPresser.desktop << 'EOF'
[Desktop Entry]
Version=1.0
Type=Application
Name=Auto Key Presser
Comment=Automated keyboard and mouse actions
Exec=/path/to/AutoKeyPresser.sh
Icon=/path/to/icon.png
Terminal=false
StartupWMClass=AutoKeyPresser
Categories=Utility;Development;
EOF
```

### Step 5: Bundle Java Runtime (Optional)

#### Download OpenJDK
```bash
# Create jre folder
mkdir jre

# Download OpenJDK for your platform
# Windows: https://adoptium.net/temurin/releases/
# Extract to jre/ folder

# Test bundled runtime
./jre/bin/java -jar AutoKeyPresser.jar
```

#### Bundle Size Optimization
```bash
# Use jlink to create minimal runtime (Java 9+)
jlink --module-path "$JAVA_HOME/jmods" \
      --add-modules java.base,java.desktop \
      --output jre-minimal \
      --compress=2 \
      --no-header-files \
      --no-man-pages

# Copy minimal runtime
cp -r jre-minimal dist/windows/jre/
```

## 📦 Final Distribution Packages

### Package Structure
```
dist/
├── windows/
│   ├── AutoKeyPresser.jar
│   ├── AutoKeyPresser.bat
│   ├── AutoKeyPresser.ps1
│   ├── install.bat
│   ├── jre/ (optional)
│   └── README.txt
├── macos/
│   ├── AutoKeyPresser.jar
│   ├── AutoKeyPresser.command
│   ├── Auto Key Presser.app/
│   └── README.txt
└── linux/
    ├── AutoKeyPresser.jar
    ├── AutoKeyPresser.sh
    ├── AutoKeyPresser.desktop
    ├── jre/ (optional)
    └── README.txt
```

### Create Installation Instructions

#### README.txt for Windows
```txt
Auto Key Presser - Installation Instructions

QUICK START:
1. Double-click AutoKeyPresser.bat to run
2. If Java error occurs, install Java from: https://java.com/download

INSTALLATION:
1. Run install.bat to install to your system
2. The program will be available in your Start Menu

REQUIREMENTS:
- Windows 7 or higher
- Java 8 or higher (will prompt to install if missing)

SUPPORT:
- GitHub: https://github.com/kiddogreed/AutoKeyPresser
- Issues: Report bugs on GitHub Issues page
```

### Create Zip Packages
```bash
# Create distribution archives
cd dist
zip -r AutoKeyPresser-Windows.zip windows/
zip -r AutoKeyPresser-macOS.zip macos/
zip -r AutoKeyPresser-Linux.zip linux/

# Create universal package
zip -r AutoKeyPresser-Universal.zip ../AutoKeyPresser.jar ../README.md
```

## 🚀 Deployment Options

### Option 1: GitHub Releases
1. Create releases on GitHub
2. Upload zip files as assets
3. Users download and extract

### Option 2: Direct Download
1. Host files on your website
2. Provide download links
3. Include installation instructions

### Option 3: Package Managers

#### Windows (Chocolatey)
Create `AutoKeyPresser.nuspec`:
```xml
<?xml version="1.0"?>
<package xmlns="http://schemas.microsoft.com/packaging/2010/07/nuspec.xsd">
  <metadata>
    <id>autokeyPresser</id>
    <version>1.0.0</version>
    <title>Auto Key Presser</title>
    <authors>Your Name</authors>
    <description>Automated keyboard and mouse actions</description>
    <projectUrl>https://github.com/kiddogreed/AutoKeyPresser</projectUrl>
    <tags>automation keyboard mouse</tags>
  </metadata>
</package>
```

#### macOS (Homebrew)
Create formula for Homebrew Cask

#### Linux (Snap)
Create snapcraft.yaml for Snap package

## ✅ Testing Your Distribution

### Pre-release Checklist
- [ ] Application compiles without errors
- [ ] JAR file runs correctly
- [ ] All platform launchers work
- [ ] Bundled Java runtime functions (if included)
- [ ] Installation scripts complete successfully
- [ ] Documentation is accurate and complete
- [ ] All features work as expected
- [ ] Error handling works properly

### Test on Different Systems
- [ ] Windows 10/11
- [ ] macOS (latest version)
- [ ] Ubuntu Linux
- [ ] Systems without Java pre-installed
- [ ] Systems with different Java versions

This comprehensive guide will help you distribute Auto Key Presser to any device without requiring users to install Java or development tools manually.