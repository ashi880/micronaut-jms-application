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
package au.com.rma.micronaut.app.client;

import au.com.rma.micronaut.app.model.Book;
import au.com.rma.micronaut.configuration.jmsmq.annotation.*;
import io.micronaut.messaging.annotation.Body;

@JmsClient("admin")
@JmsProperties({
    @JmsProperty(name="deliveryDelay", value="0"),
    @JmsProperty(name="priority", value="3")
})
public interface BookService {

  @JmsProperty(name="disableMessageId", value="false")
  @JmsProperty(name="disableMessageTimestamp", value="false")
  @JmsDestination(value = "${services.book.queue}")
  @JmsReplyDestination(value = "${services.replyQueue}", timeout = 5_000)
  Book sendBook(
      @JmsProperty(name="deliveryDelay") long deliveryDelay,
      @JmsProperty(name="correlationId") String correlationId,
      @Body Book book);
}
