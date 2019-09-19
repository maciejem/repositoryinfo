package pl.maciejem.repositoryinfo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import pl.maciejem.repositoryinfo.model.dto.RepositoryInfo;
import org.assertj.core.api.Assertions;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RepositoryInfoControllerIntegrationTest {

    private static final String EXTERNAL_REST_SERVER_HOST = "https://api.github.com/repos/vmg/redcarpet";
    private static final String APP_HOST = "/repositories/vmg/redcarpet";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RestTemplate externalRestTemplate;

    @Value("classpath:githubresponse.json")
    Resource githubResponse;


    @Test
    public void shouldGetInteralServerErrorResponseWithErrorInfo() throws JSONException {

        //given
        String expectedNotFoundResponse = "{\"status\":\"INTERNAL_SERVER_ERROR\",\"message\":\"500 Internal Server Error\",\"errors\":[\"Github API is not available\"]}";
        //when
        ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(APP_HOST, String.class);
        //then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        JSONAssert.assertEquals(expectedNotFoundResponse, responseEntity.getBody(), false);
    }

    @Test
    public void shouldGetNotFoundResponseWithErrorInfo() throws JSONException {

        //given
        MockRestServiceServer mockServer = mockGithubServerNotFoundResponse();
        String expectedNotFoundResponse = "{\"status\":\"NOT_FOUND\",\"message\":\"404 Not Found\",\"errors\":[\"Bad request to Github API\"]}";
        //when
        ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(APP_HOST, String.class);
        //then
        mockServer.verify();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        JSONAssert.assertEquals(expectedNotFoundResponse, responseEntity.getBody(), false);
    }

    @Test
    public void shouldGetOKRepsonseWithRepositoryInfo() throws IOException {

        //given
        MockRestServiceServer mockServer = mockGithubServerOKResponse();
        //when
        ResponseEntity<RepositoryInfo> responseEntity = this.restTemplate.getForEntity(APP_HOST, RepositoryInfo.class);
        //then
        mockServer.verify();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        RepositoryInfo repositoryInfo = responseEntity.getBody();
        Assertions.assertThat(repositoryInfo).isNotNull();
        Assertions.assertThat(repositoryInfo.getFullName()).isEqualTo("vmg/redcarpet");
        Assertions.assertThat(repositoryInfo.getCloneUrl()).isEqualTo("https://github.com/vmg/redcarpet.git");
        Assertions.assertThat(repositoryInfo.getCreatedAt()).isEqualTo("2011-03-24T22:05:14Z");
        Assertions.assertThat(repositoryInfo.getDescription()).isEqualTo("The safe Markdown parser, reloaded.");
        Assertions.assertThat(repositoryInfo.getStars()).isEqualTo(4446);
    }

    private MockRestServiceServer mockGithubServerInternalServerErrorResponse() {
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(externalRestTemplate);
        mockServer
                .expect(once(), requestTo(EXTERNAL_REST_SERVER_HOST))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));
        return mockServer;
    }

    private MockRestServiceServer mockGithubServerNotFoundResponse() {
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(externalRestTemplate);
        mockServer
                .expect(once(), requestTo(EXTERNAL_REST_SERVER_HOST))
                .andExpect(method(HttpMethod.GET))

                .andRespond(withStatus(HttpStatus.NOT_FOUND));
        return mockServer;
    }

    private MockRestServiceServer mockGithubServerOKResponse() throws IOException {

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(externalRestTemplate);
        mockServer
                .expect(once(), requestTo(EXTERNAL_REST_SERVER_HOST))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(new String(githubResponse.getInputStream().readAllBytes()), MediaType.APPLICATION_JSON));
        return mockServer;
    }
}