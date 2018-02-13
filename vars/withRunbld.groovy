
/**
 * Wraps a *scripted* pipeline with calls to runbld
 */
def call(Closure body) {
    def buildId = co.elastic.Runbld.randomId()
    try {
        println InetAddress.localHost.hostName
        co.elastic.Runbld.call(buildId, "--event", "build-start")
        body()
    } finally {
        println InetAddress.localHost.hostName
        def logfile = currentBuild.rawBuild.getLogFile()
        co.elastic.Runbld.call(buildId,
                               "--cwd", env.WORKSPACE,
                               "--event", "build-end",
                               "--result", currentBuild.currentResult,
                               "--log-file", logfile.getAbsolutePath())
    }
}
