
// dunno if this is allowed or will work cross-node

def bld = new co.elastic.Runbld()

def buildId() {
    bld.buildId
}

def recordStart() {
    bld.call("--event", "build-start")
}

def recordEnd() {
    def logfile = currentBuild.rawBuild.getLogFile()
    bld.call("--cwd", env.WORKSPACE,
                "--event", "build-end",
                "--result", currentBuild.currentResult,
                "--log-file", logfile.getAbsolutePath())
}
