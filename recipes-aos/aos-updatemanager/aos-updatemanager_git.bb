DESCRIPTION = "AOS Update Manager"

LICENSE = "CLOSED"

GO_IMPORT = "aos_updatemanager"

BRANCH = "master"
SRCREV = "${AUTOREV}"
SRC_URI = "git://git@gitpct.epam.com/epmd-aepr/${GO_IMPORT}.git;branch=${BRANCH};protocol=ssh"

inherit go

AOS_UM_UPDATE_MODULES ??= "testmodule"
AOS_UM_CUSTOM_UPDATE_MODULES ??= ""

# SM crashes if dynamic link selected, disable dynamic link till the problem is solved
GO_LINKSHARED = ""

# embed version
GO_LDFLAGS += '-ldflags="-X main.GitSummary=`git --git-dir=${S}/src/${GO_IMPORT}/.git describe --tags --always` ${GO_RPATH} ${GO_LINKMODE} -extldflags '${GO_EXTLDFLAGS}'"'

# this flag is requied when GO_LINKSHARED is enabled
# LDFLAGS += "-lpthread"

do_prepare_modules() {
    file="${S}/src/${GO_IMPORT}/updatemodules/modules.go"

    echo 'package updatemodules' > ${file}
    echo 'import (' >> ${file}

    for module in ${AOS_UM_UPDATE_MODULES}; do
        echo "\t_ \"aos_updatemanager/updatemodules/${module}\"" >> ${file}
    done

    for custom_update_module in ${AOS_UM_CUSTOM_UPDATE_MODULES}; do
        echo "\t_ \"aos_updatemanager/${custom_update_module}\"" >> ${file}
    done

    echo ')' >> ${file}
}

RDEPENDS_${PN}-dev += " bash make"
RDEPENDS_${PN}-staticdev += " bash make"

addtask prepare_modules after do_unpack before do_compile