package model;

import java.util.ArrayList;
import java.util.List;

/**
 *  Custom made LinkedListQueue for Book waiting list using generics.
 *  References: Apart of explanations and code samples given on Week 4, this code sample also helped me to
 *  understand and create this class: https://introcs.cs.princeton.edu/java/43stack/Queue.java.html
 *  @celsoM_2017216
 */

public class Queue<T> {
    private Element first;
    private Element last;
    public void add(T thing) {
        if (first == null) {
            first = new Element(thing);
        } else if (last == null) {
            last = new Element(thing);
            first.next = last;
        } else {
            Element element = new Element(thing);
            last.next = element;
            last = element;
        }
    }

    public T firstWithoutRemoving() {
        if (first == null) {
            return null;
        }

        return first.element;
    }

    public List<T> all() {
        List<T> elements = new ArrayList<>();

        Element element = first;
        while (element != null) {
            elements.add(element.element);
            element = element.next;
        }

        return elements;
    }


    private final class Element {
        private final T element;
        private Element next;

        public Element(T thing) {
            element = thing;
        }
    }
}
