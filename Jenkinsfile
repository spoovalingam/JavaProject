node {
    agent {
    // Equivalent to "docker build -f Dockerfile.build --build-arg version=1.0.2 ./build/
    dockerfile {
        filename 'Dockerfile_tomcate.docker'
        label 'my-defined-label'
        additionalBuildArgs  '--build-arg version=1.0.1'
        args '-v /tmp:/tmp'
    }
}
}