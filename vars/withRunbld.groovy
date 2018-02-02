
/**
 * Wraps a *scripted* pipeline with calls to runbld
 */
def call(Closure body) {
    def runbld = new co.elastic.Runbld()
    try {
        // log that the job started
        runbld.call("--event", "build-start")
        body()
    } finally {
        println "echo FINALLY"
        def logfile = currentBuild.rawBuild.getLogFile()
        runbld.call("--cwd", env.WORKSPACE,
                    "--event", "build-end",
                    "--result", currentBuild.currentResult,
                    "--log-file", logfile.getAbsolutePath())
    }
}
