/*
 * Copyright (c) 2020 Richard Allwood
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package au.com.rma.micronaut.app.model;

import io.micronaut.core.annotation.Introspected;

import java.util.Objects;

@Introspected
public class Book {
  private Author author;
  private String title;
  private int yearPublished;
  private int numberOfPages;
  private CoverType coverType;
  private String ISBN;

  public Author getAuthor() {
    return author;
  }

  public void setAuthor(Author author) {
    this.author = author;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getYearPublished() {
    return yearPublished;
  }

  public void setYearPublished(int yearPublished) {
    this.yearPublished = yearPublished;
  }

  public int getNumberOfPages() {
    return numberOfPages;
  }

  public void setNumberOfPages(int numberOfPages) {
    this.numberOfPages = numberOfPages;
  }

  public CoverType getCoverType() {
    return coverType;
  }

  public void setCoverType(CoverType coverType) {
    this.coverType = coverType;
  }

  public String getISBN() {
    return ISBN;
  }

  public void setISBN(String ISBN) {
    this.ISBN = ISBN;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Book)) return false;
    Book book = (Book) o;
    return getNumberOfPages() == book.getNumberOfPages() &&
        Objects.equals(getAuthor(), book.getAuthor()) &&
        Objects.equals(getTitle(), book.getTitle()) &&
        getYearPublished() == book.getYearPublished() &&
        getCoverType() == book.getCoverType() &&
        Objects.equals(getISBN(), book.getISBN());
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getAuthor(),
        getTitle(),
        getYearPublished(),
        getNumberOfPages(),
        getCoverType(),
        getISBN());
  }

  @Override
  public String toString() {
    return "Book{" +
        "author=" + author +
        ", title='" + title + '\'' +
        ", yearPublished=" + yearPublished +
        ", numberOfPages=" + numberOfPages +
        ", coverType=" + coverType +
        ", isbn=" + ISBN +
        '}';
  }
}
