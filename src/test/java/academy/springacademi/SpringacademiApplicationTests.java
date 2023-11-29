package academy.springacademi;

import com.jayway.jsonpath.DocumentContext;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SpringacademiApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void addData(){
        CashCard cards = new CashCard(null,432D);
        ResponseEntity<Void> create = restTemplate.postForEntity("/cashcards",cards,Void.class);
        assertThat(create.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        ResponseEntity<String> entity = restTemplate.getForEntity("/cashcards", String.class);
        DocumentContext documentContext = JsonPath.parse(entity.getBody());
        int read = documentContext.read("$.length()");
    }

    @Test
    void shouldReturnAllCashCardsWhenListIsRequested() {
        ResponseEntity<String> response = restTemplate.getForEntity("/cashcards", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int cashCardCount = documentContext.read("$.length()");
        assertThat(cashCardCount).isEqualTo(3);

//        JSONArray ids = documentContext.read("$..id");
//        assertThat(ids).containsExactlyInAnyOrder(99, 100, 101);
//
//        JSONArray amounts = documentContext.read("$..amount");
//        assertThat(amounts).containsExactlyInAnyOrder(123.45, 1.00, 150.00);
    }

    @Test
    void shouldReturnAPageOfCashCards() {
        ResponseEntity<String> response = restTemplate.getForEntity("/cashcards?page=0&size=1",String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        JSONArray page = documentContext.read("$[*]");
        assertThat(page.size()).isEqualTo(1);
    }

    @Test
    void shouldNotReturnACashCardWhenUsingBadCredentials() {
        ResponseEntity<String> response = restTemplate
            .withBasicAuth("BAD-USER", "abc123")
            .getForEntity("/cashcards/99", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        response = restTemplate
            .withBasicAuth("sarah1", "BAD-PASSWORD")
            .getForEntity("/cashcards/99", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void getAll(){
        ResponseEntity<String> response = restTemplate.withBasicAuth("sarah1","abc123").getForEntity("/cashcards",String.class);
        System.out.println(response.getBody());
    }

}
