javaw -version
if %ERRORLEVEL% neq 0 goto ProcessError
start javaw -jar kafka-client-1.1.1.jar
exit /b 0

:ProcessError
java -jar kafka-client-1.1.1.jar