package springprojects.urlshortener.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springprojects.urlshortener.dto.UrlDto;
import springprojects.urlshortener.service.UrlService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class UrlController {

    private final UrlService service;

    @PostMapping(value = "/generate/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UrlDto generateShortUrl(@RequestBody String longUrl) {
        return service.createShortUrl(longUrl);
    }

    @GetMapping(value = "/{short_url}")
    public void redirect(@PathVariable("short_url") String shortUrl, HttpServletResponse response) throws Exception {
        try {
            UrlDto redirectUrl = service.getRedirectUrl(shortUrl);
            response.sendRedirect(redirectUrl.getLongUrl());
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @GetMapping(value = "/dashboard",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UrlDto> getDashboard() {
        return service.getDashboard();
    }
}
