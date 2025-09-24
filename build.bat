@echo off
echo Building Auto Key Presser...
echo ============================

echo Compiling Java file...
javac src\Main.java

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ✅ Build successful!
    echo.
    echo Available commands:
    echo   java -cp src Main --gui           ^(Launch GUI^)
    echo   java -cp src Main "text" "2s"     ^(Command line^)
    echo   launcher.bat                     ^(Interactive launcher^)
) else (
    echo.
    echo ❌ Build failed!
    echo Please check the error messages above.
)

echo.
pause
