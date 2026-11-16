@echo off
setlocal
set "MAVEN_VERSION=3.9.11"
set "MAVEN_HOME=%USERPROFILE%\.maven\apache-maven-%MAVEN_VERSION%"

if exist "%MAVEN_HOME%\bin\mvn.cmd" goto runmaven

echo Downloading Maven. Please keep the internet connected.
powershell -NoProfile -ExecutionPolicy Bypass -Command "$ErrorActionPreference='Stop'; $d=Join-Path $env:USERPROFILE '.maven'; New-Item -ItemType Directory -Force -Path $d | Out-Null; $z=Join-Path $env:TEMP 'apache-maven.zip'; Invoke-WebRequest -Uri 'https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.11/apache-maven-3.9.11-bin.zip' -OutFile $z; Expand-Archive -LiteralPath $z -DestinationPath $d -Force"

if errorlevel 1 (
    echo Maven download failed. Check your internet connection and try again.
    exit /b 1
)

:runmaven
call "%MAVEN_HOME%\bin\mvn.cmd" %*
endlocal
