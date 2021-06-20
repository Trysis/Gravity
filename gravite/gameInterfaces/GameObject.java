package gameInterfaces;

public interface GameObject {
	public default void update(long n) {
		updateData(n);
	}
	public void updateData(long n);
}
