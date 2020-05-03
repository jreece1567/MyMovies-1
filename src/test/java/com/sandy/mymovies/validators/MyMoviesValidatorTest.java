package com.sandy.mymovies.validators;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.sandy.mymovies.models.domain.Actor;
import com.sandy.mymovies.models.dto.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyMoviesValidatorTest {

  @Mock
  SpringValidatorAdapter validator;

  @Autowired
  MyMoviesValidator moviesValidator;

  @Test
  public void testSupports_withvalidclass_succeeds() {
    assertThat(moviesValidator.supports(new Movie().getClass()),is(true));
  }

  @Test
  public void testSupports_withinvalidclass_fails() {
    assertThat(moviesValidator.supports(new Actor().getClass()),is(false));
  }

  @Test
  public void testErrors_withnoerrors_succeeds() {
    Movie movie = new Movie();
    final Errors errors = new BeanPropertyBindingResult(movie, movie.getClass().getName());
    MyMoviesValidator.processErrors(errors);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testErrors_witherrors_succeeds() {
    Movie movie = new Movie();
    final Errors errors = new BeanPropertyBindingResult(movie, movie.getClass().getName());
    errors.reject("an error");
    MyMoviesValidator.processErrors(errors);
  }
}
