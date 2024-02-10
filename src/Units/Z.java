package Units;

public class Z implements Unit {
	public final double re;
	public final double im;

	public Z(double re, double im) {
		this.re = re;
		this.im = im;
	}

	@Override
	public Unit zeroInstance() {
		return new Z(0, 0);
	}

	@Override
	public Unit plus(Unit other) throws Exception {
		if (other instanceof Z z) {
			return new Z(this.re + z.re, this.im + z.im);
		} else {
			throw new Exception("incompatible types");
		}
	}

	@Override
	public Unit minus(Unit other) throws Exception {
		if (other instanceof Z z) {
			return new Z(this.re - z.re, this.im - z.im);
		} else {
			throw new Exception("incompatible types");
		}
	}

	@Override
	public Unit times(Unit other) throws Exception {
		if (other instanceof Z z) {
			return new Z(this.re * z.re - this.im * z.im, this.re * z.im + this.im * z.re);
		} else {
			throw new Exception("incompatible types");
		}
	}

	@Override
	public Unit times(double k) {
		return new Z(this.re * k, this.im * k);
	}

	@Override
	public Unit divide(Unit other) throws Exception {
		if (other instanceof Z z) {
			Unit coupled = new Z(z.re, -z.im);
			double k = ((Z) z.times(coupled)).re;
			return this.times(coupled).divide(k);
		} else {
			throw new Exception("incompatible types");
		}
	}

	@Override
	public Unit divide(double k) {
		return this.times(1 / k);
	}
}
