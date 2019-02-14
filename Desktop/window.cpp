#include <iostream>
#include <QObject>
#include <QtWidgets>

#define MENU_ITEMS 1
#define MAIN_TABS 3
#define SETTING_ITEMS 4

#include "window.h"

window::window(QApplication *par) {

        setupTrayIcon(par);

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


        if (&settingsLayout) {
                settingsLayout.~QGridLayout();
        }
        if (&feedLayout) {
                feedLayout.~QGridLayout();
        }
        if (&sysIcon) {
                sysIcon->~QSystemTrayIcon();
        }
        if (&Feed) {
                Feed.~QWidget();
        }
        if (&Settings) {
                Settings.~QWidget();
        }
        if (&center) {
                center.~QWidget();
        }
        if (&menuEntries) {
                for (int i = 0; i < MENU_ITEMS; i++)
                        menuEntries[i].~QMenu();
        }
        if (&menuBar) {
                menuBar.~QMenuBar();
        }
        if (&coreWin) {
                coreWin.~QMainWindow();
        }
        

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
        // TBA when settings are declared
}

void window::initFeed() {
        // TBA when packets be sending

        QPushButton *qb = new QPushButton("Test");

        QObject::connect(qb, SIGNAL(clicked()), this, SLOT(sendNotif()));

        feedLayout.addWidget(qb, 0, 0, Qt::AlignLeft);

        Feed.setLayout(&feedLayout);

}

void window::setupTrayIcon(QApplication* par) {
        trayIcon = new QIcon("warning.svg");
        sysIcon = new QSystemTrayIcon(*trayIcon, par);
        sysIcon->show();
}

void window::sendNotif(){

        if (sysIcon)
                sysIcon->showMessage("Test Notification", "This is a test to see if the notifications work");

}