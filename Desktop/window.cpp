#include <iostream>
#include <QObject>
#include <QtWidgets>

#define MENU_ITEMS 1
#define MAIN_TABS 3

#include "window.h"

window::window() {

        setupMenuEntries();
        setupMenuBar();
        setupCenter();

        coreWin.setFixedSize(400, 400);
        coreWin.show();
}

void window::setupMenuBar(){
        for (int i = 0; i < MENU_ITEMS; i++){
                menuBar.addMenu(&menuEntries[i]);
        }

        coreWin.setMenuBar(&menuBar);
}

void window::setupMenuEntries() {
        menuEntries = new QMenu[MENU_ITEMS]();

        menuEntries[0].setTitle("File");

        QMenu &File = menuEntries[0];

        auto openSettings_ = [this]() {this->openSettings();};
        auto openFeed_ = [this]() {this->openFeed();};

        File.addAction("Feed", openFeed_, Qt::ALT + Qt::Key_F);

        File.addAction("Settings", openSettings_, Qt::ALT + Qt::Key_S);

        //File.addAction("Settings", this, &window::openSettings, Qt::ALT + Qt::Key_S);

        // Exit shortcut
        File.addAction("Quit", window::quit, Qt::ALT + Qt::Key_F4);

}

void window::setupCenter(){

        center.setTabBarAutoHide(true);
        center.setTabsClosable(true);

        center.setMovable(true);

        openFeed();

        QObject::connect(&center, SIGNAL(tabCloseRequested(int)), this, SLOT(closeTab(int)));

        coreWin.setCentralWidget(&center);
}

void window::quit() {
        exit(0);
}

void window::openSettings() {
        if (center.indexOf(&Settings) == -1){
                center.addTab(&Settings, "Settings");
        }
}

void window::openFeed() {
        if (center.indexOf(&Feed) == -1){
                center.addTab(&Feed, "Feed");
                center.tabBar()->tabButton(center.indexOf(&Feed), QTabBar::RightSide)->hide();
        }
}

void window::closeTab(int i) {
        center.removeTab(i);
}

void window::initSettings() {

}

void window::initFeed() {

}