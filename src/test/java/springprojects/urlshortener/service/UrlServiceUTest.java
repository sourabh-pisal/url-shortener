package springprojects.urlshortener.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import springprojects.urlshortener.component.ServerUrl;
import springprojects.urlshortener.dto.UrlDto;
import springprojects.urlshortener.model.Url;
import springprojects.urlshortener.repository.UrlRepository;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UrlServiceUTest {
    @Mock
    private UrlRepository repository;
    @Mock
    private ServerUrl serverUrl;

    @InjectMocks
    private UrlService service;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static final String GOOGLE_LONG_URL = "https://www.google.com/";
    private static final String GOOGLE_SHORT_URL = "cac87a2c";

    @Before
    public void setUp() {
        when(serverUrl.getUrl()).thenReturn("");
    }

    @After
    public void tearDown() {
        clearInvocations(serverUrl);
        clearInvocations(repository);
    }

    @Test
    public void givenShortUrlWhenPresentThenShouldReturnUrl() {
        UrlDto expectedResult = new UrlDto(GOOGLE_SHORT_URL, GOOGLE_LONG_URL, 1L);
        //Given
        Url googleUrl = new Url(null, GOOGLE_SHORT_URL, GOOGLE_LONG_URL, 0L);
        when(repository.findFirstByShortUrl(GOOGLE_SHORT_URL))
                .thenReturn(Optional.of(googleUrl));

        //When
        UrlDto actualResult = service.getRedirectUrl(GOOGLE_SHORT_URL);

        //Then
        assertThat(actualResult, is(expectedResult));
    }

    @Test
    public void givenShortUrlWhenPresentThenShouldUpdateNumberOfHitsByOne() {
        final long expectedResult = 1L;
        //Given
        Url googleUrl = new Url(null, GOOGLE_SHORT_URL, GOOGLE_LONG_URL, 0L);
        when(repository.findFirstByShortUrl(GOOGLE_SHORT_URL))
                .thenReturn(Optional.of(googleUrl));

        //When
        service.getRedirectUrl(GOOGLE_SHORT_URL);

        //Then
        ArgumentCaptor<Url> argument = ArgumentCaptor.forClass(Url.class);
        Mockito.verify(repository).save(argument.capture());
        Url actualValue = argument.getValue();
        assertThat(expectedResult, is(actualValue.getNumberOfHits()));
    }

    @Test
    public void givenShortUrlWhenNotPresentThenShouldThrowIllegalArgumentException() {
        //Given
        when(repository.findFirstByShortUrl(GOOGLE_SHORT_URL))
                .thenReturn(Optional.empty());

        //Then
        expectedException.expect(IllegalArgumentException.class);

        //When
        service.getRedirectUrl(GOOGLE_SHORT_URL);
    }

    @Test
    public void givenLongUrlWhenNotPresentThenShouldCreateNewUrl() {
        //Given
        Url googleUrl = new Url(null, GOOGLE_SHORT_URL, GOOGLE_LONG_URL, 0L);
        when(repository.findFirstByLongUrl(GOOGLE_LONG_URL)).thenReturn(Optional.empty());
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        //When
        service.createShortUrl(GOOGLE_LONG_URL);

        //Then
        verify(repository, times(1)).save(googleUrl);
    }

    @Test
    public void giveLongUrlWhenPresentThenShouldNotCreateNewUrl() {
        //Given
        Url googleUrl = new Url(null, GOOGLE_SHORT_URL, GOOGLE_LONG_URL, 0L);
        when(repository.findFirstByLongUrl(GOOGLE_LONG_URL)).thenReturn(Optional.of(googleUrl));

        //When
        service.createShortUrl(GOOGLE_LONG_URL);

        //Then
        verify(repository, times(0)).save(googleUrl);
    }

    public void givenLongUrlWhenPresentThenShouldReturnUrl() {
        //Given
        UrlDto expectedUrl = new UrlDto(GOOGLE_SHORT_URL, GOOGLE_LONG_URL, 0L);
        Url googleUrl = new Url(null, GOOGLE_SHORT_URL, GOOGLE_LONG_URL, 0L);
        when(repository.findFirstByLongUrl(GOOGLE_LONG_URL)).thenReturn(Optional.of(googleUrl));

        //When
        UrlDto actualUrl = service.createShortUrl(GOOGLE_LONG_URL);

        //Then
        assertThat(actualUrl, is(expectedUrl));
    }

    @Test
    public void whenUrlPresentThenReturnListOfUrl() {
        //Given
        List<UrlDto> expectedList = List.of(new UrlDto(GOOGLE_SHORT_URL, GOOGLE_LONG_URL, 0L));
        Url googleUrl = new Url(null, GOOGLE_SHORT_URL, GOOGLE_LONG_URL, 0L);
        when(repository.findAll()).thenReturn(List.of(googleUrl));

        //When
        List<UrlDto> actualList = service.getDashboard();

        //Then
        assertThat(actualList, is(expectedList));
    }
}