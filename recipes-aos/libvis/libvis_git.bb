DESCRIPTION = "AOS Python VIS Client"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

BRANCH = "master"
SRC_URI = "git://github.com/aoscloud/libvis.git;branch=${BRANCH};protocol=https"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

inherit setuptools3
