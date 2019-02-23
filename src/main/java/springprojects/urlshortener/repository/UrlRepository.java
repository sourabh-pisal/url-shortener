package springprojects.urlshortener.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import springprojects.urlshortener.model.Url;

@Repository
public interface UrlRepository extends ReactiveCrudRepository<Url, String> {

    Mono<Url> findFirstByShortUrl(String shortUrl);

    Mono<Url> findFirstByLongUrl(String longUrl);
}
