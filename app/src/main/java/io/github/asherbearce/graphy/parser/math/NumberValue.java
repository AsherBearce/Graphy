package io.github.asherbearce.graphy.parser.math;

public interface NumberValue<T extends NumberValue> {
  T add(T rhs);
  T sub(T rhs);
  T mul(T rhs);
  T div(T rhs);
  T pow(T rhs);
  T conjugate();
  T negative();
  Real dual();
  Real real();
  Real imaginary();
  Real dualImaginary();
  Class<? extends NumberValue> enclosingType(Class<? extends NumberValue> clazz);
}
