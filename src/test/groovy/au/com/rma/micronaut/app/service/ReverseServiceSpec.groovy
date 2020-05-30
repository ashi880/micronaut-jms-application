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
package au.com.rma.micronaut.app.service

import spock.lang.Specification

class ReverseServiceSpec extends Specification {

  def "verify default reverse service works as expected"() {
    given:
    def service = new ReverseService();

    when:
    def response = service.reverseString("some text")

    then:
    response == "txet emos"
  }

  def "verify reverse service supports prefixes"() {
    given:
    def service = new ReverseService();
    service.prefix = "pre-"

    when:
    def response = service.reverseString("some text")

    then:
    response == "pre-txet emos"
  }

  def "verify reverse service supports postfixes"() {
    given:
    def service = new ReverseService();
    service.postfix = "-post"

    when:
    def response = service.reverseString("some text")

    then:
    response == "txet emos-post"
  }

  def "verify reverse service supports prefixes and postfix"() {
    given:
    def service = new ReverseService();
    service.prefix = "pre-"
    service.postfix = "-post"

    when:
    def response = service.reverseString("some text")

    then:
    response == "pre-txet emos-post"
  }

}
