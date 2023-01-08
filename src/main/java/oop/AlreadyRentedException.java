package oop;

public class AlreadyRentedException extends IllegalStateException {
    public AlreadyRentedException(String s) {
        super(s);
    }
}
