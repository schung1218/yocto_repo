# yocto_repo

repo init -u git://github.com/schung1218/yocto_repo.git -m nua3500.xml

repo sync

# build yocto

DISTRO=nuvoton-nua3500-fb MACHINE=nua3500evb source setup-environment build

bitbake core-image-minimal
