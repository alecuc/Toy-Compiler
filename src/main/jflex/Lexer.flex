//imports
package lexer;
import java_cup.runtime.Symbol;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;
import java.lang.*;
import parser.*;

%%

%public
%implements Symbols
%class Lexer
%line
%column
%char
%unicode
%cup

%{
    StringBuffer string = new StringBuffer();
    ComplexSymbolFactory symbolFactory;

    public Lexer(java.io.Reader in, ComplexSymbolFactory sf){
	    this(in);
	    symbolFactory = sf;
    }

  private Symbol symbol(String name, int sym) {
      return symbolFactory.newSymbol(name, sym, new Location(yyline+1,yycolumn+1,(int)yychar), new Location(yyline+1,yycolumn+yylength(),(int)(yychar+yylength())));
  }

  private Symbol symbol(String name, int sym, Object val) {
      Location left = new Location(yyline+1,yycolumn+1,(int)yychar);
      Location right= new Location(yyline+1,yycolumn+yylength(), (int)(yychar+yylength()));
      return symbolFactory.newSymbol(name, sym, left, right,val);
  }

  private Symbol symbol(String name, int sym, Object val,int buflength) {
      Location left = new Location(yyline+1,yycolumn+yylength()-buflength,(int)(yychar+yylength()-buflength));
      Location right= new Location(yyline+1,yycolumn+yylength(), (int)(yychar+yylength()));
      return symbolFactory.newSymbol(name, sym, left, right,val);
  }

  private void error(String message) {
    System.out.println("Error at line "+(yyline+1)+", column "+(yycolumn+1)+" : "+message);
  }
%}

%eofval{
     return symbolFactory.newSymbol("EOF", EOF, new Location(yyline+1,yycolumn+1,(int)yychar), new Location(yyline+1,yycolumn+1,(int)(yychar+1)));
%eofval}

ALPHA=[A-Za-z]
DIGIT=[0-9]
NONZERO_DIGIT=[1-9]
NEWLINE=\r|\n|\r\n
WHITESPACE = {NEWLINE} | [ \t\f]
ID = ({ALPHA}|_)({ALPHA}|{DIGIT}|_)*
INT = (({NONZERO_DIGIT}+{DIGIT}*)|0)
FLOAT = {INT}(\.{DIGIT}+)
STRING_TEXT = [^\"]*
COMMENT_TEXT = ([^*]|\*+[^/])*

%%

<YYINITIAL> {

  "proc"          {return symbol("PROC", PROC); }
  "corp"          {return symbol("CORP", CORP); }
  
//Type
  "int"             { return symbol("INT",INT,  ); }
  "float"           { return symbol("FLOAT",FLOAT, new Integer(FLOAT)); }
  "bool"            { return symbol("BOOL",BOOL, new Integer(BOOL)); }
  "string"          { return symbol("STRING",STRING, new Integer(STRING)); }
  "void"            { return symbol("VOID",VOID, new Integer(VOID)); }

  //Statements
  "while"           {return symbol("WHILE",WHILE);  }
  "do"              {return symbol("DO",DO);        }     
  "od"              {return symbol("OD",OD);        }
  "read"            {return symbol("READ",READ);    }
  "write"           {return symbol("WRITE",WRITE);  }
  "assign"          {return symbol("ASSIGN",ASSIGN); }                  
  "if"              {return symbol("IF",IF); }          
  "then"            {return symbol("THEN",THEN); }      
  "fi"              {return symbol("FI",FI); }          
  "else"            {return symbol("ELSE",ELSE); }          
  "elif"            {return symbol("ELIF",ELIF); }          

  //Separators
  "("               {return symbol("LPAR",LPAR); }                 
  ")"               {return symbol("RPAR",RPAR); }
  ":"               {return symbol("COLON",COLON); }
  ","               {return symbol("COMMA",COMMA); }
  ";"               {return symbol("SEMI",SEMI); }


  //Operators
  ":="              {return symbol("ASSIGN",ASSIGN);    }                         
	"+"               {return symbol("PLUS",PLUS);        }             
	"-"               {return symbol("MINUS",MINUS);      }             
	"*"               {return symbol("TIMES",TIMES);      }             
	"/"               {return symbol("DIV",DIV);          }             
	"="               {return symbol("EQ",EQ);            }             
	"<>"              {return symbol("NE",NE);            }              
	"<"               {return symbol("LT",LT);            }             
	"<="              {return symbol("LE",LE);            }              
	">"               {return symbol("GT",GT);            }             
	">="              {return symbol("GE",GE);            }              
	"&&"              {return symbol("AND",AND);          }
	"||"              {return symbol("OR",OR);            }
	"!"               {return symbol("NOT",NOT);          }             
	"null"            {return symbol("NULL",NULL);        }    
  "true"            {return symbol("TRUE",TRUE);        }                           
  "false"           {return symbol("FALSE",FALSE);      } 
  "->"              {return symbol("RETURN",RETURN);    }

  //Constants
  {INT}           {return symbol("INT_CONST",INT_CONST,new Integer(yytext()));        } 
  {FLOAT}         {return symbol("FLOAT_CONST",FLOAT_CONST,new Float(yytext()));      } 
  {ID}            {return symbol("ID",ID,yytext());                                   }



  \"{STRING_TEXT}\" {
    String stringWithNoQuotes =  yytext().substring(1,yylength()-1);
    return symbol("STRING_CONST",STRING_CONST,stringWithNoQuotes);
  }

  \"{STRING_TEXT} {
    error("Non-closed string at "+ (yycolumn+1) + ":" + (yyline+1));
  }

  \/\*{COMMENT_TEXT}\*\/ { }

  \/\*{COMMENT_TEXT} {
    error("Non-closed comment at "+ (yycolumn+1) + ":" + (yyline+1));
  }


{NEWLINE} { }
{WHITESPACE} { }

[^] { error("Unrecognized symbol " + yytext()); }

}