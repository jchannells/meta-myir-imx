# Copyright 2021 NXP

DESCRIPTION = "NXP Audio Front End (AFE) for incorporating Voice Assistants"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7bdef19938f3503cfc4c586461f99012"

PV = "1.0+git${SRCPV}" 

SRCBRANCH = "master"
NXPAFE_SRC ?= "git://source.codeaurora.org/external/mmcsh/audio-front-end.git;protocol=https"
SRC_URI = " \
    ${NXPAFE_SRC};branch=${SRCBRANCH} \
"

SRCREV = "534c01d6561ac3c80a1fb74e95ba77d6f7bbe5e7"

S = "${WORKDIR}/git"

DEPENDS += "alsa-lib"

TARGET_CC_ARCH += "${LDFLAGS}"

do_compile() {
        oe_runmake clean
        oe_runmake all
}

do_install() {
        install -d ${D}${libdir}/nxp-afe
        install -d ${D}/unit_tests/nxp-afe
        install -m 0644 ${WORKDIR}/deploy_afe/*.so.1.0 ${D}${libdir}/nxp-afe
        ln -sf -r ${D}${libdir}/nxp-afe/libdummyimpl.so.1.0 ${D}${libdir}/nxp-afe/libdummyimpl.so
        install -m 0755 ${WORKDIR}/deploy_afe/afe ${D}/unit_tests/nxp-afe
        install -m 0644 ${WORKDIR}/deploy_afe/asound.conf    ${D}/unit_tests/nxp-afe
        install -m 0644 ${WORKDIR}/deploy_afe/TODO.md    ${D}/unit_tests/nxp-afe
}

FILES_${PN} += "/unit_tests"
INSANE_SKIP_${PN} += "dev-so"