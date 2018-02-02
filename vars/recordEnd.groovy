
def call(id) {
    def logfile = currentBuild.rawBuild.getLogFile()
    co.elastic.Runbld.call(id,
                           "--cwd", env.WORKSPACE,
                           "--event", "build-end",
                           "--result", currentBuild.currentResult,
                           "--log-file", logfile.getAbsolutePath())
}
