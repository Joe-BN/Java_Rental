@echo off
setlocal enabledelayedexpansion
cd /d "%~dp0"

if not exist out (
    mkdir out
)

echo.
echo ---------------------------------------------------------------
echo                 Compiling Java sources .....
echo ---------------------------------------------------------------
echo.

javac -cp "lib/*" -d out *.java models\*.java

if %errorlevel% neq 0 (
    echo.
    echo [❌] Compilation failed. Please fix errors above.
    pause
    exit /b
)

echo.
echo Running .....
echo.
echo.

java --enable-native-access=ALL-UNNAMED -cp "out;lib/*" com.Main

echo.
echo [✔] Program exited.
pause
