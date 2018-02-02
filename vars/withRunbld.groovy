def hasRunbld() {
    def path = ""
    if (System.properties['os.name'].toLowerCase().contains('windows')) {
        path = "where.exe runbld".execute().text
    } else {
        path = "which runbld".execute().text
    }
    return "" != path
}

def runbld(String... args) {
    try {
        if(hasRunbld()) {
            def runbldProc = "runbld ${args.join(' ')}".execute()
            if( 0 == runbldProc.exitValue() ) {
                return runbldProc.text
            } else {
                // TODO: print the exit code, stderr/stdout

            }
        } else {
            println "runbld not found, skipping..."
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
        runbld("--build-id", buildId, "--event", "build-start")
        body()
    } finally {
        println "echo FINALLY"
        def logfile = currentBuild.rawBuild.getLogFile()
        runbld("--build-id", buildId,
               "--cwd", env.WORKSPACE,
               "--event", "build-end",
               "--result", currentBuild.currentResult,
               "--log-file", logfile.getAbsolutePath())
    }
}
