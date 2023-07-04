(ns git.helpers
  (:require [babashka.process :as p]
            [clojure.string :as str]))

(defn has-upstream?
  "Returns true if current repo has a configured upstream, else false"
  [repo]
  (let [exit (:exit (p/sh {:dir repo} "git" "rev-parse" "--abbrev-ref" "@{upstream}"))]
    (if (zero? exit)
      true false)))

(defn repo?
  "Returns true if current directory is part of a git work tree, else false"
  [path]
  (let [exit (:exit (p/sh {:dir path} "git" "rev-parse" "--is-inside-work-tree"))]
    (if (zero? exit)
      true false)))

(defn repo-root
  "Returns the root directory of the current repository"
  [repo]
  (let [{:keys [exit out]} (p/sh {:dir repo} "git" "rev-parse" "--show-toplevel")]
    (when (zero? exit)
      (str/trim out))))

(defn dirty? [repo]
  (let [exit (:exit (p/sh {:dir repo} "git" "diff-index" "--quiet" "HEAD" "--"))]
    (if (= 1 exit)
      true false)))

(defn has-untracked?
  "Returns true if repo has untracked files, else false"
  [repo]
  (let [{:keys [out exit]} (p/sh {:dir repo} "git" "ls-files" "--other" "--exclude-standard")]
    (cond
      (not (zero? exit)) false
      (str/blank? out) false
      :else true)))
