package pl.maciejem.repositoryinfo.model.reader.external.github.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public final class GithubRepositoryInfo {

    private final String fullName;

    private final String description;

    private final String cloneUrl;

    private final Integer stars;

    private final String createdAt;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public GithubRepositoryInfo(@JsonProperty("full_name") String fullName, @JsonProperty("description") String description,
                                @JsonProperty("clone_url") String cloneUrl, @JsonProperty("stargazers_count") Integer stars,
                                @JsonProperty("created_at") String createdAt) {
        this.fullName = fullName;
        this.description = description;
        this.cloneUrl = cloneUrl;
        this.stars = stars;
        this.createdAt = createdAt;
    }
}