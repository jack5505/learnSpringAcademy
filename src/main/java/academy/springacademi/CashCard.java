package academy.springacademi;

import org.springframework.data.annotation.Id;

/*
 *
 *  @author Sabirov Jakhongir
 *
 */
public record CashCard(@Id Long id,Double amount) {
}
