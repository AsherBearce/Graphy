package io.github.asherbearce.graphy.parser.parsing;

import io.github.asherbearce.graphy.parser.math.Real;
import io.github.asherbearce.graphy.parser.token.IdentifierToken;
import io.github.asherbearce.graphy.parser.token.NumberToken;
import io.github.asherbearce.graphy.parser.token.OperatorTokens;
import io.github.asherbearce.graphy.parser.token.Token;
import io.github.asherbearce.graphy.parser.token.TokenTypes;
import java.util.LinkedList;

//TODO add identifiers and complex numbers to the tokenizing process

public final class Tokenizer {
  private final String toTokenize;
  private int currentIndex;

  public Tokenizer(String rawString){
    toTokenize = rawString;
  }

  private char nextChar(){
    return toTokenize.charAt(
        (currentIndex + 1 < toTokenize.length())
            ? ++currentIndex : currentIndex++);
  }

  private boolean hasNext(){
    return currentIndex < toTokenize.length();
  }

  public LinkedList<Token> Tokenize(){
    LinkedList<Token> result = new LinkedList<>();
    char currentCharacter = toTokenize.charAt(0);

    while(hasNext()){
      switch (currentCharacter){
        case ' ':
        case '\t':
        case '\n':
        case '\r': {
          currentCharacter = nextChar();
          continue;
        }
        case '+': {
          result.addLast(OperatorTokens.PLUS);
          break;
        }
        case '-': {
          result.addLast(OperatorTokens.MINUS);
          break;
        }
        case '*': {
          result.addLast(OperatorTokens.MULTIPLY);
          break;
        }
        case '^': {
          result.addLast(OperatorTokens.EXPONENT);
          break;
        }
        case '/': {
          result.addLast(OperatorTokens.DIVIDE);
          break;
        }
        case '=':{
          result.addLast(TokenTypes.EQUALS);
          break;
        }
        case ',':{
          result.addLast(TokenTypes.COMMA);
          break;
        }
        case '(': {
          result.addLast(TokenTypes.OPEN_PAREN);
          break;
        }
        case ')': {
          result.addLast(TokenTypes.CLOSE_PAREN);
          break;
        }
        case '{': {
          result.addLast(TokenTypes.OPEN_CURLY);
          break;
        }
        case '}': {
          result.addLast(TokenTypes.CLOSE_CURLY);
          break;
        }
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9': {
          int charCount = 0;

          while (hasNext() && Character.isDigit(currentCharacter) || currentCharacter == 'e' ||
              currentCharacter == '.' || currentCharacter == '-'){
            charCount++;
            currentCharacter = nextChar();
          }

          double parsedValue = Double.valueOf(
              toTokenize.substring(currentIndex - charCount, currentIndex));

          result.addLast(new NumberToken(new Real(parsedValue)));
          break;
        }
        default: {
          int charCount = 0;

          while (hasNext() && Character.isAlphabetic(currentCharacter)){
            charCount++;
            currentCharacter = nextChar();
          }

          String identifier = toTokenize.substring(currentIndex - charCount, currentIndex);
          result.addLast(new IdentifierToken(identifier));
          break;
        }
      }

      if (result.getLast().getTokenType() != TokenTypes.NUMBER &&
          result.getLast().getTokenType() != TokenTypes.IDENTIFIER){
        currentCharacter = nextChar();
      }
    }

    result.addLast(TokenTypes.END);

    return result;
  }
}
