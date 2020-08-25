# Copyright (C) 2019-2020
# Copyright 2019-2020 Nuvoton
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Linux Kernel provided and supported by Nuvoton"
DESCRIPTION = "Linux Kernel provided and supported by Nuvoton nua3500"

inherit kernel

# We need to pass it as param since kernel might support more then one
# machine, with different entry points
NUA3500_KERNEL_LOADADDR = "0x80080000"
KERNEL_EXTRA_ARGS += "LOADADDR=${NUA3500_KERNEL_LOADADDR}"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

SRCBRANCH = "linux-5.4.y"
LOCALVERSION = "-${SRCBRANCH}"
KERNEL_SRC ?= "git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux.git;proctocl=git"
SRC_URI = "${KERNEL_SRC};branch=${SRCBRANCH}"
SRCREV="${AUTOREV}"
S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

SRC_URI += " \
	file://0001-Add-NUA3500.patch \
	file://0002-Update-Uart.patch \
	file://0003-Add-nua3500-pinfunc.h.patch \
	file://0004-Add-RTC-driver.patch \
	file://0005-USBH-Add-USB-Host-controller-EHCI0-1-and-OHCI0-1-2-d.patch \
	file://0006-KS-Add-Key-Store-driver.patch \
	file://0007-add-KPI-driver-and-update-dtsi-for-UART-RTC-KPI.patch \
	"

#DEPENDS += "lzop-native bc-native"
DEPENDS += "openssl-native util-linux-native libyaml-native"

DEFAULT_PREFERENCE = "1"


# =========================================================================
# Kernel
# =========================================================================
# Kernel device tree
KERNEL_DEVICETREE = "nuvoton/nua3500-evb.dtb"

# Kernel image type
KERNEL_IMAGETYPE = "Image"

# Defconfig
#KERNEL_DEFCONFIG        = "defconfig"

# Module kernel signature
#KERNEL_SIGN_ENABLE ?= "0"
#EXTRA_OEMAKE += "${@oe.utils.ifelse(d.getVar('KERNEL_SIGN_ENABLE') == '1', 'INSTALL_MOD_STRIP=1','')}"

do_configure_prepend() {
    bbnote "Copying defconfig"
    cp ${S}/arch/${ARCH}/configs/nua3500_defconfig ${WORKDIR}/defconfig
}

COMPATIBLE_MACHINE = "(nua3500)"
