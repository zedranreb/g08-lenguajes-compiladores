package lyc.compiler;

import java_cup.runtime.Symbol;
import lyc.compiler.ParserSym;
import lyc.compiler.model.*;
import static lyc.compiler.constants.Constants.*;

/* -> Comienzo de las declaraciones <- */
%%

%public
%class Lexer
%unicode
%cup
%line
%column
%throws CompilerException
%eofval{
  return symbol(ParserSym.EOF);
%eofval}


%{
  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
  }
  
   private void validarEntero(String texto) throws InvalidIntegerException {
    try {
        int valor = Integer.parseInt(texto);
        if (valor < Short.MIN_VALUE || valor > Short.MAX_VALUE) {
            throw new InvalidIntegerException("La constante entera '" + texto + "' excede el tamaño de 16 bits.");
        }
    } catch (NumberFormatException e) {
        throw new InvalidIntegerException("No es un numero entero valido.");
    }
  }

  
  private void validarFloat() {String texto} throws InvalidFloatException{
  	try {
        Float.parseFloat(texto);
    } catch (NumberFormatException e) {
        throw new InvalidFloatException("No es un numero float valido");
    }
  }
  
  private void validarString() {String texto} throws  CompilerException{
  	String content = texto.substring(1, texto.length() - 1);
      if (content.length() > 50) {
          throw new CompilerException("La constante de cadena excede el tamaño máximo de 50 caracteres.");
      }
  }
  
%}

/* BASICO */
LETRA 					= [a-zA-Z]
NUMERO 					= [0-9]
NATURAL 				= [1-9]

EOL 					= \r|\n|\r\n
CIN 					= [^\r\n]
TAB 					= [ \t\f]

/*CARACTERES*/
CA_PA					= "("
CA_PC					= ")"
CA_CA					= "["
CA_CC					= "]"
CA_LA					= "{"
CA_LC					= "}"
CA_CO					= ","
CA_DP					= ":"
CA_PY					= ";"
CA_COM					= "\""

/* ARITMETICA */
OP_ASI					= "=" | ":="
OP_SUM 					= "+"
OP_RES					= "-"
OP_MUL					= "*"
OP_DIV					= "/"

/* INCREMENTALES ?*/ 

/* COMPARACION */
CP_MEN					= "<"
CP_MENI					= "<="
CP_MAY					= ">"
CP_MAYI					= ">="
CP_IGUA					= "=="
CP_DIST					= "!="
CP_Y					= "AND" | "and"
CP_O					= "OR" | "or"
CP_NO					= "NOT" | "not"
CP_BITT					= "TRUE" | "true"
CP_BITF					= "FALSE" | "false"

/* CONSTANTES NUMERICAS */
CONST_FLO             	= -?({NUMERO})+"."({NUMERO})*|({NUMERO})*"."({NUMERO})+
CONST_INT               = -?{NATURAL}{NUMERO}*|[0]
CONST_STR               = {CA_COM}[^']*{CA_COM}
ID                      = {LETRA}({LETRA}|{NUMERO})*

/* PALABRAS RESERVADAS - TIPOS DE DATOS */
INT						= "Int" | "INT" | "int"
FLOAT					= "Float"	| "FLOAT" | "float"
STRING					= "String" | "STRING" | "string"

/* PALABRAS RESERVADAS - FLUJO DE PROGRAMA */
INI						= "INIT" | "init"
IF						= "IF"	| "if"
ELSE					= "ELSE" | "else"
WRITE					= "WRITE" | "write"
READ					= "READ" | "read"
WHILE					= "WHILE" | "while"
TAM						= "TRIANGLEAREAMAXIMUM" | "triangleAreaMaximum"
CON						= "CONVDATE" | "convDate"

COMEN_INI				= "#+"
COMEN_FIN				= "+#"
%%
/* -> FIN de las declaraciones <- */

/* keywords */

<YYINITIAL> {                                 
  {EOL} 						 { return symbol(ParserSym.EOF); }
  {CIN} 						 { }
  {TAB} 						 { }
  
  /*CARACTERES*/
  {CA_PA}									{ return symbol(ParserSym.OPEN_BRACKET); }
  {CA_PC}									{ return symbol(ParserSym.CLOSE_BRACKET); }
  {CA_CA}					                { return symbol(ParserSym.CA_CA); }
  {CA_CC}					                { return symbol(ParserSym.CA_CC); }
  {CA_LA}					                { return symbol(ParserSym.CA_LA); }
  {CA_LC}					                { return symbol(ParserSym.CA_LC); }
  {CA_CO}					                { return symbol(ParserSym.CA_CO); }
  {CA_DP}					                { return symbol(ParserSym.CA_DP); }
  {CA_PY}					                { return symbol(ParserSym.CA_PY); }
  {CA_COM}				                    { return symbol(ParserSym.CA_COM); }
  
  /* ARITMETICA */
  {OP_ASI}									 { return symbol(ParserSym.ASSIG); }
  {OP_SUM} 					                 { return symbol(ParserSym.PLUS); }
  {OP_RES}					                 { return symbol(ParserSym.SUB); }
  {OP_MUL}					                 { return symbol(ParserSym.MULT); }
  {OP_DIV}					                 { return symbol(ParserSym.DIV); }
                                             
  {CP_MEN}					                 { return symbol(ParserSym.CP_MEN); }
  {CP_MENI}					                 { return symbol(ParserSym.CP_MENI); }
  {CP_MAY}					                 { return symbol(ParserSym.CP_MAY); }
  {CP_MAYI}					                 { return symbol(ParserSym.CP_MAYI); }
  {CP_IGUA}					                 { return symbol(ParserSym.CP_IGUA); }
  {CP_DIST}					                 { return symbol(ParserSym.CP_DIST); }
  {CP_Y}					                 { return symbol(ParserSym.CP_Y); }
  {CP_O}					                 { return symbol(ParserSym.CP_O); }
  {CP_NO}					                 { return symbol(ParserSym.CP_NO); }
  {CP_BITT}					                 { return symbol(ParserSym.CP_BITT); }
  {CP_BITT}					                 { return symbol(ParserSym.CP_BITT); }
  
  /* CONSTANTES NUMERICAS */
  {CONST_FLO}             					 {validarFloat(yytext()); /* TODO: Implementar tabla de simbolos */ ; return symbol(ParserSym.CONST_FLO, yytext()) }
  {CONST_INT}               				 {validarEntero(yytext()); /* TODO: Implementar tabla de simbolos */ ; return symbol(ParserSym.INTEGER_CONSTANT, yytext()) }
  {CONST_STR}               				 { validarString(yytext()); /* TODO: Implementar tabla de simbolos */ ; return symbol(ParserSym.CONST_STR, yytext()) }
  {ID}                     					 { /* TODO: Implementar tabla de simbolos */ ; return symbol(ParserSym.IDENTIFIER, yytext()) }
  
  /* PALABRAS RESERVADAS */
  {INT}										 { return symbol(ParserSym.INT); }
  {FLOAT}								     { return symbol(ParserSym.FLOAT); }
  {STRING}									 { return symbol(ParserSym.STRING); }
  
  /* PALABRAS RESERVADAS */
  {INI}										 { return symbol(ParserSym.INI); }
  {IF}										 { return symbol(ParserSym.FIN); }
  {ELSE}					                 { return symbol(ParserSym.ELSE); }
  {WRITE}					                 { return symbol(ParserSym.WRITE); }
  {READ}					                 { return symbol(ParserSym.READ); }
  {WHILE}					                 { return symbol(ParserSym.WHILE); }
  {TAM}						                 { return symbol(ParserSym.TAM); }
  {CON}						                 { return symbol(ParserSym.COM); }
                                             
  {COMEN_INI}				                 {yybegin(COMENTARIO);}
}
<COMENTARIO> {  
  {COMEN_FIN}      {yybegin(YYNITIAL);}
  .                 {}
  {EOL}             {}
}

/* error fallback */
[^]                              { throw new UnknownCharacterException(yytext()); }
