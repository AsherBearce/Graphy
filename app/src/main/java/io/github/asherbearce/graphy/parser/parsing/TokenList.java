package io.github.asherbearce.graphy.parser.parsing;

import io.github.asherbearce.graphy.parser.token.Token;
import java.util.LinkedList;

public class TokenList {
  private TokenContainer first;
  private TokenContainer last;
  private int count;

  public int getCount(){
    return count;
  }

  public TokenContainer getLast(){
    return last;
  }

  public TokenContainer getFirst(){
    return first;
  }

  public TokenList(LinkedList<Token> from){
    for (Token t : from){
      addLast(t);
    }
  }

  public LinkedList<Token> tokenLinkedList(){
    TokenContainer currentElement = this.first;
    LinkedList<Token> result = new LinkedList<>();

    while (currentElement != this.last){
      result.addLast(currentElement.value);
      currentElement = currentElement.next;
    }

    result.addLast(currentElement.value);

    return result;
  }

  public void addLast(Token value){
    TokenContainer newNode = new TokenContainer(value);
    count++;
    if (first == null){
      first = newNode;
      last = newNode;
    }
    else{
      last.next = newNode;
      newNode.previous = last;
      last = newNode;
    }
  }

  public static class TokenContainer{
    public Token value;
    public TokenContainer next;
    public TokenContainer previous;

    public TokenContainer(Token value){
      this.value = value;
    }
  }
}
