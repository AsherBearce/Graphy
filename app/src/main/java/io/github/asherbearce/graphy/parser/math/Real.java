package io.github.asherbearce.graphy.parser.math;

public class Real implements NumberValue<Real> {

  private final double value;

  public Real(double value){
    this.value = value;
  }

  public double getValue(){
    return value;
  }

  @Override
  public Real add(Real rhs) {
    return new Real(value + rhs.value);
  }

  @Override
  public Real sub(Real rhs) {
    return new Real(value - rhs.value);
  }

  @Override
  public Real mul(Real rhs) {
    return new Real(value * rhs.value);
  }

  @Override
  public Real div(Real rhs) {
    return new Real(value / rhs.value);
  }

  @Override
  public Real pow(Real rhs) {
    return new Real(Math.pow(value, rhs.value));
  }

  @Override
  public Real conjugate() {
    return this;
  }

  @Override
  public Real negative() {
    return new Real(-value);
  }

  @Override
  public Real dual() {
    return new Real(0);
  }

  @Override
  public Real real() {
    return this;
  }

  @Override
  public Real imaginary() {
    return new Real(0);
  }

  @Override
  public Real dualImaginary() {
    return new Real(0);
  }

  @Override
  public Class<? extends NumberValue> enclosingType(Class<? extends NumberValue> clazz) {
    Class<? extends NumberValue> result;

    if (clazz == Real.class) {
      result = Real.class;
    } else if (clazz == Complex.class) {
      result = Complex.class;
    }
    else if (clazz == Dual.class){
      result = Dual.class;
    }
    else{
      result = ComplexDual.class;
    }

    return result;
  }

  @Override
  public String toString(){
    return String.format("%f", value);
  }

  public static Real from(Real value) {
    return value;
  }
}
