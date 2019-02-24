package springprojects.urlshortener.dto;

import lombok.*;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UrlDto {
    private String shortUrl;
    private String longUrl;
    private long numberOfHits;
}
