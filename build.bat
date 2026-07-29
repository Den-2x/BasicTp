@echo off
setlocal enabledelayedexpansion

if not "%JAVA_HOME%"=="" (
    set "PATH=%JAVA_HOME%\bin;%PATH%"
) else (
    where java >nul 2>&1
    if !errorlevel! neq 0 (
        echo [BasicTp] java not found. set JAVA_HOME or add java to PATH.
        exit /b 1
    )
)

echo [BasicTp] java found.

where mvn >nul 2>&1
if !errorlevel! neq 0 (
    set "MVN_DIR=%USERPROFILE%\.m2\maven\apache-maven-3.9.9\bin"
    if exist "!MVN_DIR!\mvn.cmd" (
        set "PATH=!MVN_DIR!;%PATH%"
    ) else (
        echo [BasicTp] maven not found.
        echo [BasicTp] install maven manually: https://maven.apache.org/download.cgi
        exit /b 1
    )
)

echo [BasicTp] resolving dependencies...
call mvn dependency:resolve -q
if !errorlevel! neq 0 (
    echo [BasicTp] dependency resolution failed.
    exit /b 1
)

echo [BasicTp] building...
call mvn clean package -q
if !errorlevel! neq 0 (
    echo [BasicTp] build failed.
    exit /b 1
)

echo [BasicTp] copying jar...
if not exist build mkdir build
copy /y target\BasicTp.jar build\BasicTp.jar >nul
if !errorlevel! equ 0 (
    echo [BasicTp] done. jar is at build\BasicTp.jar.
) else (
    echo [BasicTp] copy failed.
    exit /b 1
)
