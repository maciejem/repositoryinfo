package pl.maciejem.repositoryinfo.model;

import pl.maciejem.repositoryinfo.model.dto.RepositoryInfo;

public interface RepositoryInfoService {
    RepositoryInfo getRepositoryInfo(String owner, String repository);
}