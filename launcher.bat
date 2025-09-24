@echo off
echo Auto Key Presser Launcher
echo ========================
echo.
echo Choose an option:
echo 1. Launch GUI Interface
echo 2. Use Command Line Interface
echo 3. View Help
echo.
set /p choice="Enter your choice (1-3): "

if "%choice%"=="1" goto gui
if "%choice%"=="2" goto cli
if "%choice%"=="3" goto help
goto invalid

:gui
echo Launching GUI interface...
java -cp src Main --gui
goto end

:cli
echo.
echo Command Line Interface
echo Enter parameters: characters interval [duration]
echo Example: hello 2s 10s
echo.
set /p chars="Characters: "
set /p interval="Interval: "
set /p duration="Duration (optional, press Enter for infinite): "

if "%duration%"=="" (
    java -cp src Main "%chars%" "%interval%"
) else (
    java -cp src Main "%chars%" "%interval%" "%duration%"
)
goto end

:help
java -cp src Main
goto end

:invalid
echo Invalid choice. Please run the script again.
goto end

:end
pause
