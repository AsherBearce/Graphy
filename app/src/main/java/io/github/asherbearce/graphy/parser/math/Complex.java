package io.github.asherbearce.graphy.parser.math;

public class Complex implements NumberValue<Complex> {

  private final double real;
  private final double imaginary;

  public Complex(double real, double imaginary){
    this.real = real;
    this.imaginary = imaginary;
  }

  @Override
  public Complex add(Complex rhs) {
    return new Complex(real + rhs.real, imaginary + rhs.imaginary);
  }

  @Override
  public Complex sub(Complex rhs) {
    return new Complex(real - rhs.real, imaginary - rhs.imaginary);
  }

  @Override
  public Complex mul(Complex rhs) {
    return new Complex(
        real * rhs.real - imaginary * rhs.imaginary,
        real * rhs.imaginary + imaginary * rhs.real);
  }

  @Override
  public Complex div(Complex rhs) {
    double denom = rhs.real * rhs.real + rhs.imaginary * rhs.imaginary;
    return new Complex(
        (real * rhs.real + imaginary * rhs.imaginary) / denom,
        (imaginary * rhs.real - real * rhs.imaginary) / denom);
  }

  @Override
  public Complex pow(Complex rhs) {
    double arg = Math.atan2(imaginary, real);
    double lenSquared = real * real + imaginary * imaginary;
    double theta = rhs.real * arg + rhs.imaginary * Math.log(lenSquared) / 2;
    double factor = Math.pow(lenSquared, rhs.real / 2) * Math.exp(-rhs.imaginary * arg);

    return new Complex(factor * Math.cos(theta), factor * Math.sin(theta));
  }

  @Override
  public Complex conjugate() {
    return new Complex(real, -imaginary);
  }

  @Override
  public Complex negative() {
    return new Complex(-real, -imaginary);
  }

  @Override
  public Real dual() {
    return new Real(0);
  }

  @Override
  public Real real() {
    return new Real(real);
  }

  @Override
  public Real imaginary() {
    return new Real(imaginary);
  }

  @Override
  public Real dualImaginary() {
    return new Real(0);
  }

  @Override
  public Class<? extends NumberValue> enclosingType(Class<? extends NumberValue> clazz) {
    Class<? extends NumberValue> result;

    if (clazz == Complex.class || clazz == Real.class){
      result = Complex.class;
    }
    else{
      result = ComplexDual.class;
    }

    return result;
  }

  @Override
  public String toString(){
    return String.format("%f + i%f", real().getValue(), imaginary().getValue());
  }

  public static Complex from(Real real){
    return new Complex(real.getValue(), 0);
  }

  public static Complex from(Complex value){
    return value;
  }
}
