inherit image_types

do_image_nand[depends] = "virtual/trusted-firmware-a:do_deploy \
		         ${@bb.utils.contains('MACHINE_FEATURES', 'optee', 'virtual/optee-os:do_deploy', '',d)} \
                         virtual/kernel:do_deploy \
                         virtual/bootloader:do_deploy \
                         "

do_image_spinand[depends] = "virtual/trusted-firmware-a:do_deploy \
                              ${@bb.utils.contains('MACHINE_FEATURES', 'optee', 'virtual/optee-os:do_deploy', '',d)} \
                              virtual/kernel:do_deploy \
                              virtual/bootloader:do_deploy \
                             "

do_image_sdcard[depends] = "virtual/trusted-firmware-a:do_deploy \
                            ${@bb.utils.contains('MACHINE_FEATURES', 'optee', 'virtual/optee-os:do_deploy', '',d)} \
                            virtual/kernel:do_deploy \
                            virtual/bootloader:do_deploy \
                           "

# Generate the FIP image  with the bl2.bin and required Device Tree
generate_fip_image(){
if ${@bb.utils.contains('MACHINE_FEATURES', 'optee', 'true', 'false', d)}; then
    ${DEPLOY_DIR_IMAGE}/fiptool create \
        --soc-fw ${DEPLOY_DIR_IMAGE}/bl31-${TFA_PLATFORM}.bin \
        --tos-fw ${DEPLOY_DIR_IMAGE}/tee-header_v2-optee.bin \
        --tos-fw-extra1 ${DEPLOY_DIR_IMAGE}/tee-pager_v2-optee.bin \
        --nt-fw ${DEPLOY_DIR_IMAGE}/u-boot.bin \
        ${DEPLOY_DIR_IMAGE}/fip_with_optee.bin
    (cd ${DEPLOY_DIR_IMAGE}; ln -sf fip_with_optee.bin fip.bin)
else
    ${DEPLOY_DIR_IMAGE}/fiptool create \
        --soc-fw ${DEPLOY_DIR_IMAGE}/bl31-${TFA_PLATFORM}.bin \
        --nt-fw ${DEPLOY_DIR_IMAGE}/u-boot.bin \
        ${DEPLOY_DIR_IMAGE}/fip_without_optee.bin
    (cd ${DEPLOY_DIR_IMAGE}; ln -sf fip_without_optee.bin fip.bin)
fi
   # generate header file
   (cd ${DEPLOY_DIR_IMAGE}; nuwriter/nuwriter -c nuwriter/header.json)
}

IMAGE_CMD_spinand() {
    (cd ${DEPLOY_DIR_IMAGE}; ln -sf ${IMAGE_NAME}.rootfs.ubi rootfs.ubi)
    generate_fip_image
    (cd ${DEPLOY_DIR_IMAGE}; \
     nuwriter/nuwriter -c nuwriter/pack-spinand.json; \
     ln -sf $(readlink -f pack/pack.bin) ${IMAGE_BASENAME}-${MACHINE}-spinand.pack \
    )
} 

IMAGE_CMD_nand() {
    (cd ${DEPLOY_DIR_IMAGE}; ln -sf ${IMAGE_NAME}.rootfs.ubi rootfs.ubi)
    generate_fip_image
    (cd ${DEPLOY_DIR_IMAGE}; \
     nuwriter/nuwriter -c nuwriter/pack-nand.json; \
     ln -sf $(readlink -f pack/pack.bin) ${IMAGE_BASENAME}-${MACHINE}-nand.pack \
    )
}

IMAGE_CMD_sdcard() {
    (cd ${DEPLOY_DIR_IMAGE}; ln -sf ${IMAGE_NAME}.rootfs.ext2 rootfs.ext2)
    generate_fip_image
    (cd ${DEPLOY_DIR_IMAGE}; \
     nuwriter/nuwriter -c nuwriter/pack-sdcard.json; \
     ln -sf $(readlink -f pack/pack.bin) ${IMAGE_BASENAME}-${MACHINE}-sdcard.pack \
    )
}

