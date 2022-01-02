package hr.fer.zemris.java.gui.prim;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.util.*;

/**
 * {@link ListModel} implementation for displaying lists of primitive numbers.
 * Supplies the client with method next for calculating and displaying next primitive number.
 */
public class PrimListModel implements ListModel<Integer> {
    /**
     * Underlying {@link List} storing all calculated primitive numbers to be displayed.
     */
    private List<Integer> prims;
    /**
     * Underlying set of subscribed {@link ListDataListener} objects
     */
    private Set<ListDataListener> listeners;

    /**
     * Constructor
     */
    public PrimListModel() {
        prims = new ArrayList<>();
        prims.add(1);
        listeners = new HashSet<>();
    }

    /**
     * Adds next primitive number to underlying list and notifies subscribed {@link ListDataListener} objects.
     */
    public void next() {
        int position = prims.size();

        prims.add(nextPrime(prims.get(prims.size() -1 )));

        ListDataEvent listDataEvent = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, position, position);
        listeners.forEach(l -> l.contentsChanged(listDataEvent));
    }

    /**
     * Returns next primitive number starting from passed value.
     * @param start starting value
     * @return next primitive number
     */
    private Integer nextPrime(Integer start) {
        int n = start + 1;
        while(true) {
            boolean isPrime = true;
            for (int i = 2; i <= n/2; i++) {
                if (n % i == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) {
                return n;
            }
            n++;
        }
    }


    /**
     * Returns the length of the list.
     *
     * @return the length of the list
     */
    @Override
    public int getSize() {
        return prims.size();
    }

    /**
     * Returns the value at the specified index.
     *
     * @param index the requested index
     * @return the value at <code>index</code>
     */
    @Override
    public Integer getElementAt(int index) {
        return prims.get(index);
    }

    /**
     * Adds a listener to the list that's notified each time a change
     * to the data model occurs.
     *
     * @param l the <code>ListDataListener</code> to be added
     */
    @Override
    public void addListDataListener(ListDataListener l) {
        listeners.add(l);
    }

    /**
     * Removes a listener from the list that's notified each time a
     * change to the data model occurs.
     *
     * @param l the <code>ListDataListener</code> to be removed
     */
    @Override
    public void removeListDataListener(ListDataListener l) {
        listeners.remove(l);
    }
}
