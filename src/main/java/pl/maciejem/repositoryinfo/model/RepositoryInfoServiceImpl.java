package pl.maciejem.repositoryinfo.model;

import pl.maciejem.repositoryinfo.model.reader.RepositoryInfoReaderFacade;
import pl.maciejem.repositoryinfo.model.dto.RepositoryInfo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RepositoryInfoServiceImpl implements RepositoryInfoService {

    @Qualifier("githubRepositoryInfoApiFacade")
    private RepositoryInfoReaderFacade githubRepositoryInfoReaderFacade;

    public RepositoryInfo getRepositoryInfo(String owner, String repository) {
        return githubRepositoryInfoReaderFacade.getRepositoryInfo(owner, repository);
    }
}
