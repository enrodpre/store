package es.enrodpre.store.application.integration;

import org.json.JSONException;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author enrodpre
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UseCaseIntegrationTest {

    @LocalServerPort
    private int port;

    private static TestRestTemplate restTemplate;
    private static HttpEntity<String> entity;

    @BeforeClass
    public static void setUpClass() {
        restTemplate = new TestRestTemplate();
        entity = new HttpEntity<>(null, new HttpHeaders());
    }

    @Test
    public void testGetProduct() throws JSONException {
        String expected = "{\"name\":\"V-NECH BASIC SHIRT\",\"salesUnits\":100,\"shortUnits\":0,\"mediumUnits\":9,\"largeUnits\":4}";
        ResponseEntity<String> response
                = restTemplate.exchange(
                        createURL("/product/1"),
                        HttpMethod.GET, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testProductNotFound() throws JSONException {
        ResponseEntity<String> response = restTemplate.exchange(
                createURL("/product/7"),
                HttpMethod.GET, entity, String.class);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
    }

    @Test
    public void testMalformedURL() throws JSONException {
        ResponseEntity<String> response = restTemplate.exchange(
                createURL("/malformedurl"),
                HttpMethod.GET, entity, String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testMethodNotAllowed() throws JSONException {
        ResponseEntity<String> response = restTemplate.exchange(
                createURL("/product/2"),
                HttpMethod.DELETE, entity, String.class);

        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
    }

    @Test
    public void testWrongParameterProduct() throws JSONException {
        ResponseEntity<String> response = restTemplate.exchange(
                createURL("/product/a"),
                HttpMethod.GET, entity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testWrongParameterProducts() throws JSONException {
        ResponseEntity<String> response = restTemplate.exchange(
                createURL("/products/a/b"),
                HttpMethod.GET, entity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testEqualSort() {
        ResponseEntity<String> response = restTemplate.exchange(
                createURL("/products/0.5/0.5"),
                HttpMethod.GET, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testInequitativeOrder() throws JSONException {
        String expected = "[{\"name\":\"SLOGAN T-SHIRT\",\"salesUnits\":20,\"shortUnits\":5,\"mediumUnits\":2,\"largeUnits\":9},{\"name\":\"PLEATED T-SHIRT\",\"salesUnits\":3,\"shortUnits\":10,\"mediumUnits\":30,\"largeUnits\":25},{\"name\":\"CONTRASTING FABRIC T-SHIRT\",\"salesUnits\":50,\"shortUnits\":9,\"mediumUnits\":9,\"largeUnits\":35},{\"name\":\"RAISED PRINT T-SHIRT\",\"salesUnits\":80,\"shortUnits\":20,\"mediumUnits\":2,\"largeUnits\":20},{\"name\":\"V-NECH BASIC SHIRT\",\"salesUnits\":100,\"shortUnits\":0,\"mediumUnits\":9,\"largeUnits\":4},{\"name\":\"CONTRASTING LACE T-SHIRT\",\"salesUnits\":650,\"shortUnits\":0,\"mediumUnits\":1,\"largeUnits\":0}]";
        ResponseEntity<String> response
                = restTemplate.exchange(
                        createURL("/products/0.7/0.3"),
                        HttpMethod.GET, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testEquitativeOrder() throws JSONException {
        String expected = "[{\"name\":\"SLOGAN T-SHIRT\",\"salesUnits\":20,\"shortUnits\":5,\"mediumUnits\":2,\"largeUnits\":9},{\"name\":\"PLEATED T-SHIRT\",\"salesUnits\":3,\"shortUnits\":10,\"mediumUnits\":30,\"largeUnits\":25},{\"name\":\"CONTRASTING FABRIC T-SHIRT\",\"salesUnits\":50,\"shortUnits\":9,\"mediumUnits\":9,\"largeUnits\":35},{\"name\":\"V-NECH BASIC SHIRT\",\"salesUnits\":100,\"shortUnits\":0,\"mediumUnits\":9,\"largeUnits\":4},{\"name\":\"RAISED PRINT T-SHIRT\",\"salesUnits\":80,\"shortUnits\":20,\"mediumUnits\":2,\"largeUnits\":20},{\"name\":\"CONTRASTING LACE T-SHIRT\",\"salesUnits\":650,\"shortUnits\":0,\"mediumUnits\":1,\"largeUnits\":0}]";
        ResponseEntity<String> response
                = restTemplate.exchange(
                        createURL("/products/0.5/0.5"),
                        HttpMethod.GET, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    private String createURL(String path) {
        return String.format("http://localhost:%s/%s", port, path);
    }

}
