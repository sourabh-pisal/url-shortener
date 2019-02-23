package springprojects.urlshortener.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import springprojects.urlshortener.model.Url;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlRepositoryITest {

    @Autowired
    private UrlRepository repository;

    @Autowired
    private ReactiveMongoOperations operations;

    private static final long NUMBER_OF_HITS = 0L;
    private static final String NULL_ID = null;

    private static final String GOOGLE_SHORT_URL = "https://bit.ly/1bdDlXc";
    private static final String GOOGLE_LONG_URL = "https://google.com";
    private static final Url GOOGLE_URL = new Url(NULL_ID, GOOGLE_SHORT_URL, GOOGLE_LONG_URL, NUMBER_OF_HITS);

    private static final String GITHUB_SHORT_URL = "https://bit.ly/1lEXCbN";
    private static final String GITHUB_LONG_URL = "https://github.com/";
    private static final Url GITHUB_URL = new Url(null, GITHUB_SHORT_URL, GITHUB_LONG_URL, NUMBER_OF_HITS);

    @Before
    public void setUp() {
        operations.dropCollection(Url.class).then().block();
        operations.createCollection(Url.class).then().block();
        repository.saveAll(Flux.just(GOOGLE_URL, GITHUB_URL)).then().block();
    }

    @Test
    public void findFirstByShortUrlTest() {
        Mono<Url> shortUrl = repository.findFirstByShortUrl(GOOGLE_SHORT_URL);
        StepVerifier.create(shortUrl)
                .assertNext(url -> assertThat(GOOGLE_URL, is(url)))
                .expectComplete()
                .verify();
    }

    @Test
    public void findFirstByLongUrlTest() {
        Mono<Url> longUrl = repository.findFirstByLongUrl(GITHUB_LONG_URL);
        StepVerifier.create(longUrl)
                .assertNext(url -> assertThat(GITHUB_URL, is(url)))
                .expectComplete()
                .verify();
    }

    @After
    public void tearDown() {
        repository.deleteAll().then().block();
        operations.dropCollection(Url.class).then().block();
    }
}