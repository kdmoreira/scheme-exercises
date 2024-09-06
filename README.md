# Scheme exercises: software design for flexibility
Examples and exercises from the book, in scheme and clojure.
## Requirements
### MIT/GNU Scheme
https://www.gnu.org/software/mit-scheme/
### Clojure
https://clojure.org/guides/install_clojure
### Babashka
https://github.com/babashka/babashka#installation
## Eval
- Scheme: the `scheme` command will start a REPL, where you can paste code. If you use VSCode, you can execute the `run scheme` task in the .scm files.
- Clojure:
Start a REPL with project type 'Babashka' and eval the code in the .clj files.
## Tests
Use babashka to run the clojure tests:
```bash
bb test:bb
```