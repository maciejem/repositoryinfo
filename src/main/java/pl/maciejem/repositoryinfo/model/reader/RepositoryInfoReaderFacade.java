package pl.maciejem.repositoryinfo.model.reader;

import pl.maciejem.repositoryinfo.model.dto.RepositoryInfo;

public interface RepositoryInfoReaderFacade {
     RepositoryInfo getRepositoryInfo(String owner, String repository);
}
