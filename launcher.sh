#!/bin/bash

echo "========================================"
echo "   Auto Key Presser - Launcher"
echo "========================================"
echo

# Check if Java is available
if ! command -v java &> /dev/null; then
    echo "ERROR: Java is not installed or not in PATH"
    echo
    echo "Please install Java:"
    echo "  Ubuntu/Debian: sudo apt install openjdk-11-jre"
    echo "  macOS: brew install openjdk@11"
    echo "  Or download from: https://java.com/download"
    echo
    read -p "Press Enter to exit..."
    exit 1
fi

# Check Java version
echo "Java version:"
java -version
echo

# Navigate to script directory
cd "$(dirname "$0")"

# Check if compiled class exists
if [ ! -f "src/Main.class" ]; then
    echo "Compiling application..."
    javac src/Main.java
    if [ $? -ne 0 ]; then
        echo "ERROR: Compilation failed"
        read -p "Press Enter to exit..."
        exit 1
    fi
    echo "Compilation successful!"
    echo
fi

# Launch the application
echo "Starting Auto Key Presser..."
echo
echo "========================================"
echo "   GUI Interface Loading..."
echo "========================================"
echo

java -cp src Main

# Check exit status
if [ $? -ne 0 ]; then
    echo
    echo "ERROR: Application encountered an error"
    echo "Error code: $?"
    echo
    read -p "Press Enter to exit..."
fi

echo
echo "Application closed."