package units;

public class R implements Unit {
	public final double r;

	public R(double r) {
		this.r = r;
	}

	@Override
	public Unit zeroInstance() {
		return new R(0);
	}

	@Override
	public boolean equals(Unit other) {
		if (other instanceof R r) {
			return this.r == r.r;
		}
		return false;
	}

	@Override
	public Unit plus(Unit other) throws Exception {
		if (other instanceof R r) {
			return new R(this.r + r.r);
		} else {
			throw new Exception("incompatible types");
		}
	}

	@Override
	public Unit minus(Unit other) throws Exception {
		if (other instanceof R r) {
			return new R(this.r - r.r);
		} else {
			throw new Exception("incompatible types");
		}
	}

	@Override
	public Unit times(Unit other) throws Exception {
		if (other instanceof R r) {
			return new R(this.r * r.r);
		} else {
			throw new Exception("incompatible types");
		}
	}

	@Override
	public Unit times(double k) {
		return new R(this.r * k);
	}

	@Override
	public Unit divide(Unit other) throws Exception {
		if (other instanceof R r) {
			return new R(this.r / r.r);
		} else {
			throw new Exception("incompatible types");
		}
	}

	@Override
	public Unit divide(double k) {
		return new R(this.r / k);
	}
}
