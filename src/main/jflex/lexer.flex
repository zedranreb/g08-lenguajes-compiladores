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
CA_PC					= ";"
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
  /* identifiers */
  {Identifier}                             { return symbol(ParserSym.IDENTIFIER, yytext()); }
  /* Constants */
  {IntegerConstant}                        { return symbol(ParserSym.INTEGER_CONSTANT, yytext()); }

  /* operators */
  {Plus}                                    { return symbol(ParserSym.PLUS); }
  {Sub}                                     { return symbol(ParserSym.SUB); }
  {Mult}                                    { return symbol(ParserSym.MULT); }
  {Div}                                     { return symbol(ParserSym.DIV); }
  {Assig}                                   { return symbol(ParserSym.ASSIG); }
  {OpenBracket}                             { return symbol(ParserSym.OPEN_BRACKET); }
  {CloseBracket}                            { return symbol(ParserSym.CLOSE_BRACKET); }

  /* whitespace */
  {WhiteSpace}                   { /* ignore */ }
}


/* error fallback */
[^]                              { throw new UnknownCharacterException(yytext()); }
