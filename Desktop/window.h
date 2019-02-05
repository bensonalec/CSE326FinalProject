#include <iostream>
#include <QtWidgets>

class window
{
        public:
                window();

        private:
                void setupMenuBar();
                void setupMenuEntries();

                QMainWindow coreWin;
                QMenuBar menuBar;
                QMenu *menuEntries;

};

class terminate {
        public:
                static void quit();
};

class settings {
        public:
                static void toggle();
                static QMessageBox settingsWin;        

        private:
};