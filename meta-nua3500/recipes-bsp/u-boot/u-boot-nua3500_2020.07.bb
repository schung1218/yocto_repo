
DESCRIPTION = "NUA3500 U-Boot suppporting nua3500 ev boards."
#SECTION = "bootloaders"
require recipes-bsp/u-boot/u-boot.inc

PROVIDES += "u-boot"
DEPENDS += "dtc-native bc-native flex-native bison-native"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"

#UBOOT_SRC ?= "git://github.com/u-boot/u-boot.git;protocol=https"
UBOOT_SRC ?= "git://github.com/schung1218/nua3500-u-boot-v2020.07.git;protocol=https"

SRCBRANCH = "2020.07"
SRC_URI = "${UBOOT_SRC}"
#SRCREV= "2f5fbb5b39f7b67044dda5c35e4a4b31685a3109"
SRCREV = "master"

PV = "${SRCBRANCH}"
S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

LOCALVERSION ?= "-${SRCBRANCH}"

#UBOOT_MACHINE = "imx8qxp_mek_defconfig"

#do_compile_append_nua3500(){
#	oe_runmake -C ${S} O=${B}/${UBOOT_MACHINE} DEVICE_TREE=${devicetree} DEVICE_TREE_EXT=${devicetree}.dtb
#	cp ${B}/configs/imx8qxp_mek_defconfig ${B}/.config
#	oe_runmake 
#}
#BOOT_TOOLS = "imx-boot-tools"

#do_deploy_append_nua3500 () {
#do_deploy_append_mx8m () {
#    # Deploy the mkimage, u-boot-nodtb.bin and fsl-imx8mq-XX.dtb for mkimage to generate boot binary
#    if [ -n "${UBOOT_CONFIG}" ]
#    then
#        for config in ${UBOOT_MACHINE}; do
#            i=$(expr $i + 1);
#            for type in ${UBOOT_CONFIG}; do
#                j=$(expr $j + 1);
#                if [ $j -eq $i ]
#                then
#                    install -d ${DEPLOYDIR}/${BOOT_TOOLS}
#                    install -m 0777 ${B}/${config}/arch/arm/dts/${UBOOT_DTB_NAME}  ${DEPLOYDIR}/${BOOT_TOOLS}
#                    install -m 0777 ${B}/${config}/tools/mkimage  ${DEPLOYDIR}/${BOOT_TOOLS}/mkimage_uboot
#                    install -m 0777 ${B}/${config}/u-boot-nodtb.bin  ${DEPLOYDIR}/${BOOT_TOOLS}/u-boot-nodtb.bin-${MACHINE}-${UBOOT_CONFIG}
#                fi
#            done
#            unset  j
#        done
#        unset  i
#    fi
#
#}

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(nua3500)"
