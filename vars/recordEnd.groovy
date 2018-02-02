
def call() {
    def runbld = new co.elastic.Runbld(env.RUNBLD_ID)
    def logfile = currentBuild.rawBuild.getLogFile()
    runbld.call("--cwd", env.WORKSPACE,
                "--event", "build-end",
                "--result", currentBuild.currentResult,
                "--log-file", logfile.getAbsolutePath())
}
