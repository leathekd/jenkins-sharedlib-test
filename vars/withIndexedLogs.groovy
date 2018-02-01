def call(Closure body) {
    try {
        body()
    }
    finally {
        sh "echo FINALLY"
        def logfile = currentBuild.rawBuild.getLogFile()
        sh "echo ${logfile.getAbsolutePath()}"
        sh "echo ${currentBuild.rawBuild.getLogReader().readLine()}"
    }
}
