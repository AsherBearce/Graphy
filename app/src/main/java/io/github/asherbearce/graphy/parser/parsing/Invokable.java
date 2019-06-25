package io.github.asherbearce.graphy.parser.parsing;

import io.github.asherbearce.graphy.parser.exception.ParseException;
import io.github.asherbearce.graphy.parser.math.NumberValue;

public interface Invokable {
  NumberValue invoke(NumberValue... args) throws ParseException;
}
