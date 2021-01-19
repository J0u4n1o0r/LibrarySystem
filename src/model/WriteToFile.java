
package model;

import controller.Controller;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

/**
 *  Class writes records to borrowings.txt file and to return next borrowing from queue.
 *  @celsoM_2017216
 */

public class WriteToFile {

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH);
    private final File borrowListPath = new File("resources/borrowings.txt");

    /**
     * Writes a new borrowing to the borrowing file
     * @param newBorrow borrowing to be added
     */
    public void newBorrowing(Borrow newBorrow) {
        // write single borrowing wit append true to file
        writeToBorrowFile(Collections.singletonList(newBorrow), true);
    }

    /**
     * marks a book returned and returns the next borrowing in queue
     * @param book to be processed
     * @return next borrowing in queue
     */
    public Borrow returnBorrowingAndGetNextBorrowing(Book book) {
        List<Borrow> borrows = new Controller().orderBorrowings(Comparator.comparing(Borrow::getBorrowDate));

        // find and set return dates of borrowings where the id matches and the borrowing is not returned
        // resulting in all current borrowings to be considered returned
        for (Borrow b : borrows) {
            if (b.getBookId().equals(book.getId()) && !b.isBorrowReturned()) {
                if (!b.isInQueue()) {
                    b.setReturnDate(Date.from(Instant.now()));
                }
            }
        }

        // write all borrowings to the file
        writeToBorrowFile(borrows, false);
        // return the first borrowing in queue
        return book.getFirstBorrowInQueue();
    }

    /**
     * Remove the first person in queue of a book
     * assign the removed person to be the person that borrows the book
     * @param book book to be processed
     */
    public void removeFirstBorrowInQueueAndAssignNextPerson(Book book) {
        List<Borrow> borrows = new Controller().orderBorrowings(Comparator.comparing(Borrow::getBorrowDate));

        Borrow nextBorrow = book.getFirstBorrowInQueue();

        // borrowing might not be the same instance as the borrowing from the queue
        // search through all borrowings for a matching id and set to be not in queue
        // resulting in a new current borrowing because the return date is not set
        for (Borrow b : borrows) {
            if (b.getBorrowId().equals(nextBorrow.getBorrowId())) {
                b.setInQueue(false);
                break;
            }
        }

        // write all borrowings to the file
        writeToBorrowFile(borrows, false);
    }

    /**
     * Write a list of borrowings to the borrowings file
     * @param borrows borrowings to be written
     * @param append should the borrowings be appended
     */
    private void writeToBorrowFile(List<Borrow> borrows, boolean append) {
        try {
            FileWriter fw = new FileWriter(borrowListPath, append);

            for (Borrow borrow1 : borrows) {
                String readerId = borrow1.getReaderId();
                String name = borrow1.getName();
                String bookId = borrow1.getBookId();
                String title = borrow1.getTitle();
                String borrowDate = sdf.format(borrow1.getBorrowDate());
                String returnDate = borrow1.getReturnDate() != null ? sdf.format(borrow1.getReturnDate()) : null;
                String borrowId = borrow1.getBorrowId();
                boolean isInQueue = borrow1.isInQueue();
                fw.write(readerId + "," + name + "," + bookId + "," + title + "," + borrowDate + "," + returnDate + "," + borrowId + "," + isInQueue + "\n");
            }
            fw.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Success...");
    }


}