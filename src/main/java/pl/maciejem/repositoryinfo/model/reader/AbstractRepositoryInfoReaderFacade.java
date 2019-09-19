package pl.maciejem.repositoryinfo.model.reader;

import pl.maciejem.repositoryinfo.model.dto.RepositoryInfo;

public abstract class AbstractRepositoryInfoReaderFacade<T> {

    public RepositoryInfo getRepositoryInfo(String owner, String repository) {
        T t = getSpecificRepositoryInfo(owner, repository);
        return convert(t);
    }

    protected abstract RepositoryInfo convert(T t);

    public abstract T getSpecificRepositoryInfo(String owner, String repository);
}
