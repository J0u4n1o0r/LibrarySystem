package controller;

import model.*;

import java.time.Instant;
import java.util.*;
import java.util.function.Predicate;

/**
 *  This class holds methods for searching, sorting etc.
 *  References: some of the searching algorithms were modified from code given in class by @apont and
 *  using Predicate, which was learned mostly through: https://www.youtube.com/watch?v=lefRpUTcE1g&ab_channel=DouglasSchmidt
 *  and comparator: https://www.youtube.com/watch?v=u8D2fydghj4&ab_channel=JavaMadeEasy among other videos on youtube.
 *  @celsoM_2017216
 */

public class Controller {
    private final WriteToFile wtf;
    private final ReadFromFile rff;

    // constructor initializes instances of ReadFromFile and WriteToFile
    public Controller() {
        rff = new ReadFromFile();
        wtf = new WriteToFile();
    }

    // linearSearch for Readers based on a predicate
    public Reader readerSearch(Predicate<Reader> searchPredicate) {

        // Going one by one the elements in the array
        List<Reader> readersList = rff.loadReadersList();
        for (Reader r : readersList) {
            if (searchPredicate.test(r)) {
                // print found reader
                System.out.println(r);
                return r;
            }
        }

        // print error that no reader was found
        System.out.println("The Reader was not found in the database");
        return null;
    }

    // Create new Borrowing and write it to the file using Instant.now to get current date
    public void borrowBook(Reader r, Book b) {
        Borrow borrow = new Borrow(r.getId(), r.getName(), b.getId(), b.getTitle(), Date.from(Instant.now()));
        wtf.newBorrowing(borrow);
    }

    // Linear Search for borrowing of a book. Method checks whether book is available or not and if book is in waiting list
    public boolean isBookNotBorrowed(Book b) {
        List<Borrow> borrowList = new ReadFromFile().loadBorrowList();

        for (Borrow borrow : borrowList) {
            String bID = borrow.getBookId();
            if (bID.equals(b.getId()) && !borrow.isBorrowReturned() && !borrow.isInQueue()) {
                // a borrowed book that is not in the queue has been found -> return false
                return false;
            }
        }
        // no borrow entry has been found -> return true
        return true;
    }

    // Register a new book return and get next borrowing
    public Borrow returnBook(Book book) {
        return wtf.returnBorrowingAndGetNextBorrowing(book);
    }

    // Take first of queue and borrow the book
    public void removeFirstBorrowInQueueAndAssignNextPerson(Book book) {
        wtf.removeFirstBorrowInQueueAndAssignNextPerson(book);
    }

    // insert a new borrowed member into the queue
    public void addToQueue(Reader r, Book b) {
        Borrow borrow = new Borrow(r.getId(), r.getName(), b.getId(), b.getTitle(), Date.from(Instant.now()));
        borrow.setInQueue(true);
        wtf.newBorrowing(borrow);
    }

    // Sort books by comparator
    public List<Book> orderBooks(Comparator<Book> comparing) {
        List<Book> booksList = rff.loadBooksList();
        return orderList(comparing, booksList);
    }

    // Sort readers by comparator
    public List<Reader> orderReaders(Comparator<Reader> comparing) {
        List<Reader> readersList = rff.loadReadersList();
        return orderList(comparing, readersList);
    }

    // Sort borrowings by comparator
    public List<Borrow> orderBorrowings(Comparator<Borrow> comparator) {
        List<Borrow> borrows = rff.loadBorrowList();
        return orderList(comparator, borrows);
    }

    // Generic bubblesort that uses a comparator for comparing 
    public <T> List<T> orderList(Comparator<T> comparator, List<T> listToSort) {
        List<T> copyOfList = new ArrayList<>(listToSort);
        
        for (int j = 0; j < copyOfList.size() - 1; j++) {
            for (int i = j + 1; i < copyOfList.size(); i++) {
                if (comparator.compare(copyOfList.get(j), copyOfList.get(i)) > 0) {
                    T temp = copyOfList.get(j);
                    copyOfList.set(j, copyOfList.get(i));
                    copyOfList.set(i, temp);
                }
            }
        }
        return copyOfList;
    }

    // Linear Search for book by id
    public Book findBook(String bookID) {
        List<Book> booksList = rff.loadBooksList();
        for (Book b : booksList) {
            // When the element is found, stop the loop and return the book
            if (b.getId().equals(bookID)) {
                return b;
            }
        }
        return null;
    }

    // Linear Search for reader by id
    public Reader findReader(String readerID) {
        List<Reader> readersList = rff.loadReadersList();
        for (Reader r : readersList) {
            // When the element is found, stop the loop and return the reader
            if (r.getId().equals(readerID)) {
                return r;
            }
        }
        return null;
    }

    // linearSearch for Books that match the predicate
    public List<Book> bookSearch(Predicate<Book> searchPredicate) {
        // Going one by one the elements in the array

        List<Book> results = new ArrayList<>();
        List<Book> booksList = rff.loadBooksList();

        for (Book b : booksList) {
            // When the element is found, add it to the results
            if (searchPredicate.test(b)) {
                results.add(b);
            }

        }
        return results;
    }

}