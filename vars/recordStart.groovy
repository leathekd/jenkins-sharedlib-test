
def call() {
    def runbld = new co.elastic.Runbld(env.RUNBLD_ID)
    runbld.call("--event", "build-start")
}
