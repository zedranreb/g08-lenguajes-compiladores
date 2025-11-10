echo "Compiling ciclos.txt"
java "-jar" "target\lyc-compiler-1.0.0.jar" "target\input\ciclos.txt"
COPY "target\output\final.asm" "target\asm\final.asm"