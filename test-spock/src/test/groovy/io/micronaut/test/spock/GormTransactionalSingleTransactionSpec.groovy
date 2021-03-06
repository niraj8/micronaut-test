/*
 * Copyright 2017-2020 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.test.spock

import io.micronaut.context.ApplicationContext
import io.micronaut.test.annotation.MicronautTest
import io.micronaut.test.annotation.TransactionMode
import io.micronaut.test.spock.entities.Book
import spock.lang.Specification
import spock.lang.Stepwise

import javax.inject.Inject

@MicronautTest(packages = "io.micronaut.test.spock.entities", transactionMode = TransactionMode.SINGLE_TRANSACTION)
@HibernateProperties
@Stepwise
class GormTransactionalSingleTransactionSpec extends Specification {

    @Inject
    ApplicationContext applicationContext

    def setup() {
        new Book(name: "The Shining").save(failOnError: true, flush: true)
    }

    def cleanup() {
        // check book from setup was rolled back
        assert Book.count() == 0
    }

    void "book was saved"() {
        expect:
        Book.count() == 1
    }

    void "book was saved again"() {
        expect:
        Book.count() == 1
    }
}