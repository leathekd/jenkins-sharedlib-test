
// dunno if this is allowed or will work cross-node

@Field
String bld = new co.elastic.Runbld()

def recordStart() {
    stage ('Record build start with runbld') {
        steps {
            script {
                bld.call("--event", "build-start")
            }
        }
    }
}

def recordEnd() {
    stage ('Record build done with runbld') {
        steps {
            script {
                def logfile = currentBuild.rawBuild.getLogFile()
                bld.call("--cwd", env.WORKSPACE,
                         "--event", "build-end",
                         "--result", currentBuild.currentResult,
                         "--log-file", logfile.getAbsolutePath())
            }
        }
    }
}
