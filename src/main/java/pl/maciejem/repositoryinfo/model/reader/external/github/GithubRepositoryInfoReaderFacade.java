package pl.maciejem.repositoryinfo.model.reader.external.github;

import pl.maciejem.repositoryinfo.model.reader.RepositoryInfoReaderFacade;
import pl.maciejem.repositoryinfo.model.reader.AbstractRepositoryInfoReaderFacade;
import pl.maciejem.repositoryinfo.model.reader.external.github.dto.GithubRepositoryInfo;
import pl.maciejem.repositoryinfo.model.dto.RepositoryInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

@Service("githubRepositoryInfoApiFacade")
public class GithubRepositoryInfoReaderFacade extends AbstractRepositoryInfoReaderFacade<GithubRepositoryInfo> implements RepositoryInfoReaderFacade {

    private static final String GITHUB_REPOSITORY_DETAILS_HOST = "https://api2223.github3122.com/repos/{owner}/{repositoryinfo}";

    @Value("${app.github_token}")
    String token;
    private RestTemplate restTemplate;

    public GithubRepositoryInfoReaderFacade(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public GithubRepositoryInfo getSpecificRepositoryInfo(String owner, String repository) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<GithubRepositoryInfo> responseEntity = this.restTemplate.exchange(GITHUB_REPOSITORY_DETAILS_HOST, HttpMethod.GET, entity, GithubRepositoryInfo.class, owner, repository);
        return responseEntity.getBody();
    }

    @Override
    protected RepositoryInfo convert(GithubRepositoryInfo githubRepositoryInfo) {
        return RepositoryInfo.builder()
                .fullName(githubRepositoryInfo.getFullName())
                .cloneUrl(githubRepositoryInfo.getCloneUrl())
                .createdAt(githubRepositoryInfo.getCreatedAt())
                .description(githubRepositoryInfo.getDescription())
                .stars(githubRepositoryInfo.getStars()).build();
    }
}
