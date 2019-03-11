package example;

import io.reactivex.Observable;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

// Compare methods do****
public class DeclarativeWithObservable {

    public <T, E> T doImperative(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (entry.getValue() == value) {
                return entry.getKey();
            }
        }
        return null;
    }

    public <T, E> Optional<T> doDeclarative(Map<T, E> map, E value) {
        return map
                .entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), value))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    public <T, E> Observable<T> doRxDeclarative(Map<T, E> map, E value) {
        return Observable
                .fromIterable(map.entrySet())
                .filter(entry -> Objects.equals(entry.getValue(), value))
                .map(Map.Entry::getKey)
                .first(null)
                .toObservable();
    }

    public <T, E> T doRxDeclarativeAndReturnValue(Map<T, E> map, E value) {
        return Observable
                .fromIterable(map.entrySet())
                .filter(entry -> Objects.equals(entry.getValue(), value))
                .map(Map.Entry::getKey)
                .blockingFirst(null);
    }
}
