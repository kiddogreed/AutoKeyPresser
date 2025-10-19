#!/bin/bash

echo "========================================"
echo "   Auto Key Presser - Package Creator"
echo "========================================"
echo

# Create JAR file
echo "Step 1: Creating JAR file..."
jar cfe AutoKeyPresser.jar Main -C src .
if [ $? -eq 0 ]; then
    echo "✅ JAR file created: AutoKeyPresser.jar"
else
    echo "❌ Failed to create JAR file"
    exit 1
fi
echo

# Create distribution directory
echo "Step 2: Creating distribution package..."
mkdir -p dist
cp AutoKeyPresser.jar dist/
cp README.md dist/
cp USER_MANUAL.md dist/
cp INSTALLATION.md dist/
cp DISTRIBUTION.md dist/
cp launcher.bat dist/
cp launcher.sh dist/
cp -r web dist/

echo "✅ Distribution package created in 'dist' directory"
echo

# Show package contents
echo "Package contents:"
ls -la dist/
echo

echo "========================================"
echo "   Distribution Ready!"
echo "========================================"
echo
echo "To distribute:"
echo "1. Zip the 'dist' directory"
echo "2. Users can run launcher.bat (Windows) or launcher.sh (Linux/macOS)"
echo "3. Or double-click AutoKeyPresser.jar (requires Java)"
echo "4. Or access web/index.html for web interface"
echo
echo "For standalone executable (no Java required):"
echo "  jpackage --input dist --name AutoKeyPresser --main-jar AutoKeyPresser.jar --main-class Main --type exe"
echo