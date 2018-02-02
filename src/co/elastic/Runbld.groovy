package co.elastic

class Runbld implements Serializable {
    static String randomId() {
        def id = (java.util.UUID.randomUUID()).toString().split("-")[0]
        def date = (new Date()).format("yyyyMMddHHmmss")
        return "${id}-${date}"
    }

    static def isAvailable() {
        def path = ""
        if (System.properties['os.name'].toLowerCase().contains('windows')) {
            path = "where.exe runbld".execute().text
        } else {
            path = "which runbld".execute().text
        }
        return "" != path
    }

    static def call(String id, String... args) {
        try {
            if(isAvailable()) {
                def runbldProc = "runbld --build-id '${id}' ${args.join(' ')}".execute()
                if( 0 == runbldProc.exitValue() ) {
                    return runbldProc.text
                } else {
                    // TODO: print the exit code, stderr/stdout

                }
            } else {
                println "runbld not found, skipping..."
            }
        } catch(ex) {
            // Don't let runbld kill the build
            // TODO: print debug info
        }
    }
}
