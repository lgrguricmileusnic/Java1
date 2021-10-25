package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.Collection;
import hr.fer.oprpp1.custom.collections.ElementsGetter;

/**
 * Demo displays ElementsGetter detecting concurrent modification during its use and throwing a
 * {@link java.util.ConcurrentModificationException};
 */
public class ElementsGetterDemo2 {

    public static void main(String[] args) {
        Collection col = new ArrayIndexedCollection();
        col.add("Ivo");
        col.add("Ana");
        col.add("Jasna");
        ElementsGetter getter = col.createElementsGetter();
        System.out.println("Jedan element: " + getter.getNextElement());
        System.out.println("Jedan element: " + getter.getNextElement());
        col.clear();
        System.out.println("Jedan element: " + getter.getNextElement());
    }

}
