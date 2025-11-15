package utils;

import java.util.Iterator;

/**
 * Extended iterator with backward navigation and positioning operations.
 * @param <E> element type
 */
public interface ExtendedIterator<E> extends Iterator<E> {
    /**
     * Return the current element and move to the previous element.
     * @return the element that was current before moving
     */
    E previous();

    /**
     * True if there is a previous element available.
     * @return true if previous element exists
     */
    boolean hasPrevious();

    /** Place the iterator at the first element. */
    void goFirst();

    /** Place the iterator at the last element. */
    void goLast();
}

