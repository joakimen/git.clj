(ns git.branch
  (:require [git.sh :refer [sh]]))

(defn current
  "Returns the name of the current branch"
  [repo]
  (sh "git" "-C" repo "branch" "--show-current"))
