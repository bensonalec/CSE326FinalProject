#include <iostream>
#include <QtWidgets>

#define MENU_ITEMS 1

#include "window.h"

window::window() {
        setupMenuEntries();
        setupMenuBar();

        coreWin.setCentralWidget(new QPlainTextEdit);
        coreWin.setMenuBar(&menuBar);
        coreWin.show();
}

void window::setupMenuBar(){
        for (int i = 0; i < MENU_ITEMS; i++){
                menuBar.addMenu(&menuEntries[i]);
        }
}

void window::setupMenuEntries() {
        menuEntries = new QMenu[MENU_ITEMS]();

        menuEntries[0].setTitle("File");

        QMenu &File = menuEntries[0];

        File.addAction("Preferences", settings::toggle, Qt::ALT + Qt::Key_S);

        // Exit shortcut
        File.addAction("Quit", terminate::quit, Qt::ALT + Qt::Key_F4);


        //menuEntries[1].setTitle("Settings");
}

void terminate::quit() {
        exit(0);
}


void settings::toggle() {
        QMessageBox settingsWin;
        settingsWin.setText("This is a test");
        settingsWin.setWindowTitle("another test");
        settingsWin.exec();
}