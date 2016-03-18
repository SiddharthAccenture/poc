package com.acenture.poc;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.treewalk.TreeWalk;

public class GitHubTest {
	public static void main(String[] args) throws InvalidRemoteException,
			TransportException, GitAPIException {
		FileRepositoryBuilder builder = new FileRepositoryBuilder();

		try {
			Repository repository = builder
					.setGitDir(
							new File("C:\\Users\\sidhu\\Documents\\MyProject\\.git"))
					.readEnvironment().findGitDir().build();

			listRepositoryContents(repository);

			// Close repo
			repository.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void listRepositoryContents(Repository repository)
			throws IOException, InvalidRemoteException, TransportException,
			GitAPIException {

		Ref head = repository.getRef("HEAD");

		// a RevWalk allows to walk over commits based on some filtering that is
		// defined
		RevWalk walk = new RevWalk(repository);

		RevCommit commit = walk.parseCommit(head.getObjectId());
		RevTree tree = commit.getTree();
		System.out.println("Having tree: " + tree);

		// now use a TreeWalk to iterate over all files in the Tree recursively
		// you can set Filters to narrow down the results if needed
		TreeWalk treeWalk = new TreeWalk(repository);
		treeWalk.addTree(tree);
		treeWalk.setRecursive(true);
		while (treeWalk.next()) {
			System.out.println("found: " + treeWalk.getPathString());
		}

		try (Git git2 = new Git(repository)) {
			CredentialsProvider cp = new UsernamePasswordCredentialsProvider(
					"SiddharthAccenture", "Abcd@2018");
			Iterable<PushResult> s = git2.push().setCredentialsProvider(cp).setPushAll().setRemote("https://github.com/SiddharthAccenture/poc.git").call();
			System.out.println(s);
		      
		}
		
	}
}
