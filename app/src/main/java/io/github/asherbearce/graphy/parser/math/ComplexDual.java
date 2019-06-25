package io.github.asherbearce.graphy.parser.math;

public class ComplexDual implements NumberValue<ComplexDual> {
  //Quick note, this is viewed as being a + bi where a, b are dual numbers.
  private final double real;
  private final double dual;
  private final double imaginary;
  private final double imaginaryDual;

  public ComplexDual(double real, double dual, double imaginary, double imaginaryDual){
    this.real = real;
    this.dual = dual;
    this.imaginary = imaginary;
    this.imaginaryDual = imaginaryDual;
  }

  @Override
  public ComplexDual add(ComplexDual rhs) {
    return new ComplexDual(real + rhs.real, dual + rhs.dual, imaginary + rhs.imaginary,
        imaginaryDual + rhs.imaginaryDual);
  }

  @Override
  public ComplexDual sub(ComplexDual rhs) {
    return new ComplexDual(real - rhs.real, dual - rhs.dual, imaginary - rhs.imaginary,
        imaginaryDual - rhs.imaginaryDual);
  }

  @Override
  public ComplexDual mul(ComplexDual rhs) {
    Dual alpha = new Dual(real, dual);
    Dual beta = new Dual(imaginary, imaginaryDual);
    Dual gamma = new Dual(rhs.real, rhs.dual);
    Dual delta = new Dual(rhs.imaginary, rhs.imaginaryDual);
    Dual dualReal = alpha.mul(gamma).sub(delta.mul(beta));
    Dual imDual = alpha.mul(delta).add(beta.mul(gamma));
    double realResult = dualReal.real().getValue();
    double dualResult = dualReal.dual().getValue();
    double imResult = imDual.real().getValue();
    double imaginaryDualResult = imDual.dual().getValue();

    return new ComplexDual(realResult, dualResult, imResult, imaginaryDualResult);
  }

  @Override
  public ComplexDual div(ComplexDual rhs) {
    return null;
  }

  @Override
  public ComplexDual pow(ComplexDual rhs) {
    return null;
  }

  //Note: There are four different types of conjugation for this, I'll be choosing complex conjugation
  @Override
  public ComplexDual conjugate() {
    return new ComplexDual(real, dual, -imaginary, -imaginaryDual);
  }

  @Override
  public ComplexDual negative() {
    return null;
  }

  @Override
  public Real dual() {
    return null;
  }

  @Override
  public Real real() {
    return null;
  }

  @Override
  public Real imaginary() {
    return null;
  }

  @Override
  public Real dualImaginary() {
    return null;
  }



  @Override
  public Class<? extends NumberValue> enclosingType(Class<? extends NumberValue> clazz) {
    return ComplexDual.class;
  }
}
