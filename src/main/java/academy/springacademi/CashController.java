package academy.springacademi;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/*
 *
 *  @author Sabirov Jakhongir
 *
 */
@RestController
@RequestMapping("/cashcards")
public class CashController {
    @Autowired
    private CashRepository cashRepository;

    @GetMapping
    public ResponseEntity<?> getAll(Pageable pageRequest){
        Page<CashCard> all = cashRepository.findAll(PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize(),pageRequest.getSort()));
        return ResponseEntity.ok(all.getContent());
    }
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CashCard cards, UriComponentsBuilder ucb){
        CashCard savedCashCard = cashRepository.save(cards);
        URI locationOfNewCashCard = ucb
            .path("cashcards/{id}")
            .buildAndExpand(savedCashCard.id())
            .toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
    }
}
