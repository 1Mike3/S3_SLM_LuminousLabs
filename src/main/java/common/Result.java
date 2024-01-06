package common;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Represents a result that can either contain a successful value of type 'T' or an error of type 'E'.
 * This class is designed to handle both successful and error scenarios in a functional way.
 *
 * @param <T> The type of the successful value.
 * @param <E> The type of the error value.
 */
public record Result<T, E>(T value, E error) {

    /**
     * Creates a new 'Result' instance representing a successful result with the given value.
     *
     * @param value The successful value to wrap.
     * @param <T>   The type of the successful value.
     * @param <E>   The type of the error value.
     * @return A 'Result' instance representing a successful result.
     */
    public static <T, E> Result<T, E> ok(T value) {
        return new Result<>(value, null);
    }

    /**
     * Creates a new 'Result' instance representing an error result with the given error value.
     *
     * @param error The error value to wrap.
     * @param <T>   The type of the successful value.
     * @param <E>   The type of the error value.
     * @return A 'Result' instance representing an error result.
     * @throws IllegalArgumentException if the 'error' parameter is null.
     */
    public static <T, E> Result<T, E> err(E error) {
        if (error == null) {
            throw new IllegalArgumentException("Error object must be provided for err.");
        }
        return new Result<>(null, error);
    }

    /**
     * Checks if this 'Result' represents a successful result.
     *
     * @return true if it's a successful result, false otherwise.
     */
    public boolean isOk() {
        return value() != null;
    }

    /**
     * Checks if this 'Result' represents an error result.
     *
     * @return true if it's an error result, false otherwise.
     */
    public boolean isErr() {
        return error() != null;
    }

    /**
     * Retrieves the successful value from this 'Result' if it's a successful result.
     *
     * @return The successful value.
     * @throws IllegalStateException if this 'Result' represents an error result.
     */
    public T unwrap() {
        if (isOk()) {
            return value();
        }
        throw new IllegalStateException("Cannot unwrap an err result.");
    }

    /**
     * Retrieves the error value from this 'Result' if it's an error result.
     *
     * @return The error value.
     * @throws IllegalStateException if this 'Result' represents a successful result.
     */
    public E getErr() {
        if (isErr()) {
            return error();
        }
        throw new IllegalStateException("No error object available for an ok result.");
    }

    /**
     * Maps the successful value of this 'Result' to a new 'Result' instance with a different successful value type.
     *
     * @param mapper The function to apply to the successful value.
     * @param <U>    The type of the new successful value.
     * @return A new 'Result' instance with the mapped successful value, or an error result if this 'Result' represents an error.
     * @throws IllegalArgumentException if the 'mapper' parameter is null.
     */
    public <U> Result<U, E> map(Function<T, U> mapper) {
        if(mapper == null){
            throw new IllegalArgumentException("A mapper must be provided for map.");
        }
        if (isOk()) {
            return Result.ok(mapper.apply(value()));
        }
        return Result.err(error());
    }

    /**
     * Performs an action on the successful value of this 'Result' if it represents a successful result.
     *
     * @param consumer The action to perform on the successful value.
     * @return This 'Result' instance.
     */
    public Result<T,E> ifOk(Consumer<T> consumer) {
        if (consumer != null && isOk()) {
            consumer.accept(value());
        }
        return this;
    }

    /**
     * Performs an action on the error value of this 'Result' if it represents an error result.
     *
     * @param consumer The action to perform on the error value.
     * @return This 'Result' instance.
     */
    public Result<T, E> ifErr(Consumer<E> consumer) {
        if (consumer != null && isErr()) {
            consumer.accept(error());
        }
        return this;
    }
}
