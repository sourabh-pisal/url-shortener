package springprojects.urlshortener.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(exclude = {"id"})
@ToString(exclude = {"id"})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document
public class Url {
    @Id
    private String id;
    private String shortUrl;
    private String longUrl;
    private Long numberOfHits;
}
