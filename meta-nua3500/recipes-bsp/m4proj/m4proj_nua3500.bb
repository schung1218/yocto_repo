
DESCRIPTION = "NUA3500 M4 BSP suppporting nua3500 ev boards."
DEPENDS = " gcc-arm-none-eabi-native nu-eclipse-native "

inherit deploy
#require recipes-bsp/includes/paths.inc

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://Readme.pdf;md5=7d0111ef81ddfefe54601cebe12be7c4"

SRCREV="${AUTOREV}"

SRC_URI = "git://github.com/OpenNuvoton/M480BSP.git;protocol=https;branch=master"



PV = "M4-BSP"
S = "${WORKDIR}/git"
B =  "${WORKDIR}/build"
# for external source support with devtools
#EXTERNALSRC_BUILD_pn-${PN} = "${WORKDIR}/build"

#inherit systemd

#do_configure() {
#	echo  ${S}
#	echo  ${BSP_RECIPE}
#}

export CROSS_COMPILE = "${RECIPE_SYSROOT_NATIVE}/${datadir}/gcc-arm-none-eabi/bin/arm-none-eabi-"
export GCC_PATH = "${RECIPE_SYSROOT_NATIVE}/${datadir}/gcc-arm-none-eabi/bin"
export NUECLIPSE = "${RECIPE_SYSROOT_NATIVE}/${datadir}/nu-eclipse"
export DISPLAY= ":99"
#nu-eclipse

python do_compile() {
    import os
    import subprocess
    import shutil
    import fnmatch
    os.environ["PATH"] += os.pathsep + d.getVar('GCC_PATH',1)
    f = open('gcc_log.txt', "w+")
    root = os.getcwd()
    os.chdir(d.getVar('S',1))
    f.write("PATH="+os.environ["PATH"]+"\n")
    f.write("S="+d.getVar('S',1)+"\n")
    f.write("NUECLIPSE="+d.getVar('NUECLIPSE',1))
    f.write("\n======= m480-bsp =======\n")
    for dirPath, dirNames, fileNames in os.walk("SampleCode/StdDriver"):
        for file in fnmatch.filter(fileNames, '*.cproject'):
            if not os.path.isdir(dirPath+"/Release"):
                f.write("dirPath="+dirPath+"\n")
                if not os.path.isdir("Temp"):
                    os.mkdir("Temp")
                else:
                    shutil.rmtree("Temp")
                    os.mkdir("Temp")
                cmd = "Xvfb :99>/dev/null & " +d.getVar('NUECLIPSE',1)+"/eclipse/eclipse -nosplash --launcher.suppressErrors -application org.eclipse.cdt.managedbuilder.core.headlessbuild -data Temp -cleanBuild all -import "+dirPath + "\n"
                f.write("cmd="+cmd+"\n")
                f.flush()
                retcode = subprocess.call(cmd,shell=True,stdout=f)
                shutil.rmtree("Temp")
    os.chdir(root)
    f.close()
}

python do_install() {
    import os
    import subprocess
    import shutil
    import fnmatch

    f = open('install_log.txt', "w+")
    root = os.getcwd()
    os.chdir(d.getVar('S',1))
    f.write("S="+d.getVar('S',1)+"\n")
    f.write("NUECLIPSE="+d.getVar('NUECLIPSE',1)+"\n")
    #f.write("PDK_INSTALL_DIR_RECIPE="+d.getVar('PDK_INSTALL_DIR_RECIPE',1)+"\n")
    f.write("======= m480-bsp =======\n")
    for dirPath, dirNames, fileNames in os.walk("SampleCode/StdDriver"):
        for file in fnmatch.filter(fileNames, '*.elf'):
            cmd = "cp "+ dirPath + "/" + file +" "+ d.getVar('D',1)
            f.write("cmd="+cmd+"\n")
            subprocess.call(cmd,shell=True,stdout=f)
    os.chdir(root)
    f.close()
}

do_deploy() {
	echo ${S}
}

#FILES_${PN} += "${PDK_INSTALL_DIR_RECIPE}/packages"

#INSANE_SKIP_${PN} += "installed-vs-shipped"
#ALLOW_EMPTY_${PN} = "1"

#INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
#INHIBIT_PACKAGE_STRIP = "1"
#INHIBIT_SYSROOT_STRIP = "1"

#deltask do_populate_sysroot do_prepare_recipe_sysroot
#deltask do_prepare_recipe_sysroot

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(nua3500)"


