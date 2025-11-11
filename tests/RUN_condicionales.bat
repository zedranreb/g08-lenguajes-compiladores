echo "Compiling condicionales.txt"
COPY /Y "tests\archivosTest\condicionales.txt" "src\main\resources\input\test.txt"
call mvnw.cmd clean install
java "-jar" "target\lyc-compiler-1.0.0.jar" "target\input\test.txt"
COPY "target\output\final.asm" "target\asm\final.asm"