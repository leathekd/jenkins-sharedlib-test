
// dunno if this is allowed or will work cross-node

def buildId() {
    return (new co.elastic.Runbld()).buildId
}

def recordStart(id) {
    def bld = new co.elastic.Runbld(id)
    bld.call("--event", "build-start")
}

def recordEnd(id) {
    def bld = new co.elastic.Runbld(id)
    def logfile = currentBuild.rawBuild.getLogFile()
    bld.call("--cwd", env.WORKSPACE,
                "--event", "build-end",
                "--result", currentBuild.currentResult,
                "--log-file", logfile.getAbsolutePath())
}
