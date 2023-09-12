package com.polarbookshop.catalogservice.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class BookValidationTesting {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void whenAllFieldsCorrectThenValidationSucceeds() {
        Book book = new Book("1234567890", "Title", "Author", 9.90);
        Set<ConstraintViolation<Book>> violationSet = validator.validate(book);
        Assertions.assertThat(violationSet).isEmpty();
    }

    @Test
    void whenIsbnDefinedButIncorrectThenValidationFails(){
        Book book = new Book("123456789", "Title", "Author", 9.90);
        Set<ConstraintViolation<Book>> violationSet = validator.validate(book);
        Assertions.assertThat(violationSet).hasSize(1);
        ConstraintViolation<Book> violation = violationSet.iterator().next();
        Assertions.assertThat(violation.getMessage()).isEqualTo("The ISBN format must be valid.");
    }
}