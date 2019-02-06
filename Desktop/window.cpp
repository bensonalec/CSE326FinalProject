#include <iostream>
#include <QObject>
#include <QtWidgets>

#define MENU_ITEMS 1
#define MAIN_TABS 3
#define SETTING_ITEMS 4

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
        auto quit_ = [this]() {this->quit();};

        File.addAction("Feed", openFeed_, Qt::ALT + Qt::Key_F);

        File.addAction("Settings", openSettings_, Qt::ALT + Qt::Key_S);

        // Exit shortcut
        File.addAction("Quit", quit_, Qt::ALT + Qt::Key_F4);

}

void window::setupCenter(){

        center.setTabBarAutoHide(true);
        center.setTabsClosable(true);

        center.setMovable(true);

        openFeed();

        initFeed();

        QObject::connect(&center, SIGNAL(tabCloseRequested(int)), this, SLOT(closeTab(int)));

        coreWin.setCentralWidget(&center);
}

void window::quit() {
        Settings.~QWidget();
        Feed.~QWidget();
        for (int i = 0; i < MENU_ITEMS; i++){
                menuEntries[i].~QMenu();
        }
        center.~QTabWidget();
        menuBar.~QMenuBar();
        coreWin.~QMainWindow();
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
        // TBA when settings are declared
}

void window::initFeed() {
        // TBA when packets be sending
}