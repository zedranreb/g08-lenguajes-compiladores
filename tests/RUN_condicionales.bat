echo "Compiling condicionales.txt"
java "-jar" "target\lyc-compiler-1.0.0.jar" "target\input\condicionales.txt"
COPY "target\output\final.asm" "target\asm\final.asm"