public class Z {
	public final double re;
	public final double im;

	public Z(double re, double im) {
		this.re = re;
		this.im = im;
	}

	public Z plus(Z other) {
		return new Z(this.re + other.re, this.im + other.im);
	}

	public Z minus(Z other) {
		return new Z(this.re - other.re, this.im - other.im);
	}

	public Z times(Z other) {
		return new Z(this.re * other.re - this.im * other.im, this.re * other.im + this.im * other.re);
	}

	public Z times(double k) {
		return new Z(this.re * k, this.im * k);
	}

	public Z divide(Z other) {
		Z coupled = new Z(other.re, -other.im);
		double k = other.times(coupled).re;
		return this.times(coupled).divide(k);
	}
	public Z divide(double k)
	{
		return this.times(1/k);
	}
}
