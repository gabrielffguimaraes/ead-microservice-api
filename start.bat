@echo off
setlocal enabledelayedexpansion

REM Define an array of port numbers to be killed
set "PORTS=8888 8080 8081 8082 8761 8083 8084 "

REM Loop through each port number in the array
for %%P in (%PORTS%) do (
    set "PORT=%%P"

    REM Find and kill the process using the current port number
    for /f "tokens=5" %%a in ('netstat -aon ^| findstr LISTENING ^| findstr :!PORT!') do (
        set "PID=%%a"
    )

    if not "!PID!"=="" (
        echo Killing process with PID !PID! using port !PORT!...
        taskkill /F /PID !PID!
        echo Process killed.
    ) else (
        echo No process using port !PORT! found.
    )
)

bash ./start.sh