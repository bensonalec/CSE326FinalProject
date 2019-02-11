QT       += core
QT       += network
QT       -= gui

TARGET = QUdpSocket
CONFIG   += console
CONFIG   -= app_bundle

TEMPLATE = app

HEADERS += listen.h
SOURCES += main.cpp Listen.cpp

