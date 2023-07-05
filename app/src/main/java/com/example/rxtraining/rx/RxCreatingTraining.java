package com.example.rxtraining.rx;

import com.example.rxtraining.exceptions.ExpectedException;
import com.example.rxtraining.exceptions.NotImplementedException;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.internal.operators.observable.ObservableAll;

/**
 * @author Arthur Korchagin (artur.korchagin@simbirsoft.com)
 * @since 13.11.18
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class RxCreatingTraining {

    /* Тренировочные методы */

    /**
     * Эммит одного элемента
     *
     * @param value - Произвольное число
     * @return {@link Observable}, который эммитит только значение {@code value}
     */
    public Observable<Integer> valueToObservable(int value) {
        return Observable.just(value);
    }

    /**
     * Эммит элементов массива в {@link Observable}
     *
     * @param array - Массив произвольных строк
     * @return {@link Observable}, который эммитит по порядку все строки из заданного массива
     */
    public Observable<String> arrayToObservable(String[] array) {
        return Observable.fromArray(array);
    }

    /**
     * Выполнение метода с длительными вычислениями: {@link #expensiveMethod()}. Необходимо, чтобы метод
     * вызывался только при подписке на Observable
     *
     * @return {@link Observable} - который эммитит результат выполнения метода
     * {@link #expensiveMethod()}
     */
    public Observable<Integer> expensiveMethodResult() {
        return Observable.defer(() -> Observable.just(expensiveMethod()));
    }

    /**
     * Возрастающая последовательность, начинающаяся с нуля с первоначальной задержкой и заданным
     * интервалом
     *
     * @return {@link Observable} - который эммитит возрастающую последовательность значений,
     * начиная с 0L, пока не произойдёт отписка.
     * Значения начинают эммититься с задержкой {@code initialDelay} миллисекунд и каждый
     * последующий с интервалом {@code period} миллисекунд.
     * {@code onError} или {@code onComplete} не должны вызваться.
     */
    public Observable<Long> increasingSequenceWithDelays(long initialDelay, long period) {
        return Observable
                // Создаем бесконечную последовательность, начиная с 0L
                .interval(0L, period, TimeUnit.MILLISECONDS)
                // Добавляем начальную задержку
                .delay(initialDelay, TimeUnit.MILLISECONDS)
                // Преобразуем значения, добавляя к каждому 1L
                .map(n -> n + 1L);
    }

    /**
     * Возращение значения 0L с заданной задержкой
     *
     * @param delay - Задержка
     * @return Observable который эммитит только одно значение 0L с указанной
     * задержкой {@code delay}
     */
    public Observable<Long> delayedZero(long delay) {
        return Observable.timer(delay, TimeUnit.MILLISECONDS)
                .map(time -> 0L);
    }

    /**
     * Последовательный вызов нескольких методов с длительными вычислениями.
     *
     * @param unstableCondition - условие, которое необходимо передавать в {@code unstableMethod}
     * @return {@link Observable} который последовательно эммитит результаты выполнения методов, в
     * следующем порядке:
     * 1. {@link #expensiveMethod()}
     * 2. {@link #alternativeExpensiveMethod()}
     * 3. {@link #unstableMethod(boolean)}
     */
    public Observable<Integer> combinationExpensiveMethods(final boolean unstableCondition) {
        return Observable.defer(() -> {
            Integer result1 = expensiveMethod();
            Integer result2 = alternativeExpensiveMethod();
            Integer result3 = unstableMethod(unstableCondition);

            return Observable.just(result1, result2, result3);
        });
    }

    /**
     * Без каких либо событий
     *
     * @return {@link Observable} который не эммитит ни одного элемента и не вызывает
     * {@code onComplete} или {@code onError}
     */
    public Observable<Integer> withoutAnyEvents() {
        throw new NotImplementedException();
    }

    /**
     * Пустая последовательность
     *
     * @return {@link Observable} который не эммитит значения, вызывается только {@code onComplete}
     */
    public Observable<Integer> onlyComplete() {
        throw new NotImplementedException();
    }

    /**
     * Только одна ошибка
     *
     * @return {@link Observable} который не эммитит значения, только в {@code onError} приходит
     * ошибка {@link ExpectedException}
     */
    public Observable<Integer> onlyError() {
        throw new NotImplementedException();
    }

    /* Вспомогательные методы */

    /**
     * Длительные вычисления. (Вспомогательный метод! Не изменять!)
     *
     * @return Результат вычислений
     */
    public int expensiveMethod() {
        // Some Expensive Calculations
        return Integer.MAX_VALUE;
    }

    /**
     * Длительные вычисления. (Вспомогательный метод! Не изменять!)
     *
     * @return Результат вычислений
     */
    public int alternativeExpensiveMethod() {
        // Some Expensive Calculations
        return Integer.MAX_VALUE;
    }

    /**
     * Метод, генерирующий ошибку при unstableCondition=true
     * (Вспомогательный метод! Не изменять!)
     *
     * @return Результат вычислений
     */
    public int unstableMethod(boolean unstableCondition) {
        if (unstableCondition) {
            throw new ExpectedException();
        }
        return Integer.MAX_VALUE;
    }

}
