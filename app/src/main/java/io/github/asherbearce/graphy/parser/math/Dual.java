package io.github.asherbearce.graphy.parser.math;

public class Dual implements NumberValue<Dual> {
  private final double real;
  private final double dual;

  public Dual(double real, double dual){
    this.real = real;
    this.dual = dual;
  }

  @Override
  public Dual add(Dual rhs) {
    return new Dual(real + rhs.real, dual + rhs.dual);
  }

  @Override
  public Dual sub(Dual rhs) {
    return new Dual(real - rhs.real, dual - rhs.dual);
  }

  @Override
  public Dual mul(Dual rhs) {
    return new Dual(real * rhs.real, real * rhs.dual + dual * rhs.real);
  }

  @Override
  public Dual div(Dual rhs) {
    return new Dual(real / rhs.real, (dual * rhs.real - real * rhs.dual) / (rhs.real * rhs.real));
  }

  @Override
  public Dual pow(Dual rhs) {
    double factor = Math.pow(real, rhs.real);
    return new Dual(factor, factor * rhs.dual * Math.log(real) + dual * rhs.real / real);
  }

  @Override
  public Dual conjugate() {
    return this;
  }

  @Override
  public Dual negative() {
    return new Dual(-real, -dual);
  }

  @Override
  public Real dual() {
    return new Real(dual);
  }

  @Override
  public Real real() {
    return new Real(real);
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

    if (clazz == Real.class || clazz == Dual.class){
      result = Dual.class;
    }
    else{
      result = ComplexDual.class;
    }

    return result;
  }

  @Override
  public String toString(){
    return String.format("%f + E%f", real().getValue(), dual().getValue());
  }

  public static Dual from(Real real){
    return new Dual(real.getValue(), 0);
  }

  public static Dual from(Dual value){
    return value;
  }
}
