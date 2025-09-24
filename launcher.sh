#!/bin/bash

echo "Auto Key Presser Launcher"
echo "========================"
echo ""
echo "Choose an option:"
echo "1. Launch GUI Interface"
echo "2. Use Command Line Interface"
echo "3. View Help"
echo ""
read -p "Enter your choice (1-3): " choice

case $choice in
    1)
        echo "Launching GUI interface..."
        java -cp src Main --gui
        ;;
    2)
        echo ""
        echo "Command Line Interface"
        echo "Enter parameters: characters interval [duration]"
        echo "Example: hello 2s 10s"
        echo ""
        read -p "Characters: " chars
        read -p "Interval: " interval
        read -p "Duration (optional, press Enter for infinite): " duration
        
        if [ -z "$duration" ]; then
            java -cp src Main "$chars" "$interval"
        else
            java -cp src Main "$chars" "$interval" "$duration"
        fi
        ;;
    3)
        java -cp src Main
        ;;
    *)
        echo "Invalid choice. Please run the script again."
        ;;
esac

echo ""
read -p "Press Enter to exit..."
