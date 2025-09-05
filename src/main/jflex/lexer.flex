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
            throw new InvalidIntegerException("LEX-ERR: La constante entera '" + texto + "' excede el tamaño de 16 bits.");
        }
    } catch (NumberFormatException e) {
        throw new InvalidIntegerException("LEX-ERR: '" + texto + "' no es un numero entero valido.");
    }
  }

  private void validarFloat(String texto) throws InvalidFloatException{
  	try {
        Float.parseFloat(texto);
    } catch (NumberFormatException e) {
        throw new InvalidFloatException("LEX-ERR: '" + texto + "' es un numero float valido");
    }
  }
  
  private void validarString(String texto) throws InvalidLengthException {
	if (texto.length() - 2> 50) {
		throw new InvalidLengthException("LEX-ERR: La constante '" 
					+ texto 
					+ "' (" 
					+ texto.length()
					+ ") excede el tamaño maximo de 50 caracteres.");
	}
  }
  
  private void validarID(String texto) throws InvalidLengthException {
	if (texto.length() > 50) {
		throw new InvalidLengthException("LEX-ERR: La variable '" 
					+ texto 
					+ "' (" 
					+ texto.length()
					+ ") excede el tamaño maximo de 50 caracteres.");
	}
  }
  private void agregarAlaTabla(){
  	// 1ra opcion
  	//tabla creada
  	//crear la tabla aca
  	//rellenarla aca
  	//envia
  	//2da opcion
  	//relleno
  	
  }
  
%}
%state COMENTARIO

/* BASICO */
LETRA 					= [a-zA-Z]
NUMERO 					= [0-9]
NATURAL 				= [1-9]

EOL 					= \r|\n|\r\n
TAB 					= [ \t\f]
IGNORAR					= {EOL} | {TAB}

/*CARACTERES*/
OPEN_BRACKET			= "("
CLOSE_BRACKET			= ")"
CA_CA					= "["
CA_CC					= "]"
CA_LA					= "{"
CA_LC					= "}"
CA_CO					= ","
CA_DP					= ":"
CA_PY					= ";"
CA_COM					= "\""

/* ARITMETICA */
ASSIG					= "=" | ":="
PLUS 					= "+"
SUB						= "-"
MULT					= "*"
DIV						= "/"

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
CONST_FLO             	= ({NUMERO})+"."({NUMERO})* | "."({NUMERO})+
CONST_INT               = {NATURAL}{NUMERO}*|[0]
CONST_STR               = {CA_COM}[^\"]*{CA_COM}
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
  /*CARACTERES*/
  {OPEN_BRACKET}							{ return symbol(ParserSym.OPEN_BRACKET); }
  {CLOSE_BRACKET}							{ return symbol(ParserSym.CLOSE_BRACKET); }
  {CA_CA}					                { return symbol(ParserSym.CA_CA); }
  {CA_CC}					                { return symbol(ParserSym.CA_CC); }
  {CA_LA}					                { return symbol(ParserSym.CA_LA); }
  {CA_LC}					                { return symbol(ParserSym.CA_LC); }
  {CA_CO}					                { return symbol(ParserSym.CA_CO); }
  {CA_DP}					                { return symbol(ParserSym.CA_DP); }
  {CA_PY}					                { return symbol(ParserSym.CA_PY); }
  {CA_COM}				                    { return symbol(ParserSym.CA_COM); }
  
  /* ARITMETICA */
  {ASSIG}									 { return symbol(ParserSym.ASSIG); }
  {PLUS} 					                 { return symbol(ParserSym.PLUS); }
  {SUB}					                	 { return symbol(ParserSym.SUB); }
  {MULT}					                 { return symbol(ParserSym.MULT); }
  {DIV}					                 	 { return symbol(ParserSym.DIV); }
                                             
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
  /* PALABRAS RESERVADAS */
  {INI}										 { return symbol(ParserSym.INI); }
  {IF}										 { return symbol(ParserSym.IF); }
  {ELSE}					                 { return symbol(ParserSym.ELSE); }
  {WRITE}					                 { return symbol(ParserSym.WRITE); }
  {READ}					                 { return symbol(ParserSym.READ); }
  {WHILE}					                 { return symbol(ParserSym.WHILE); }
  {TAM}						                 { return symbol(ParserSym.TAM); }
  {CON}						                 { return symbol(ParserSym.CON); }
  /* PALABRAS RESERVADAS */
  {INT}										 { return symbol(ParserSym.INT); }
  {FLOAT}								     { return symbol(ParserSym.FLOAT); }
  {STRING}									 { return symbol(ParserSym.STRING); }
  
  /* CONSTANTES NUMERICAS */
  {CONST_FLO}             					 { validarFloat(yytext()); return symbol(ParserSym.CONST_FLO, yytext()); }
  {CONST_INT}               				 { validarEntero(yytext()); return symbol(ParserSym.INTEGER_CONSTANT, yytext()); }
  {CONST_STR}               				 { validarString(yytext()); return symbol(ParserSym.CONST_STR, yytext()); }
  {ID}                     					 { validarID(yytext()); return symbol(ParserSym.IDENTIFIER, yytext()); }
                                
  {IGNORAR} 						 		 { }
  {COMEN_INI}				                 { yybegin(COMENTARIO); }
}

<COMENTARIO> {  
  {COMEN_FIN}      {yybegin(YYINITIAL);}
  .                 {}
  {EOL}             {}
}

/* error fallback */
[^]                              { throw new UnknownCharacterException(yytext()); }