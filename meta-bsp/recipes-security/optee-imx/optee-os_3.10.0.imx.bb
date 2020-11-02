# Copyright (C) 2020 NXP
require optee-os.imx.inc

FILESEXTRAPATHS_prepend := "${BSPDIR}/sources/meta-freescale/recipes-security/optee-imx/${PN}:"

DEPENDS_append = " python3-pycryptodomex-native"

OPTEE_OS_SRC ?= "git://source.codeaurora.org/external/imx/imx-optee-os.git;protocol=https"
SRC_URI = "${OPTEE_OS_SRC};branch=${SRCBRANCH}"

SRCBRANCH = "imx_3.10.y"
SRCREV = "b609848fdbe443af4c272b29068dbcc424939d50"

# tee-init_load_addr.txt has been remove in lates optee-os version.
# to keep backward compatibility with existing optee-os recipe.
do_compile_append () {
    if [ "${OPTEE_ARCH}" != "arm64" ]; then
        IMX_LOAD_ADDR=`${TARGET_PREFIX}readelf -h ${B}/core/tee.elf | grep "Entry point address" | awk '{print $4}'` && \
        echo ${IMX_LOAD_ADDR} > ${B}/core/tee-init_load_addr.txt
    fi
}