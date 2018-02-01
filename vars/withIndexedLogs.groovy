def call(Closure body) {
    try {
        body()
    }
    finally {
        def logfile = currentBuild.rawBuild.getLogFile()
        sh "echo ${logfile.getAbsolutePath()}"
    }
}
