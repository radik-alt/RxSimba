package com.example.rxtraining.rx;

import com.example.rxtraining.exceptions.ExpectedException;
import com.example.rxtraining.exceptions.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

/**
 * @author Arthur Korchagin (artur.korchagin@simbirsoft.com)
 * @since 20.11.18
 */
public class RxSingleTraining {

    /* Тренировочные методы */

    /**
     * Эммит только 1 положительного элемента либо ошибка {@link ExpectedException}
     *
     * @param value любое произвольное число
     * @return {@code Single} который эммитит значение {@code value} если оно положительное,
     * либо ошибку {@link ExpectedException} если оно отрицательное
     */
    public Single<Integer> onlyOneElement(Integer value) {
        return Single.create(emitter -> {
            if (value <= 0) {
                emitter.onError(new ExpectedException());
            } else {
                emitter.onSuccess(value);
            }
        });
    }

    /**
     * Преобразование последовательности {@code Observable} в {@code Single}
     *
     * @param integerObservable {@link Observable} произвольная последовательность чисел
     * @return {@link Single} который эммитит либо самый первый элемент последовательности
     * {@code integerObservable}, либо ошибку {@link NoSuchElementException} в случае, если
     * последовательность пустая
     */
    public Single<Integer> onlyOneElementOfSequence(Observable<Integer> integerObservable) {
        return integerObservable.singleOrError();
    }

    /**
     * Сумма всех элементов последовательности
     *
     * @param integerObservable {@link Observable} произвольная последовательность чисел
     * @return {@link Single} который эммитит сумму всех элементов, либо 0 если последовательность
     * пустая
     */
    public Single<Integer> calculateSumOfValues(Observable<Integer> integerObservable) {
        return integerObservable.reduce(0, Integer::sum).onErrorReturnItem(0);
    }

    /**
     * Преобразование последовательности в список
     *
     * @param integerObservable {@link Observable} произвольная последовательность чисел
     * @return {@link Single} который эммитит {@link List} со всеми элементами последовательности
     * {@code integerObservable}
     */
    public Single<List<Integer>> collectionOfValues(Observable<Integer> integerObservable) {
        return integerObservable.toList();
    }

    /**
     * Проверка всех элементов на положительность
     *
     * @param integerSingle {@link Observable} произвольная последовательность чисел
     * @return {@link Single} который эммитит {@code true} если все элементы последовательности
     * {@code integerSingle} положительны, {@code false} если есть отрицательные элементы
     */
    public Single<Boolean> allElementsIsPositive(Observable<Integer> integerSingle) {
        return integerSingle.all(i -> i > 0);
    }

}
