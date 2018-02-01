def existsInPath(String exec) {
    boolean existsInPath = Stream.of(System.getenv("PATH")
                                     .split(Pattern.quote(File.pathSeparator)))
        .map(Paths::get)
        .anyMatch(path -> Files.exists(path.resolve(exec)));
    return existsInPath
}

def runbld(String... args) {
    try {
        if(existsInPath("runbld")) {
            def runbldProc = "runbld ${args.join(" ")}".execute()
            if( 0 == runbldProc.exitValue() ) {
                return runbldProc.text
            } else {
                // TODO: print the exit code, stderr/stdout
                
            }
        }
    } catch(ex) {
        // Don't let runbld kill the build
        // TODO: print debug info
    }
}

def call(Closure body) {
    def buildId = runbld("--generateId")
    try {
        // log that the job started
        runbld("--build-id", buildId, "--event" "build-start")
        body()
    } finally {
        sh "echo FINALLY"
        def logfile = currentBuild.rawBuild.getLogFile()
        runbld("--build-id", buildId,
               "--event", "build-end",
               "--result", currentBuild.currentResult,
               "--log-file", logfile.getAbsolutePath())
    }
}
