package springprojects.urlshortener.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springprojects.urlshortener.component.ServerUrl;
import springprojects.urlshortener.dto.UrlDto;
import springprojects.urlshortener.model.Url;
import springprojects.urlshortener.repository.UrlRepository;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.hash.Hashing.murmur3_32;
import static java.lang.String.format;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class UrlService {
    private static final String SHORT_URL_NOT_FOUND_ERROR_MSG = "URL Not Found: %s";
    private static final long DEFAULT_NUMBER_OF_HITS = 0L;

    private final UrlRepository repository;
    private final ServerUrl serverUrl;

    private final ModelMapper modelMapper = new ModelMapper();

    public UrlDto createShortUrl(String longUrl) {
        Url url = repository.findFirstByLongUrl(longUrl)
                .orElseGet(() ->
                        repository.save(new Url(null, generateShortUrl(longUrl), longUrl, DEFAULT_NUMBER_OF_HITS)));
        url.setShortUrl(serverUrl.getUrl() + url.getShortUrl());
        return modelMapper.map(url, UrlDto.class);
    }

    public UrlDto getRedirectUrl(String shortUrl) {
        Url url = repository.findFirstByShortUrl(shortUrl)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                format(SHORT_URL_NOT_FOUND_ERROR_MSG,
                                        shortUrl)));
        url.setNumberOfHits(url.getNumberOfHits() + 1);
        repository.save(url);
        return modelMapper.map(url, UrlDto.class);
    }

    public List<UrlDto> getDashboard() {
        return repository.findAll()
                .stream()
                .map(url -> {
                    url.setShortUrl(serverUrl.getUrl() + url.getShortUrl());
                    return modelMapper.map(url, UrlDto.class);
                }).collect(Collectors.toList());
    }

    private String generateShortUrl(String longUrl) {
        return murmur3_32().hashString(longUrl, StandardCharsets.UTF_8).toString();
    }
}
