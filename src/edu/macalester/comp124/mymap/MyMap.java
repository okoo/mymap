package edu.macalester.comp124.mymap;

import java.util.LinkedList;
import java.util.List;

/**
 * A simple implementation of a hashtable.
 *
 * @author shilad
 *
 * @param <K> Class for keys in the table.
 * @param <V> Class for values in the table.
 */
public class MyMap <K, V> {
    V value;
    private static final int INITIAL_SIZE = 4;

    /** The table is package-protected so that the unit test can examine it. */
	List<MyEntry<K, V>> [] buckets;
	
	/** Number of unique entries (e.g. keys) in the table */
	private int numEntries = 0;
	
	/** Threshold that determines when the table should expand */
	private double loadFactor = 0.75;
	
	/**
	 * Initializes the data structures associated with a new hashmap.
	 */
	public MyMap() {
		buckets = newArrayOfEntries(INITIAL_SIZE);
	}
	
	/**
	 * Returns the number of unique entries (e.g. keys) in the table.
	 * @return the number of entries.
	 */
	public int size() {
		return numEntries;
	}
	
	/**
	 * Adds a new key, value pair to the table.
	 * @param key
	 * @param value
	 */
	public void put(K key, V value) {
        if (value == null) {
            throw new NullPointerException();
        }


        expandIfNecessary();


        //make sure the key is not already in there
        boolean put = true;
        int hash = key.hashCode();
        int index = hash % buckets.length;
        List<MyEntry<K, V>> bucket = buckets[index];
        for (int i = 0; i < bucket.size(); i++) {
            if ((bucket.get(i).getKey().equals(key))) {
                bucket.get(i).setValue(value);
                put = false;
            }
        }
        //adding new entry
        while (put) {
            MyEntry<K, V> e = new MyEntry<>(key, value);
            bucket = buckets[index];
            bucket.add(bucket.size(), e);
            bucket.get(bucket.size() - 1).setValue(value);
            numEntries += 1;
            put = false;
        }


        // TODO: Store the key.
    }
	
	/**
	 * Returns the value associated with the specified key, or null if it
	 * doesn't exist.
	 * 
	 * @param key
	 * @return
	 */
	public V get(K key) {
		// TODO: retrieve the key.

        int hash = key.hashCode();
        int index = hash % buckets.length;
        List<MyEntry<K, V>> bucket = buckets[index];
        for (int i = 0; i < bucket.size(); i++) {
            if ((bucket.get(i).getKey().equals(key))) {
                value = bucket.get(i).getValue();
                break;
            } else {
                value = null;
            }
        }

        return value;
    }

    /**
     * Expands the table to double the size, if necessary.
     */
	private void expandIfNecessary() {
		// TODO: expand if necessary
        if (size() > buckets.length * loadFactor) {
            List<MyEntry<K, V>>[] newBuckets;
            newBuckets = newArrayOfEntries(buckets.length * 2);
            for (int x = 0; x < buckets.length; x++) {
                for (int y = 0; y < buckets[x].size(); y++) {
                    K key = buckets[x].get(y).getKey();
                    V value = buckets[x].get(y).getValue();
                    MyEntry<K, V> e = new MyEntry<>(key, value);
                    int hash = key.hashCode();
                    int index = hash % newBuckets.length;
                    List<MyEntry<K, V>> newBucket = newBuckets[index];
                    boolean togo = true;
                    for (int i = 0; 0 < newBucket.size(); i++) {
                        if (newBucket.get(i).getKey().equals(key)) {
                            newBucket.get(i).setValue(value);
                            togo = false;
                        }
                    }
                    while (togo) {
                        newBucket.add(newBucket.size(), e);
                        togo = false;
                    }
                }
            }
            buckets = newBuckets;
        }

    }

    /**
	 * Returns an array of the specified size, each
	 * containing an empty linked list that can be
	 * filled with MyEntry objects.
	 * 
	 * @param capacity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<MyEntry<K,V>>[] newArrayOfEntries(int capacity) {
		List<MyEntry<K, V>> [] entries = (List<MyEntry<K,V>> []) (
				java.lang.reflect.Array.newInstance(LinkedList.class, capacity));
		for (int i = 0; i < entries.length; i++) {
			entries[i] = new LinkedList();
		}
		return entries;
	}
	
}
