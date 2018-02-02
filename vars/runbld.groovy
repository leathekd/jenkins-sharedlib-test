
// dunno if this is allowed or will work cross-node

def runbld = new co.elastic.Runbld()

def buildId() {
    runbld.buildId
}

def recordStart() {
    runbld.call("--event", "build-start")
}

def recordEnd() {
    def logfile = currentBuild.rawBuild.getLogFile()
    runbld.call("--cwd", env.WORKSPACE,
                "--event", "build-end",
                "--result", currentBuild.currentResult,
                "--log-file", logfile.getAbsolutePath())

}
