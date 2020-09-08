SUMMARY = "Eclipse IDE for Nuvoton"
LICENSE = "BSD"
#LICENSE = "GPL-3.0-with-GCC-exception & GPLv3"
LIC_FILES_CHKSUM = "file://uninstall.sh;md5=5bbc99690ef3452ab8e1fc6744c1322c"

inherit native

SRC_URI = "https://www.nuvoton.com/opencms/resource-download.jsp?tp_GUID=SW1120200401185819;downloadfilename=nu-eclipse.tar.gz;name=nu-eclipse"
SRC_URI[nu-eclipse.md5sum] = "609696413c95dd675a7809cb67d898c0"
SRC_URI[nu-eclipse.sha256sum] = "d173a3d9da198f1ab68deb826a9b54d46d1b41ca5520302498affc97f88c4e9a"

#SRC_URI = "https://www.nuvoton.com/opencms/resource-download.jsp?tp_GUID=SW1120200401185819;downloadfilename=nu-eclipse.tar.gz"
#SRC_URI[md5sum] = "609696413c95dd675a7809cb67d898c0"
#SRC_URI[sha256sum] = "d173a3d9da198f1ab68deb826a9b54d46d1b41ca5520302498affc97f88c4e9a"


S = "${WORKDIR}/NuEclipse_V1.01.016_Linux_Setup"

do_install() {
	install -d ${D}/${datadir}/nu-eclipse
	cp -r ${S}/. ${D}${datadir}/nu-eclipse
}

#INSANE_SKIP_${PN} = "already-stripped file-rdeps"

#FILES_${PN} += "${datadir}/nu-eclipse"

#ALLOW_EMPTY_${PN} = "1"


#FILES_${PN} += "${NUECLIPSE_RECIPE}/*"
#INSANE_SKIP_${PN} = "already-stripped"
INSANE_SKIP_${PN} = "already-stripped file-rdeps sysroot_strip"
LLOW_EMPTY_${PN} = "1"

INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"

