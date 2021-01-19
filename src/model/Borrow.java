/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

/**
 *  Class containing setters and getters for object Borrow using UUID. Also contains different constructors for different instances of the object.
 *  References: UUID used for borrowID based on youtube video: https://www.youtube.com/watch?v=eCrh5jhuIkQ&ab_channel=LearningProgramming
 *  @celsoM_2017216
 */

public class Borrow {
    private String readerId;
    private String bookId;
    private String name;
    private String title;
    private Date borrowDate;
    private Date returnDate;
    private final String borrowId;
    private boolean isInQueue;

    public Borrow(String readerId, String name, String bookId, String title, Date borrowDate) {
        this.readerId = readerId;
        this.name = name;
        this.bookId = bookId;
        this.title = title;
        this.borrowDate = borrowDate;
        // Getting borrow random unique identifier to String
        borrowId = UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return "Borrow{" +
                "readerId=" + readerId + '\n' +
                "bookId=" + bookId + '\n' +
                ", name='" + name + '\n' +
                ", title='" + title + '\n' +
                ", borrowDate=" + borrowDate + '\n' +
                ", returnDate=" + returnDate + '\n';
    }

    public Borrow(String readerId, String name, String bookId, String title, Date borrowDate, Date returnDate, boolean isInQueue, String borrowId) {
        this.readerId = readerId;
        this.name = name;
        this.bookId = bookId;
        this.title = title;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.isInQueue = isInQueue;
        this.borrowId = borrowId;
    }


    public String getReaderId() {
        return readerId;
    }

    public void setReaderId(String readerId) {
        this.readerId = readerId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }


    //
    public Borrow(String readerId, String bookId, Date borrowDate) {
        this.readerId = readerId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        // Getting borrow random unique identifier to String
        borrowId = UUID.randomUUID().toString();
    }


    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBorrowId() {
        return borrowId;
    }

    public boolean isBorrowReturned() {
        return returnDate != null;
    }

    public boolean isInQueue() {
        return isInQueue;
    }

    public void setInQueue(boolean inQueue) {
        isInQueue = inQueue;
    }
}
