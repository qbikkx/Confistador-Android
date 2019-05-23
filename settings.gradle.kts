include(":app", ":core-utils", "core-ui")

include(":conferences-feature-core")
project(":conferences-feature-core").projectDir = file("conferences/conferences-feature-core")

include(":conferences-feature")
project(":conferences-feature").projectDir = file("conferences/conferences-feature")

include(":conferences-feature-local")
project(":conferences-feature-local").projectDir = file("conferences/conferences-feature-local")

include(":conferences-feature-remote")
project(":conferences-feature-remote").projectDir = file("conferences/conferences-feature-remote")

include(":conferences-feature-domain")
project(":conferences-feature-domain").projectDir = file("conferences/conferences-feature-domain")

include(":conferences-feature-repository")
project(":conferences-feature-repository").projectDir = file("conferences/conferences-feature-repository")
