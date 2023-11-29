package academy.springacademi;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/*
 *
 *  @author Sabirov Jakhongir
 *
 */
public interface CashRepository  extends CrudRepository<CashCard,Long>, PagingAndSortingRepository<CashCard,Long> {
}
