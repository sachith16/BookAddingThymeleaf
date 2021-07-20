package com.example.books;

public class Book {
    private String bookId;
    private String bookName;
    private String copyCount;
    private String author;
    private String coverImage;
    private String addedBy;

    public Book(String bookId, String bookName, String copyCount, String author, String coverImage, String addedBy) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.copyCount = copyCount;
        this.author = author;
        this.coverImage = coverImage;
        this.addedBy = addedBy;
    }

    public Book() {}

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getCopyCount() {
        return copyCount;
    }

    public void setCopyCount(String copyCount) {
        this.copyCount = copyCount;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId='" + bookId + '\'' +
                ", bookName='" + bookName + '\'' +
                ", copyCount='" + copyCount + '\'' +
                ", author='" + author + '\'' +
                ", coverImage='" + coverImage + '\'' +
                ", addedBy='" + addedBy + '\'' +
                '}';
    }
}
