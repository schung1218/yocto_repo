SUMMARY = "This is a python nuwriter for nua3500 tool "

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://README;md5=01f0f27c23ca2525f860a07ca3dbfc7a"

inherit deploy native pypi setuptools3

SRCREV= "master"

SRC_URI = "git://github.com/NUA3500/nuwriter.git;protocol=https;branch=master"
S = "${WORKDIR}/git"
B =  "${WORKDIR}/build"

PACKAGES = ""
#inherit pypi setuptools3
do_compile[noexec] = "1"
do_install[noexec] = "1"
#do_package_write_rpm[noexec] = "1"

DEPENDS += " \
    libusb1-native \
    pyinstaller-native \
    pyinstaller-hooks-contrib-native \
    python3-altgraph-native \
    python3-pyusb-native \
    python3-pycrypto-native \
    python3-crcmod-native \
    python3-tqdm-native \
    python3-crcmod-native \
    python3-ecdsa-native \
    python3-six-native \
"

BBCLASSEXTEND = "native nativesdk"

do_compile(){
    pyinstaller --clean --win-private-assemblies ${S}/nuwriter.py -D -n nuwriter -y --distpath ${B}
}

do_deploy() {
    install -d ${DEPLOYDIR}/${BOOT_TOOLS}/nuwriter-${PV}
    cp -rf ${B}/nuwriter/* ${DEPLOYDIR}/${BOOT_TOOLS}/nuwriter-${PV}/
}

FILES_${PN} = ""
#addtask do_run_testsuite before do_install after do_configure
#addtask do_deploy after do_run_testsuite
addtask deploy after do_compile
#INSANE_SKIP_${PN}_append = "already-stripped" 
