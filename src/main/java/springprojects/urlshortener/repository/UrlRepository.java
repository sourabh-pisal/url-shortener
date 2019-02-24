package springprojects.urlshortener.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import springprojects.urlshortener.model.Url;

import java.util.Optional;

public interface UrlRepository extends MongoRepository<Url, String> {
    Optional<Url> findFirstByShortUrl(String shortUrl);
    Optional<Url> findFirstByLongUrl(String longUrl);
}
