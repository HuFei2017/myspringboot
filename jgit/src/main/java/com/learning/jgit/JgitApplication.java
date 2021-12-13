package com.learning.jgit;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SpringBootApplication
public class JgitApplication {

    public static void main(String[] args) throws Exception {
        //SpringApplication.run(JgitApplication.class, args);

        //远程获取所有分支
        UsernamePasswordCredentialsProvider pro = new UsernamePasswordCredentialsProvider("fei.hu@ronds.com.cn", "ling1008hu1996");
        Collection<Ref> refList = Git.lsRemoteRepository().setRemote("http://172.16.0.8:30000/jiasd/datamanagerplatform.git").setCredentialsProvider(pro).call();

        for (Ref ref : refList) {
            System.out.println(ref.getName());
        }

    }

    //凭证管理
    public static CredentialsProvider createCredential(String userName, String password) {
        return new UsernamePasswordCredentialsProvider(userName, password);
    }

    //git-clone
    public static Git fromCloneRepository(String repoUrl, String cloneDir, CredentialsProvider provider) throws GitAPIException {
        return Git.cloneRepository()
                .setCredentialsProvider(provider)
                .setURI(repoUrl)
                .setDirectory(new File(cloneDir)).call();
    }

    //git-commit
    public static void commit(Git git, String message, CredentialsProvider provider) throws GitAPIException {
        git.add().addFilepattern(".").call();
        git.commit()
                .setMessage(message)
                .call();
    }

    //git-push
    public static void push(Git git, String branch, CredentialsProvider provider) throws GitAPIException, IOException {
        if (branch == null) {
            branch = git.getRepository().getBranch();
        }
        git.push()
                .setCredentialsProvider(provider)
                .setRemote("origin").setRefSpecs(new RefSpec(branch)).call();
    }

    //git-pull
    public static void pull(Git git, String branch, CredentialsProvider provider) throws GitAPIException, IOException {
        if (branch == null) {
            branch = git.getRepository().getBranch();
        }
        git.pull()
                .setCredentialsProvider(provider)
                .setRemoteBranchName(branch).call();
    }

    //读取已有仓库
    public static Repository getRepositoryFromDir(String dir) throws IOException {
        return new FileRepositoryBuilder()
                .setGitDir(Paths.get(dir, ".git").toFile())
                .build();
    }

    //读取仓库日志
    public static List<String> getLogsSinceCommit(Repository repository, String branch, String commit) throws IOException {
        if (branch == null) {
            branch = repository.getBranch();
        }
        Ref head = repository.findRef("refs/heads/" + branch);
        List<String> commits = new ArrayList<>();
        if (head != null) {
            try (RevWalk revWalk = new RevWalk(repository)) {
                revWalk.markStart(revWalk.parseCommit(head.getObjectId()));
                for (RevCommit revCommit : revWalk) {
                    if (revCommit.getId().getName().equals(commit)) {
                        break;
                    }
                    commits.add(revCommit.getFullMessage());
                    System.out.println("\nCommit-Message: " + revCommit.getFullMessage());
                }
                revWalk.dispose();
            }
        }

        return commits;
    }

}
