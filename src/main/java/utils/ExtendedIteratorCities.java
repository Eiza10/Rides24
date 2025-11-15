package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ExtendedIteratorCities implements ExtendedIterator<String> {

	private final List<String> cities;
	/** Cursor points to the index of the next element to be returned by next() */
	private int cursor;

	/** Construct an iterator over a copy of the provided list (null treated as empty). */
	public ExtendedIteratorCities(List<String> cities) {
		if (cities == null) {
			this.cities = new ArrayList<>();
		} else {
			this.cities = new ArrayList<>(cities);
		}
		this.cursor = 0;
	}

	/** Construct an empty iterator. */
	public ExtendedIteratorCities() {
		this.cities = new ArrayList<>();
		this.cursor = 0;
	}

	@Override
	public boolean hasNext() {
		return cursor < cities.size();
	}

	@Override
	public String next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		return cities.get(cursor++);
	}

	@Override
	public String previous() {
		if (!hasPrevious()) {
			throw new NoSuchElementException();
		}
		return cities.get(--cursor);
	}

	@Override
	public boolean hasPrevious() {
		return cursor > 0;
	}

	@Override
	public void goFirst() {
		cursor = 0;
	}

	@Override
	public void goLast() {
		// Position cursor so that previous() will return the last element
		cursor = cities.size();
	}

	@Override
	public void remove() {
		// Optional operation not supported for this simple iterator
		throw new UnsupportedOperationException("remove");
	}

}
