#!/bin/sh

build() {
        if [ -f Makefile ]; then
                ./cleanup.sh
        fi
        case $1 in 
        windows)
                i686-w64-mingw32.static-qmake-qt5
                make MXE_TARGETS=x86_64-w64-mingw32.static QT5_BUILD_TYPE=-release
                if [ $? != 0 ]; then
                        printf '\n\nError encountered. Cleaning up\n\n\n'
                        ./cleanup.sh
                fi
        ;;
        windows_64)
                i686-w64-mingw32.static-qmake-qt5
                make MXE_TARGETS=x86_64-w64-mingw32.static qtbase
                if [ $? != 0 ]; then
                        printf '\n\nError encountered. Cleaning up\n\n\n'
                        ./cleanup.sh
                fi
        ;;
        windows_32)
                i686-w64-mingw32.static-qmake-qt5
                make MXE_TARGETS=i686-w64-mingw32.static qtbase
                if [ $? != 0 ]; then
                        printf '\n\nError encountered. Cleaning up\n\n\n'
                        ./cleanup.sh
                fi
        ;;
        linux)
                qmake
                make
                if [ $? != 0 ]; then
                        printf '\n\nError encountered. Cleaning up\n\n\n'
                        ./cleanup.sh
                fi
        ;;
        macos)
                printf 'Mac OS compilation has yet to be implemented\n'
        ;;
        esac
}

if [ -z $1 ]; then
        printf 'Usage:\n./build.sh -s SYSTEM\nThe program will assume linux\n'
        system='linux'
        build $system
fi

while getopts "s:" opt; do

        case $opt in
        s)
                system=$OPTARG
                        build $system
                ;;    
        esac

done
