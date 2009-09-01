#!/bin/sh
exec scala -cp /home/glen/src/gitwatch/lib/jgit.jar $0 $@
!#
import java.io.File
import org.spearce.jgit.lib.{Commit => GitCommit, Repository}

val repo = new Repository(new File(args(0)))
val head = repo.mapCommit("HEAD")

def history(c: GitCommit): List[GitCommit] = {
  c :: c.getParentIds.take(1).map(repo.mapCommit(_)).flatMap(history(_)).toList
}

println(history(head).take(args(1).toInt).map(_.getMessage).mkString(""))
