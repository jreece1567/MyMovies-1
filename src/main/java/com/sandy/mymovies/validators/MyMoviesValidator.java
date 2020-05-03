package com.sandy.mymovies.validators;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

/**
 * JSR-303 Validator for validating DTO beans.
 */
@Component
public class MyMoviesValidator implements Validator {

  private final SpringValidatorAdapter validator;

  public MyMoviesValidator(final SpringValidatorAdapter validator) {
    super();
    this.validator = validator;
  }

  @Override
  public boolean supports(final Class theClass) {

    // we can validate any class from the 'models.dto' package
    return theClass.getPackage().getName().startsWith("com.sandy.mymovies.models.dto");
  }

  /**
   * Validate a bean via JSR-303 annotations, and possibly via additional custom validation. If
   * JSR-303 validation fails, custom validation will not be performed.
   *
   * @param obj the POJO to be validated
   * @param errors the object for recording any errors
   */
  @Override
  public void validate(final Object obj, final Errors errors) {

    // jsr303 validation - value-range constaints only
    validator.validate(obj, errors);

    if (!errors.hasErrors()) {
      // no errors so far ... continue validating
      // custom validation
      customValidation(obj, errors);
    }

    // the result of the validation is in the 'errors' list.
  }

  /**
   * Perform custom validation on an Object that cannot be performed by JSR-303. Typically this
   * validation includes 'business logic' that cannot be expressed in simple value-range-constraints
   * (JSR-303-style).
   *
   * @param obj the POJO to be validated
   * @param errors the object for recording any errors
   */
  private void customValidation(final Object obj, final Errors errors) {

    // ... reject (mark as in-error) fields or other error-cases (usually field-combinations)
    // errors.reject(... , ...);
    // errors.rejectField(... , ...);

    // the result of the validation is in the 'errors' list.
  }

  /**
   * Evaluate any validation errors.
   * @param errors the collection of errors from the JSR-303 validator
   * @throws IllegalArgumentException if any errors are present
   */
  public static void processErrors(Errors errors) {
    if (errors.hasErrors()) {
      final Map<String, Set<String>> errorsMap = errors.getFieldErrors().stream().collect(Collectors
          .groupingBy(
              FieldError::getField,
              Collectors.mapping(FieldError::getDefaultMessage, Collectors.toSet())));
      throw new IllegalArgumentException(errorsMap.toString());
    }
  }
}
