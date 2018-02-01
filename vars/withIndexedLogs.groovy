def call(Closure body) {
    try {
        body()
    }
    finally {
        sh "echo FINALLY"
        def firstLine
        def logfile = currentBuild.rawBuild.getLogFile()
        logfile.withReader { firstLine = it.readLine() }
        sh "echo ${logfile.getAbsolutePath()}"
        sh "echo \"${firstLine}\""
    }
}
