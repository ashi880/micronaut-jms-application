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
package au.com.rma.micronaut.app;

import au.com.rma.micronaut.app.client.BookService;
import au.com.rma.micronaut.app.client.TextService;
import au.com.rma.micronaut.app.model.Author;
import au.com.rma.micronaut.app.model.Book;
import au.com.rma.micronaut.app.model.CoverType;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.context.event.StartupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static java.time.Month.OCTOBER;

@Singleton
public class StartupListener implements ApplicationEventListener<StartupEvent> {
  private static final Logger logger = LoggerFactory.getLogger(StartupListener.class);

  @Inject
  private TextService textService;

  @Inject
  private BookService bookService;

  @Override
  public void onApplicationEvent(StartupEvent event) {
    logger.info("Reverse String: {}", textService.reverseString("Reverse String Service " + LocalDateTime.now()));
    logger.info("Uppercase String: {}", textService.uppercaseString("uppercase service call"));

    Book book = bookService.sendBook(200, UUID.randomUUID().toString(), createBook());

    logger.info("*** Book {} ***", book);
  }

  private Book createBook() {
    Author author = new Author();
    author.setName("David Brin");
    author.setBirthDate(LocalDate.of(1950, OCTOBER, 6));

    Book book = new Book();
    book.setAuthor(author);
    book.setTitle("The River of Time");
    book.setYearPublished(1997);
    book.setCoverType(CoverType.PAPERBACK);
    book.setNumberOfPages(295);

    return book;
  }
}
