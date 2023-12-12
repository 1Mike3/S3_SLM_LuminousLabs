package common;

import java.util.function.Consumer;
import java.util.function.Function;

public record Result<T, E>(T value, E error) {

    public static <T, E> Result<T, E> ok(T value) {
        return new Result<>(value, null);
    }

    public static <T, E> Result<T, E> err(E error) {
        if (error == null) {
            throw new IllegalArgumentException("Error object must be provided for err.");
        }
        return new Result<>(null, error);
    }

    public boolean isOk() {
        return value() != null;
    }

    public boolean isErr() {
        return error() != null;
    }

    public T unwrap() {
        if (isOk()) {
            return value();
        }
        throw new IllegalStateException("Cannot unwrap an err result.");
    }

    public E getErr() {
        if (isErr()) {
            return error();
        }
        throw new IllegalStateException("No error object available for an ok result.");
    }

    public <U> Result<U, E> map(Function<T, U> mapper) {
        if (isOk()) {
            return Result.ok(mapper.apply(value()));
        }
        return Result.err(error());
    }

    public void ifOk(Consumer<T> consumer) {
        if (isOk()) {
            consumer.accept(value());
        }
    }

    public void ifErr(Consumer<E> consumer) {
        if (isErr()) {
            consumer.accept(error());
        }
    }

    public static void main(String[] args) {
        // Example usage:
        Result<Integer, String> result = divide(10, 2);
        result.ifOk(value -> System.out.println("Result: " + value));
        result.ifErr(error -> System.err.println("Error: " + error));
    }

    private static Result<Integer, String> divide(int numerator, int denominator) {
        if (denominator == 0) {
            return Result.err("Cannot divide by zero.");
        }
        return Result.ok(numerator / denominator);
    }

}