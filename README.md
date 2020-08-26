# yocto_repo

repo init -u git://github.com/schung1218/yocto_repo.git -m  meta-nua3500/base/nua3500.xml

repo sync

# build yocto

DISTRO=nuvoton-nua3500-fb MACHINE=nua3500evb source  sources/init-build-env build

###### Usage:
	MACHINE=<machine> DISTRO=<distro> source sources/init-build-env <build-dir>
	<machine>    machine name
	<distro>     distro name
	<build-dir>  build directory

# step by step to build yocto
To build and use the yocto, do the following:
```
$ repo init -u git://github.com/schung1218/yocto_repo.git -m meta-nua3500/base/nua3500.xml
$ repo sync
$ DISTRO=nuvoton-nua3500-fb MACHINE=nua3500evb source  sources/init-build-env build
$ bitbake core-image-minimal
```
