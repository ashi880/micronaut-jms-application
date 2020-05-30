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
package au.com.rma.micronaut.app.controller;

import au.com.rma.micronaut.app.client.BookService;
import au.com.rma.micronaut.app.client.TextService;
import au.com.rma.micronaut.app.model.Book;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

import javax.inject.Inject;
import java.util.UUID;

@Controller("/api")
public class MessageSenderController {
  @Inject
  TextService textService;

  @Inject
  BookService bookService;

  @Post("/reverse")
  public HttpResponse<String> reverse(@Body String text) {
    return HttpResponse.ok(this.textService.reverseString(text));
  }

  @Post("/upperCase")
  public HttpResponse<String> upperCase(@Body String text) {
    return HttpResponse.ok(this.textService.uppercaseString(text));
  }

  @Post("/book")
  public HttpResponse<Book> postComplexMessage(@Body Book book) {
    Book result = bookService.sendBook(100, UUID.randomUUID().toString(), book);

    return HttpResponse.ok(result);
  }
}
