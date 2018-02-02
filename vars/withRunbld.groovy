
/**
 * Wraps a *scripted* pipeline with calls to runbld
 */
def call(Closure body) {
    def buildId = co.elastic.Runbld.randomId()
    try {
        // log that the job started
        runbld.call(buildId, "--event", "build-start")
        body()
    } finally {
        println "echo FINALLY"
        def logfile = currentBuild.rawBuild.getLogFile()
        runbld.call(buildId,
                    "--cwd", env.WORKSPACE,
                    "--event", "build-end",
                    "--result", currentBuild.currentResult,
                    "--log-file", logfile.getAbsolutePath())
    }
}
