
def call(id) {
    stage("Recording build start in runbld") {
        steps {
            script {
                co.elastic.Runbld.call(id, "--event", "build-start")
            }
        }
    }
}
