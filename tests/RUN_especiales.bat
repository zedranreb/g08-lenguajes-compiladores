echo "Compiling especiales.txt"
java "-jar" "target\lyc-compiler-1.0.0.jar" "target\input\especiales.txt"
COPY "target\output\final.asm" "target\asm\final.asm"