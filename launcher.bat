@echo off
title Auto Key Presser Launcher

echo ========================================
echo    Auto Key Presser - Launcher
echo ========================================
echo.

REM Check if Java is available
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo.
    echo Please either:
    echo 1. Install Java from https://java.com/download
    echo 2. Use the standalone AutoKeyPresser.exe instead
    echo.
    pause
    exit /b 1
)

REM Check if compiled class exists
if not exist "src\Main.class" (
    echo Compiling application...
    javac src\Main.java
    if %errorlevel% neq 0 (
        echo ERROR: Compilation failed
        pause
        exit /b 1
    )
    echo Compilation successful!
    echo.
)

REM Launch the application
echo Starting Auto Key Presser...
echo.
echo ========================================
echo    GUI Interface Loading...
echo ========================================
echo.

java -cp src Main

REM Check exit status
if %errorlevel% neq 0 (
    echo.
    echo ERROR: Application encountered an error
    echo Error code: %errorlevel%
    echo.
    pause
)

echo.
echo Application closed.
pause