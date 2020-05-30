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
package au.com.rma.micronaut.app.service;

import au.com.rma.micronaut.configuration.jmsmq.annotation.JmsDestination;
import au.com.rma.micronaut.configuration.jmsmq.annotation.JmsListener;
import io.micronaut.context.annotation.Infrastructure;
import io.micronaut.context.annotation.Property;
import io.micronaut.messaging.annotation.Body;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Infrastructure
@JmsListener("app")
public class ReverseService {
  private static Logger logger = LoggerFactory.getLogger(ReverseService.class);
  private static final String EMPTY_STRING = "";

  @Property(name = "services.reverse.prefix")
  protected String prefix = EMPTY_STRING;

  @Property(name = "services.reverse.postfix")
  protected String postfix = EMPTY_STRING;

  @JmsDestination("${services.reverse.queue}")
  public String reverseString(@Body String text) {
    logger.info("reverseString called with {}", text);

    return prefix + StringUtils.reverse(text) + postfix;
  }
}
