@echo off
title Multi-Threaded CSV Sales Analytics Processor

echo ============================================================
echo      MULTI-THREADED CSV SALES ANALYTICS PROCESSOR
echo ============================================================
echo.

if not exist "out" mkdir out

javac -d out src\Main.java src\model\SalesRecord.java src\model\FileProcessingResult.java src\config\ProcessorConfig.java src\processor\CsvFileProcessor.java src\report\ReportAggregator.java

if %ERRORLEVEL% neq 0 (
    echo.
    echo [ERROR] Compilation failed. Make sure JDK is installed.
    pause
    exit /b 1
)

echo Compilation successful!
echo.
java -cp out Main

echo.
echo ============================================================
echo Output files: report.txt and processed_sales.csv
echo ============================================================
pause
