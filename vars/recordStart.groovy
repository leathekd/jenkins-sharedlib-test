
def call(id) {
    co.elastic.Runbld.call(id, "--event", "build-start")
}
