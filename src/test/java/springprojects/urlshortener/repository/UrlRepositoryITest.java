package springprojects.urlshortener.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import springprojects.urlshortener.model.Url;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlRepositoryITest {

    @Autowired
    private UrlRepository repository;

    private static final String SHORT_URL = "http://localhost:8080/3a4770d4";
    private static final String LONG_URL = "https://www.google.com/";
    private static final Url GOOGLE_URL = new Url(null, SHORT_URL, LONG_URL, 0L);

    @Before
    public void setUp() {
        repository.deleteAll();
        repository.save(GOOGLE_URL);
    }

    @Test
    public void findFirstByShortUrlTest() {
        Optional<Url> optionalResult = repository.findFirstByShortUrl(SHORT_URL);
        assertThat(optionalResult.isPresent(), is(true));
        assertThat(optionalResult.get(), is(GOOGLE_URL));
    }

    @Test
    public void findFirstByLongUrlTest() {
        Optional<Url> optionalResult = repository.findFirstByLongUrl(LONG_URL);
        assertThat(optionalResult.isPresent(), is(true));
        assertThat(optionalResult.get(), is(GOOGLE_URL));
    }

    @After
    public void tearDown() {
        repository.deleteAll();
    }
}