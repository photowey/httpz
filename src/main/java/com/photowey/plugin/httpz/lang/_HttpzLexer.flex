package com.photowey.plugin.httpz.lang;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static com.photowey.plugin.httpz.lang.HttpzTypes.*;

%%

%{
  public _HttpzLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _HttpzLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s+

WHITE_SPACE=[ \t\n\x0B\f\r]+
URL=(https?):"//"[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]
QUOTED_STRING=('([^'\\]|\\.)*'|\"([^\"\\]|\\.)*\")
BASIC_STRING=[0-9a-zA_Z]*
COMMENT="//".*|"/"\*\*.*\*"/"
OPTION=--header|-H|--data|-d|--query|-q|--user-agent|-A|--env|-e|--profile|-p|--config|-c|--file|-f
METHOD=GET|POST|PUT|PATCH|DELETE
NEWLINE=([ \\]*\\)

%%
<YYINITIAL> {
  {WHITE_SPACE}        { return WHITE_SPACE; }

  "HTTPZ"              { return HTTPZ; }

  {WHITE_SPACE}        { return WHITE_SPACE; }
  {URL}                { return URL; }
  {QUOTED_STRING}      { return QUOTED_STRING; }
  {BASIC_STRING}       { return BASIC_STRING; }
  {COMMENT}            { return COMMENT; }
  {OPTION}             { return OPTION; }
  {METHOD}             { return METHOD; }
  {NEWLINE}            { return NEWLINE; }

}

[^] { return BAD_CHARACTER; }
