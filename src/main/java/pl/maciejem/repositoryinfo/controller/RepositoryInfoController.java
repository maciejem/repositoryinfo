package pl.maciejem.repositoryinfo.controller;

import pl.maciejem.repositoryinfo.model.RepositoryInfoServiceImpl;
import pl.maciejem.repositoryinfo.model.dto.RepositoryInfo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/repositories")
@AllArgsConstructor
public class RepositoryInfoController {

    private RepositoryInfoServiceImpl repositoryInfoServiceImpl;

    @GetMapping("/{owner}/{repository}")
    public RepositoryInfo getRepositoryInfo(@PathVariable String owner, @PathVariable String repository) {
        return repositoryInfoServiceImpl.getRepositoryInfo(owner, repository);
    }

}
