package Units;

public interface Unit {
	Unit zeroInstance();

	Unit plus(Unit other) throws Exception;

	Unit minus(Unit other) throws Exception;

	Unit times(Unit other) throws Exception;

	Unit times(double k);

	Unit divide(Unit other) throws Exception;

	Unit divide(double k);
}
