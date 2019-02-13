QT       += core
QT       += network
QT       -= gui

TARGET = QUdpSocket
CONFIG   += console
CONFIG   -= app_bundle

TEMPLATE = app

HEADERS += myudp.h
SOURCES += main.cpp myudp.cpp