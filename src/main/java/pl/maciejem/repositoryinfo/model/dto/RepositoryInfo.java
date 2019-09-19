package pl.maciejem.repositoryinfo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public final class RepositoryInfo {
    private final String fullName;
    private final String description;
    private final String cloneUrl;
    private final Integer stars;
    private final String createdAt;
}
